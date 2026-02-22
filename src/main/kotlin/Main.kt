import parser.CsvParser
import resolver.Resolver
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.data.general.DefaultPieDataset
import java.io.File

fun main() {
    val resolver = Resolver(CsvParser.getPlayers())

    println("1. Выведите количество игроков, интересы которых не представляет агенство:\n" +
            resolver.getCountWithoutAgency())

    val (name, goalsNumber) = resolver.getBestScorerDefender()
    println("2. Выведите автора наибольшего числа голов из числа защитников и их количество:\n"
        + "$name, $goalsNumber голов")

    println("3. Выведите русское название позиции самого дорогого немецкого игрока:\n"
        + resolver.getTheExpensiveGermanPlayerPosition()
    )

    val rudestTeam = resolver.getTheRudestTeam()
    println("4. Выберите команду с наибольшим средним числом красных карточек на одного игрока:\n"
        + "Название команды - ${rudestTeam.name}, город - ${rudestTeam.city}")

    val playersByCountry: Map<String, Int> = resolver.getPlayersByCountry()
    createPlayersSharePieChart(playersByCountry, "players_by_country.png")
}

fun createPlayersSharePieChart(data: Map<String, Int>, fileName: String) {
    val dataset = DefaultPieDataset<String>()
    data.forEach { (country, count) ->
        dataset.setValue(country, count.toDouble())
    }

    val chart = ChartFactory.createPieChart(
        "Доля игроков по странам",
        dataset,
        true,
        true,
        false
    )

    val width = 800
    val height = 600
    val file = File(fileName)
    ChartUtils.saveChartAsPNG(file, chart, width, height)
}