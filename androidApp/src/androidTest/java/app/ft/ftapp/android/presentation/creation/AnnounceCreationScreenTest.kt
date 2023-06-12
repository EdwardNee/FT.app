package app.ft.ftapp.android.presentation.creation

import app.ft.ftapp.android.TestTags
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import app.ft.ftapp.android.TestActivity
import app.ft.ftapp.android.ui.ScreenValues
import org.junit.Rule
import org.junit.Test

/**
 * Testing class for [app.ft.ftapp.android.presentation.creation.AnnounceCreationScreen]
 */
class AnnounceCreationScreenTest {
    @get:Rule
    val rule = createAndroidComposeRule<TestActivity>()


    @Test
    fun test_to_check_all_fields_existence(): Unit = with(rule) {
        onNodeWithTag(ScreenValues.CREATION_MAP).assertExists()
        onNodeWithTag(ScreenValues.CREATION_MAP).performClick()
        onNodeWithText("Откуда едем?").assertExists()

        onNodeWithText("Откуда едем?").performClick()
        onNodeWithTag(TestTags.MapCreationButton).performClick()

        waitUntil(timeoutMillis = 1000) {
            onAllNodesWithText("Создать объявление").fetchSemanticsNodes().isNotEmpty()
        }

        onNodeWithText("Откуда едем?").assertExists()
        onNodeWithText("Откуда едем?").performTextInput("ВШЭ москва")

//        waitUntil(timeoutMillis = 15000) {
//            onAllNodesWithText("Высшая школа экономики").fetchSemanticsNodes().isNotEmpty()
//        }

        onNodeWithText("Дубки 1 корпус").performClick()

        onNodeWithText("Куда едем?").performClick()
        onNodeWithText("Дубки 3 корпус").performClick()


//        onNodeWithTag(app.ft.ftapp.android.TestTags.PriceTextField).assert
        onNodeWithTag(TestTags.PlacesTextField).onChildAt(0).performTextClearance()
        onNodeWithTag(TestTags.PlacesTextField).onChildAt(0).performTextInput("3")

        onNodeWithText("Дополнительные комментарии...").performTextInput("Комментарий поездки")
        onNodeWithText("Опубликовать").performClick()


        waitUntil(timeoutMillis = 5000) {
            onAllNodesWithText("Объявление опубликовано!").fetchSemanticsNodes().isNotEmpty()
        }
    }
}