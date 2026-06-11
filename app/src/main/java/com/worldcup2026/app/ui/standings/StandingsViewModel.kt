package com.worldcup2026.app.ui.standings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worldcup2026.app.data.model.GroupStanding
import com.worldcup2026.app.data.repository.WorldCupRepository
import com.worldcup2026.app.utils.Resource
import kotlinx.coroutines.launch

class StandingsViewModel : ViewModel() {
    private val repository = WorldCupRepository()

    private val _standings = MutableLiveData<Resource<List<GroupStanding>>>()
    val standings: LiveData<Resource<List<GroupStanding>>> = _standings

    init { loadStandings() }

    fun loadStandings() {
        viewModelScope.launch {
            _standings.value = Resource.Loading
            _standings.value = repository.getStandings()
        }
    }
}
