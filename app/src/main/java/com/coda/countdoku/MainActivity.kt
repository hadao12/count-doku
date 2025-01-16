package com.coda.countdoku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.coda.countdoku.data.local.DatabaseHelper
import com.coda.countdoku.graph.RootNavigation
import com.coda.countdoku.ui.theme.CountDokuTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var db: DatabaseHelper? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            db = DatabaseHelper(this)
            db?.openDatabase()
            CountDokuTheme {
                Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
                    RootNavigation()
                }
            }
        }
    }
}