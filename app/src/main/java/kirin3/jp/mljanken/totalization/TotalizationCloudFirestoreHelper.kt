package kirin3.jp.mljanken.totalization

import android.content.Context
import androidx.fragment.app.FragmentManager
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kirin3.jp.mljanken.Config
import kirin3.jp.mljanken.util.CalculationUtils
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGD

object TotalizationCloudFirestoreHelper {

    private val TAG = LogUtils.makeLogTag(TotalizationCloudFirestoreHelper::class.java)

    var genderWinNum = mutableMapOf(1 to 0, 2 to 0)
    var genderLoseNum = mutableMapOf(1 to 0, 2 to 0)
    var genderProbability = mutableMapOf(1 to 0.0f, 2 to 0.0f)

    var ageWinNum = mutableMapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
    var ageLoseNum = mutableMapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
    var ageProbability =
        mutableMapOf(1 to 0.0f, 2 to 0.0f, 3 to 0.0f, 4 to 0.0f, 5 to 0.0f, 6 to 0.0f, 7 to 0.0f, 8 to 0.0f, 9 to 0.0f)

    var prefectureWinNum = mutableMapOf(
        1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0, 10 to 0,
        11 to 0, 12 to 0, 13 to 0, 14 to 0, 15 to 0, 16 to 0, 17 to 0, 18 to 0, 19 to 0, 20 to 0,
        21 to 0, 22 to 0, 23 to 0, 24 to 0, 25 to 0, 26 to 0, 27 to 0, 28 to 0, 29 to 0, 30 to 0,
        31 to 0, 32 to 0, 33 to 0, 34 to 0, 35 to 0, 36 to 0, 37 to 0, 38 to 0, 39 to 0, 40 to 0,
        41 to 0, 42 to 0, 43 to 0, 44 to 0, 45 to 0, 46 to 0, 47 to 0
    )
    var prefectureLoseNum = mutableMapOf(
        1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0, 10 to 0,
        11 to 0, 12 to 0, 13 to 0, 14 to 0, 15 to 0, 16 to 0, 17 to 0, 18 to 0, 19 to 0, 20 to 0,
        21 to 0, 22 to 0, 23 to 0, 24 to 0, 25 to 0, 26 to 0, 27 to 0, 28 to 0, 29 to 0, 30 to 0,
        31 to 0, 32 to 0, 33 to 0, 34 to 0, 35 to 0, 36 to 0, 37 to 0, 38 to 0, 39 to 0, 40 to 0,
        41 to 0, 42 to 0, 43 to 0, 44 to 0, 45 to 0, 46 to 0, 47 to 0
    )
    var prefectureProbability = mutableMapOf(
        1 to 0.0f,
        2 to 0.0f,
        3 to 0.0f,
        4 to 0.0f,
        5 to 0.0f,
        6 to 0.0f,
        7 to 0.0f,
        8 to 0.0f,
        9 to 0.0f,
        10 to 0.0f,
        11 to 0.0f,
        12 to 0.0f,
        13 to 0.0f,
        14 to 0.0f,
        15 to 0.0f,
        16 to 0.0f,
        17 to 0.0f,
        18 to 0.0f,
        19 to 0.0f,
        20 to 0.0f,
        21 to 0.0f,
        22 to 0.0f,
        23 to 0.0f,
        24 to 0.0f,
        25 to 0.0f,
        26 to 0.0f,
        27 to 0.0f,
        28 to 0.0f,
        29 to 0.0f,
        30 to 0.0f,
        31 to 0.0f,
        32 to 0.0f,
        33 to 0.0f,
        34 to 0.0f,
        35 to 0.0f,
        36 to 0.0f,
        37 to 0.0f,
        38 to 0.0f,
        39 to 0.0f,
        40 to 0.0f,
        41 to 0.0f,
        42 to 0.0f,
        43 to 0.0f,
        44 to 0.0f,
        45 to 0.0f,
        46 to 0.0f,
        47 to 0.0f
    )

    fun initTotalizationData(){
        genderWinNum = mutableMapOf(1 to 0, 2 to 0)
        genderLoseNum = mutableMapOf(1 to 0, 2 to 0)
        genderProbability = mutableMapOf(1 to 0.0f, 2 to 0.0f)

        ageWinNum = mutableMapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        ageLoseNum = mutableMapOf(1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0)
        ageProbability =
            mutableMapOf(1 to 0.0f, 2 to 0.0f, 3 to 0.0f, 4 to 0.0f, 5 to 0.0f, 6 to 0.0f, 7 to 0.0f, 8 to 0.0f, 9 to 0.0f)

        prefectureWinNum = mutableMapOf(
            1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0, 10 to 0,
            11 to 0, 12 to 0, 13 to 0, 14 to 0, 15 to 0, 16 to 0, 17 to 0, 18 to 0, 19 to 0, 20 to 0,
            21 to 0, 22 to 0, 23 to 0, 24 to 0, 25 to 0, 26 to 0, 27 to 0, 28 to 0, 29 to 0, 30 to 0,
            31 to 0, 32 to 0, 33 to 0, 34 to 0, 35 to 0, 36 to 0, 37 to 0, 38 to 0, 39 to 0, 40 to 0,
            41 to 0, 42 to 0, 43 to 0, 44 to 0, 45 to 0, 46 to 0, 47 to 0
        )
        prefectureLoseNum = mutableMapOf(
            1 to 0, 2 to 0, 3 to 0, 4 to 0, 5 to 0, 6 to 0, 7 to 0, 8 to 0, 9 to 0, 10 to 0,
            11 to 0, 12 to 0, 13 to 0, 14 to 0, 15 to 0, 16 to 0, 17 to 0, 18 to 0, 19 to 0, 20 to 0,
            21 to 0, 22 to 0, 23 to 0, 24 to 0, 25 to 0, 26 to 0, 27 to 0, 28 to 0, 29 to 0, 30 to 0,
            31 to 0, 32 to 0, 33 to 0, 34 to 0, 35 to 0, 36 to 0, 37 to 0, 38 to 0, 39 to 0, 40 to 0,
            41 to 0, 42 to 0, 43 to 0, 44 to 0, 45 to 0, 46 to 0, 47 to 0
        )
        prefectureProbability = mutableMapOf(
            1 to 0.0f,
            2 to 0.0f,
            3 to 0.0f,
            4 to 0.0f,
            5 to 0.0f,
            6 to 0.0f,
            7 to 0.0f,
            8 to 0.0f,
            9 to 0.0f,
            10 to 0.0f,
            11 to 0.0f,
            12 to 0.0f,
            13 to 0.0f,
            14 to 0.0f,
            15 to 0.0f,
            16 to 0.0f,
            17 to 0.0f,
            18 to 0.0f,
            19 to 0.0f,
            20 to 0.0f,
            21 to 0.0f,
            22 to 0.0f,
            23 to 0.0f,
            24 to 0.0f,
            25 to 0.0f,
            26 to 0.0f,
            27 to 0.0f,
            28 to 0.0f,
            29 to 0.0f,
            30 to 0.0f,
            31 to 0.0f,
            32 to 0.0f,
            33 to 0.0f,
            34 to 0.0f,
            35 to 0.0f,
            36 to 0.0f,
            37 to 0.0f,
            38 to 0.0f,
            39 to 0.0f,
            40 to 0.0f,
            41 to 0.0f,
            42 to 0.0f,
            43 to 0.0f,
            44 to 0.0f,
            45 to 0.0f,
            46 to 0.0f,
            47 to 0.0f
        )
    }


    /**
     * CroudFirestoreから全データを取得し、Awardに必要なデータを取得する。
     * 取得後は、Awardのデータ表示機能を呼び出す。
     */
    fun getTotalizationData(
        db: FirebaseFirestore,
        collection: String,
        context: Context,
        supportFragmentManager: androidx.fragment.app.FragmentManager
    ): Int {
        initTotalizationData()

        LOGD(TAG, "TotalizationCloudFirestoreHelper getTotalizationData")

        db.collection(collection)
            .orderBy(CloudFirestoreHelper.UserItem::b2_win_num.name)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val document = task.result
                    if (document != null && document.toObjects(CloudFirestoreHelper.UserItem::class.java) != null) {
                        val userList = document.toObjects(CloudFirestoreHelper.UserItem::class.java)
                        LOGD(TAG, "TotalizationCloudFirestoreHelper userList.size " + userList.size)
                        Log.w( "DEBUG_DATA", "TotalizationCloudFirestoreHelper userList.size " + userList.size)
                        for (i in 0 until userList.size) {

                            // 本番モードの場合、デバックモードのデータは非表示
                            if(Config.IS_DOGFOOD_BUILD == false && userList.get(i).y1_debug_flg == true) continue

                            genderWinNum.put(
                                userList.get(i).a1_gender,
                                genderWinNum[userList.get(i).a1_gender]!! + userList.get(i).b2_win_num
                            )
                            genderLoseNum.put(
                                userList.get(i).a1_gender,
                                genderLoseNum[userList.get(i).a1_gender]!! + userList.get(i).b4_lose_num
                            )
                            Log.w( "DEBUG_DATA", "xxx genderWinNum[userList.get(i).a1_gender]: " + genderWinNum[userList.get(i).a1_gender] );
                            Log.w( "DEBUG_DATA","xxx genderLoseNum[userList.get(i).a1_gender]: " + genderLoseNum[userList.get(i).a1_gender] );
                            Log.w( "DEBUG_DATA","xxx CalculationUtils.getProbability(genderWinNum[userList.get(i).a1_gender]!!,genderLoseNum[userList.get(i).a1_gender]!!): " + CalculationUtils.getProbability(genderWinNum[userList.get(i).a1_gender]!!,genderLoseNum[userList.get(i).a1_gender]!!) );
                            Log.w( "DEBUG_DATA","xxx CalculationUtils.getProbability2(genderWinNum[userList.get(i).a1_gender]!!,genderLoseNum[userList.get(i).a1_gender]!!): " + CalculationUtils.getProbability2(genderWinNum[userList.get(i).a1_gender]!!,genderLoseNum[userList.get(i).a1_gender]!!) );

                            genderProbability.put(
                                userList.get(i).a1_gender,
                                CalculationUtils.getProbability(
                                    genderWinNum[userList.get(i).a1_gender]!!,
                                    genderLoseNum[userList.get(i).a1_gender]!!
                                )
                            )

                            ageWinNum.put(
                                userList.get(i).a2_age,
                                ageWinNum[userList.get(i).a2_age]!! + userList.get(i).b2_win_num
                            )
                            ageLoseNum.put(
                                userList.get(i).a2_age,
                                ageLoseNum[userList.get(i).a2_age]!! + userList.get(i).b4_lose_num
                            )
                            ageProbability.put(
                                userList.get(i).a2_age,
                                CalculationUtils.getProbability(
                                    ageWinNum[userList.get(i).a2_age]!!,
                                    ageLoseNum[userList.get(i).a2_age]!!
                                )
                            )

                            prefectureWinNum.put(
                                userList.get(i).a3_prefecture,
                                prefectureWinNum[userList.get(i).a3_prefecture]!! + userList.get(i).b2_win_num
                            )
                            prefectureLoseNum.put(
                                userList.get(i).a3_prefecture,
                                prefectureLoseNum[userList.get(i).a3_prefecture]!! + userList.get(i).b4_lose_num
                            )
                            prefectureProbability.put(
                                userList.get(i).a3_prefecture,
                                CalculationUtils.getProbability(
                                    prefectureWinNum[userList.get(i).a3_prefecture]!!,
                                    prefectureLoseNum[userList.get(i).a3_prefecture]!!
                                )
                            )
                        }
                        TotalizationActivity.setViewPager(supportFragmentManager)
                    }
                } else {
                    LOGD(TAG, "TotalizationCloudFirestoreHelper getTotalizationData No such document")
                }
            }
        return 0
    }
}

