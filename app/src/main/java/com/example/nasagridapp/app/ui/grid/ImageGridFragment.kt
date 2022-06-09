package com.example.nasagridapp.app.ui.grid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.nasagridapp.MainActivity
import com.example.nasagridapp.R
import com.example.nasagridapp.app.model.ImageModel
import com.example.nasagridapp.app.ui.grid.adapter.ImageGridAdapter
import com.example.nasagridapp.app.ui.grid.viewmodel.ImageGridViewModel
import com.example.nasagridapp.app.utils.Utils
import com.example.nasagridapp.databinding.FragmentImageGridBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ImageGridFragment : Fragment(), ImageGridAdapter.OnItemClickListener {

    private lateinit var binding: FragmentImageGridBinding
    private lateinit var adapter: ImageGridAdapter
    private val viewModel by sharedViewModel<ImageGridViewModel>()
    private lateinit var imageData: ArrayList<ImageModel>
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private lateinit var parentActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageGridBinding.inflate(inflater, container, false)
        parentActivity = activity as MainActivity
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigationComponent()
        val imageJSONPath = context?.let { Utils().getJsonFromAssets(it, "data.json") }
        if (imageJSONPath != null) {
            viewModel.getImageList(imageJSONPath)
        }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.imageList.observe(viewLifecycleOwner) {
            imageData = it as ArrayList<ImageModel>
            adapter = ImageGridAdapter(this@ImageGridFragment, it)
            binding.imageGridRV.setHasFixedSize(true)
            binding.imageGridRV.adapter = adapter
        }
    }

    private fun setupNavigationComponent() {
        navHostFragment =
            parentActivity.supportFragmentManager.findFragmentById(R.id.navigation_main) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onImageClick(position: Int) {
        val bundle = Bundle()
        bundle.putInt("imagePosition", position)
        navController.navigate(R.id.action_imageGridFragmentt_to_imageDetailsFragment, bundle)
    }

}