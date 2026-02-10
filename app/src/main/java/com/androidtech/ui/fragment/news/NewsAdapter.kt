package com.androidtech.ui.fragment.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import com.androidtech.base.R
import com.androidtech.base.databinding.ItemNewsBinding
import com.androidtech.domain.model.news.Article
import com.androidtech.util.Logger

class NewsAdapter: RecyclerView.Adapter<NewsAdapter.ItemNewsViewHolder>() {
    private var listNews = listOf<Article>()

    fun setData(list: List<Article>) {
        listNews = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemNewsViewHolder {
        return ItemNewsViewHolder(ItemNewsBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(
        holder: ItemNewsViewHolder,
        position: Int
    ) {
        holder.bind(listNews[position])
    }

    override fun getItemCount(): Int {
        return listNews.size
    }

    inner class ItemNewsViewHolder(
        var binding: ItemNewsBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(article: Article) {
            binding.tvTitle.text = article.title
            binding.imgNews.load(article.imageUrl) {
                crossfade(true)
               placeholder(R.drawable.img_test)
            }
            binding.tvDescription.text = article.description
            Logger.d("Called bind() with ${article.title}")
        }
    }

}