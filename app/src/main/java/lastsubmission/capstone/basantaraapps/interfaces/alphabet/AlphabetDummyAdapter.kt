package lastsubmission.capstone.basantaraapps.interfaces.alphabet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import lastsubmission.capstone.basantaraapps.R
import lastsubmission.capstone.basantaraapps.data.dummy.Alphabet

class AlphabetDummyAdapter(private val alphabetList: List<Alphabet>) : RecyclerView.Adapter<AlphabetDummyAdapter.AlphabetViewHolder>(){


    class AlphabetViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.tv_name_alphabet)
        val imgVectorImageView: ImageView = view.findViewById(R.id.tv_picture)
        val descTextView: TextView = view.findViewById(R.id.tv_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alphabet_cv, parent, false)
        return AlphabetViewHolder(view)
    }

    override fun getItemCount(): Int {
        return alphabetList.size
    }

    override fun onBindViewHolder(holder: AlphabetViewHolder, position: Int) {
        val alphabet = alphabetList[position]
        holder.nameTextView.text = alphabet.name
        holder.descTextView.text = alphabet.descriptionID
        Glide.with(holder.itemView.context).load(alphabet.imgVector).into(holder.imgVectorImageView)
    }
}