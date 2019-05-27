package kirin3.jp.mljanken.totalization

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.os.Bundle



class TotalizationFragmentStatePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    companion object {
        var sNowPagePosition: Int = 0

        fun getPositiona(): Int {
            return sNowPagePosition
        }
    }

    override fun getItem(i: Int): Fragment {
        when (i) {
            0 -> return TotalizationFragmentSex()
            1 -> return TotalizationFragmentAge()
            else -> {
                val fragment = TotalizationFragmentPrefecture()
                val args = Bundle()
                args.putInt(TotalizationFragmentPrefecture.VIEW_POSITION, i)
                fragment.setArguments(args)
                return fragment
            }
        }
    }

    override fun getCount(): Int {
        return 7
    }

    override fun getPageTitle(position: Int): CharSequence? {

        sNowPagePosition = position

        when (position) {
            0 -> return "性別勝率順位"
            1 -> return "年代別勝率順位"
            2 -> return "地域別勝率順位（１～１０位）"
            3 -> return "地域別勝率順位（１１～２０位）"
            4 -> return "地域別勝率順位（２１～３０位）"
            5 -> return "地域別勝率順位（３１～４０位）"
            else -> return "地域別勝率順位（４１～４７位）"
        }
    }
}