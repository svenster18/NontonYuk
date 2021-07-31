package com.mohamadrizki.nontonyuk.di

import com.mohamadrizki.nontonyuk.core.domain.usecase.MovieTvInteractor
import com.mohamadrizki.nontonyuk.core.domain.usecase.MovieTvUseCase
import com.mohamadrizki.nontonyuk.detail.DetailMovieViewModel
import com.mohamadrizki.nontonyuk.detail.DetailTvShowViewModel
import com.mohamadrizki.nontonyuk.movie.MovieViewModel
import com.mohamadrizki.nontonyuk.tvshow.TvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieTvUseCase> { MovieTvInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvShowViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
    viewModel { DetailTvShowViewModel(get()) }
}