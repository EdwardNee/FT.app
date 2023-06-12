package app.ft.ftapp.android.presentation.preview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.android.R
import app.ft.ftapp.android.ui.ScreenValues
import app.ft.ftapp.android.ui.theme.Montserrat
import app.ft.ftapp.android.ui.theme.chipTimeColor
import app.ft.ftapp.android.ui.theme.hseBlue
import app.ft.ftapp.android.utils.SingletonHelper
import app.ft.ftapp.di.DIFactory
import app.ft.ftapp.presentation.viewmodels.MainActivityViewModel
import app.ft.ftapp.presentation.viewmodels.ModelsState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.kodein.di.instance

@Composable
@Preview
fun PreviewComposable() {
    val kodein = DIFactory.di
    val viewModel: MainActivityViewModel by kodein.instance(tag = "mainact_vm")

    val isError = remember { mutableStateOf(false) }
    val isExpired: Boolean by viewModel.isExpired.collectAsState()
    val registerState: ModelsState by viewModel.registerState.collectAsState()

    viewModel.checkIfExpired(System.currentTimeMillis() / 1000)

    val scope = rememberCoroutineScope()
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isExpired) {
            Spacer(modifier = Modifier.weight(1f))
        }
        Text(
            "FT.app",
            fontFamily = Montserrat,
            fontWeight = FontWeight.Bold,
            fontSize = 64.sp,
            color = Color.White
        )
        Text("Поиск попутчиков", fontFamily = Montserrat, fontSize = 12.sp, color = Color.LightGray)
        LaunchedEffect(Unit) {
            scope.launch {
                viewModel.registerState.collectLatest {
                    println(it)

                    when (it) {
                        is ModelsState.Error -> {
                            isError.value = true
                        }
                        ModelsState.Loading -> {
                            isError.value = false
                        }
                        ModelsState.NoData -> {
                            isError.value = false
                        }
                        is ModelsState.Success<*> -> {
                            isError.value = false
                            viewModel.fillCredentials()
                            SingletonHelper.appNavigator.navigateTo(ScreenValues.SECOND_APP)
                        }
                    }
                }
                viewModel.isExpired.collectLatest {
                    println("isExpired is scope $isExpired $it")
                    if (!it) {
//                        SingletonHelper.appNavigator.navigateTo(ScreenValues.SECOND_APP)
                    }
                }

//                if (!isExpired) {
//                    SingletonHelper.appNavigator.navigateTo(ScreenValues.SECOND_APP)
//                }

            }
        }
        if (isExpired) {
            Spacer(Modifier.padding(80.dp))
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { DIFactory.locationListener?.processHseAuth() },
                colors = ButtonDefaults.buttonColors(backgroundColor = hseBlue)
            ) {
                Text(
                    stringResource(id = R.string.hse_enter),
                    color = Color.White,
                    modifier = Modifier.padding(7.dp),
                    fontSize = 14.sp
                )
            }
            Spacer(Modifier.padding(top = 40.dp))
        }

        if (isError.value) {
            Spacer(Modifier.padding(80.dp))
            Text(
                "Возникла ошибка на стороне сервера. Попробуйте перезайти в приложение.",
                fontSize = 14.sp,
                fontFamily = Montserrat,
                color = chipTimeColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}