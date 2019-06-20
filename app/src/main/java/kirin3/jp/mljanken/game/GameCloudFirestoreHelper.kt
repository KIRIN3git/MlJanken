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
    var mostChoiceGender = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
    var mostChoiceAge = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
    var mostChoicePrefecture = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
    var firstChoiceGender = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
    var firstChoiceAge = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
    var firstChoicePrefecture = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
    var dataExisting = false // データ取得済みフラグ

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

                                mostChoiceGender[userList.get(i).c1_most_choice] = mostChoiceGender[userList.get(i).c1_most_choice]!! + 1
                            }

                            if(userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))){
                                mostChoiceAge[userList.get(i).c1_most_choice] = mostChoiceAge[userList.get(i).c1_most_choice]!! + 1
                            }

                            if(userList.get(i).a3_prefecture.equals(SettingsUtils.getSettingRadioIdPrefecture(context))){
                                mostChoicePrefecture[userList.get(i).c1_most_choice] = mostChoicePrefecture[userList.get(i).c1_most_choice]!! + 1
                            }

                            // 同性の人の最初の出し手をカウントする。
                            if(userList.get(i).a1_gender.equals(SettingsUtils.getSettingRadioIdGender(context))){
                                firstChoiceGender[userList.get(i).c2_first_choice] = firstChoiceGender[userList.get(i).c2_first_choice]!! + 1
                            }

                            if(userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))){
                                firstChoiceAge[userList.get(i).c2_first_choice] = firstChoiceAge[userList.get(i).c2_first_choice]!! + 1
                            }

                            if(userList.get(i).a3_prefecture.equals(SettingsUtils.getSettingRadioIdPrefecture(context))){
                                firstChoicePrefecture[userList.get(i).c2_first_choice] = firstChoicePrefecture[userList.get(i).c2_first_choice]!! + 1
                            }
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData userList.get(i).a1_gender:"+ userList.get(i).a1_gender)
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData userList.get(i).a2_age:"+ userList.get(i).a2_age)
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData userList.get(i).a3_prefecture:"+ userList.get(i).a3_prefecture)
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData mostChoiceGender[1]:"+ mostChoiceGender[1])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData mostChoiceGender[2]:"+ mostChoiceGender[2])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData mostChoiceGender[3]:"+ mostChoiceGender[3])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData mostChoiceAge[1]:"+ mostChoiceAge[1])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData mostChoiceAge[2]:"+ mostChoiceAge[2])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData mostChoiceAge[3]:"+ mostChoiceAge[3])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData mostChoicePrefecture[1]:"+ mostChoicePrefecture[1])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData mostChoicePrefecture[2]:"+ mostChoicePrefecture[2])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData mostChoicePrefecture[3]:"+ mostChoicePrefecture[3])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData firstChoiceGender[1]:"+ firstChoiceGender[1])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData firstChoiceGender[2]:"+ firstChoiceGender[2])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData firstChoiceGender[3]:"+ firstChoiceGender[3])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData firstChoiceAge[1]:"+ firstChoiceAge[1])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData firstChoiceAge[2]:"+ firstChoiceAge[2])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData firstChoiceAge[3]:"+ firstChoiceAge[3])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData firstChoicePrefecture[1]:"+ firstChoicePrefecture[1])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData firstChoicePrefecture[2]:"+ firstChoicePrefecture[2])
                            LOGD(TAG, "GameCloudFirestoreHelper.getGameData firstChoicePrefecture[3]:"+ firstChoicePrefecture[3])
                        }
                        dataExisting = true
                        LOGD(TAG, "GameCloudFirestoreHelper.getGameData OK")
                    }
                } else {
                    LOGD(TAG, "GameCloudFirestoreHelper.getGameData No such document")
                }
            }
        return 0
    }
}

