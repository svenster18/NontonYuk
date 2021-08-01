package com.mohamadrizki.nontonyuk.core.data

import com.mohamadrizki.nontonyuk.core.data.source.local.LocalDataSource
import com.mohamadrizki.nontonyuk.core.data.source.remote.RemoteDataSource
import com.mohamadrizki.nontonyuk.core.data.source.remote.network.ApiResponse
import com.mohamadrizki.nontonyuk.core.data.source.remote.response.MovieItem
import com.mohamadrizki.nontonyuk.core.data.source.remote.response.TvItem
import com.mohamadrizki.nontonyuk.core.domain.model.Movie
import com.mohamadrizki.nontonyuk.core.domain.model.TvShow
import com.mohamadrizki.nontonyuk.core.domain.repository.IMovieTvRepository
import com.mohamadrizki.nontonyuk.core.utils.AppExecutors
import com.mohamadrizki.nontonyuk.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieTvRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMovieTvRepository {

    override fun getAllMovies(): Flow<Resource<List<Movie>>> =
        object : NetworkBoundResource<List<Movie>, List<MovieItem>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovies().map { DataMapper.mapMovieEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<MovieItem>>> =
                remoteDataSource.getAllMovie()

            override suspend fun saveCallResult(data: List<MovieItem>) {
                val movieList = DataMapper.mapMovieResponsesToEntities(data)
                localDataSource.insertMovies(movieList)
            }
        }.asFlow()

    override fun getAllTvs(): Flow<Resource<List<TvShow>>> =
        object : NetworkBoundResource<List<TvShow>, List<TvItem>>() {
            override fun loadFromDB(): Flow<List<TvShow>> {
                return localDataSource.getAllTvShows().map {
                    DataMapper.mapTvShowEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<TvShow>?): Boolean =
               true

            override suspend fun createCall(): Flow<ApiResponse<List<TvItem>>> =
                remoteDataSource.getAllTv()

            override suspend fun saveCallResult(data: List<TvItem>) {
                val tvList = DataMapper.mapTvShowResponsesToEntities(data)
                localDataSource.insertTvShows(tvList)
            }
        }.asFlow()

    override fun getMovie(id: Int): Flow<Movie> =
        localDataSource.getMovie(id).map {
            DataMapper.mapMovieEntityToDomain(it)
        }

    override fun getTv(id: Int): Flow<TvShow> =
        localDataSource.getTvShow(id).map {
            DataMapper.mapTvShowEntityToDomain(it)
        }

    override fun getMovieFavorite(): Flow<List<Movie>> {
        return localDataSource.getFavoriteMovies().map { DataMapper.mapMovieEntitiesToDomain(it) }
    }

    override fun getTvShowFavorite(): Flow<List<TvShow>> {
        return localDataSource.getFavoriteTvShows().map { DataMapper.mapTvShowEntitiesToDomain(it) }
    }

    override fun setMovieFavorite(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToMovieEntity(movie)
        appExecutors.diskIO().execute { localDataSource.setMovieFavorite(movieEntity, state) }
    }

    override fun setTvShowFavorite(tvShow: TvShow, state: Boolean) {
        val tvShowEntity = DataMapper.mapDomainToTvShowEntity(tvShow)
        appExecutors.diskIO().execute { localDataSource.setTvShowFavorite(tvShowEntity, state) }
    }
}