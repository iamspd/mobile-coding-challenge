package com.example.audiopodcasts.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    imageVector: ImageVector? = null,
    @StringRes displayText: Int,
    style: TextStyle,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (imageVector != null) {
            Icon(
                imageVector = imageVector,
                contentDescription = stringResource(displayText)
            )
        }
        Text(
            text = stringResource(displayText),
            style = style,
            fontWeight = FontWeight.Bold,
        )
    }
}