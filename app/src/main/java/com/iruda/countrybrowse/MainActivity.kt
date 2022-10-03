package com.iruda.countrybrowse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.iruda.countrybrowse.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener{
            val countryName = binding.countryNameEditText.text.toString()

            lifecycleScope.launch{
                val countries = restCountriesAPI.getCountryByName(countryName)
                val country = countries[0]

                binding.countryNameTextView.text = country.name
                binding.capitalTextView.text = country.capital
                binding.popularityTextView.text = country.population.toString()
                binding.areaTextView.text = country.area.toString()
                val currency: String = currencyToString(country.currencies)
                binding.currencyTextView.text = currency
                val languages: String = languageToString(country.languages)
                binding.languagesTextView.text = languages
            }

        }
    }

    private fun languageToString(list: List<Language>): String {
        return list.joinToString { it.name }
    }

    private fun currencyToString(list: List<Currency>): String {
        return list.joinToString { it.name.plus(" " + it.symbol) }
    }
}