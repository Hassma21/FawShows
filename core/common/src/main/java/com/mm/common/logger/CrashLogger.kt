package com.mm.common.logger

interface CrashLogger {
    fun log(message: String)
    fun recordException(throwable: Throwable)
}