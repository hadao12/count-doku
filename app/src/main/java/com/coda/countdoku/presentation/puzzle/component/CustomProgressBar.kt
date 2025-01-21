package com.coda.countdoku.presentation.puzzle.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coda.countdoku.R

@Composable
fun CustomProgressBar(
    modifier: Modifier = Modifier,
    currentTotalPuzzles: Int,
    answeredCount: Int,
    rotation: Float,
    onNavigateBack: () -> Unit = {},
    levelSelectedToPlay: Int,
    currentTotalPuzzle: Int
) {
    val progress = if (currentTotalPuzzles > 0) {
        answeredCount.toFloat() / currentTotalPuzzles
    } else {
        0f
    }

    val size by animateFloatAsState(
        targetValue = progress.coerceIn(0f, 1f),
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 200,
            easing = LinearOutSlowInEasing
        )
    )

    Column(
        modifier = modifier
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.blob_shape5),
                contentDescription = "Blob Shape 5",
                modifier = Modifier
                    .size(64.dp)
                    .graphicsLayer(
                        rotationZ = -rotation
                    )
            )
            Image(
                painter = painterResource(id = R.drawable.arrow),
                contentDescription = "Blob Shape 5",
                modifier = modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable(onClick = { onNavigateBack() })
            )
        }
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = buildAnnotatedString {
                    append("Level ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(levelSelectedToPlay.toString())
                    }
                },
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = "$currentTotalPuzzle to go",
                style = TextStyle(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                text = buildAnnotatedString {
                    append("Level ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append((levelSelectedToPlay + 1).toString())
                    }
                },
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(9.dp))
                    .background(Color(0xFFE0E0E0))
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(size)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(9.dp))
                    .background(Color(0xFFFFFFFF))
                    .animateContentSize()
            )
        }
    }
}
