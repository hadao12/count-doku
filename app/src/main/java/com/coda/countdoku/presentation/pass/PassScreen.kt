package com.coda.countdoku.presentation.pass

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.coda.countdoku.presentation.utils.getGradientForLevel
import com.coda.countdoku.ui.theme.CountDokuTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.LevelScreenDestination
import com.ramcosta.composedestinations.generated.destinations.PuzzleScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<RootGraph>
@Composable
fun PassScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: PassViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Pass(
        modifier = modifier,
        currentLevel = uiState.currentLevel,
        onClickToLater = {
            navigator.navigate(LevelScreenDestination())
        },
        onClickToGoNextLevel = {
            navigator.navigate(
                PuzzleScreenDestination(
                    levelSelectedToPlay = uiState.currentLevel,
                    currentTotalPuzzle = uiState.currentTotalPuzzle
                )
            )
        }
    )
}

@Composable
fun Pass(
    modifier: Modifier = Modifier,
    currentLevel: Int = 1,
    onClickToGoNextLevel: () -> Unit = {},
    onClickToLater: () -> Unit = {}
) {
    val gradientBrush = getGradientForLevel(level = currentLevel)
    Scaffold(
        modifier = modifier.background(gradientBrush),
        containerColor = Color.Transparent
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            PassRotatingShapes(modifier = Modifier.fillMaxWidth())
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
            ) {
                Column(
                    modifier = modifier.fillMaxWidth()
                        .padding(bottom = 70.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Level $currentLevel Passed!",
                        style = TextStyle(
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                        ),
                        modifier = modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "Would you like to go to \n the next level?",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFFBFFCD),
                            letterSpacing = 2.sp,
                            textAlign = TextAlign.Center,
                        ),
                    )
                }
                Column(
                    modifier = Modifier.padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = onClickToGoNextLevel,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(67.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        )
                    ) {
                        Text(
                            text = "NEXT LEVEL",
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 4.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onClickToLater,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(67.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0x19FFFFFF)
                        )
                    ) {
                        Text(
                            text = "LATER",
                            fontSize = 18.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 4.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PassRotatingShapes(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()

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

    Box(
        modifier = modifier.height(600.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.blob_shape3),
            contentDescription = "Blob Shape 3",
            modifier = Modifier
                .size(180.dp)
                .graphicsLayer(
                    rotationZ = rotation
                )
        )
        Image(
            painter = painterResource(id = R.drawable.blob_shape6),
            contentDescription = "Blob Shape 6",
            modifier = Modifier
                .size(120.dp)
                .graphicsLayer(
                    rotationZ = rotation
                )
        )
        Image(
            painter = painterResource(id = R.drawable.done_check),
            contentDescription = "Done Check",
            modifier = Modifier
                .size(80.dp)
                .graphicsLayer(
                    rotationZ = rotation
                )
        )
    }
    Image(
        painter = painterResource(id = R.drawable.blob_shape4),
        contentDescription = "Blob Shape 4",
        modifier = Modifier
            .padding(end = 120.dp, top = 700.dp)
            .graphicsLayer(
                scaleX = 3f,
                scaleY = 3f,
                rotationZ = -rotation
            )
    )
}

@Preview
@Composable
fun PassScreenPreview() {
    CountDokuTheme {
        Pass(
            currentLevel = 1
        )
    }
}

