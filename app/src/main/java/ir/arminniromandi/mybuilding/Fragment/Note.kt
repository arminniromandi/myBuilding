package ir.arminniromandi.mybuilding.Fragment

import android.annotation.SuppressLint
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.arminniromandi.mybuilding.Activity.ui.theme.back
import ir.arminniromandi.mybuilding.Activity.ui.theme.primary
import ir.arminniromandi.mybuilding.R
import ir.arminniromandi.mybuilding.dataBase.DBHandler
import ir.arminniromandi.mybuilding.dataBase.dataModel.NoteProblem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition",
    "RememberReturnType"
)
@Composable
fun Note() {

    val context = LocalContext.current

    val openDialog = remember { mutableStateOf(false) }
    val editMessage = remember { mutableStateOf("") }


    Scaffold(
        floatingActionButton = {
            MainUI(openDialog)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(back)
                .padding(horizontal = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(back)
                    .padding(vertical = 16.dp)
                    ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                // TODO: show a alert dialog

                IconButton(
                    onClick = {

                        CoroutineScope(Dispatchers.IO).launch {
                            DBHandler.getDataBase(context).NoteDao().deleteAllNotes()
                        }
                    }
                ){
                    Icon(
                        painter = painterResource(id = R.drawable.trash),
                        contentDescription = "",
                        tint = Color.White
                    )
                }

                Box(modifier = Modifier

                    .fillMaxWidth(0.9f)
                    .height(68.dp)
                    .drawBehind {
                        drawRoundRect(
                            primary,
                            cornerRadius = CornerRadius(10.dp.toPx())
                        )
                    },
                    contentAlignment = Alignment.CenterEnd
                ) {

                    Text(
                        modifier = Modifier.padding(end = 8.dp),
                        text = "جدول مدیریت مشکلات",
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

            ShowNote()

        }
    }




    if (openDialog.value) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = contentColorFor(MaterialTheme.colors.background)
                        .copy(alpha = 0.6f)
                )
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        openDialog.value = false
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            CustomDialog( openDialog, editMessage)
        }
    }


}


@Composable
fun ShowNote() {
    val context = LocalContext.current
    val notes = remember { mutableStateOf<Array<NoteProblem>>(emptyArray()) }
    LaunchedEffect(key1 = Unit) {
        DBHandler.getDataBase(context).NoteDao().getNotes().collect { fetchedNotes ->
            notes.value = fetchedNotes.toTypedArray()
        }
    }

    LazyColumn {
        items(notes.value.size) {
            Items(notes.value[it])
        }

    }
}


@Composable
fun Items(data: NoteProblem) {
    val context = LocalContext.current
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
            Checkbox(checked = data.Checked,
                onCheckedChange = {
                    data.Checked = it
                    CoroutineScope(Dispatchers.IO).launch {
                        DBHandler.getDataBase(context).NoteDao()
                            .updateNotes(
                                data.copy(Checked = it)
                            )
                    }
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = primary,
                    uncheckedColor = Color.White,
                    checkmarkColor = Color.Black

                ),
                modifier = Modifier.size(width = 26.dp , height = 24.dp)

            )

            Text(
                text = data.Note,
                color = Color.White,
                fontSize = 24.sp,
                style = TextStyle(
                    textDecoration = if (data.Checked) TextDecoration.LineThrough else TextDecoration.None
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
    openDialog: MutableState<Boolean>
) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf(
        MiniFabItems(Icons.Filled.Add, "Add"),

        )
    Column(horizontalAlignment = Alignment.End) {
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }) + expandVertically(),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }) + shrinkVertically()
        ) {
            LazyColumn(Modifier.padding(bottom = 8.dp)) {
                items(items.size) {
                    ItemUi(icon = items[it].icon, openDialog = openDialog)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        val transition = updateTransition(targetState = expanded, label = "transition")
        val rotation by transition.animateFloat(label = "rotation") {
            if (it) 315f else 0f
        }

        FloatingActionButton(
            onClick = { expanded = !expanded },
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
    openDialog: MutableState<Boolean> = mutableStateOf(false)
) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End) {

        FloatingActionButton(
            onClick = {
                openDialog.value = true
            }, modifier = Modifier.size(45.dp),
            containerColor = Color(0xFF27D300)

        ) {
            Icon(imageVector = icon, contentDescription = "")
        }
    }
}


@Composable
fun CustomDialog(
    openDialog: MutableState<Boolean>,
    editMessage: MutableState<String>
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.background)
            .padding(8.dp),
    ) {
        Column(
            modifier = Modifier

                .padding(16.dp),
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = editMessage.value,
                onValueChange = { editMessage.value = it },
                singleLine = true,
                textStyle = TextStyle(
                    textDirection = TextDirection.Rtl
                ),
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 8.dp)
                    .fillMaxWidth(),

                label = {
                    Text(
                        "یادداشت",
                        modifier = Modifier.fillMaxWidth(),
                        style = TextStyle(
                            textDirection = TextDirection.Rtl
                        )
                    )
                },
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.align(Alignment.Start)
        ) {
            Button(
                onClick = {
                    openDialog.value = false
                }
            ) {
                androidx.compose.material.Text("Cancel")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {

                        DBHandler.getDataBase(context).NoteDao().insertNote(
                            NoteProblem(
                                Note = editMessage.value,
                                Checked = false
                            )
                        )
                    }


                    openDialog.value = false
                }
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




