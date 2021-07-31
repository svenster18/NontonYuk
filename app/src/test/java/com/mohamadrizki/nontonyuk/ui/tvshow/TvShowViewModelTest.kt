package com.mohamadrizki.nontonyuk.ui.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.mohamadrizki.nontonyuk.core.data.MovieTvRepository
import com.mohamadrizki.nontonyuk.data.source.local.entity.TvShowEntity
import com.mohamadrizki.nontonyuk.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowViewModelTest {

    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvShowEntity>>>

    @Mock
    private lateinit var favoriteObserver: Observer<PagedList<TvShowEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = TvShowViewModel(movieTvRepository)
    }

    @Test
    fun getTvShows() {
        val dummyTvShow = Resource.success(pagedList)
        Mockito.`when`(dummyTvShow.data.size).thenReturn(10)
        val tvShows = MutableLiveData<Resource<PagedList<TvShowEntity>>>()
        tvShows.value = dummyTvShow

        Mockito.`when`(movieTvRepository.getAllTvs()).thenReturn(tvShows)
        val tvShowEntities = viewModel.getTvShows().value.data
        Mockito.verify(movieTvRepository).getAllTvs()
        assertNotNull(tvShowEntities)
        assertEquals(10, tvShowEntities.size)

        viewModel.getTvShows().observeForever(observer)
        verify(observer).onChanged(dummyTvShow)
    }

    @Test
    fun getFavoriteMovies() {
        val dummyTvShows = pagedList
        Mockito.`when`(dummyTvShows.size).thenReturn(10)
        val tvShows = MutableLiveData<PagedList<TvShowEntity>>()
        tvShows.value = dummyTvShows

        Mockito.`when`(movieTvRepository.getTvShowFavorite()).thenReturn(tvShows)
        val tvShowEntities = viewModel.getFavoriteTvShows().value
        Mockito.verify(movieTvRepository).getTvShowFavorite()
        assertNotNull(tvShowEntities)
        assertEquals(10, tvShowEntities.size)

        viewModel.getFavoriteTvShows().observeForever(favoriteObserver)
        Mockito.verify(favoriteObserver).onChanged(dummyTvShows)
    }
}