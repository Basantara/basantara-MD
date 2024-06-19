package lastsubmission.capstone.basantaraapps.interfaces.alphabet

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lastsubmission.capstone.basantaraapps.data.responses.AlphabetResponseItem
import lastsubmission.capstone.basantaraapps.databinding.AlphabetCvBinding

class ListAlphabetAdapter(
    private val context: Context,
    private val alphabetList: List<AlphabetResponseItem>
) : RecyclerView.Adapter<ListAlphabetAdapter.AlphabetViewHolder>() {

    inner class AlphabetViewHolder(private val binding: AlphabetCvBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AlphabetResponseItem) {
            // Bind data to views
            binding.tvNameAlphabet.text = item.name
            binding.tvDescription.text = item.descriptionID

            // Load image using Glide or any other image loading library
            Glide.with(context)
                .load(item.imgVector)
                .centerCrop()
                .into(binding.tvPicture)

            // Implement click listener if needed
            binding.root.setOnClickListener {
                // Handle item click if required
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetViewHolder {
        val binding = AlphabetCvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlphabetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlphabetViewHolder, position: Int) {
        val item = alphabetList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return alphabetList.size
    }
}
