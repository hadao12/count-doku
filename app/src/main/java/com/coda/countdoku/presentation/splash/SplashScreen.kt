package com.coda.countdoku.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.coda.countdoku.R
import com.coda.countdoku.presentation.utils.createGradientBackground
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    val gradientBrush = createGradientBackground(
        colors = listOf(
            Color(0xFFEBFF9B),
            Color(0xFF8ED1D1),
            Color(0xFFBB79D2)
        )
    )

    Scaffold(
        modifier = modifier.background(gradientBrush),
        containerColor = Color.Transparent
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            RotatingShapes(modifier = Modifier.fillMaxSize())
            AnimatedText(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun RotatingShapes(modifier: Modifier = Modifier) {
    var rotation by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            rotation += 0.4f
            delay(16)
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.blob_shape),
            contentDescription = "Blob Shape",
            modifier = Modifier
                .size(400.dp)
                .graphicsLayer(
                    scaleX = 1.3f,
                    scaleY = 1.3f,
                    rotationZ = rotation
                )
        )
        Image(
            painter = painterResource(id = R.drawable.blob_shape1),
            contentDescription = "Blob Shape 1",
            modifier = Modifier
                .size(300.dp)
                .graphicsLayer(
                    rotationZ = -rotation
                )
        )
        Image(
            painter = painterResource(id = R.drawable.blob_shape2),
            contentDescription = "Blob Shape 2",
            modifier = Modifier
                .size(600.dp)
                .graphicsLayer(
                    scaleX = 2f,
                    scaleY = 2f,
                    rotationZ = -rotation
                )
        )
        Image(
            painter = painterResource(id = R.drawable.blob_shape3),
            contentDescription = "Blob Shape 3",
            modifier = Modifier
                .size(800.dp)
                .graphicsLayer(
                    scaleX = 2.2f,
                    scaleY = 2.2f,
                    rotationZ = rotation
                )
        )
    }
}

@Composable
fun AnimatedText(modifier: Modifier = Modifier) {
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(500)
        isVisible = true
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "COUNT",
            style = TextStyle(
                fontSize = 72.sp,
                fontWeight = FontWeight.Light,
                color = Color.White,
                letterSpacing = 4.sp
            )
        )
        Text(
            text = "DOKU",
            style = TextStyle(
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 2.sp
            )
        )
    }
}
