package com.anmp.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.anmp.myapplication.databinding.ActivityRegisterBinding
import com.anmp.myapplication.model.User
import com.anmp.myapplication.viewmodel.UserViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val username = binding.editTextUsername.text.toString()
        val firstName = binding.editTextFirstName.text.toString()
        val lastName = binding.editTextLastName.text.toString()
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
        val confirmPassword = binding.editTextConfirmPassword.text.toString()

        // Validasi input kosong
        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
            return
        }

        // Validasi password dan konfirmasi password cocok
        if (password != confirmPassword) {
            Toast.makeText(this, "Password dan konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show()
            return
        }

        // Panggil fungsi register dari userViewModel
        userViewModel.registerUser(User(username = username, firstName = firstName, lastName = lastName, email = email, password = password))

        // Amati LiveData statusRegisterLiveData untuk mendapatkan hasil dari operasi registrasi
        userViewModel.statusRegisterLiveData.observe(this) { status ->
            when (status) {
                "Registration successful" -> {
                    Toast.makeText(this, "Register Berhasil, Silahkan Login", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                "Registration failed" -> {
                    Toast.makeText(this, "Register Gagal", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Handle other possible status values
                    Toast.makeText(this, "Status registrasi: $status", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
