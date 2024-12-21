package ddwu.com.mobile.finalproject.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeApi {
    @GET("{keyId}/{serviceId}/{dataType}/{startIdx}/{endIdx}")
    suspend fun getRecipes(
        @Path("keyId") keyId: String,
        @Path("serviceId") serviceId: String,
        @Path("dataType") dataType:String,
        @Path("startIdx") startIdx: Int,
        @Path("endIdx") endIdx: Int,
    ) : RecipeRoot
}