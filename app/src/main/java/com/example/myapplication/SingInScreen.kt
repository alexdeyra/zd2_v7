package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class SingInScreen : AppCompatActivity() {
    private lateinit var log: EditText
    private lateinit var pass: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_in_screen)
        log = findViewById(R.id.login)
        pass = findViewById(R.id.password)
    }
    fun next(view: View) {
        if (log.text.toString().isNotEmpty() && pass.text.toString().isNotEmpty()) {
            if (pass.length() > 8) {
                val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("username", log.text.toString())
                editor.putString("password", pass.text.toString())
                editor.apply()
                val intent = Intent(this, MainScreen::class.java)
                startActivity(intent)
            } else {
                Snackbar.make(view, "Пароль минимум 8 символов", Snackbar.LENGTH_LONG)
                    .show();
            }

        } else {
            Snackbar.make(view, "Заполните все поля", Snackbar.LENGTH_LONG)
                .show();
        }
    }
}