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
import com.team8.tsuinskips.presentation.models.AttachFileItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class NewRequestViewModel : ViewModel() {

    private val _selectedImages: MutableStateFlow<List<AttachFileItem>> = MutableStateFlow(listOf())
    val selectedImages: StateFlow<List<AttachFileItem>> = _selectedImages.asStateFlow()

    fun attachNewConfirmationFile(file: AttachFileItem) {
        viewModelScope.launch {
            _selectedImages.value = _selectedImages.value.toMutableList() + file
        }
    }

    fun changeConfirmationFileName(index: Int, name: String) {
        viewModelScope.launch {
            val images = _selectedImages.value.toMutableList()
            images[index] = images[index].copy(name = name)
            _selectedImages.value = images
        }
    }

    fun detachConfirmationFile(index: Int) {
        viewModelScope.launch {
            _selectedImages.value = _selectedImages.value.toMutableList().apply { removeAt(index) }
        }
    }

    fun createNewMissRequest(from: LocalDate, to: LocalDate, type: MissRequestType): Boolean {
        try {
            viewModelScope.launch {
                RequestRepository.createRequest(RequestCreateModel(
                    startDate = from,
                    endDate = to,
                    missRequestType = type,
                    confirmationFiles = _selectedImages.value.map { file ->
                        Attachment(
                            file.name,
                            loadBitmapFromUri(file.uri)
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