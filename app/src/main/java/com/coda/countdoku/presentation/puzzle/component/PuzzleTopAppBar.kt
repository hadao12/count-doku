package com.coda.countdoku.presentation.puzzle.component

import androidx.compose.foundation.Image
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.coda.countdoku.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuzzleTopAppBar(
    modifier: Modifier,
    onNavigateBack: () -> Unit,
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(text = "",)
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigateBack
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    contentDescription = "Blob Shape 4",
                )
            }
        }
    )
}