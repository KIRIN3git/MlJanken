package kirin3.jp.mljanken.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.util.Log
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kirin3.jp.mljanken.util.TimeUtils.getCurrentTime

class HandHelper internal constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    companion object {

        // データーベースのバージョン
        const val DATABASE_VERSION = 1
        // データーベース名
        const val DATABASE_NAME = "Hands.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        var SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS " + HandColumns.TABLE + " (" +
                HandColumns._ID + " INTEGER PRIMARY KEY," +
                HandColumns.HAND_ID + " INTEGER," +
                HandColumns.RESULT_ID + " INTEGER," +
                HandColumns.WIN_NUM + " INTEGER," +
                HandColumns.LOSE_NUM + " INTEGER," +
                HandColumns.WIN_CHAIN_NUM + " INTEGER," +
                HandColumns.LOSE_CHAIN_NUM + " INTEGER," +
                HandColumns.CREATE_TIME + " INTEGER)"

        LOGD(TAG, "SQL_CREATE_ENTRIES:" + SQL_CREATE_ENTRIES)

        // テーブル作成
        db.execSQL(
            SQL_CREATE_ENTRIES
        )
    }

    override fun onUpgrade(db:SQLiteDatabase, oldVersion:Int, newVersion:Int) {
        var SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + HandColumns.TABLE

        // LOGD(TAG, "After upgrade logic, at version " + version)

        if (oldVersion != DATABASE_VERSION)
        {
            LOGD(TAG, "SQL_DELETE_ENTRIES:" + SQL_DELETE_ENTRIES)

            db.execSQL(
                SQL_DELETE_ENTRIES
            )
            onCreate(db)
        }
    }

    override fun onDowngrade(
        db: SQLiteDatabase,
        oldVersion: Int, newVersion: Int
    ) {
//        onUpgrade(db, oldVersion, newVersion)
    }

    fun saveData(db: SQLiteDatabase,context:Context, hand_id: Int, result_id: Int, win_num: Int, lose_num: Int, wind_chain_num: Int, lose_chain_num: Int) {

        val create_time = getCurrentTime(context)
        val values = ContentValues()
        values.put(HandColumns.HAND_ID, hand_id)
        values.put(HandColumns.RESULT_ID, result_id)
        values.put(HandColumns.WIN_NUM, win_num)
        values.put(HandColumns.LOSE_NUM, lose_num)
        values.put(HandColumns.WIN_CHAIN_NUM, wind_chain_num)
        values.put(HandColumns.LOSE_CHAIN_NUM, lose_chain_num)
        values.put(HandColumns.CREATE_TIME,create_time )

        LOGD(TAG,  "saveData:" + "HAND_ID " + hand_id + "RESULT_ID " + result_id + "WIN_NUM " + win_num + "LOSE_NUM " + lose_num
                + "WIN_CHAIN_NUM " + wind_chain_num + "LOSE_CHAIN_NUM " + lose_chain_num + "CREATE_TIME " + create_time )
        db.insert(HandColumns.TABLE, null, values)
    }

    fun readData(db:SQLiteDatabase?):Array<HandData?>?{

        if( db == null) return null

        val cursor = db.query(
            HandColumns.TABLE,
            null, null, null, null, null, null
        )

        var handArray = arrayOfNulls<HandData>(cursor.count)

        cursor.moveToFirst()

        for (i in 0 until cursor.count) {
            var hand = HandData(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6),cursor.getInt(7))
            handArray[i] = hand

            LOGD(TAG, "readData:" + "ID " + cursor.getInt(0) + "RESULT_ID " + cursor.getInt(1) + "RESULT_ID " + cursor.getInt(2) + "WIN_NUM " + cursor.getInt(3)
                    + "LOSE_NUM " + cursor.getInt(4) + "WIN_CHAIN_NUM " + cursor.getInt(5) + "LOSE_CHAIN_NUM " + cursor.getInt(6) + "CREATE_TIME " + cursor.getInt(7) )


            cursor.moveToNext()
        }

        cursor.close()

        return handArray
    }

    fun deleteDatabase(context: Context) {
        context.deleteDatabase(DATABASE_NAME)
    }


}