package com.coda.countdoku.presentation.puzzle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.coda.countdoku.presentation.utils.getGradientForLevel

@Composable
fun PuzzleScreen(
    modifier : Modifier = Modifier,
    navController: NavController,
    viewModel: PuzzleViewModel = viewModel(),
){
    val level1List by viewModel.lever1List.collectAsState()
    val gradientBrush = getGradientForLevel(level = 1)
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(gradientBrush),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        level1List.forEach { level1 ->
            Text(
                text = "Hint: ${level1.hint}, Target: ${level1.target}",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}