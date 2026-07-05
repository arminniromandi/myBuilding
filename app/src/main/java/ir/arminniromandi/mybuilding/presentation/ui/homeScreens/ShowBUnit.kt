package ir.arminniromandi.mybuilding.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.BUser
import ir.arminniromandi.mybuilding.presentation.Activity.ui.theme.backDialog
import ir.arminniromandi.mybuilding.presentation.Activity.ui.theme.customButton
import ir.arminniromandi.mybuilding.presentation.Activity.ui.theme.editTextColor
import ir.arminniromandi.mybuilding.presentation.Activity.ui.theme.editTextColor_Text
import ir.arminniromandi.mybuilding.presentation.viewModel.ShowBUnitViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit

@Composable
fun ShowBUnit(
    viewModel: ShowBUnitViewModel = hiltViewModel()
) {
    val allUsers by viewModel.allUsers.collectAsState()
    val isDialogVisible by viewModel.isDialogVisible.collectAsState()
    val selectedUnit by viewModel.selectedUnit.collectAsState()
    val selectedUnitUsers by viewModel.selectedUnitUsers.collectAsState()

    val textColor = if (!isSystemInDarkTheme()) Color.Black else Color.White

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 16.dp, horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "نمایش افراد واحد ساختمان",
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.yekanbakh)
                    ),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            )
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            thickness = 0.5.dp
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                modifier = Modifier.padding(start = 12.dp),
                tint = textColor
            )

            Text(
                text = "تعداد افراد",
                fontSize = 18.sp,
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.yekanbakh)
                    ),
                    color = textColor
                )
            )

            Text(
                text = "واحد",
                fontSize = 20.sp,
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.yekanbakh)
                    ),
                    color = textColor
                )
            )
        }

        LazyColumn(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            items(
                items = allUsers,
                key = { user -> user.U_id }
            ) { user ->
                ItemshowBUnit(
                    users = user,
                    onClick = viewModel::openEditDialog
                )
            }
        }

        if (isDialogVisible) {
            ShowDialog(
                unit = selectedUnit,
                count = selectedUnitUsers,
                onDismiss = viewModel::closeDialog,
                onIncreaseClick = viewModel::increaseUnitUsers,
                onDecreaseClick = viewModel::decreaseUnitUsers,
                onConfirmClick = viewModel::updateSelectedUnitUsers
            )
        }
    }
}

@Composable
fun ItemshowBUnit(
    users: BUser,
    onClick: (BUser) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(68.dp)
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                onClick(users)
            },
            colors = buttonColors(
                backgroundColor = customButton
            )
        ) {
            Text(
                text = "ویرایش",
                fontSize = 18.sp,
                color = Color.White,
                style = TextStyle(
                    fontFamily = FontFamily(
                        Font(R.font.yekanbakhr)
                    )
                )
            )
        }

        Text(
            text = users.U_Unit.toString(),
            fontSize = 24.sp,
            color = Color.White,
            style = TextStyle(
                fontFamily = FontFamily(
                    Font(R.font.yekanbakh)
                )
            )
        )

        Text(
            text = users.U_id.toString(),
            color = Color.White,
            fontSize = 24.sp,
            style = TextStyle(
                fontFamily = FontFamily(
                    Font(R.font.yekanbakh)
                )
            )
        )
    }
}

@Composable
internal fun ShowDialog(
    unit: Int,
    count: Int,
    onDismiss: () -> Unit,
    onIncreaseClick: () -> Unit,
    onDecreaseClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            Modifier
                .clip(shape = RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .background(backDialog),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "$unit : واحد",
                fontSize = 24.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.yekanbakh))
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onIncreaseClick,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(4.dp))
                        .background(Color.Green)
                ) {
                    Image(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }

                Text(
                    text = count.toString(),
                    fontSize = 34.sp,
                    color = editTextColor_Text,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(editTextColor)
                        .padding(horizontal = 34.dp, vertical = 4.dp)
                )

                IconButton(
                    onClick = onDecreaseClick,
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(4.dp))
                        .background(Color.Red)
                ) {
                    Image(
                        painter = painterResource(R.drawable.baseline_remove_24),
                        contentDescription = "Add"
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onConfirmClick,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    ),
                shape = RoundedCornerShape(8.dp),
                colors = buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "ویرایش",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(
                        Font(R.font.yekanbakhsb)
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}