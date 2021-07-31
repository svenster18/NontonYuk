package com.mohamadrizki.nontonyuk.ui.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.mohamadrizki.nontonyuk.core.data.MovieTvRepository
import com.mohamadrizki.nontonyuk.data.source.local.entity.MovieEntity
import com.mohamadrizki.nontonyuk.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {
    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var favoriteObserver: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        viewModel = MovieViewModel(movieTvRepository)
    }

    @Test
    fun getMovies() {
        val dummyMovies = Resource.success(pagedList)
        `when`(dummyMovies.data.size).thenReturn(10)
        val movies = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        movies.value = dummyMovies

        `when`(movieTvRepository.getAllMovies()).thenReturn(movies)
        val movieEntities = viewModel.getMovies().value.data
        verify(movieTvRepository).getAllMovies()
        assertNotNull(movieEntities)
        assertEquals(10, movieEntities.size)

        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }

    @Test
    fun getFavoriteMovies() {
        val dummyMovies = pagedList
        `when`(dummyMovies.size).thenReturn(10)
        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dummyMovies

        `when`(movieTvRepository.getMovieFavorite()).thenReturn(movies)
        val moviEntities = viewModel.getFavoriteMovies().value
        verify(movieTvRepository).getMovieFavorite()
        assertNotNull(moviEntities)
        assertEquals(10, moviEntities.size)

        viewModel.getFavoriteMovies().observeForever(favoriteObserver)
        verify(favoriteObserver).onChanged(dummyMovies)
    }

}