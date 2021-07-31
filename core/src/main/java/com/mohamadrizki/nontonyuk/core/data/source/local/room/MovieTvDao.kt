package com.mohamadrizki.nontonyuk.core.data.source.local.room

import androidx.room.*
import com.mohamadrizki.nontonyuk.core.data.source.local.entity.MovieEntity
import com.mohamadrizki.nontonyuk.core.data.source.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieTvDao {

    @Query("SELECT * FROM movieentities")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tvshowentities")
    fun getTvShows(): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM movieentities where favorite = 1")
    fun getFavoriteMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM tvshowentities where favorite = 1")
    fun getFavoriteTvShow(): Flow<List<TvShowEntity>>

    @Query("SELECT * FROM movieentities where id = :id")
    fun getMovie(id: Int): Flow<MovieEntity>

    @Query("SELECT * FROM tvshowentities where id = :id")
    fun getTvShow(id: Int): Flow<TvShowEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(tvShows: List<TvShowEntity>)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)
}