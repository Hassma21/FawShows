package com.mm.domain.use_case

import com.mm.common.network.Dispatcher
import com.mm.common.network.FsDispatchers
import com.mm.domain.repository.FavouriteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoveFavouriteUseCase @Inject constructor(
    private val repository: FavouriteRepository,
    @Dispatcher(FsDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(tmdbId: Long) =
        withContext(ioDispatcher) {
            repository.removeFavourite(tmdbId)
        }
}
