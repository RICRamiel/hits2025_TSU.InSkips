package com.team8.tsuinskips.viewModel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team8.tsuinskips.common.application.InSkipsApplication
import com.team8.tsuinskips.data.datasource.MissRequestType
import com.team8.tsuinskips.data.repository.RequestRepository
import com.team8.tsuinskips.domain.Attachment
import com.team8.tsuinskips.domain.RequestCreateModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class NewRequestViewModel : ViewModel() {

    private val _selectedImagesUris: MutableStateFlow<List<Uri>> = MutableStateFlow(listOf())
    val selectedImagesUris: StateFlow<List<Uri>> = _selectedImagesUris.asStateFlow()

    fun attachNewConfirmationFile(uri: Uri) {
        viewModelScope.launch {
            _selectedImagesUris.value = _selectedImagesUris.value.toMutableList() + uri
        }
    }

    fun detachConfirmationFile(index: Int) {
        viewModelScope.launch {
            _selectedImagesUris.value = _selectedImagesUris.value.toMutableList().apply { removeAt(index) }
        }
    }

    fun createNewMissRequest(from: LocalDate, to: LocalDate, type: MissRequestType): Boolean {
        try {
            viewModelScope.launch {
                RequestRepository.createRequest(RequestCreateModel(
                    startDate = from,
                    endDate = to,
                    missRequestType = type,
                    confirmationFiles = _selectedImagesUris.value.map { uri ->
                        Attachment(
                            uri.lastPathSegment ?: "",
                            loadBitmapFromUri(uri)
                        )
                    }
                ))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    private fun loadBitmapFromUri(uri: Uri): Bitmap {
        val inputStream = InSkipsApplication.getApp().contentResolver.openInputStream(uri)
        return BitmapFactory.decodeStream(inputStream)
    }
}