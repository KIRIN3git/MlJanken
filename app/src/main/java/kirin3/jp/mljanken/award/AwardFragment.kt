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
import kirin3.jp.mljanken.data.HandHelper
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kirin3.jp.mljanken.util.SettingsUtils
import kirin3.jp.mljanken.util.SettingsUtils.TAG


class AwardFragment : Fragment() {
    val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    private var mContext: Context? = null
    private var mDbHelper: HandHelper? = null
    private var mDb: SQLiteDatabase? = null




    companion object {

        var sTextPrefetture: TextView? = null
        var sTextSex: TextView? = null
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
        var sTextWinNumSex: TextView? = null
        var sTextProbabilitySex: TextView? = null
        var sTextMaxChainWinNumSex: TextView? = null
        var sTextWinNumAge: TextView? = null
        var sTextProbabilityAge: TextView? = null
        var sTextMaxChainWinNumAge: TextView? = null

        fun initView(view:View){
            sTextPrefetture = view?.findViewById(R.id.prefecture) as TextView
            sTextSex = view?.findViewById(R.id.sex) as TextView
            sTextAge = view?.findViewById(R.id.age) as TextView

            sTextWinNum = view?.findViewById(R.id.win_num) as TextView
            sTextProbability = view?.findViewById(R.id.probability) as TextView
            sTextMaxChainWinNum = view?.findViewById(R.id.win_chain_num) as TextView

            sTextWinNumAll = view?.findViewById(R.id.win_num_all) as TextView
            sTextWinNumPrefecture = view?.findViewById(R.id.win_num_prefecture) as TextView
            sTextWinNumSex = view?.findViewById(R.id.win_num_sex) as TextView
            sTextWinNumAge = view?.findViewById(R.id.win_num_age) as TextView

            sTextProbabilityAll = view?.findViewById(R.id.probability_all) as TextView
            sTextProbabilityPrefecture = view?.findViewById(R.id.probability_prefecture) as TextView
            sTextProbabilitySex = view?.findViewById(R.id.probability_sex) as TextView
            sTextProbabilityAge = view?.findViewById(R.id.probability_age) as TextView

            sTextMaxChainWinNumAll = view?.findViewById(R.id.win_chain_num_all) as TextView
            sTextMaxChainWinNumPrefecture = view?.findViewById(R.id.win_chain_num_prefecture) as TextView
            sTextMaxChainWinNumSex = view?.findViewById(R.id.win_chain_num_sex) as TextView
            sTextMaxChainWinNumAge = view?.findViewById(R.id.win_chain_num_age) as TextView
        }
        fun setBasicData(context: Context){

            sTextWinNum?.text = SettingsUtils.getSettingWinNum(context!!).toString()
            sTextProbability?.text = SettingsUtils.getSettingProbability(context!!).toString()
            sTextMaxChainWinNum?.text = SettingsUtils.getSettingMaxChainWinNum(context!!).toString()

            sTextPrefetture?.text = SettingsUtils.prefecture_items[SettingsUtils.getSettingRadioIdPrefecture(context!!)]
            sTextSex?.text = SettingsUtils.sex_items[SettingsUtils.getSettingRadioIdSex(context!!)]
            sTextAge?.text = SettingsUtils.age_items[SettingsUtils.getSettingRadioIdAge(context!!)]


        }

        fun setAwardData(){
            sTextWinNumAll?.text = AwardCoudFirestoreHelper.win_num_all_rank_user.toString() + "/" + AwardCoudFirestoreHelper.win_num_all_rank_everyone.toString().toString() + "位"
            sTextWinNumPrefecture?.text = AwardCoudFirestoreHelper.win_num_prefecture_rank_user.toString() + "/" + AwardCoudFirestoreHelper.win_num_prefecture_rank_everyone.toString().toString() + "位"
            sTextWinNumSex?.text = AwardCoudFirestoreHelper.win_num_sex_rank_user.toString() + "/" + AwardCoudFirestoreHelper.win_num_sex_rank_everyone.toString().toString() + "位"
            sTextWinNumAge?.text = AwardCoudFirestoreHelper.win_num_age_rank_user.toString() + "/" + AwardCoudFirestoreHelper.win_num_age_rank_everyone.toString().toString() + "位"

            sTextProbabilityAll?.text = AwardCoudFirestoreHelper.probability_all_rank_user.toString() + "/" + AwardCoudFirestoreHelper.probability_all_rank_everyone.toString().toString() + "位"
            sTextProbabilityPrefecture?.text = AwardCoudFirestoreHelper.probability_prefecture_rank_user.toString() + "/" + AwardCoudFirestoreHelper.probability_prefecture_rank_everyone.toString().toString() + "位"
            sTextProbabilitySex?.text = AwardCoudFirestoreHelper.probability_sex_rank_user.toString() + "/" + AwardCoudFirestoreHelper.probability_sex_rank_everyone.toString().toString() + "位"
            sTextProbabilityAge?.text = AwardCoudFirestoreHelper.probability_age_rank_user.toString() + "/" + AwardCoudFirestoreHelper.probability_age_rank_everyone.toString().toString() + "位"

            sTextMaxChainWinNumAll?.text = AwardCoudFirestoreHelper.max_chain_win_num_all_rank_user.toString() + "/" + AwardCoudFirestoreHelper.max_chain_win_num_all_rank_everyone.toString().toString() + "位"
            sTextMaxChainWinNumPrefecture?.text = AwardCoudFirestoreHelper.max_chain_win_num_prefecture_rank_user.toString() + "/" + AwardCoudFirestoreHelper.max_chain_win_num_prefecture_rank_everyone.toString().toString() + "位"
            sTextMaxChainWinNumSex?.text = AwardCoudFirestoreHelper.max_chain_win_num_sex_rank_user.toString() + "/" + AwardCoudFirestoreHelper.max_chain_win_num_sex_rank_everyone.toString().toString() + "位"
            sTextMaxChainWinNumAge?.text = AwardCoudFirestoreHelper.max_chain_win_num_age_rank_user.toString() + "/" + AwardCoudFirestoreHelper.max_chain_win_num_age_rank_everyone.toString().toString() + "位"
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
        /*
        CloudFirestoreHelper.getWinNum(db,"users",mContext!!)
        CloudFirestoreHelper.getProbability(db,"users",mContext!!)
        CloudFirestoreHelper.getMaxChainWinNum(db,"users",mContext!!)
        */
        AwardCoudFirestoreHelper.getAwardData(db,"users",mContext!!)

        /*
        do{
            LOGD(TAG, "DEBUG_DATA:sleep");

            try {
                Thread.sleep(500) //ミリ秒
            } catch (e: InterruptedException) {
            }
        } while (CloudFirestoreHelper.data_rady_flg == false)
        LOGD(TAG, "DEBUG_DATA:sleep OK");
        */
    }

    /*
    fun setAwardData(){
        LOGD(TAG, "DEBUG_DATA win_num_all_rank_everyonefewe:" + CloudFirestoreHelper.win_num_all_rank_everyone );
        sTextWinNumAll?.text = CloudFirestoreHelper.win_num_all_rank_user.toString() + "/" + CloudFirestoreHelper.win_num_all_rank_everyone.toString().toString() + "位"
    }
    */
}