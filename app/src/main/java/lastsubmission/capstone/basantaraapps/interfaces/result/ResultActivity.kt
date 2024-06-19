package lastsubmission.capstone.basantaraapps.interfaces.result

import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import lastsubmission.capstone.basantaraapps.R
import lastsubmission.capstone.basantaraapps.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.openBottomSheet.setOnClickListener {
            val bottomSheetFragment = BottomViewsFragment.newInstance("Deskripsi hasil penjelasan akan ditampilkan disini")
            bottomSheetFragment.show(supportFragmentManager,  BottomViewsFragment::class.java.simpleName)
        }

        displayResult()
    }

    private fun displayResult() {
        val imageUriString = intent.getStringExtra(EXTRA_IMAGE_URI)
        val result = intent.getStringExtra(EXTRA_RESULT)

        imageUriString?.let {uriString ->
            val imageUri = Uri.parse(uriString)
            Log.d("Image Uri", "showImage: $imageUri")

            binding.placeholderImage.setImageURI(imageUri)
        }

        result?.let {
            binding.scanResult.text = it

        }
    }





    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
        const val EXTRA_RESULT = "extra_result"
    }
}