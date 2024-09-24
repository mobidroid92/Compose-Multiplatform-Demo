package com.myapplication.pressentation.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import myapplication.shared.generated.resources.Res
import myapplication.shared.generated.resources.error_msg
import myapplication.shared.generated.resources.retry
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

fun LazyListScope.loadMoreProgressRow() {
    item {
        CircularProgressIndicator(
            modifier = Modifier.fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(8.dp)
        )
    }
}

fun LazyListScope.errorAndRetryRow(
    errorMsgRes: StringResource = Res.string.error_msg,
    retryLabelRes: StringResource = Res.string.retry,
    onRetry: () -> Unit = {}
) {
    item {
        val errorMsg = stringResource(errorMsgRes)
        val retryLabel = stringResource(retryLabelRes)
        Row(
            modifier = Modifier.fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(end = 10.dp),
                text = errorMsg,
            )
            Button(
                onClick = { onRetry() }
            ) {
                Text(text = retryLabel)
            }
        }
    }
}

fun Modifier.shimmerLoadingAnimation(
    isLoading: Boolean = true,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
): Modifier {

    if (isLoading) {
        return composed {

            val shimmerColors = listOf(
                Color.White.copy(alpha = 0.3f),
                Color.White.copy(alpha = 0.5f),
                Color.White.copy(alpha = 1.0f),
                Color.White.copy(alpha = 0.5f),
                Color.White.copy(alpha = 0.3f),
            )

            val transition = rememberInfiniteTransition()

            val translateAnimation = transition.animateFloat(
                initialValue = 0f,
                targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = durationMillis,
                        easing = LinearEasing,
                    ),
                    repeatMode = RepeatMode.Restart,
                )
            )

            this.background(
                brush = Brush.linearGradient(
                    colors = shimmerColors,
                    start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                    end = Offset(x = translateAnimation.value, y = angleOfAxisY),
                ),
            )
        }
    } else {
        return this
    }
}