package ddwu.com.mobile.finalproject.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ddwu.com.mobile.finalproject.data.network.RecipeDetail
import ddwu.com.mobile.finalproject.databinding.ListItemBinding

//레시피 전체 리스트 adapter
class RecipeAdapter(private var recipes: List<RecipeDetail>) : RecyclerView.Adapter<RecipeAdapter.RecipeHolder>() {

    override fun getItemCount(): Int {
        return recipes.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecipeHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        val recipe = recipes[position]

        holder.itemBinding.recipeName.text = recipe.recipeName
        holder.itemBinding.typeOfFood.text = recipe.recipeType
        Glide.with(holder.itemView.context)
            .load(recipe.manualImageUrl)
            .into(holder.itemBinding.imageView)
    }

    class RecipeHolder(val itemBinding: ListItemBinding) : RecyclerView.ViewHolder(itemBinding.root)
}