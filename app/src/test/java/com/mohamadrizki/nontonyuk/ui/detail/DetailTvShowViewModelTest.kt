package com.mohamadrizki.nontonyuk.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.mohamadrizki.nontonyuk.core.data.MovieTvRepository
import com.mohamadrizki.nontonyuk.data.source.local.entity.TvShowEntity
import com.mohamadrizki.nontonyuk.utils.DataDummy
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailTvShowViewModelTest {
    private lateinit var viewModel: DetailTvShowViewModel
    private val dummyTv = DataDummy.generateDummyTvShow()[0]
    private val id = dummyTv.id

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieTvRepository: MovieTvRepository

    @Before
    fun setUp() {
        viewModel = DetailTvShowViewModel(movieTvRepository)
        viewModel.setSelectedTvShow(id)
    }

    @Test
    fun getTvShow() {
        val tv = MutableLiveData<TvShowEntity>()
        tv.value = dummyTv

        `when`(movieTvRepository.getTv(id)).thenReturn(tv)

        val observer = Mockito.mock(Observer::class.java) as Observer<TvShowEntity>
        viewModel.tvShow.observeForever(observer)
        verify(observer).onChanged(dummyTv)
    }
}