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

class TotalizationFragmentPrefecture() : Fragment() {
    val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    var appContext: Context? = null
    var dbHelper: HandHelper? = null
    var db: SQLiteDatabase? = null
    var chart: BarChart? = null
    var position: Int = 0 // 2～6 ViewPagerのポジション
    var rankPg: Int = 0 // 1～5 1:1～10位,2:11:～20位,3:21:～30位,4:31:～40位,5:41:～47位

    companion object {
        var VIEW_POSITION: String = "VIEW_POSITION"
    }

    fun initStaticData(activity: FragmentActivity, view: View) {
        appContext = activity.applicationContext
        chart = view.findViewById(R.id.chart)
    }

    fun drawGraph() {
        var dataNum = 0
        when (rankPg) {
            1, 2, 3, 4 -> dataNum = 10
            else -> dataNum = 7
        }

        var labels = arrayOfNulls<String>(dataNum)
        var colors = IntArray(dataNum)
        var data = ArrayList<Float>();

        val prefecture_probability_sort =
            TotalizationCloudFirestoreHelper.prefectureProbability.toList().sortedByDescending { (_, value) -> value }
                .toMap()

        var i = -1
        var data_i = 0
        loop@ for (entry in prefecture_probability_sort) {
            i++

            when (rankPg) {
                1 -> if (!(0 <= i && i <= 9)) continue@loop
                2 -> if (!(10 <= i && i <= 19)) continue@loop
                3 -> if (!(20 <= i && i <= 29)) continue@loop
                4 -> if (!(30 <= i && i <= 39)) continue@loop
                else -> if (!(40 <= i && i <= 47)) continue@loop
            }

            // ラベルを登録
            labels[data_i] = SettingsUtils.prefectureItems[entry.key]

            // 色を登録
            when (entry.key) {
                1 -> colors[data_i] = R.color.lightBlue3
                2, 3, 4, 5, 6, 7 -> colors[data_i] = R.color.lightBlue
                8, 9, 10, 11, 12, 13, 14 -> colors[data_i] = R.color.lightGreen
                15, 16, 17, 18, 19, 20 -> colors[data_i] = R.color.lightGreen2
                21, 22, 23, 24, 25, 26 -> colors[data_i] = R.color.lightSalmon
                27, 28, 29, 30, 31, 32, 33, 34, 35 -> colors[data_i] = R.color.lightPurple
                36, 37, 38, 39 -> colors[data_i] = R.color.lightGreen4
                40, 41, 42, 43, 44, 45, 46 -> colors[data_i] = R.color.yellow
                else -> colors[data_i] = R.color.lightRed2
            }

            // 確率データを登録
            data.add(entry.value)

            data_i++
        }

        GraphMng.setInitBar(chart!!, appContext!!, 0, labels, colors, data)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dbHelper = HandHelper(activity!!.applicationContext)
        db = dbHelper?.getWritableDatabase()

        // ViewPagerの位置からRankPgを選択
        val args = arguments
        if (args != null) {
            position = args.getInt(VIEW_POSITION)
            when (position) {
                2 -> rankPg = 1
                3 -> rankPg = 2
                4 -> rankPg = 3
                5 -> rankPg = 4
                6 -> rankPg = 5
            }
        }

        return inflater.inflate(R.layout.fragment_totalization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initStaticData(activity!!, view)

        drawGraph()
    }
}