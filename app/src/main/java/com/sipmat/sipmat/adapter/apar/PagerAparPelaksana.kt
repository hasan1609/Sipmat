package com.sipmat.sipmat.adapter.apar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sipmat.sipmat.pelaksana.aparpelaksana.uiapar.CekAparFragment
import com.sipmat.sipmat.pelaksana.aparpelaksana.uiapar.KadaluarsaAparFragment

class PagerAparPelaksana(fm: FragmentManager): FragmentPagerAdapter(fm) {

    private var fragmentList= ArrayList<Fragment>()
    private var titleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return when (position){
            0-> {
                CekAparFragment()
            }
            1->{
                KadaluarsaAparFragment()
            }

            else->
                CekAparFragment()
        }

    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0->"APAR"
            1->"APAR kadaluarsa"
            else ->"APAR"
        }
    }

    fun addFragment(
        fragment: Fragment?,
        title: String?
    ) {
        fragmentList.add(fragment!!)
        titleList.add(title!!)
    }

}
