package com.worldcup2026.app.ui.standings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.worldcup2026.app.data.model.GroupStanding
import com.worldcup2026.app.data.model.TeamStanding
import com.worldcup2026.app.databinding.ItemGroupStandingBinding
import com.worldcup2026.app.databinding.ItemTeamStandingBinding

class StandingsAdapter : ListAdapter<GroupStanding, StandingsAdapter.GroupViewHolder>(GroupDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val binding = ItemGroupStandingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class GroupViewHolder(private val binding: ItemGroupStandingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(group: GroupStanding) {
            binding.tvGroupTitle.text = "Group ${group.group}"
            binding.rvTeams.removeAllViews()

            group.teams.forEachIndexed { index, team ->
                val teamBinding = ItemTeamStandingBinding.inflate(
                    LayoutInflater.from(binding.root.context), binding.rvTeams, false
                )
                teamBinding.tvPosition.text = "${index + 1}"
                teamBinding.tvTeamName.text = team.team
                teamBinding.tvPlayed.text = team.played.toString()
                teamBinding.tvWon.text = team.won.toString()
                teamBinding.tvDrawn.text = team.drawn.toString()
                teamBinding.tvLost.text = team.lost.toString()
                teamBinding.tvGD.text = if (team.goalDifference >= 0) "+${team.goalDifference}" else "${team.goalDifference}"
                teamBinding.tvPoints.text = team.points.toString()
                // Highlight top 2 (qualify)
                if (index < 2) {
                    teamBinding.root.setBackgroundColor(
                        binding.root.context.getColor(android.R.color.holo_green_light)
                    )
                }
                binding.rvTeams.addView(teamBinding.root)
            }
        }
    }

    class GroupDiffCallback : DiffUtil.ItemCallback<GroupStanding>() {
        override fun areItemsTheSame(oldItem: GroupStanding, newItem: GroupStanding) = oldItem.group == newItem.group
        override fun areContentsTheSame(oldItem: GroupStanding, newItem: GroupStanding) = oldItem == newItem
    }
}
