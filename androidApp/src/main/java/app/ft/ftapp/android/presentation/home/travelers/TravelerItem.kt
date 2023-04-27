package app.ft.ftapp.android.presentation.home.travelers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.android.ui.theme.blueTextColor
import app.ft.ftapp.domain.models.Participant

@Composable
fun TravelerItem(participant: Participant) {
    Surface(
        elevation = 8.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Rounded.Person, "", modifier = Modifier.size(84.dp))
            Column() {
                Text(participant.username, fontSize = 18.sp)
                Text(
                    participant.email,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    fontSize = 16.sp
                )
                Text("Бакалавриат группа 197-2019", color = blueTextColor, fontSize = 16.sp)
            }
        }
    }
}