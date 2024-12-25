package ddwu.com.mobile.finalproject

import android.content.ContentValues
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobile.finalproject.data.database.RecipeDBHelper
import ddwu.com.mobile.finalproject.data.database.RecipeDao
import ddwu.com.mobile.finalproject.data.database.RecipeDto
import ddwu.com.mobile.finalproject.databinding.ActivityAddRecipeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddActivity : AppCompatActivity() {
    val addBinding by lazy {
        ActivityAddRecipeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(addBinding.root)

        // 스피너에 어댑터 설정
        val recipeCategories = resources.getStringArray(R.array.recipe_categories)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, recipeCategories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        addBinding.spinnerRecipeType.adapter = adapter

        //등록 버튼 클릭
        addBinding.btnAddRecipe.setOnClickListener {
            val recipeName = addBinding.etAddRecipeName.text.toString()
            val type = addBinding.spinnerRecipeType.selectedItem.toString()
            val ingredient = addBinding.etAddIngredients.text.toString()
            val recipeStep = addBinding.etAddRecipeStep.text.toString()

            // 필드가 비어있는지 체크
            if (recipeName.isEmpty() || type.isEmpty() || ingredient.isEmpty() || recipeStep.isEmpty()) {
                Toast.makeText(this, "모든 필드를 입력해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 비동기로 레시피 추가 작업
            GlobalScope.launch(Dispatchers.IO) {
                val result = addRecipe(RecipeDto(0, recipeName, type, ingredient, recipeStep))

                withContext(Dispatchers.Main) {
                    if (result > 0) {
                        setResult(RESULT_OK)
                    } else {
                        Toast.makeText(this@AddActivity, "레시피 추가 실패", Toast.LENGTH_SHORT).show()
                    }
                    finish()
                }
            }

            finish()
        }
        //취소 버튼 클릭
        addBinding.btnAddCancle.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    //레시피 등록
    fun addRecipe(dto: RecipeDto) : Long{
        val helper = RecipeDBHelper(this)
        val db = helper.writableDatabase

        val newValue = ContentValues()
        newValue.put(RecipeDBHelper.COL_RECIPE, dto.recipe)
        newValue.put(RecipeDBHelper.COL_TYPE, dto.type)
        newValue.put(RecipeDBHelper.COL_INGREDIENT, dto.ingredient)
        newValue.put(RecipeDBHelper.COL_STEP, dto.step)

        val newCount = db.insert(RecipeDBHelper.TABLE_NAME, null, newValue)

        helper.close()

        return newCount
    }
}