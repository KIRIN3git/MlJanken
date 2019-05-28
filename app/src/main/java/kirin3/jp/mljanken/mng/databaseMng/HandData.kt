package kirin3.jp.mljanken.mng.databaseMng

// DBから取得したデータ格納用クラス
data class HandData(
    var id: Int,
    var hand_id: Int,
    var result_id: Int,
    var mode_id: Int,
    var win_num: Int,
    var drow_num: Int,
    var lose_num: Int,
    var win_chain_num: Int,
    var lose_chain_num: Int,
    var create_time1: Int,
    var create_time2: String
)

