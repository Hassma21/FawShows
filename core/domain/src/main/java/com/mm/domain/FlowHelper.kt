package com.mm.domain

import com.mm.common.logger.CrashLogger
import com.mm.common.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FlowHelper @Inject constructor(
    private val crashLogger: CrashLogger
) {
    fun <T> flowResult(block: suspend () -> T): Flow<Result<T>> =
        flow {
            emit(Result.Loading)
            emit(Result.Success(block()))
        }.catch { e ->
            crashLogger.recordException(e)
            emit(Result.Error(e))
        }
}
