package com.exercise.musicshuffle.application

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.exercise.musicshuffle.domain.artist.GetArtistListUseCase
import com.exercise.musicshuffle.domain.artist.Music
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    val artistList = MutableLiveData<List<Music>>()
    private val disposables = CompositeDisposable()

    fun getArtistList(artistId: String) {
        disposables.add(
            GetArtistListUseCase
                .execute(artistId = artistId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleGetArtistListSuccess)
        )
    }

    fun destroy() {
        disposables.clear()
    }

    private fun handleGetArtistListSuccess(result: GetArtistListUseCase.Result) {
        when (result) {
            is GetArtistListUseCase.Result.Success -> {
                artistList.postValue(result.musicList)
            }
            is GetArtistListUseCase.Result.Failure -> {
                Log.e("Error", result.failure.toString())
            }
        }
    }

}