package lastsubmission.capstone.basantaraapps.interfaces.register

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import lastsubmission.capstone.basantaraapps.R
import lastsubmission.capstone.basantaraapps.databinding.ActivityRegisterBinding
import lastsubmission.capstone.basantaraapps.helper.Result
import lastsubmission.capstone.basantaraapps.helper.ViewModelFactory
import lastsubmission.capstone.basantaraapps.interfaces.login.LoginActivity
import lastsubmission.capstone.basantaraapps.repository.UserRepository


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels {
        // Provide the ViewModelFactory if required
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupListeners()
        observeViewModel()


    }

    private fun setupListeners() {
        binding.btnRegister.setOnClickListener {
            val username = binding.etName.text.toString()
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (validateInput(username, email, password)) {
                registerViewModel.register(username, email, password)
            }
        }
    }

    private fun observeViewModel() {
        registerViewModel.registerUser.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    AlertDialog.Builder(this).apply {
                        setTitle("Selamat")
                        setMessage("Akunmu sudah dibuat")
                        setCancelable(false)
                        setPositiveButton("Login"){_, _ ->
                            val intent = Intent(context, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        create()
                        show()
                    }
                }
                is Result.Error -> {
                    // Handle error
                    Toast.makeText(this, "Registration failed: ${result.error}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateInput(username: String, email: String, password: String): Boolean {
        return when {
            username.isEmpty() -> {
                binding.etName.error = "Please enter a username"
                false
            }
            email.isEmpty() -> {
                binding.etEmail.error = "Please enter an email"
                false
            }
            password.isEmpty() -> {
                binding.etPassword.error = "Please enter a password"
                false
            }
            else -> true
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}