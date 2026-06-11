package com.worldcup2026.app.ui.teams

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.worldcup2026.app.R
import com.worldcup2026.app.data.model.Team
import com.worldcup2026.app.databinding.ItemTeamBinding

class TeamsAdapter : ListAdapter<Team, TeamsAdapter.TeamViewHolder>(TeamDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding = ItemTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TeamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TeamViewHolder(private val binding: ItemTeamBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(team: Team) {
            binding.tvTeamName.text = team.name
            binding.tvTeamGroup.text = "Group ${team.group}"
            binding.tvConfederation.text = team.confederation

            if (!team.flag.isNullOrEmpty()) {
                Glide.with(binding.root.context)
                    .load(team.flag)
                    .placeholder(R.drawable.ic_flag_placeholder)
                    .error(R.drawable.ic_flag_placeholder)
                    .into(binding.ivFlag)
            }
        }
    }

    class TeamDiffCallback : DiffUtil.ItemCallback<Team>() {
        override fun areItemsTheSame(oldItem: Team, newItem: Team) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Team, newItem: Team) = oldItem == newItem
    }
}
