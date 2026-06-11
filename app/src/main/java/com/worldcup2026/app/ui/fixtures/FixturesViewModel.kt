package com.worldcup2026.app.ui.fixtures

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worldcup2026.app.data.model.Match
import com.worldcup2026.app.data.repository.WorldCupRepository
import com.worldcup2026.app.utils.Resource
import kotlinx.coroutines.launch

class FixturesViewModel : ViewModel() {

    private val repository = WorldCupRepository()

    private val _matches = MutableLiveData<Resource<List<Match>>>()
    val matches: LiveData<Resource<List<Match>>> = _matches

    private val _liveMatches = MutableLiveData<Resource<List<Match>>>()
    val liveMatches: LiveData<Resource<List<Match>>> = _liveMatches

    var selectedGroup: String = "All"

    init {
        loadMatches()
    }

    fun loadMatches() {
        viewModelScope.launch {
            _matches.value = Resource.Loading
            val result = if (selectedGroup == "All") {
                repository.getAllMatches()
            } else {
                repository.getMatchesByGroup(selectedGroup)
            }
            _matches.value = result
        }
    }

    fun loadLiveMatches() {
        viewModelScope.launch {
            _liveMatches.value = Resource.Loading
            _liveMatches.value = repository.getLiveMatches()
        }
    }

    fun filterByGroup(group: String) {
        selectedGroup = group
        loadMatches()
    }
}
