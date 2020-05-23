package com.ne4ephoji.chessboard_test_app

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.ne4ephoji.chessboard.data.model.ChessFigure
import com.ne4ephoji.chessboard.data.model.ChessboardField
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chessboard.position = listOf(
            ChessboardField(ChessFigure.WHITE_ROOK, 0, 7),
            ChessboardField(ChessFigure.WHITE_KNIGHT, 1, 7),
            ChessboardField(ChessFigure.WHITE_BISHOP, 2, 7),
            ChessboardField(ChessFigure.WHITE_QUEEN, 3, 7),
            ChessboardField(ChessFigure.WHITE_KING, 4, 7),
            ChessboardField(ChessFigure.WHITE_BISHOP, 5, 7),
            ChessboardField(ChessFigure.WHITE_KNIGHT, 6, 7),
            ChessboardField(ChessFigure.WHITE_ROOK, 7, 7),
            ChessboardField(ChessFigure.WHITE_PAWN, 0, 6),
            ChessboardField(ChessFigure.WHITE_PAWN, 1, 6),
            ChessboardField(ChessFigure.WHITE_PAWN, 2, 6),
            ChessboardField(ChessFigure.WHITE_PAWN, 3, 6),
            ChessboardField(ChessFigure.WHITE_PAWN, 4, 6),
            ChessboardField(ChessFigure.WHITE_PAWN, 5, 6),
            ChessboardField(ChessFigure.WHITE_PAWN, 6, 6),
            ChessboardField(ChessFigure.WHITE_PAWN, 7, 6),
            ChessboardField(ChessFigure.EMPTY, 0, 5),
            ChessboardField(ChessFigure.EMPTY, 1, 5),
            ChessboardField(ChessFigure.EMPTY, 2, 5),
            ChessboardField(ChessFigure.EMPTY, 3, 5),
            ChessboardField(ChessFigure.EMPTY, 4, 5),
            ChessboardField(ChessFigure.EMPTY, 5, 5),
            ChessboardField(ChessFigure.EMPTY, 6, 5),
            ChessboardField(ChessFigure.EMPTY, 7, 5),
            ChessboardField(ChessFigure.EMPTY, 0, 4),
            ChessboardField(ChessFigure.EMPTY, 1, 4),
            ChessboardField(ChessFigure.EMPTY, 2, 4),
            ChessboardField(ChessFigure.EMPTY, 3, 4),
            ChessboardField(ChessFigure.EMPTY, 4, 4),
            ChessboardField(ChessFigure.EMPTY, 5, 4),
            ChessboardField(ChessFigure.EMPTY, 6, 4),
            ChessboardField(ChessFigure.EMPTY, 7, 4),
            ChessboardField(ChessFigure.EMPTY, 0, 3),
            ChessboardField(ChessFigure.EMPTY, 1, 3),
            ChessboardField(ChessFigure.EMPTY, 2, 3),
            ChessboardField(ChessFigure.EMPTY, 3, 3),
            ChessboardField(ChessFigure.EMPTY, 4, 3),
            ChessboardField(ChessFigure.EMPTY, 5, 3),
            ChessboardField(ChessFigure.EMPTY, 6, 3),
            ChessboardField(ChessFigure.EMPTY, 7, 3),
            ChessboardField(ChessFigure.EMPTY, 0, 2),
            ChessboardField(ChessFigure.EMPTY, 1, 2),
            ChessboardField(ChessFigure.EMPTY, 2, 2),
            ChessboardField(ChessFigure.EMPTY, 3, 2),
            ChessboardField(ChessFigure.EMPTY, 4, 2),
            ChessboardField(ChessFigure.EMPTY, 5, 2),
            ChessboardField(ChessFigure.EMPTY, 6, 2),
            ChessboardField(ChessFigure.EMPTY, 7, 2),
            ChessboardField(ChessFigure.BLACK_PAWN, 0, 1),
            ChessboardField(ChessFigure.BLACK_PAWN, 1, 1),
            ChessboardField(ChessFigure.BLACK_PAWN, 2, 1),
            ChessboardField(ChessFigure.BLACK_PAWN, 3, 1),
            ChessboardField(ChessFigure.BLACK_PAWN, 4, 1),
            ChessboardField(ChessFigure.BLACK_PAWN, 5, 1),
            ChessboardField(ChessFigure.BLACK_PAWN, 6, 1),
            ChessboardField(ChessFigure.BLACK_PAWN, 7, 1),
            ChessboardField(ChessFigure.BLACK_ROOK, 0, 0),
            ChessboardField(ChessFigure.BLACK_KNIGHT, 1, 0),
            ChessboardField(ChessFigure.BLACK_BISHOP, 2, 0),
            ChessboardField(ChessFigure.BLACK_QUEEN, 3, 0),
            ChessboardField(ChessFigure.BLACK_KING, 4, 0),
            ChessboardField(ChessFigure.BLACK_BISHOP, 5, 0),
            ChessboardField(ChessFigure.BLACK_KNIGHT, 6, 0),
            ChessboardField(ChessFigure.BLACK_ROOK, 7, 0)
        )
        Handler().postDelayed(
            { chessboard.setPositionFromFEN("3n4/k1prp3/2p2R1K/3qpP2/7P/2P2p1p/5b2/8 w - - 0 1") },
            1000L
        )
        Handler().postDelayed(
            { chessboard.setPositionFromFEN("8/2p1Ppp1/k2P2P1/3P4/3KbPR1/2rpp3/8/n7 w - - 0 1") },
            2000L
        )
        Handler().postDelayed(
            { chessboard.setPositionFromFEN("4kr2/1p1b1p1P/R4p2/8/n2NP3/q2n3P/6K1/4B3 w - - 0 1") },
            3000L
        )
    }
}
