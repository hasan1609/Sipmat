package com.sipmat.sipmat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sipmat.sipmat.admin.LoginAdminActivity
import com.sipmat.sipmat.admin.ui.ARSIP.ArsipFragment
import com.sipmat.sipmat.admin.ui.PELAKSANA.PelaksanaFragment
import com.sipmat.sipmat.admin.ui.PM.PMFragment
import com.sipmat.sipmat.session.SessionManager
import com.sipmat.sipmat.ui.HomeFragment
import com.sipmat.sipmat.ui.ProfilFragment
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framehome,
                        HomeFragment()
                    ).commit()
                    return@OnNavigationItemSelectedListener true
                }


                R.id.navigation_logout -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framehome,
                        ProfilFragment()
                    ).commit()
                    return@OnNavigationItemSelectedListener true

                }


            }

            false
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sessionManager = SessionManager(this)

        val navView: BottomNavigationView = findViewById(R.id.nav_viewhome)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        moveToFragment(HomeFragment())
    }


    private fun moveToFragment(fragment: Fragment) {
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.framehome, fragment)
        fragmentTrans.commit()
    }

}