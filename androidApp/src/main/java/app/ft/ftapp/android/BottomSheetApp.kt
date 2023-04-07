package app.ft.ftapp.android

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.unit.dp
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
            sheetContent(modalBottomSheetState)
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
