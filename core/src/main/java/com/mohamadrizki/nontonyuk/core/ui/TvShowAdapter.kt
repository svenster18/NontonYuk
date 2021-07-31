package com.mohamadrizki.nontonyuk.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mohamadrizki.nontonyuk.core.R
import com.mohamadrizki.nontonyuk.core.databinding.ItemsMovieTvBinding
import com.mohamadrizki.nontonyuk.core.domain.model.TvShow

class TvShowAdapter : RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {

    private var listData = ArrayList<TvShow>()
    var onItemClick: ((TvShow) -> Unit)? = null

    fun setData(newListData: List<TvShow>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder =
        TvShowViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.items_movie_tv, parent, false))

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = listData[position]
        holder.bind(tvShow)
    }

    inner class TvShowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemsMovieTvBinding.bind(itemView)
        fun bind(tvShow: TvShow) {
            with(binding) {
                tvItemTitle.text = tvShow.title
                tvItemDescription.text = tvShow.description
                Glide.with(itemView.context)
                    .load(tvShow.image)
                    .apply(RequestOptions().override(200, 200))
                    .into(imgPoster)
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }
}