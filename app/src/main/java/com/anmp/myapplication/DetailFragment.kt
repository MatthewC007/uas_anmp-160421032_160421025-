package com.anmp.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anmp.myapplication.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var fullDescription: String
    private var currentPageIndex = 0
    private var totalPageCount = 0
    private val pageSize = 400

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = DetailFragmentArgs.fromBundle(requireArguments())
        val selectedItem = args.item
        binding.news = selectedItem

        fullDescription = selectedItem.description
        totalPageCount = calculateTotalPages(fullDescription)

        // Inisialisasi halaman pertama
        currentPageIndex = 0
        binding.txtCurrentPage.text = (currentPageIndex + 1).toString() // Mulai dari halaman 1
        binding.txtTotalPage.text = totalPageCount.toString()

        updateDescriptionText(currentPageIndex)

        binding.txtJudul.text = selectedItem.title
        binding.txtPenulis.text = selectedItem.username
        Picasso.get()
            .load(selectedItem.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(binding.imageView)


        binding.btnNext.setOnClickListener {
            if (currentPageIndex < totalPageCount - 1) {
                currentPageIndex++
                binding.txtCurrentPage.text = (currentPageIndex + 1).toString() // Mengubah indeks ke nomor halaman
                updateDescriptionText(currentPageIndex)
            }
        }


        binding.btnBack.setOnClickListener {
            if (currentPageIndex > 0) {
                currentPageIndex--
                binding.txtCurrentPage.text = (currentPageIndex + 1).toString() // Mengubah indeks ke nomor halaman
                updateDescriptionText(currentPageIndex)
            }
        }
    }

    private fun calculateTotalPages(description: String): Int {
        return if (description.isEmpty()) {
            0
        } else {
            (description.length + pageSize - 1) / pageSize
        }
    }

    private fun updateDescriptionText(pageIndex: Int) {
        val startIndex = pageIndex * pageSize
        val endIndex = startIndex + pageSize
        val displayText = fullDescription.substring(startIndex, endIndex.coerceAtMost(fullDescription.length))
        binding.txtDeskripsi.text = displayText
    }
}
