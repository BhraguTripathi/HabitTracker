package com.example.habittracker.ui.screens.splash

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittracker.ui.theme.GradientEnd
import com.example.habittracker.ui.theme.GradientStart
import com.habittracker.R
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



//Constants
private const val MIN_SPLASH_DURATION = 800L
private const val RING_PLUSE_DURATION = 1200
private const val RING_MIN_SCALE = 0.88f
private const val RING_MAX_SCALE = 1.00f
@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit = {}
){
    val ring1Scale = remember { Animatable(RING_MAX_SCALE)}
    val ring2Scale = remember { Animatable(RING_MAX_SCALE)}
    val ring3Scale = remember { Animatable(RING_MAX_SCALE)}

    LaunchedEffect(Unit) {

        //ring 1 animation
        launch {
            ring1Scale.animateTo(
                targetValue = RING_MIN_SCALE,
                animationSpec = infiniteRepeatable(
                    animation = tween (
                        durationMillis = RING_PLUSE_DURATION,
                        easing = FastOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }

        //ring 2 animation
        launch {
            delay(200)
            ring2Scale.animateTo(
                targetValue = RING_MIN_SCALE,
                animationSpec = infiniteRepeatable(
                    animation = tween (
                        durationMillis = RING_PLUSE_DURATION,
                        easing = FastOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }

        //ring 3 animation
        launch {
            delay(400)
            ring3Scale.animateTo(
                targetValue = RING_MIN_SCALE,
                animationSpec = infiniteRepeatable(
                    animation = tween (
                        durationMillis = RING_PLUSE_DURATION,
                        easing = FastOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }
    }

    LaunchedEffect(Unit) {
        val minTimeJob = async { delay(MIN_SPLASH_DURATION) }
        val initJob = async { performAppInitialization() }

        awaitAll(minTimeJob, initJob)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        GradientStart,
                        GradientEnd
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(
                        Float.POSITIVE_INFINITY,
                        Float.POSITIVE_INFINITY
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ){
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val center = this.center
            val strokeWidth = 1.dp.toPx()
            val ringColor = Color.White.copy(alpha = 0.08f)

            val baseRadii = listOf(
                size.minDimension * 0.28f,
                size.minDimension * 0.42f,
                size.minDimension * 0.56f
            )

            val scale = listOf(
                ring1Scale.value,
                ring2Scale.value,
                ring3Scale.value
            )

            baseRadii.forEachIndexed {
                index, baseRadii ->
                drawCircle(
                    color = ringColor,
                    radius = baseRadii * scale[index],
                    center = center,
                    style = Stroke(width = strokeWidth)
                )
            }
        }

        //Logo
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(width = 220.dp, height = 80.dp),
            contentScale = ContentScale.Fit
        )


    }

}

private suspend fun performAppInitialization() {

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}

