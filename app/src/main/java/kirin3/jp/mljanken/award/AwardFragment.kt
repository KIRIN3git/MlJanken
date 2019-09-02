package kirin3.jp.mljanken.award

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.mng.databaseMng.HandHelper
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.SettingsUtils


class AwardFragment : androidx.fragment.app.Fragment() {
    val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    private var appContext: Context? = null
    private var dbHelper: HandHelper? = null
    private var db: SQLiteDatabase? = null


    // ViewModelのインスタンスを作成
    private var viewModel: AwardViewModel? = null

    companion object {
        private lateinit var textPrefetture: TextView
        private lateinit var textGender: TextView
        private lateinit var textAge: TextView

        private lateinit var textWinNum: TextView
        private lateinit var textProbability: TextView
        private lateinit var textMaxChainWinNum: TextView

        private lateinit var textWinNumAll: TextView
        private lateinit var textProbabilityAll: TextView
        private lateinit var textMaxChainWinNumAll: TextView
        private lateinit var textWinNumPrefecture: TextView
        private lateinit var textProbabilityPrefecture: TextView
        private lateinit var textMaxChainWinNumPrefecture: TextView
        private lateinit var textWinNumGender: TextView
        private lateinit var textProbabilityGender: TextView
        private lateinit var textMaxChainWinNumGender: TextView
        private lateinit var textWinNumAge: TextView
        private lateinit var textProbabilityAge: TextView
        private lateinit var textMaxChainWinNumAge: TextView


        fun setAwardData() {
            textWinNumAll?.text =
                AwardCloudFirestoreHelper.win_num_all_rank_user.toString() + "/" + AwardCloudFirestoreHelper.win_num_all_rank_everyone.toString().toString() + "位"
            textWinNumPrefecture?.text =
                AwardCloudFirestoreHelper.win_num_prefecture_rank_user.toString() + "/" + AwardCloudFirestoreHelper.win_num_prefecture_rank_everyone.toString().toString() + "位"
            textWinNumGender?.text =
                AwardCloudFirestoreHelper.win_num_gender_rank_user.toString() + "/" + AwardCloudFirestoreHelper.win_num_gender_rank_everyone.toString().toString() + "位"
            textWinNumAge?.text =
                AwardCloudFirestoreHelper.win_num_age_rank_user.toString() + "/" + AwardCloudFirestoreHelper.win_num_age_rank_everyone.toString().toString() + "位"

            textProbabilityAll?.text =
                AwardCloudFirestoreHelper.probability_all_rank_user.toString() + "/" + AwardCloudFirestoreHelper.probability_all_rank_everyone.toString().toString() + "位"
            textProbabilityPrefecture?.text =
                AwardCloudFirestoreHelper.probability_prefecture_rank_user.toString() + "/" + AwardCloudFirestoreHelper.probability_prefecture_rank_everyone.toString().toString() + "位"
            textProbabilityGender?.text =
                AwardCloudFirestoreHelper.probability_gender_rank_user.toString() + "/" + AwardCloudFirestoreHelper.probability_gender_rank_everyone.toString().toString() + "位"
            textProbabilityAge?.text =
                AwardCloudFirestoreHelper.probability_age_rank_user.toString() + "/" + AwardCloudFirestoreHelper.probability_age_rank_everyone.toString().toString() + "位"

            textMaxChainWinNumAll?.text =
                AwardCloudFirestoreHelper.max_chain_win_num_all_rank_user.toString() + "/" + AwardCloudFirestoreHelper.max_chain_win_num_all_rank_everyone.toString().toString() + "位"
            textMaxChainWinNumPrefecture?.text =
                AwardCloudFirestoreHelper.max_chain_win_num_prefecture_rank_user.toString() + "/" + AwardCloudFirestoreHelper.max_chain_win_num_prefecture_rank_everyone.toString().toString() + "位"
            textMaxChainWinNumGender?.text =
                AwardCloudFirestoreHelper.max_chain_win_num_gender_rank_user.toString() + "/" + AwardCloudFirestoreHelper.max_chain_win_num_gender_rank_everyone.toString().toString() + "位"
            textMaxChainWinNumAge?.text =
                AwardCloudFirestoreHelper.max_chain_win_num_age_rank_user.toString() + "/" + AwardCloudFirestoreHelper.max_chain_win_num_age_rank_everyone.toString().toString() + "位"
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        appContext = activity?.applicationContext
        dbHelper = HandHelper(activity!!.applicationContext)
        db = dbHelper?.getWritableDatabase()
        viewModel = ViewModelProviders.of(this).get(AwardViewModel::class.java)

        return inflater.inflate(R.layout.fragment_award, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)

        setBasicData(appContext!!)

        var db = CloudFirestoreHelper.getInitDb(activity!!.applicationContext)
        AwardCloudFirestoreHelper.getAwardData(db, "users", appContext!!)

        /*
        if ( !(SettingsUtils.getSettingRadioIdGender(appContext!!) == 0
            || SettingsUtils.getSettingRadioIdAge(appContext!!) == 0
            || SettingsUtils.getSettingRadioIdPrefecture(appContext!!) == 0) ) {
        }
        */
    }

    fun initView(view: View) {
        textPrefetture = view.findViewById(R.id.prefecture) as TextView
        textGender = view.findViewById(R.id.gender) as TextView
        textAge = view.findViewById(R.id.age) as TextView

        textWinNum = view.findViewById(R.id.win_num) as TextView
        textProbability = view.findViewById(R.id.probability) as TextView
        textMaxChainWinNum = view.findViewById(R.id.win_chain_num) as TextView

        textWinNumAll = view.findViewById(R.id.win_num_all) as TextView
        textWinNumPrefecture = view.findViewById(R.id.win_num_prefecture) as TextView
        textWinNumGender = view.findViewById(R.id.win_num_gender) as TextView
        textWinNumAge = view.findViewById(R.id.win_num_age) as TextView

        textProbabilityAll = view.findViewById(R.id.probability_all) as TextView
        textProbabilityPrefecture = view.findViewById(R.id.probability_prefecture) as TextView
        textProbabilityGender = view.findViewById(R.id.probability_gender) as TextView
        textProbabilityAge = view.findViewById(R.id.probability_age) as TextView

        textMaxChainWinNumAll = view.findViewById(R.id.win_chain_num_all) as TextView
        textMaxChainWinNumPrefecture = view.findViewById(R.id.win_chain_num_prefecture) as TextView
        textMaxChainWinNumGender = view.findViewById(R.id.win_chain_num_gender) as TextView
        textMaxChainWinNumAge = view.findViewById(R.id.win_chain_num_age) as TextView
    }

    fun setBasicData(context: Context) {
        textWinNum?.text = SettingsUtils.getSettingWinNum(context).toString()
        textProbability?.text = SettingsUtils.getSettingProbability(context).toString()
        textMaxChainWinNum?.text = SettingsUtils.getSettingMaxChainWinNum(context).toString()

        textPrefetture?.text = SettingsUtils.prefectureItems[SettingsUtils.getSettingRadioIdPrefecture(context)]
        textGender?.text = SettingsUtils.genderItems[SettingsUtils.getSettingRadioIdGender(context)]
        textAge?.text = SettingsUtils.ageItems[SettingsUtils.getSettingRadioIdAge(context)]
    }
}