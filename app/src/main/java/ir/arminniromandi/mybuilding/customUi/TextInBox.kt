package ir.arminniromandi.mybuilding.customUi

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.arminniromandi.mybuilding.R

@Composable
fun TextInBox(
    text: String,
    icon:ImageVector,
    onClick : () ->Unit
) {

    Box(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .height(95.dp)
            .fillMaxWidth()
            .padding(top = 8.dp)
            .padding(horizontal = 8.dp)
            .drawBehind {
                drawRoundRect(
                    Brush.horizontalGradient(
                        listOf(
                            Color.Black,
                            Color(0xFF001896)
                        )
                    ),
                    cornerRadius = CornerRadius(8.dp.toPx())
                )
            },
        contentAlignment = Alignment.Center
    ) {


        Row (
            modifier = Modifier
                .fillMaxWidth()
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


