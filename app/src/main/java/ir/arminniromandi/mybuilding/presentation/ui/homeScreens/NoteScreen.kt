package ir.arminniromandi.mybuilding.presentation.ui.homeScreens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.TextButton
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.presentation.Activity.ui.theme.primary
import ir.arminniromandi.mybuilding.presentation.viewModel.home.NoteViewModel
import androidx.compose.material.MaterialTheme as MaterialTheme2
import androidx.compose.material3.MaterialTheme as MaterialTheme3
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.NoteTable.Note as NoteEntity


@Composable
fun NoteScreen(
    viewModel: NoteViewModel = hiltViewModel()
) {
    val notes by viewModel.notes.collectAsState()
    val isAddDialogVisible by viewModel.isAddDialogVisible.collectAsState()
    val noteText by viewModel.noteText.collectAsState()
    val isFabExpanded by viewModel.isFabExpanded.collectAsState()

    var isDeleteDialogVisble by
            remember {
                mutableStateOf(false)
            }
    val textColor = if (!isSystemInDarkTheme()) Color.Black else Color.White



    if (isDeleteDialogVisble){
        DeleteAllNotesDialog(
          onDismiss = {isDeleteDialogVisble = false},
            onConfirmDelete = {viewModel.deleteAllNotes()}
        )
    }



    Scaffold(
        floatingActionButton = {
            MainUI(
                expanded = isFabExpanded,
                onMainFabClick = viewModel::toggleFab,
                onAddClick = viewModel::openAddDialog
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding)
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { isDeleteDialogVisble = true }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.trash),
                        contentDescription = "",
                        tint = textColor
                    )
                }

                val primaryColor = MaterialTheme3.colorScheme.primary

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(68.dp)
                        .drawBehind {
                            drawRoundRect(
                                primaryColor,
                                cornerRadius = CornerRadius(10.dp.toPx())
                            )
                        },
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        modifier = Modifier.padding(end = 12.dp),
                        text = "یادداشت ها",
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
            }

            Spacer(modifier = Modifier.height(18.dp))

            ShowNote(
                notes = notes,
                onCheckedChange = viewModel::updateChecked
            )
        }
    }

    if (isAddDialogVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = contentColorFor(MaterialTheme2.colors.background)
                        .copy(alpha = 0.6f)
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = viewModel::closeAddDialog
                ),
            contentAlignment = Alignment.Center
        ) {
            CustomDialog(
                noteText = noteText,
                onTextChanged = viewModel::onNoteTextChanged,
                onCancelClick = viewModel::closeAddDialog,
                onConfirmClick = viewModel::insertNote
            )
        }
    }



}

@Composable
fun ShowNote(
    notes: List<NoteEntity>,
    onCheckedChange: (NoteEntity, Boolean) -> Unit
) {
    LazyColumn {
        items(
            items = notes
        ) { note ->
            NoteItem(
                data = note,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

@Composable
fun NoteItem(
    data: NoteEntity,
    onCheckedChange: (NoteEntity, Boolean) -> Unit
) {
    val textColor = if (!isSystemInDarkTheme()) Color.Black else Color.White

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Checkbox(
                checked = data.Checked,
                onCheckedChange = { checked ->
                    onCheckedChange(data, checked)
                },
                modifier = Modifier.size(width = 26.dp, height = 24.dp)
            )

            Text(
                text = data.Note,
                color = textColor,
                fontSize = 24.sp,
                style = TextStyle(
                    textDecoration = if (data.Checked) {
                        TextDecoration.LineThrough
                    } else {
                        TextDecoration.None
                    }
                ),
                fontFamily = FontFamily(
                    Font(R.font.yekanbakh)
                )
            )
        }
    }
}

@Composable
fun MainUI(
    expanded: Boolean,
    onMainFabClick: () -> Unit,
    onAddClick: () -> Unit
) {
    val items = listOf(
        MiniFabItems(
            icon = Icons.Filled.Add,
            title = "Add"
        )
    )

    Column(horizontalAlignment = Alignment.End) {
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }) + expandVertically(),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }) + shrinkVertically()
        ) {
            Column(Modifier.padding(bottom = 8.dp)) {
                items.forEach { item ->
                    ItemUi(
                        icon = item.icon,
                        onClick = onAddClick
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        val transition = updateTransition(
            targetState = expanded,
            label = "transition"
        )

        val rotation by transition.animateFloat(label = "rotation") {
            if (it) 315f else 0f
        }

        FloatingActionButton(
            onClick = onMainFabClick,
            containerColor = primary,
            contentColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "",
                modifier = Modifier.rotate(rotation)
            )
        }
    }
}

@Composable
fun ItemUi(
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        SmallFloatingActionButton(
            onClick = onClick,
            modifier = Modifier.size(45.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = ""
            )
        }
    }
}


@Composable
fun DeleteAllNotesDialog(
    onDismiss: () -> Unit,
    onConfirmDelete: () -> Unit
) {

    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl
    ) {

        AlertDialog(

            onDismissRequest = {
                onDismiss()
            },

            title = {
                Text(
                    text = "حذف همه یادداشت‌ها",
                    color = MaterialTheme.colorScheme.onSurface
                )
            },

            text = {
                Text(
                    text = "آیا مطمئن هستید که می‌خواهید تمام یادداشت‌ها را حذف کنید؟ این عملیات قابل بازگشت نیست.",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },


            confirmButton = {

                androidx.compose.material3.Button (
                    onClick = {
                        onConfirmDelete()
                        onDismiss()
                    },

                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )

                ) {

                    Text("حذف")
                }
            },


            dismissButton = {

                OutlinedButton(

                    onClick = {
                        onDismiss()
                    },

                    modifier = Modifier.padding(horizontal = 8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )

                ) {

                    Text("لغو")
                }
            },


            containerColor = MaterialTheme.colorScheme.surface,

            titleContentColor = MaterialTheme.colorScheme.onSurface,

            textContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}


@Composable
fun CustomDialog(
    noteText: String,
    onTextChanged: (String) -> Unit,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit
) {

    val textColor = if (isSystemInDarkTheme()) Color.Black else Color.White

    Dialog(
        onDismissRequest = onCancelClick
    ) {
        CompositionLocalProvider(
            LocalLayoutDirection provides LayoutDirection.Rtl
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                shadowElevation = 12.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "ثبت یادداشت",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "یادداشت موردنظر خود را وارد کنید.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = noteText,
                        onValueChange = onTextChanged,
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = "متن یادداشت")
                        },
                        placeholder = {
                            Text(text = "برای مثال: پرداخت هزینه آب")
                        },
                        maxLines = 5,
                        shape = RoundedCornerShape(14.dp),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (noteText.isNotBlank()) {
                                    onConfirmClick()
                                }
                            }
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor =
                                MaterialTheme.colorScheme.primary,
                            focusedLabelColor =
                                MaterialTheme.colorScheme.primary
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = onCancelClick
                        ) {
                            Text(text = "انصراف")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = onConfirmClick,
                            enabled = noteText.isNotBlank(),
                            shape = RoundedCornerShape(12.dp),
                            contentPadding = PaddingValues(
                                horizontal = 24.dp,
                                vertical = 12.dp
                            )
                        ) {
                            Text(
                                text = "ثبت یادداشت",
                                fontWeight = FontWeight.SemiBold ,
                                color = textColor
                            )
                        }
                    }
                }
            }
        }
    }
}





data class MiniFabItems(
    val icon: ImageVector,
    val title: String
)