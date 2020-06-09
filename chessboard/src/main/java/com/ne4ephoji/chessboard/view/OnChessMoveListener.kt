package com.ne4ephoji.chessboard.view

import com.ne4ephoji.entities.ChessMove
import com.ne4ephoji.entities.ChessPosition

interface OnChessMoveListener {
    fun onChessMove(move: ChessMove, position: ChessPosition)
}