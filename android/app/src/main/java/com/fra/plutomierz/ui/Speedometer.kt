package com.fra.plutomierz.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun Speedometer(value: Int) {
    val isPlutaLevelCritical: Boolean = value > 50 || value < -65
    var isVisible by remember { mutableStateOf(true) }

    if (isPlutaLevelCritical) {
        LaunchedEffect(Unit) {
            while (true) {
                isVisible = !isVisible
                delay(500)
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(250.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                brush = Brush.sweepGradient(
                    colors = listOf(Color.Red, Color.Red, Color.Yellow, Color.Green),
                    center = center
                ),
                startAngle = 135f,
                sweepAngle = 270f,
                useCenter = false,
                style = Stroke(width = 20f, cap = StrokeCap.Round)
            )
            val angle = 135f + ((value + 75) / 150f) * 270f

            drawLine(
                brush = SolidColor(Color.Black),
                start = center,
                end = center + Offset(
                    x = (size.minDimension / 2 * cos(Math.toRadians(angle.toDouble()))).toFloat(),
                    y = (size.minDimension / 2 * sin(Math.toRadians(angle.toDouble()))).toFloat()
                ),
                strokeWidth = 8f,
                cap = StrokeCap.Round
            )

            drawCircle(
                brush = SolidColor(Color.Black),
                radius = 10f,
                center = center
            )
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = "$value Plut",
                color = if (isPlutaLevelCritical) Color.Red else Color.Black,
                style = MaterialTheme.typography.titleLarge
            )
            if (isPlutaLevelCritical && isVisible) {
                Text(
                    text = "POZIOM KRYTYCZNY!",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}