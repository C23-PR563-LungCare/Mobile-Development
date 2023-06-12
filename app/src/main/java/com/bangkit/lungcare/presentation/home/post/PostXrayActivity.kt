package com.bangkit.lungcare.presentation.home.post

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bangkit.lungcare.R
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.databinding.ActivityPostXrayBinding
import com.bangkit.lungcare.domain.model.XrayUpload
import com.bangkit.lungcare.presentation.camera.CameraActivity
import com.bangkit.lungcare.utils.reduceFileImage
import com.bangkit.lungcare.utils.rotateBitmap
import com.bangkit.lungcare.utils.uriToFile
import com.bumptech.glide.Glide
import java.io.File

class PostXrayActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostXrayViewModel>()

    private var _binding: ActivityPostXrayBinding? = null
    private val binding get() = _binding

    private var getFile: File? = null

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionGranted()) {
                showToast(getString(R.string.err_permission))
                finish()
            }
        }
    }

    private fun allPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPostXrayBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.apply {
            openCameraBtn.setOnClickListener {
                if (!allPermissionGranted()) {
                    ActivityCompat.requestPermissions(
                        this@PostXrayActivity,
                        REQUIRED_PERMISSIONS,
                        REQUEST_CODE_PERMISSIONS
                    )
                } else {
                    val intent = Intent(this@PostXrayActivity, CameraActivity::class.java)
                    startActivity(intent)
                    launcherIntentCamera.launch(intent)
                }
            }

            openGalleryBtn.setOnClickListener {
                startGallery()
            }

            uploadBtn.setOnClickListener {
                setupPostXrayToPrediction()
            }
        }

    }

    private fun setupPostXrayToPrediction() {
        if (getFile != null) {
            val compressFile = reduceFileImage(getFile as File)
            viewModel.uploadXrayToPredict(compressFile)
        } else {
            showToast(getString(R.string.err_image_field))
        }

        viewModel.xrayResult.observe(this, observerPostXray)
    }

    private val observerPostXray = Observer<Result<XrayUpload>> { result ->
        when (result) {
            is Result.Loading -> {
                binding?.progressbar?.visibility = View.VISIBLE
            }

            is Result.Error -> {
                binding?.progressbar?.visibility = View.GONE
                showToast(result.error)
            }

            is Result.Success -> {
                binding?.progressbar?.visibility = View.GONE
                moveToResult()
            }
        }
    }

    private fun moveToResult() {
        val intent = Intent(this)
    }

    private fun startGallery() {
        val intent = Intent().apply {
            action = Intent.ACTION_GET_CONTENT
            type = "image/*"
        }
        val chooser = Intent.createChooser(intent, getString(R.string.choose_picture))
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra(PostXrayFragment.PICTURE_EXTRA, File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra(PostXrayFragment.PICTURE_EXTRA)
            } as? File

            val isBackCamera =
                it.data?.getBooleanExtra(PostXrayFragment.IS_BACK_CAMERA, true) as Boolean

            myFile?.let { file ->
                getFile = file
                binding?.previewIv?.let { image ->
                    Glide.with(this)
                        .load(rotateBitmap(BitmapFactory.decodeFile(file.path), isBackCamera))
                        .into(image)
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
                val myFile = uriToFile(uri, this@PostXrayActivity)
                getFile = myFile
                binding?.previewIv?.let {
                    Glide.with(this).load(myFile).into(it)
                }
            }

        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        const val PICTURE_EXTRA = "picture_extra"
        const val IS_BACK_CAMERA = "is_back_camera_extra"
        const val CAMERA_RESULT = 200
    }
}