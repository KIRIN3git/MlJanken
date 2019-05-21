package kirin3.jp.mljanken.mng

import android.content.Context
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import java.util.ArrayList

object GraphMng {



    fun setInitBar(bar_chart:BarChart,context:Context,mode:Int,labels:Array<String>,colors:IntArray,data_list:ArrayList<Float>){

        val chart: BarChart = bar_chart

        chart.getDescription().isEnabled = false

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(60)

        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false)
        chart.setTouchEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.setDrawValueAboveBar(true)
        chart.setDrawBarShadow(false)
        chart.setDrawGridBackground(false)

        // アニメーション速度
        chart.animateY(1000)
        // コンポーネントを利用するには true
        chart.getLegend().isEnabled = false

        // X軸の設定
        chart.xAxis.apply {
            // ラベルを表示するか
            setDrawLabels(true)
            // 表示させるラベル数
            labelCount = labels.size
            setValueFormatter(IndexAxisValueFormatter(labels))
            // 結果の数値の表示位置
            position = XAxis.XAxisPosition.BOTTOM
            // グリッドラインを表示するか
            setDrawGridLines(false)
            setDrawAxisLine(true)
        }

        //Y軸(左)の設定
        chart.axisLeft.apply {
            axisMinimum = 0f
            axisMaximum = 100f
            labelCount = 20
            setDrawTopYLabelEntry(true)
        }

        // Y軸（右）の設定
        chart.axisRight.apply {
            axisMinimum = 0f
            axisMaximum = 100f
            labelCount = 20
            setDrawTopYLabelEntry(true)
        }

        val data = BarData(getBarData(context,mode,colors,data_list))
        chart.setData(data)


    }

    fun getBarData(context: Context,mode: Int,colors:IntArray,data_list: ArrayList<Float>): ArrayList<IBarDataSet> {

        val values = ArrayList<BarEntry>()
        for (i in 0 until data_list.size) {

            values.add(BarEntry(i.toFloat(), data_list[i]))
        }

        val set1: BarDataSet

        set1 = BarDataSet(values, "Data Set").apply {

            // 個別で色を設定
            setColors(colors, context)

            // フォントサイズ
            setValueTextSize(15f)

        }

        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set1)

        return dataSets
    }
}