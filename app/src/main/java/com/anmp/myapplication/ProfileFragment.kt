package com.anmp.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.anmp.myapplication.databinding.FragmentHomeBinding
import com.anmp.myapplication.databinding.FragmentProfileBinding
import com.anmp.myapplication.model.User


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val sharedPreferences = requireActivity().getSharedPreferences("userData", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", "")
        val firstName = sharedPreferences.getString("firstName", "")
        val lastName = sharedPreferences.getString("lastName", "")
        val email = sharedPreferences.getString("email", "")
        val password = sharedPreferences.getString("password", "")
        val user = User(
            username = username.toString(),
            firstName = firstName.toString(),
            lastName = lastName.toString(),
            email = email.toString(),
            password = password.toString()
        )
        binding.user=user

        // Mengatur teks TextView di XML dengan nilai yang diperoleh dari SharedPreferences
        binding.valuefirst.text = firstName
        binding.valuelast.text = lastName
        binding.valueemail.text = email
        binding.valuedate.text = username
        binding.btnLogOut.setOnClickListener {
           val sharedPreferences = requireActivity().getSharedPreferences("userData", Context.MODE_PRIVATE)
           val editor: SharedPreferences.Editor = sharedPreferences.edit()
           editor.clear()
           editor.apply()
           val intent = Intent(requireActivity(), LoginActivity::class.java)
           startActivity(intent)
           requireActivity().finish()

       }
        binding.editprofile.setOnClickListener {
            val action = ProfileFragmentDirections.actionItemProfileToUpdateProfileFragment()
            Navigation.findNavController(view).navigate(action)
        }
        binding.editpassword.setOnClickListener {
            val action = ProfileFragmentDirections.actionItemProfileToUpdatePasswordFragment()
            Navigation.findNavController(view).navigate(action)
        }
    }


}