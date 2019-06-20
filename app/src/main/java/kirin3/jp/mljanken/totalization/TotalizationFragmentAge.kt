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
import kirin3.jp.mljanken.mng.GraphMng
import kirin3.jp.mljanken.mng.databaseMng.HandHelper
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.SettingsUtils
import java.util.*


class TotalizationFragmentAge : Fragment() {
    val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    var appContext: Context? = null
    var dbHelper: HandHelper? = null
    var db: SQLiteDatabase? = null
    private lateinit var chart: BarChart

    companion object {
    }

    fun initStaticData(activity: FragmentActivity, view: View) {
        appContext = activity.applicationContext
        chart = view.findViewById(R.id.chart)
    }

    fun drawGraph() {
        var labels = arrayOfNulls<String>(9)
        var colors = IntArray(9)
        var data = ArrayList<Float>();

        val age_probability_sort =
            TotalizationCloudFirestoreHelper.ageProbability.toList().sortedByDescending { (_, value) -> value }.toMap()
        var i = 0
        for (entry in age_probability_sort) {
            // ラベルを登録
            labels[i] = SettingsUtils.ageItems[entry.key]
            // 色を登録
            when (entry.key) {
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
        GraphMng.setInitBar(chart, appContext!!, 0, labels, colors, data)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dbHelper = HandHelper(activity!!.applicationContext)
        db = dbHelper?.getWritableDatabase()

        return inflater.inflate(R.layout.fragment_totalization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initStaticData(activity!!, view)

        drawGraph()
    }
}