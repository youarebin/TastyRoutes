package ddwu.com.mobile.finalproject.data.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns

class RecipeDao(val context: Context) {
    private val helper = RecipeDBHelper(context)

    //레시피 수정
    fun updateRecipe(dto : RecipeDto) : Int {
        val db = helper.writableDatabase

        val updateValue = ContentValues()
        updateValue.put(RecipeDBHelper.COL_RECIPE, dto.recipe)
        updateValue.put(RecipeDBHelper.COL_TYPE, dto.type)
        updateValue.put(RecipeDBHelper.COL_INGREDIENT, dto.ingredient)
        updateValue.put(RecipeDBHelper.COL_STEP, dto.step)

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(dto.id.toString())

        val resultCount = db.update(RecipeDBHelper.TABLE_NAME, updateValue, whereClause, whereArgs)

        helper.close()

        return resultCount
    }

    //레시피 삭제
    fun deleteRecipe(dto : RecipeDto) : Int {
        val db = helper.writableDatabase

        val whereClause = "${BaseColumns._ID}=?"
        val whereArgs = arrayOf(dto.id.toString())

        return db.delete(RecipeDBHelper.TABLE_NAME, whereClause, whereArgs)
    }

    //모든 레시피 가져오기
    @SuppressLint("Range")
    fun getAllRecipes() : ArrayList<RecipeDto> {
        val db = helper.readableDatabase
        val cursor = db.query(RecipeDBHelper.TABLE_NAME, null, null, null, null, null, null)
        val recipes = arrayListOf<RecipeDto>()

        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndex(BaseColumns._ID))
                val recipe = getString(getColumnIndex(RecipeDBHelper.COL_RECIPE))
                val type = getString(getColumnIndex(RecipeDBHelper.COL_TYPE))
                val ingredient = getString(getColumnIndex(RecipeDBHelper.COL_INGREDIENT))
                val step = getString(getColumnIndex(RecipeDBHelper.COL_STEP))

                val dto = RecipeDto(id, recipe, type, ingredient, step)

                recipes.add(dto)
            }
        }
        return recipes
    }
}