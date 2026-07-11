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
import androidx.compose.material.Button
import androidx.compose.material.TextField
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.NoteTable.Note as NoteEntity
import ir.arminniromandi.mybuilding.presentation.Activity.ui.theme.primary
import ir.arminniromandi.mybuilding.presentation.viewModel.home.NoteViewModel
import androidx.compose.material.MaterialTheme as MaterialTheme2
import androidx.compose.material3.MaterialTheme as MaterialTheme3



@Composable
fun NoteScreen(
    viewModel: NoteViewModel = hiltViewModel()
) {
    val notes by viewModel.notes.collectAsState()
    val isAddDialogVisible by viewModel.isAddDialogVisible.collectAsState()
    val noteText by viewModel.noteText.collectAsState()
    val isFabExpanded by viewModel.isFabExpanded.collectAsState()

    val textColor = if (!isSystemInDarkTheme()) Color.Black else Color.White

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
                    onClick = viewModel::deleteAllNotes
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
fun CustomDialog(
    noteText: String,
    onTextChanged: (String) -> Unit,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme2.colors.background)
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = noteText,
                onValueChange = onTextChanged,
                singleLine = true,
                textStyle = TextStyle(
                    textDirection = TextDirection.Rtl
                ),
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 8.dp)
                    .fillMaxWidth(),
                label = {
                    Text(
                        text = "یادداشت",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(
                            textDirection = TextDirection.Rtl
                        )
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.align(Alignment.Start)
        ) {
            Button(
                onClick = onCancelClick
            ) {
                androidx.compose.material.Text("Cancel")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = onConfirmClick,
                enabled = noteText.isNotBlank()
            ) {
                androidx.compose.material.Text("OK")
            }
        }
    }
}

data class MiniFabItems(
    val icon: ImageVector,
    val title: String
)