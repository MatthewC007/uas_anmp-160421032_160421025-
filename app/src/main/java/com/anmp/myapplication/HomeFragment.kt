package com.anmp.myapplication

import NewsViewModel
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.anmp.myapplication.databinding.FragmentHomeBinding
import com.anmp.myapplication.model.AuthorDAO
import com.anmp.myapplication.model.HobbyDatabase
import com.anmp.myapplication.model.News



class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView( inflater: LayoutInflater, container:
    ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sharedFile = "userData"
        var shared: SharedPreferences = this.requireActivity().getSharedPreferences(sharedFile,
            Context.MODE_PRIVATE )
        var user_id: Int? = shared.getInt("id",0)



        Log.d("UserData", "User ID from SharedPreferences: $user_id")


        if (user_id != null && user_id != 0) {

            newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
//            newsViewModel.inputNews()
            newsViewModel.refresh()

            newsViewModel.newsLiveData.observe(viewLifecycleOwner) { newsList ->
                setupRecyclerView(newsList)


                Log.d("DATA", newsList.toString())
            }

        } else {

            val action = HomeFragmentDirections.actionLogin()
            Navigation.findNavController(view).navigate(action)
        }
    }
    private fun setupRecyclerView(newsList: List<News>) {
        val adapter = NewsListAdapter(newsList)
        binding.rvHome.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }



}

