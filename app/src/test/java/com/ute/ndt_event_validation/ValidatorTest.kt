package com.ute.ndt_event_validation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/** Unit test cho các rule validation của form Đăng kí. */
class ValidatorTest {

    @Test
    fun fullName() {
        assertEquals(R.string.err_name_empty, Validator.validateFullName("   "))
        assertEquals(R.string.err_name_invalid, Validator.validateFullName("Nguyen Van 123"))
        assertEquals(R.string.err_name_invalid, Validator.validateFullName("An@"))
        assertEquals(R.string.err_name_too_short, Validator.validateFullName("An"))
        assertEquals(R.string.err_name_too_long, Validator.validateFullName("Nguyễn " + "A".repeat(50)))
        assertNull(Validator.validateFullName("Nguyễn Văn An"))
        assertNull(Validator.validateFullName("  Trần   Thị  Bích  "))
    }

    @Test
    fun email() {
        assertEquals(R.string.err_email_empty, Validator.validateEmail(""))
        assertEquals(R.string.err_email_invalid, Validator.validateEmail("abc"))
        assertEquals(R.string.err_email_invalid, Validator.validateEmail("abc@gmail"))
        assertEquals(R.string.err_email_invalid, Validator.validateEmail("abc@@gmail.com"))
        assertEquals(R.string.err_email_invalid, Validator.validateEmail(".abc@gmail.com"))
        assertNull(Validator.validateEmail("an.nguyen@gmail.com"))
        assertNull(Validator.validateEmail("sv_01@ute.udn.vn"))
    }

    @Test
    fun phone() {
        assertEquals(R.string.err_phone_empty, Validator.validatePhone(""))
        assertEquals(R.string.err_phone_not_digit, Validator.validatePhone("09a2345678"))
        assertEquals(R.string.err_phone_length, Validator.validatePhone("091234567"))
        assertEquals(R.string.err_phone_length, Validator.validatePhone("09123456789"))
        assertEquals(R.string.err_phone_invalid, Validator.validatePhone("0123456789"))
        assertEquals(R.string.err_phone_invalid, Validator.validatePhone("1912345678"))
        assertNull(Validator.validatePhone("0912345678"))
        assertNull(Validator.validatePhone("0356789012"))
    }

    @Test
    fun studentId() {
        assertEquals(R.string.err_student_id_empty, Validator.validateStudentId(""))
        assertEquals(R.string.err_student_id_not_digit, Validator.validateStudentId("SV123456"))
        assertEquals(R.string.err_student_id_length, Validator.validateStudentId("241505312224"))
        assertEquals(R.string.err_student_id_length, Validator.validateStudentId("24150531222401"))
        assertNull(Validator.validateStudentId("2415053122240"))
    }

    @Test
    fun className() {
        assertEquals(R.string.err_class_empty, Validator.validateClassName(""))
        assertEquals(R.string.err_class_invalid, Validator.validateClassName("22 T1"))
        assertEquals(R.string.err_class_invalid, Validator.validateClassName("22T1!"))
        assertEquals(R.string.err_class_length, Validator.validateClassName("A"))
        assertEquals(R.string.err_class_need_digit, Validator.validateClassName("CNTT"))
        assertNull(Validator.validateClassName("22T1"))
        assertNull(Validator.validateClassName("21dtha2"))
    }
}
