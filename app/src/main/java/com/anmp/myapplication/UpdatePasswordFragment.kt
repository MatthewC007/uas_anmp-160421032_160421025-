package com.anmp.myapplication

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.anmp.myapplication.databinding.FragmentUpdatePasswordBinding
import com.anmp.myapplication.databinding.FragmentUpdateProfileBinding
import com.anmp.myapplication.viewmodel.UserViewModel


class UpdatePasswordFragment : Fragment() {

    private lateinit var binding: FragmentUpdatePasswordBinding
    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdatePasswordBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding.btnUpdatePassword.setOnClickListener {
            val oldPassword = binding.editTextOldPassword.text.toString()
            val newPassword = binding.editTextNewPassword.text.toString()
            if (oldPassword.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(requireContext(), "Harap isi semua kolom", Toast.LENGTH_SHORT).show()

            }
            else{
                updatePassword()
            }

        }

        }
    private fun updatePassword() {
        val sharedPreferences = requireActivity().getSharedPreferences("userData", Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt("id", 0)
        val oldPass = sharedPreferences.getString("password", "")

        val oldPassword = binding.editTextOldPassword.text.toString()
        val newPassword = binding.editTextNewPassword.text.toString()

        if (oldPassword == oldPass) {
            userViewModel.updatePassword(id, newPassword)

            userViewModel.statusUpdatePasswordLiveData.observe(viewLifecycleOwner) { status ->
                when (status) {
                    "Password updated successfully" -> {
                        Toast.makeText(requireContext(), "Password Berhasil Di Update", Toast.LENGTH_SHORT).show()


                        val editor = sharedPreferences.edit()
                        editor.putString("password", newPassword)
                        editor.apply()


                        val action = UpdatePasswordFragmentDirections.actionUpdatePasswordFragmentToItemProfile()
                        Navigation.findNavController(requireView()).navigate(action)
                    }
                    "Error updating password" -> {
                        Toast.makeText(requireContext(), "Password Gagal Di Update", Toast.LENGTH_SHORT).show()
                    }
                    else -> {

                        Toast.makeText(requireContext(), "Status update password: $status", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "Password Lama Salah", Toast.LENGTH_SHORT).show()
        }
    }







}


