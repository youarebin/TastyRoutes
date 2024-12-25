package ddwu.com.mobile.finalproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ddwu.com.mobile.finalproject.R
import ddwu.com.mobile.finalproject.data.database.RecipeDto
import ddwu.com.mobile.finalproject.data.network.Recipe.RecipeDetail
import ddwu.com.mobile.finalproject.databinding.ListItemBinding
import ddwu.com.mobile.finalproject.ui.RecipeAdapter.RecipeDtoViewHolder
import ddwu.com.mobile.finalproject.ui.RecipeDetailAdapter.RecipeDetailViewHolder

//레시피 전체 리스트 adapter
class RecipeAdapter(
    private var recipeDetails: List<RecipeDto> // RecipeDto만 받음
) : RecyclerView.Adapter<RecipeAdapter.RecipeDtoViewHolder>() {

    override fun getItemCount(): Int {
        return recipeDetails.size
    }

    // ViewHolder 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeDtoViewHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeDtoViewHolder(itemBinding)  // RecipeDto만 사용
    }

    override fun onBindViewHolder(holder: RecipeDtoViewHolder, position: Int) {
        val recipe = recipeDetails[position]  // position에 해당하는 RecipeDto 가져오기

        holder.itemBinding.recipeName.text = recipe.recipe
        holder.itemBinding.typeOfFood.text = recipe.type
        Glide.with(holder.itemView.context)
            .load(R.drawable.no_image)
            .into(holder.itemBinding.imageView)

        // 클릭 이벤트 처리
        holder.itemView.setOnClickListener {
            listener?.onItemClick(it, position)  // itemView에서 클릭 리스너 설정
        }
        // 롱클릭 이벤트 처리
        holder.itemView.setOnLongClickListener {
            longClickListener?.onItemLongClick(it, position) ?: false
        }
    }

    class RecipeDtoViewHolder(val itemBinding: ListItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

    // 클릭 리스너 설정
    var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener?) {
        this.listener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    //롱클릭 리스너 설정
    var longClickListener: OnItemLongClickListener? = null
    fun setOnItemLongClickListener(listener: OnItemLongClickListener?) {
        this.longClickListener = listener
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(view: View, position: Int): Boolean
    }

}
