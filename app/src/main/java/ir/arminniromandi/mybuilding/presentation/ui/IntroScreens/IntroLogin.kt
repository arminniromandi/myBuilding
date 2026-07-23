package ir.arminniromandi.mybuilding.presentation.ui.IntroScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ir.arminniromandi.mybuilding.presentation.customUi.IntroStepper
import ir.arminniromandi.mybuilding.presentation.viewModel.intro.IntroViewModel


@Preview
@Composable
fun IntroLogin(
) {

    var currentStep by remember {
        mutableIntStateOf(1)
    }



    Column {

        IntroStepper(
            currentStep = currentStep
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = {
                if (currentStep < 2)
                    currentStep++
            }
        ) {
            Text("بعدی")
        }

    }





}