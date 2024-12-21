package ddwu.com.mobile.finalproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ddwu.com.mobile.finalproject.data.RecipeRepository

class RecipeViewModelFactory(private val repo : RecipeRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
            return RecipeViewModel(repo) as T
        }
        return IllegalArgumentException("Unknown ViewModel class") as T
    }
}