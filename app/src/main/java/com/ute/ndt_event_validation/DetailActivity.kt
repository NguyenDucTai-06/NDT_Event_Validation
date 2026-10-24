package com.ute.ndt_event_validation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ute.ndt_event_validation.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        showData()

        binding.btnBackRegister.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun showData() {
        val noData = getString(R.string.no_data)
        fun extra(key: String) = intent.getStringExtra(key)?.takeIf { it.isNotBlank() } ?: noData

        binding.tvFullName.text = extra(EXTRA_FULL_NAME)
        binding.tvEmail.text = extra(EXTRA_EMAIL)
        binding.tvPhone.text = extra(EXTRA_PHONE)
        binding.tvStudentId.text = extra(EXTRA_STUDENT_ID)
        binding.tvClassName.text = extra(EXTRA_CLASS_NAME)
    }

    companion object {
        const val EXTRA_FULL_NAME = "extra_full_name"
        const val EXTRA_EMAIL = "extra_email"
        const val EXTRA_PHONE = "extra_phone"
        const val EXTRA_STUDENT_ID = "extra_student_id"
        const val EXTRA_CLASS_NAME = "extra_class_name"

        fun newIntent(
            context: Context,
            fullName: String,
            email: String,
            phone: String,
            studentId: String,
            className: String,
        ): Intent = Intent(context, DetailActivity::class.java).apply {
            putExtra(EXTRA_FULL_NAME, fullName)
            putExtra(EXTRA_EMAIL, email)
            putExtra(EXTRA_PHONE, phone)
            putExtra(EXTRA_STUDENT_ID, studentId)
            putExtra(EXTRA_CLASS_NAME, className)
        }
    }
}
