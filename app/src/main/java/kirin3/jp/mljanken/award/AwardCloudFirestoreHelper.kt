package kirin3.jp.mljanken.award

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import kirin3.jp.mljanken.Config
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kirin3.jp.mljanken.util.SettingsUtils


object AwardCloudFirestoreHelper {

    private val TAG = LogUtils.makeLogTag(AwardCloudFirestoreHelper::class.java)

    var win_num_all_rank_user = 0
    var win_num_prefecture_rank_user = 0
    var win_num_gender_rank_user = 0
    var win_num_age_rank_user = 0
    var probability_all_rank_user = 0
    var probability_prefecture_rank_user = 0
    var probability_gender_rank_user = 0
    var probability_age_rank_user = 0
    var max_chain_win_num_all_rank_user = 0
    var max_chain_win_num_prefecture_rank_user = 0
    var max_chain_win_num_gender_rank_user = 0
    var max_chain_win_num_age_rank_user = 0

    var win_num_all_rank_everyone = 0
    var win_num_prefecture_rank_everyone = 0
    var win_num_gender_rank_everyone = 0
    var win_num_age_rank_everyone = 0
    var probability_all_rank_everyone = 0
    var probability_prefecture_rank_everyone = 0
    var probability_gender_rank_everyone = 0
    var probability_age_rank_everyone = 0
    var max_chain_win_num_all_rank_everyone = 0
    var max_chain_win_num_prefecture_rank_everyone = 0
    var max_chain_win_num_gender_rank_everyone = 0
    var max_chain_win_num_age_rank_everyone = 0

    fun initAwardData(){
        win_num_all_rank_everyone = 0
        win_num_prefecture_rank_everyone = 0
        win_num_gender_rank_everyone = 0
        win_num_age_rank_everyone = 0

        probability_all_rank_everyone = 0
        probability_prefecture_rank_everyone = 0
        probability_gender_rank_everyone = 0
        probability_age_rank_everyone = 0

        max_chain_win_num_all_rank_everyone = 0
        max_chain_win_num_prefecture_rank_everyone = 0
        max_chain_win_num_gender_rank_everyone = 0
        max_chain_win_num_age_rank_everyone = 0

        win_num_all_rank_user = 0
        win_num_prefecture_rank_user = 0
        win_num_gender_rank_user = 0
        win_num_age_rank_user = 0

        probability_all_rank_user = 0
        probability_prefecture_rank_user = 0
        probability_gender_rank_user = 0
        probability_age_rank_user = 0

        max_chain_win_num_all_rank_user = 0
        max_chain_win_num_prefecture_rank_user = 0
        max_chain_win_num_gender_rank_user = 0
        max_chain_win_num_age_rank_user = 0
    }

    /**
     * CroudFirestoreから全データを取得し、Awardに必要なデータを取得する。
     * 取得後は、Awardのデータ表示機能を呼び出す。
     */
    fun getAwardData(db: FirebaseFirestore, collection: String, context: Context): Int {

        initAwardData()
        LOGD(TAG, "getAwardData getAwardData")
        db.collection(collection)
            .orderBy(CloudFirestoreHelper.UserItem::b2_win_num.name)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.toObjects(CloudFirestoreHelper.UserItem::class.java) != null) {
                        val userList = document.toObjects(CloudFirestoreHelper.UserItem::class.java)
                        for (i in 0 until userList.size) {
                            // 本番モードの場合、デバックモードのデータは非表示
                            if (Config.IS_DOGFOOD_BUILD == false && userList.get(i).y1_debug_flg == true) continue

                            if (userList.get(i).a1_gender.equals(SettingsUtils.getSettingRadioIdGender(context))) {
                                win_num_gender_rank_everyone++
                            }
                            if (userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))) {
                                win_num_age_rank_everyone++
                            }
                            if (userList.get(i).a3_prefecture.equals(SettingsUtils.getSettingRadioIdPrefecture(context))) {
                                win_num_prefecture_rank_everyone++
                            }
                            win_num_all_rank_everyone++

                            if (userList.get(i).a1_gender.equals(SettingsUtils.getSettingRadioIdGender(context))) {
                                probability_gender_rank_everyone++
                            }
                            if (userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))) {
                                probability_age_rank_everyone++
                            }
                            if (userList.get(i).a3_prefecture.equals(SettingsUtils.getSettingRadioIdPrefecture(context))) {
                                probability_prefecture_rank_everyone++
                            }
                            probability_all_rank_everyone++

                            if (userList.get(i).a1_gender.equals(SettingsUtils.getSettingRadioIdGender(context))) {
                                max_chain_win_num_gender_rank_everyone++
                            }
                            if (userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))) {
                                max_chain_win_num_age_rank_everyone++
                            }
                            if (userList.get(i).a3_prefecture.equals(SettingsUtils.getSettingRadioIdPrefecture(context))) {
                                max_chain_win_num_prefecture_rank_everyone++
                            }
                            max_chain_win_num_all_rank_everyone++
                        }

                        win_num_gender_rank_user = win_num_gender_rank_everyone
                        if (SettingsUtils.getSettingWinNum(context) != 0) win_num_gender_rank_user++
                        win_num_age_rank_user = win_num_age_rank_everyone
                        if (SettingsUtils.getSettingWinNum(context) != 0) win_num_age_rank_user++
                        win_num_prefecture_rank_user = win_num_prefecture_rank_everyone
                        if (SettingsUtils.getSettingWinNum(context) != 0) win_num_prefecture_rank_user++
                        win_num_all_rank_user = win_num_all_rank_everyone
                        if (SettingsUtils.getSettingWinNum(context) != 0) win_num_all_rank_user++

                        probability_gender_rank_user = probability_gender_rank_everyone
                        if (SettingsUtils.getSettingProbability(context) != 0.0) probability_gender_rank_user++
                        probability_age_rank_user = probability_age_rank_everyone
                        if (SettingsUtils.getSettingProbability(context) != 0.0) probability_age_rank_user++
                        probability_prefecture_rank_user = probability_prefecture_rank_everyone
                        if (SettingsUtils.getSettingProbability(context) != 0.0) probability_prefecture_rank_user++
                        probability_all_rank_user = probability_all_rank_everyone
                        if (SettingsUtils.getSettingProbability(context) != 0.0) probability_all_rank_user++

                        max_chain_win_num_gender_rank_user = max_chain_win_num_gender_rank_everyone
                        if (SettingsUtils.getSettingMaxChainWinNum(context) != 0) max_chain_win_num_gender_rank_user++
                        max_chain_win_num_age_rank_user = max_chain_win_num_age_rank_everyone
                        if (SettingsUtils.getSettingMaxChainWinNum(context) != 0) max_chain_win_num_age_rank_user++
                        max_chain_win_num_prefecture_rank_user = max_chain_win_num_prefecture_rank_everyone
                        if (SettingsUtils.getSettingMaxChainWinNum(context) != 0) max_chain_win_num_prefecture_rank_user++
                        max_chain_win_num_all_rank_user = max_chain_win_num_all_rank_everyone
                        if (SettingsUtils.getSettingMaxChainWinNum(context) != 0) max_chain_win_num_all_rank_user++

                        for (i in 0 until userList.size) {
                            // 本番モードの場合、デバックモードのデータは非表示
                            if (Config.IS_DOGFOOD_BUILD == false && userList.get(i).y1_debug_flg == true) continue

                            if (SettingsUtils.getSettingWinNum(context) != 0) {
                                if (userList.get(i).a1_gender.equals(SettingsUtils.getSettingRadioIdGender(context))) {
                                    if (userList.get(i).b2_win_num <= SettingsUtils.getSettingWinNum(context)) win_num_gender_rank_user--
                                }
                                if (userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))) {
                                    if (userList.get(i).b2_win_num <= SettingsUtils.getSettingWinNum(context)) win_num_age_rank_user--
                                }
                                if (userList.get(i).a3_prefecture.equals(
                                        SettingsUtils.getSettingRadioIdPrefecture(
                                            context
                                        )
                                    )
                                ) {
                                    if (userList.get(i).b2_win_num <= SettingsUtils.getSettingWinNum(context)) win_num_prefecture_rank_user--
                                }
                                if (userList.get(i).b2_win_num <= SettingsUtils.getSettingWinNum(context)) win_num_all_rank_user--
                            }

                            if (SettingsUtils.getSettingProbability(context) != 0.0) {
                                if (userList.get(i).a1_gender.equals(SettingsUtils.getSettingRadioIdGender(context))) {
                                    if (userList.get(i).b5_probability <= SettingsUtils.getSettingProbability(context)) probability_gender_rank_user--
                                }
                                if (userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))) {
                                    if (userList.get(i).b5_probability <= SettingsUtils.getSettingProbability(context)) probability_age_rank_user--
                                }
                                if (userList.get(i).a3_prefecture.equals(
                                        SettingsUtils.getSettingRadioIdPrefecture(
                                            context
                                        )
                                    )
                                ) {
                                    if (userList.get(i).b5_probability <= SettingsUtils.getSettingProbability(context)) probability_prefecture_rank_user--
                                }
                                if (userList.get(i).b5_probability <= SettingsUtils.getSettingProbability(context)) probability_all_rank_user--
                            }

                            if (SettingsUtils.getSettingMaxChainWinNum(context) != 0) {
                                if (userList.get(i).a1_gender.equals(SettingsUtils.getSettingRadioIdGender(context))) {
                                    if (userList.get(i).b6_max_chain_win_num <= SettingsUtils.getSettingMaxChainWinNum(
                                            context
                                        )
                                    ) max_chain_win_num_gender_rank_user--
                                }
                                if (userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))) {
                                    if (userList.get(i).b6_max_chain_win_num <= SettingsUtils.getSettingMaxChainWinNum(
                                            context
                                        )
                                    ) max_chain_win_num_age_rank_user--
                                }
                                if (userList.get(i).a3_prefecture.equals(
                                        SettingsUtils.getSettingRadioIdPrefecture(
                                            context
                                        )
                                    )
                                ) {
                                    if (userList.get(i).b6_max_chain_win_num <= SettingsUtils.getSettingMaxChainWinNum(
                                            context
                                        )
                                    ) max_chain_win_num_prefecture_rank_user--
                                }
                                if (userList.get(i).b6_max_chain_win_num <= SettingsUtils.getSettingMaxChainWinNum(
                                        context
                                    )
                                ) max_chain_win_num_all_rank_user--
                            }
                        }

                        LOGD(
                            TAG,
                            "getAwardData SettingsUtils.getSettingUuid(context):" + SettingsUtils.getSettingUuid(context)
                        )
                        LOGD(TAG, "getAwardData win_num_prefecture_rank_user:" + win_num_prefecture_rank_user)
                        LOGD(TAG, "getAwardData win_num_gender_rank_everyone:" + win_num_gender_rank_everyone)
                        LOGD(TAG, "getAwardData win_num_age_rank_user:" + win_num_age_rank_user)
                        LOGD(TAG, "getAwardData win_num_age_rank_everyone:" + win_num_age_rank_everyone)
                        LOGD(TAG, "getAwardData win_num_prefecture_rank_everyone:" + win_num_prefecture_rank_everyone)
                        LOGD(TAG, "getAwardData win_num_gender_rank_user:" + win_num_gender_rank_user)
                        LOGD(TAG, "getAwardData win_num_all_rank_user:" + win_num_all_rank_user)
                        LOGD(TAG, "getAwardData win_num_all_rank_everyone:" + win_num_all_rank_everyone)

                        LOGD(
                            TAG,
                            "getAwardData SettingsUtils.getSettingUuid(context):" + SettingsUtils.getSettingUuid(context)
                        )
                        LOGD(TAG, "getAwardData probability_gender_rank_user:" + probability_gender_rank_user)
                        LOGD(TAG, "getAwardData probability_gender_rank_everyone:" + probability_gender_rank_everyone)
                        LOGD(TAG, "getAwardData probability_age_rank_user:" + probability_age_rank_user)
                        LOGD(TAG, "getAwardData probability_age_rank_everyone:" + probability_age_rank_everyone)
                        LOGD(TAG, "getAwardData probability_prefecture_rank_user:" + probability_prefecture_rank_user)
                        LOGD(
                            TAG,
                            "getAwardData probability_prefecture_rank_everyone:" + probability_prefecture_rank_everyone
                        )
                        LOGD(TAG, "getAwardData probability_all_rank_user:" + probability_all_rank_user)
                        LOGD(TAG, "getAwardData probability_all_rank_everyone:" + win_num_all_rank_everyone)

                        LOGD(
                            TAG,
                            "getAwardData SettingsUtils.getSettingUuid(context):" + SettingsUtils.getSettingUuid(context)
                        )
                        LOGD(
                            TAG,
                            "getAwardData max_chain_win_num_gender_rank_user:" + max_chain_win_num_gender_rank_user
                        )
                        LOGD(
                            TAG,
                            "getAwardData max_chain_win_num_gender_rank_everyone:" + max_chain_win_num_gender_rank_everyone
                        )
                        LOGD(TAG, "getAwardData max_chain_win_num_age_rank_user:" + max_chain_win_num_age_rank_user)
                        LOGD(
                            TAG,
                            "getAwardData max_chain_win_num_age_rank_everyone:" + max_chain_win_num_age_rank_everyone
                        )
                        LOGD(
                            TAG,
                            "getAwardData max_chain_win_num_prefecture_rank_user:" + max_chain_win_num_prefecture_rank_user
                        )
                        LOGD(
                            TAG,
                            "getAwardData max_chain_win_num_prefecture_rank_everyone:" + max_chain_win_num_prefecture_rank_everyone
                        )
                        LOGD(TAG, "getAwardData max_chain_win_num_all_rank_user:" + max_chain_win_num_all_rank_user)
                        LOGD(
                            TAG,
                            "getAwardData max_chain_win_num_all_rank_everyone:" + max_chain_win_num_all_rank_everyone
                        )

                        AwardFragment.setAwardData()
                    }
                } else {
                    LOGD(TAG, "getAwardData No such document")
                }
            }

        return 0
    }

}

