package com.socratesdiaz.coroutinesamples.features.apifetch.dto

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    @Expose val userId: Int,
    @Expose val id: Int,
    @Expose val title: String,
    @Expose val body: String
): Parcelable