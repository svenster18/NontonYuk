package com.mohamadrizki.nontonyuk.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mohamadrizki.nontonyuk.core.domain.usecase.MovieTvUseCase

class MovieViewModel(movieTvUseCase: MovieTvUseCase) : ViewModel() {
    val movie = movieTvUseCase.getAllMovies().asLiveData()
}