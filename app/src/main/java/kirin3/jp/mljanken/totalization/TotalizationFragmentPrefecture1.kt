package kirin3.jp.mljanken.totalization

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import kirin3.jp.mljanken.R
import kirin3.jp.mljanken.data.HandHelper
import kirin3.jp.mljanken.mng.GraphMng
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kotlinx.android.synthetic.main.fragment_totalization.*
import kotlinx.android.synthetic.main.fragment_totalization.view.*
import java.util.ArrayList


class TotalizationFragmentPrefecture1 : Fragment() {
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
            val labels = arrayOf("0～9歳","10代","20代","30代","40代","50代","60代","70代","80～")
            val colors = intArrayOf(R.color.lightBlue,R.color.lightRed)

            var data = ArrayList<Float>();
            data.add(TotalizationCoudFirestoreHelper.age_probability[0])
            data.add(TotalizationCoudFirestoreHelper.age_probability[1])
            data.add(TotalizationCoudFirestoreHelper.age_probability[2])
            data.add(TotalizationCoudFirestoreHelper.age_probability[3])
            data.add(TotalizationCoudFirestoreHelper.age_probability[4])
            data.add(TotalizationCoudFirestoreHelper.age_probability[5])
            data.add(TotalizationCoudFirestoreHelper.age_probability[6])
            data.add(TotalizationCoudFirestoreHelper.age_probability[7])
            data.add(TotalizationCoudFirestoreHelper.age_probability[8])
//            GraphMng.setInitBar(sChart!!,sContext!!,0,labels,colors,data)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mDbHelper = HandHelper(activity!!.applicationContext)
        mDb = mDbHelper?.getWritableDatabase()

        return inflater.inflate(R.layout.fragment_totalization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LOGD(TAG, "DEBUG_DATA:onViewCreated   2");

        initStaticData(activity!!,view)


        drawGraph()
    }
}