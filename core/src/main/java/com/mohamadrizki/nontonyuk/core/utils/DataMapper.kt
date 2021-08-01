package com.mohamadrizki.nontonyuk.core.utils

import com.mohamadrizki.nontonyuk.core.data.source.local.entity.MovieEntity
import com.mohamadrizki.nontonyuk.core.data.source.local.entity.TvShowEntity
import com.mohamadrizki.nontonyuk.core.data.source.remote.response.MovieItem
import com.mohamadrizki.nontonyuk.core.data.source.remote.response.TvItem
import com.mohamadrizki.nontonyuk.core.domain.model.Movie
import com.mohamadrizki.nontonyuk.core.domain.model.TvShow

object DataMapper {

    fun mapMovieResponsesToEntities(input: List<MovieItem>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                id = it.id,
                title = it.title,
                "",
                year = it.releaseDate.substring(0,4).toLong(),
                date = it.releaseDate,
                "",
                "",
                userScore = (it.voteAverage*10).toInt(),
                "",
                description = it.overview,
                image = "https://image.tmdb.org/t/p/w500${it.posterPath}"
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapTvShowResponsesToEntities(input: List<TvItem>): List<TvShowEntity> {
        val tvShowList = ArrayList<TvShowEntity>()
        input.map {
            val tvShow = TvShowEntity(
                id = it.id,
                title = it.name,
                "",
                year = it.firstAirDate.substring(0,4).toLong(),
                date = it.firstAirDate,
                "",
                "",
                userScore = (it.voteAverage*10).toInt(),
                "",
                description = it.overview,
                image = "https://image.tmdb.org/t/p/w500${it.posterPath}"
            )
            tvShowList.add(tvShow)
        }
        return tvShowList
    }

    fun mapMovieEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                id = it.id,
                title = it.title,
                rating = it.rating,
                year = it.year,
                date = it.date,
                genre = it.genre,
                duration = it.duration,
                userScore = it.userScore,
                quotes = it.quotes,
                description = it.description,
                image = it.image,
                favorite = it.favorite
            )
        }

    fun mapTvShowEntitiesToDomain(input: List<TvShowEntity>): List<TvShow> =
        input.map {
            TvShow(
                id = it.id,
                title = it.title,
                rating = it.rating,
                year = it.year,
                date = it.date,
                genre = it.genre,
                duration = it.duration,
                userScore = it.userScore,
                quotes = it.quotes,
                description = it.description,
                image = it.image,
                favorite = it.favorite
            )
        }

    fun mapMovieEntityToDomain(input: MovieEntity): Movie =
        Movie(
            id = input.id,
            title = input.title,
            rating = input.rating,
            year = input.year,
            date = input.date,
            genre = input.genre,
            duration = input.duration,
            userScore = input.userScore,
            quotes = input.quotes,
            description = input.description,
            image = input.image,
            favorite = input.favorite
        )

    fun mapTvShowEntityToDomain(input: TvShowEntity): TvShow =
            TvShow(
                id = input.id,
                title = input.title,
                rating = input.rating,
                year = input.year,
                date = input.date,
                genre = input.genre,
                duration = input.duration,
                userScore = input.userScore,
                quotes = input.quotes,
                description = input.description,
                image = input.image,
                favorite = input.favorite
            )

    fun mapDomainToMovieEntity(input: Movie) = MovieEntity(
        id = input.id,
        title = input.title,
        rating = input.rating,
        year = input.year,
        date = input.date,
        genre = input.genre,
        duration = input.duration,
        userScore = input.userScore,
        quotes = input.quotes,
        description = input.description,
        image = input.image,
        favorite = input.favorite
    )

    fun mapDomainToTvShowEntity(input: TvShow) = TvShowEntity(
        id = input.id,
        title = input.title,
        rating = input.rating,
        year = input.year,
        date = input.date,
        genre = input.genre,
        duration = input.duration,
        userScore = input.userScore,
        quotes = input.quotes,
        description = input.description,
        image = input.image,
        favorite = input.favorite
    )
}