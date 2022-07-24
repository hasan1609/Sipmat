package com.sipmat.sipmat

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.sipmat.sipmat.session.SessionManager
import com.sipmat.sipmat.webservice.ApiClient
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var api = ApiClient.instance()

    var token : String? = null
    lateinit var progressDialog: ProgressDialog
    lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        progressDialog = ProgressDialog(this)
        sessionManager = SessionManager(this)

        btnlogin.setOnClickListener {
            val username = edtusername.text.toString().trim()
            val password = edtpassword.text.toString().trim()
            if (username.isNotEmpty() && password.isNotEmpty()){
//                gettoken(username,password)
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


/*    private fun gettoken(username : String, password : String) {
        loading(true)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                toast("gagal dapat token")
                return@OnCompleteListener
            }
            // Get new FCM registration token
            token = task.result
            if (token != null) {
                api.login(username,password,token!!).enqueue(object : Callback<LoginResponse>{
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        try {
                            if (response.body()!!.status ==1) {
                                sessionManager.setToken(response.body()!!.data!!.token_id!!)
                                sessionManager.setLogin(true)
                                progressDialog.dismiss()
                                toast("login berhasil")
                                startActivity<MainActivity>()
                                finish()
                            } else {
                                progressDialog.dismiss()
                                Snackbar.make(view, "Email atau password salah", Snackbar.LENGTH_SHORT).show()

                            }


                        }catch (e : Exception){
                            progressDialog.dismiss()
                            info { "dinda ${e.message }${response.code()} " }
                        }

                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })

            }
        })
    }*/

}