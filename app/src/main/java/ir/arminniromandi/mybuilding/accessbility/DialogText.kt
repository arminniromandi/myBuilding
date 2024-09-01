package ir.arminniromandi.mybuilding.accessbility

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun DialogText(text :String , size: Int = 15 , color: Color = Color.LightGray) {
    Text(
        text = text,
        fontSize = size.sp,
        color = color,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.End

        )
}