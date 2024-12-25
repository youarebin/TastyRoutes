package ddwu.com.mobile.finalproject.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class RecipeDBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, 1) {
    val TAG = "RecipeDBHelper"

    companion object {
        const val DB_NAME = "recipe_db"
        const val TABLE_NAME = "recipe_table"
        const val COL_RECIPE = "recipe_name"
        const val COL_TYPE = "recipe_type"
        const val COL_INGREDIENT = "ingredients"
        const val COL_STEP = "cooking_steps"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ( ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COL_RECIPE TEXT, $COL_TYPE text, $COL_INGREDIENT TEXT, $COL_STEP TEXT)"

        db?.execSQL(CREATE_TABLE)
        /*샘플 데이터*/
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '스파게티 볼로네제', '메인 요리', '스파게티, 다진 고기, 토마토 소스, 마늘, 양파, 올리브 오일', '1. 스파게티를 끓인다. 2. 볼로네제 소스를 준비한다. 3. 스파게티와 소스를 합친다.')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '시저 샐러드', '사이드 요리', '상추, 닭고기, 크루통, 시저 드레싱, 파마산 치즈', '1. 닭고기를 굽는다. 2. 상추에 드레싱을 뿌린다. 3. 닭고기와 크루통을 넣는다. 4. 파마산 치즈를 뿌린다.')")
        db?.execSQL("INSERT INTO $TABLE_NAME VALUES (NULL, '초콜릿 케이크', '디저트', '밀가루, 코코아 가루, 계란, 설탕, 버터, 베이킹 파우더', '1. 건조 재료를 섞는다. 2. 액체 재료를 추가한다. 3. 350°F에서 30분간 굽는다.')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE ="DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }
}