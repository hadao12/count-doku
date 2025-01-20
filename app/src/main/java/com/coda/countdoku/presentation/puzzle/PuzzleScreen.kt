package com.coda.countdoku.presentation.puzzle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coda.countdoku.presentation.puzzle.component.CustomProgressBar
import com.coda.countdoku.presentation.utils.getGradientForLevel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import kotlinx.coroutines.delay

@Destination<RootGraph>(
    navArgs = PuzzleArgs::class
)
@Composable
fun PuzzleScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    resultNavigator: ResultBackNavigator<Boolean>,
    viewModel: PuzzleViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    Puzzle(
        modifier = modifier,
        levelSelectedToPlay = uiState.levelSelectedToPlay,
        currentTotalPuzzle = uiState.currentTotalPuzzle,
        answeredCount = 1,
        onNavigateBack = {
            resultNavigator.navigateBack(true)
        }
    )
}

@Composable
fun Puzzle(
    modifier: Modifier = Modifier,
    levelSelectedToPlay: Int,
    currentTotalPuzzle: Int,
    answeredCount: Int,
    onNavigateBack: () -> Unit = {}
) {
    var rotation by remember { mutableFloatStateOf(0f) }
    val gradientBrush = getGradientForLevel(level = levelSelectedToPlay)
    LaunchedEffect(Unit) {
        while (true) {
            rotation += 0.4f
            delay(16)
        }
    }
    Scaffold(
        modifier = modifier.background(gradientBrush),
        containerColor = Color.Transparent,
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            PuzzleRotatingShape(
                modifier = Modifier.fillMaxWidth(),
                rotation = rotation
            )
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                CustomProgressBar(
                    currentTotalPuzzles = currentTotalPuzzle,
                    answeredCount = answeredCount,
                    rotation = rotation,
                    onNavigateBack = onNavigateBack,
                    levelSelectedToPlay = levelSelectedToPlay,
                    currentTotalPuzzle = currentTotalPuzzle
                )
            }
        }
    }
}

@Composable
fun PuzzleRotatingShape(modifier: Modifier = Modifier, rotation: Float) {
    Box(
        modifier = modifier.fillMaxSize(),
    ) {

    }
}