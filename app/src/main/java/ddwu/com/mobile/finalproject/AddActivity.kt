package ddwu.com.mobile.finalproject

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobile.finalproject.databinding.ActivityAddrecipeBinding

class AddActivity : AppCompatActivity() {
    val addBinding by lazy {
        ActivityAddrecipeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(addBinding.root)
        //등록 버튼 클릭
        addBinding.btnAddRecipe.setOnClickListener {
            val recipeName = addBinding.etAddRecipeName.text.toString()
            val ingredient = addBinding.etAddIngredients.text.toString()
            val recipeStep = addBinding.etAddRecipeStep.text.toString()

            if (recipeName.isEmpty() || ingredient.isEmpty() || recipeStep.isEmpty()) {
                Toast.makeText(this, "모든 필드를 입력해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
        //취소 버튼 클릭
        addBinding.btnAddCancle.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}