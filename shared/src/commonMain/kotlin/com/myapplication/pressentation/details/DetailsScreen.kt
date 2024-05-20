package com.myapplication.pressentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter

@Composable
fun DetailsScreen(
    name: String,
    image: String
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RenderTitle(name)
        RenderImage(image)
    }
}

@Composable
private fun RenderTitle(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.h5,
        modifier = Modifier
            .wrapContentWidth()
            .padding(top = 40.dp)
    )
}

@Composable
private fun RenderImage(image: String) {
    CompositionLocalProvider {
        Image(
            painter = rememberImagePainter(image),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 20.dp)
                .size(200.dp)
                .clip(RoundedCornerShape(10.dp))
        )
    }
}