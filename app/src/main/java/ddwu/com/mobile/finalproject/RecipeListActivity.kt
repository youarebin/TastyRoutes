package ddwu.com.mobile.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.finalproject.data.network.Recipe.RecipeDetail
import ddwu.com.mobile.finalproject.databinding.ActivityRecipeListBinding
import ddwu.com.mobile.finalproject.ui.RecipeDetailAdapter
import ddwu.com.mobile.finalproject.ui.RecipeViewModel
import ddwu.com.mobile.finalproject.ui.RecipeViewModelFactory

class RecipeListActivity : AppCompatActivity() {

    val REQ_DETAIL= 200

    val binding by lazy {
        ActivityRecipeListBinding.inflate(layoutInflater)
    }

    /// MutableList로 초기화
    var recipes: MutableList<RecipeDetail> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /*RecyclerView 의 layoutManager 지정*/
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvRecipes.layoutManager = layoutManager

        // 초기 어댑터 설정 (빈 리스트로 시작)
        val adapter = RecipeDetailAdapter(emptyList())
        binding.rvRecipes.adapter = adapter

        val recipeViewModel: RecipeViewModel by viewModels {
            RecipeViewModelFactory((application as RecipeApplication).recipeRepository)
        }

        // ViewModel의 데이터 관찰 및 업데이트
        recipeViewModel.recipes.observe(this, Observer { recipeList ->
            if (!recipeList.isNullOrEmpty()) {
                // 받은 레시피 데이터로 어댑터 업데이트
                recipes.clear() // 기존 리스트 초기화
                recipes.addAll(recipeList) // 새로운 데이터 추가
                adapter.updateRecipes(recipes) // 어댑터에 업데이트된 레시피 전달
            } else {
                Log.d("RecipeListActivity", "No recipes available")
            }
        })

        //클릭시 레시피 디테일 페이지로 이동
        adapter.setOnItemClickListener(object : RecipeDetailAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(this@RecipeListActivity, RecipeDetailActivity::class.java)
                intent.putExtra("recipeDetail",recipes[position])// 클릭한 RecyclerView 항목 위치의 dto 전달
                startActivityForResult(intent, REQ_DETAIL)   // 수정결과를 받아오도록 Activity 호출
            }
        })

        // 레시피 목록 가져오기
        recipeViewModel.getRecipes(1, 50, "json")

        //재료 검색 버튼 클릭 -> 해당 레시피 보여줌
        binding.btnSearch.setOnClickListener {
            val ingredient = binding.etIngredient.text.toString().trim()
            Log.d("RecipeListActivity", "ingredient $ingredient")
            recipeViewModel.getRecipes(1, 50, "json", ingredient)
        }

    }

}