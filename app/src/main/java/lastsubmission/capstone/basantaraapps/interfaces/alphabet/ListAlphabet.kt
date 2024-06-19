package lastsubmission.capstone.basantaraapps.interfaces.alphabet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import lastsubmission.capstone.basantaraapps.R
import lastsubmission.capstone.basantaraapps.data.responses.AlphabetResponseItem
import lastsubmission.capstone.basantaraapps.databinding.ActivityListAlphabetBinding
import lastsubmission.capstone.basantaraapps.interfaces.home.HomeActivity
import androidx.lifecycle.Observer
import androidx.appcompat.widget.SearchView

class ListAlphabet : AppCompatActivity() {
    private lateinit var binding:ActivityListAlphabetBinding
    private lateinit var alphabetAdapter: ListAlphabetAdapter
    private val alphabetList = mutableListOf<AlphabetResponseItem>()
    private val alphabetViewModel: ListAlphabetViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAlphabetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("Not yet implemented")
            }
        })

        setupRecyclerView()
        alphabetViewModel.isLoading.observe(this ) {
            showLoading(it)
        }



        alphabetViewModel.alphabetList.observe(this, Observer { list ->
            alphabetList.clear()
            alphabetList.addAll(list)
            alphabetAdapter.notifyDataSetChanged()
        })



        alphabetViewModel.isError.observe(this, Observer { isError ->
            if (isError) {
                Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        })



    }

    private fun showLoading(isLoading: Boolean) { binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE }


    private fun setupRecyclerView() {
        alphabetAdapter = ListAlphabetAdapter(this, alphabetList)
        binding.rvAlphabet.apply {
            layoutManager = LinearLayoutManager(this@ListAlphabet)
            adapter = alphabetAdapter
        }
    }

}