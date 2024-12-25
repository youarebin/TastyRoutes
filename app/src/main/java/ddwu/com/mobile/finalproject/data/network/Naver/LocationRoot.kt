package ddwu.com.mobile.finalproject.data.network.Naver

import java.io.Serializable

data class LocationRoot (
    val items : List<LocationDetail>
)

data class LocationDetail (
    val title : String,
    val category : String,
    val description : String,
    val roadAddress : String,
    val mapx : Int,
    val mapy : Int
) : Serializable
{
    override fun toString(): String {
        return "$title - $category - $roadAddress  - $description - $mapx - $mapy"
    }
}