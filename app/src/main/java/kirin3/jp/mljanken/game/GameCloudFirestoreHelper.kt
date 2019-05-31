package kirin3.jp.mljanken.game

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kirin3.jp.mljanken.util.SettingsUtils

object GameCloudFirestoreHelper {

    private val TAG = LogUtils.makeLogTag(GameCloudFirestoreHelper::class.java)

    var most_gender_choice = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
    var most_age_choice = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
    var most_prefecture_choice = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
    var most_gender_first_choice = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
    var most_age_first_choice = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
    var most_prefecture_first_choice = mutableMapOf(1 to 0, 2 to 0, 3 to 0)
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

        LOGD(TAG, "getGameData")

        db.collection(collection)
//            .orderBy(CloudFirestoreHelper.UserItem::b2_win_num.name)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.toObjects(CloudFirestoreHelper.UserItem::class.java) != null) {
                        val userList = document.toObjects(CloudFirestoreHelper.UserItem::class.java)
                        LOGD(TAG, "userList.size " + userList.size)
                        for (i in 0 until userList.size) {

                            if(userList.get(i).a1_gender.equals(SettingsUtils.getSettingRadioIdGender(context))){
                                most_gender_choice[userList.get(i).c1_most_choice] = most_gender_choice[userList.get(i).c1_most_choice]!! + 1
                            }

                            if(userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))){
                                most_age_choice[userList.get(i).c1_most_choice] = most_age_choice[userList.get(i).c1_most_choice]!! + 1
                            }

                            if(userList.get(i).a3_prefecture.equals(SettingsUtils.getSettingRadioIdPrefecture(context))){
                                most_prefecture_choice[userList.get(i).c1_most_choice] = most_prefecture_first_choice[userList.get(i).c1_most_choice]!! + 1
                            }



                            if(userList.get(i).a1_gender.equals(SettingsUtils.getSettingRadioIdGender(context))){
                                most_gender_first_choice[userList.get(i).c2_first_choice] = most_gender_first_choice[userList.get(i).c2_first_choice]!! + 1
                            }

                            if(userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))){
                                most_age_first_choice[userList.get(i).c2_first_choice] = most_age_first_choice[userList.get(i).c2_first_choice]!! + 1
                            }

                            if(userList.get(i).a3_prefecture.equals(SettingsUtils.getSettingRadioIdPrefecture(context))){
                                most_prefecture_first_choice[userList.get(i).c2_first_choice] = most_prefecture_first_choice[userList.get(i).c2_first_choice]!! + 1
                            }
                        }
                        data_existing = true
                    }
                } else {
                    LOGD(TAG, "No such document")
                }
            }
        return 0
    }
}

