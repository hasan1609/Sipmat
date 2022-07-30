package com.sipmat.sipmat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.sipmat.sipmat.admin.LoginAdminActivity
import com.sipmat.sipmat.admin.ui.ARSIP.ArsipFragment
import com.sipmat.sipmat.admin.ui.PELAKSANA.PelaksanaFragment
import com.sipmat.sipmat.admin.ui.PM.PMFragment
import com.sipmat.sipmat.pelaksana.apatpelaksana.DetailCekApatActivity
import com.sipmat.sipmat.session.SessionManager
import com.sipmat.sipmat.ui.HomeFragment
import com.sipmat.sipmat.ui.ProfilFragment
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.toast

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
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("Logout ? ")
                    builder.setPositiveButton("Ok") { dialog, which ->
                        sessionManager.setLoginadmin(false)
                        sessionManager.setLogin(false)
                        sessionManager.setToken("")
                        startActivity<LoginAdminActivity>()
                        toast("Berhasil Logout")
                        finish()
                    }


                    builder.setNegativeButton("Cancel ?") { dialog, which ->

                    }

                    builder.show()

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