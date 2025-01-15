package com.coda.countdoku.graph

sealed class Screen(val route: String) {
    object SPLASH: Screen("splash_screen")
    object HOME: Screen("home_screen")
}