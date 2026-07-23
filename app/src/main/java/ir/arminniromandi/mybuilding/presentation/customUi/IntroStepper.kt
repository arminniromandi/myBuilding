package ir.arminniromandi.mybuilding.presentation.customUi

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Primary = Color(0xff4B4CF7)
private val Gray = Color(0xffE7E8F3)

@Composable
fun IntroStepper(
    currentStep: Int
) {

    val steps = listOf(
        "نام ساختمان",
        "تعداد واحد",
        "واحدها"
    )


    Column(
        Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {


        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .height(2.dp)
        ) {

            val section = size.width / 3

            drawLine(
                color = if (currentStep > 0) Primary else Gray,
                start = Offset(section / 2, 0f),
                end = Offset(section + section / 2, 0f),
                strokeWidth = 5f
            )

            drawLine(
                color = if (currentStep > 1) Primary else Gray,
                start = Offset(section + section / 2, 0f),
                end = Offset(section * 2 + section / 2, 0f),
                strokeWidth = 5f
            )
        }



        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            steps.forEachIndexed { index, _ ->

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {

                    Canvas(
                        modifier = Modifier
                            .size(24.dp)
                    ) {

                        if (index < currentStep) {

                            drawCircle(
                                color = Primary
                            )

                            // تیک
                            drawLine(
                                Color.White,
                                Offset(size.width * .30f, size.height * .55f),
                                Offset(size.width * .45f, size.height * .70f),
                                strokeWidth = 6f,
                                cap = StrokeCap.Round
                            )

                            drawLine(
                                Color.White,
                                Offset(size.width * .45f, size.height * .70f),
                                Offset(size.width * .72f, size.height * .35f),
                                strokeWidth = 6f,
                                cap = StrokeCap.Round
                            )

                        } else if (index == currentStep) {

                            drawCircle(
                                color = Primary
                            )

                            drawCircle(
                                color = Color.White,
                                radius = size.minDimension / 6
                            )

                        } else {

                            drawCircle(
                                color = Gray
                            )

                        }

                    }


                }

            }

        }



    }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
        ) {

            steps.forEachIndexed { index, text ->

                Text(
                    text = text,
                    modifier = Modifier
                        .weight(1f),
                    color = if(index == currentStep)
                        Primary
                    else
                        Color(0xff8C8CA1),

                    fontSize = 12.sp,
                    fontWeight = if(index == currentStep)
                        FontWeight.Medium
                    else
                        FontWeight.Normal,

                    textAlign = TextAlign.Center
                )

            }
        }


}



}