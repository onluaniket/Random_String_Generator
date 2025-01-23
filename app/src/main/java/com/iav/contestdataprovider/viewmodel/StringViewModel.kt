package com.iav.contestdataprovider.viewmodel

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iav.contestdataprovider.model.GeneratedString
import org.json.JSONObject

class StringViewModel(application: Application) : AndroidViewModel(application) {

    private val _generatedStrings = MutableLiveData<List<GeneratedString>>()
    val generatedStrings: LiveData<List<GeneratedString>> = _generatedStrings

    private val context = getApplication<Application>().applicationContext

    fun fetchRandomString(length: Int) {
        val uri = Uri.parse("content://com.iav.contestdataprovider/text")
        val selectionArgs = arrayOf(length.toString())

        try {
            val cursor = context.contentResolver.query(uri, arrayOf("data"), "length = ?", selectionArgs, null)

            cursor?.let {
                if (it.moveToFirst()) {
                    val jsonString = it.getString(it.getColumnIndex("data"))
                    val randomText = JSONObject(jsonString).getJSONObject("randomText")
                    val value = randomText.getString("value")
                    val created = randomText.getString("created")
                    val generatedString = GeneratedString(value, length, created)

                    _generatedStrings.value = _generatedStrings.value.orEmpty() + generatedString
                }
                it.close()
            }
        } catch (e: Exception) {
            Log.e("StringViewModel", "Error fetching random string", e)
        }
    }

    fun clearAllStrings() {
        _generatedStrings.value = emptyList()

    }

    fun deleteString(generatedString: GeneratedString) {
        _generatedStrings.value = _generatedStrings.value.orEmpty().filter { it != generatedString }
    }
}
