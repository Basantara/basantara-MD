package lastsubmission.capstone.basantaraapps.interfaces.home.ui.profiles

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import lastsubmission.capstone.basantaraapps.databinding.FragmentProfilesBinding


class ProfilesFragment : Fragment() {

    private var _binding: FragmentProfilesBinding?= null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfilesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Fetch username from SharedPreferences
        val sharedPreferences = activity?.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)
        val username = sharedPreferences?.getString("username", "Username not found")

        // Set the username to the TextView
        binding.tvName.text = username



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}