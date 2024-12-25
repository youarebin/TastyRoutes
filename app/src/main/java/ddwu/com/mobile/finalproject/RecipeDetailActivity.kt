package ddwu.com.mobile.finalproject

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import ddwu.com.mobile.finalproject.data.database.RecipeDto
import ddwu.com.mobile.finalproject.data.network.Recipe.Instruction
import ddwu.com.mobile.finalproject.data.network.Recipe.RecipeDetail
import ddwu.com.mobile.finalproject.databinding.ActivityDetailRecipeBinding
import ddwu.com.mobile.finalproject.ui.StepAdapter

//레시피 상세 페이지
class RecipeDetailActivity : AppCompatActivity() {
    val TAG = "RecipeDetailActivity"

    val binding by lazy {
        ActivityDetailRecipeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //intent로 전달받은 레시피 정보 가져오기
        val recipeDetail = intent.getSerializableExtra("recipeDetail") as? RecipeDetail


        val imageUrl = recipeDetail?.manualImageUrl?.replace("http://", "https://")  ?: ""
        Glide.with(this)
            .load(imageUrl)
            .error(R.drawable.no_image)
            .into(binding.recipeImage)

        binding.tvRecipeName.text = recipeDetail?.recipeName
        binding.recipeType.text = recipeDetail?.recipeType
        binding.ingredients.text = recipeDetail?.recipeDetails
        binding.calorie.text = recipeDetail?.calorie
        binding.car.text = recipeDetail?.car
        binding.pro.text = recipeDetail?.pro
        binding.fat.text = recipeDetail?.fat
        binding.na.text = recipeDetail?.na

        // 레시피 단계와 이미지 목록을 RecyclerView로 표시
        val steps = recipeDetail?.getValidInstructions() ?: emptyList()
        Log.d(TAG, "steps $steps")
        setupStepsRecyclerView(steps)

        //뒤로가기 버튼 클릭
        binding.backButton.setOnClickListener {
            finish()
        }

        //sns 공유 버튼 클릭
        binding.sns.setOnClickListener{
            val imageUrl = recipeDetail?.manualImageUrl?.replace("http://", "https://") ?: ""
            val recipeName = recipeDetail?.recipeName ?: "레시피"
            val recipeDescription = recipeDetail?.recipeDetails ?: "레시피 설명"

            // SNS 공유를 위한 Intent
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"  // 이미지 공유
                putExtra(Intent.EXTRA_SUBJECT, recipeName)  // 제목
                putExtra(Intent.EXTRA_TEXT, "$recipeName\n$recipeDescription")  // 내용
                putExtra(Intent.EXTRA_STREAM, Uri.parse(imageUrl))  // 이미지 URL
            }

            // SNS 앱 선택 창 열기
            startActivity(Intent.createChooser(shareIntent, "SNS로 공유"))
        }

    }

    private fun setupStepsRecyclerView(steps: List<Instruction>) {
        // RecyclerView 설정
        val stepAdapter = StepAdapter(steps)

        val layoutManager = LinearLayoutManager(this).apply {
            orientation = LinearLayoutManager.VERTICAL
        }
        binding.stepsRecyclerView.layoutManager = layoutManager

        binding.stepsRecyclerView.adapter = stepAdapter  // 어댑터 설정
    }


}