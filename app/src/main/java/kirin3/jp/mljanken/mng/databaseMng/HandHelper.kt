package kirin3.jp.mljanken.mng.databaseMng

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteCursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kirin3.jp.mljanken.game.GameData
import kirin3.jp.mljanken.util.CalculationUtils
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kirin3.jp.mljanken.util.TimeUtils


class HandHelper internal constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    companion object {
        // データーベースのバージョン
        const val DATABASE_VERSION = 3
        // データーベース名
        const val DATABASE_NAME = "Hands.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        var SQL_CREATE_ENTRIES = "CREATE TABLE IF NOT EXISTS " + HandColumns.TABLE + " (" +
                HandColumns._ID + " INTEGER PRIMARY KEY," +
                HandColumns.HAND_ID + " INTEGER," +
                HandColumns.RESULT_ID + " INTEGER," +
                HandColumns.MODE_ID + " INTEGER," +
                HandColumns.WIN_NUM + " INTEGER," +
                HandColumns.DROW_NUM + " INTEGER," +
                HandColumns.LOSE_NUM + " INTEGER," +
                HandColumns.WIN_CHAIN_NUM + " INTEGER," +
                HandColumns.LOSE_CHAIN_NUM + " INTEGER," +
                HandColumns.CREATE_TIME1 + " INTEGER," +
                HandColumns.CREATE_TIME2 + " STRING)"

        LOGD(TAG, "SQL_CREATE_ENTRIES:" + SQL_CREATE_ENTRIES)

        // テーブル作成
        db.execSQL(
            SQL_CREATE_ENTRIES
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        var SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + HandColumns.TABLE

        if (oldVersion != DATABASE_VERSION) {
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
        onUpgrade(db, oldVersion, newVersion)
    }

    fun saveData(
        db: SQLiteDatabase,
        context: Context,
        hand_id: Int,
        result_id: Int,
        mode_id: Int,
        win_num: Int,
        drow_num: Int,
        lose_num: Int,
        win_chain_num: Int,
        lose_chain_num: Int
    ) {

        val create_time1 = TimeUtils.getCurrentTime(context)
        val create_time2 = TimeUtils.formatDateTime(context, TimeUtils.getCurrentTime(context))
        val values = ContentValues()
        values.put(HandColumns.HAND_ID, hand_id)
        values.put(HandColumns.RESULT_ID, result_id)
        values.put(HandColumns.MODE_ID, mode_id)
        values.put(HandColumns.WIN_NUM, win_num)
        values.put(HandColumns.DROW_NUM, drow_num)
        values.put(HandColumns.LOSE_NUM, lose_num)
        values.put(HandColumns.WIN_CHAIN_NUM, win_chain_num)
        values.put(HandColumns.LOSE_CHAIN_NUM, lose_chain_num)
        values.put(HandColumns.CREATE_TIME1, create_time1)
        values.put(HandColumns.CREATE_TIME2, create_time2)

        LOGD(
            TAG,
            "saveData:" + "HAND_ID " + hand_id + "RESULT_ID " + result_id + "MODE_ID " + mode_id + "WIN_NUM " + win_num + "DROW_NUM " + drow_num + "LOSE_NUM " + lose_num
                    + "WIN_CHAIN_NUM " + win_chain_num + "LOSE_CHAIN_NUM " + lose_chain_num + "CREATE_TIME1 " + create_time1 + "CREATE_TIME2 " + create_time2
        )
        db.insert(HandColumns.TABLE, null, values)
    }

    fun readData(db: SQLiteDatabase?): Array<HandData?>? {

        if (db == null) return null

        val cursor = db.query(
            HandColumns.TABLE,
            null, null, null, null, null, null
        )

        var handArray = arrayOfNulls<HandData>(cursor.count)

        cursor.moveToFirst()

        for (i in 0 until cursor.count) {
            var hand = HandData(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getInt(3),
                cursor.getInt(4),
                cursor.getInt(5),
                cursor.getInt(6),
                cursor.getInt(7),
                cursor.getInt(8),
                cursor.getInt(9),
                cursor.getString(10)
            )
            handArray[i] = hand

            LOGD(
                TAG,
                "readData:" + "ID " + cursor.getInt(0) + " HAND_ID " + cursor.getInt(1) + " RESULT_ID " + cursor.getInt(
                    2
                ) + " MODE_ID " + cursor.getInt(3)
                        + " WIN_NUM " + cursor.getInt(4) + " DROW_NUM " + cursor.getInt(5) + " LOSE_NUM " + cursor.getInt(
                    6
                ) + " WIN_CHAIN_NUM " + cursor.getInt(7) + " LOSE_CHAIN_NUM " + cursor.getInt(8)
                        + " CREATE_TIME1 " + cursor.getInt(9) + " CREATE_TIME2 " + cursor.getString(10)
            )

            cursor.moveToNext()
        }

        cursor.close()

        return handArray
    }


    /**
     * 最もユーザーが選択している手を取得
     *
     * @param db SQLiteDatabase
     * @return 最も選択しているhand_id(0:なし,1:グー,2:チョキ,3:パー)
     * （注意）同一数の場合、上から優先される
     */
    fun getMostChoice(db: SQLiteDatabase?): Int {

        LOGD(TAG, "getMostChoice")

        if (db == null) return 0

        val sql = "select hand_id from " + HandColumns.TABLE + ";"
        val cursor = db.rawQuery(sql, null) as SQLiteCursor


        LOGD(TAG, "SQL:" + sql)

        cursor.moveToFirst()

        var guu_num = 0
        var choki_num = 0
        var paa_num = 0
        var result_id = 0

        for (i in 0 until cursor.count) {

            LOGD(TAG, "readData:" + "hand_id " + cursor.getInt(0))

            if (cursor.getInt(0) == GameData.GUU) guu_num++
            else if (cursor.getInt(0) == GameData.CHOKI) choki_num++
            else if (cursor.getInt(0) == GameData.PAA) paa_num++

            cursor.moveToNext()
        }

        if (0 < guu_num) result_id = GameData.GUU
        if (guu_num < choki_num) result_id = GameData.CHOKI
        if (guu_num < paa_num && choki_num < paa_num) result_id = GameData.PAA

        cursor.close()

        LOGD(TAG, "result_id:" + result_id)

        return result_id
    }


    /**
     * 最もユーザーが連勝時に選択している手を取得
     *
     * @param db SQLiteDatabase
     * @return 最も選択しているhand_id(0:なし,1:グー,2:チョキ,3:パー)
     * （注意）同一数の場合、上から優先される
     */
    fun getMostChainChoice(db: SQLiteDatabase?): Int {

        LOGD(TAG, "getMostChainChoice")

        if (db == null) return 0

        val sql = "select hand_id from " + HandColumns.TABLE + " where win_chain_num > 0;"
        val cursor = db.rawQuery(sql, null) as SQLiteCursor


        LOGD(TAG, "SQL:" + sql)

        cursor.moveToFirst()

        var guu_num = 0
        var choki_num = 0
        var paa_num = 0
        var result_id = 0

        for (i in 0 until cursor.count) {

            if (cursor.getInt(0) == GameData.GUU) guu_num++
            else if (cursor.getInt(0) == GameData.CHOKI) choki_num++
            else if (cursor.getInt(0) == GameData.PAA) paa_num++

            cursor.moveToNext()
        }

        if (0 < guu_num) result_id = GameData.GUU
        if (guu_num < choki_num) result_id = GameData.CHOKI
        if (guu_num < paa_num && choki_num < paa_num) result_id = GameData.PAA

        cursor.close()

        LOGD(TAG, "result_id:" + result_id)

        return result_id
    }

    /**
     * ユーザーが最初に出したhand_idを取得
     *
     * @param db SQLiteDatabase
     * @return 最も選択しているhand_id(0:なし,1:グー,2:チョキ,3:パー)
     * （注意）同一数の場合、上から優先される
     */
    fun getFirstChoice(db: SQLiteDatabase?): Int {

        LOGD(TAG, "getFirstChoice")

        if (db == null) return 0

        val sql = "select hand_id from " + HandColumns.TABLE + " order by _id limit 1;"
        val cursor = db.rawQuery(sql, null) as SQLiteCursor

        LOGD(TAG, "SQL:" + sql)

        cursor.moveToFirst()

        var guu_num = 0
        var choki_num = 0
        var paa_num = 0
        var result_id = 0

        for (i in 0 until cursor.count) {

            if (cursor.getInt(0) == GameData.GUU) guu_num++
            else if (cursor.getInt(0) == GameData.CHOKI) choki_num++
            else if (cursor.getInt(0) == GameData.PAA) paa_num++

            cursor.moveToNext()
        }

        if (0 < guu_num) result_id = GameData.GUU
        if (guu_num < choki_num) result_id = GameData.CHOKI
        if (guu_num < paa_num && choki_num < paa_num) result_id = GameData.PAA

        cursor.close()

        LOGD(TAG, "result_id:" + result_id)

        return result_id
    }

    /**
     * 最もユーザーに勝率が高いモードを取得
     *
     * @param db SQLiteDatabase
     * @return 最も選択しているhand_id(0:なし,1:グー,2:チョキ,3:パー)
     * （注意）同一数の場合、上から優先される
     */
    fun getExcellenceMode(db: SQLiteDatabase?): Int {

        LOGD(TAG, "getExcellenceMode")

        if (db == null) return 0

        var most_probability = 0.0
        var buf_probability = 0.0
        var most_mode = 0

        readData(db)

        for (i in 1 until GameData.MODE_NUM + 1) {
            buf_probability = getModeProbability(db, i)
            if (most_probability < buf_probability) {
                most_probability = buf_probability
                most_mode = i
            }
        }

        LOGD(TAG, "most_mode:" + most_mode)

        return most_mode
    }


    /**
     * モードの勝率を取得
     *
     * @param db SQLiteDatabase
     * @param mode 勝率を取得するモード
     * @return 勝率
     * （注意）同一数の場合は上から優先される、ユーザーの勝率ではなく機械の勝率が返却される
     */
    fun getModeProbability(db: SQLiteDatabase?, mode: Int): Double {
        if (db == null) return 0.0

        val sql = "select result_id from " + HandColumns.TABLE + " where mode_id = " + mode + ";"
        val cursor = db.rawQuery(sql, null) as SQLiteCursor


        LOGD(TAG, "SQL:" + sql)

        cursor.moveToFirst()

        var win_num = 0
        var lose_num = 0

        for (i in 0 until cursor.count) {
            LOGD(TAG, "SQL:" + cursor.getInt(0));
            if (cursor.getInt(0) == GameData.WIN) win_num++
            else if (cursor.getInt(0) == GameData.LOSE) lose_num++

            cursor.moveToNext()
        }
        cursor.close()

        var result = CalculationUtils.getProbability2(win_num,lose_num)

        LOGD(TAG, "getModeProbability mode:$mode Probability:$result")
        return result
    }


    fun deleteDatabase(context: Context) {
        context.deleteDatabase(DATABASE_NAME)
    }


}