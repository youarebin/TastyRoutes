package ddwu.com.mobile.finalproject.data.network

data class RecipeRoot(
    val cookrcP01: RecipeData
)

data class RecipeData (
    val row : List<RecipeDetail>
)

data class RecipeDetail (
    val manualImageUrl: String, //ATT_FILE_NO_MAIN(소)
    val recipeName: String, //RCP_NM
    val recipeType: String, //RCP_PAT2
    val recipeDetails: String, //RCP_PARTS_DTLS
    val hashTag: String, //HASH_TAG
    val dd: Int, //INFO_ENG
    val instructions: List<Instruction>, //레시피 단계(text,img)
)

data class Instruction(
    val text: String, //MANUAL01, MANUAL02 ...
    val imageUrl: String //MANUAL_IMG01, MANUAL_IMG02 ...
)