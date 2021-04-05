package com.flowz.byteworksjobtask.Model.modelsfromjsonfile


import com.google.gson.annotations.SerializedName

data class CountryStateModelLiteItem(
    val capital: String,
    val currency: String,
    @SerializedName("currency_symbol")
    val currencySymbol: String,
    val emoji: String,
    val emojiU: String,
    val iso2: String,
    val iso3: String,
    val latitude: String,
    val longitude: String,
    val name: String,
    val native: String,
    @SerializedName("phone_code")
    val phoneCode: String,
    val region: String,
    val states: List<State>,
    val subregion: String,
    val timezones: List<Timezone>,
    val tld: String,
    val translations: Translations
)