package com.example.nasagridapp.app.utils

import android.content.Context
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.CoroutineContext

class Utils {

    fun getJsonFromAssets(context: Context, fileName: String?): String? {
        val jsonString: String = try {
            val `is`: InputStream? = fileName?.let { context.assets.open(it) }
            val size: Int = `is`!!.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            String(buffer, Charset.forName("UTF-8"))
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }
        return jsonString
    }

    fun getDate(dateString: String?, format:String?): Date? {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        return try {
            formatter.parse(dateString)
        } catch (ex: Exception) {
            null
        }
    }

}