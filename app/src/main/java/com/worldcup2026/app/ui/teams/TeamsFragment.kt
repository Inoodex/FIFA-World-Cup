package com.worldcup2026.app.ui.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.worldcup2026.app.databinding.FragmentTeamsBinding
import com.worldcup2026.app.utils.Resource

class TeamsFragment : Fragment() {

    private var _binding: FragmentTeamsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: TeamsViewModel by viewModels()
    private lateinit var adapter: TeamsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTeamsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TeamsAdapter()
        binding.rvTeams.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvTeams.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.search(newText ?: "")
                return true
            }
        })

        binding.swipeRefresh.setOnRefreshListener { viewModel.loadTeams() }

        viewModel.teams.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvTeams.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    binding.rvTeams.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                    adapter.submitList(resource.data)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    binding.tvError.text = resource.message
                    binding.tvError.visibility = View.VISIBLE
                    binding.rvTeams.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
