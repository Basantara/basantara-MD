package lastsubmission.capstone.basantaraapps.interfaces.alphabet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import lastsubmission.capstone.basantaraapps.R
import lastsubmission.capstone.basantaraapps.data.responses.AlphabetResponseItem
import lastsubmission.capstone.basantaraapps.databinding.ActivityListAlphabetBinding
import lastsubmission.capstone.basantaraapps.helper.Result
import lastsubmission.capstone.basantaraapps.helper.ViewModelFactory
import lastsubmission.capstone.basantaraapps.interfaces.home.HomeActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import lastsubmission.capstone.basantaraapps.data.dummy.Alphabet
import lastsubmission.capstone.basantaraapps.data.preferences.UserModelPreferences
import lastsubmission.capstone.basantaraapps.data.preferences.dataStore

class ListAlphabet : AppCompatActivity() {
    private lateinit var binding: ActivityListAlphabetBinding
    private lateinit var alphabetAdapter: ListAlphabetAdapter
    private val alphabetList = mutableListOf<AlphabetResponseItem>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var alphabetDummyDataAdapter: AlphabetDummyAdapter
    private lateinit var listDummyAlphabet: List<Alphabet>


    private val alphabetViewModel by viewModels<ListAlphabetViewModel> {
        ViewModelFactory.getInstance(this)
    }

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
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                alphabetAdapter.filter(newText ?: "")
                return false
            }
        })

        //setupRecyclerView()
        recyclerView = findViewById(R.id.rv_alphabet)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val jsonString = """
    {
        "status": "Success",
        "message": "fetch data success",
        "data": [
            {
                "name": "a",
                "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/a.png",
                "descriptionID": "Regangkan jari telunjuk dan jempol kedua tangan dan tempelkan satu sama lain sehingga membentuk segitiga."
            },
            {
                "name": "b",
                "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/b.png",
                "descriptionID": "Regangkan jari telunjuk, jari tengah dan jari manis tangan kanan, tempelkan tangan kanan ke jari telunjuk tangan kiri dengan telapak tangan kanan menghadap ke dalam."
            },
            {
                "name": "c",
                "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/c.png",
                "descriptionID": "Bengkokkan jari tangan kanan seperti posisi mengenggam, jauhkan antara jari telunjuk dengan jempol sehingga posisi jari melingkar."
            },
            {
                "name": "d",
                "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/d.png",
                "descriptionID": "Regangkan jari telunjuk dan jari jempol tangan kanan lalu bengkokkan sedikit, tempelkan jari telunjuk dan jempol tangan kanan ke jari telunjuk tangan kiri."
            },
            {
                "name": "e",
                "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/e.png",
                "descriptionID": "Regangkan jari telunjuk, tengah, dan manis tangan kiri dan posisikan tangan secara horizontal dengan telapak tangan menghadap ke dalam."
            },
            {
                "name": "f",
                "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/f.png",
                "descriptionID": "Regangkan jari telunjuk dan tengah tangan kiri, posisikan tangan kiri secara horizontal dengan telapak tangan menghadap ke dalam dan tempelkan jari telunjuk tangan kanan ke pangkal jari tangan kiri."
            },
            {
                "name": "g",
                "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/g.png",
                "descriptionID": "Kepalkan kedua tangan dan tumpuk kedua tangan dengan posisi tangan kiri di atas dan telapak tangan menghadap ke dalam."
            },
            {
                "name": "h",
                "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/h.png",
                "descriptionID": "Regangkan jari telunjuk tangan kiri, regangkan jari telunjuk dan jari tengah tangan kanan dan turunkan jari tengah hingga datar, tempelkan jari tengah ke bagian tengah jari telunjuk kiri."
            },
            {
                "name": "i",
                "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/i.png",
                "descriptionID": "Regangkan ibu jari tangan kiri kemudian regangkan jari telunjuk tangan kanan dan tekan ke bagian pangkal ibu jari tangan kiri."
            },
            {
                "name": "j",
                "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/j.png",
                "descriptionID": "Regangkan jari telunjuk tangan kiri, regangkan jari telunjuk tangan kanan dan letakkan di bagian tengah jari telunjuk kiri dengan telapak tangan kanan menghadap ke dalam."
            },
            {
                "name": "k",
                "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/k.png",
                "descriptionID": "Regangkan ibu jari, telunjuk, dan jari tengah tangan kanan dan arahkan ke atas, regangkan ibu jari tangan kiri dan letakkan jempol kanan di atas jempol kiri."
            },
            {
                "name": "l",
              "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/l.png",
              "descriptionID": "Regangkan jari telunjuk dan jempol tangan kanan dan arahkan telapak tangan ke arah luar."
            },
            {
              "name": "m",
              "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/m.png",
              "descriptionID": "Regangkan jari telunjuk, tengah dan manis tangan kanan dan tempelkan ke telapak tangan kiri yang menghadap ke luar."
            },
            {
              "name": "n",
              "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/n.png",
              "descriptionID": "Regangkan jari telunjuk dan tengah tangan kanan dan tempelkan ke telapak tangan kiri yang menghadap ke luar."
            },
            {
              "name": "o",
              "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/o.png",
              "descriptionID": "Posisikan tangan kanan seperti menggenggam dengan ujung jari telunjuk menempel pada ujung jari jempol."
            },
            {
              "name": "p",
              "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/p.png",
              "descriptionID": "Regangkan jari telunjuk tangan kiri dengan telapak tangan menghadap ke luar dan tempelkan ujung jari telunjuk dan jempol tangan kanan ke jari telunjuk tangan kiri."
            },
            {
              "name": "q",
              "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/q.png",
              "descriptionID": "Posisikan tangan kiri seperti menggenggam dengan ujung jari telunjuk menempel pada ujung jari jempol dan tempelkan jari telunjuk tangan kanan dengan posisi diagonal pada tangan kiri."
            },
            {
              "name": "r",
              "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/r.png",
              "descriptionID": "Jentikkan jari tangan kanan."
            },
            {
              "name": "s",
              "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/s.png",
              "descriptionID": "Bengkokkan jari telunjuk dan jari jempol kedua tangan dan tempelkan jari telunjuk tangan kanan dengan jari jempol tangan kiri."
            },
            {
              "name": "t",
              "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/t.png",
              "descriptionID": "Regangkan jari telunjuk tangan kiri dan posisikan tangan kiri secara horizontal, tempelkan jari telunjuk tangan kanan ke bagian tengah jari telunjuk kiri."
            },
            {
              "name": "u",
              "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/u.png",
              "descriptionID": "Bengkokkan jari telunjuk dan jari jempol tangan kanan dan arahkan ke atas."
            },
            {
              "name": "v",
              "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/v.png",
              "descriptionID": "Regangkan jari telunjuk dan jari jempol tangan kanan dan arahkan miring ke atas dengan telapak tangan menghadap ke luar, atau bisa dengan regangkan jari telunjuk dan jari tengah dengan telapak tangan menghadap ke luar."
            },
            {
              "name": "w",
              "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/w.png",
              "descriptionID": "Regangkan jari telunjuk dan jari jempol kedua tangan dan arahkan miring ke atas dengan telapak tangan menghadap ke luar, tempelkan jempol tangan kanan dan kiri."
            },
            {
              "name": "x",
              "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/x.png",
              "descriptionID": "Regangkan jari telunjuk kedua tangan dan silangkan satu sama lain."
            },
            {
              "name": "y",
              "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/y.png",
              "descriptionID": "Regangkan jari telunjuk dan jari jempol tangan kiri dan arahkan miring ke atas dengan telapak tangan menghadap ke luar, dan tempelkan jari telunjuk tangan kanan ke pangkal telapak tangan kiri."
            },
            {
              "name": "z",
              "imgVector": "https://storage.googleapis.com/basantara-assets/images-vector/z.png",
              "descriptionID": "Buka telapak tangan kanan dan tekuk tangan dan siku sehingga membentuk huruf \"Z\"."
            }
                    
        ]
    }
"""


        listDummyAlphabet = parseAlphabetData(jsonString)
        alphabetDummyDataAdapter = AlphabetDummyAdapter(listDummyAlphabet)
        recyclerView.adapter =  alphabetDummyDataAdapter
        fetchTokenAndObserveViewModel()
    }

    private fun parseAlphabetData(jsonString: String): List<Alphabet> {
        val gson = Gson()
        val jsonObject = JsonParser.parseString(jsonString).asJsonObject
        val jsonData = jsonObject.getAsJsonArray("data")

        val listType = object : TypeToken<List<Alphabet>>() {}.type
        return gson.fromJson(jsonData, listType)
    }

    private fun getToken(): String {
        // Return the provided token
        return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE3MTg4NDU1MDR9.9OEGX2DYMHeSIYzjNpmLYGbl3p83ahYwK7tMTjhDiTs"
    }

    private fun fetchTokenAndObserveViewModel() {
        val userModelPreferences = UserModelPreferences.getInstance(dataStore)
        lifecycleScope.launch {
            userModelPreferences.getSession().collect { userModel ->
                if (userModel.isLogin) {
                    observeViewModel(userModel.token)
                } else {
                    Toast.makeText(this@ListAlphabet, "User not logged in", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun observeViewModel(token: String) {
        alphabetViewModel.fetchAlphabets(token)
        alphabetViewModel.alphabets.observe(this@ListAlphabet, { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    showLoading(false)
                    alphabetList.clear()
                    alphabetList.addAll(result.data.alphabetResponse)
                    alphabetAdapter.filter("") // Update the adapter with new data
                }
                is Result.Error -> {
                    showLoading(false)
                    Toast.makeText(this@ListAlphabet, "Failed to load data", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupRecyclerView() {
        alphabetAdapter = ListAlphabetAdapter(alphabetList)
        binding.rvAlphabet.apply {
            layoutManager = LinearLayoutManager(this@ListAlphabet)
            adapter = alphabetAdapter
        }
    }

    private fun setupRecyclerViewDummyData() {

    }
}
