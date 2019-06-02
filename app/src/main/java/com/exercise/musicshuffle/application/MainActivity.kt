package com.exercise.musicshuffle.application

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.exercise.musicshuffle.R
import com.exercise.musicshuffle.domain.artist.Music
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var model: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLayout(this)
        setUpViewModel()
    }

    private fun setUpViewModel() {
        model = ViewModelProviders.of(this, MainViewModel.MainViewModelFactory()).get(MainViewModel::class.java)
        model.getArtistList("909253,1171421960,358714030,1419227,264111789")
        model.artistList.observe(this, Observer<List<Music>> { it: List<Music> ->
            updateMusicAdapter(it)
        })
    }

    private fun initLayout(activityContext: Context) {
        recyclerViewMusic.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activityContext)
            DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL).apply {
                activityContext.getDrawable(R.drawable.music_list_divider)?.let {
                    setDrawable(it)
                    addItemDecoration(this)
                }
            }
        }
    }

    private fun updateMusicAdapter(it: List<Music>) {
        recyclerViewMusic.adapter = MusicAdapter(it, Glide.with(this))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_shuffle -> {
                model.shuffleArtistList()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onPause() {
        model.destroy()
        super.onPause()
    }
}
