package com.mm.fawshows.di

import com.mm.common.logger.CrashLogger
import com.mm.fawshows.FirebaseCrashLogger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CrashLoggerModule {

    @Binds
    abstract fun bindCrashLogger(
        impl: FirebaseCrashLogger
    ): CrashLogger
}