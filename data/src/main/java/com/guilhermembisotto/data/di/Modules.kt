package com.guilhermembisotto.data.di

import com.guilhermembisotto.data.RetrofitInitializer
import com.guilhermembisotto.data.songs.SongsRepositoryImpl
import com.guilhermembisotto.data.songs.contracts.SongsDataSource
import com.guilhermembisotto.data.songs.contracts.SongsRepository
import com.guilhermembisotto.data.songs.remote.datasource.SongsRemoteDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

private val apiServiceModule = module {
    single { RetrofitInitializer().createSongsServiceApi() }
}

private val repositoryModule = module {
    single<SongsRepository> { SongsRepositoryImpl(get()) }
}

private val dataSourceModule = module {
    single<SongsDataSource.Remote> {
        SongsRemoteDataSource(get())
    }
}

fun getDataModules(): List<Module> = listOf(apiServiceModule, dataSourceModule, repositoryModule)
