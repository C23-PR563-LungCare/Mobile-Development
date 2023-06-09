package com.bangkit.lungcare.presentation.home.post

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bangkit.lungcare.R
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.databinding.FragmentPostXrayBinding
import com.bangkit.lungcare.domain.model.XrayUpload
import com.bangkit.lungcare.utils.reduceFileImage
import com.bangkit.lungcare.utils.rotateBitmap
import com.bangkit.lungcare.utils.uriToFile
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class PostXrayFragment : Fragment() {

    private val viewModel by viewModels<PostXrayViewModel>()

    private var _binding: FragmentPostXrayBinding? = null
    private val binding get() = _binding!!

    private var getFile: File? = null

    private val launcherPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted && !allPermissionGranted()) {
            showToast(getString(R.string.err_permission))
        }
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSION.all {
        ContextCompat.checkSelfPermission(
            requireActivity(),
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPostXrayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launcherPermission.launch(REQUIRED_PERMISSION.first())
        setFragmentListener()

        binding.apply {

            openCameraBtn.setOnClickListener {
                findNavController().navigate(PostXrayFragmentDirections.actionPostXrayFragmentToCameraFragment())
            }

            openGalleryBtn.setOnClickListener {
                startGallery()
            }

            uploadBtn.setOnClickListener {
                setupPostAction()
            }

        }

    }

    private fun setupPostAction() {
        if (getFile != null) {
            val compressFile = reduceFileImage(getFile as File)
            viewModel.uploadXrayToPredict(compressFile)
        } else {
            showToast(getString(R.string.err_image_field))
        }

        viewModel.xrayResult.observe(viewLifecycleOwner, observerPostXray)

    }

    private val observerPostXray = Observer<Result<XrayUpload>> { result ->
        when (result) {
            is Result.Loading -> {
                binding.progressbar.visibility = View.VISIBLE
            }

            is Result.Error -> {
                binding.progressbar.visibility = View.GONE
                showToast(result.error)
            }

            is Result.Success -> {
                binding.progressbar.visibility = View.GONE

                moveToResult()
            }
        }
    }

    private fun moveToResult() {
        findNavController().navigate(PostXrayFragmentDirections.actionPostXrayFragmentToDetailXrayFragment())
    }

    private fun startGallery() {
        val intent = Intent().apply {
            action = ACTION_GET_CONTENT
            type = "image/*"
        }
        val chooser = Intent.createChooser(intent, getString(R.string.choose_picture))
        launcherIntentGallery.launch(chooser)
    }

    private fun setFragmentListener() {
        setFragmentResultListener(CAMERA_RESULT) { _, bundle ->
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getSerializable(PICTURE_EXTRA, File::class.java)
            } else {
                @Suppress("DEPRECATION")
                bundle.getSerializable(PICTURE_EXTRA)
            } as? File

            val isBackCamera = bundle.getBoolean(IS_BACK_CAMERA, true)

            myFile?.let { file ->
                getFile = file
                binding.previewIv.let {
                    Glide.with(this)
                        .load(rotateBitmap(BitmapFactory.decodeFile(file.path), isBackCamera))
                        .into(it)
                }
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImage = result.data?.data as Uri

            selectedImage.let { uri ->
                val myFile = uriToFile(uri, requireActivity())
                getFile = myFile
                binding.previewIv.let {
                    Glide.with(requireActivity()).load(myFile).into(it)
                }
            }

        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private val REQUIRED_PERMISSION = arrayOf(Manifest.permission.CAMERA)
        const val PICTURE_EXTRA = "picture_extra"
        const val IS_BACK_CAMERA = "is_back_camera_extra"
        const val CAMERA_RESULT = "200"
    }
}