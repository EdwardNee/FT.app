package app.ft.ftapp.android.presentation.groupchat

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.android.presentation.common.PlaceHolderText
import app.ft.ftapp.android.ui.theme.Montserrat
import app.ft.ftapp.android.ui.theme.editTextBackground
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun GroupChat() {
    val scrollableRemember = rememberCoroutineScope()
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = Color.White) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { }) {
                        Icon(

                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }

                    Column() {
                        Text(
                            text = "Group chat",
                            fontWeight = FontWeight.Bold,
                            fontFamily = Montserrat,
                            fontSize = 18.sp,
                            color = Color.Black,
                        )
                        Text(text = "4 участника", fontSize = 12.sp)
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            var message = remember { mutableStateOf("") }
            val messages = remember {
                mutableStateListOf(
                    Mes("public", true),
                    Mes("public void main()adasdsadaa aaadjalll", false),
                    Mes("public vo", false),
                    Mes("public vo", true)

                )
            }

            val lazyState = rememberLazyListState()
            MessagesList(messages = messages, lazyState = lazyState, modifier = Modifier.align(Alignment.End))

            val interactionSource = remember { MutableInteractionSource() }

            BasicTextField(
                value = message.value,
                textStyle = TextStyle.Default.copy(fontSize = 16.sp, fontFamily = Montserrat),
                onValueChange = { message.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = editTextBackground)
                    .padding(start = 8.dp).padding(vertical = 18.dp),
            ) { innerTextField ->
                TextFieldDefaults.TextFieldDecorationBox(
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Send,
                            contentDescription = "",
                            modifier = Modifier.clickable {
                                messages.add(Mes(message.value, true))
                                scrollableRemember.launch {
                                    lazyState.animateScrollToItem(messages.size - 1)
                                }
                            }
                        )
                    },
                    value = message.value,
                    visualTransformation = VisualTransformation.None,
                    innerTextField = innerTextField,
                    singleLine = true,
                    enabled = true,
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(0.dp), // this is how you can remove the padding
                    placeholder = {
                        if (message.value.trim().isEmpty()) {
                            PlaceHolderText(helpText = "Введите сообщение")
                        }
                    }
                )
            }
        }
    }
}


data class Mes(val text: String, val myMes: Boolean)

@Composable
fun MessagesList(modifier: Modifier = Modifier, messages: SnapshotStateList<Mes>, lazyState: LazyListState) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val modifierMy = modifier.then(Modifier.padding(bottom = 4.dp, start = screenWidth / 4, end = 8.dp))

    val modifierNo = Modifier
        .padding(bottom = 4.dp, end = screenWidth / 4, start = 8.dp)
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f), verticalArrangement = Arrangement.Bottom,
        state = lazyState
    ) {
        item {
            Column() {
                for (e in messages) {
                    val mod = if (e.myMes) modifierMy else modifierNo
                    ChatMessageComponent(mod, e.text, e.myMes)
                }
            }
        }
    }
}