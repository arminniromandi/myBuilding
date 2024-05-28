package ir.arminniromandi.mybuilding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextInBox(
    text : String,
    icon :ImageVector
) {
    Box(
        modifier = Modifier
            .height(95.dp)
            .fillMaxWidth()
            .padding(top = 8.dp)
            .padding(horizontal = 8.dp)
            .drawBehind {
                drawRoundRect(
                    Brush.horizontalGradient(
                        listOf(
                            Color.Blue,
                            Color.Black
                        )
                    ),
                    cornerRadius = CornerRadius(14.dp.toPx())
                )
            },
        contentAlignment = Alignment.Center
    ) {


        Row (
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically ,
            horizontalArrangement = Arrangement.SpaceBetween
        ){


            Icon(imageVector = icon,
                contentDescription ="" ,
                modifier = Modifier.size(34.dp) ,
                tint = Color.White
                )

            Text(
                text = text,
                Modifier,
                color = Color.White,
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.yekanbakh)
                    ),
                    fontSize = 20.sp
                )
            )



        }
    }
}

//@Preview(name = "TextInBox")
//@Composable
//private fun PreviewTextInBox() {
//    TextInBox("اضافه کردن واحد")
//}