package com.worldcup2026.app.ui.teams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worldcup2026.app.data.model.Team
import com.worldcup2026.app.data.repository.WorldCupRepository
import com.worldcup2026.app.utils.Resource
import kotlinx.coroutines.launch

class TeamsViewModel : ViewModel() {
    private val repository = WorldCupRepository()

    private val _teams = MutableLiveData<Resource<List<Team>>>()
    val teams: LiveData<Resource<List<Team>>> = _teams

    private var allTeams: List<Team> = emptyList()

    init { loadTeams() }

    fun loadTeams() {
        viewModelScope.launch {
            _teams.value = Resource.Loading
            val result = repository.getAllTeams()
            if (result is Resource.Success) allTeams = result.data
            _teams.value = result
        }
    }

    fun filterByConfederation(conf: String) {
        if (conf == "All") {
            _teams.value = Resource.Success(allTeams)
        } else {
            _teams.value = Resource.Success(allTeams.filter {
                it.confederation.contains(conf, ignoreCase = true)
            })
        }
    }

    fun search(query: String) {
        _teams.value = Resource.Success(allTeams.filter {
            it.name.contains(query, ignoreCase = true)
        })
    }
}
