package com.worldcup2026.app.ui.standings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.worldcup2026.app.databinding.FragmentStandingsBinding
import com.worldcup2026.app.utils.Resource

class StandingsFragment : Fragment() {

    private var _binding: FragmentStandingsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StandingsViewModel by viewModels()
    private lateinit var adapter: StandingsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStandingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = StandingsAdapter()
        binding.rvStandings.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStandings.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener { viewModel.loadStandings() }

        viewModel.standings.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvStandings.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    binding.rvStandings.visibility = View.VISIBLE
                    binding.tvError.visibility = View.GONE
                    adapter.submitList(resource.data)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    binding.tvError.text = resource.message
                    binding.tvError.visibility = View.VISIBLE
                    binding.rvStandings.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
