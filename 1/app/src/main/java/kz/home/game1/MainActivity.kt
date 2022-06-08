package kz.home.game1

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private var player = 1
    private var winner = 0
    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val x = getString(R.string.x)
        val o = getString(R.string.o)
        val whichMove = findViewById<TextView>(R.id.playerIs)
        val list = listOf<TextView>(
            findViewById(R.id.rec1), findViewById(R.id.rec2), findViewById(R.id.rec3),
            findViewById(R.id.rec4), findViewById(R.id.rec5), findViewById(R.id.rec6),
            findViewById(R.id.rec7), findViewById(R.id.rec8), findViewById(R.id.rec9)
        )

        for (i in list.indices) {
            list[i].setOnClickListener {
                if (list[i].text.isBlank()) {
                    if (player == 1) {
                        cellPressed(list[i], x, R.drawable.rectangle_x)
                        whichMove.text = getString(R.string.player2)
                        player = 2
                        counter += 1
                    } else if (player == 2) {
                        cellPressed(list[i], o, R.drawable.rectangle_o)
                        whichMove.text = getString(R.string.player1)
                        player = 1
                        counter += 1
                    }
                }

                horizontalLineCheck(list, x, o)
                verticalLineCheck(list, x, o)
                diagonalLineCheck(list, x, o)

                if (counter == list.size && winner == 0) {
                    winner = 3
                }

                if (winner != 0) {
                    val dialogView = layoutInflater.inflate(R.layout.dialog_custom, null)
                    val dialog = AlertDialog.Builder(this).apply {
                        setView(dialogView)
                    }.create()

                    dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                    if (winner == 1) {
                        dialogView.findViewById<TextView>(R.id.winnerIs).text =
                            getString(R.string.win_x)
                    } else if (winner == 2) {
                        dialogView.findViewById<TextView>(R.id.winnerIs).text =
                            getString(R.string.win_o)
                    } else {
                        dialogView.findViewById<TextView>(R.id.winnerIs).text =
                            getString(R.string.win_no)
                    }

                    dialogView.findViewById<View>(R.id.buttonAgain).setOnClickListener {
                        player = 1
                        winner = 0
                        counter = 0
                        whichMove.text = getString(R.string.player1)
                        for (j in list.indices) {
                            cellPressed(list[j], "", R.drawable.rectangle_background)
                        }
                        dialog.dismiss()
                    }

                    dialog.show()
                }
            }
        }
    }

    private fun cellPressed(list: TextView, text: String, drawable: Int) {
        list.background =
            AppCompatResources.getDrawable(this, drawable)
        list.text = text
    }

    private fun horizontalLineCheck(list: List<TextView>, x: String, o: String) {
        for (j in list.indices step 3) {
            if (list[j].text == x && list[j + 1].text == x && list[j + 2].text == x) {
                winner = 1
            }
            if (list[j].text == o && list[j + 1].text == o && list[j + 2].text == o) {
                winner = 2
            }
        }
    }

    private fun verticalLineCheck(list: List<TextView>, x: String, o: String) {
        for (j in 0..2) {
            if (list[j].text == x && list[j + 3].text == x && list[j + 6].text == x) {
                winner = 1
            }
            if (list[j].text == o && list[j + 3].text == o && list[j + 6].text == o) {
                winner = 2
            }
        }
    }

    private fun diagonalLineCheck(list: List<TextView>, x: String, o: String) {
        if (list[4].text == x && ((list[0].text == x && list[8].text == x) || (list[2].text == x && list[6].text == x))) {
            winner = 1
        }
        if (list[4].text == o && ((list[0].text == o && list[8].text == o) || (list[2].text == o && list[6].text == o))) {
            winner = 2
        }
    }
}