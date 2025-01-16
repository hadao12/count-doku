package com.coda.countdoku.models

data class Level1(
    val unique_id: String,
    val idx: Int,
    val level: Int,
    val hint: String,
    val numbers: String,
    val target: Int
)