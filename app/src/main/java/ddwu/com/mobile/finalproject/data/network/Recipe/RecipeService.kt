package ddwu.com.mobile.finalproject.data.network.Recipe

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import ddwu.com.mobile.finalproject.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeService(val context: Context) {
    val TAG = "RefService"
    val recipeService: RecipeApi

    init {
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl(context.resources.getString(R.string.food_url))
            .addConverterFactory( GsonConverterFactory.create() )
            .build()

        recipeService = retrofit.create(RecipeApi::class.java)
    }

    //api에서 idx에 해당하는 모든 레시피 가져오기
    suspend fun getRecipes(startIdx: Int, endIdx: Int, dataType: String, ingredient: String? = null) : List<RecipeDetail> {
        val keyId = context.getString(R.string.keyId)
        val serviceId = context.getString(R.string.serviceId)

        //검색한 재료가 있을 경우
        return if (ingredient != null) {
            val recipeRoot = recipeService.getRecipesWithIngredient(
                keyId, serviceId, dataType, startIdx, endIdx, ingredient
            )
            Log.d(TAG, "Received recipeRoot with ingredient: $recipeRoot")
            recipeRoot.COOKRCP01.row
        } else { // ingredient가 없을 경우
            val recipeRoot = recipeService.getRecipes(
                keyId, serviceId, dataType, startIdx, endIdx
            )
            Log.d(TAG, "Received recipeRoot without ingredient: $recipeRoot")
            recipeRoot.COOKRCP01.row
        }

    }

    //레시피 이미지 가져와 Bitmap으로 반환
    suspend fun getImage(url: String?): Bitmap {
        val futureTarget : FutureTarget<Bitmap> =
            Glide.with(context)
                .asBitmap()
                .load(url)
                .submit()
        val bitmap = futureTarget.get()
        Glide.with(context).clear(futureTarget)

        return bitmap
    }

}