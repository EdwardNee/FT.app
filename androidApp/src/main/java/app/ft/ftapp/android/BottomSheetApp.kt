package app.ft.ftapp.android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.ft.ftapp.android.ui.theme.appBackground
import kotlinx.coroutines.launch

/**
 * Bottom sheet template to show some content.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetApp(
    sheetContent: @Composable (modalBottomSheetState: ModalBottomSheetState, ) -> Unit,
    pageContent: @Composable (onClick: () -> Unit) -> Unit
) {
    val modalBottomSheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        sheetContent = {
            if(modalBottomSheetState.isVisible) {
                sheetContent(modalBottomSheetState)
            } else {
                Surface(Modifier.size(0.2.dp).background(Color.Transparent)) {
                    Text("", fontSize = 0.sp)
                }
            }
        },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetBackgroundColor = appBackground,
        // scrimColor = ,  //Color for the fade background when open/close the drawer
    ) {
        pageContent() {
            scope.launch {
                modalBottomSheetState.show()
            }
        }
    }
}
