package fr.epita.android.hellogames

import retrofit2.http.GET
import retrofit2.Call

/**
 * Created by valentin on 3/7/18.
 */
interface IGames {
    @GET("game/list")
   fun getListGame(): Call<List<Game>>;
}