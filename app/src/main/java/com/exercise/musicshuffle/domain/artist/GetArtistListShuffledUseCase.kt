package com.exercise.musicshuffle.domain.artist

import com.exercise.musicshuffle.domain.UseCase
import io.reactivex.Observable
import kotlin.random.Random

class GetArtistListShuffledUseCase : UseCase {

    sealed class Result {
        object Loading : Result()
        data class Success(val musicList: List<Music>) : Result()
        data class Failure(val failure: Throwable) : Result()
    }

    fun execute(musicList: MutableList<Music>): Observable<Result> {
        return Observable.just(musicList)
            .map { it.shuffleMusicList() }
            .map { Result.Success(it) as Result }
    }

    private fun MutableList<Music>.shuffleMusicList(): List<Music> {
        val shuffleList: MutableList<Music> = mutableListOf()
        val random = Random(System.currentTimeMillis())
        var index = random.nextInt(this.size)
        var getRandomMusic: Music
        shuffleList.add(this.removeAt(index))
        while (this.size > 0) {
            index = random.nextInt(this.size)
            getRandomMusic = this[index]
            if (!(shuffleList.last().artistName.equals(other = getRandomMusic.artistName, ignoreCase = true))) {
                shuffleList.add(this.removeAt(index))
            }
        }
        return shuffleList
    }
}