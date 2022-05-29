package com.example.newsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.HeadlineItemBinding
import com.example.newsapp.presentation.ext.picassoLoading
import com.example.newsapp.presentation.model.HeadlinePresentation
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class HeadlinesAdapter : RecyclerView.Adapter<HeadlinesAdapter.HeadlinesViewHolder>() {
    private var headlines = listOf<HeadlinePresentation>()

    fun setDataAdapter(list: List<HeadlinePresentation>) {
        headlines = list
        notifyDataSetChanged()
    }

    var onItemClick: ((HeadlinePresentation) -> Unit) = { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlinesViewHolder {
        return HeadlinesViewHolder(
            HeadlineItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HeadlinesViewHolder, position: Int) {
        holder.bind(headlines[position])
    }

    override fun getItemCount(): Int = headlines.size

    inner class HeadlinesViewHolder(private val binding: HeadlineItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick(headlines[adapterPosition])
            }
        }

        fun bind(headline: HeadlinePresentation) {
            binding.titleHeadlineTv.text = headline.title
            binding.progressBar.visibility = View.VISIBLE
            Picasso.get()
                .picassoLoading(headline.urlImage, binding.imgHeadlineIv, binding.progressBar)
        }
    }
}


