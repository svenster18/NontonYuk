package com.mohamadrizki.nontonyuk.favorite

import com.mohamadrizki.nontonyuk.favorite.movie.MovieFavoriteViewModel
import com.mohamadrizki.nontonyuk.favorite.tvshow.TvShowFavoriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { MovieFavoriteViewModel(get()) }
    viewModel { TvShowFavoriteViewModel(get()) }
}