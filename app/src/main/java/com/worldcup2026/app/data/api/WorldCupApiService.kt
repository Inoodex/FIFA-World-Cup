package com.worldcup2026.app.data.api

import com.worldcup2026.app.data.model.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WorldCupApiService {

    // ── Matches ──────────────────────────────────────────────────────────
    @GET("matches")
    suspend fun getAllMatches(): Response<MatchesResponse>

    @GET("matches")
    suspend fun getMatchesByGroup(@Query("group") group: String): Response<MatchesResponse>

    @GET("matches")
    suspend fun getMatchesByRound(@Query("round") round: String): Response<MatchesResponse>

    @GET("matches/live")
    suspend fun getLiveMatches(): Response<MatchesResponse>

    @GET("matches/{id}")
    suspend fun getMatchById(@Path("id") id: Int): Response<Match>

    // ── Standings ────────────────────────────────────────────────────────
    @GET("standings")
    suspend fun getStandings(): Response<StandingsResponse>

    @GET("standings/{group}")
    suspend fun getGroupStanding(@Path("group") group: String): Response<GroupStanding>

    // ── Teams ────────────────────────────────────────────────────────────
    @GET("teams")
    suspend fun getAllTeams(): Response<TeamsResponse>

    @GET("teams/{id}")
    suspend fun getTeamById(@Path("id") id: Int): Response<Team>
}
