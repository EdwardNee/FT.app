package app.ft.ftapp.android.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import app.ft.ftapp.android.R
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

val Montserrat = FontFamily(
    Font(R.font.montserrat_black, weight = FontWeight.Black),
    Font(R.font.montserrat_bold, weight = FontWeight.Bold),
    Font(R.font.montserrat_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.montserrat_light, weight = FontWeight.Light),
    Font(R.font.montserrat_medium, weight = FontWeight.Medium),
    Font(R.font.montserrat_extralight, weight = FontWeight.ExtraLight),
    Font(R.font.montserrat_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.montserrat_regular, weight = FontWeight.Normal),
    Font(R.font.montserrat_semibold, weight = FontWeight.SemiBold)
)

val MontserratTypography = Typography(
    defaultFontFamily = Montserrat,
    h1 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h2 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body1 = TextStyle(
        fontFamily = Montserrat,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)