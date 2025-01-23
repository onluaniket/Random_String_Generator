package com.iav.contestdataprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RandomStringContentProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val length = selectionArgs?.get(0)?.toIntOrNull() ?: 10
        val randomString = generateRandomString(length)

        val matrixCursor = MatrixCursor(arrayOf("data"))
        val createdAt = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).format(Date())

        val json = """
            {
                "randomText": {
                    "value": "$randomString",
                    "length": $length,
                    "created": "$createdAt"
                }
            }
        """

        matrixCursor.addRow(arrayOf(json))
        return matrixCursor
    }

    private fun generateRandomString(length: Int): String {
        val characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..length).map { characters.random() }.joinToString("")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int = 0
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0
    override fun getType(uri: Uri): String? = null
}
