package space.dector.openprinttag.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
fun Theme(content: @Composable () -> Unit) {
    MaterialTheme {
        content()
    }
}
