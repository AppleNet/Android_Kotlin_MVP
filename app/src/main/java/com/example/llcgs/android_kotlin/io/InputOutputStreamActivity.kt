package com.example.llcgs.android_kotlin.io

import android.os.Bundle
import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.base.activity.BaseActivity
import com.example.llcgs.android_kotlin.io.presenter.impl.InputOutputStreamPresenter
import com.example.llcgs.android_kotlin.io.view.InputOutputStreamView
import com.example.llcgs.android_kotlin.utils.log.logD
import java.io.*
import java.nio.ByteBuffer

class InputOutputStreamActivity: BaseActivity<InputOutputStreamView, InputOutputStreamPresenter>(){

    override fun createPresenter()= InputOutputStreamPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_output)
    }

    // ===================== 字节流读取 ===================== //
    /**
     *  将 sd 卡上的某个文件写入到内存中
     *
     * */
    private fun fileInputStream(fileName: String){

        // 拿到 sd 卡上的文件路径，转换成 file
        val file = File(fileName)
        // 将文件转换成写入到内存中的流
        val fileInputStream = FileInputStream(file)
        // 将流进行一层 cache
        val bufferedInputStream = BufferedInputStream(fileInputStream)
        // 将数据进行内存的写入工作
        val dataInputStream = DataInputStream(bufferedInputStream)
        // 每次读 1024*1024 个字节
        val byte = ByteArray(1024 * 1024)
        var len: Int = 0
        while ((dataInputStream.read(byte)).also { len = it } != -1) {
            val str = byte.toString()
            str.logD()
        }
        dataInputStream.close()
    }

    /**
     *  将内存中的数据写入到具体的某个文件中
     *
     * */
    private fun fileOutputStream(filePath: String) {
        // 根据路径生成相应的 file
        var file = File(filePath)
        // 判断是否存在
        if(file.exists()) {
            file.delete()
        }
        // 不存在或者不是文件夹
        if (!file.isDirectory){
            // 创建文件夹
            file.mkdirs()
        }
        // 根据路径和文件名 获取响应的 file
        file = File(filePath + System.currentTimeMillis())
        // 判断文件是否存在
        if (!file.exists()) {
            // 创建空白文件
            file.createNewFile()
        }
        // 开始写入数据
        val fileOutputStream = FileOutputStream(file)
        val bufferOutputStream = BufferedOutputStream(fileOutputStream)
        val dataOutputStream = DataOutputStream(bufferOutputStream)
        val byte = ByteArray(1024 * 1024)
        dataOutputStream.write(byte)
        dataOutputStream.close()
    }

    // ===================== 字节流读取 ===================== //
    private fun fileReader(fileName: String) {

        val file = File(fileName)
        val inputStream = FileInputStream(file)
        val inputStreamReader = InputStreamReader(inputStream)

        // or
        val fileReader = FileReader(file)

        val bufferReader = BufferedReader(inputStreamReader)

        var string: String
        while ((bufferReader.readLine().also { string = it }) != null) {
            string.logD()
        }
        bufferReader.close()
    }

    private fun fileWriter(fileName: String) {

        val file = File(fileName)
        val outputStream = FileOutputStream(file)
        val outputStreamWriter = OutputStreamWriter(outputStream)

        // or
        val fileWriter = FileWriter(file)

        val bufferedWriter = BufferedWriter(outputStreamWriter)
        bufferedWriter.write("")
        bufferedWriter.flush()
        bufferedWriter.close()
    }

    // ===================== FileChannel读取 ===================== //
    private fun fileChannel() {

        val sourceFile = File("")
        val randomAccessSourceFile = RandomAccessFile(sourceFile, "r")

        val targetFile = File("")
        val randomAccessTargetFile = RandomAccessFile(targetFile, "rw")

        val sourceFileChannel = randomAccessSourceFile.channel
        val targetFileChannel = randomAccessTargetFile.channel

        val byteBuffer = ByteBuffer.allocate(1024 * 1024)

        while ((sourceFileChannel.read(byteBuffer)) != -1) {
            byteBuffer.flip()
            targetFileChannel.write(byteBuffer)
            byteBuffer.clear()
        }
    }

}
