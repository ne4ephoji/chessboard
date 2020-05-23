package com.ne4ephoji.chessboard.data.model

enum class ChessFigure {
    EMPTY,
    WHITE_PAWN, WHITE_KNIGHT, WHITE_BISHOP, WHITE_ROOK, WHITE_QUEEN, WHITE_KING,
    BLACK_PAWN, BLACK_KNIGHT, BLACK_BISHOP, BLACK_ROOK, BLACK_QUEEN, BLACK_KING
}

class ChessboardField(
    val figure: ChessFigure,
    val file: Int,
    val rank: Int
) {
    fun isBlack(): Boolean = (file + rank) % 2 == 1
}