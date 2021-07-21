package com.joyy.gradledownloadplugin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var switch = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var textStr = findViewById<TextView>(R.id.textStr)

        textStr.setOnClickListener {
            switch = !switch
            // 切换语言
            if (switch)
                MultiLanguageHelper.switchEN(this)
            else
                MultiLanguageHelper.switchKR(this)

            textStr.text = ""
            arrayListOf(
                R.string.check_network_toast,
                R.string.not_login_reminder_course,
                R.string.report_problem_describe,
                R.string.download_file_invalid,
//                R.string.Network_Diagnostics,
                R.string.leave_early,
                R.string.report_problem_question_content,
                R.string.leave_message_describe,
//                R.string.Help_Support,
                R.string.router_uri_cannot_open,
//                R.string.Login_successfully,
                R.string.not_login_reminder_timatable,
                R.string.not_login_reminder_batch,
                R.string.live_end,
//                R.string.Report_a_Problem,
//                R.string.Sign_Up_Successfully,
                R.string.check_network_state,
                R.string.deadline,
//                R.string.zoom_error_livelinkInvalid,
                R.string.faq_describe,
                R.string.not_login_reminder_chatlist,
                R.string.check_network_warning,
                R.string.report_problem_question_title,
                R.string.send_otp,
//                R.string.timetable_Nodata_alertString,
                R.string.timetable,
                R.string.check_network_describe,
//                R.string.Attendance_Status,
//                R.string.ResendFormats,
                R.string.contact_via_whatsapp_describe
            ).forEach { id ->
                textStr.append(getString(id))
                textStr.append("\n")
            }
        }
    }
}