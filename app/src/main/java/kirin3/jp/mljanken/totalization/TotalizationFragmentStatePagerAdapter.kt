package kirin3.jp.mljanken.totalization

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import android.os.Bundle



class TotalizationFragmentStatePagerAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentStatePagerAdapter(fm) {

    companion object {
        var nowPagePosition: Int = 0

        fun getPositiona(): Int {
            return nowPagePosition
        }
    }

    override fun getItem(i: Int): androidx.fragment.app.Fragment {
        when (i) {
            0 -> return TotalizationFragmentGender()
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

        nowPagePosition = position

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