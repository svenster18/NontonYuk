package com.mohamadrizki.nontonyuk.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    var id: Int,
    var title: String,
    var rating: String,
    var year: String?,
    var date: String?,
    var genre: String,
    var duration: String,
    var userScore: Int,
    var quotes: String,
    var description: String,
    var image: String,
    var favorite: Boolean = false
) : Parcelable
