package kirin3.jp.mljanken.totalization

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import kirin3.jp.mljanken.award.AwardFragment
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.SettingsUtils

object TotalizationCoudFirestoreHelper {

    private val TAG = LogUtils.makeLogTag(TotalizationCoudFirestoreHelper::class.java)

    var sex_probability = ArrayList<Float>()
    var age_probability = ArrayList<Float>()
    var prefecture_probability = ArrayList<Float>()


    /**
     * CroudFirestoreから全データを取得し、Awardに必要なデータを取得する。
     * 取得後は、Awardのデータ表示機能を呼び出す。
     */
    fun getTotalizationData(db: FirebaseFirestore, collection:String, context: Context):Int{

        var win_sex_probability = ArrayList<Int>(2)
        var win_age_probability = ArrayList<Int>(9)
        var win_prefecture_probability = ArrayList<Int>(47)

        var lose_sex_probability = ArrayList<Int>(2)
        var lose_age_probability = ArrayList<Int>(9)
        var lose_prefecture_probability = ArrayList<Int>(47)

        db.collection(collection)
            .orderBy(CloudFirestoreHelper.UserItem::b2_win_num.name)
            .get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.toObjects(CloudFirestoreHelper.UserItem::class.java) != null) {
                        val userList = document.toObjects(CloudFirestoreHelper.UserItem::class.java)
                        LogUtils.LOGD(TAG, "getDataOrderByLimit")
                        LogUtils.LOGD(TAG, "userList.size " + userList.size)
                        for(i in 0 until  userList.size){

                            LogUtils.LOGD(TAG, "userList.get(" + i + ").win_num " + userList.get(i).b2_win_num)

                            if(userList.get(i).a1_sex == 1){
                                // 男性勝利数
                                win_sex_probability[0] += userList.get(i).b2_win_num
                                // 男性負け数
                                lose_sex_probability[0] += userList.get(i).b4_lose_num
                            }
                            else if(userList.get(i).a1_sex == 2){
                                // 女性勝利数
                                win_sex_probability[1] += userList.get(i).b2_win_num
                                // 女性負け数
                                lose_sex_probability[1] += userList.get(i).b4_lose_num
                            }

                            if(userList.get(i).a1_sex.equals(SettingsUtils.getSettingRadioIdSex(context))){
                                win_num_sex_rank_everyone++
                            }

                            if(userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))){
                                win_num_age_rank_everyone++
                            }

                            win_num_all_rank_everyone++


                            LogUtils.LOGD(
                                TAG,
                                "userList.get(" + i + ").b5_probability " + userList.get(i).b5_probability
                            )

                            if(userList.get(i).a3_prefecture.equals(SettingsUtils.getSettingRadioIdPrefecture(context))){
                                probability_prefecture_rank_everyone++
                            }

                            if(userList.get(i).a1_sex.equals(SettingsUtils.getSettingRadioIdSex(context))){
                                probability_sex_rank_everyone++
                            }

                            if(userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))){
                                probability_age_rank_everyone++
                            }

                            probability_all_rank_everyone++


                            LogUtils.LOGD(
                                TAG,
                                "userList.get(" + i + ").max_chain_win_num " + userList.get(i).b6_max_chain_win_num
                            )

                            if(userList.get(i).a3_prefecture.equals(SettingsUtils.getSettingRadioIdPrefecture(context))){
                                max_chain_win_num_prefecture_rank_everyone++
                            }

                            if(userList.get(i).a1_sex.equals(SettingsUtils.getSettingRadioIdSex(context))){
                                max_chain_win_num_sex_rank_everyone++
                            }

                            if(userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))){
                                max_chain_win_num_age_rank_everyone++
                            }

                            max_chain_win_num_all_rank_everyone++
                        }

                        win_num_prefecture_rank_user = win_num_prefecture_rank_everyone + 1
                        win_num_sex_rank_user = win_num_sex_rank_everyone + 1
                        win_num_age_rank_user = win_num_age_rank_everyone + 1
                        win_num_all_rank_user = win_num_all_rank_everyone + 1

                        probability_prefecture_rank_user = probability_prefecture_rank_everyone + 1
                        probability_sex_rank_user = probability_sex_rank_everyone + 1
                        probability_age_rank_user = probability_age_rank_everyone + 1
                        probability_all_rank_user = probability_all_rank_everyone + 1


                        max_chain_win_num_prefecture_rank_user = max_chain_win_num_prefecture_rank_everyone + 1
                        max_chain_win_num_sex_rank_user = max_chain_win_num_sex_rank_everyone + 1
                        max_chain_win_num_age_rank_user = max_chain_win_num_age_rank_everyone + 1
                        max_chain_win_num_all_rank_user = max_chain_win_num_all_rank_everyone + 1

                        for(i in 0 until  userList.size){

                            LogUtils.LOGD(TAG, "userList.get(" + i + ").b2_win_num " + userList.get(i).b2_win_num)

                            if(userList.get(i).a3_prefecture.equals(SettingsUtils.getSettingRadioIdPrefecture(context))){
                                if(userList.get(i).b2_win_num <= SettingsUtils.getSettingWinNum(context)) win_num_prefecture_rank_user--
                            }

                            if(userList.get(i).a1_sex.equals(SettingsUtils.getSettingRadioIdSex(context))){
                                if(userList.get(i).b2_win_num <= SettingsUtils.getSettingWinNum(context)) win_num_sex_rank_user--
                            }

                            if(userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))){
                                if(userList.get(i).b2_win_num <= SettingsUtils.getSettingWinNum(context)) win_num_age_rank_user--
                            }

                            if(userList.get(i).b2_win_num <= SettingsUtils.getSettingWinNum(context)) win_num_all_rank_user--


                            LogUtils.LOGD(TAG, "userList.get(" + i + ").probability " + userList.get(i).b5_probability)

                            if(userList.get(i).a3_prefecture.equals(SettingsUtils.getSettingRadioIdPrefecture(context))){
                                if(userList.get(i).b5_probability <= SettingsUtils.getSettingProbability(context)) probability_prefecture_rank_user--
                            }

                            if(userList.get(i).a1_sex.equals(SettingsUtils.getSettingRadioIdSex(context))){
                                if(userList.get(i).b5_probability <= SettingsUtils.getSettingProbability(context)) probability_sex_rank_user--
                            }

                            if(userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))){
                                if(userList.get(i).b5_probability <= SettingsUtils.getSettingProbability(context)) probability_age_rank_user--
                            }

                            if(userList.get(i).b5_probability <= SettingsUtils.getSettingProbability(context)) probability_all_rank_user--

                            LogUtils.LOGD(
                                TAG,
                                "userList.get(" + i + ").b6_max_chain_win_num " + userList.get(i).b6_max_chain_win_num
                            )

                            if(userList.get(i).a3_prefecture.equals(SettingsUtils.getSettingRadioIdPrefecture(context))){
                                if(userList.get(i).b6_max_chain_win_num <= SettingsUtils.getSettingMaxChainWinNum(context)) max_chain_win_num_prefecture_rank_user--


                            }

                            if(userList.get(i).a1_sex.equals(SettingsUtils.getSettingRadioIdSex(context))){

                                if(userList.get(i).b6_max_chain_win_num <= SettingsUtils.getSettingMaxChainWinNum(context)) max_chain_win_num_sex_rank_user--

                                LogUtils.LOGD(
                                    TAG,
                                    "DEBUG_DATAggg SettingsUtils.getSettingWinNum(context):" + SettingsUtils.getSettingWinNum(
                                        context
                                    )
                                );
                                LogUtils.LOGD(
                                    TAG,
                                    "DEBUG_DATAggg userList.get(i).b6_max_chain_win_num:" + userList.get(i).b6_max_chain_win_num
                                );
                                LogUtils.LOGD(
                                    TAG,
                                    "DEBUG_DATAggg max_chain_win_num_sex_rank_user:" + max_chain_win_num_sex_rank_user
                                )
                            }

                            if(userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))){
                                if(userList.get(i).b6_max_chain_win_num <= SettingsUtils.getSettingMaxChainWinNum(context)) max_chain_win_num_age_rank_user--
                            }

                            if(userList.get(i).b6_max_chain_win_num <= SettingsUtils.getSettingMaxChainWinNum(context)) max_chain_win_num_all_rank_user--
                        }

                        LogUtils.LOGD(
                            TAG,
                            "DEBUG_DATA SettingsUtils.getSettingUuid(context):" + SettingsUtils.getSettingUuid(context)
                        )
                        LogUtils.LOGD(TAG, "DEBUG_DATA win_num_prefecture_rank_user:" + win_num_prefecture_rank_user)
                        LogUtils.LOGD(
                            TAG,
                            "DEBUG_DATA win_num_prefecture_rank_everyone:" + win_num_prefecture_rank_everyone
                        )
                        LogUtils.LOGD(TAG, "DEBUG_DATA win_num_sex_rank_user:" + win_num_sex_rank_user)
                        LogUtils.LOGD(TAG, "DEBUG_DATA win_num_sex_rank_everyone:" + win_num_sex_rank_everyone)
                        LogUtils.LOGD(TAG, "DEBUG_DATA win_num_age_rank_user:" + win_num_age_rank_user)
                        LogUtils.LOGD(TAG, "DEBUG_DATA win_num_age_rank_everyone:" + win_num_age_rank_everyone)
                        LogUtils.LOGD(TAG, "DEBUG_DATA win_num_all_rank_user:" + win_num_all_rank_user)
                        LogUtils.LOGD(TAG, "DEBUG_DATA win_num_all_rank_everyone:" + win_num_all_rank_everyone)


                        LogUtils.LOGD(
                            TAG,
                            "DEBUG_DATA SettingsUtils.getSettingUuid(context):" + SettingsUtils.getSettingUuid(context)
                        )
                        LogUtils.LOGD(
                            TAG,
                            "DEBUG_DATA probability_prefecture_rank_user:" + probability_prefecture_rank_user
                        )
                        LogUtils.LOGD(
                            TAG,
                            "DEBUG_DATA probability_prefecture_rank_everyone:" + probability_prefecture_rank_everyone
                        )
                        LogUtils.LOGD(TAG, "DEBUG_DATA probability_sex_rank_user:" + probability_sex_rank_user)
                        LogUtils.LOGD(TAG, "DEBUG_DATA probability_sex_rank_everyone:" + probability_sex_rank_everyone)
                        LogUtils.LOGD(TAG, "DEBUG_DATA probability_age_rank_user:" + probability_age_rank_user)
                        LogUtils.LOGD(TAG, "DEBUG_DATA probability_age_rank_everyone:" + probability_age_rank_everyone)
                        LogUtils.LOGD(TAG, "DEBUG_DATA probability_all_rank_user:" + probability_all_rank_user)
                        LogUtils.LOGD(TAG, "DEBUG_DATA probability_all_rank_everyone:" + win_num_all_rank_everyone)


                        LogUtils.LOGD(
                            TAG,
                            "DEBUG_DATA SettingsUtils.getSettingUuid(context):" + SettingsUtils.getSettingUuid(context)
                        )
                        LogUtils.LOGD(
                            TAG,
                            "DEBUG_DATA max_chain_win_num_prefecture_rank_user:" + max_chain_win_num_prefecture_rank_user
                        )
                        LogUtils.LOGD(
                            TAG,
                            "DEBUG_DATA max_chain_win_num_prefecture_rank_everyone:" + max_chain_win_num_prefecture_rank_everyone
                        )
                        LogUtils.LOGD(
                            TAG,
                            "DEBUG_DATA max_chain_win_num_sex_rank_user:" + max_chain_win_num_sex_rank_user
                        )
                        LogUtils.LOGD(
                            TAG,
                            "DEBUG_DATA max_chain_win_num_sex_rank_everyone:" + max_chain_win_num_sex_rank_everyone
                        )
                        LogUtils.LOGD(
                            TAG,
                            "DEBUG_DATA max_chain_win_num_age_rank_user:" + max_chain_win_num_age_rank_user
                        )
                        LogUtils.LOGD(
                            TAG,
                            "DEBUG_DATA max_chain_win_num_age_rank_everyone:" + max_chain_win_num_age_rank_everyone
                        )
                        LogUtils.LOGD(
                            TAG,
                            "DEBUG_DATA max_chain_win_num_all_rank_user:" + max_chain_win_num_all_rank_user
                        )
                        LogUtils.LOGD(
                            TAG,
                            "DEBUG_DATA max_chain_win_num_all_rank_everyone:" + max_chain_win_num_all_rank_everyone
                        )

                        AwardFragment.setAwardData()
                    }
                } else {
                    LogUtils.LOGD(TAG, "No such document")
                }
            }

        return 0
    }



    /*
    fun getSettingProbability(context: Context): Double {
        val win_num = getSettingWinNum(context)
        val lose_num = getSettingLoseNum(context)

        LOGD(TAG, "aaa win_num : " + win_num );
        LOGD(TAG, "aaa lose_num : " + lose_num );


        if(win_num == 0) return 0.0
        else if(lose_num == 0) return 100.0
        else{
            val win_num_d = win_num.toDouble()
            val lose_num_d = lose_num.toDouble()
            val probability = ( win_num_d / ( win_num_d + lose_num_d ) ) * 100.0
            var bd = BigDecimal(probability)
            bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP)
            val ret_probability = bd.toDouble()

            return ret_probability
        }
    }
     */
}

