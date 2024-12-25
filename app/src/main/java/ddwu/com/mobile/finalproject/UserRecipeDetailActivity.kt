package ddwu.com.mobile.finalproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobile.finalproject.data.database.RecipeDto
import ddwu.com.mobile.finalproject.databinding.ActivityUserDetailRecipeBinding

class UserRecipeDetailActivity : AppCompatActivity()  {

    val binding by lazy {
        ActivityUserDetailRecipeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val recipeDto = intent.getSerializableExtra("recipeDto") as? RecipeDto

        binding.tvRecipeName.text = recipeDto?.recipe
        binding.recipeType.text = recipeDto?.type
        binding.ingredients.text = recipeDto?.ingredient
        binding.step.text = recipeDto?.step

        //뒤로가기 버튼 클릭
        binding.backButton.setOnClickListener {
            finish()
        }
    }
}