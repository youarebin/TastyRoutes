package ddwu.com.mobile.finalproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ddwu.com.mobile.finalproject.R
import ddwu.com.mobile.finalproject.data.network.Recipe.Instruction
import ddwu.com.mobile.finalproject.databinding.StepItemBinding

//레시피 조리 단계 adpater
class StepAdapter(
    private val steps: List<Instruction>
) : RecyclerView.Adapter<StepAdapter.StepViewHolder>() {

    override fun getItemCount(): Int = steps.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val binding = StepItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StepViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        val step = steps[position]

        // 텍스트가 null일 경우 빈 문자열 대신 설정
        holder.binding.stepText.text = step.text

        // 단계 이미지 설정
        val imageUrl = step.imageUrl.replace("http://", "https://")  ?: ""
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .into(holder.binding.stepImage)
    }

    // ViewHolder 클래스
    class StepViewHolder(val binding: StepItemBinding) : RecyclerView.ViewHolder(binding.root)
}
