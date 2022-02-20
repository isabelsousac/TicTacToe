package tictactoe

fun main() {
    val field = startGame()
    for (i in 1..9) {
        val player = if (i % 2 == 1) "X" else "O"
        promptPlayer(field, player)
        val winner = checkWinner(field)
        if (winner != null) {
            println("$winner wins")
            return
        }
    }
    println("Draw")
}

fun startGame(): MutableList<MutableList<String>> {
    val field = MutableList(3) { MutableList(3) { " " } }
    printField(field)
    return field
}

fun printField(field: MutableList<MutableList<String>>) {
    println(
        """
       ----------
       | ${field[0][0]} ${field[0][1]} ${field[0][2]} |
       | ${field[1][0]} ${field[1][1]} ${field[1][2]} |
       | ${field[2][0]} ${field[2][1]} ${field[2][2]} |
       ----------
    """.trimIndent()
    )
}

fun promptPlayer(field: MutableList<MutableList<String>>, player: String) {
    do {
        // ask the coordinates
        print("Enter the coordinates: ")
        val coordinates = readln().split(" ")
        var validCoordinates = true

        val x = coordinates[0].toIntOrNull()
        val y = coordinates[1].toIntOrNull()
        if (x == null || y == null) {
            // check if the coordinates are numbers
            println("You should enter numbers!")
            validCoordinates = false
        } else if (x !in 1..3 || y !in 1..3) {
            // and check if the coordinates are between 1 and 3
            println("Coordinates should be from 1 to 3!")
            validCoordinates = false
        } else if (field[x - 1][y - 1] == "X" || field[x - 1][y - 1] == "O") {
            // check if the cell is occupied
            println("This cell is occupied! Choose another one!")
            validCoordinates = false
        } else {
            // update the grid with the move
            field[x - 1][y - 1] = player

            printField(field)
        }
    } while (!validCoordinates)
}

fun checkWinner(field: MutableList<MutableList<String>>): String? {
    val xWins = playerWins(field, "X")
    val oWins = playerWins(field, "O")

    return when {
        xWins -> "X"
        oWins -> "O"
        else -> null
    }
}

private fun playerWins(field: MutableList<MutableList<String>>, player: String): Boolean {
    return field.all { it[0] == player } || // vertical first
        field.all { it[1] == player } || // vertical middle
        field.all { it[2] == player } || // vertical last
        field[0].all { it == player } || // horizontal first
        field[1].all { it == player } || // horizontal middle
        field[2].all { it == player } || // horizontal last
        field[0][2] == player && field[1][1] == player && field[2][0] == player || // diagonal right to left
        field[0][0] == player && field[1][1] == player && field[2][2] == player    // diagonal left to right
}