package kirin3.jp.mljanken.game

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kirin3.jp.mljanken.util.SettingsUtils

object GameCloudFirestoreHelper {

    private val TAG = LogUtils.makeLogTag(GameCloudFirestoreHelper::class.java)

    // 出し手を保存
    var most_choice_gender = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
    var most_choice_age = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
    var most_choice_prefecture = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
    var first_choice_gender = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
    var first_choice_age = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
    var first_choice_prefecture = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
    var data_existing = false // データ取得済みフラグ

    /**
     * CroudFirestoreから全データを取得し、Gameに必要なデータを取得する。
     * 取得後は、Awardのデータ表示機能を呼び出す。
     */
    fun getGameData(
        db: FirebaseFirestore,
        collection: String,
        context: Context
    ): Int {

        LOGD(TAG, "GameCloudFirestoreHelper.getGameData")

        db.collection(collection)
//            .orderBy(CloudFirestoreHelper.UserItem::b2_win_num.name)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.toObjects(CloudFirestoreHelper.UserItem::class.java) != null) {
                        val userList = document.toObjects(CloudFirestoreHelper.UserItem::class.java)
                        LOGD(TAG, "GameCloudFirestoreHelper.getGameData userList.size " + userList.size)
                        for (i in 0 until userList.size) {
                            // まだ勝負していない人は0なので弾く
                            if( userList.get(i).c1_most_choice == 0 || userList.get(i).c2_first_choice == 0) continue

                            // 同性の人の最も出している手をカウントする
                            if(userList.get(i).a1_gender.equals(SettingsUtils.getSettingRadioIdGender(context))){

                                most_choice_gender[userList.get(i).c1_most_choice] = most_choice_gender[userList.get(i).c1_most_choice]!! + 1
                            }

                            if(userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))){
                                most_choice_age[userList.get(i).c1_most_choice] = most_choice_age[userList.get(i).c1_most_choice]!! + 1
                            }

                            if(userList.get(i).a3_prefecture.equals(SettingsUtils.getSettingRadioIdPrefecture(context))){
                                most_choice_prefecture[userList.get(i).c1_most_choice] = most_choice_prefecture[userList.get(i).c1_most_choice]!! + 1
                            }

                            // 同性の人の最初の出し手をカウントする。
                            if(userList.get(i).a1_gender.equals(SettingsUtils.getSettingRadioIdGender(context))){
                                first_choice_gender[userList.get(i).c2_first_choice] = first_choice_gender[userList.get(i).c2_first_choice]!! + 1
                            }

                            if(userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))){
                                first_choice_age[userList.get(i).c2_first_choice] = first_choice_age[userList.get(i).c2_first_choice]!! + 1
                            }

                            if(userList.get(i).a3_prefecture.equals(SettingsUtils.getSettingRadioIdPrefecture(context))){
                                first_choice_prefecture[userList.get(i).c2_first_choice] = first_choice_prefecture[userList.get(i).c2_first_choice]!! + 1
                            }
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData userList.get(i).a1_gender:"+ userList.get(i).a1_gender)
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData userList.get(i).a2_age:"+ userList.get(i).a2_age)
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData userList.get(i).a3_prefecture:"+ userList.get(i).a3_prefecture)
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData most_choice_gender[1]:"+ most_choice_gender[1])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData most_choice_gender[2]:"+ most_choice_gender[2])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData most_choice_gender[3]:"+ most_choice_gender[3])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData most_choice_age[1]:"+ most_choice_age[1])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData most_choice_age[2]:"+ most_choice_age[2])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData most_choice_age[3]:"+ most_choice_age[3])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData most_choice_prefecture[1]:"+ most_choice_prefecture[1])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData most_choice_prefecture[2]:"+ most_choice_prefecture[2])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData most_choice_prefecture[3]:"+ most_choice_prefecture[3])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData first_choice_gender[1]:"+ first_choice_gender[1])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData first_choice_gender[2]:"+ first_choice_gender[2])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData first_choice_gender[3]:"+ first_choice_gender[3])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData first_choice_age[1]:"+ first_choice_age[1])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData first_choice_age[2]:"+ first_choice_age[2])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData first_choice_age[3]:"+ first_choice_age[3])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData first_choice_prefecture[1]:"+ first_choice_prefecture[1])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData first_choice_prefecture[2]:"+ first_choice_prefecture[2])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData first_choice_prefecture[3]:"+ first_choice_prefecture[3])
                        }
                        data_existing = true
                        LOGD(TAG, "GameCloudFirestoreHelper.getGameData OK")
                    }
                } else {
                    LOGD(TAG, "No such document")
                }
            }
        return 0
    }
}

