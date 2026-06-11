package com.worldcup2026.app.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

// ── Match ──────────────────────────────────────────────────────────────────
@Parcelize
data class Match(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("match_number") val matchNumber: Int = 0,
    @SerializedName("round") val round: String = "",
    @SerializedName("group_name") val groupName: String? = null,
    @SerializedName("home_team") val homeTeam: String = "",
    @SerializedName("away_team") val awayTeam: String = "",
    @SerializedName("home_score") val homeScore: Int? = null,
    @SerializedName("away_score") val awayScore: Int? = null,
    @SerializedName("stadium") val stadium: String = "",
    @SerializedName("city") val city: String = "",
    @SerializedName("kickoff_utc") val kickoffUtc: String = "",
    @SerializedName("status") val status: String = "scheduled", // scheduled, live, finished
    @SerializedName("home_team_flag") val homeTeamFlag: String? = null,
    @SerializedName("away_team_flag") val awayTeamFlag: String? = null
) : Parcelable {
    val isLive get() = status.lowercase() == "live" || status.lowercase() == "in_play"
    val isFinished get() = status.lowercase() == "finished" || status.lowercase() == "ft"
    val scoreDisplay get() = if (homeScore != null && awayScore != null) "$homeScore - $awayScore" else "vs"
}

// ── Group / Standing ───────────────────────────────────────────────────────
@Parcelize
data class GroupStanding(
    @SerializedName("group") val group: String = "",
    @SerializedName("teams") val teams: List<TeamStanding> = emptyList()
) : Parcelable

@Parcelize
data class TeamStanding(
    @SerializedName("team") val team: String = "",
    @SerializedName("played") val played: Int = 0,
    @SerializedName("won") val won: Int = 0,
    @SerializedName("drawn") val drawn: Int = 0,
    @SerializedName("lost") val lost: Int = 0,
    @SerializedName("goals_for") val goalsFor: Int = 0,
    @SerializedName("goals_against") val goalsAgainst: Int = 0,
    @SerializedName("goal_difference") val goalDifference: Int = 0,
    @SerializedName("points") val points: Int = 0,
    @SerializedName("flag") val flag: String? = null
) : Parcelable

// ── Team ───────────────────────────────────────────────────────────────────
@Parcelize
data class Team(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("code") val code: String = "",
    @SerializedName("group") val group: String = "",
    @SerializedName("flag") val flag: String? = null,
    @SerializedName("confederation") val confederation: String = "",
    @SerializedName("players") val players: List<Player>? = null
) : Parcelable

@Parcelize
data class Player(
    @SerializedName("name") val name: String = "",
    @SerializedName("position") val position: String = "",
    @SerializedName("number") val number: Int = 0,
    @SerializedName("club") val club: String = ""
) : Parcelable

// ── Knockout ───────────────────────────────────────────────────────────────
@Parcelize
data class KnockoutMatch(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("round") val round: String = "",
    @SerializedName("match_number") val matchNumber: Int = 0,
    @SerializedName("home_team") val homeTeam: String = "TBD",
    @SerializedName("away_team") val awayTeam: String = "TBD",
    @SerializedName("home_score") val homeScore: Int? = null,
    @SerializedName("away_score") val awayScore: Int? = null,
    @SerializedName("kickoff_utc") val kickoffUtc: String = "",
    @SerializedName("stadium") val stadium: String = "",
    @SerializedName("status") val status: String = "scheduled"
) : Parcelable

// ── API Response wrappers ──────────────────────────────────────────────────
data class MatchesResponse(
    @SerializedName("matches") val matches: List<Match> = emptyList(),
    @SerializedName("data") val data: List<Match>? = null
)

data class StandingsResponse(
    @SerializedName("standings") val standings: List<GroupStanding> = emptyList(),
    @SerializedName("data") val data: List<GroupStanding>? = null
)

data class TeamsResponse(
    @SerializedName("teams") val teams: List<Team> = emptyList(),
    @SerializedName("data") val data: List<Team>? = null
)
