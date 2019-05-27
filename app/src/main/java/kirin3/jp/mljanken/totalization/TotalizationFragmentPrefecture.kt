package kirin3.jp.mljanken.totalization

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.util.Log
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

class TotalizationFragmentPrefecture() : Fragment() {
    val TAG = LogUtils.makeLogTag(CloudFirestoreHelper::class.java)

    var mContext: Context ?= null
    var mDbHelper: HandHelper? = null
    var mDb: SQLiteDatabase? = null
    var mChart: BarChart ?= null
    var mPosition: Int = 0 // 2～6 ViewPagerのポジション
    var mRankPg: Int = 0 // 1～5 1:1～10位,2:11:～20位,3:21:～30位,4:31:～40位,5:41:～47位

    companion object {
        var VIEW_POSITION: String = "VIEW_POSITION"
    }

    fun initStaticData(activity: FragmentActivity, view:View) {
        mContext = activity?.applicationContext
        mChart = view?.chart
    }

    fun drawGraph(){
        var dataNum = 0
        when(mRankPg){
            1,2,3,4 -> dataNum = 10
            else -> dataNum = 7
        }

        var labels = arrayOfNulls<String>(dataNum)
        var colors = IntArray(dataNum)
        var data = ArrayList<Float>();

        val prefecture_probability_sort = TotalizationCloudFirestoreHelper.prefecture_probability.toList().sortedByDescending { (_, value) -> value}.toMap()

        var i = -1
        var data_i = 0
        loop@ for (entry in prefecture_probability_sort) {
            i++

            when( mRankPg ){
                1 -> if( !(0 <= i && i <= 9) ) continue@loop
                2 -> if( !(10 <= i && i <= 19) ) continue@loop
                3 -> if( !(20 <= i && i <= 29) ) continue@loop
                4 -> if( !(30 <= i && i <= 39) ) continue@loop
                else -> if( !(40 <= i && i <= 47) ) continue@loop
            }

            // ラベルを登録
            labels[data_i] = SettingsUtils.prefecture_items[entry.key]

            // 色を登録
            when(entry.key){
                1 -> colors[data_i] = R.color.lightBlue3
                2,3,4,5,6,7 -> colors[data_i] = R.color.lightBlue
                8,9,10,11,12,13,14 -> colors[data_i] = R.color.lightGreen
                15,16,17,18,19,20 -> colors[data_i] = R.color.lightGreen2
                21,22,23,24,25,26 -> colors[data_i] = R.color.lightSalmon
                27,28,29,30,31,32,33,34,35 -> colors[data_i] = R.color.lightPurple
                36,37,38,39 -> colors[data_i] = R.color.lightGreen4
                40,41,42,43,44,45,46 -> colors[data_i] = R.color.yellow
                else -> colors[data_i] = R.color.lightRed2
            }

            // 確率データを登録
            data.add(entry.value)

            data_i++
        }

        GraphMng.setInitBar(mChart!!,mContext!!,0,labels,colors,data)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mDbHelper = HandHelper(activity!!.applicationContext)
        mDb = mDbHelper?.getWritableDatabase()

        // ViewPagerの位置からRankPgを選択
        val args = arguments
        if (args != null) {
            mPosition = args.getInt(VIEW_POSITION)
            when(mPosition){
                2 -> mRankPg = 1
                3 -> mRankPg = 2
                4 -> mRankPg = 3
                5 -> mRankPg = 4
                6 -> mRankPg = 5
            }
        }

        return inflater.inflate(R.layout.fragment_totalization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initStaticData(activity!!,view)

        drawGraph()
    }
}