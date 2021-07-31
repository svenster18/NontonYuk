package com.mohamadrizki.nontonyuk.favorite.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mohamadrizki.nontonyuk.core.domain.usecase.MovieTvUseCase

class TvShowFavoriteViewModel(movieTvUseCase: MovieTvUseCase) : ViewModel() {
    val favoriteTvShow = movieTvUseCase.getTvShowFavorite().asLiveData()
}