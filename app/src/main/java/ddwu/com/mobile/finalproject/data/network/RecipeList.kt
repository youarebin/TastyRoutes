package ddwu.com.mobile.finalproject.data.network

data class Root(
    val cookrcP01: COOKRCP01
)

data class COOKRCP01 (
    val row : List<Row>
)

data class Row (
    val MANUAL_IMG02: String,
    val RCP_NM: String,
    val RCP_PAT2: String,
    val RCP_PARTS_DTLS: String,
    val MANUAL: List<Manual>,
)

data class Manual (
    val manualText: String,
    val manualImage: String,
)