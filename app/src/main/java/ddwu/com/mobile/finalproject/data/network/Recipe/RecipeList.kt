package ddwu.com.mobile.finalproject.data.network.Recipe

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RecipeRoot(
    val COOKRCP01: RecipeData
)

data class RecipeData (
    val row : List<RecipeDetail>
)

data class RecipeDetail (
    @SerializedName("ATT_FILE_NO_MAIN")
    val manualImageUrl: String, //음식 사진: ATT_FILE_NO_MAIN(소)
    @SerializedName("RCP_NM")
    val recipeName: String, //RCP_NM
    @SerializedName("RCP_PAT2")
    val recipeType: String, //요리종류(국,반찬 등):RCP_PAT2
    @SerializedName("RCP_PARTS_DTLS")
    val recipeDetails: String, //재료: RCP_PARTS_DTLS
    @SerializedName("HASH_TAG")
    val hashTag: String, //HASH_TAG
    @SerializedName("INFO_ENG")
    val calorie: String, //열량: INFO_ENG
    @SerializedName("INFO_CAR")
    val car : String, //탄수화물
    @SerializedName("INFO_PRO")
    val pro : String, //단백질
    @SerializedName("INFO_FAT")
    val fat: String, //지방
    @SerializedName("INFO_NA")
    val na: String, //나트퓸

    // 메뉴얼 텍스트
    val MANUAL01: String?, val MANUAL_IMG01: String?,
    val MANUAL02: String?, val MANUAL_IMG02: String?,
    val MANUAL03: String?, val MANUAL_IMG03: String?,
    val MANUAL04: String?, val MANUAL_IMG04: String?,
    val MANUAL05: String?, val MANUAL_IMG05: String?,
    val MANUAL06: String?, val MANUAL_IMG06: String?,
    val MANUAL07: String?, val MANUAL_IMG07: String?,
    val MANUAL08: String?, val MANUAL_IMG08: String?,
    val MANUAL09: String?, val MANUAL_IMG09: String?,
    val MANUAL10: String?, val MANUAL_IMG10: String?,
    val MANUAL11: String?, val MANUAL_IMG11: String?,
    val MANUAL12: String?, val MANUAL_IMG12: String?,
    val MANUAL13: String?, val MANUAL_IMG13: String?,
    val MANUAL14: String?, val MANUAL_IMG14: String?,
    val MANUAL15: String?, val MANUAL_IMG15: String?,
    val MANUAL16: String?, val MANUAL_IMG16: String?,
    val MANUAL17: String?, val MANUAL_IMG17: String?,
    val MANUAL18: String?, val MANUAL_IMG18: String?,
    val MANUAL19: String?, val MANUAL_IMG19: String?,
    val MANUAL20: String?, val MANUAL_IMG20: String?
) : Serializable {
    // 동적으로 MANUAL01, MANUAL02, ... 과 MANUAL_IMG01, MANUAL_IMG02, ...를 처리
    fun getValidInstructions(): List<Instruction> {
        val instructionsList = mutableListOf<Instruction>()

        // MANUAL01부터 MANUAL20까지 확인
        for (i in 1..20) {
            val manualText = this.javaClass.getDeclaredField("MANUAL${"%02d".format(i)}").apply { isAccessible = true }.get(this) as? String
            val manualImage = this.javaClass.getDeclaredField("MANUAL_IMG${"%02d".format(i)}").apply { isAccessible = true }.get(this) as? String

            // 텍스트와 이미지가 모두 null이 아니면 리스트에 추가
            if (!manualText.isNullOrBlank() && !manualImage.isNullOrBlank()) {
                instructionsList.add(Instruction(manualText, manualImage))
            } else {
                // 텍스트나 이미지가 존재하는 경우만 추가
                manualText?.takeIf { it.isNotBlank() }?.let { instructionsList.add(Instruction(it, manualImage ?: "")) }
                manualImage?.takeIf { it.isNotBlank() }?.let { instructionsList.add(Instruction(manualText ?: "", it)) }
            }
        }
        return instructionsList
    }
}

data class Instruction(
    val text: String, //MANUAL01, MANUAL02 ...
    val imageUrl: String //단계별 이미지: MANUAL_IMG01, MANUAL_IMG02 ...
)