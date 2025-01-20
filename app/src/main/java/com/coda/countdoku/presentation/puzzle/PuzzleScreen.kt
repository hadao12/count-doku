package com.coda.countdoku.presentation.puzzle

import IconButtonComponent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coda.countdoku.R
import com.coda.countdoku.models.Level
import com.coda.countdoku.presentation.puzzle.component.CustomProgressBar
import com.coda.countdoku.presentation.utils.getGradientForLevel
import com.coda.countdoku.ui.theme.CountDokuTheme
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
    val levelData = uiState.dataLevels.firstOrNull()
    Puzzle(
        modifier = modifier,
        levelSelectedToPlay = uiState.levelSelectedToPlay,
        currentTotalPuzzle = uiState.currentTotalPuzzle,
        answeredCount = 1,
        onNavigateBack = {
            resultNavigator.navigateBack(true)
        },
        levelData = levelData,
        target = 9
    )
}

@Composable
fun Puzzle(
    modifier: Modifier = Modifier,
    levelSelectedToPlay: Int,
    currentTotalPuzzle: Int,
    answeredCount: Int,
    levelData: Level?,
    target: Int,
    onNavigateBack: () -> Unit = {}
) {
    var rotation by remember { mutableFloatStateOf(0f) }
    val gradientBrush = getGradientForLevel(level = levelSelectedToPlay)
    var numbers = remember { mutableStateListOf(2, 3, 4) }
    var selectedNumbers = remember { mutableStateOf<List<Int>>(emptyList()) }
    var selectedMath = remember { mutableStateOf<String>("") }
    var total by remember { mutableIntStateOf(0) }
    var showFinalButton by remember { mutableStateOf(false) }

    fun rollback() {
        selectedNumbers.value = emptyList()
        selectedMath.value = ""
        total = 0
        showFinalButton = false
        numbers.clear()
        numbers.addAll(listOf(2, 3, 4))
    }

    LaunchedEffect(Unit) {
        while (true) {
            rotation += 0.4f
            delay(16)
        }
    }

    fun performOperation() {
        if (selectedNumbers.value.size == 2 && selectedMath.value.isNotEmpty()) {
            val num1 = selectedNumbers.value[0]
            val num2 = selectedNumbers.value[1]
            val result = when (selectedMath.value) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                "/" -> if (num2 != 0) num1 / num2 else 0
                else -> 0
            }

            total = result
            numbers.removeAll(selectedNumbers.value)
            numbers.add(total)

            selectedNumbers.value = emptyList()
            selectedMath.value = ""

            if (numbers.size == 1) {
                if (numbers[0] == target) {
                    showFinalButton = true
                }
            }
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
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomProgressBar(
                    currentTotalPuzzles = currentTotalPuzzle,
                    answeredCount = answeredCount,
                    rotation = rotation,
                    onNavigateBack = onNavigateBack,
                    levelSelectedToPlay = levelSelectedToPlay,
                    currentTotalPuzzle = currentTotalPuzzle
                )
                Text(
                    text = "$target",
                    style = TextStyle(
                        fontSize = 120.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    ),
                    modifier = modifier.padding(top = 64.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Make 9 using:",
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFBFFCD),
                        letterSpacing = 4.sp
                    ),
                    modifier = modifier.padding(bottom = 18.dp)
                )
                Row(
                    modifier = modifier.padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    numbers.forEachIndexed { index, number ->
                        val isSelected = selectedNumbers.value.contains(number)
                        IconButtonComponent(
                            onClick = {
                                if (isSelected) {
                                    selectedNumbers.value =
                                        selectedNumbers.value.filter { it != number }
                                } else {
                                    if (selectedNumbers.value.size < 2) {
                                        selectedNumbers.value = selectedNumbers.value + number
                                    }
                                }
                            },
                            icon = number.toString(),
                            color = Color.White.copy(alpha = 0.15f),
                            modifier = modifier
                                .then(
                                    if (isSelected) {
                                        modifier.border(
                                            2.dp,
                                            Color.White,
                                            shape = RoundedCornerShape(32.dp)
                                        )
                                    } else {
                                        modifier
                                    }
                                )
                        )
                    }
                }

                Row(
                    modifier = modifier.padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    val operations = listOf("+", "-", "*", "/")
                    operations.forEach { operation ->
                        val isSelected = selectedMath.value == operation
                        IconButtonComponent(
                            onClick = {
                                if (isSelected) {
                                    selectedMath.value = ""
                                } else {
                                    selectedMath.value = operation
                                }
                            },
                            icon = when (operation) {
                                "+" -> R.drawable.add
                                "-" -> R.drawable.minus
                                "*" -> R.drawable.cross
                                "/" -> R.drawable.divided
                                else -> R.drawable.add
                            },
                            color = Color.White.copy(alpha = 0.15f),
                            modifier = modifier
                                .then(
                                    if (isSelected) {
                                        modifier.border(
                                            2.dp,
                                            Color.White,
                                            shape = RoundedCornerShape(32.dp)
                                        )
                                    } else {
                                        modifier
                                    }
                                )
                        )
                    }
                    IconButtonComponent(
                        onClick = { rollback() },
                        icon = R.drawable.refresh,
                        color = Color.White
                    )
                }

                if (selectedNumbers.value.size == 2 && selectedMath.value.isNotEmpty()) {
                    performOperation()
                }

                if (showFinalButton) {
                    Text(
                        text = "Congratulations! You reached the target.",
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Green
                        ),
                        modifier = modifier.padding(bottom = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PuzzleRotatingShape(modifier: Modifier = Modifier, rotation: Float) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.blob_shape4),
            contentDescription = "Blob Shape 4",
            modifier = Modifier
                .padding(end = 100.dp, top = 700.dp)
                .graphicsLayer(
                    scaleX = 3f,
                    scaleY = 3f,
                    rotationZ = -rotation
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ShowPuzzleScreen(
    modifier: Modifier = Modifier
) {
    CountDokuTheme {

    }
}
