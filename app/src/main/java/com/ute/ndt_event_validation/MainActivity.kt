package com.ute.ndt_event_validation

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.ute.ndt_event_validation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private class FormField(
        val layout: TextInputLayout,
        val input: TextInputEditText,
        val validator: (String) -> Int?,
    ) {
        var touched = false
        val text: String get() = input.text?.toString().orEmpty()
    }

    private lateinit var fields: List<FormField>

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

        fields = listOf(
            FormField(binding.tilFullName, binding.edtFullName, Validator::validateFullName),
            FormField(binding.tilEmail, binding.edtEmail, Validator::validateEmail),
            FormField(binding.tilPhone, binding.edtPhone, Validator::validatePhone),
            FormField(binding.tilStudentId, binding.edtStudentId, Validator::validateStudentId),
            FormField(binding.tilClassName, binding.edtClassName, Validator::validateClassName),
        )

        setupFieldEvents()

        binding.btnRegister.setOnClickListener { onRegisterClicked() }
        binding.btnReset.setOnClickListener { resetForm() }

        binding.edtClassName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onRegisterClicked()
                true
            } else false
        }
    }

    private fun setupFieldEvents() {
        fields.forEach { field ->
            field.input.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && field.text.isNotEmpty()) {
                    field.touched = true
                    validateField(field)
                }
            }
            field.input.doAfterTextChanged {
                if (field.touched) validateField(field)
            }
        }
    }

    private fun validateField(field: FormField): Boolean {
        val errorRes = field.validator(field.text)
        if (errorRes == null) {
            field.layout.error = null
            field.layout.isErrorEnabled = false
        } else {
            field.layout.error = getString(errorRes)
        }
        return errorRes == null
    }

    private fun onRegisterClicked() {
        var invalidCount = 0
        var firstInvalid: FormField? = null
        fields.forEach { field ->
            field.touched = true
            if (!validateField(field)) {
                invalidCount++
                if (firstInvalid == null) firstInvalid = field
            }
        }

        if (invalidCount > 0) {
            firstInvalid?.input?.requestFocus()
            Toast.makeText(
                this,
                getString(R.string.msg_fix_errors, invalidCount),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        Toast.makeText(this, R.string.msg_register_success, Toast.LENGTH_SHORT).show()
        val intent = DetailActivity.newIntent(
            context = this,
            fullName = Validator.normalize(binding.edtFullName.text.toString()),
            email = binding.edtEmail.text.toString().trim(),
            phone = binding.edtPhone.text.toString().trim(),
            studentId = binding.edtStudentId.text.toString().trim(),
            className = binding.edtClassName.text.toString().trim().uppercase(),
        )
        startActivity(intent)
    }

    private fun resetForm() {
        fields.forEach { field ->
            field.touched = false
            field.input.text = null
            field.layout.error = null
            field.layout.isErrorEnabled = false
        }
        binding.edtFullName.requestFocus()
    }
}
