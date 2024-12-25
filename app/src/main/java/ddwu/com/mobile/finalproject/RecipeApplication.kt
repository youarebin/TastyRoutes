package ddwu.com.mobile.finalproject

import android.app.Application
import ddwu.com.mobile.finalproject.data.RecipeRepository
import ddwu.com.mobile.finalproject.data.network.Recipe.RecipeService

class RecipeApplication : Application() {
    val recipeService by lazy {
        RecipeService(this)
    }

    val recipeRepository by lazy {
        RecipeRepository(recipeService)
    }
}