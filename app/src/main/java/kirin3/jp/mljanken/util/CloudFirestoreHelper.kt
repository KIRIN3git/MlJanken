package kirin3.jp.mljanken.util

import android.content.Context
import android.provider.ContactsContract
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import com.google.firebase.firestore.Query
import kirin3.jp.mljanken.award.AwardFragment
import kirin3.jp.mljanken.util.LogUtils.LOGD
import java.util.*


object CloudFirestoreHelper {

    private val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

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

    var data_rady_flg = false


    fun getInitDb(context:Context):FirebaseFirestore {
        var db = FirebaseFirestore.getInstance()
        return db
    }

    data class UserItem(
        val a1_sex: Int = 0,
        val a2_age: Int = 0,
        val a3_prefecture: Int = 0,
        val b1_battel_num: Int = 0,
        val b2_win_num: Int = 0,
        val b3_drow_num: Int = 0,
        val b4_lose_num: Int = 0,
        val b5_probability:Double = 0.0,
        val b6_max_chain_win_num: Int = 0,
        val b7_max_chain_lose_num: Int = 0,
        val c1_most_choice: Int = 0,
        val c2_first_choice: Int = 0,
        val c3_most_chain_choice: Int = 0,
        val c4_excellence_mode: Int = 0,
        val d1_mode1_rate: Double = 0.0,
        val d2_mode2_rate: Double = 0.0,
        val d3_mode3_rate: Double = 0.0,
        val d4_mode4_rate: Double = 0.0,
        val d5_mode5_rate: Double = 0.0,
        val d6_mode6_rate: Double = 0.0,
        val d7_mode7_rate: Double = 0.0,
        val z1_upd_time: Date = Date()
    )


    /*
     * コレクション、ドキュメント、フィールドを登録
     * ・コレクション、ドキュメントが同名だと上書き保存
     * ・ .documnentを外せば、ランダムでユニークなIDが自動で付与される
     */
    fun addUserData(db:FirebaseFirestore,user:UserItem,collection:String,document:String){
        db.collection(collection)
            .document(document)
            .set(user)
            .addOnSuccessListener { documentReference ->
                LOGD(TAG, "addData")
            }
            .addOnFailureListener { e -> LOGD(TAG, "Error adding document" + e)}
    }

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
            .orderBy(UserItem::b2_win_num.name)
            .get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.toObjects(UserItem::class.java) != null) {
                        val userList = document.toObjects(UserItem::class.java)
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


    fun getWinNum(db:FirebaseFirestore,collection:String,context:Context):Int{
        win_num_all_rank_everyone = 0
        win_num_prefecture_rank_everyone = 0
        win_num_sex_rank_everyone = 0
        win_num_age_rank_everyone = 0

        win_num_all_rank_user = 0
        win_num_prefecture_rank_user = 0
        win_num_sex_rank_user = 0
        win_num_age_rank_user = 0

        db.collection(collection)
            .orderBy(UserItem::b2_win_num.name)
            .get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.toObjects(UserItem::class.java) != null) {
                        val userList = document.toObjects(UserItem::class.java)
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
                        }

                        win_num_prefecture_rank_user = win_num_prefecture_rank_everyone + 1
                        win_num_sex_rank_user = win_num_sex_rank_everyone + 1
                        win_num_age_rank_user = win_num_age_rank_everyone + 1
                        win_num_all_rank_user = win_num_all_rank_everyone + 1

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

                    }
                } else {
                    LOGD(TAG, "No such document")
                }
            }

        return 0
    }


    fun getProbability(db:FirebaseFirestore,collection:String,context:Context):Int{
        probability_all_rank_everyone = 0
        probability_prefecture_rank_everyone = 0
        probability_sex_rank_everyone = 0
        probability_age_rank_everyone = 0

        probability_all_rank_user = 0
        probability_prefecture_rank_user = 0
        probability_sex_rank_user = 0
        probability_age_rank_user = 0

        db.collection(collection)
            .orderBy(UserItem::b5_probability.name)
            .get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.toObjects(UserItem::class.java) != null) {
                        val userList = document.toObjects(UserItem::class.java)
                        LOGD(TAG, "getDataOrderByLimit")
                        LOGD(TAG, "userList.size " + userList.size)
                        for(i in 0 until  userList.size){

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
                        }

                        probability_prefecture_rank_user = probability_prefecture_rank_everyone + 1
                        probability_sex_rank_user = probability_sex_rank_everyone + 1
                        probability_age_rank_user = probability_age_rank_everyone + 1
                        probability_all_rank_user = probability_all_rank_everyone + 1

                        for(i in 0 until  userList.size){

                            LOGD(TAG, "userList.get(" + i + ").probability " + userList.get(i).b5_probability)

                            if(userList.get(i).a3_prefecture.equals(SettingsUtils.getSettingRadioIdPrefecture(context))){
                                if(userList.get(i).b5_probability <= SettingsUtils.getSettingWinNum(context)) probability_prefecture_rank_user--
                            }

                            if(userList.get(i).a1_sex.equals(SettingsUtils.getSettingRadioIdSex(context))){
                                if(userList.get(i).b5_probability <= SettingsUtils.getSettingWinNum(context)) probability_sex_rank_user--
                            }

                            if(userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))){
                                if(userList.get(i).b5_probability <= SettingsUtils.getSettingWinNum(context)) probability_age_rank_user--
                            }

                            if(userList.get(i).b5_probability <= SettingsUtils.getSettingWinNum(context)) probability_all_rank_user--
                        }

                        LOGD(TAG, "DEBUG_DATA SettingsUtils.getSettingUuid(context):" + SettingsUtils.getSettingUuid(context) )
                        LOGD(TAG, "DEBUG_DATA probability_prefecture_rank_user:" + probability_prefecture_rank_user )
                        LOGD(TAG, "DEBUG_DATA probability_prefecture_rank_everyone:" + probability_prefecture_rank_everyone )
                        LOGD(TAG, "DEBUG_DATA probability_sex_rank_user:" + probability_sex_rank_user )
                        LOGD(TAG, "DEBUG_DATA probability_sex_rank_everyone:" + probability_sex_rank_everyone )
                        LOGD(TAG, "DEBUG_DATA probability_age_rank_user:" + probability_age_rank_user )
                        LOGD(TAG, "DEBUG_DATA probability_age_rank_everyone:" + probability_age_rank_everyone )
                        LOGD(TAG, "DEBUG_DATA probability_all_rank_user:" + probability_all_rank_user )
                        LOGD(TAG, "DEBUG_DATA probability_all_rank_everyone:" + win_num_all_rank_everyone )
                    }
                } else {
                    LOGD(TAG, "No such document")
                }
            }

        return 0
    }
    
    
    fun getMaxChainWinNum(db:FirebaseFirestore,collection:String,context:Context):Int{
        max_chain_win_num_all_rank_everyone = 0
        max_chain_win_num_prefecture_rank_everyone = 0
        max_chain_win_num_sex_rank_everyone = 0
        max_chain_win_num_age_rank_everyone = 0

        max_chain_win_num_all_rank_user = 0
        max_chain_win_num_prefecture_rank_user = 0
        max_chain_win_num_sex_rank_user = 0
        max_chain_win_num_age_rank_user = 0

        db.collection(collection)
            .orderBy(UserItem::b6_max_chain_win_num.name)
            .get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.toObjects(UserItem::class.java) != null) {
                        val userList = document.toObjects(UserItem::class.java)
                        LOGD(TAG, "getDataOrderByLimit")
                        LOGD(TAG, "userList.size " + userList.size)
                        for(i in 0 until  userList.size){

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

                        max_chain_win_num_prefecture_rank_user = max_chain_win_num_prefecture_rank_everyone + 1
                        max_chain_win_num_sex_rank_user = max_chain_win_num_sex_rank_everyone + 1
                        max_chain_win_num_age_rank_user = max_chain_win_num_age_rank_everyone + 1
                        max_chain_win_num_all_rank_user = max_chain_win_num_all_rank_everyone + 1

                        for(i in 0 until  userList.size){

                            LOGD(TAG, "userList.get(" + i + ").b6_max_chain_win_num " + userList.get(i).b6_max_chain_win_num)

                            if(userList.get(i).a3_prefecture.equals(SettingsUtils.getSettingRadioIdPrefecture(context))){
                                if(userList.get(i).b6_max_chain_win_num <= SettingsUtils.getSettingWinNum(context)) max_chain_win_num_prefecture_rank_user--
                            }

                            if(userList.get(i).a1_sex.equals(SettingsUtils.getSettingRadioIdSex(context))){
                                if(userList.get(i).b6_max_chain_win_num <= SettingsUtils.getSettingWinNum(context)) max_chain_win_num_sex_rank_user--
                            }

                            if(userList.get(i).a2_age.equals(SettingsUtils.getSettingRadioIdAge(context))){
                                if(userList.get(i).b6_max_chain_win_num <= SettingsUtils.getSettingWinNum(context)) max_chain_win_num_age_rank_user--
                            }

                            if(userList.get(i).b6_max_chain_win_num <= SettingsUtils.getSettingWinNum(context)) max_chain_win_num_all_rank_user--
                        }

                        LOGD(TAG, "DEBUG_DATA SettingsUtils.getSettingUuid(context):" + SettingsUtils.getSettingUuid(context) )
                        LOGD(TAG, "DEBUG_DATA max_chain_win_num_prefecture_rank_user:" + max_chain_win_num_prefecture_rank_user )
                        LOGD(TAG, "DEBUG_DATA max_chain_win_num_prefecture_rank_everyone:" + max_chain_win_num_prefecture_rank_everyone )
                        LOGD(TAG, "DEBUG_DATA max_chain_win_num_sex_rank_user:" + max_chain_win_num_sex_rank_user )
                        LOGD(TAG, "DEBUG_DATA max_chain_win_num_sex_rank_everyone:" + max_chain_win_num_sex_rank_everyone )
                        LOGD(TAG, "DEBUG_DATA max_chain_win_num_age_rank_user:" + max_chain_win_num_age_rank_user )
                        LOGD(TAG, "DEBUG_DATA max_chain_win_num_age_rank_everyone:" + max_chain_win_num_age_rank_everyone )
                        LOGD(TAG, "DEBUG_DATA max_chain_win_num_all_rank_user:" + max_chain_win_num_all_rank_user )
                        LOGD(TAG, "DEBUG_DATA max_chain_win_num_all_rank_everyone:" + max_chain_win_num_all_rank_everyone )
                    }
                } else {
                    LOGD(TAG, "No such document")
                }
            }

        return 0
    }






    /*
     * コレクション、ドキュメント、フィールドを登録
     * ・コレクション、ドキュメントが同名だと上書き保存
     * ・ .documnentを外せば、ランダムでユニークなIDが自動で付与される
     * （例）val user = UserItem("Ichiro","Suzuki",55,true,"TOKYO")
     */
    fun addData(db:FirebaseFirestore,user:UserItem,collection:String,document:String){
        db.collection(collection)
            .document(document)
            .set(user)
            .addOnSuccessListener { documentReference ->
                LOGD(TAG, "addUserData user:" + user)
            }
            .addOnFailureListener { e -> LOGD(TAG, "Error adding document" + e)}
    }





    /*
     * 利用サンプル
     */
    /*
            // CloudFirestore初期化
        var db = CloudFirestoreHelper.getInitDb(mContext)

        val user1 = CloudFirestoreHelper.UserItem("Ichiro","Suzuki",55,true,"TOKYO")
        val user2 = CloudFirestoreHelper.UserItem("Junji","Takada",18,false,"OSAKA")
        val user3 = CloudFirestoreHelper.UserItem("Hanako","Yoshida",17,true,"TOKYO")
        val user4 = CloudFirestoreHelper.UserItem("Toshio","Sato",25,false,"TOKYO")
        val user5 = CloudFirestoreHelper.UserItem("Yoshiko","Imai",30,false,"OSAKA")
        val user6 = CloudFirestoreHelper.UserItem("Taro","Nagase",43,false,"TOKYO")

        val hobby1 = CloudFirestoreHelper.HobbyItem("Ski",3)

        CloudFirestoreHelper.addData(db,user1,"users","u001")
        CloudFirestoreHelper.addData(db,user2,"users","u002")
        CloudFirestoreHelper.addData(db,user3,"users","u003")
        CloudFirestoreHelper.addData(db,user4,"users","u004")
        CloudFirestoreHelper.addData(db,user5,"users","u005")
        CloudFirestoreHelper.addData(db,user6,"users","u006")
        CloudFirestoreHelper.addDataSecondCollection(db,hobby1,"users","u001","hobby","h001")

        CloudFirestoreHelper.getData(db,"users","u001")
        CloudFirestoreHelper.getDataSecondCollection(db,"users","u001","hobby","h001")
        CloudFirestoreHelper.getDataAll(db,"users")
        CloudFirestoreHelper.getDataWhere(db,"users","TOKYO")
        CloudFirestoreHelper.getDataOrderByLimit(db,"users")
     */


    data class HobbyItem(
        val firstHobby: String = "Ski",
        val year: Int = 3)



    /*
     * コレクション、ドキュメント、フィールドを登録
     * （注意）コレクション、ドキュメントが同名だと上書き保存
     */
    fun addDataSecondCollection(db:FirebaseFirestore,hobby:HobbyItem,collectionFirst:String,documentFirst:String,collectionSecond:String,documentSecond:String){
        db.collection(collectionFirst)
            .document(documentFirst)
            .collection(collectionSecond)
            .document(documentSecond)
            .set(hobby)
            .addOnSuccessListener { documentReference ->
                LOGD(TAG, "addDataSecondCollection")
            }
            .addOnFailureListener { e -> LOGD(TAG, "Error adding document" + e)}
    }

    /*
     * コレクション、ドキュメントを指定してデータ取得
     */
    fun getData(db:FirebaseFirestore,collection:String,document:String){
        db.collection(collection)
            .document(document)
            .get()
            .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (document != null && document.data != null) {
                    LOGD(TAG, "getData")

                    LOGD(TAG, "DocumentSnapshot data: " + document.data?.get("firstName"))
                    LOGD(TAG, "DocumentSnapshot data: " + document.data?.get("lastName"))
                    LOGD(TAG, "DocumentSnapshot data: " + document.data?.get("age"))
                    LOGD(TAG, "DocumentSnapshot data: " + document.data?.get("regTime"))
                } else {
                    LOGD(TAG, "No such document")
                }
            } else {
                LOGD(TAG, "get failed with " + task.exception)
            }
        }
            .addOnFailureListener { e -> LOGD(TAG, "Error adding document" + e)}
    }

    /*
 * コレクション、ドキュメントを指定してデータ取得
 */
    fun getDataSecondCollection(db:FirebaseFirestore,collectionFirst:String,documentFirst:String,collectionSecond:String,documentSecond:String){
        db.collection(collectionFirst)
            .document(documentFirst)
            .collection(collectionSecond)
            .document(documentSecond)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.data != null) {
                        LOGD(TAG, "getDataSecondCollection")

                        LOGD(TAG, "DocumentSnapshot data: " + document.data?.get("firstHobby"))
                        LOGD(TAG, "DocumentSnapshot data: " + document.data?.get("year"))
                    } else {
                        LOGD(TAG, "No such document")
                    }
                } else {
                    LOGD(TAG, "get failed with " + task.exception)
                }
            }
            .addOnFailureListener { e -> LOGD(TAG, "Error adding document" + e)}
    }

    /*
     * コレクションとフィールドの一致を指定して全データを取得
     */
    fun getDataAll(db:FirebaseFirestore,collection:String){
        db.collection(collection)
            .get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.toObjects(UserItem::class.java) != null) {
                        val userList = document.toObjects(UserItem::class.java)
                        LOGD(TAG, "getDataAll")
                        LOGD(TAG, "userList.size " + userList.size)
                        for(i in 0 until  userList.size){
                            LOGD(TAG, "userList.get(" + i + ").age " + userList.get(i).a2_age)
                        }
                    }
                } else {
                    LOGD(TAG, "No such document")
                }
            }
    }


    /*
     * コレクションを指定してデータ取得
     * Where,Limit,OrderBy,Betweenを設定
     * 等価演算子（==）と範囲比較（<、<=、>、>=）を組み合わせる場合はインデックスの作成が必要
     * OrderByは重ねることも可能
     * OrderByにQuery.Direction.DESCENDINGを付ければ逆順
     * 境界値を含みたい場合はstartAfter()とendBefore()
     */
    fun getDataConditions(db:FirebaseFirestore,collection:String){
        db.collection(collection)
            .whereEqualTo(UserItem::a2_age.name,"TOKYO")
            .orderBy(UserItem::a2_age.name,Query.Direction.DESCENDING)
            .startAt(40)
            .endAt(60)
            .limit(2)
            .get()
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.toObjects(UserItem::class.java) != null) {
                        val userList = document.toObjects(UserItem::class.java)
                        LOGD(TAG, "getDataOrderByLimit")
                        LOGD(TAG, "userList.size " + userList.size)
                        for(i in 0 until  userList.size){
                            LOGD(TAG, "userList.get(" + i + ").age " + userList.get(i).a2_age)
                        }
                    }
                } else {
                    LOGD(TAG, "No such document")
                }
            }
    }
}

