package fr.epita.android.hellogames

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    val gameFour = arrayListOf<Game>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = arrayListOf<Game>()
        val baseURL = "https://androidlessonsapi.herokuapp.com/api/"

        val jsonConverter = GsonConverterFactory.create(GsonBuilder().create())

        val retrofit = Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(jsonConverter)
                .build()

        val service: IGames = retrofit.create(IGames::class.java)

        val callback = object : Callback<List<Game>> {
            override fun onFailure(call: retrofit2.Call<List<Game>>?, t: Throwable?) {
                Log.d("Tag", "Webservice Call failed")
            }

            override fun onResponse(call: retrofit2.Call<List<Game>>?,
                                    response: Response<List<Game>>?) {

                if (response != null)
                {
                    if (response.code() == 200)
                    {
                        val responseData = response.body()
                        if (responseData != null)
                        {
                            data.addAll(responseData)
                            selectData(data)
                            setImage()
                            Log.d("size", data.size.toString())

                        }
                    }
                }
            }
        }
        service.getListGame().enqueue(callback);

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

    fun setImage()
    {
        imageTopLeft.setImageResource(factory(gameFour[0].name))
        imageTopLeft.setOnClickListener({
            val explicitIntent = Intent(this, Main2Activity::class.java)
                    //val message = gameFour[0].id.toString()
            explicitIntent.putExtra("MESSAGE", gameFour[0].id)
            startActivity(explicitIntent)
        })

        imageTopRight.setImageResource(factory(gameFour[1].name))
        imageTopRight.setOnClickListener({
            val explicitIntent = Intent(this, Main2Activity::class.java)
            //val message = gameFour[0].id.toString()
            explicitIntent.putExtra("MESSAGE", gameFour[1].id)
            startActivity(explicitIntent)
        })
        imageBottomLeft.setImageResource(factory(gameFour[2].name))
        imageBottomLeft.setOnClickListener({
            val explicitIntent = Intent(this, Main2Activity::class.java)
            //val message = gameFour[0].id.toString()
            explicitIntent.putExtra("MESSAGE", gameFour[2].id)
            startActivity(explicitIntent)
        })
        imageBottomRight.setImageResource(factory(gameFour[3].name))
        imageBottomRight.setOnClickListener({
            val explicitIntent = Intent(this, Main2Activity::class.java)
            //val message = gameFour[0].id.toString()
            explicitIntent.putExtra("MESSAGE", gameFour[3].id)
            startActivity(explicitIntent)
        })
    }

    fun selectData(games: List<Game>)
    {
        var tmp = arrayListOf<Game>()
        tmp.addAll(games)
        var x : Int = 0
        while (x < 4)
        {
            val randomVar: Int = Random().nextInt(tmp.size)
            gameFour.add(tmp[randomVar])
            tmp.removeAt(randomVar)
            x++
        }

    }
}
