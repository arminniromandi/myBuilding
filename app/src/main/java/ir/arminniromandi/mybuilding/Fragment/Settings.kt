package ir.arminniromandi.mybuilding.Fragment

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Settings(
    modifier: Modifier = Modifier
) {
    Box(modifier) {
        Text(text = "Settings")
    }
}

@Preview(name = "Settings")
@Composable
private fun PreviewSettings() {
    Settings()
}