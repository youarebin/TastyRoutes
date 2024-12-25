package ddwu.com.mobile.finalproject.data.network.Naver

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NVLocationApi {
    @GET("v1/search/local.json")
    suspend fun getLocationByKeyword(
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Header("X-Naver-Client-Id") clientID: String,
        @Header("X-Naver-Client-Secret") clientSecret: String,
    ) : LocationRoot
}