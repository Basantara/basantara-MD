package lastsubmission.capstone.basantaraapps.interfaces.home.ui.scanning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

import lastsubmission.capstone.basantaraapps.databinding.FragmentScanningBinding
import lastsubmission.capstone.basantaraapps.helper.ImageClassifierHelper

class ScanningFragment : Fragment() {

    private var _binding: FragmentScanningBinding? = null
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentScanningBinding.inflate(inflater, container, false)
        val root: View = binding.root



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}