package com.mohamadrizki.nontonyuk.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.mohamadrizki.nontonyuk.core.data.source.local.LocalDataSource
import com.mohamadrizki.nontonyuk.core.data.source.remote.RemoteDataSource
import com.mohamadrizki.nontonyuk.core.utils.AppExecutors
import com.mohamadrizki.nontonyuk.data.source.local.entity.MovieEntity
import com.mohamadrizki.nontonyuk.data.source.local.entity.TvShowEntity
import com.mohamadrizki.nontonyuk.utils.DataDummy
import com.mohamadrizki.nontonyuk.utils.LiveDataTestUtil
import com.mohamadrizki.nontonyuk.utils.PagedListUtil
import com.mohamadrizki.nontonyuk.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MovieTvRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val movieTvRepository = FakeMovieTvRepository(remote, local, appExecutors)

    private val movieResponses = DataDummy.generateRemoteDummyMovies()
    private val movieId = movieResponses[0].id
    private val tvResponses = DataDummy.generateRemoteDummyTvShow()
    private val tvId = tvResponses[0].id

    @Test
    fun getAllMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        movieTvRepository.getAllMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getAllMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data.size.toLong())
    }

    @Test
    fun getAllTvs() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getAllTvShows()).thenReturn(dataSourceFactory)
        movieTvRepository.getAllTvs()

        val tvEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShow()))
        verify(local).getAllTvShows()
        assertNotNull(tvEntities.data)
        assertEquals(tvResponses.size.toLong(), tvEntities.data.size.toLong())
    }

    @Test
    fun getMovie() {
        val dummyEntity = MutableLiveData<MovieEntity>()
        dummyEntity.value = DataDummy.generateDummyMovies()[0]
        `when`(local.getMovie(movieId)).thenReturn(dummyEntity)

        val movieEntity = LiveDataTestUtil.getValue(movieTvRepository.getMovie(movieId))
        verify(local).getMovie(movieId)
        assertNotNull(movieEntity)
        assertEquals(movieResponses[0].title, movieEntity.title)
    }

    @Test
    fun getTv() {
        val dummyEntity = MutableLiveData<TvShowEntity>()
        dummyEntity.value = DataDummy.generateDummyTvShow()[0]
        `when`(local.getTvShow(tvId)).thenReturn(dummyEntity)

        val tvShowEntity = LiveDataTestUtil.getValue(movieTvRepository.getTv(tvId))
        verify(local).getTvShow(tvId)
        assertNotNull(tvShowEntity)
        assertEquals(tvResponses[0].name, tvShowEntity.title)
    }

    @Test
    fun getFavoriteMovie() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
        movieTvRepository.getMovieFavorite()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShow()))
        verify(local).getFavoriteMovies()
        assertNotNull(movieEntities)
        assertEquals(movieResponses.size.toLong(), movieEntities.data.size.toLong())
    }

    @Test
    fun getFavoriteTvShow() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getFavoriteTvShows()).thenReturn(dataSourceFactory)
        movieTvRepository.getTvShowFavorite()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShow()))
        verify(local).getFavoriteTvShows()
        assertNotNull(tvShowEntities)
        assertEquals(tvResponses.size.toLong(), tvShowEntities.data.size.toLong())
    }
}