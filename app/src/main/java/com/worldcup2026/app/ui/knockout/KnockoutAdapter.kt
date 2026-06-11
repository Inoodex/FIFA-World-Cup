package com.worldcup2026.app.ui.knockout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.worldcup2026.app.data.model.Match
import com.worldcup2026.app.databinding.ItemKnockoutMatchBinding
import com.worldcup2026.app.utils.DateUtils

class KnockoutAdapter : ListAdapter<Match, KnockoutAdapter.KnockoutViewHolder>(KnockoutDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnockoutViewHolder {
        val binding = ItemKnockoutMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KnockoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: KnockoutViewHolder, position: Int) {
        val item = getItem(position)
        val prevItem = if (position > 0) getItem(position - 1) else null
        holder.bind(item, prevItem?.round != item.round)
    }

    inner class KnockoutViewHolder(private val binding: ItemKnockoutMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(match: Match, showHeader: Boolean) {
            if (showHeader) {
                binding.tvRoundHeader.visibility = android.view.View.VISIBLE
                binding.tvRoundHeader.text = DateUtils.formatRoundName(match.round)
            } else {
                binding.tvRoundHeader.visibility = android.view.View.GONE
            }
            binding.tvHomeTeam.text = match.homeTeam
            binding.tvAwayTeam.text = match.awayTeam
            binding.tvScore.text = match.scoreDisplay
            binding.tvMatchDate.text = DateUtils.formatMatchDate(match.kickoffUtc)
            binding.tvStadium.text = match.stadium
        }
    }

    class KnockoutDiffCallback : DiffUtil.ItemCallback<Match>() {
        override fun areItemsTheSame(oldItem: Match, newItem: Match) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Match, newItem: Match) = oldItem == newItem
    }
}
