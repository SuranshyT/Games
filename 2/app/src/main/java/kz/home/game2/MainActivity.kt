package kz.home.game2

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private var chosen = 0
    private lateinit var status: TextView
    private lateinit var startButton: Button
    private lateinit var chooseButton: Button
    private lateinit var rock: ImageView
    private lateinit var paper: ImageView
    private lateinit var scissors: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        status = findViewById(R.id.choose)
        startButton = findViewById(R.id.startButton)
        chooseButton = findViewById(R.id.button_choose)
        rock = findViewById(R.id.chosenRock)
        paper = findViewById(R.id.chosenPaper)
        scissors = findViewById(R.id.chosenScissors)
        val againButton = findViewById<Button>(R.id.againButton)
        val what = findViewById<ImageView>(R.id.opponent)
        val randomRock = findViewById<ImageView>(R.id.randomRock)
        val randomPaper = findViewById<ImageView>(R.id.randomPaper)
        val randomScissors = findViewById<ImageView>(R.id.randomScissors)

        chooseButton.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_custom, null)
            val dialog = AlertDialog.Builder(this).apply {
                setView(dialogView)
            }.create()

            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialogView.findViewById<ImageView>(R.id.rock).setOnClickListener {
                chosen = 1
                weaponSelected(R.string.chosen_rock, rock)
                dialog.dismiss()
            }
            dialogView.findViewById<ImageView>(R.id.paper).setOnClickListener {
                chosen = 2
                weaponSelected(R.string.chosen_paper, paper)
                dialog.dismiss()
            }
            dialogView.findViewById<ImageView>(R.id.scissors).setOnClickListener {
                chosen = 3
                weaponSelected(R.string.chosen_scissors, scissors)
                dialog.dismiss()
            }

            dialog.show()
        }

        startButton.setOnClickListener {
            what.visibility = View.GONE
            val random = Random.nextInt(1, 4)
            whoWon(random, chosen)
            when (random) {
                1 -> {
                    randomRock.visibility = View.VISIBLE
                }
                2 -> {
                    randomPaper.visibility = View.VISIBLE
                }
                3 -> {
                    randomScissors.visibility = View.VISIBLE
                }
            }
            startButton.visibility = View.GONE
            againButton.visibility = View.VISIBLE
        }

        againButton.setOnClickListener {
            againButton.visibility = View.GONE
            rock.visibility = View.GONE
            paper.visibility = View.GONE
            scissors.visibility = View.GONE
            randomRock.visibility = View.GONE
            randomPaper.visibility = View.GONE
            randomScissors.visibility = View.GONE
            status.text = getString(R.string.choose)
            status.setTextColor(ContextCompat.getColor(this, R.color.black))
            what.visibility = View.VISIBLE
            chooseButton.visibility = View.VISIBLE
        }
    }

    private fun weaponSelected(id: Int, image: ImageView) {
        status.text = getString(id)
        startButton.visibility = View.VISIBLE
        chooseButton.visibility = View.GONE
        image.visibility = View.VISIBLE
    }

    private fun win() {
        status.text = getString(R.string.winner)
        status.setTextColor(ContextCompat.getColor(this, R.color.win))
    }

    private fun lose() {
        status.text = getString(R.string.loser)
        status.setTextColor(ContextCompat.getColor(this, R.color.lose))
    }

    private fun draw() {
        status.text = getString(R.string.draw)
    }

    private fun whoWon(computer: Int, player: Int) {
        if (computer == player) {
            draw()
        }
        if ((computer == 1 && player == 2) || (computer == 2 && player == 3) || (computer == 3 && player == 1)) {
            win()
        } else if ((computer == 1 && player == 3) || (computer == 2 && player == 1) || (computer == 3 && player == 2)) {
            lose()
        }
    }
}