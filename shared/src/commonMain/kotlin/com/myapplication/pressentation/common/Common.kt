package com.myapplication.pressentation.common

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