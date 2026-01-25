package com.mm.fawshows

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.mm.common.logger.CrashLogger
import javax.inject.Inject

class FirebaseCrashLogger @Inject constructor() : CrashLogger {

    override fun log(message: String) {
        FirebaseCrashlytics.getInstance().log(message)
    }

    override fun recordException(throwable: Throwable) {
        FirebaseCrashlytics.getInstance().recordException(throwable)
    }
}
