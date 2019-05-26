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
            var colors = IntArray(2)
            var data = ArrayList<Float>();

            val age_probability_sort = TotalizationCoudFirestoreHelper.age_probability.toList().sortedByDescending { (_, value) -> value}.toMap()
            var i = 0
            for (entry in age_probability_sort) {
                // 男性、女性を登録
                labels[i] = SettingsUtils.age_items[entry.key]
                // 色を登録
                when(entry.key){
                    1 -> colors[i] = R.color.lightBlue
                    else -> colors[i] = R.color.lightRed
                }
                // 確率データを登録
                data.add(entry.value)

                i++
            }

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

        initStaticData(activity!!,view)

        drawGraph()
    }
}