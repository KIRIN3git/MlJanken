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
import kotlinx.android.synthetic.main.fragment_totalization.view.*
import java.util.ArrayList


class TotalizationFragmentSex : Fragment() {
    val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    private var mDbHelper: HandHelper? = null
    private var mDb: SQLiteDatabase? = null

    companion object {


        var sSexChart: BarChart ?= null
        var sContext: Context ?= null

        fun initStaticData(activity: FragmentActivity, view:View) {
            sContext = activity?.applicationContext
            sSexChart = view?.sexChart
        }
        fun drawGraph(){
            val labels = arrayOf("男性","女性")
            val colors = intArrayOf(R.color.lightBlue,R.color.lightRed)

            var data = ArrayList<Float>();
            data.add(TotalizationCoudFirestoreHelper.sex_probability[0])
            data.add(TotalizationCoudFirestoreHelper.sex_probability[1])
            GraphMng.setInitBar(sSexChart!!,sContext!!,0,labels,colors,data)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mDbHelper = HandHelper(activity!!.applicationContext)
        mDb = mDbHelper?.getWritableDatabase()

        return inflater.inflate(R.layout.fragment_totalization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LOGD(TAG, "DEBUG_DATA:onViewCreated");

        initStaticData(activity!!,view)


        drawGraph()
    }
}