package kirin3.jp.mljanken.award

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.mng.databaseMng.HandHelper
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.SettingsUtils


class AwardFragment : Fragment() {
    val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    private var mContext: Context? = null
    private var mDbHelper: HandHelper? = null
    private var mDb: SQLiteDatabase? = null

    companion object {

        var sTextPrefetture: TextView? = null
        var sTextGender: TextView? = null
        var sTextAge: TextView? = null

        var sTextWinNum: TextView? = null
        var sTextProbability: TextView? = null
        var sTextMaxChainWinNum: TextView? = null

        var sTextWinNumAll: TextView? = null
        var sTextProbabilityAll: TextView? = null
        var sTextMaxChainWinNumAll: TextView? = null
        var sTextWinNumPrefecture: TextView? = null
        var sTextProbabilityPrefecture: TextView? = null
        var sTextMaxChainWinNumPrefecture: TextView? = null
        var sTextWinNumGender: TextView? = null
        var sTextProbabilityGender: TextView? = null
        var sTextMaxChainWinNumGender: TextView? = null
        var sTextWinNumAge: TextView? = null
        var sTextProbabilityAge: TextView? = null
        var sTextMaxChainWinNumAge: TextView? = null

        fun initView(view: View) {
            sTextPrefetture = view?.findViewById(R.id.prefecture) as TextView
            sTextGender = view?.findViewById(R.id.gender) as TextView
            sTextAge = view?.findViewById(R.id.age) as TextView

            sTextWinNum = view?.findViewById(R.id.win_num) as TextView
            sTextProbability = view?.findViewById(R.id.probability) as TextView
            sTextMaxChainWinNum = view?.findViewById(R.id.win_chain_num) as TextView

            sTextWinNumAll = view?.findViewById(R.id.win_num_all) as TextView
            sTextWinNumPrefecture = view?.findViewById(R.id.win_num_prefecture) as TextView
            sTextWinNumGender = view?.findViewById(R.id.win_num_gender) as TextView
            sTextWinNumAge = view?.findViewById(R.id.win_num_age) as TextView

            sTextProbabilityAll = view?.findViewById(R.id.probability_all) as TextView
            sTextProbabilityPrefecture = view?.findViewById(R.id.probability_prefecture) as TextView
            sTextProbabilityGender = view?.findViewById(R.id.probability_gender) as TextView
            sTextProbabilityAge = view?.findViewById(R.id.probability_age) as TextView

            sTextMaxChainWinNumAll = view?.findViewById(R.id.win_chain_num_all) as TextView
            sTextMaxChainWinNumPrefecture = view?.findViewById(R.id.win_chain_num_prefecture) as TextView
            sTextMaxChainWinNumGender = view?.findViewById(R.id.win_chain_num_gender) as TextView
            sTextMaxChainWinNumAge = view?.findViewById(R.id.win_chain_num_age) as TextView
        }

        fun setBasicData(context: Context) {
            sTextWinNum?.text = SettingsUtils.getSettingWinNum(context!!).toString()
            sTextProbability?.text = SettingsUtils.getSettingProbability(context!!).toString()
            sTextMaxChainWinNum?.text = SettingsUtils.getSettingMaxChainWinNum(context!!).toString()

            sTextPrefetture?.text = SettingsUtils.prefecture_items[SettingsUtils.getSettingRadioIdPrefecture(context!!)]
            sTextGender?.text = SettingsUtils.gender_items[SettingsUtils.getSettingRadioIdGender(context!!)]
            sTextAge?.text = SettingsUtils.age_items[SettingsUtils.getSettingRadioIdAge(context!!)]
        }

        fun setAwardData() {
            sTextWinNumAll?.text =
                AwardCloudFirestoreHelper.win_num_all_rank_user.toString() + "/" + AwardCloudFirestoreHelper.win_num_all_rank_everyone.toString().toString() + "位"
            sTextWinNumPrefecture?.text =
                AwardCloudFirestoreHelper.win_num_prefecture_rank_user.toString() + "/" + AwardCloudFirestoreHelper.win_num_prefecture_rank_everyone.toString().toString() + "位"
            sTextWinNumGender?.text =
                AwardCloudFirestoreHelper.win_num_gender_rank_user.toString() + "/" + AwardCloudFirestoreHelper.win_num_gender_rank_everyone.toString().toString() + "位"
            sTextWinNumAge?.text =
                AwardCloudFirestoreHelper.win_num_age_rank_user.toString() + "/" + AwardCloudFirestoreHelper.win_num_age_rank_everyone.toString().toString() + "位"

            sTextProbabilityAll?.text =
                AwardCloudFirestoreHelper.probability_all_rank_user.toString() + "/" + AwardCloudFirestoreHelper.probability_all_rank_everyone.toString().toString() + "位"
            sTextProbabilityPrefecture?.text =
                AwardCloudFirestoreHelper.probability_prefecture_rank_user.toString() + "/" + AwardCloudFirestoreHelper.probability_prefecture_rank_everyone.toString().toString() + "位"
            sTextProbabilityGender?.text =
                AwardCloudFirestoreHelper.probability_gender_rank_user.toString() + "/" + AwardCloudFirestoreHelper.probability_gender_rank_everyone.toString().toString() + "位"
            sTextProbabilityAge?.text =
                AwardCloudFirestoreHelper.probability_age_rank_user.toString() + "/" + AwardCloudFirestoreHelper.probability_age_rank_everyone.toString().toString() + "位"

            sTextMaxChainWinNumAll?.text =
                AwardCloudFirestoreHelper.max_chain_win_num_all_rank_user.toString() + "/" + AwardCloudFirestoreHelper.max_chain_win_num_all_rank_everyone.toString().toString() + "位"
            sTextMaxChainWinNumPrefecture?.text =
                AwardCloudFirestoreHelper.max_chain_win_num_prefecture_rank_user.toString() + "/" + AwardCloudFirestoreHelper.max_chain_win_num_prefecture_rank_everyone.toString().toString() + "位"
            sTextMaxChainWinNumGender?.text =
                AwardCloudFirestoreHelper.max_chain_win_num_gender_rank_user.toString() + "/" + AwardCloudFirestoreHelper.max_chain_win_num_gender_rank_everyone.toString().toString() + "位"
            sTextMaxChainWinNumAge?.text =
                AwardCloudFirestoreHelper.max_chain_win_num_age_rank_user.toString() + "/" + AwardCloudFirestoreHelper.max_chain_win_num_age_rank_everyone.toString().toString() + "位"
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = activity?.applicationContext
        mDbHelper = HandHelper(activity!!.applicationContext)
        mDb = mDbHelper?.getWritableDatabase()

        return inflater.inflate(R.layout.fragment_award, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)

        setBasicData(mContext!!)


        var db = CloudFirestoreHelper.getInitDb(activity!!.applicationContext)
        AwardCloudFirestoreHelper.getAwardData(db, "users", mContext!!)
    }
}