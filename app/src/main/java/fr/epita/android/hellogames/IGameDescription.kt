package fr.epita.android.hellogames

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Call

/**
 * Created by valentin on 3/8/18.
 */
interface IGameDescription {
    @GET("game/details")
    fun getGameDescription(@Query("game_id")game_id: Int): Call<GameDescription>
}