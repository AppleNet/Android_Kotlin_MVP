/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.llcgs.android_kotlin.material.mediaplayer.service

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.XmlResourceParser
import android.os.Process
import android.util.Base64

import com.example.llcgs.android_kotlin.R
import com.example.llcgs.android_kotlin.utils.media.LogHelper

import org.xmlpull.v1.XmlPullParserException

import java.io.IOException
import java.util.ArrayList
import java.util.HashMap

/**
 * Validates that the calling package is authorized to browse a
 * [android.service.media.MediaBrowserService].
 *
 * The list of allowed signing certificates and their corresponding package names is defined in
 * res/xml/allowed_media_browser_callers.xml.
 *
 * If you add a new valid caller to allowed_media_browser_callers.xml and you don't know
 * its signature, this class will print to logcat (INFO level) a message with the proper base64
 * version of the caller certificate that has not been validated. You can copy from logcat and
 * paste into allowed_media_browser_callers.xml. Spaces and newlines are ignored.
 */
class PackageValidator(ctx: Context) {

    /**
     * Map allowed callers' certificate keys to the expected caller information.
     *
     */
    private val mValidCertificates: Map<String, ArrayList<CallerInfo>>

    init {
        mValidCertificates = readValidCertificates(ctx.resources.getXml(
                R.xml.allowed_media_browser_callers))
    }

    private fun readValidCertificates(parser: XmlResourceParser): Map<String, ArrayList<CallerInfo>> {
        val validCertificates = HashMap<String, ArrayList<CallerInfo>>()
        try {
            var eventType = parser.next()
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG && parser.name == "signing_certificate") {

                    val name = parser.getAttributeValue(null, "name")
                    val packageName = parser.getAttributeValue(null, "package")
                    val isRelease = parser.getAttributeBooleanValue(null, "release", false)
                    val certificate = parser.nextText().replace("\\s|\\n".toRegex(), "")

                    val info = CallerInfo(name, packageName, isRelease)

                    var infos: ArrayList<CallerInfo>? = validCertificates[certificate]
                    if (infos == null) {
                        infos = ArrayList()
                        validCertificates[certificate] = infos
                    }
                    LogHelper.v(TAG, "Adding allowed caller: ", info.name,
                            " package=", info.packageName, " release=", info.release,
                            " certificate=", certificate)
                    infos.add(info)
                }
                eventType = parser.next()
            }
        } catch (e: XmlPullParserException) {
            LogHelper.e(TAG, e, "Could not read allowed callers from XML.")
        } catch (e: IOException) {
            LogHelper.e(TAG, e, "Could not read allowed callers from XML.")
        }

        return validCertificates
    }

    /**
     * @return false if the caller is not authorized to get data from this MediaBrowserService
     */
    fun isCallerAllowed(context: Context, callingPackage: String, callingUid: Int): Boolean {
        // Always allow calls from the framework, self app or development environment.
        if (Process.SYSTEM_UID == callingUid || Process.myUid() == callingUid) {
            return true
        }

        if (isPlatformSigned(context, callingPackage)) {
            return true
        }

        val packageInfo = getPackageInfo(context, callingPackage) ?: return false
        if (packageInfo.signatures.size != 1) {
            LogHelper.w(TAG, "Caller does not have exactly one signature certificate!")
            return false
        }
        val signature = Base64.encodeToString(
                packageInfo.signatures[0].toByteArray(), Base64.NO_WRAP)

        // Test for known signatures:
        val validCallers = mValidCertificates[signature]
        if (validCallers == null) {
            LogHelper.v(TAG, "Signature for caller ", callingPackage, " is not valid: \n", signature)
            if (mValidCertificates.isEmpty()) {
                LogHelper.w(TAG, "The list of valid certificates is empty. Either your file ",
                        "res/xml/allowed_media_browser_callers.xml is empty or there was an error ",
                        "while reading it. Check previous log messages.")
            }
            return false
        }

        // Check if the package name is valid for the certificate:
        val expectedPackages = StringBuffer()
        for (info in validCallers) {
            if (callingPackage == info.packageName) {
                LogHelper.v(TAG, "Valid caller: ", info.name, "  package=", info.packageName,
                        " release=", info.release)
                return true
            }
            expectedPackages.append(info.packageName).append(' ')
        }

        LogHelper.i(TAG, "Caller has a valid certificate, but its package doesn't match any ",
                "expected package for the given certificate. Caller's package is ", callingPackage,
                ". Expected packages as defined in res/xml/allowed_media_browser_callers.xml are (",
                expectedPackages, "). This caller's certificate is: \n", signature)

        return false
    }

    /**
     * @return true if the installed package signature matches the platform signature.
     */
    private fun isPlatformSigned(context: Context, pkgName: String): Boolean {
        val platformPackageInfo = getPackageInfo(context, "android")

        // Should never happen.
        if (platformPackageInfo == null || platformPackageInfo.signatures == null
                || platformPackageInfo.signatures.size == 0) {
            return false
        }

        val clientPackageInfo = getPackageInfo(context, pkgName)

        return (clientPackageInfo?.signatures != null
                && clientPackageInfo.signatures.isNotEmpty() &&
                platformPackageInfo.signatures[0] == clientPackageInfo.signatures[0])
    }

    /**
     * @return [PackageInfo] for the package name or null if it's not found.
     */
    @SuppressLint("PackageManagerGetSignatures")
    private fun getPackageInfo(context: Context, pkgName: String): PackageInfo? {
        try {
            val pm = context.packageManager
            return pm.getPackageInfo(pkgName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            LogHelper.w(TAG, e, "Package manager can't find package: ", pkgName)
        }

        return null
    }

    private class CallerInfo(internal val name: String, internal val packageName: String, internal val release: Boolean)

    companion object {
        private val TAG = LogHelper.makeLogTag(PackageValidator::class.java)
    }
}
