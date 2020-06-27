package com.xently.persona.utils

import android.util.Log
import com.xently.persona.BuildConfig
import com.xently.persona.utils.Log.Type.*
import java.util.*


object Log {
    enum class Type {
        ASSERT,
        DEBUG,
        INFO,
        WARNING,
        ERROR,
        VERBOSE
    }

    /**
     * Shows logs only when build type is debug
     * @param tag: Log TAG
     * @param message: Log message
     * @param throwable: Exception to accompany the log
     * @see BuildConfig.BUILD_TYPE
     */
    @JvmOverloads
    @JvmStatic
    fun show(
        tag: String,
        message: String?,
        throwable: Throwable? = null,
        type: Type = DEBUG,
        logRelease: Boolean = false
    ) {
        if ((!isReleaseBuild() || logRelease) && message != null) {
            when (type) {
                DEBUG -> {
                    if (throwable == null) {
                        Log.d(tag, message)
                        return
                    }
                    Log.d(tag, message, throwable)
                }
                INFO -> {
                    if (throwable == null) {
                        Log.i(tag, message)
                        return
                    }
                    Log.i(tag, message, throwable)
                }
                WARNING -> {
                    if (throwable == null) {
                        Log.w(tag, message)
                        return
                    }
                    Log.w(tag, message, throwable)
                }
                ERROR -> {
                    if (throwable == null) {
                        Log.e(tag, message)
                        return
                    }
                    Log.e(tag, message, throwable)
                }
                VERBOSE -> {
                    if (throwable == null) {
                        Log.v(tag, message)
                        return
                    }
                    Log.v(tag, message, throwable)
                }
                ASSERT -> {
                    if (throwable == null) {
                        Log.wtf(tag, message)
                        return
                    }
                    Log.wtf(tag, message, throwable)
                }
            }
        }
    }

    /**
     * @see show
     */
    @JvmOverloads
    @JvmStatic
    fun show(
        tag: String, message: Any?, throwable: Throwable? = null, type: Type = DEBUG,
        logRelease: Boolean = false
    ) {
        show(tag, "$message", throwable, type, logRelease)
    }
}

fun isReleaseBuild() = BuildConfig.BUILD_TYPE.toLowerCase(Locale.ROOT).contains(Regex("^release$"))