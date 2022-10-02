package com.iruda.countrybrowse

data class Country(
    val name: String,
    val capital: String,
    val population: Long,
    val area: Long,
    val currencies: List<Currency>,
    val languages: List<Language>,
) {

}

data class Currency(
    val name: String,
    val symbol: String,
) {

}

data class Language(
    val name: String,
) {

}