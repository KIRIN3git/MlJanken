package kirin3.jp.mljanken.data

import android.provider.BaseColumns

class HandColumns : BaseColumns {
    companion object {

        // テーブル名
        val TABLE = "hands"

        //// カラム
        // 主キー連番
        val _ID = "_id"
        // 手のID(1:グー,2:チョキ,3:パー)
        val HAND_ID = "hand_id"
        // 結果ID(1:勝ち,2:あいこ,3:負け)
        val RESULT_ID = "result_id"
        // 勝ち数
        val WIN_NUM = "win_num"
        // 負け数
        val LOSE_NUM ="lose_num"
        // 連勝数
        val WIN_CHAIN_NUM = "win_chain_num"
        // 連敗数
        val LOSE_CHAIN_NUM = "lose_chain_num"
        // アップデート時間
        val CREATE_TIME = "create_time"
    }
}