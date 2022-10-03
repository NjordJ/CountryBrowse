package com.iruda.countrybrowse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import coil.size.Scale
import com.iruda.countrybrowse.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchButton.setOnClickListener{
            val countryName = binding.countryNameEditText.text.toString()

            lifecycleScope.launch{
                try {
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

                    loadImageFromApi(binding.imageView, country.flag)

                    binding.statusLayout.visibility = View.INVISIBLE
                    binding.resultLayout.visibility = View.VISIBLE
                } catch (e: Exception) {
                    binding.statusTextView.text = "Something went wrong"
                    binding.statusImageView.setImageResource(R.drawable.ic_baseline_error_24)

                    binding.statusLayout.visibility = View.VISIBLE
                    binding.resultLayout.visibility = View.INVISIBLE
                }
            }

        }
    }

    private fun languageToString(list: List<Language>): String {
        return list.joinToString { it.name }
    }

    private fun currencyToString(list: List<Currency>): String {
        return list.joinToString { it.name.plus(" " + it.symbol) }
    }

    private suspend fun loadImageFromApi(imageView: ImageView, url: String) {
        if(url.lowercase(Locale.ENGLISH).endsWith("svg")) {
            val imageLoader = ImageLoader.Builder(imageView.context)
                .components {
                    add(SvgDecoder.Factory())
                }
                .build()

            val request = ImageRequest.Builder(imageView.context)
                .data(url)
                .target(imageView)
                .build()
            imageLoader.execute(request)
        } else {
            imageView.load(url){
                scale(Scale.FILL)
            }
        }
    }
}