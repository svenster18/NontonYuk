package com.mohamadrizki.nontonyuk.core.data.source.local

import com.mohamadrizki.nontonyuk.core.data.source.local.entity.MovieEntity
import com.mohamadrizki.nontonyuk.core.data.source.local.entity.TvShowEntity
import com.mohamadrizki.nontonyuk.core.data.source.local.room.MovieTvDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val mMovieTvDao: MovieTvDao) {

    fun getAllMovies(): Flow<List<MovieEntity>> = mMovieTvDao.getMovies()

    fun getAllTvShows(): Flow<List<TvShowEntity>> = mMovieTvDao.getTvShows()

    fun getFavoriteMovies(): Flow<List<MovieEntity>> = mMovieTvDao.getFavoriteMovie()

    fun getFavoriteTvShows(): Flow<List<TvShowEntity>> = mMovieTvDao.getFavoriteTvShow()

    fun getMovie(id: Int): Flow<MovieEntity> = mMovieTvDao.getMovie(id)

    fun getTvShow(id: Int): Flow<TvShowEntity> = mMovieTvDao.getTvShow(id)

    suspend fun insertMovies(movies: List<MovieEntity>) = mMovieTvDao.insertMovies(movies)

    suspend fun insertTvShows(tvShows: List<TvShowEntity>) = mMovieTvDao.insertTvShows(tvShows)

    fun setMovieFavorite(movie: MovieEntity, newState: Boolean) {
        movie.favorite = newState
        mMovieTvDao.updateMovie(movie)
    }

    fun setTvShowFavorite(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.favorite = newState
        mMovieTvDao.updateTvShow(tvShow)
    }
}