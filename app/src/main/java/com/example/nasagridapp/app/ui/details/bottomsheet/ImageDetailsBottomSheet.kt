package com.example.nasagridapp.app.ui.details.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nasagridapp.app.model.ImageModel
import com.example.nasagridapp.databinding.BottomsheetImageDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ImageDetailsBottomSheet(var imageDetails: ImageModel) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomsheetImageDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BottomsheetImageDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        binding.closeBtSheetIcon.setOnClickListener { dismiss() }
        binding.imageTitleBtSheet.text = imageDetails.title
        binding.imageDetailsBtSheet.text = imageDetails.explanation
        binding.imageURLBtSheet.text = imageDetails.hdurl
        if (imageDetails.copyright != null)
            binding.authorNameBTSheet.text = "By " + imageDetails.copyright
        else
            binding.authorNameBTSheet.visibility = View.GONE
        binding.dateBTSheet.text = imageDetails.date
    }

}