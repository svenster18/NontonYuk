package com.mohamadrizki.nontonyuk.core.data.source.remote.network

import com.mohamadrizki.nontonyuk.core.data.source.remote.response.MovieItem
import com.mohamadrizki.nontonyuk.core.data.source.remote.response.MovieResponse
import com.mohamadrizki.nontonyuk.core.data.source.remote.response.TvItem
import com.mohamadrizki.nontonyuk.core.data.source.remote.response.TvResponse
import retrofit2.http.*

interface ApiService {
    @GET("3/movie/popular?api_key=0ef68cbe1d05aed4015f6d6c0c896805")
    suspend fun getAllMovie(): MovieResponse

    @GET("3/tv/popular?api_key=0ef68cbe1d05aed4015f6d6c0c896805")
    suspend fun getAllTv(): TvResponse

    @GET("3/movie/{id}?api_key=0ef68cbe1d05aed4015f6d6c0c896805")
    suspend fun getMovie(
        @Path("id") id: Int
    ) : MovieItem

    @GET("3/tv/{id}?api_key=0ef68cbe1d05aed4015f6d6c0c896805")
    suspend fun getTv(
        @Path("id") id: Int
    ): TvItem
}