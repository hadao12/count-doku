package com.coda.countdoku.presentation.level

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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.coda.countdoku.R
import com.coda.countdoku.models.GameLevel
import com.coda.countdoku.presentation.utils.getGradientForLevel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.PuzzleScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import timber.log.Timber

@Destination<RootGraph>
@Composable
fun LevelScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    viewModel: LevelViewModel = hiltViewModel(),
    resultPuzzle: ResultRecipient<PuzzleScreenDestination, Boolean>,
) {
    val uiState by viewModel.uiState.collectAsState()
    val pagerState = rememberPagerState(initialPage = uiState.currentLevel - 1) {
        uiState.gameLevelList.size
    }

    LaunchedEffect(uiState.currentLevel) {
        viewModel.onEvent(LevelUiAction.RefreshLevel)
        pagerState.scrollToPage(uiState.currentLevel - 1)
    }

    resultPuzzle.onNavResult { result ->
        when (result) {
            NavResult.Canceled -> {}
            is NavResult.Value -> {
                if (result.value) {
                    viewModel.onEvent(LevelUiAction.RefreshLevel)
                }
            }
        }
    }

    Level(
        modifier = modifier,
        gameLevelList = uiState.gameLevelList,
        currentLevel = uiState.currentLevel,
        pagerState = pagerState,
        onClickToPlay = { levelSelected ->
            navigator.navigate(
                PuzzleScreenDestination(
                    levelSelectedToPlay = levelSelected,
                    currentTotalPuzzle = uiState.currentTotalPuzzle
                )
            )
        },
        onClickToGoADFree = {},
    )
}

@Composable
fun Level(
    modifier: Modifier = Modifier,
    gameLevelList: List<GameLevel>,
    currentLevel: Int = 0,
    pagerState: PagerState,
    onClickToPlay: (Int) -> Unit = {},
    onClickToGoADFree: () -> Unit = {}
) {

    val currentPage = pagerState.currentPage
    val gradientBrush = getGradientForLevel(level = currentPage + 1)
    val levelSelectedToPlay = currentPage + 1

    Timber.d("LevelSelectedToPlay: $levelSelectedToPlay, CurrentPage: $currentPage")
    Scaffold(
        modifier = modifier.background(gradientBrush),
        containerColor = Color.Transparent
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LevelRotatingShapes(modifier = Modifier.fillMaxWidth())
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "level",
                    style = TextStyle(
                        fontSize = 68.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White,
                        letterSpacing = 4.sp
                    ),
                    modifier = modifier.padding(top = 96.dp)
                )

                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(horizontal = 50.dp)
                ) { index ->
                    val gameLevel = gameLevelList[index]
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "${gameLevel.level}",
                            style = TextStyle(
                                fontSize = 186.sp,
                                fontWeight = FontWeight.Thin,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }

                val isLocked = gameLevelList[currentPage].level > currentLevel

                Row(
                    modifier = modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!isLocked) {
                        Image(
                            painter = painterResource(id = R.drawable.math_icon),
                            contentDescription = "Math Icon",
                            modifier = Modifier
                                .size(45.dp)
                                .padding(end = 10.dp)
                        )
                        Text(
                            text = "12 to pass",
                            style = TextStyle(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFBFFCD),
                                letterSpacing = 4.sp
                            ),
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.lock),
                            contentDescription = "Lock Icon",
                            modifier = Modifier
                                .size(45.dp)
                                .padding(end = 10.dp)
                        )
                        Text(
                            text = "Complete Level $currentLevel",
                            style = TextStyle(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFBFFCD),
                                letterSpacing = 4.sp
                            ),
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(top = 90.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { onClickToPlay(levelSelectedToPlay) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(67.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = if (isLocked) {
                            ButtonDefaults.buttonColors(
                                containerColor = Color(0x19FFFFFF)
                            )
                        } else {
                            ButtonDefaults.buttonColors(
                                containerColor = Color.White
                            )
                        },
                        enabled = !isLocked
                    ) {
                        Text(
                            text = "PLAY",
                            fontSize = 18.sp,
                            color = if (isLocked) Color.Black.copy(alpha = 0.5f) else Color.Black,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 4.sp,
                            modifier = Modifier.graphicsLayer(alpha = if (isLocked) 0.5f else 1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onClickToGoADFree,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(67.dp),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0x19FFFFFF)
                        )
                    ) {
                        Text(
                            text = "GO AD-FREE",
                            fontSize = 18.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 4.sp
                        )
                    }

                    Text(
                        text = buildAnnotatedString {
                            append("By continuing, you agree to our\n ")
                            withStyle(
                                style = SpanStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append("Terms of Conditions")
                            }
                            append(" and acknowledge\n our ")
                            withStyle(
                                style = SpanStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append("Privacy Policy")
                            }
                            append(".")
                        },
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = modifier.padding(top = 35.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun LevelRotatingShapes(modifier: Modifier = Modifier) {
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
            painter = painterResource(id = R.drawable.blob_shape1),
            contentDescription = "Blob Shape 1",
            modifier = Modifier
                .size(150.dp)
                .graphicsLayer(
                    rotationZ = -rotation
                )
        )
        Image(
            painter = painterResource(id = R.drawable.blob_shape3),
            contentDescription = "Blob Shape 3",
            modifier = Modifier
                .size(200.dp)
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
