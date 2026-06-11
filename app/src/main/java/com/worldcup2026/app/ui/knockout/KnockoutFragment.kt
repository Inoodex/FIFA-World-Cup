package com.worldcup2026.app.ui.knockout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.worldcup2026.app.databinding.FragmentKnockoutBinding
import com.worldcup2026.app.utils.Resource

class KnockoutFragment : Fragment() {

    private var _binding: FragmentKnockoutBinding? = null
    private val binding get() = _binding!!
    private val viewModel: KnockoutViewModel by viewModels()
    private lateinit var adapter: KnockoutAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentKnockoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = KnockoutAdapter()
        binding.rvKnockout.layoutManager = LinearLayoutManager(requireContext())
        binding.rvKnockout.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener { viewModel.loadKnockout() }

        viewModel.knockoutMatches.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvKnockout.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    if (resource.data.isEmpty()) {
                        binding.tvError.text = "Knockout stage not started yet"
                        binding.tvError.visibility = View.VISIBLE
                        binding.rvKnockout.visibility = View.GONE
                    } else {
                        binding.rvKnockout.visibility = View.VISIBLE
                        binding.tvError.visibility = View.GONE
                        adapter.submitList(resource.data)
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.swipeRefresh.isRefreshing = false
                    binding.tvError.text = resource.message
                    binding.tvError.visibility = View.VISIBLE
                    binding.rvKnockout.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
