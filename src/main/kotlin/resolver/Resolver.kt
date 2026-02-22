package resolver

import model.Player
import model.Team
import model.Position

class Resolver(private val players: List<Player>) : IResolver {
    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency.isBlank() }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        return players
            .filter { it.position == Position.DEFENDER }
            .maxByOrNull { it.goals }
            ?.let { it.name to it.goals }
            ?: ("" to 0)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        return players
            .filter { it.nationality.equals("Germany", ignoreCase = true) }
            .maxByOrNull { it.transferCost }
            ?.position
            ?.russianName()
            ?: ""
    }

    override fun getTheRudestTeam(): Team {
        return players
            .groupBy { it.team }
            .maxByOrNull { (_, teamPlayers) ->
                teamPlayers.sumOf { it.redCards }.toDouble() / teamPlayers.size
            }?.key
            ?: Team("", "")
    }

    fun getPlayersByCountry(): Map<String, Int> {
        return players.groupingBy { it.nationality }.eachCount()
    }
}

private fun Position.russianName(): String = when(this) {
    Position.GOALKEEPER -> "Вратарь"
    Position.DEFENDER   -> "Защитник"
    Position.MIDFIELD   -> "Полузащитник"
    Position.FORWARD    -> "Нападающий"
}