package ddwu.com.mobile.finalproject.data.network

import android.content.Context
import ddwu.com.mobile.finalproject.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RefService(val context: Context) {
    val TAG = "RefService"
    val recipeService: RecipeService

    init {
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.food_url))
            .addConverterFactory( GsonConverterFactory.create() )
            .build()

        recipeService = retrofit.create(RecipeService::class.java)
    }
}