package app.ft.ftapp.android.presentation.announce_details.component

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.compose.ui.graphics.Color
import app.ft.ftapp.android.R
import kotlin.properties.Delegates

class SeatsFreeView(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : View(context, attributeSet, defStyleAttr, defStyleRes) {

    private var gridColor by Delegates.notNull<Int>()
    private var filledColor by Delegates.notNull<Int>()
    private var emptyColor by Delegates.notNull<Int>()

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attributeSet,
        defStyleAttr,
        R.style.defaultFilledStyle
    )

    init {
        if (attributeSet != null) {
            initAttributes(attributeSet, defStyleAttr, defStyleRes)
        } else {
            initDefaultColors()
        }
    }

    private fun initDefaultColors() {
        filledColor = android.graphics.Color.GREEN
        emptyColor = android.graphics.Color.WHITE
        gridColor =  android.graphics.Color.BLACK
    }

    private fun initAttributes(
        attributeSet: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.SeatsFreeView,
            defStyleAttr,
            defStyleRes
        )

        filledColor = typedArray.getColor(R.styleable.SeatsFreeView_filledColor, android.graphics.Color.GREEN)
        emptyColor = typedArray.getColor(R.styleable.SeatsFreeView_emptyColor, android.graphics.Color.WHITE)
        gridColor = typedArray.getColor(R.styleable.SeatsFreeView_gridColor, android.graphics.Color.BLACK)

        typedArray.recycle()
    }
}

typealias UsersAdded = () -> Unit

enum class Cell {
    EMPTY, FILLED
}

data class SeatsOverall(val maximum: Int) {
    private val cells = Array(maximum) {
        Cell.EMPTY
    }

    private var current: Int = 0

    fun removeSell() {
        if (current <= 0) {
            return
        }

        cells[--current] = Cell.EMPTY
    }

    fun setCell() {
        if (current >= maximum) {
            return
        }

        cells[current++] = Cell.FILLED
    }
}