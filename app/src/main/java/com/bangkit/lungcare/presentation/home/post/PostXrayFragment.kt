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
import com.bangkit.lungcare.R
import com.bangkit.lungcare.databinding.FragmentPostXrayBinding
import com.bangkit.lungcare.utils.rotateBitmap
import com.bangkit.lungcare.utils.uriToFile
import com.bumptech.glide.Glide
import java.io.File


class PostXrayFragment : Fragment() {

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

            getFile = myFile

            getFile?.let { file ->
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
            val myFile = uriToFile(selectedImage, requireActivity())
            getFile = result?.data?.data?.let { uriToFile(it, requireActivity()) }

            binding.previewIv.let {
                Glide.with(requireActivity()).load(myFile).into(it)
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