package com.worldcup2026.app.ui.fixtures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.worldcup2026.app.databinding.FragmentFixturesBinding
import com.worldcup2026.app.utils.Resource

class FixturesFragment : Fragment() {

    private var _binding: FragmentFixturesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FixturesViewModel by viewModels()
    private lateinit var adapter: MatchAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFixturesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupGroupFilter()
        setupSwipeRefresh()
        observeMatches()
    }

    private fun setupRecyclerView() {
        adapter = MatchAdapter()
        binding.rvMatches.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMatches.adapter = adapter
    }

    private fun setupGroupFilter() {
        val groups = listOf("All", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L")
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, groups)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGroup.adapter = spinnerAdapter

        binding.spinnerGroup.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.filterByGroup(groups[position])
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadMatches()
        }
    }

    private fun observeMatches() {
        viewModel.matches.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvMatches.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    if (resource.data.isEmpty()) {
                        binding.tvError.text = "No matches found"
                        binding.tvError.visibility = View.VISIBLE
                        binding.rvMatches.visibility = View.GONE
                    } else {
                        binding.rvMatches.visibility = View.VISIBLE
                        binding.tvError.visibility = View.GONE
                        adapter.submitList(resource.data)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    binding.tvError.text = resource.message
                    binding.tvError.visibility = View.VISIBLE
                    binding.rvMatches.visibility = View.GONE
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
