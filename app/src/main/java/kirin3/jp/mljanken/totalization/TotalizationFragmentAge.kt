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
import kirin3.jp.mljanken.util.SettingsUtils
import kotlinx.android.synthetic.main.fragment_totalization.view.*
import java.util.ArrayList


class TotalizationFragmentAge : Fragment() {
    val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    var mContext: Context ?= null
    var mDbHelper: HandHelper? = null
    var mDb: SQLiteDatabase? = null
    var mChart: BarChart ?= null

    companion object {
    }

    fun initStaticData(activity: FragmentActivity, view:View) {
        mContext = activity?.applicationContext
        mChart = view?.chart
    }
    fun drawGraph(){
        var labels = arrayOfNulls<String>(9)
        var colors = IntArray(9)
        var data = ArrayList<Float>();

        val age_probability_sort = TotalizationCloudFirestoreHelper.age_probability.toList().sortedByDescending { (_, value) -> value}.toMap()
        var i = 0
        for (entry in age_probability_sort) {
            // ラベルを登録
            labels[i] = SettingsUtils.age_items[entry.key]
            // 色を登録
            when(entry.key){
                1 -> colors[i] = R.color.lightRed
                2 -> colors[i] = R.color.lightOrange
                3 -> colors[i] = R.color.lightPink
                4 -> colors[i] = R.color.lightYellow
                5 -> colors[i] = R.color.lightGreen4
                6 -> colors[i] = R.color.lightGreen2
                7 -> colors[i] = R.color.lightPurple
                8 -> colors[i] = R.color.lightPurple2
                else -> colors[i] = R.color.lightBlue2
            }
            // 確率データを登録
            data.add(entry.value)

            i++
        }
        GraphMng.setInitBar(mChart!!,mContext!!,0,labels,colors,data)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mDbHelper = HandHelper(activity!!.applicationContext)
        mDb = mDbHelper?.getWritableDatabase()

        return inflater.inflate(R.layout.fragment_totalization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initStaticData(activity!!,view)

        drawGraph()
    }
}