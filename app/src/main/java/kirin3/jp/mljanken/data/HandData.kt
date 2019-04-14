package kirin3.jp.mljanken.data

data class HandData(
    var id: Int,
    var hand_id: Int,
    var result_id: Int,
    var win_num: Int,
    var lose_num: Int,
    var win_chain_num: Int,
    var lose_chain_num: Int,
    var create_time: Int)