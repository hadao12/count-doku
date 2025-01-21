package com.coda.countdoku.models

data class Puzzle(
    val unique_id: String,
    val idx: Int,
    val level: Int,
    val hint: String,
    val numbers: List<Int>,
    val target: Int
)