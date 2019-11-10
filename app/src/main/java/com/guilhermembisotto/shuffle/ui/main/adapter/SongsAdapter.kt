package com.guilhermembisotto.shuffle.ui.main.adapter

import com.guilhermembisotto.data.songs.models.Song
import com.guilhermembisotto.shuffle.R
import com.guilhermembisotto.shuffle.ui.base.BaseRecyclerViewAdapter

class SongsAdapter : BaseRecyclerViewAdapter<Song>(listOf()) {

    override fun getObjForPosition(position: Int): Any = getList()[position]
    override fun getLayoutIdForPosition(position: Int): Int = R.layout.item_song
    override fun getAdapter(position: Int): BaseRecyclerViewAdapter<Song> = this
    override fun getItemCount(): Int = getList().size
}