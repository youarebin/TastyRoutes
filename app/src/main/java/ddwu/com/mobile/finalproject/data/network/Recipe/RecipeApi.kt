package ddwu.com.mobile.finalproject.data.network.Recipe

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {
    //전체 레시피 받아오기
    @GET("{keyId}/{serviceId}/{dataType}/{startIdx}/{endIdx}")
    suspend fun getRecipes(
        @Path("keyId") keyId: String,
        @Path("serviceId") serviceId: String,
        @Path("dataType") dataType:String,
        @Path("startIdx") startIdx: Int,
        @Path("endIdx") endIdx: Int,
    ) : RecipeRoot

    //검색 재료에 따른 레시피 받아오기
    @GET("{keyId}/{serviceId}/{dataType}/{startIdx}/{endIdx}/RCP_PARTS_DTLS={ingredient}")
    suspend fun getRecipesWithIngredient(
        @Path("keyId") keyId: String,
        @Path("serviceId") serviceId: String,
        @Path("dataType") dataType:String,
        @Path("startIdx") startIdx: Int,
        @Path("endIdx") endIdx: Int,
        @Path("ingredient") ingredient: String
    ) : RecipeRoot
}