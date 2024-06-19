package lastsubmission.capstone.basantaraapps.interfaces.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import lastsubmission.capstone.basantaraapps.R
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


import android.widget.TextView

class BottomViewsFragment : BottomSheetDialogFragment() {

    companion object {
        private const val ARG_DESCRIPTION = "description"

        fun newInstance(description: String): BottomViewsFragment{
            val fragment = BottomViewsFragment()
            val args = Bundle()
            args.putString(ARG_DESCRIPTION, description)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_layout, container, false)

        val descriptionTextView: TextView = view.findViewById(R.id.bottom_sheet_description)
        arguments?.getString(ARG_DESCRIPTION)?.let { description ->
            descriptionTextView.text = description
        }

        return view
    }
}
