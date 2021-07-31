package com.mohamadrizki.nontonyuk.favorite.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mohamadrizki.nontonyuk.core.domain.usecase.MovieTvUseCase

class MovieFavoriteViewModel(movieTvUseCase: MovieTvUseCase) : ViewModel() {
    val favoriteMovie = movieTvUseCase.getMovieFavorite().asLiveData()
}