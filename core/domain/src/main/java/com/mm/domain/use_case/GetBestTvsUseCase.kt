package com.mm.domain.use_case

import com.mm.common.network.Dispatcher
import com.mm.common.network.FsDispatchers
import com.mm.common.result.Result
import com.mm.domain.FlowHelper
import com.mm.domain.model.MediaDto
import com.mm.domain.repository.MediaRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetBestTvsUseCase @Inject constructor(
    private val repository: MediaRepository,
    @Dispatcher(FsDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher,
    private val flowHelper: FlowHelper
) {
    operator fun invoke(): Flow<Result<List<MediaDto>>> =
        flowHelper.flowResult {
            repository.getBestTvs()
        }.flowOn(ioDispatcher)
}

