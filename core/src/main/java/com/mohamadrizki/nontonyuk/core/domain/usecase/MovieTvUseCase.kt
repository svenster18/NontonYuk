package com.mohamadrizki.nontonyuk.core.domain.usecase

import com.mohamadrizki.nontonyuk.core.data.Resource
import com.mohamadrizki.nontonyuk.core.domain.model.Movie
import com.mohamadrizki.nontonyuk.core.domain.model.TvShow
import kotlinx.coroutines.flow.Flow

interface MovieTvUseCase {
    fun getAllMovies(): Flow<Resource<List<Movie>>>
    fun getAllTvs(): Flow<Resource<List<TvShow>>>
    fun getMovie(id: Int): Flow<Movie>
    fun getTv(id: Int): Flow<TvShow>
    fun getMovieFavorite(): Flow<List<Movie>>
    fun getTvShowFavorite(): Flow<List<TvShow>>
    fun setMovieFavorite(movie: Movie, state: Boolean)
    fun setTvShowFavorite(tvShow: TvShow, state: Boolean)

}