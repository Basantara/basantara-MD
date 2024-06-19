package lastsubmission.capstone.basantaraapps.interfaces.home.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import lastsubmission.capstone.basantaraapps.R
import lastsubmission.capstone.basantaraapps.databinding.FragmentHomeBinding

import lastsubmission.capstone.basantaraapps.interfaces.alphabet.ListAlphabet
import lastsubmission.capstone.basantaraapps.interfaces.home.MainActivity
import lastsubmission.capstone.basantaraapps.interfaces.home.ui.scanning.ScanningFragment
import lastsubmission.capstone.basantaraapps.interfaces.upload.UploadActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        // Set up intent for "perkenalan" CardView
        binding.perkenalan.setOnClickListener {
            val intent = Intent(activity, ListAlphabet::class.java)
            startActivity(intent)
        }

        // Set up intent for "scan" CardView
        binding.scan.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.live.setOnClickListener {
            val scanningFragment = ScanningFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, scanningFragment)
                .addToBackStack(null)
                .commit()
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}