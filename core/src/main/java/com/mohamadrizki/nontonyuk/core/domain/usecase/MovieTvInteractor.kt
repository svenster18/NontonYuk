package com.mohamadrizki.nontonyuk.core.domain.usecase

import com.mohamadrizki.nontonyuk.core.domain.model.Movie
import com.mohamadrizki.nontonyuk.core.domain.model.TvShow
import com.mohamadrizki.nontonyuk.core.domain.repository.IMovieTvRepository

class MovieTvInteractor(private val movieTvRepository: IMovieTvRepository): MovieTvUseCase {
    override fun getAllMovies() = movieTvRepository.getAllMovies()

    override fun getAllTvs() = movieTvRepository.getAllTvs()

    override fun getMovie(id: Int) = movieTvRepository.getMovie(id)

    override fun getTv(id: Int) = movieTvRepository.getTv(id)

    override fun getMovieFavorite() = movieTvRepository.getMovieFavorite()

    override fun getTvShowFavorite() = movieTvRepository.getTvShowFavorite()

    override fun setMovieFavorite(movie: Movie, state: Boolean) = movieTvRepository.setMovieFavorite(movie, state)

    override fun setTvShowFavorite(tvShow: TvShow, state: Boolean) = movieTvRepository.setTvShowFavorite(tvShow, state)
}