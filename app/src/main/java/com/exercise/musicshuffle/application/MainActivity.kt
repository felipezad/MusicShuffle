package com.exercise.musicshuffle.application

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.exercise.musicshuffle.R
import com.exercise.musicshuffle.domain.artist.Music

class MainActivity : AppCompatActivity() {
    lateinit var model: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        model = ViewModelProviders.of(this, MainViewModel.MainViewModelFactory()).get(MainViewModel::class.java)
        model.getArtistList("909253")
        model.artistList.observe(this, Observer<List<Music>> { it ->
            Log.d("lista", it.get(1).toString())
        })
    }

    override fun onDestroy() {
        model.destroy()
        super.onDestroy()
    }
}
