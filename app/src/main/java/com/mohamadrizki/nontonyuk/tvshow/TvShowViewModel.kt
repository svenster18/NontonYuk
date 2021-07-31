package com.mohamadrizki.nontonyuk.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mohamadrizki.nontonyuk.core.domain.usecase.MovieTvUseCase

class TvShowViewModel(movieTvUseCase: MovieTvUseCase) :ViewModel() {

    val tvShow = movieTvUseCase.getAllTvs().asLiveData()

}