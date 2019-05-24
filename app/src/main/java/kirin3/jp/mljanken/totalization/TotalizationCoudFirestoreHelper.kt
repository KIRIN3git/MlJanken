package kirin3.jp.mljanken.totalization

import android.content.Context
import android.support.v4.app.FragmentManager
import com.google.firebase.firestore.FirebaseFirestore
import kirin3.jp.mljanken.award.AwardFragment
import kirin3.jp.mljanken.util.CalculationUtils
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
    fun getTotalizationData(db: FirebaseFirestore, collection:String, context: Context,supportFragmentManager: FragmentManager):Int{

        var sex_win_num = ArrayList<Int>(2)
        var sex_lose_num = ArrayList<Int>(2)
        var sex_probability = ArrayList<Float>(2)

        var age_win_num = ArrayList<Int>(9)
        var age_lose_num = ArrayList<Int>(9)
        var age_probability = ArrayList<Float>(9)

        var prefecture_win_num = ArrayList<Int>(47)
        var prefecture_lose_num = ArrayList<Int>(47)
        var prefecture_probability = ArrayList<Float>(47)

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

                            // 性別勝利数、負け数取得
/*
                            when(userList.get(i).a1_sex){
                                1 -> {
                                    win_sex_probability[0] += userList.get(i).b2_win_num
                                    lose_sex_probability[0] += userList.get(i).b4_lose_num
                                }
                                2 -> {
                                    win_sex_probability[1] += userList.get(i).b2_win_num
                                    lose_sex_probability[1] += userList.get(i).b4_lose_num
                                }
                            }
*/

                            sex_win_num[userList.get(i).a1_sex -1] += userList.get(i).b2_win_num
                            sex_lose_num[userList.get(i).a1_sex -1] += userList.get(i).b4_lose_num
                            sex_probability[userList.get(i).a1_sex -1] = CalculationUtils.getProbability(sex_win_num[userList.get(i).a1_sex -1],sex_lose_num[userList.get(i).a1_sex -1])

                            age_win_num[userList.get(i).a2_age -1] += userList.get(i).b2_win_num
                            age_lose_num[userList.get(i).a2_age -1] += userList.get(i).b4_lose_num
                            age_probability[userList.get(i).a2_age -1] = CalculationUtils.getProbability(sex_win_num[userList.get(i).a2_age -1],sex_lose_num[userList.get(i).a2_age -1])

                            prefecture_win_num[userList.get(i).a3_prefecture -1] += userList.get(i).b2_win_num
                            prefecture_lose_num[userList.get(i).a3_prefecture -1] += userList.get(i).b4_lose_num
                            prefecture_probability[userList.get(i).a3_prefecture -1] = CalculationUtils.getProbability(sex_win_num[userList.get(i).a3_prefecture -1],sex_lose_num[userList.get(i).a3_prefecture -1])
                        }


                        TotalizationActivity.setViewPager(supportFragmentManager)
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

