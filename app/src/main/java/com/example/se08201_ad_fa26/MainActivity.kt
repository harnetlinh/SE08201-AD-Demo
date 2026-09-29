package com.example.se08201_ad_fa26

import Student
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Message
import android.view.KeyEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Job
import java.time.LocalDate
import java.util.Date

class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val edtName = findViewById<EditText>(R.id.edtName);
        val edtStudentId = findViewById<EditText>(R.id.edtStudentId);
        val edtMajor = findViewById<EditText>(R.id.edtMajor);
        val btnShow = findViewById<Button>(R.id.btnShow);
        val spnAcademicYear = findViewById<Spinner>(R.id.spnAcademicYear);
        val ckbJoinClub = findViewById<CheckBox>(R.id.ckbJoinClub);
        val edtBirthYear = findViewById<EditText>(R.id.edtBirthYear);

        // Khai báo các giá trị cho spinner của layout
        val years = listOf(
            "Select study year",
            "Year 1",
            "Year 2",
            "Year 3"
        )
        // Khai báo biến trung gian đưa các giá trị vào spinner
        val yearAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            years
        );
        // gán giá trị cho spinner
        spnAcademicYear.adapter = yearAdapter;

        edtBirthYear.setOnKeyListener (View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP) {
                val birthYear = edtBirthYear.text.toString().toInt();
                var currentYear = LocalDate.now().year;
                if ((birthYear + 10) >= currentYear || (birthYear - 10) < 1940){
                    Toast.makeText(this, "Năm sinh không hợp lệ", Toast.LENGTH_LONG).show();
                    edtBirthYear.setBackgroundColor(Color.RED);
                }else{
                    edtBirthYear.setBackgroundColor(Color.GREEN);
                }
                return@OnKeyListener true
            }
            false
        })

        btnShow.setOnClickListener {
            var name = "[Unknown]";
            var studentid = "[Unknown]";
            var major = "[Unknown]";
            var isValid = true;
            val wantsJoinClub = ckbJoinClub.isChecked;
            var academicYear = spnAcademicYear.selectedItemPosition;
            var age = 0;

            if (edtName.text.toString().trim().isBlank()){
                isValid = false;
                Toast.makeText(this, "Không được để trống tên", Toast.LENGTH_LONG).show();
            } else {
                name = edtName.text.toString().trim();
            }

            if (edtStudentId.text.toString().trim().isBlank()){
                isValid = false;
                Toast.makeText(this, "Không được để trống Mã sinh viên", Toast.LENGTH_LONG).show();
            } else {
                studentid = edtStudentId.text.toString().trim();
            }

            if (edtMajor.text.toString().trim().isBlank()){
                isValid = false;
                Toast.makeText(this, "Không được để trống ngành", Toast.LENGTH_LONG).show();
            } else {
                major = edtMajor.text.toString().trim();
            }

            val birthYear = edtBirthYear.text.toString().toInt();
            var currentYear = LocalDate.now().year;
            if ((birthYear + 10) < currentYear && (birthYear - 10) >= 1940){
                age =  currentYear - birthYear;
            }else{
                isValid = false;
                Toast.makeText(this, "Năm sinh không hợp lệ", Toast.LENGTH_LONG).show();
                edtBirthYear.setBackgroundColor(Color.RED);
            }

            if(academicYear == 0){
                isValid = false;
                Toast.makeText(this, "Không được để trống năm học", Toast.LENGTH_LONG).show();
            }

            if (isValid){
                val theStudent = Student(name,major,studentid,academicYear,wantsJoinClub,birthYear);
                showInfor(theStudent);
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun showInfor(theStudent: Student){
        val message = theStudent.showInfor();
        AlertDialog.Builder(this)
            .setTitle("Thông báo ${theStudent.name}!! CHÚ Ý!!!")
            .setMessage(message).show();
    }
}