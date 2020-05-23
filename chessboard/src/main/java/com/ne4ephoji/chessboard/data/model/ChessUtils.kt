package com.ne4ephoji.chessboard.data.model

fun String.toPositionAsFEN(): List<ChessboardField> {
    val fields = ArrayList<ChessboardField>()
    val figureLines = split(' ').first().split('/')
    var fieldCounter = 0
    for (i in figureLines.indices) {
        for (char in figureLines[i]) {
            if (char.isDigit()) {
                val charValue = char.toString().toInt()
                for (k in 0 until charValue) {
                    fields.add(ChessboardField(ChessFigure.EMPTY, fieldCounter, i))
                    fieldCounter++
                }
            } else {
                val figure = when (char) {
                    'P' -> ChessFigure.WHITE_PAWN
                    'N' -> ChessFigure.WHITE_KNIGHT
                    'B' -> ChessFigure.WHITE_BISHOP
                    'R' -> ChessFigure.WHITE_ROOK
                    'Q' -> ChessFigure.WHITE_QUEEN
                    'K' -> ChessFigure.WHITE_KING
                    'p' -> ChessFigure.BLACK_PAWN
                    'n' -> ChessFigure.BLACK_KNIGHT
                    'b' -> ChessFigure.BLACK_BISHOP
                    'r' -> ChessFigure.BLACK_ROOK
                    'q' -> ChessFigure.BLACK_QUEEN
                    'k' -> ChessFigure.BLACK_KING
                    else -> throw IllegalArgumentException()
                }
                fields.add(ChessboardField(figure, fieldCounter, i))
                fieldCounter++
            }
        }
        fieldCounter = 0
    }
    return fields.toList()
}