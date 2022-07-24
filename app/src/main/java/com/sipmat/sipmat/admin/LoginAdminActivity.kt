package com.sipmat.sipmat.admin

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.sipmat.sipmat.MainActivity
import com.sipmat.sipmat.R
import com.sipmat.sipmat.model.LoginResponse
import com.sipmat.sipmat.session.SessionManager
import com.sipmat.sipmat.webservice.ApiClient
import kotlinx.android.synthetic.main.activity_login_admin.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginAdminActivity : AppCompatActivity(),AnkoLogger {


    var api = ApiClient.instance()

    var token : String? = null
    lateinit var progressDialog: ProgressDialog
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_admin)
        progressDialog = ProgressDialog(this)
        sessionManager = SessionManager(this)

        btnlogin.setOnClickListener {
            val username = edtusername.text.toString().trim()
            val password = edtpassword.text.toString().trim()
            if (username.isNotEmpty() && password.isNotEmpty()){
                gettoken(username,password,it)
            }else{
                snackbar("jangan kosongi kolom",it)
            }
        }
    }

    fun snackbar(text : String, view : View){
        Snackbar.make(view,text,3000).show()
    }

    fun loading(status : Boolean){
        if (status){
            progressDialog.setTitle("Loading...")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()
        }else{
            progressDialog.dismiss()
        }
    }

    private fun gettoken(username : String, password : String,view : View) {
        loading(true)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                toast("gagal dapat token")
                return@OnCompleteListener
            }
            // Get new FCM registration token
            token = task.result
            if (token != null) {
                api.login(username,password,token!!).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        try {
                            if (response.isSuccessful){
                                if (response.body()!!.status ==1) {
                                    sessionManager.setToken(response.body()!!.data!!.tokenId!!)
                                    sessionManager.setLoginadmin(true)
                                    loading(false)
                                    toast("login berhasil")
                                    startActivity<HomeAdminActivity>()
                                    finish()
                                }else if (response.body()!!.status ==2){
                                    sessionManager.setToken(response.body()!!.data!!.tokenId!!)
                                    sessionManager.setNama(response.body()!!.data!!.username!!)
                                    sessionManager.setLogin(true)
                                    loading(false)
                                    toast("login berhasil")
                                    startActivity<MainActivity>()

                                }
                                else {
                                    loading(false)
                                    snackbar("Email atau password salah",view)

                                }

                            }else{
                                snackbar("Kesalahan aplikasi",view)
                            }


                        }catch (e : Exception){
                            loading(false)
                            info { "dinda ${e.message }${response.code()} " }
                        }

                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        loading(false)
                        info { "dinda ${t.message } " }
                        snackbar("Kesalahan Jaringan",view)

                    }

                })

            }
        })
    }

    override fun onStart() {
        super.onStart()
        if (sessionManager.getLoginadmin() == true){
            startActivity<HomeAdminActivity>()
            finish()
        }else if (sessionManager.getLogin() == true){
            startActivity<MainActivity>()
            finish()
        }
    }
}