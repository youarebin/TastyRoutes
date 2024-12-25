package ddwu.com.mobile.finalproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ddwu.com.mobile.finalproject.data.network.Naver.LocationDetail
import ddwu.com.mobile.finalproject.databinding.ActivityLocationDetailBinding

class LocationDetailActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityLocationDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val locDetail = intent.getSerializableExtra("locationDetail") as? LocationDetail

        binding.locName.text = locDetail?.title
        binding.locAddress.text = locDetail?.roadAddress
        binding.locCategory.text = locDetail?.category
        binding.locDescription.text = locDetail?.description
    }
}