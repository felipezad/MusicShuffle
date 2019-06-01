package com.exercise.musicshuffle.application

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.exercise.musicshuffle.R
import com.exercise.musicshuffle.domain.artist.Music

class MusicAdapter(private val musics: List<Music>) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.music_list_item, parent, false)
        return MusicViewHolder(view)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int = musics.size


    class MusicViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var textViewMusicName: TextView
        lateinit var textViewMusicArtist: TextView

    }
}