package com.anmp.myapplication


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.anmp.myapplication.databinding.ActivityLoginBinding
import com.anmp.myapplication.model.User
import com.anmp.myapplication.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.button.setOnClickListener {
            loginUser()
        }


        binding.txtRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser() {
        val username = binding.txtInputUsername.text.toString()
        val password = binding.txtPassword.editText?.text.toString()

        // Validasi input kosong
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Harap isi username dan password", Toast.LENGTH_SHORT).show()
            return
        }

        // Panggil fungsi login dari userViewModel
        userViewModel.loginUser(username, password)

        // Amati LiveData statusLoginLiveData untuk mendapatkan hasil dari operasi login
        userViewModel.statusLoginLiveData.observe(this) { status ->
            Log.d("status",status.toString());
            when (status) {

                    "Login successful" -> {
                    Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()

                    // Amati LiveData userLiveData untuk mendapatkan data pengguna yang telah login
                    userViewModel.userLiveData.observe(this) { loggedInUser ->
                        if (loggedInUser != null) {
                            // Simpan data pengguna yang telah login
                            saveUserData(loggedInUser)

                            // Redirect ke MainActivity setelah login berhasil
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                "Login failed" -> {
                    Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Handle other possible status values
                    Toast.makeText(this, "Status login: $status", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun saveUserData(loggedInUser: User) {
        // Simpan data user ke SharedPreferences
        val sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("id", loggedInUser.id)
        editor.putString("password",loggedInUser.password)
        editor.putString("username", loggedInUser.username)
        editor.putString("firstName", loggedInUser.firstName)
        editor.putString("lastName", loggedInUser.lastName)
        editor.putString("email", loggedInUser.email)
        editor.apply()
    }
}

