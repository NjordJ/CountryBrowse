package com.iruda.countrybrowse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.iruda.countrybrowse.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
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
                binding.currencyTextView.text = country.currencies.toString()
                binding.languagesTextView.text = country.languages.toString()
            }

        }
    }
}