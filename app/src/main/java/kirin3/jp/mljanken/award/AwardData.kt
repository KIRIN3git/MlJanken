package kirin3.jp.mljanken.award

data class AwardData(
    val win_num_all_rank_user: Int,
    val win_num_prefecture_rank_user: Int,
    val win_num_gender_rank_user: Int,
    val win_num_age_rank_user: Int,
    val probability_all_rank_user: Int,
    val probability_prefecture_rank_user: Int,
    val probability_gender_rank_user: Int,
    val probability_age_rank_user: Int,
    val max_chain_win_num_all_rank_user: Int,
    val max_chain_win_num_prefecture_rank_user: Int,
    val max_chain_win_num_gender_rank_user: Int,
    val max_chain_win_num_age_rank_user: Int,

    val win_num_all_rank_everyone: Int,
    val win_num_prefecture_rank_everyone: Int,
    val win_num_gender_rank_everyone: Int,
    val win_num_age_rank_everyone: Int,
    val probability_all_rank_everyone: Int,
    val probability_prefecture_rank_everyone: Int,
    val probability_gender_rank_everyone: Int,
    val probability_age_rank_everyone: Int,
    val max_chain_win_num_all_rank_everyone: Int,
    val max_chain_win_num_prefecture_rank_everyone: Int,
    val max_chain_win_num_gender_rank_everyone: Int,
    val max_chain_win_num_age_rank_everyone: Int
)


