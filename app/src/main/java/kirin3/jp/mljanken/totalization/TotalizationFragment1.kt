package kirin3.jp.mljanken.totalization

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v4.app.Fragment
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
import kirin3.jp.mljanken.util.CloudFirestoreHelper
import kirin3.jp.mljanken.util.LogUtils
import kirin3.jp.mljanken.util.LogUtils.LOGD
import kotlinx.android.synthetic.main.fragment_totalization.*
import java.util.ArrayList


class TotalizationFragment1 : Fragment() {
    val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    private var mContext: Context? = null
    private var mDbHelper: HandHelper? = null
    private var mDb: SQLiteDatabase? = null
    private var chart: BarChart? = null

    companion object {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mContext = activity?.applicationContext
        mDbHelper = HandHelper(activity!!.applicationContext)
        mDb = mDbHelper?.getWritableDatabase()

        return inflater.inflate(R.layout.fragment_totalization, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LOGD(TAG, "DEBUG_DATA:eeeeeeeeeeeeeeeeeeeeeeeeeezzzzzzzzzzzz");

        setInitBar()

    }



    fun setInitBar(){

        chart = chart1

        chart!!.getDescription().isEnabled = false

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart!!.setMaxVisibleValueCount(60)

        // scaling can now only be done on x- and y-axis separately
        chart!!.setPinchZoom(false)

        chart!!.setDrawValueAboveBar(true)
        chart!!.setDrawBarShadow(false)
        chart!!.setDrawGridBackground(false)

        // アニメーション速度p
        chart!!.animateY(1500)
        // コンポーネントを利用するには true
        chart!!.getLegend().isEnabled = false


        // X軸の設定
        val labels = arrayOf("東京","大阪","北海道","和歌山","長野","神奈川","栃木","鹿児島","大分","愛媛") //最初の””は原点の値
        chart!!.xAxis.apply {
            // ラベルを表示するか
            setDrawLabels(true)
            // 表示させるラベル数
            labelCount = 10
            valueFormatter = IndexAxisValueFormatter(labels)
            // 結果の数値の表示位置
            position = XAxis.XAxisPosition.BOTTOM
            // グリッドラインを表示するか
            setDrawGridLines(false)
            setDrawAxisLine(true)
        }


        //Y軸(左)の設定
        chart!!.axisLeft.apply {
            axisMinimum = 0f
            axisMaximum = 100f
            labelCount = 10
            setDrawTopYLabelEntry(true)
        }


        // Y軸（右）の設定
        chart!!.axisRight.apply {

            //            setDrawLabels(true)
//            setDrawGridLines(true)
//            setDrawZeroLine(true)

            axisMinimum = 0f
            axisMaximum = 100f
            labelCount = 10
            setDrawTopYLabelEntry(true)
        }

        val data = BarData(getBarData())
        chart!!.setData(data)



    }

    fun getBarData(): ArrayList<IBarDataSet> {

        val values = ArrayList<BarEntry>()

        for (i in 0 until 10) {
            val multi = (100 + 1).toFloat()
            val `val` = (Math.random() * multi).toFloat()
            values.add(BarEntry(i.toFloat(), `val`))
        }

        val set1: BarDataSet

        set1 = BarDataSet(values, "Data Set").apply {

            // ランダムで色をセット
            // setColors(*ColorTemplate.VORDIPLOM_COLORS)
            // 個別で色を設定
            setColors(intArrayOf(
                R.color.abc_btn_colored_borderless_text_material,
                R.color.abc_btn_colored_borderless_text_material,
                R.color.abc_btn_colored_borderless_text_material,
                R.color.material_blue_grey_800,
                R.color.material_blue_grey_800,
                R.color.material_blue_grey_800,
                R.color.material_blue_grey_800,
                R.color.material_blue_grey_800,
                R.color.material_blue_grey_800,
                R.color.material_blue_grey_800), mContext)

            // フォントサイズ
            setValueTextSize(15f)

            //整数で表示
            //alueFormatter = IValueFormatter { value, _, _, _ -> "" + value.toInt() }
        }



        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set1)

        return dataSets
    }

}