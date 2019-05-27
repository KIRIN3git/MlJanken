package kirin3.jp.mljanken.award

import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.SettingsUtils

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.LogUtils.LOGD


object AwardCloudFirestoreHelper {

    private val TAG = LogUtils.makeLogTag(AwardCloudFirestoreHelper::class.java)

    var win_num_all_rank_user = 0
    var win_num_prefecture_rank_user = 0
    var win_num_sex_rank_user = 0
    var win_num_age_rank_user = 0
    var probability_all_rank_user = 0
    var probability_prefecture_rank_user = 0
    var probability_sex_rank_user = 0
    var probability_age_rank_user = 0
    var max_chain_win_num_all_rank_user = 0
    var max_chain_win_num_prefecture_rank_user = 0
    var max_chain_win_num_sex_rank_user = 0
    var max_chain_win_num_age_rank_user = 0

    var win_num_all_rank_everyone = 0
    var win_num_prefecture_rank_everyone = 0
    var win_num_sex_rank_everyone = 0
    var win_num_age_rank_everyone = 0
    var probability_all_rank_everyone = 0
    var probability_prefecture_rank_everyone = 0
    var probability_sex_rank_everyone = 0
    var probability_age_rank_everyone = 0
    var max_chain_win_num_all_rank_everyone = 0
    var max_chain_win_num_prefecture_rank_everyone = 0
    var max_chain_win_num_sex_rank_everyone = 0
    var max_chain_win_num_age_rank_everyone = 0

    /**
     * CroudFirestoreから全データを取得し、Awardに必要なデータを取得する。
     * 取得後は、Awardのデータ表示機能を呼び出す。
     */
    fun getAwardData(db:FirebaseFirestore, collection:String, context:Context):Int{
        win_num_all_rank_everyone = 0
        win_num_prefecture_rank_everyone = 0
        win_num_sex_rank_everyone = 0
        win_num_age_rank_everyone = 0

        probability_all_rank_everyone = 0
        probability_prefecture_rank_everyone = 0
        probability_sex_rank_everyone = 0
        probability_age_rank_everyone = 0

        max_chain_win_num_all_rank_everyone = 0
        max_chain_win_num_prefecture_rank_everyone = 0
        max_chain_win_num_sex_rank_everyone = 0
        max_chain_win_num_age_rank_everyone = 0


        win_num_all_rank_user = 0
        win_num_prefecture_rank_user = 0
        win_num_sex_rank_user = 0
        win_num_age_rank_user = 0

        probability_all_rank_user = 0
        probability_prefecture_rank_user = 0
        probability_sex_rank_user = 0
        probability_age_rank_user = 0

        max_chain_win_num_all_rank_user = 0
        max_chain_win_num_prefecture_rank_user = 0
        max_chain_win_num_sex_rank_user = 0
        max_chain_win_num_age_rank_user = 0

        db.collection(collection)
            .orderBy(CloudFirestoreHelper.UserItem::b2_win_num.name)
            .get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.toObjects(CloudFirestoreHelper.UserItem::class.java) != null) {
                        val userList = document.toObjects(CloudFirestoreHelper.UserItem::class.java)
                        LOGD(TAG, "getDataOrderByLimit")
                        LOGD(TAG, "userList.size " + userList.size)
                        for(i in 0 until  userList.size){

                            LOGD(TAG, "userList.get(" + i + ").win_num " + userList.get(i).b2_win_num)

                            if(userList.get(i).a3_prefecture.equals(SettingsUtils.getSettingRadioIdPrefecture(context))){
                                win_num_prefecture_rank_everyone++
                            }

                            if(userList.get(i).a1_sex.equals(SettingsUtils.getSettingRadioIdSex(context))){
                                win_num_sex_rank_everyone++
                            }

                            if(userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))){
                                win_num_age_rank_everyone++
                            }

                            win_num_all_rank_everyone++


                            LOGD(TAG, "userList.get(" + i + ").b5_probability " + userList.get(i).b5_probability)

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


                            LOGD(TAG, "userList.get(" + i + ").max_chain_win_num " + userList.get(i).b6_max_chain_win_num)

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

                            LOGD(TAG, "userList.get(" + i + ").b2_win_num " + userList.get(i).b2_win_num)

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


                            LOGD(TAG, "userList.get(" + i + ").probability " + userList.get(i).b5_probability)

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

                            LOGD(TAG, "userList.get(" + i + ").b6_max_chain_win_num " + userList.get(i).b6_max_chain_win_num)

                            if(userList.get(i).a3_prefecture.equals(SettingsUtils.getSettingRadioIdPrefecture(context))){
                                if(userList.get(i).b6_max_chain_win_num <= SettingsUtils.getSettingMaxChainWinNum(context)) max_chain_win_num_prefecture_rank_user--


                            }

                            if(userList.get(i).a1_sex.equals(SettingsUtils.getSettingRadioIdSex(context))){

                                if(userList.get(i).b6_max_chain_win_num <= SettingsUtils.getSettingMaxChainWinNum(context)) max_chain_win_num_sex_rank_user--

                                LOGD(TAG, "DEBUG_DATAggg SettingsUtils.getSettingWinNum(context):" + SettingsUtils.getSettingWinNum(context) );
                                LOGD(TAG, "DEBUG_DATAggg userList.get(i).b6_max_chain_win_num:" + userList.get(i).b6_max_chain_win_num );
                                LOGD(TAG, "DEBUG_DATAggg max_chain_win_num_sex_rank_user:" + max_chain_win_num_sex_rank_user )
                            }

                            if(userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))){
                                if(userList.get(i).b6_max_chain_win_num <= SettingsUtils.getSettingMaxChainWinNum(context)) max_chain_win_num_age_rank_user--
                            }

                            if(userList.get(i).b6_max_chain_win_num <= SettingsUtils.getSettingMaxChainWinNum(context)) max_chain_win_num_all_rank_user--
                        }

                        LOGD(TAG, "DEBUG_DATA SettingsUtils.getSettingUuid(context):" + SettingsUtils.getSettingUuid(context) )
                        LOGD(TAG, "DEBUG_DATA win_num_prefecture_rank_user:" + win_num_prefecture_rank_user )
                        LOGD(TAG, "DEBUG_DATA win_num_prefecture_rank_everyone:" + win_num_prefecture_rank_everyone )
                        LOGD(TAG, "DEBUG_DATA win_num_sex_rank_user:" + win_num_sex_rank_user )
                        LOGD(TAG, "DEBUG_DATA win_num_sex_rank_everyone:" + win_num_sex_rank_everyone )
                        LOGD(TAG, "DEBUG_DATA win_num_age_rank_user:" + win_num_age_rank_user )
                        LOGD(TAG, "DEBUG_DATA win_num_age_rank_everyone:" + win_num_age_rank_everyone )
                        LOGD(TAG, "DEBUG_DATA win_num_all_rank_user:" + win_num_all_rank_user )
                        LOGD(TAG, "DEBUG_DATA win_num_all_rank_everyone:" + win_num_all_rank_everyone )


                        LOGD(TAG, "DEBUG_DATA SettingsUtils.getSettingUuid(context):" + SettingsUtils.getSettingUuid(context) )
                        LOGD(TAG, "DEBUG_DATA probability_prefecture_rank_user:" + probability_prefecture_rank_user )
                        LOGD(TAG, "DEBUG_DATA probability_prefecture_rank_everyone:" + probability_prefecture_rank_everyone )
                        LOGD(TAG, "DEBUG_DATA probability_sex_rank_user:" + probability_sex_rank_user )
                        LOGD(TAG, "DEBUG_DATA probability_sex_rank_everyone:" + probability_sex_rank_everyone )
                        LOGD(TAG, "DEBUG_DATA probability_age_rank_user:" + probability_age_rank_user )
                        LOGD(TAG, "DEBUG_DATA probability_age_rank_everyone:" + probability_age_rank_everyone )
                        LOGD(TAG, "DEBUG_DATA probability_all_rank_user:" + probability_all_rank_user )
                        LOGD(TAG, "DEBUG_DATA probability_all_rank_everyone:" + win_num_all_rank_everyone )


                        LOGD(TAG, "DEBUG_DATA SettingsUtils.getSettingUuid(context):" + SettingsUtils.getSettingUuid(context) )
                        LOGD(TAG, "DEBUG_DATA max_chain_win_num_prefecture_rank_user:" + max_chain_win_num_prefecture_rank_user )
                        LOGD(TAG, "DEBUG_DATA max_chain_win_num_prefecture_rank_everyone:" + max_chain_win_num_prefecture_rank_everyone )
                        LOGD(TAG, "DEBUG_DATA max_chain_win_num_sex_rank_user:" + max_chain_win_num_sex_rank_user )
                        LOGD(TAG, "DEBUG_DATA max_chain_win_num_sex_rank_everyone:" + max_chain_win_num_sex_rank_everyone )
                        LOGD(TAG, "DEBUG_DATA max_chain_win_num_age_rank_user:" + max_chain_win_num_age_rank_user )
                        LOGD(TAG, "DEBUG_DATA max_chain_win_num_age_rank_everyone:" + max_chain_win_num_age_rank_everyone )
                        LOGD(TAG, "DEBUG_DATA max_chain_win_num_all_rank_user:" + max_chain_win_num_all_rank_user )
                        LOGD(TAG, "DEBUG_DATA max_chain_win_num_all_rank_everyone:" + max_chain_win_num_all_rank_everyone )

                        AwardFragment.setAwardData()
                    }
                } else {
                    LOGD(TAG, "No such document")
                }
            }

        return 0
    }

}

