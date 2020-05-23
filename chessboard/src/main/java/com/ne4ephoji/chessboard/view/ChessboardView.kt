package com.ne4ephoji.chessboard.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.ne4ephoji.chessboard.R
import com.ne4ephoji.chessboard.data.model.ChessFigure
import com.ne4ephoji.chessboard.data.model.ChessboardField
import com.ne4ephoji.chessboard.data.model.toPositionAsFEN
import java.util.*
import kotlin.math.min

class ChessboardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    var position: List<ChessboardField> = emptyList()
        set(value) {
            field = value
            invalidate()
        }

    private var fieldSize = 0f

    private val blackPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.GRAY
    }

    private val whitePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.WHITE
    }

    private val figurePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 110f
        typeface = ResourcesCompat.getFont(context, R.font.casefont)
    }

    fun setPositionFromFEN(fenString: String) {
        position = fenString.toPositionAsFEN()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (field in position) {
            var figureChar = when (field.figure) {
                ChessFigure.EMPTY -> " "
                ChessFigure.WHITE_PAWN -> "p"
                ChessFigure.WHITE_KNIGHT -> "n"
                ChessFigure.WHITE_BISHOP -> "b"
                ChessFigure.WHITE_ROOK -> "r"
                ChessFigure.WHITE_QUEEN -> "q"
                ChessFigure.WHITE_KING -> "k"
                ChessFigure.BLACK_PAWN -> "o"
                ChessFigure.BLACK_KNIGHT -> "m"
                ChessFigure.BLACK_BISHOP -> "v"
                ChessFigure.BLACK_ROOK -> "t"
                ChessFigure.BLACK_QUEEN -> "w"
                ChessFigure.BLACK_KING -> "l"
            }
            if (field.isBlack()) {
                figureChar =
                    if (field.figure == ChessFigure.EMPTY) "+"
                    else figureChar.toUpperCase(Locale.US)
            }
            canvas.drawText(
                figureChar,
                fieldSize * field.file,
                fieldSize * (field.rank + 1),
                figurePaint
            )
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        fieldSize = min(width, height).toFloat() / 8
        figurePaint.setTextSizeForWidth(fieldSize, "W")
    }
}

fun Paint.setTextSizeForWidth(
    desiredWidth: Float,
    text: String
) {
    // Pick a reasonably large value for the test. Larger values produce
    // more accurate results, but may cause problems with hardware
    // acceleration. But there are workarounds for that, too; refer to
    // http://stackoverflow.com/questions/6253528/font-size-too-large-to-fit-in-cache
    val testTextSize = 48f

    // Get the bounds of the text, using our testTextSize.
    textSize = testTextSize
    val bounds = Rect()
    getTextBounds(text, 0, text.length, bounds)

    // Calculate the desired size as a proportion of our testTextSize.
    val desiredTextSize: Float = testTextSize * desiredWidth / bounds.width()

    // Set the paint for that size.
    textSize = desiredTextSize
}