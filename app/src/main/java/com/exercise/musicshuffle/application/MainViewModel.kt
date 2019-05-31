package com.exercise.musicshuffle.application

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.exercise.musicshuffle.domain.artist.GetArtistListUseCase
import com.exercise.musicshuffle.domain.artist.Music
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val getArtistListUseCase: GetArtistListUseCase) : ViewModel() {

    val artistList = MutableLiveData<List<Music>>()
    private val disposables = CompositeDisposable()

    fun getArtistList(artistId: String) {
        disposables.add(
            getArtistListUseCase
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

    class MainViewModelFactory(private val getArtistListUseCase: GetArtistListUseCase = GetArtistListUseCase()) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainViewModel(getArtistListUseCase) as T
        }

    }
}