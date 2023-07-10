package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import com.dicoding.courseschedule.util.timeFormat
import com.google.android.material.textfield.TextInputEditText

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var viewModel      : AddCourseViewModel
    private lateinit var edCourseName   : TextInputEditText
    private lateinit var edLecturer     : TextInputEditText
    private lateinit var edNote         : TextInputEditText
    private lateinit var ibStartTime    : ImageButton
    private lateinit var ibEndTime      : ImageButton
    private lateinit var tvStartTime    : TextView
    private lateinit var tvEndTime      : TextView

    private var day: Int = 0
    private var startTime: String = ""
    private var endTime: String = ""
    private var ibChecker: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = AddCourseViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory)[AddCourseViewModel::class.java]

        val spinner = findViewById<Spinner>(R.id.day_spinner)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapter: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                day = position
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        tvStartTime = findViewById(R.id.tv_start_time)
        tvEndTime = findViewById(R.id.tv_end_time)
        ibStartTime = findViewById(R.id.ib_start_time)
        ibEndTime = findViewById(R.id.ib_end_time)

        ibStartTime.setOnClickListener {
            ibChecker = "startTime"
            val timePickerFragment: DialogFragment = TimePickerFragment()
            timePickerFragment.show(supportFragmentManager, "timePickerFragment")
        }

        ibEndTime.setOnClickListener {
            ibChecker = "endTime"
            val timePickerFragment: DialogFragment = TimePickerFragment()
            timePickerFragment.show(supportFragmentManager, "timePickerFragment")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                edCourseName = findViewById(R.id.ed_course_name)
                edLecturer = findViewById(R.id.ed_lecturer)
                edNote = findViewById(R.id.ed_note)

                val courseName = edCourseName.text.toString()
                val lecturer = edLecturer.text.toString()
                val note = edNote.text.toString()

                viewModel.insertCourse(courseName, day, startTime, endTime, lecturer, note)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        if (ibChecker == "startTime") {
            startTime = timeFormat(hour, minute)
            tvStartTime.text = startTime
        } else if (ibChecker == "endTime") {
            endTime = timeFormat(hour, minute)
            tvEndTime.text = endTime
        }
    }
}