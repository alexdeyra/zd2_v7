package com.example.myapplication

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import android.content.Context

class ListScreen : AppCompatActivity() {
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_screen)

        listView = findViewById(R.id.listView)

        // Получаем данные из Intent
        val recipes = intent.getStringArrayListExtra("recipes") ?: loadRecipes()

        // Устанавливаем адаптер для ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, recipes)
        listView.adapter = adapter

        // Сохраняем данные в SharedPreferences
        saveRecipes(recipes)
    }

    private fun saveRecipes(recipes: List<String>) {
        val sharedPreferences = getSharedPreferences("MyRecipes", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putStringSet("recipes", recipes.toSet()) // Сохраняем как Set
        editor.apply()
    }

    private fun loadRecipes(): List<String> {
        val sharedPreferences = getSharedPreferences("MyRecipes", Context.MODE_PRIVATE)
        return sharedPreferences.getStringSet("recipes", emptySet())?.toList() ?: emptyList()
    }
}