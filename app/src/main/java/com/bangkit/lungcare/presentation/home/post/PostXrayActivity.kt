package com.bangkit.lungcare.presentation.home.post

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bangkit.lungcare.R
import com.bangkit.lungcare.data.Result
import com.bangkit.lungcare.databinding.ActivityPostXrayBinding
import com.bangkit.lungcare.domain.model.xray.XrayUpload
import com.bangkit.lungcare.presentation.auth.login.LoginActivity
import com.bangkit.lungcare.presentation.camera.CameraActivity
import com.bangkit.lungcare.presentation.home.detail.DetailXrayResultActivity
import com.bangkit.lungcare.utils.reduceFileImage
import com.bangkit.lungcare.utils.rotateBitmap
import com.bangkit.lungcare.utils.uriToFile
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class PostXrayActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostXrayViewModel>()

    private val binding: ActivityPostXrayBinding by lazy {
        ActivityPostXrayBinding.inflate(layoutInflater)
    }

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
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            openCameraBtn.setOnClickListener {
                if (!allPermissionGranted()) {
                    ActivityCompat.requestPermissions(
                        this@PostXrayActivity,
                        REQUIRED_PERMISSIONS,
                        REQUEST_CODE_PERMISSIONS
                    )
                } else {
                    val intent = Intent(this@PostXrayActivity, CameraActivity::class.java)
                    launcherIntentCamera.launch(intent)
                }
            }

            openGalleryBtn.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_GET_CONTENT
                    type = "image/*"
                }
                val chooser = Intent.createChooser(intent, getString(R.string.choose_picture))
                launcherIntentGallery.launch(chooser)
            }

        }

        observerToken()

    }

    private fun observerToken() {
        viewModel.getToken().observe(this) { token ->
            Log.d("PostXrayActivity", "getToken: $token")
            if (token.isEmpty()) {
                moveToLogin()
            } else {
                setupPostXray("Bearer $token")
            }
        }
    }

    private fun setupPostXray(token: String) {
        binding.uploadBtn.setOnClickListener {
            if (getFile != null) {
                val compressFile = reduceFileImage(getFile as File)
                viewModel.uploadXrayToPredict(token, compressFile)
            } else {
                showToast(getString(R.string.err_image_field))
            }

            viewModel.xrayResult.observe(this, observerPostXray)
        }
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
                val predictionIdData = result.data.id
                val predictionResultData = result.data.result
                moveToResult(predictionIdData, predictionResultData)
            }
        }
    }

    private fun moveToResult(id: String?, resultPrediction: String?) {
        val intent = Intent(this, DetailXrayResultActivity::class.java).apply {
            putExtra(DetailXrayResultActivity.EXTRA_RESULT_ID, id)
            putExtra(DetailXrayResultActivity.EXTRA_RESULT, resultPrediction)
        }
        startActivity(intent)

    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_RESULT) {
            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.data?.getSerializableExtra(PICTURE_EXTRA, File::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.data?.getSerializableExtra(PICTURE_EXTRA)
            } as? File

            val isBackCamera =
                it.data?.getBooleanExtra(IS_BACK_CAMERA, true) as Boolean

            myFile?.let { file ->
                getFile = file
                binding.previewIv.let { image ->
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
                binding.previewIv.let {
                    Glide.with(this).load(myFile).into(it)
                }
            }

        }
    }

    private fun moveToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        const val PICTURE_EXTRA = "picture_extra"
        const val IS_BACK_CAMERA = "is_back_camera_extra"
        const val CAMERA_RESULT = 200
    }
}