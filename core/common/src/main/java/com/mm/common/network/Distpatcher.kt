package com.mm.common.network

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

@Qualifier
@Retention(RUNTIME)
annotation class Dispatcher(val niaDispatcher: FsDispatchers)

enum class FsDispatchers {
    Default,
    IO,
}