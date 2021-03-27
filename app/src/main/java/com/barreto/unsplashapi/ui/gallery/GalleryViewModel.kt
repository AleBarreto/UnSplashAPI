package com.barreto.unsplashapi.ui.gallery


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.barreto.unsplashapi.api.repository.UnSplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val repository: UnSplashRepository) :
    ViewModel() {

    companion object {
        private const val DEFAULT_QUERY = "cats"
    }


    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val photos = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString)
    }

    fun searchPhotos(query: String) {
        currentQuery.value = query
    }

}