package fr.epita.android.hellogames

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.GsonBuilder

import kotlinx.android.synthetic.main.activity_main2.*
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        var data: GameDescription

        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"

        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())

        val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(jsonConverter)
                .build()

        val service: IGameDescription = retrofit.create(IGameDescription::class.java)

        val callback = object : Callback<GameDescription> {
            override fun onFailure(call: retrofit2.Call<GameDescription>?, t: Throwable?) {
                Log.d("Tag", "Webservice Call failed")
            }

            override fun onResponse(call: retrofit2.Call<GameDescription>?,
                                    response: Response<GameDescription>?) {

                if (response != null)
                {
                    if (response.code() == 200)
                    {
                        val responseData = response.body()
                        if (responseData != null)
                        {
                            data = responseData
                            fulfillElement(data)
                        }
                    }
                }
            }
        }
        val intent = intent
        val message = intent.getIntExtra("MESSAGE", 0)

        service.getGameDescription(message).enqueue(callback)
    }

    fun fulfillElement(data: GameDescription)
    {
        nameActivity2.text = data.name
        typeActivity2.text = data.type
        nbplayerActivity2.text = data.player.toString()
        imageDescription.setImageResource(factory(data.name))
        description.text = data.description_en
        yearActivity2.text = data.year.toString()
        knowmore.setOnClickListener({
            val indent = Intent(Intent.ACTION_VIEW)
            indent.data = Uri.parse(data.url)
            startActivity(indent)
        })
    }

    fun factory(name: String) : Int
    {
        when (name) {
            "Battleship" -> return R.drawable.battleship
            "GameOfLife" -> return R.drawable.gameoflife
            "Hangman" -> return R.drawable.hangman
            "Mastermind" -> return R.drawable.mastermind
            "Minesweeper" -> return R.drawable.minesweeper
            "Simon" -> return R.drawable.simon
            "TicTacToe" -> return R.drawable.tictactoe
            "Sudoku" -> return R.drawable.sudoku
            else -> return R.drawable.memory
        }
    }


}
