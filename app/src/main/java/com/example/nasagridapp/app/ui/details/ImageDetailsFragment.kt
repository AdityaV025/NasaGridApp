package com.example.nasagridapp.app.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.nasagridapp.app.model.ImageModel
import com.example.nasagridapp.app.ui.details.adapter.ImageDetailsAdapter
import com.example.nasagridapp.app.ui.details.bottomsheet.ImageDetailsBottomSheet
import com.example.nasagridapp.app.ui.grid.viewmodel.ImageGridViewModel
import com.example.nasagridapp.app.utils.SnapHelperOneByOne
import com.example.nasagridapp.databinding.FragmentImageDetailsBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ImageDetailsFragment : Fragment(), ImageDetailsAdapter.OnItemClickListener {

    private lateinit var binding: FragmentImageDetailsBinding
    private var selectedImagePosition: Int = 0
    private val adapter: ImageDetailsAdapter by inject()
    private val viewModel by sharedViewModel<ImageGridViewModel>()
    private lateinit var bottomSheet: ImageDetailsBottomSheet
    private lateinit var imageListDetails: List<ImageModel>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageDetailsBinding.inflate(inflater,container,false)
        selectedImagePosition = arguments?.getInt("imagePosition")!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        adapter.setupItemClickListener(this)
    }

    private fun setupObservers() {
        viewModel.imageList.observe(viewLifecycleOwner) {
            setupRecyclerView(it as ArrayList<ImageModel>)
            imageListDetails = it
        }
    }

    private fun setupRecyclerView(it: ArrayList<ImageModel>) {
        adapter.addImageList(it)
        binding.imageDetailRv.setHasFixedSize(true)
        binding.imageDetailRv.adapter = adapter
        binding.imageDetailRv.scrollToPosition(selectedImagePosition)
        val snapHelper = SnapHelperOneByOne(isVertical = false)
        snapHelper.attachToRecyclerView(binding.imageDetailRv)
        binding.imageDetailRv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val offset: Int = binding.imageDetailRv.computeHorizontalScrollOffset()
                var position: Float = offset.toFloat() / (binding.imageDetailRv.getChildAt(0).measuredWidth).toFloat()
                position += 0.5f
                val postInt: Int = position.toInt()
                val positionIndex = postInt + 1
                val listSize = adapter.itemCount
                binding.totalImageCountText.text = "$positionIndex / $listSize"
            }
        })
    }

    override fun onViewMoreClick(itemPosition: Int) {
        bottomSheet = ImageDetailsBottomSheet(imageListDetails[itemPosition])
        val fm = childFragmentManager
        bottomSheet.show(fm,tag)
    }

    override fun onBackClick() {
        activity?.onBackPressed()
    }

}