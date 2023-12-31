package org.hyperskill.secretdiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val etPin = findViewById<EditText>(R.id.etPin)

        findViewById<Button>(R.id.btnLogin).setOnClickListener {
            if (etPin.text.toString() == "1234") {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                etPin.error = "Wrong PIN!"
            }
        }
    }
}