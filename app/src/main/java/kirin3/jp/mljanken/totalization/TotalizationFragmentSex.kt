package kirin3.jp.mljanken.totalization

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.BarChart
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.data.HandHelper
import kirin3.jp.mljanken.mng.GraphMng
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kirin3.jp.mljanken.util.SettingsUtils
import kirin3.jp.mljanken.util.SettingsUtils.TAG
import kotlinx.android.synthetic.main.fragment_totalization.view.*
import java.util.ArrayList


class TotalizationFragmentSex : Fragment() {
    val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    private var mDbHelper: HandHelper? = null
    private var mDb: SQLiteDatabase? = null

    companion object {
        var sChart: BarChart ?= null
        var sContext: Context ?= null

        fun initStaticData(activity: FragmentActivity, view:View) {
            sContext = activity?.applicationContext
            sChart = view?.chart
        }
        fun drawGraph(){
            var labels = arrayOfNulls<String>(2)

            for (entry in TotalizationCoudFirestoreHelper.sex_probability) {
                LOGD(TAG, "bbb333 Key: " + entry.key)
                LOGD(TAG, "bbb333 Value: " + entry.value)
            }

            val sex_probability_sort = TotalizationCoudFirestoreHelper.sex_probability.toList().sortedByDescending { (_, value) -> value}.toMap()
            var i = 0
            for (entry in sex_probability_sort) {
                LOGD(TAG, "bbb444 Key: " + entry.key)
                LOGD(TAG, "bbb444 Value: " + entry.value)

                labels[i] = SettingsUtils.sex_items[entry.key]

                i++
            }

            val colors = intArrayOf(R.color.lightBlue,R.color.lightRed)

            var data = ArrayList<Float>();

            LOGD(TAG, "DEBUG_DATA TotalizationCoudFirestoreHelper.sex_probability[0]:" +  TotalizationCoudFirestoreHelper.sex_probability[0]);

//            data.add(TotalizationCoudFirestoreHelper.sex_probability[0])
//            data.add(TotalizationCoudFirestoreHelper.sex_probability[1])
            data.add(56f)
            data.add(46f)

            GraphMng.setInitBar(sChart!!,sContext!!,0,labels,colors,data)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mDbHelper = HandHelper(activity!!.applicationContext)
        mDb = mDbHelper?.getWritableDatabase()

        return inflater.inflate(R.layout.fragment_totalization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LOGD(TAG, "DEBUG_DATA:onViewCreated   1");

        initStaticData(activity!!,view)

        drawGraph()
    }
}