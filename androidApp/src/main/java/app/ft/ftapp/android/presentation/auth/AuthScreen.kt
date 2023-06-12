package app.ft.ftapp.android.presentation.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.R
import app.ft.ftapp.android.ui.navigation.AppDestination
import app.ft.ftapp.android.utils.SingletonHelper

/**
 * Composable method to show authentication screen.
 */
@Composable
@Preview
fun AuthScreen() {
    var login by remember { mutableStateOf("") }

    val localFocusManager = LocalFocusManager.current
    val onNextOption: (KeyboardActionScope.() -> Unit)? =
        { localFocusManager.moveFocus(FocusDirection.Down) }
    val onDoneOption: (KeyboardActionScope.() -> Unit)? = { localFocusManager.clearFocus() }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.find_traveler),
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                color = Color.Gray
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            CredentialsField(
                params = CredentialsEnterParams(
                    "Login", VisualTransformation.None,
                    ImeAction.Next,
                    onValueChange = { login = it },
                    onNextOption = onNextOption,
                    onDoneOption = onDoneOption
                )
            )

            LogInButton { SingletonHelper.appNavigator.tryNavigateTo(AppDestination.ListAnnounces()) }
        }
    }
}

/**
 * TextField to enter user's credentials.
 */
@Composable
fun CredentialsField(params: CredentialsEnterParams) {
    TextField(
        modifier = Modifier
            .padding(8.dp),
        value = stringResource(id = R.string.enter_password),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Green,
            backgroundColor = Color.Transparent
        ),
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center, color = Color.Gray),
        singleLine = true,
        onValueChange = { },

        keyboardOptions = KeyboardOptions.Default.copy(imeAction = params.imeAction),
        keyboardActions = KeyboardActions(
            onNext = params.onNextOption,
            onDone = params.onDoneOption
        ),
        visualTransformation = params.visualTransformation,
    )
}

/**
 * Composable to show and process login button.
 */
@Composable
fun LogInButton(onClick: () -> Unit) {
    Button(
        onClick = { onClick() }, //onClick()
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        border = BorderStroke(1.dp, Color.Black),
    ) {
        Text(
            stringResource(id = R.string.onward),
            color = Color.Black,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(4.dp)
                .padding(horizontal = 64.dp)
        )
    }
}


/**
 * Dataclass to fill UI credentials values.
 */
data class CredentialsEnterParams(
    val label: String,
    val visualTransformation: VisualTransformation,
    val imeAction: ImeAction,
    val onValueChange: (String) -> Unit,
    val onNextOption: (KeyboardActionScope.() -> Unit)?,
    val onDoneOption: (KeyboardActionScope.() -> Unit)?
)

