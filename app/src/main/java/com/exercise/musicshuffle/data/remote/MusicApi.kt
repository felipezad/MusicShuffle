package com.exercise.musicshuffle.data.remote

import com.exercise.musicshuffle.data.model.MusicResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi : Api {
    override val endpoint: String
        get() = "https://us-central1-tw-exercicio-mobile.cloudfunctions.net/"

    @GET("lookup")
    fun getMusic(@Query("id") artistId: String, @Query("limit") limit: Int = 5): List<MusicResponse>
}