package com.worldcup2026.app.ui.knockout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.worldcup2026.app.data.model.Match
import com.worldcup2026.app.data.repository.WorldCupRepository
import com.worldcup2026.app.utils.Resource
import kotlinx.coroutines.launch

class KnockoutViewModel : ViewModel() {
    private val repository = WorldCupRepository()

    private val _knockoutMatches = MutableLiveData<Resource<List<Match>>>()
    val knockoutMatches: LiveData<Resource<List<Match>>> = _knockoutMatches

    init { loadKnockout() }

    fun loadKnockout() {
        viewModelScope.launch {
            _knockoutMatches.value = Resource.Loading
            _knockoutMatches.value = repository.getKnockoutMatches()
        }
    }
}
