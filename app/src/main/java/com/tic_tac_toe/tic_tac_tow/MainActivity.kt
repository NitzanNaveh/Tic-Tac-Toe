package com.tic_tac_toe.tic_tac_tow

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var button00: Button
    private lateinit var button01: Button
    private lateinit var button02: Button
    private lateinit var button10: Button
    private lateinit var button11: Button
    private lateinit var button12: Button
    private lateinit var button20: Button
    private lateinit var button21: Button
    private lateinit var button22: Button
    private lateinit var playAgainButton: Button
    private lateinit var playNowButton: Button
    private lateinit var gameBoard: LinearLayout
    private lateinit var welcomeMessage: TextView
    private lateinit var turnIndicator: TextView

    private var board: Array<Array<String>> = Array(3){Array(3){ "" }}
    private var current: Boolean ?= null // starting from X, false is O

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        button00 = findViewById(R.id.button00)
        button01 = findViewById(R.id.button01)
        button02 = findViewById(R.id.button02)
        button10 = findViewById(R.id.button10)
        button11 = findViewById(R.id.button11)
        button12 = findViewById(R.id.button12)
        button20 = findViewById(R.id.button20)
        button21 = findViewById(R.id.button21)
        button22 = findViewById(R.id.button22)
        playAgainButton = findViewById(R.id.playAgainButton)
        playNowButton = findViewById(R.id.playNowButton)
        gameBoard = findViewById(R.id.gameBoard)
        welcomeMessage = findViewById(R.id.welcomeMessage)
        turnIndicator = findViewById(R.id.turnIndicator)

        // show only the "Play Now" button
        gameBoard.visibility = View.GONE
        turnIndicator.visibility = View.GONE

        // set click listener for play now button
        playNowButton.setOnClickListener{
            playNowButton.visibility = View.GONE
            gameBoard.visibility = View.VISIBLE
            turnIndicator.visibility = View.VISIBLE
            turnIndicator.text = "Press a cell to start!"
        }

        // Create a 2D list of buttons
        val buttons = listOf(
            listOf(button00, button01, button02),
            listOf(button10, button11, button12),
            listOf(button20, button21, button22)
        )
        // Set up click listeners for the buttons
        for (i in 0 until buttons.size) {
            for (j in 0 until buttons[i].size) {
                buttons[i][j].setOnClickListener {
                    onClick(i, j, buttons[i][j])
                }
            }
        }

        playAgainButton.setOnClickListener {
            playAgain() // Reset the game when the button is clicked
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun checkWin(): Boolean {
        // Check main diagonal
        if (board[0][0] != "" && board[0][0] == board[1][1] && board[1][1] == board[2][2])
        {return true}
        // Check secondary diagonal
        if (board[0][2] != "" && board[0][2] == board[1][1] && board[1][1] == board[2][0])
        {return true}

        // Check rows
        for (i in 0 until board.size) {
            if (board[i][0] != "" && board[i][0] == board[i][1] && board[i][1] == board[i][2])
            {return true}
        }
        // Check columns
        for (j in 0 until board[0].size) {
            if (board[0][j] != "" && board[0][j] == board[1][j] && board[1][j] == board[2][j])
            {return true}
        }
        return false
    }

    private fun isDraw(): Boolean {
        for (i in 0..2) {
            for (j in 0..2) {
                if (board[i][j] == "") return false // Empty cell found
            }
        }
        return true // All cells are filled
    }

    private fun onClick(row:Int, col:Int, button:Button): Unit{
        if(board[row][col] != ""){ // cell isn't empty
            return
        }
        if(current == null){
            current = true
            turnIndicator.text = "Press a cell to start!" // first move
            return
        }
        if(current == true){
            board[row][col] = "X"
            button.text = "X"
            turnIndicator.text = "Player O's Turn"
        }
        else{
            board[row][col] = "O"
            button.text = "O"
            turnIndicator.text = "Player X's Turn"
        }
        current = current == false

        if (checkWin()) {
            gameOver("Player ${if (current == false) "X" else "O"} wins!")
        } else if (isDraw()) {
            gameOver("It's a draw!")
        }
    }

    private fun gameOver(message: String) {
        // Show an alert dialog with the game over message
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Game Over!")
        builder.setMessage(message)
        builder.setCancelable(false) // Prevent the dialog from being dismissed without action
        builder.setPositiveButton("Play Again!") { _, _ ->
            playAgain()
        }
        builder.setNegativeButton("Close") {_, _ ->
            // close the dialog
        }
        builder.show()
        // Disable all buttons on the board to prevent further play
        val buttons = listOf(
            listOf(button00, button01, button02),
            listOf(button10, button11, button12),
            listOf(button20, button21, button22)
        )
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j].isEnabled = false
            }
        }
    }


    private fun playAgain() {
        // Reset the board array
        for (i in 0 until board.size) {
            for (j in 0 until board[i].size) {
                board[i][j] = ""
            }
        }
        // Reset the current player to player X
        current = null
        turnIndicator.text = "Press a cell to start!"

        // Reset button texts
        val buttons = listOf(
            listOf(button00, button01, button02),
            listOf(button10, button11, button12),
            listOf(button20, button21, button22)
        )

        for (i in 0 until buttons.size) {
            for (j in 0 until buttons[i].size) {
                buttons[i][j].text = ""
                buttons[i][j].isEnabled = true
            }
        }
    }


}