package com.mohamadrizki.nontonyuk.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mohamadrizki.nontonyuk.core.data.MovieTvRepository
import com.mohamadrizki.nontonyuk.data.source.local.entity.MovieEntity
import com.mohamadrizki.nontonyuk.utils.DataDummy
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {

    private lateinit var viewModel: DetailMovieViewModel
    private val dummyMovie = DataDummy.generateDummyMovies()[0]
    private val id = dummyMovie.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(movieTvRepository)
        viewModel.setSelectedMovie(id)
    }


    @Test
    fun getMovie() {
        val movie = MutableLiveData<MovieEntity>()
        movie.value = dummyMovie

        `when`(movieTvRepository.getMovie(id)).thenReturn(movie)

        val observer = mock(Observer::class.java) as Observer<MovieEntity>
        viewModel.movie.observeForever(observer)
        verify(observer).onChanged(dummyMovie)
    }
}