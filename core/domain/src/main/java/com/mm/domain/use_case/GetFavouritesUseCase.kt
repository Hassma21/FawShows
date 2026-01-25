package com.mm.domain.use_case

import com.mm.common.network.Dispatcher
import com.mm.common.network.FsDispatchers
import com.mm.domain.model.Favourite
import com.mm.domain.repository.FavouriteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(
    private val repository: FavouriteRepository,
    @Dispatcher(FsDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(): Flow<List<Favourite>> =
        repository.getFavourites()
            .flowOn(ioDispatcher)
}
