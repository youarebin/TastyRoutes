package ddwu.com.mobile.finalproject.data.database

import java.io.Serializable

class RecipeDto(
    val id: Int,
    var recipe: String,
    var type: String,
    var ingredient : String,
    var step : String) : Serializable {
    override fun toString() = "$id - $recipe ( $type )"
}