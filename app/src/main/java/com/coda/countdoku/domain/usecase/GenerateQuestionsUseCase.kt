package com.coda.countdoku.domain.usecase

import com.coda.countdoku.models.Puzzle
import javax.inject.Inject

class GenerateQuestionsUseCase @Inject constructor() {

    operator fun invoke(level: Int, dataLevels: List<Puzzle>, totalQuestions: Int): List<Puzzle> {
        val selectedQuestions = mutableListOf<Puzzle>()

        repeat(totalQuestions) {
            val randomNumbers = generateRandomNumbers(level)

            val filteredQuestions = dataLevels.filter { puzzle ->
                puzzle.numbers.any { it in randomNumbers }
            }

            if (filteredQuestions.isNotEmpty()) {
                val randomQuestion = filteredQuestions.random()
                selectedQuestions.add(randomQuestion)
            }
        }

        return selectedQuestions
    }

    private fun generateRandomNumbers(level: Int): List<Int> {
        return when (level) {
            1 -> getRandomNumbers(XSMALL, 2)
            2 -> getRandomNumbers(SMALL, 2)
            3 -> getRandomNumbers(XSMALL, 2) + getRandomNumbers(MEDIUM, 1)
            4 -> getRandomNumbers(XSMALL, 3) + getRandomNumbers(MEDIUM, 1)
            5 -> getRandomNumbers(XSMALL, 3) + getRandomNumbers(MEDIUM, 1) + getRandomNumbers(LARGE, 1)
            6 -> getRandomNumbers(XSMALL, 3) + getRandomNumbers(MEDIUM, 1) + getRandomNumbers(LARGE, 1)
            7 -> getRandomNumbers(XSMALL, 3) + getRandomNumbers(SMALL, 1) + getRandomNumbers(MEDIUM, 1)
            8 -> getRandomNumbers(XSMALL, 4) + getRandomNumbers(SMALL, 2)
            9 -> getRandomNumbers(XSMALL, 4) + getRandomNumbers(SMALL, 1) + getRandomNumbers(LARGE, 1)
            10 -> getRandomNumbers(XSMALL, 4) + getRandomNumbers(SMALL, 1) + getRandomNumbers(MEDIUM, 1)
            11 -> getRandomNumbers(XSMALL, 4) + getRandomNumbers(SMALL, 1) + getRandomNumbers(LARGE, 1)
            12 -> getRandomNumbers(XSMALL, 5) + getRandomNumbers(SMALL, 2)
            13 -> getRandomNumbers(XSMALL, 5) + getRandomNumbers(SMALL, 2)
            14 -> getRandomNumbers(XSMALL, 5) + getRandomNumbers(SMALL, 2) + getRandomNumbers(MEDIUM, 1)
            15 -> getRandomNumbers(XSMALL, 6) + getRandomNumbers(SMALL, 2)
            16 -> getRandomNumbers(XSMALL, 6) + getRandomNumbers(SMALL, 2)
            17 -> getRandomNumbers(XSMALL, 6) + getRandomNumbers(SMALL, 2) + getRandomNumbers(MEDIUM, 2)
            18 -> getRandomNumbers(XSMALL, 6) + getRandomNumbers(SMALL, 2) + getRandomNumbers(MEDIUM, 2) + getRandomNumbers(LARGE, 1)
            19 -> getRandomNumbers(XSMALL, 6) + getRandomNumbers(SMALL, 2) + getRandomNumbers(MEDIUM, 2) + getRandomNumbers(LARGE, 1)
            20 -> getRandomNumbers(XSMALL, 6) + getRandomNumbers(SMALL, 2) + getRandomNumbers(MEDIUM, 2) + getRandomNumbers(LARGE, 1)
            21 -> getRandomNumbers(XSMALL + SMALL, 4) + getRandomNumbers(MEDIUM + LARGE + XLARGE, 2)
            else -> listOf()
        }
    }
    private fun getRandomNumbers(range: List<Int>, count: Int): List<Int> {
        return range.shuffled().take(count)
    }

    companion object {
        val XSMALL = listOf(1, 2, 3, 4, 5)
        val SMALL = listOf(5, 6, 7, 8, 9)
        val MEDIUM = listOf(10, 15, 20)
        val LARGE = listOf(25, 30, 35, 40, 45, 50)
        val XLARGE = listOf(100)
    }
}