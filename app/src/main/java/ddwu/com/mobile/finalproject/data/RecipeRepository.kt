package ddwu.com.mobile.finalproject.data

import android.graphics.Bitmap
import ddwu.com.mobile.finalproject.data.network.RecipeDetail
import ddwu.com.mobile.finalproject.data.network.RecipeService

class RecipeRepository(private val recipeService: RecipeService ) {

    suspend fun getRecipes(startIdx: Int, endIdx: Int, dataType: String) : List<RecipeDetail> {
        return recipeService.getRecipes(startIdx, endIdx, dataType)
    }

    suspend fun getImage(url: String?) : Bitmap {
        return recipeService.getImage(url)
    }
}