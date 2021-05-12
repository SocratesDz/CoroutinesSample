package com.socratesdiaz.coroutinesamples.features.apifetch.dto

import com.google.gson.annotations.Expose

data class Comment(
    @Expose val postId: Int,
    @Expose val id: Int,
    @Expose val name: String,
    @Expose val email: String,
    @Expose val body: String
)