package com.mm.domain.use_case

import com.mm.common.network.Dispatcher
import com.mm.common.network.FsDispatchers
import com.mm.domain.model.MediaDetail
import com.mm.domain.model.MediaType
import com.mm.domain.repository.MediaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.mm.common.result.Result
import com.mm.domain.FlowHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn

class GetMediaDetailUseCase @Inject constructor(
    private val mediaRepository: MediaRepository,
    @Dispatcher(FsDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher,
    private val flowHelper: FlowHelper
) {
    operator fun invoke(
        tmdbId: Long,
        mediaType: Int,
        language: String = "en-US"
    ): Flow<Result<MediaDetail>> =
        flowHelper.flowResult {
            if (mediaType == MediaType.Movie.ordinal)
                mediaRepository.getMovieDetail(tmdbId, language)
            else
                mediaRepository.getTvDetail(tmdbId, language)
        }.flowOn(ioDispatcher)
}




