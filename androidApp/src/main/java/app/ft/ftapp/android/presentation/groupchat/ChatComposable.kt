package app.ft.ftapp.android.presentation.groupchat

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.work.*
import app.ft.ftapp.EMAIL
import app.ft.ftapp.R
import app.ft.ftapp.android.presentation.LoadingView
import app.ft.ftapp.android.presentation.common.PlaceHolderText
import app.ft.ftapp.android.presentation.home.ErrorView
import app.ft.ftapp.android.presentation.home.NoDataView
import app.ft.ftapp.android.presentation.models.NoRippleInteractionSource
import app.ft.ftapp.android.presentation.viewmodels.factory.ArgsViewModelFactory
import app.ft.ftapp.android.presentation.viewmodels.factory.FactoryArgs
import app.ft.ftapp.android.presentation.viewmodels.factory.setupViewModel
import app.ft.ftapp.android.ui.theme.*
import app.ft.ftapp.domain.models.ChatSenderMessage
import app.ft.ftapp.presentation.viewmodels.ChatEvent
import app.ft.ftapp.presentation.viewmodels.ChatViewModel
import app.ft.ftapp.presentation.viewmodels.ModelsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Composable method for drawing group chat screen.
 */
@Composable
fun GroupChat() {
    val scrollableRemember = rememberCoroutineScope()
    val scope = rememberCoroutineScope()
    val viewModel = setupViewModel<ChatViewModel>()

    val screenViewModel: ChatScreenViewModel = setupViewModel<ChatScreenViewModel>(
        ArgsViewModelFactory(FactoryArgs(viewModel))
    )
    val lazyState = rememberLazyListState()

    val isLoading by viewModel.isShowProgress.collectAsState()
    val chatMessages by viewModel.chatMessages.collectAsState()
    val participantsCount by viewModel.participants.collectAsState()

    val chatLoad by viewModel.chatLoadState.collectAsState()

    LaunchedEffect(Unit) {
        scrollableRemember.launch {
            viewModel.chatMessages.collectLatest {
                lazyState.animateScrollToItem(it.size)
            }

        }
    }
    DisposableEffect(Unit) {
        val job = scope.launch {
            viewModel.chatId.collectLatest {
                if (it > 0) {
                    screenViewModel.startListening(it)
                } else {
                    screenViewModel.stopListening()
                }
            }
        }

        onDispose {
            screenViewModel.stopListening()
            job.cancel()
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = Color.White) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(45.dp)
                            .padding(end = 8.dp),
                        tint = Color.LightGray,
                        imageVector = Icons.Filled.Email,
                        contentDescription = ""
                    )

                    Column {
                        Text(
                            text = stringResource(R.string.group_chat),
                            fontWeight = FontWeight.Bold,
                            fontFamily = Montserrat,
                            fontSize = 18.sp,
                            color = Color.Black,
                        )
                        Text(
                            text = pluralStringResource(
                                id = R.plurals.plural_fellows,
                                participantsCount,
                                participantsCount
                            ),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    )
    {
        ConstraintLayout(
            modifier = Modifier
                .background(appBackground)
                .padding(it)
                .fillMaxWidth()
        ) {
            val (icon, text, pad) = createRefs()

            MessagesList(
                columnModifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(icon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(text.top)
                        end.linkTo(parent.end)
                    },
                messages = chatMessages,
                lazyState = lazyState,
                //modifier = Modifier.align(Alignment.End)
            )
            CustomEditText(Modifier.constrainAs(text) {
                start.linkTo(parent.start)
                top.linkTo(icon.bottom)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }, chatMessages, lazyState, scrollableRemember)
        }

        when(chatLoad) {
            is ModelsState.Error -> {
                ErrorView {

                }
            }
            ModelsState.Loading -> {}
            ModelsState.NoData -> {
                NoDataView()
            }
            is ModelsState.Success<*> -> {}
        }

        if (isLoading) {
            LoadingView()
        }
    }
}

/**
 * Composable message list to display list of messages.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MessagesList(
    columnModifier: Modifier = Modifier,
    modifier: Modifier = Modifier,
    messages: List<ChatSenderMessage>,
    lazyState: LazyListState
) {
    val bringIntoViewRequester = BringIntoViewRequester()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val modifierMy =
        modifier.then(Modifier.padding(bottom = 4.dp, start = screenWidth / 4, end = 8.dp))

    val modifierNo = Modifier
        .padding(bottom = 4.dp, end = screenWidth / 4, start = 8.dp)
    LazyColumn(
        modifier = columnModifier.then(
            Modifier
                .fillMaxHeight(0.92f)
                .bringIntoViewRequester(bringIntoViewRequester)
        ), verticalArrangement = Arrangement.Bottom,
        state = lazyState
    ) {
        item {
            Column(Modifier.fillMaxWidth()) {
                for (e in messages) {
                    val mod = if (e.sender == EMAIL) modifierMy.align(Alignment.End) else modifierNo
                    ChatMessageComponent(mod, e, e.sender == EMAIL)
                }
            }
        }
    }
}

/**
 * EditText for composing chat texts.
 */
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun CustomEditText(
    modifier: Modifier = Modifier,
    messages: List<ChatSenderMessage>,
    lazyState: LazyListState,
    scrollableRemember: CoroutineScope
) {
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val bringIntoViewRequester = BringIntoViewRequester()

    var message = remember { mutableStateOf("") }
    val viewModel = setupViewModel<ChatViewModel>()
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        value = message.value,
        textStyle = TextStyle.Default.copy(fontSize = 16.sp, fontFamily = Montserrat),
        onValueChange = { textChange -> message.value = textChange },
        modifier = modifier.then(Modifier
            .clip(RoundedCornerShape(15.dp))
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .background(color = editTextBackground)
            .padding(start = 8.dp)
            .navigationBarsPadding()
//            .navigationBarsWithImePadding()
            .imePadding()
            .onFocusChanged {
                coroutineScope.launch {
                    println("TAG_OF_S change ${messages.size}")
                    lazyState.animateScrollToItem(messages.size)
                }
            }
            .onFocusEvent {
                if (it.isFocused) {
                    coroutineScope.launch {
                        bringIntoViewRequester.bringIntoView()
                    }
                }
            })
//            .padding(vertical = 18.dp),
    ) { innerTextField ->
        TextFieldDefaults.TextFieldDecorationBox(
            trailingIcon = {
                Icon(
                    tint = if(message.value.trim().isNotEmpty()) cursorColor else Color.Gray,
                    imageVector = Icons.Filled.Send,
                    contentDescription = "",
                    modifier = Modifier
                        .size(35.dp)
                        .padding(end = 3.dp)
                        .clickable {
                            if (message.value
                                    .trim()
                                    .isNotEmpty()
                            ) {
                                viewModel.onEvent(ChatEvent.SendMessage(message.value))
                                message.value = ""
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
                    PlaceHolderText(helpText = stringResource(R.string.enter_message))
                }
            }
        )
    }
}