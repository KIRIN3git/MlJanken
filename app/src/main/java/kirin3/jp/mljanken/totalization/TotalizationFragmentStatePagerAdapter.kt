package kirin3.jp.mljanken.totalization

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class TotalizationFragmentStatePagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(i: Int): Fragment {

        when (i) {
            0 -> return TotalizationFragment1()
            1 -> return TotalizationFragment2()
            else -> return TotalizationFragment3()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Page $position"
    }
}