package com.exercise.musicshuffle.domain.artist

import com.exercise.musicshuffle.domain.UseCase
import io.reactivex.Observable

class GetArtistListUseCase(private val musicRepository: MusicRepository = MusicRepository()) : UseCase {

    sealed class Result {
        object Loading : Result()
        data class Success(val musicList: List<Music>) : Result()
        data class Failure(val failure: Throwable) : Result()
    }

    fun execute(artistId: String): Observable<Result> {
        return musicRepository.getArtist(artistId = artistId)
            .toObservable()
            .map { Result.Success(it) as Result }
            .onErrorReturn { Result.Failure(it) }
            .startWith(Result.Loading)
    }
}