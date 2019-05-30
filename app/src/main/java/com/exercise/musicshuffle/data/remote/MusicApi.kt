package com.exercise.musicshuffle.data.remote

import com.exercise.musicshuffle.data.model.BasicResponse
import com.exercise.musicshuffle.data.model.MusicResponse
import retrofit2.Response
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface MusicApi {
    companion object {
        const val ENDPOINT: String = "https://us-central1-tw-exercicio-mobile.cloudfunctions.net/"
    }

    @GET("lookup")
    fun getArtistMusics(@Query("id") artistId: String, @Query("limit") limit: Int = 5): Single<BasicResponse<MusicResponse>>
}
