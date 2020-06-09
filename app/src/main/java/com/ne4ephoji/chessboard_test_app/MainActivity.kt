package com.ne4ephoji.chessboard_test_app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ne4ephoji.chessboard.view.OnChessMoveListener
import com.ne4ephoji.entities.ChessMove
import com.ne4ephoji.entities.ChessPosition
import com.ne4ephoji.utils.toString
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chessboard.setupPositionFromFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1")
        chessboard.onChessMoveListener = object : OnChessMoveListener {
            override fun onChessMove(move: ChessMove, position: ChessPosition) {
                Log.i("CHESS MOVE DONE", move.toString(position))
            }
        }
        chessboard.makeMoveFromString("e4")
        chessboard.editorModeEnabled = true
    }
}
