package com.mohamadrizki.nontonyuk.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.mohamadrizki.nontonyuk.data.source.local.LocalDataSource
import com.mohamadrizki.nontonyuk.data.source.local.entity.MovieEntity
import com.mohamadrizki.nontonyuk.data.source.local.entity.TvShowEntity
import com.mohamadrizki.nontonyuk.data.source.remote.ApiResponse
import com.mohamadrizki.nontonyuk.data.source.remote.RemoteDataSource
import com.mohamadrizki.nontonyuk.data.source.remote.response.MovieItem
import com.mohamadrizki.nontonyuk.data.source.remote.response.TvItem
import com.mohamadrizki.nontonyuk.utils.AppExecutors
import com.mohamadrizki.nontonyuk.vo.Resource

class FakeMovieTvRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors)
    : com.mohamadrizki.nontonyuk.core.data.MovieTvDataSource {

    companion object {

        private const val BASE_URL = "https://image.tmdb.org/t/p/w500"
    }

    override fun getAllMovies(): LiveData<Resource<PagedList<MovieEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieEntity>, List<MovieItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(5)
                    .setPageSize(5)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<MovieItem>>> =
                remoteDataSource.getAllMovie()

            override fun saveCallResult(movieResponses: List<MovieItem>) {
                val movieList = ArrayList<MovieEntity>()
                for (response in movieResponses) {
                    val movie = MovieEntity(
                        response.id,
                        response.title,
                        "",
                        response.releaseDate.substring(0,4).toLong(),
                        response.releaseDate,
                        "",
                        "",
                        (response.voteAverage*10).toInt(),
                        "",
                        response.overview,
                        "$BASE_URL${response.posterPath}"
                    )
                    movieList.add(movie)
                }

                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getAllTvs(): LiveData<Resource<PagedList<TvShowEntity>>> {
        return object : NetworkBoundResource<PagedList<TvShowEntity>, List<TvItem>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<TvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(5)
                    .setPageSize(5)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTvShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<TvItem>>> =
                remoteDataSource.getAllTv()

            override fun saveCallResult(tvResponses: List<TvItem>) {
                val tvList = ArrayList<TvShowEntity>()
                for (response in tvResponses) {
                    val tv = TvShowEntity(
                        response.id,
                        response.name,
                        "",
                        response.firstAirDate.substring(0,4).toLong(),
                        response.firstAirDate,
                        "",
                        "",
                        (response.voteAverage*10).toInt(),
                        "",
                        response.overview,
                        "$BASE_URL${response.posterPath}"
                    )
                    tvList.add(tv)
                }

                localDataSource.insertTvShows(tvList)
            }
        }.asLiveData()
    }

    override fun getMovie(id: Int): LiveData<MovieEntity> =
        localDataSource.getMovie(id)

    override fun getTv(id: Int): LiveData<TvShowEntity> =
        localDataSource.getTvShow(id)

    override fun getMovieFavorite(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5)
            .setPageSize(5)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }

    override fun getTvShowFavorite(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(5)
            .setPageSize(5)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTvShows(), config).build()
    }

    override fun setMovieFavorite(movie: MovieEntity, state: Boolean) =
        appExecutors.diskIO().execute { localDataSource.setMovieFavorite(movie, state) }

    override fun setTvShowFavorite(tvShow: TvShowEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.setTvShowFavorite(tvShow, state) }
    }
}