package com.ute.ndt_event_validation

import androidx.annotation.StringRes

object Validator {

    private val NAME_REGEX = Regex("^\\p{L}+(\\s\\p{L}+)*$")
    private val EMAIL_REGEX =
        Regex("^[A-Za-z0-9]+([._%+-][A-Za-z0-9]+)*@[A-Za-z0-9]+([.-][A-Za-z0-9]+)*\\.[A-Za-z]{2,}$")
    private val DIGITS_REGEX = Regex("^\\d+$")
    private val PHONE_PREFIX_REGEX = Regex("^0[35789]\\d{8}$")
    private val CLASS_REGEX = Regex("^[A-Za-z0-9]+$")

    fun normalize(input: String): String = input.trim().replace(Regex("\\s+"), " ")

    @StringRes
    fun validateFullName(input: String): Int? {
        val value = normalize(input)
        return when {
            value.isEmpty() -> R.string.err_name_empty
            !NAME_REGEX.matches(value) -> R.string.err_name_invalid
            value.split(" ").size < 2 -> R.string.err_name_too_short
            value.length > 50 -> R.string.err_name_too_long
            else -> null
        }
    }

    @StringRes
    fun validateEmail(input: String): Int? {
        val value = input.trim()
        return when {
            value.isEmpty() -> R.string.err_email_empty
            !EMAIL_REGEX.matches(value) -> R.string.err_email_invalid
            else -> null
        }
    }

    @StringRes
    fun validatePhone(input: String): Int? {
        val value = input.trim()
        return when {
            value.isEmpty() -> R.string.err_phone_empty
            !DIGITS_REGEX.matches(value) -> R.string.err_phone_not_digit
            value.length != 10 -> R.string.err_phone_length
            !PHONE_PREFIX_REGEX.matches(value) -> R.string.err_phone_invalid
            else -> null
        }
    }

    @StringRes
    fun validateStudentId(input: String): Int? {
        val value = input.trim()
        return when {
            value.isEmpty() -> R.string.err_student_id_empty
            !DIGITS_REGEX.matches(value) -> R.string.err_student_id_not_digit
            value.length != 13 -> R.string.err_student_id_length
            else -> null
        }
    }

    @StringRes
    fun validateClassName(input: String): Int? {
        val value = input.trim()
        return when {
            value.isEmpty() -> R.string.err_class_empty
            !CLASS_REGEX.matches(value) -> R.string.err_class_invalid
            value.length !in 2..15 -> R.string.err_class_length
            value.none { it.isDigit() } -> R.string.err_class_need_digit
            else -> null
        }
    }
}
