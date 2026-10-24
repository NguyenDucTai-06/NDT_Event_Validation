package com.ute.ndt_event_validation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ute.ndt_event_validation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
            )
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnRegister.setOnClickListener { onRegisterClicked() }
    }

    private fun onRegisterClicked() {
        val intent = DetailActivity.newIntent(
            context = this,
            fullName = binding.edtFullName.text.toString().trim(),
            email = binding.edtEmail.text.toString().trim(),
            phone = binding.edtPhone.text.toString().trim(),
            studentId = binding.edtStudentId.text.toString().trim(),
            className = binding.edtClassName.text.toString().trim().uppercase(),
        )
        startActivity(intent)
    }
}
