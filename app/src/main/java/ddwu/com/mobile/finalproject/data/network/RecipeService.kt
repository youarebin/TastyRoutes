package ddwu.com.mobile.finalproject.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeService {
    @GET("{keyId}/{serviceId}/{dataType}/{startIdx}/{endIdx}")
    suspend fun getRecipes(
        @Path("keyId") keyId: String,
        @Path("serviceId") serviceId: String,
        @Path("dataType") dataType:String,
        @Path("startIdx") startIdx: String,
        @Path("endIdx") endIdx: String,
    ) : Root
}