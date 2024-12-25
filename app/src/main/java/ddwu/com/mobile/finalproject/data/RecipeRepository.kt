package ddwu.com.mobile.finalproject.data

import android.graphics.Bitmap
import ddwu.com.mobile.finalproject.data.network.Recipe.RecipeDetail
import ddwu.com.mobile.finalproject.data.network.Recipe.RecipeService

class RecipeRepository(
    private val recipeService: RecipeService,
) {

    //api - 모든 레시피 get
    suspend fun getRecipes(startIdx: Int, endIdx: Int, dataType: String, ingredient: String? = null) : List<RecipeDetail> {
        return recipeService.getRecipes(startIdx, endIdx, dataType, ingredient)
    }

    //api - 레시피 이미지
    suspend fun getImage(url: String?) : Bitmap {
        return recipeService.getImage(url)
    }
}