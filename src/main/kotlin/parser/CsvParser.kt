package parser

import model.*

object CsvParser {
    fun getPlayers(fileName: String = "fakePlayers.csv"): List<Player> {
        val inputStream = object {}.javaClass
            .getResourceAsStream("/$fileName")
            ?: throw IllegalArgumentException("Файл $fileName не найден в resources")

        return inputStream.bufferedReader().useLines { lines ->
            lines
                .drop(1)
                .filter { it.isNotBlank() }
                .map { line -> line.toPlayer() }
                .toList()
        }
    }
}

private fun String.toPlayer(): Player {
    val playerInfo = split(";")

    return Player(
        name = playerInfo[0],
        team = Team(playerInfo[1], playerInfo[2]),
        position = Position.valueOf(playerInfo[3]),
        nationality = playerInfo[4],
        agency = playerInfo[5],
        transferCost = playerInfo[6].toInt(),
        participations = playerInfo[7].toInt(),
        goals = playerInfo[8].toInt(),
        assists = playerInfo[9].toInt(),
        yellowCards = playerInfo[10].toInt(),
        redCards = playerInfo[11].toInt()
    )
}