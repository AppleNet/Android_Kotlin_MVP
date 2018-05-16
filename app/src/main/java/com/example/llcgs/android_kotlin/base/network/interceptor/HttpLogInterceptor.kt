package com.example.llcgs.android_kotlin.base.network.interceptor

import android.text.TextUtils
import com.example.llcgs.android_kotlin.BuildConfig
import com.example.llcgs.android_kotlin.utils.log.logD
import com.example.llcgs.android_kotlin.utils.log.logI
import com.example.llcgs.android_kotlin.utils.log.wtf
import okhttp3.*
import okio.Buffer
import java.lang.StringBuilder
import java.util.concurrent.TimeUnit

/**
 * com.example.llcgs.android_kotlin.base.network.interceptor.HttpLogInterceptor
 * @author liulongchao
 * @since 2018/5/11
 */
object HttpLogInterceptor: Interceptor {

    private lateinit var httpIgnoredKeyProvider: HttpIgnoredKeyProvider

    fun setIgnoredKeyProvider(httpIgnoredKeyProvider: HttpIgnoredKeyProvider): HttpLogInterceptor{
        this.httpIgnoredKeyProvider = httpIgnoredKeyProvider
        return this
    }

    override fun intercept(chain: Interceptor.Chain): Response? {
        val request: Request = chain.request()
        if(BuildConfig.DEBUG){
            return chain.proceed(request)
        }
        // 请求日志拦截
        log4Request(request)
        // 执行请求，计算请求时间
        val startNs = System.nanoTime()
        try {
            val response = chain.proceed(request)
            val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
            //响应日志拦截
            return log4Response(response, tookMs)
        } catch (e: Exception) {
            "${getFullUrl(request)} <-- HTTP FAILED: $e".logI()
        }
        return chain.proceed(request)
    }

    private fun log4Request(request: Request){
        val stringBuilder = StringBuilder()
        val requestBody = request.body()
        var contentType = ""
        if (requestBody?.contentType() != null){
            contentType = requestBody.contentType()?.type() + "/" + requestBody.contentType()?.subtype()
        }
        if (contentType.isEmpty()){
            contentType = ContentTypeEnum.form_urlencoded
        }
        try {
            val fullUrlQueryParams = getFullUrl(request)
            stringBuilder.append("\n" + request.method() + fullUrlQueryParams + "\n Content-Type = " + contentType)
            val headers = request.headers()
            val count = headers.size()
            stringBuilder.append(if (count>0) "\n请求头" else "")
            for (i in 0 until count){
                val name = headers.name(i)
                if (httpIgnoredKeyProvider.isInIgnoredRequestKeys(name)){
                    continue
                }
                stringBuilder.append("\n" + name + "=" + headers.value(i))
            }
            val queryParams: String = request.url().query()?:""
            if (queryParams.isNotEmpty()) {
                stringBuilder.append("\n请求行:\n")
            }
            val length = queryParams.length
            for (i in 0 until length){
                val charAt = queryParams.toCharArray()[i]
                if (charAt != '&'){
                    stringBuilder.append(charAt)
                }else{
                    stringBuilder.append("\n")
                }
            }
            if (requestBody == null){
                "打印请求参数: $stringBuilder".logD()
                return
            }
            if (requestBody is FormBody){
                if (requestBody.size() > 0){
                    stringBuilder.append("\n请求体\n")
                }
                for (i in 0 until requestBody.size()){
                    val name = requestBody.name(i)
                    if (httpIgnoredKeyProvider.isInIgnoredRequestKeys(name)) {
                        continue
                    }
                    val value = requestBody.value(i)
                    stringBuilder.append("$name=$value\n")
                }
            }else if (requestBody is MultipartBody){
                val partList = requestBody.parts()
                if (partList.size > 0) {
                    stringBuilder.append("\n请求体:\n")
                }
                partList.forEach {
                    val partHeaders = it.headers()
                    var partHeadContent = partHeaders?.value(0)
                    val partRequestBody = it.body()
                    when {
                        OkHttpRequestBodyClassNameEnum.textRequestBody == partRequestBody::class.java.name -> {
                            partHeadContent = subString4End(partHeadContent.toString(), ";")
//                            if (httpIgnoredKeyProvider.isInIgnoredRequestKeys(partHeadContent)) {
//                                continue
//                            }
                            val buffer = Buffer()
                            partRequestBody.writeTo(buffer)
                            val readUtf8 = buffer.readUtf8()
                            stringBuilder.append("$partHeadContent = $readUtf8\n")
                        }
                        OkHttpRequestBodyClassNameEnum.fileRequestBody == partRequestBody::class.java.name -> stringBuilder.append("application/octet-stream; $partHeadContent\n")
                        else -> "没有匹配到的 partRequestBody = ${partRequestBody::class.java}".logD()
                    }
                }
            }else if(OkHttpRequestBodyClassNameEnum.textRequestBody == requestBody::class.java.name
                ||OkHttpRequestBodyClassNameEnum.retrofitTextRequestBody == requestBody::class.java.name
                ||OkHttpRequestBodyClassNameEnum.text1RequestBody == requestBody::class.java.name){
                val buffer = Buffer()
                requestBody.writeTo(buffer)
                if (buffer.size() > 0){
                    stringBuilder.append("\n 请求体: \n")
                }
                val readUtf8 = buffer.readUtf8()
                stringBuilder.append(readUtf8 + "\n")
            }else{
               "没有匹配到的 requestBody = ${requestBody::class.java}".logD()
            }
        }catch (e: Exception){
            wtf(e, false)
        }
        "打印请求参数: $stringBuilder".logD()
    }

    private fun log4Response(response: Response, tookMs: Long): Response{
        val stringBuilder = StringBuilder()
        val builder = response.newBuilder()
        val cloneResponse = builder.build()
        var responseBody = cloneResponse.body()
        try {
            stringBuilder.append(cloneResponse.request().method() + ' ' + cloneResponse.request().url() + " (" + tookMs + "ms）")
            val headers = cloneResponse.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                val name = headers.name(i)
                if (isNotIgnoreResponseHead(name)) {
                    stringBuilder.append("\n" + name + "=" + headers.value(i))
                }
                i++
            }
            val mediaType = responseBody?.contentType()
            val isMultipartFile = isMultipartFile(mediaType)
            if (isMultipartFile) {
                stringBuilder.append("\n富媒体文件无法用字符串描述（正在下载 ...）\n")
            }else{
                val body = responseBody?.string() ?: ""
                stringBuilder.append("\n" + body)
                responseBody = ResponseBody.create(responseBody?.contentType(), body)
            }
            "打印返回数据: \n $stringBuilder".logD()
            return response.newBuilder().body(responseBody).build()
        }catch (e: Exception){
            wtf(e, false)
        }
        return response
    }

    private fun getFullUrl(request: Request):String{
        var fullUrlQueryParams = request.url().toString()
        val containsY = fullUrlQueryParams.contains("?")
        if (containsY) {
            val indexOf = fullUrlQueryParams.indexOf("?")
            fullUrlQueryParams = fullUrlQueryParams.substring(0, indexOf)
        }
        return fullUrlQueryParams
    }

    /**
     * 从 最后一个 split开始，到text结束的字串（不包含split）
     */
    private fun subString4End(text: String, split: String): String{
        if(TextUtils.isEmpty(text)){
            return ""
        }
        val lastIndexOf = text.lastIndexOf(split)
        if (lastIndexOf < 0){
            return text
        }
        return text.substring(lastIndexOf + 1, text.length)
    }

    private fun isIgnoreResponseHead(head: String): Boolean {
        var i = 0
        while (i < ignoreResponseHeads.size) {
            if (ignoreResponseHeads[i].equals(head, true)) {
                return true
            }
            i++
        }
        return false
    }

    private fun isNotIgnoreResponseHead(head: String): Boolean = !isIgnoreResponseHead(head)

    private fun isMultipartFile(mediaType: MediaType?): Boolean {
        if (mediaType?.subtype() != null) {
            val contentType = mediaType.type() + "/" + mediaType.subtype()
            if (ContentTypeEnum.multipart_form_data.equals(contentType, true)) {
                return true
            }
            if (ContentTypeEnum.octet_stream.equals(contentType, true)) {
                return true
            }
        }
        return false
    }

    private object OkHttpRequestBodyClassNameEnum {
        const val fileRequestBody = "okhttp3.RequestBody$3"
        const val textRequestBody = "okhttp3.RequestBody$2"
        const val text1RequestBody = "okhttp3.RequestBody$1"
        const val retrofitTextRequestBody = "retrofit2.RequestBuilder\$ContentTypeOverridingRequestBody"
    }

    private object ContentTypeEnum {
        const val octet_stream = "application/octet-stream"
        const val json = "application/json"
        const val multipart_form_data = "multipart/form-data"
        const val form_urlencoded = "application/x-www-form-urlencoded"
    }

    private val ignoreResponseHeads = arrayOf(
        "User-Agent",
        "Accept-Charset",
        "Accept-Language",
        "Server",
        "Date",
        "Content-PrintLevel",
        "Transfer-Encoding",
        "Set-Cookie"
    )
}