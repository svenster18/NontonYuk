package com.mohamadrizki.nontonyuk.core.di

import androidx.room.Room
import com.mohamadrizki.nontonyuk.core.data.MovieTvRepository
import com.mohamadrizki.nontonyuk.core.data.source.local.LocalDataSource
import com.mohamadrizki.nontonyuk.core.data.source.local.room.MovieTvDatabase
import com.mohamadrizki.nontonyuk.core.data.source.remote.RemoteDataSource
import com.mohamadrizki.nontonyuk.core.data.source.remote.network.ApiService
import com.mohamadrizki.nontonyuk.core.domain.repository.IMovieTvRepository
import com.mohamadrizki.nontonyuk.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieTvDatabase>().movieTvDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieTvDatabase::class.java, "MovieTv.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMovieTvRepository> {
        MovieTvRepository(
            get(),
            get(),
            get()
        )
    }
}