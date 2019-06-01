package com.exercise.musicshuffle.domain.artist

import com.exercise.musicshuffle.data.remote.MusicApi
import com.exercise.musicshuffle.domain.Repository
import com.google.gson.Gson
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MusicRepository(
    private val musicMapper: MusicMapper = MusicMapper()
) : Repository {

    private val musicApi: MusicApi by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(MusicApi.ENDPOINT)
            .build()
            .create(MusicApi::class.java)
    }

    fun getArtist(artistId: String): Single<List<Music>> {
        return musicApi.getArtistMusics(artistId = artistId)
            .map { musicMapper.to(it.results.filter { it.trackName.isNotBlank() }) }
    }
}