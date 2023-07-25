package org.hyperskill.secretdiary

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.datetime.*
import java.text.SimpleDateFormat
import java.util.Locale
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences

const val PREFERENCES_NAME = "PREF_DIARY"

class MainActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dispEditText = findViewById<EditText>(R.id.etNewWriting)
        val textView = findViewById<TextView>(R.id.tvDiary)
        textView.movementMethod = ScrollingMovementMethod()
        
        sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        textView.text = sharedPreferences.getString("KEY_DIARY_TEXT", "")

        findViewById<Button>(R.id.btnSave).setOnClickListener {
            if (dispEditText.text.isBlank() || dispEditText.text.isEmpty()) {
                Toast.makeText(applicationContext, "Empty or blank input cannot be saved", Toast.LENGTH_SHORT).show()
            } else {
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val dateTimeText = simpleDateFormat.format(Clock.System.now().toEpochMilliseconds())
                var listText = ("$dateTimeText\n${dispEditText.text}\n\n") + textView.text
                textView.text = listText.trim()
                editor.putString("KEY_DIARY_TEXT", listText.trim()).apply()
                dispEditText.text.clear()
            }
        }

        findViewById<Button>(R.id.btnUndo).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Remove last note")
                .setMessage("Do you really want to remove the last writing? This operation cannot be undone!")
                .setPositiveButton("Yes") { _, _ ->
                    val text = textView.text.toString()
                    if (!text.contains("\n\n")) {
                        textView.text = ""
                        editor.putString("KEY_DIARY_TEXT", textView.text.toString()).apply()
                    } else {
                        textView.text = text.substringAfter("\n\n").trim()
                        editor.putString("KEY_DIARY_TEXT", textView.text.toString()).apply()
                    }
                }
                .setNegativeButton("No", null)
                .show()
        }

    }

}
