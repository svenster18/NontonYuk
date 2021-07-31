package com.mohamadrizki.nontonyuk.detail

import androidx.lifecycle.*
import com.mohamadrizki.nontonyuk.core.domain.model.TvShow
import com.mohamadrizki.nontonyuk.core.domain.usecase.MovieTvUseCase

class DetailTvShowViewModel(private val movieTvUseCase: MovieTvUseCase) : ViewModel() {
    private var id = MutableLiveData<Int>()

    fun setSelectedTvShow(id: Int) {
        this.id.value = id
    }

    val tvShow: LiveData<TvShow> = Transformations.switchMap(id) { mId ->
        movieTvUseCase.getTv(mId).asLiveData()
    }

    fun setFavorite() {
        val tvShow = this.tvShow.value
        if (tvShow != null) {
            val newState = !tvShow.favorite
            movieTvUseCase.setTvShowFavorite(tvShow,newState)
        }
    }
}