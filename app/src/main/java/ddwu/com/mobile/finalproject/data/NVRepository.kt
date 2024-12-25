package ddwu.com.mobile.finalproject.data

import ddwu.com.mobile.finalproject.data.network.Naver.LocationDetail
import ddwu.com.mobile.finalproject.data.network.Naver.NVService

class NVRepository(private val nvService : NVService) {

    suspend fun getLocations(query: String) : List<LocationDetail>?{
        return nvService.getLocationData(query)
    }
}