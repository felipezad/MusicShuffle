package com.exercise.musicshuffle.domain.music

import com.exercise.musicshuffle.data.model.MusicResponse
import com.exercise.musicshuffle.domain.Mapper

class MusicMapper : Mapper<MusicResponse, Music> {

    override fun to(from: MusicResponse): Music {
        return from.run {
            Music(
                id = this.id,
                artistId = this.artistId,
                artistName = this.artistName,
                trackName = this.trackName,
                primaryGenre = this.primaryGenre
            )
        }
    }
}