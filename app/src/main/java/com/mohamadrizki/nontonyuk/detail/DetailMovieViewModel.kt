package com.mohamadrizki.nontonyuk.detail

import androidx.lifecycle.*
import com.mohamadrizki.nontonyuk.core.domain.model.Movie
import com.mohamadrizki.nontonyuk.core.domain.usecase.MovieTvUseCase

class DetailMovieViewModel(private val movieTvUseCase: MovieTvUseCase) : ViewModel() {
    private var id = MutableLiveData<Int>()

    fun setSelectedMovie(id: Int) {
        this.id.value = id
    }

    val movie: LiveData<Movie> = Transformations.switchMap(id) { mId ->
        movieTvUseCase.getMovie(mId).asLiveData()
    }

    fun setFavorite() {
        val movie = this.movie.value
        if (movie != null) {
            val newState = !movie.favorite
            movieTvUseCase.setMovieFavorite(movie,newState)
        }
    }
}