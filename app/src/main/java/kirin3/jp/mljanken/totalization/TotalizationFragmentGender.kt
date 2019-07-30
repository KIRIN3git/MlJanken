package kirin3.jp.mljanken.totalization

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
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


class TotalizationFragmentGender : androidx.fragment.app.Fragment() {
    val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    var appContext: Context? = null
    var dbHelper: HandHelper? = null
    var db: SQLiteDatabase? = null
    private lateinit var chart: BarChart

    fun initStaticData(activity: androidx.fragment.app.FragmentActivity, view: View) {
        appContext = activity.applicationContext
        chart = view.findViewById(R.id.chart)
    }

    fun drawGraph() {
        var labels = arrayOfNulls<String>(2)
        var colors = IntArray(2)
        var data = ArrayList<Float>();

        val gender_probability_sort =
            TotalizationCloudFirestoreHelper.genderProbability.toList().sortedByDescending { (_, value) -> value }.toMap()
        var i = 0
        for (entry in gender_probability_sort) {
            // ラベルを登録
            labels[i] = SettingsUtils.genderItems[entry.key]
            // 色を登録
            when (entry.key) {
                1 -> colors[i] = R.color.lightBlue
                else -> colors[i] = R.color.lightRed
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