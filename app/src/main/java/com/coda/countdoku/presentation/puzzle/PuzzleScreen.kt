package com.coda.countdoku.presentation.puzzle

import IconButtonComponent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import com.google.accompanist.flowlayout.FlowRow
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coda.countdoku.R
import com.coda.countdoku.presentation.puzzle.component.CustomProgressBar
import com.coda.countdoku.presentation.utils.getGradientForLevel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.PassScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import timber.log.Timber

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

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                PuzzleUiEvent.LevelMaxReached -> {
                    Timber.d("Đã đạt cấp độ tối đa!")
                }

                is PuzzleUiEvent.LevelUpdated -> {
                    Timber.d("Cấp độ mới: ${event.newLevel}")
                }

                PuzzleUiEvent.NavigateBack -> {
                    resultNavigator.navigateBack(true)
                }
            }
        }
    }
    val uiState by viewModel.uiState.collectAsState()
    val selectedQuestions = remember { uiState.selectedQuestions }
    val currentQuestionIndex = remember { mutableIntStateOf(0) }
    val currentPuzzle = selectedQuestions.getOrNull(currentQuestionIndex.intValue)
    var answeredCorrectly by remember { mutableIntStateOf(0) }

    Timber.d("Số câu cần trả lời: ${uiState.currentTotalPuzzle}")

    Puzzle(
        modifier = modifier,
        levelSelectedToPlay = uiState.levelSelectedToPlay,
        currentTotalPuzzle = uiState.currentTotalPuzzle,
        answeredCount = currentQuestionIndex.intValue,
        numbers = currentPuzzle?.numbers ?: emptyList(),
        target = currentPuzzle?.target ?: 0,
        onCorrectAnswer = {
            answeredCorrectly++
            Timber.d("Câu trả lời đúng: $answeredCorrectly/${uiState.currentTotalPuzzle}")
            if (currentQuestionIndex.intValue + 1 < selectedQuestions.size) {
                currentQuestionIndex.intValue++
            } else {
                viewModel.updateLevel()
                navigator.navigate(PassScreenDestination())
            }
        },
        onNavigateBack = {
            resultNavigator.navigateBack(true)
        },
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Puzzle(
    modifier: Modifier = Modifier,
    levelSelectedToPlay: Int,
    currentTotalPuzzle: Int,
    answeredCount: Int,
    numbers: List<Int>,
    target: Int,
    onCorrectAnswer: () -> Unit,
    onNavigateBack: () -> Unit = {}
) {
    Timber.d("Initial numbers: ${numbers.joinToString(", ")}")
    val gradientBrush = getGradientForLevel(level = levelSelectedToPlay)
    var currentNumbers = remember { mutableStateListOf(*numbers.toTypedArray()) }
    var selectedIndexes = remember { mutableStateListOf<Int>() }
    var selectedMath = remember { mutableStateOf<String>("") }
    var total by remember { mutableIntStateOf(0) }
    val calculations = remember { mutableStateListOf<String>() }
    val infiniteTransition = rememberInfiniteTransition()

    fun rollback() {
        selectedIndexes.clear()
        selectedMath.value = ""
        total = 0
        calculations.clear()
        currentNumbers.clear()
        currentNumbers.addAll(numbers)
        calculations.clear()
    }

    LaunchedEffect(numbers) {
        currentNumbers.clear()
        currentNumbers.addAll(numbers)
        selectedIndexes.clear()
        selectedMath.value = ""
        calculations.clear()
        total = 0
    }

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 5000,
                easing = LinearEasing
            )
        )
    )

    fun performOperation() {
        if (selectedIndexes.size == 2 && currentNumbers.size > 1 && selectedMath.value.isNotEmpty()) {
            val num1 = currentNumbers[selectedIndexes[0]]
            val num2 = currentNumbers[selectedIndexes[1]]
            val result = when (selectedMath.value) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                "/" -> if (num2 != 0) num1 / num2 else 0
                else -> 0
            }

            currentNumbers[selectedIndexes[0]] = result
            currentNumbers.removeAt(selectedIndexes[1])

            Timber.d("Initial numbers: ${numbers.joinToString(", ")}")
            Timber.d("Initial numbers: ${currentNumbers.joinToString(", ")}")
            calculations.add("$num1 ${selectedMath.value} $num2 = $result")

            if (currentNumbers.size == 1) {
                if (currentNumbers[0] == target) {
                    onCorrectAnswer()
                    rollback()
                }
            } else {
                selectedIndexes.clear()
                selectedMath.value = ""
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
                calculations.chunked(2).forEach { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        rowItems.forEach { calculation ->
                            Text(
                                text = calculation,
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFFFBFFCD)
                                ),
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                            )
                        }
                        if (rowItems.size < 2) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Make $target using:",
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFBFFCD),
                        letterSpacing = 4.sp
                    ),
                    modifier = modifier.padding(bottom = 18.dp)
                )
                FlowRow(
                    modifier = modifier.padding(bottom = 16.dp),
                    mainAxisSpacing = 16.dp,
                    crossAxisSpacing = 16.dp
                ) {
                    currentNumbers.forEachIndexed { index, number ->
                        val isSelected = selectedIndexes.contains(index)
                        IconButtonComponent(
                            onClick = {
                                if (isSelected) {
                                    selectedIndexes.remove(index)
                                } else {
                                    if (selectedIndexes.size < 2) {
                                        selectedIndexes.add(index)
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

                if (selectedIndexes.size == 2 && selectedMath.value.isNotEmpty()) {
                    performOperation()
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