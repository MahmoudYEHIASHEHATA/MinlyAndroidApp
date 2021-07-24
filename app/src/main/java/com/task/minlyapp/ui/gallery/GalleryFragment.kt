package com.task.minlyapp.ui.gallery

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.task.minlyapp.shared.helper.CAMERA_PERMISSION_REQUEST_CODE
import com.task.minlyapp.shared.apiWrapper.Status
import com.google.android.material.snackbar.Snackbar
import com.task.minlyapp.data.repos.GalleryRepository
import com.task.minlyapp.databinding.FragmentGalleryBinding
import com.task.minlyapp.shared.apiWrapper.Error
import com.task.minlyapp.shared.helper.checkCameraPermission
import com.task.minlyapp.ui.gallery.adapter.GalleryItemsAdapter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

@Suppress("DEPRECATION")
class GalleryFragment : Fragment() {

    private lateinit var binding: FragmentGalleryBinding

    private lateinit var viewModel: GalleryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGalleryBinding.inflate(inflater)
        initViewModel()
        observeFetchGalleryApi()
        observeUpImgApi()
        setupHistoryItemsRv()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.addFab.setOnClickListener {
            if (requireActivity().checkCameraPermission()) {
                onUpImg()
            }
        }
        return binding.root
    }

    private fun initViewModel() {
        val repository = GalleryRepository(requireContext())
        val viewModelFactory = GalleryViewModelFactory(requireActivity().application, repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[GalleryViewModel::class.java]
    }

    private fun observeFetchGalleryApi() {
        viewModel.currentGalleryApiWrapper.observe(viewLifecycleOwner, Observer { Resource ->
            Resource?.let {
                when (Resource.status) {
                    Status.ERROR -> {
                        handleError(Resource.error)
                    }
                    Status.LOADING -> {
                        showLoading()
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                    }
                }
            }
        })
    }

    private fun observeUpImgApi() {
        viewModel.currentUpImgApiWrapper.observe(viewLifecycleOwner, Observer { Resource ->
            Resource?.let {
                when (Resource.status) {
                    Status.ERROR -> {
                        handleError(Resource.error)
                    }
                    Status.LOADING -> {
                        showLoading()
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        viewModel.fetchGalleryData()
                        Snackbar.make(requireView(), Resource.data.toString(), Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun handleError(error: Error?) {
        error?.let {
            Snackbar.make(requireView(), it.message, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun hideLoading() {
        binding.isLoading = false
    }

    private fun showLoading() {
        binding.isLoading = true
    }

    fun onUpImg() {
        ImagePicker.with(requireActivity())
            .saveDir(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!)
            .crop()
            .compress(1024) //Final image size will be less than 1 MB(Optional)
            .start { resultCode, data ->
                if (resultCode == Activity.RESULT_OK) {
                    //Image Uri will not be null for RESULT_OK
                    val fileUri = data!!.data!!
                    val file = File(fileUri.path!!)
                    val requestFile = file
                        .asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val filePart = MultipartBody.Part.createFormData(
                        "image", "img.jpg", requestFile
                    )
                    viewModel.upImageToServer(filePart)
                } else if (resultCode == ImagePicker.RESULT_ERROR) {
                    Snackbar.make(requireView(), ImagePicker.getError(data), Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            for (permission in grantResults) {
                if (permission == PackageManager.PERMISSION_GRANTED) {
                    break
                }
            }
        }
    }

    private fun setupHistoryItemsRv() {
        binding.rvGalleryListItem.apply {
            adapter = GalleryItemsAdapter()
        }
    }
}