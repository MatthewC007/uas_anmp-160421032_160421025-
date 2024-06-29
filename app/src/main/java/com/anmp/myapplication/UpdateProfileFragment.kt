package com.anmp.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.anmp.myapplication.databinding.FragmentProfileBinding
import com.anmp.myapplication.databinding.FragmentUpdateProfileBinding
import com.anmp.myapplication.viewmodel.UserViewModel


class UpdateProfileFragment : Fragment() {

    private lateinit var binding: FragmentUpdateProfileBinding
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateProfileBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding.btnUpdate.setOnClickListener {
            val firstName = binding.editTextFirstName.text.toString()
            val lastName = binding.editTextLastName.text.toString()

            if (firstName.isEmpty() || lastName.isEmpty()) {
                Toast.makeText(requireContext(), "Harap isi semua kolom", Toast.LENGTH_SHORT).show()

            }
            else{
                update()
            }
        }



    }

    private fun update() {
        val sharedPreferences = requireActivity().getSharedPreferences("userData", Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("id", 0)

        val firstName = binding.editTextFirstName.text.toString()
        val lastName = binding.editTextLastName.text.toString()


        userViewModel.updateUserProfile(id, firstName, lastName)


        userViewModel.statusUpdateProfileLiveData.observe(viewLifecycleOwner) { status ->
            when (status) {
                "Profile updated successfully" -> {
                    Toast.makeText(requireContext(), "Profile Berhasil Di Update", Toast.LENGTH_SHORT).show()


                    val editor = sharedPreferences.edit()
                    editor.putString("firstName", firstName)
                    editor.putString("lastName", lastName)
                    editor.apply()


                    val action = UpdateProfileFragmentDirections.actionUpdateProfileFragmentToItemProfile()
                    Navigation.findNavController(requireView()).navigate(action)
                }
                "Error updating profile" -> {
                    Toast.makeText(requireContext(), "Profile Gagal Di Update", Toast.LENGTH_SHORT).show()
                }
                else -> {

                    Toast.makeText(requireContext(), "Status update profile: $status", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }




}