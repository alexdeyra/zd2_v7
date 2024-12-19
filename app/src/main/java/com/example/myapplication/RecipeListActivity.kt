package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley


class RecipeListActivity : AppCompatActivity() {
    private lateinit var artistNameEditText: EditText
    private lateinit var numberOfResultsEditText: EditText
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        // Инициализация элементов интерфейса
        artistNameEditText = findViewById(R.id.artistNameEditText)
        numberOfResultsEditText = findViewById(R.id.numberOfResultsEditText)
        textView = findViewById(R.id.textView)
    }

    fun Search(view: View) {
        val token = "DrdsOLjMHjYmrvpkDHTztHKprfIhEpPhnTKNWRWr" // Ваш токен API
        val url = "https://api.discogs.com/database/search?token=$token&artist=${artistNameEditText.text}&per_page=${numberOfResultsEditText.text}"

        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(
            com.android.volley.Request.Method.GET, url, null,
            { response ->
                val resultsList = mutableListOf<String>() // Список для хранения результатов
                val resultsArray = response.getJSONArray("results")
                for (i in 0 until resultsArray.length()) {
                    val result = resultsArray.getJSONObject(i)
                    val id = result.getInt("id")
                    val title = result.getString("title")
                    val resultInfo = "ID: $id\nTitle: $title\n"
                    resultsList.add(resultInfo)
                }

                // Передаем данные в ListScreen
                val intent = Intent(this, ListScreen::class.java)
                intent.putStringArrayListExtra("recipes", ArrayList(resultsList))
                startActivity(intent)
            },
            { error ->
                textView.text = "Error: ${error.message}"
            }
        )
        queue.add(jsonObjectRequest)
    }
}