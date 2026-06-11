package com.worldcup2026.app.data.repository

import com.worldcup2026.app.data.api.RetrofitClient
import com.worldcup2026.app.data.model.*
import com.worldcup2026.app.utils.Resource

class WorldCupRepository {

    private val api = RetrofitClient.apiService

    suspend fun getAllMatches(): Resource<List<Match>> {
        return try {
            val response = api.getAllMatches()
            if (response.isSuccessful) {
                val body = response.body()
                val matches = body?.matches ?: body?.data ?: emptyList()
                Resource.Success(matches)
            } else {
                Resource.Error("Failed to load matches: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }

    suspend fun getLiveMatches(): Resource<List<Match>> {
        return try {
            val response = api.getLiveMatches()
            if (response.isSuccessful) {
                val body = response.body()
                val matches = body?.matches ?: body?.data ?: emptyList()
                Resource.Success(matches)
            } else {
                Resource.Error("Failed: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }

    suspend fun getMatchesByGroup(group: String): Resource<List<Match>> {
        return try {
            val response = api.getMatchesByGroup(group)
            if (response.isSuccessful) {
                val body = response.body()
                Resource.Success(body?.matches ?: body?.data ?: emptyList())
            } else {
                Resource.Error("Failed: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }

    suspend fun getStandings(): Resource<List<GroupStanding>> {
        return try {
            val response = api.getStandings()
            if (response.isSuccessful) {
                val body = response.body()
                Resource.Success(body?.standings ?: body?.data ?: emptyList())
            } else {
                Resource.Error("Failed to load standings: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }

    suspend fun getAllTeams(): Resource<List<Team>> {
        return try {
            val response = api.getAllTeams()
            if (response.isSuccessful) {
                val body = response.body()
                Resource.Success(body?.teams ?: body?.data ?: emptyList())
            } else {
                Resource.Error("Failed to load teams: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }

    suspend fun getKnockoutMatches(): Resource<List<Match>> {
        return try {
            val knockoutRounds = listOf("round_of_32", "round_of_16", "quarter_final", "semi_final", "third_place", "final")
            val allMatches = mutableListOf<Match>()
            for (round in knockoutRounds) {
                val response = api.getMatchesByRound(round)
                if (response.isSuccessful) {
                    val body = response.body()
                    allMatches.addAll(body?.matches ?: body?.data ?: emptyList())
                }
            }
            Resource.Success(allMatches)
        } catch (e: Exception) {
            Resource.Error("Network error: ${e.localizedMessage}")
        }
    }
}
