package com.exercise.musicshuffle.domain.music

import com.exercise.musicshuffle.data.remote.MusicApi
import com.exercise.musicshuffle.domain.Repository
import io.reactivex.Single

class MusicRepository(
    private val musicApi: MusicApi,
    private val musicMapper: MusicMapper
) : Repository {

    fun getMusic(id: String): Single<List<Music>> {
        return musicApi.getMusic(artistId = id)
            .map { musicMapper.to(it.results) }
    }
}