package lastsubmission.capstone.basantaraapps.interfaces.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import lastsubmission.capstone.basantaraapps.R
import lastsubmission.capstone.basantaraapps.databinding.ActivitySplashBinding
import android.os.Handler
import android.os.Looper
import lastsubmission.capstone.basantaraapps.interfaces.login.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private lateinit var  binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        // Delay selama 3 detik sebelum beralih ke LoginActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3000 milidetik = 3 deti
    }
}