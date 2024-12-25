package ddwu.com.mobile.finalproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobile.finalproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //내가 등록한 레시피 조회 버튼 클릭 -> MyRecipeActiviy 이동
        binding.myRecipeBtn.setOnClickListener {
            Log.d("MainActivity", "내가 등록한 레시피 조회 버튼 클릭됨")
            val intent = Intent(this@MainActivity, MyRecipeActivity::class.java)
            startActivity(intent)
        }
        //api 레시피 조회 클릭 -> RecipeListActivity
        binding.recipeListBtn.setOnClickListener{
            Log.d("MainActivity", "레시피 조회 버튼 클릭됨")
            val intent = Intent(this@MainActivity, RecipeListActivity::class.java)
            startActivity(intent)
        }
        //맛집 찾기 버튼 클릭 -> MapActivity
        binding.RestaurantBtn.setOnClickListener{
            Log.d("MainActivity", "맛집 찾기 버튼 클릭됨")
            val intent = Intent(this@MainActivity, MapActivity::class.java)
            startActivity(intent)
        }

        //즐겨찾기 버튼 클릭 ->

    }
}