package com.example.fetchrewardscodingexercise

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.fetchrewardscodingexercise.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchData()
    }

    private fun fetchData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val json = URL("https://fetch-hiring.s3.amazonaws.com/hiring.json").readText()
                val itemType = object : TypeToken<List<Item>>() {}.type
                val items = gson.fromJson<List<Item>>(json, itemType)

                // Process data according to requirements
                val filteredItems = items
                    .filter { it.name != null && it.name.isNotBlank() }
                    .sortedWith(compareBy({ it.listId }, { it.name }))
                    .groupBy { it.listId }

                withContext(Dispatchers.Main) {
                    displayItems(filteredItems)
                }
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching data", e)
                withContext(Dispatchers.Main) {
                    binding.errorText.text = "Error: ${e.message}"
                    binding.errorText.visibility = android.view.View.VISIBLE
                }
            }
        }
    }

    private fun displayItems(itemsByListId: Map<Int, List<Item>>) {
        val container = binding.itemsContainer
        container.removeAllViews()

        itemsByListId.forEach { (listId, items) ->
            // Add listId header
            val header = TextView(this).apply {
                text = "List ID: $listId"
                textSize = 20f
                setPadding(16, 16, 16, 8)
            }
            container.addView(header)

            // Add items for this listId
            items.forEach { item ->
                val itemView = TextView(this).apply {
                    text = "Name: ${item.name} (ID: ${item.id})"
                    setPadding(32, 8, 16, 8)
                }
                container.addView(itemView)
            }

            // Add spacing between groups
            container.addView(TextView(this).apply {
                setPadding(0, 16, 0, 0)
            })
        }
    }
} 