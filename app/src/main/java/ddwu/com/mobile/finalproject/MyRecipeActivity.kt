package ddwu.com.mobile.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ddwu.com.mobile.finalproject.data.database.RecipeDao
import ddwu.com.mobile.finalproject.data.database.RecipeDto
import ddwu.com.mobile.finalproject.databinding.ActivityMyRecipeBinding
import ddwu.com.mobile.finalproject.ui.RecipeAdapter

//직접 등록한 레시피 조회
class MyRecipeActivity : AppCompatActivity() {

    val TAG = "MyRecipeActiviy"
    val REQ_DETAIL= 200

    val binding by lazy {
        ActivityMyRecipeBinding.inflate(layoutInflater)
    }

    lateinit var recipes : ArrayList<RecipeDto>
    lateinit var filteredRecipes: ArrayList<RecipeDto>
    lateinit var adapter: RecipeAdapter

    val recipeDao by lazy { RecipeDao(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /*RecyclerView 의 layoutManager 지정*/
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvRecipes.layoutManager = layoutManager

        recipes = recipeDao.getAllRecipes()
        filteredRecipes = ArrayList(recipes) // 필터링된 레시피 리스트 초기화

        adapter = RecipeAdapter(filteredRecipes) // Adapter를 필터링된 리스트로 초기화
        binding.rvRecipes.adapter = adapter // RecylcerView 에 adapter 설정

        //롱클릭시 목록에서 해당 레시피 삭제(대화상자 띄우기)
        adapter.setOnItemLongClickListener(object : RecipeAdapter.OnItemLongClickListener{
            override fun onItemLongClick(view: View, position: Int): Boolean {
                AlertDialog.Builder(this@MyRecipeActivity).apply {
                    title="레시피 삭제"
                    setMessage(recipes[position].recipe + "를 정말 삭제하시겠습니까?")
                    setPositiveButton("삭제"){ dialog, which ->
                        if(recipeDao.deleteRecipe(recipes[position]) > 0) {
                            //화면 갱신
                            recipes.clear() //기존 항목 제거
                            recipes.addAll(recipeDao.getAllRecipes()) // 항목 추가
                            binding.rvRecipes.adapter?.notifyDataSetChanged()  // RecyclerView 갱신
                        }
                    }
                    setNegativeButton("취소"){ dialog, which ->
                        dialog.dismiss()//대화상자 삭제
                    }
                    show()
                }
                return true
            }
        })

        //클릭시 해당 레시피 detail로 이동
        adapter.setOnItemClickListener(object: RecipeAdapter.OnItemClickListener{
            override fun onItemClick(view: View, position: Int) {
                val intent = Intent(this@MyRecipeActivity, UserRecipeDetailActivity::class.java)
                intent.putExtra("recipeDto", recipes[position])// 클릭한 RecyclerView 항목 위치의 dto 전달
                startActivityForResult(intent, REQ_DETAIL)      // 수정결과를 받아오도록 Activity 호출
            }
        })

        //등록하기 버튼 클릭시 등록하기 화면으로 이동
        binding.addButton.setOnClickListener{
            val intent = Intent(this@MyRecipeActivity, AddActivity::class.java)
            startActivity(intent)
        }

        //검색 버튼 클릭
        binding.btnSearch.setOnClickListener {
            val toSearch = binding.etRecipe.text.toString().trim()
            filterRecipes(toSearch) // 입력된 검색어로 필터링
        }

    }

    // 레시피 필터링 함수
    private fun filterRecipes(query: String) {
        if (query.isEmpty()) {
            filteredRecipes.clear()
            filteredRecipes.addAll(recipes) // 검색어가 없으면 전체 리스트로 되돌림
        } else {
            filteredRecipes.clear()
            val filteredList = recipes.filter { it.recipe.contains(query, ignoreCase = true) }
            Log.d(TAG, "filteredRecipes $filteredList")
            filteredRecipes.addAll(filteredList) // 검색어에 맞는 레시피만 추가
        }
        binding.rvRecipes.adapter?.notifyDataSetChanged() // RecyclerView 갱신
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            REQ_DETAIL -> {
                if(resultCode == RESULT_OK) {
                    recipes.clear()
                    recipes.addAll(recipeDao.getAllRecipes())
                    binding.rvRecipes.adapter?.notifyDataSetChanged()
                }
            }

        }
    }

}