package ddwu.com.mobile.finalproject.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ddwu.com.mobile.finalproject.R
import ddwu.com.mobile.finalproject.data.RecipeRepository
import ddwu.com.mobile.finalproject.data.network.Recipe.RecipeDetail
import ddwu.com.mobile.finalproject.databinding.ListItemBinding

class RecipeDetailAdapter(
    private var recipeDetails: List<RecipeDetail>
) : RecyclerView.Adapter<RecipeDetailAdapter.RecipeDetailViewHolder>() {

    // 데이터 업데이트 메서드 추가
    fun updateRecipes(newRecipes: List<RecipeDetail>) {
        recipeDetails = newRecipes
        notifyDataSetChanged() // RecyclerView에 변경 알림
    }

    override fun getItemCount(): Int = recipeDetails.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeDetailViewHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeDetailViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecipeDetailViewHolder, position: Int) {
        val recipe = recipeDetails?.get(position)
        if(recipe == null) {Log.d("onBindViewHolder", "null")}

        holder.itemBinding.recipeName.text = recipe?.recipeName
        holder.itemBinding.typeOfFood.text = recipe?.recipeType

        if(recipe?.hashTag != null) {
            holder.itemBinding.hashTag.text = "#${recipe?.hashTag}"
        }

        val imageUrl = recipe?.manualImageUrl?.replace("http://", "https://")
        Glide.with(holder.itemView.context)
            .load(imageUrl)
            .error(R.drawable.no_image)
            .into(holder.itemBinding.imageView)

        // 클릭 리스너 설정
        holder.itemView.setOnClickListener {
            listener?.onItemClick(it, position)
        }
    }

    class RecipeDetailViewHolder(val itemBinding: ListItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

    // 클릭 리스너 설정
    var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

}
