package com.androidtech.ui.fragment.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import com.androidtech.base.R
import com.androidtech.base.databinding.ItemNewsBinding
import com.androidtech.domain.model.news.Article
import com.androidtech.util.Logger

class ArticlePagingAdapter(
    //private val onItemClick: (String) -> Unit,
    private val onArticleClick: (Article) -> Unit,
    private val onBookmarkClick: (Article, Int) -> Unit,
    private val isBookmarked: (Article) -> Boolean
) : PagingDataAdapter<Article, ArticlePagingAdapter.ArticleViewHolder>(DIFF) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticleViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val viewHolder = ArticleViewHolder(binding)
        /*viewHolder.itemView.setOnClickListener {
            getItem(viewHolder.bindingAdapterPosition)?.let { article ->
                onArticleClick(article)
            }
        }
        binding.imgBookmark.setOnClickListener {
            getItem(viewHolder.bindingAdapterPosition)?.let { article ->
                onBookmarkClick(article)
            }
        }*/

        return viewHolder
    }

    override fun onBindViewHolder(
        holder: ArticleViewHolder,
        position: Int
    ) {
        getItem(position)?.let { article ->
            holder.bind(article)
            /*holder.itemView.setOnClickListener {
                onItemClick(article.url)
            }*/
        }
    }

    override fun getItemCount(): Int {
        val count = super.getItemCount()
        Logger.d("Adapter itemCount = $count")
        return count
    }


    inner class ArticleViewHolder(val binding: ItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    getItem(bindingAdapterPosition)?.let { article ->
                        onArticleClick(article)
                    }
                }
            }
            binding.imgBookmark.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    getItem(bindingAdapterPosition)?.let { article ->
                        onBookmarkClick(article, bindingAdapterPosition)
                    }
                }
            }
        }

        fun bind(item: Article) {

            binding.tvTitle.text = item.title
            binding.tvAuthor.text = item.author
            binding.tvDescription.text = item.description
            binding.tvPublishAt.text = item.publishedAt

            binding.imgNews.load(item.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.img_test)
            }
            if (isBookmarked(item)) {
                binding.imgBookmark.setImageResource(R.drawable.ic_bookmarkfill)
            } else {
                binding.imgBookmark.setImageResource(R.drawable.ic_bookmark)
            }

            Logger.d("Called bind() with ${item.title}")
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.url == newItem.url

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem
        }
    }
}