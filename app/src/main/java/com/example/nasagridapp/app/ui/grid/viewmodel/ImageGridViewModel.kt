package com.example.nasagridapp.app.ui.grid.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasagridapp.app.model.ImageModel
import com.example.nasagridapp.app.utils.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class ImageGridViewModel : ViewModel() {

    val imageList: MutableLiveData<List<ImageModel>> = MutableLiveData()

    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd"
    }

    fun getImageList(imageJSONPath: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val gson = Gson()
            val nasaImages: Type = object : TypeToken<List<ImageModel?>?>() {}.type
            val images: MutableList<ImageModel> = gson.fromJson(imageJSONPath, nasaImages)
            images.sortWith { image1, image2 ->
                val image1Date = Utils().getDate(image1.date, DATE_FORMAT)
                val image2Date = Utils().getDate(image2.date, DATE_FORMAT)
                image2Date?.compareTo(image1Date) ?: 0
            }
            imageList.postValue(images)
        }
    }

}