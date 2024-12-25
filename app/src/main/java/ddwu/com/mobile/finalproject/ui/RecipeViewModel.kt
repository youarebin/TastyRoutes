package ddwu.com.mobile.finalproject.ui

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ddwu.com.mobile.finalproject.data.RecipeRepository
import ddwu.com.mobile.finalproject.data.network.Recipe.RecipeDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeViewModel(private val recipeRepository: RecipeRepository) : ViewModel()  {

    //api-레시피 목록
    private val _recipes = MutableLiveData<List<RecipeDetail>>()
    val recipes : LiveData<List<RecipeDetail>> = _recipes

    fun getRecipes(startIdx: Int, endIdx: Int, dataType: String, ingredient: String? = null) = viewModelScope.launch {
        _recipes.value = recipeRepository.getRecipes(startIdx, endIdx, dataType, ingredient)
    }

    //api-레시피 이미지
    private val _drawable = MutableLiveData<Bitmap>()
    val drawable : LiveData<Bitmap> = _drawable

    fun setImage(url: String?) = viewModelScope.launch {
        var bitmap: Bitmap
        withContext(Dispatchers.IO) {
            bitmap = recipeRepository.getImage(url)
        }
        _drawable.value = bitmap
    }

}