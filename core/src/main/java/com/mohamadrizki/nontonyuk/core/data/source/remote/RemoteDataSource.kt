package com.mohamadrizki.nontonyuk.core.data.source.remote

import android.util.Log
import com.mohamadrizki.nontonyuk.core.data.source.remote.network.ApiResponse
import com.mohamadrizki.nontonyuk.core.data.source.remote.network.ApiService
import com.mohamadrizki.nontonyuk.core.data.source.remote.response.MovieItem
import com.mohamadrizki.nontonyuk.core.data.source.remote.response.TvItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception


class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getAllMovie(): Flow<ApiResponse<List<MovieItem>>> {
        return flow {
            try {
                val response = apiService.getAllMovie()
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getAllTv(): Flow<ApiResponse<List<TvItem>>> {
        return flow {
            try {
                val response = apiService.getAllTv()
                val dataArray = response.results
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}