package com.androidtech.ui.fragment.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.crossfade
import coil3.request.placeholder
import com.androidtech.base.R
import com.androidtech.base.databinding.ItemBookmarkBinding
import com.androidtech.domain.model.news.Article

class BookmarkAdapter (
    private val onDeleteClick: (Article) -> Unit
): RecyclerView.Adapter<BookmarkAdapter.ItemBookmarkViewHolder> () {
    private var listBookmark = listOf<Article>()

    fun setData(list: List<Article>) {
        listBookmark = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemBookmarkViewHolder {
        return ItemBookmarkViewHolder(ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(
        holder: ItemBookmarkViewHolder,
        position: Int
    ) {
        holder.bind(listBookmark[position])
    }

    override fun getItemCount(): Int {
        return listBookmark.size
    }

    inner class ItemBookmarkViewHolder(
        val binding: ItemBookmarkBinding
    ): RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnDelete.setOnClickListener {
                if(bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    onDeleteClick(listBookmark[bindingAdapterPosition])
                }
            }
        }

        fun bind(article: Article) {
            binding.tvTitle.text = article.title
            binding.imgNews.load(article.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.img_test)
            }
            binding.tvDescription.text = article.description
            binding.tvAuthor.text = article.author
            binding.tvPublishAt.text = article.publishedAt
        }
    }
}