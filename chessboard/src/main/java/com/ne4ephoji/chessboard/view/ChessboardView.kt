package com.ne4ephoji.chessboard.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.ne4ephoji.chessboard.R
import com.ne4ephoji.entities.*
import com.ne4ephoji.entities.ChessPosition.Companion.CHESSBOARD_SIZE
import com.ne4ephoji.utils.asFENToPosition
import com.ne4ephoji.utils.getAvailableMovesFromField
import com.ne4ephoji.utils.toChessMove
import kotlin.math.min

class ChessboardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {
    var editorModeEnabled = false
    var onChessMoveListener: OnChessMoveListener? = null
    private var lastMove: ChessMove? = null
    private var lastSelectedField: ChessField? = null
    private var availableMoves: Set<ChessMove> = emptySet()
    private var position: ChessPosition = ChessPosition(
        Array(8) { arrayOfNulls<ChessFigure>(8) },
        ChessSide.WHITE,
        ChessCastlings(
            whiteKingsideCastlingAvailable = true,
            whiteQueensideCastlingAvailable = true,
            blackKingsideCastlingAvailable = true,
            blackQueensideCastlingAvailable = true
        ),
        null,
        0,
        1
    )
        set(value) {
            field = value
            invalidate()
        }

    private var fieldSize = 0f

    private val selectedFieldPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.YELLOW
        alpha = 20
    }
    private val availableMovesPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.GREEN
        alpha = 20
    }
    private val lastMovePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        alpha = 20
    }
    private val fieldPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        textSize = 110f
        typeface = ResourcesCompat.getFont(context, R.font.casefont)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until CHESSBOARD_SIZE) {
            for (j in 0 until CHESSBOARD_SIZE) {
                val figure = position.figures[i][j]
                var figureChar = if (figure == null) ' ' else {
                    when (figure.side) {
                        ChessSide.WHITE -> when (figure.type) {
                            ChessFigure.Type.PAWN -> 'p'
                            ChessFigure.Type.KNIGHT -> 'n'
                            ChessFigure.Type.BISHOP -> 'b'
                            ChessFigure.Type.ROOK -> 'r'
                            ChessFigure.Type.QUEEN -> 'q'
                            ChessFigure.Type.KING -> 'k'
                        }
                        ChessSide.BLACK -> when (figure.type) {
                            ChessFigure.Type.PAWN -> 'o'
                            ChessFigure.Type.KNIGHT -> 'm'
                            ChessFigure.Type.BISHOP -> 'v'
                            ChessFigure.Type.ROOK -> 't'
                            ChessFigure.Type.QUEEN -> 'w'
                            ChessFigure.Type.KING -> 'l'
                        }
                    }
                }
                if ((i + j) % 2 == 1) {
                    figureChar = if (figure == null) '+' else figureChar.toUpperCase()
                }
                canvas.drawText(
                    figureChar.toString(),
                    fieldSize * j,
                    fieldSize * (i + 1),
                    fieldPaint
                )
            }

            lastSelectedField?.let {
                canvas.drawRect(
                    fieldSize * it.file,
                    fieldSize * it.rank,
                    fieldSize * (it.file + 1),
                    fieldSize * (it.rank + 1),
                    selectedFieldPaint
                )
            }
            availableMoves.map { it.target }.toSet().forEach {
                canvas.drawRect(
                    fieldSize * it.file,
                    fieldSize * it.rank,
                    fieldSize * (it.file + 1),
                    fieldSize * (it.rank + 1),
                    availableMovesPaint
                )
            }
            lastMove?.let {
                canvas.drawRect(
                    fieldSize * it.source.file,
                    fieldSize * it.source.rank,
                    fieldSize * (it.source.file + 1),
                    fieldSize * (it.source.rank + 1),
                    lastMovePaint
                )
                canvas.drawRect(
                    fieldSize * it.target.file,
                    fieldSize * it.target.rank,
                    fieldSize * (it.target.file + 1),
                    fieldSize * (it.target.rank + 1),
                    lastMovePaint
                )
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        fieldSize = min(width, height).toFloat() / CHESSBOARD_SIZE
        fieldPaint.setTextSizeForWidth(fieldSize, "W")
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (editorModeEnabled) {
            event?.let {
                if (it.action == MotionEvent.ACTION_DOWN) {
                    val selectedField = ChessField(
                        rank = (it.y / fieldSize).toInt(),
                        file = (it.x / fieldSize).toInt()
                    )
                    if (lastSelectedField == null) {
                        lastSelectedField = selectedField
                        availableMoves = position.getAvailableMovesFromField(selectedField)
                    } else {
                        if (lastSelectedField == selectedField) {
                            availableMoves = emptySet()
                            lastSelectedField = null
                        } else {
                            val moves = availableMoves.filter { move ->
                                move.source == lastSelectedField && move.target == selectedField
                            }
                            if (moves.isNotEmpty()) {
                                onChessMoveListener?.onChessMove(moves.last(), position)
                                position.makeMove(moves.last())
                                availableMoves = emptySet()
                                lastSelectedField = null
                                lastMove = moves.last()
                            } else {
                                lastSelectedField = selectedField
                                availableMoves = position.getAvailableMovesFromField(selectedField)
                            }
                        }
                    }
                    invalidate()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    fun setupPositionFromFEN(fenString: String) {
        position = fenString.asFENToPosition()
        lastMove = null
        availableMoves = emptySet()
        lastSelectedField = null
    }

    fun setupPosition(position: ChessPosition) {
        this.position = position
        lastMove = null
        availableMoves = emptySet()
        lastSelectedField = null
    }

    fun makeMoveFromString(moveString: String) {
        val move = moveString.toChessMove(position)
        onChessMoveListener?.onChessMove(move, position)
        position.makeMove(move)
        availableMoves = emptySet()
        lastSelectedField = null
        lastMove = move
    }

    fun makeMove(move: ChessMove) {
        onChessMoveListener?.onChessMove(move, position)
        position.makeMove(move)
        availableMoves = emptySet()
        lastSelectedField = null
        lastMove = move
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