package com.dicoding.courseschedule.ui.add


import android.annotation.SuppressLint
import android.icu.util.Calendar
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.databinding.ActivityAddCourseBinding
import com.dicoding.courseschedule.util.Event
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.Locale

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener{

    private val viewModel by viewModels<AddCourseViewModel> {
        AddViewModelFactory.createFactory(this)
    }
    private var _binding: ActivityAddCourseBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddCourseBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val spinner: Spinner = findViewById(R.id.spinnerDays)
        ArrayAdapter.createFromResource(
            this,
            R.array.day,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        supportActionBar?.title = getString(R.string.add_course)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_insert -> {

                val courseName = binding?.edtCourseName?.text.toString()
                val selectedDayIndex = binding?.spinnerDays?.selectedItemPosition ?: 0
                val startTime = binding?.addTvStartTime?.text.toString()
                val endTime = binding?.addTvEndTime?.text.toString()
                val lecturer = binding?.edtLecturer?.text.toString()
                val note = binding?.edtNote?.text.toString()

                viewModel.insertCourse(courseName,selectedDayIndex,startTime,endTime,lecturer,note)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSnackBar(eventMessage: Event<Int>) {
        val message = eventMessage.getContentIfNotHandled() ?: return
        Snackbar.make(
            findViewById(R.id.constraint_layout),
            getString(message),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    fun showTimePicker(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, TIME_PICKER_START_TIME)
    }

    fun showTimePicker2(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, TIME_PICKER_END_TIME)
    }

    @SuppressLint("NewApi")
    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)


        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            TIME_PICKER_START_TIME -> binding?.addTvStartTime?.text = dateFormat.format(calendar.time)
            TIME_PICKER_END_TIME -> {binding?.addTvEndTime?.text = dateFormat.format(calendar.time)}
            else -> {
            }
        }
    }

    companion object {
        private const val TIME_PICKER_START_TIME = "TimePickerStart"
        private const val TIME_PICKER_END_TIME = "TimePickerEnd"
    }

}