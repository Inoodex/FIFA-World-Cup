package com.worldcup2026.app.ui.fixtures

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.worldcup2026.app.R
import com.worldcup2026.app.data.model.Match
import com.worldcup2026.app.databinding.ItemMatchBinding
import com.worldcup2026.app.utils.DateUtils

class MatchAdapter : ListAdapter<Match, MatchAdapter.MatchViewHolder>(MatchDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val binding = ItemMatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MatchViewHolder(private val binding: ItemMatchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(match: Match) {
            binding.tvHomeTeam.text = match.homeTeam
            binding.tvAwayTeam.text = match.awayTeam
            binding.tvScore.text = match.scoreDisplay
            binding.tvMatchDate.text = DateUtils.formatMatchDate(match.kickoffUtc)
            binding.tvMatchTime.text = DateUtils.formatMatchTime(match.kickoffUtc)
            binding.tvStadium.text = "${match.stadium}, ${match.city}"
            binding.tvRound.text = if (match.groupName != null)
                "Group ${match.groupName}" else DateUtils.formatRoundName(match.round)

            // Status badge
            when {
                match.isLive -> {
                    binding.tvStatus.text = "LIVE"
                    binding.tvStatus.setBackgroundColor(Color.RED)
                    binding.tvStatus.setTextColor(Color.WHITE)
                    binding.tvStatus.visibility = View.VISIBLE
                }
                match.isFinished -> {
                    binding.tvStatus.text = "FT"
                    binding.tvStatus.setBackgroundColor(Color.GRAY)
                    binding.tvStatus.setTextColor(Color.WHITE)
                    binding.tvStatus.visibility = View.VISIBLE
                }
                else -> {
                    binding.tvStatus.visibility = View.GONE
                }
            }

            // Highlight live matches
            if (match.isLive) {
                binding.cardMatch.setCardBackgroundColor(
                    binding.root.context.getColor(R.color.live_match_bg)
                )
            } else {
                binding.cardMatch.setCardBackgroundColor(
                    binding.root.context.getColor(R.color.card_background)
                )
            }
        }
    }

    class MatchDiffCallback : DiffUtil.ItemCallback<Match>() {
        override fun areItemsTheSame(oldItem: Match, newItem: Match) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Match, newItem: Match) = oldItem == newItem
    }
}
