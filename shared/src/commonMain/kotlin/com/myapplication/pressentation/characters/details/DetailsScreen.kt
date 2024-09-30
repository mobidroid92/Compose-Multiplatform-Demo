package com.myapplication.pressentation.characters.details

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.myapplication.pressentation.characters.KEY_PREFIX_IMAGE
import com.myapplication.pressentation.characters.KEY_PREFIX_NAME

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailsScreenRoot(
    name: String,
    imageUrl: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        with(sharedTransitionScope) {
            Text(
                text = name,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "$KEY_PREFIX_NAME-$name"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .wrapContentWidth()
                    .padding(top = 40.dp)
            )

            AsyncImage(
                model = imageUrl,
                contentDescription = "Character Image",
                modifier = Modifier
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = "$KEY_PREFIX_IMAGE-$imageUrl"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .padding(top = 20.dp)
                    .size(200.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
        }

    }
}