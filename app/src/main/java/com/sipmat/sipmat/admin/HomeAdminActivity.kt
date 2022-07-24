package com.sipmat.sipmat.admin

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sipmat.sipmat.R
import com.sipmat.sipmat.admin.ui.ARSIP.ArsipFragment
import com.sipmat.sipmat.admin.ui.PELAKSANA.PelaksanaFragment
import com.sipmat.sipmat.admin.ui.PM.PMFragment
import com.sipmat.sipmat.session.SessionManager
import org.jetbrains.anko.startActivity

class HomeAdminActivity : AppCompatActivity() {

    lateinit var sessionManager: SessionManager
    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_arsip -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framehome,
                        ArsipFragment()
                    ).commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_pelaksana -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framehome,
                        PelaksanaFragment()
                    ).commit()
                    return@OnNavigationItemSelectedListener true

                }
                R.id.navigation_pm -> {
                    supportFragmentManager.beginTransaction().replace(
                        R.id.framehome,
                        PMFragment()
                    ).commit()
                    return@OnNavigationItemSelectedListener true

                }

                R.id.navigation_logout -> {
                    sessionManager.setLoginadmin(false)
                    sessionManager.setLogin(false)
                    sessionManager.setToken("")
                    startActivity<LoginAdminActivity>()
                    finish()

                }


            }

            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_admin)
        sessionManager = SessionManager(this)

        val navView: BottomNavigationView = findViewById(R.id.nav_viewhome)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        moveToFragment(ArsipFragment())

    }

    private fun moveToFragment(fragment: Fragment) {
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.framehome, fragment)
        fragmentTrans.commit()
    }

}