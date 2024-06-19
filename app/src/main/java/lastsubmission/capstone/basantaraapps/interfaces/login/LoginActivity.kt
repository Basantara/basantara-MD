package lastsubmission.capstone.basantaraapps.interfaces.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.commit
import lastsubmission.capstone.basantaraapps.helper.Result
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import lastsubmission.capstone.basantaraapps.R
import lastsubmission.capstone.basantaraapps.data.dummy.LoginData
import lastsubmission.capstone.basantaraapps.databinding.ActivityLoginBinding
import lastsubmission.capstone.basantaraapps.helper.ViewModelFactory
import lastsubmission.capstone.basantaraapps.interfaces.home.ui.home.HomeFragment
import lastsubmission.capstone.basantaraapps.interfaces.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel by viewModels<LoginViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupAction()
        playAnimation()


        loginViewModel.loginAction.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    AlertDialog.Builder(this).apply {
                        setTitle("Berhasil!")
                        setMessage(R.string.message_login)
                        setPositiveButton("Ke Halaman Utama") { _, _ ->
                            replaceHomeFragment()
                        }
                        create()
                        show()
                    }
                }
                is Result.Error -> {
                    toastFailed()
                    showLoading(false)
                }
            }
        }

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

    private fun setupAction() {
        binding.btnLogin.setOnClickListener {
            binding.apply {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    loginViewModel.login_optional(email, password)
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Email dan password harus diisi!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.tvRegister.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun playAnimation() {
        val fadeInAnimation = ObjectAnimator.ofFloat(binding.tvLoginAccount, View.ALPHA, 1f).setDuration(200)
        val welcomeAnimation = ObjectAnimator.ofFloat(binding.welcome, View.ALPHA, 1f).setDuration(200)
        val layoutAnimation = ObjectAnimator.ofFloat(binding.loginLayout, View.ALPHA, 1f).setDuration(200)
        val signUpAnimation = ObjectAnimator.ofFloat(binding.tvSignup, View.ALPHA, 1f).setDuration(200)
        val registerAnimation = ObjectAnimator.ofFloat(binding.tvRegister, View.ALPHA, 1f).setDuration(200)

        AnimatorSet().apply {
            playSequentially(
                fadeInAnimation,
                welcomeAnimation,
                layoutAnimation,
                signUpAnimation,
                registerAnimation
            )
            startDelay = 100
        }.start()
    }


    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun replaceHomeFragment() {
        supportFragmentManager.commit {
            replace(android.R.id.content, HomeFragment())
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private fun toastFailed() {
        Toast.makeText(this, R.string.failed_login, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}