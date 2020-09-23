package com.repository.views.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.repository.GlideAppImpl
import com.repository.R
import com.repository.Utils
import com.repository.databinding.ItemTrendingRepoBinding
import com.repository.extension.bindDrawableStart
import com.repository.extension.containsIgnoreCase
import com.repository.models.TrendingRepo

class TrendingAdapter constructor(val utils: Utils, var list: ArrayList<TrendingRepo>) :
    BaseSearchAdapter<TrendingRepo, TrendingAdapter.TrendingViewHolder>(list) {

    override fun initViewHolder(parent: ViewGroup, viewType: Int): TrendingViewHolder {
        val binding: ItemTrendingRepoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_trending_repo,
            parent,
            false
        )
        return TrendingViewHolder(binding)
    }

    override fun bindViewHolderData(holder: TrendingViewHolder, position: Int, data: TrendingRepo) {
        holder.bindView(data)
    }

    override fun hasSearchKey(value: TrendingRepo, filterKey: String): Boolean {
        return value.author.containsIgnoreCase(filterKey) || value.name.containsIgnoreCase(filterKey) || value.language.containsIgnoreCase(
            filterKey
        ) || value.description.containsIgnoreCase(filterKey)
    }

    inner class TrendingViewHolder(private val binding: ItemTrendingRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(trendingRepo: TrendingRepo) {
            binding.repoName.text = trendingRepo.name
            binding.repoAuthor.text = trendingRepo.author
            binding.repoDesc.text = trendingRepo.description
            binding.repoLanguage.text = trendingRepo.language
            binding.repoStarsCount.text = trendingRepo.stars.toString()
            GlideAppImpl.loadImage(
                binding.root.context,
                trendingRepo.avatar,
                binding.repoIcon,
                drawable(trendingRepo.languageColor)
            )
            binding.repoLanguage.bindDrawableStart(drawable(trendingRepo.languageColor))
        }

        private fun getColor(colorCode: String): Int {
            return Color.parseColor(colorCode)
        }

        private fun drawable(colorCode: String): Drawable? {
            return utils.getColouredDrawable(getColor(colorCode))
        }
    }
}