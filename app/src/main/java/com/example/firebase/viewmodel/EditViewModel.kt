package com.example.firebase.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase.modeldata.DetailSiswa
import com.example.firebase.modeldata.toDetailSiswa
import com.example.firebase.modeldata.toSiswa
import com.example.firebase.repositori.RepositorySiswa
import kotlinx.coroutines.launch

data class EditUiState(
    val detailSiswa: DetailSiswa = DetailSiswa(),
    val isEntryValid: Boolean = false,
    val isLoading: Boolean = false,
    val isLoadingData: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

class EditViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositorySiswa: RepositorySiswa
) : ViewModel() {

    private val siswaId: String = checkNotNull(savedStateHandle["siswaId"])

    var uiState by mutableStateOf(EditUiState(isLoadingData = true))
        private set

    init {
        loadSiswa()
    }


    private fun loadSiswa() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoadingData = true, error = null)

            try {
                val siswa = repositorySiswa.getSiswa(siswaId)
                if (siswa != null) {
                    uiState = uiState.copy(
                        detailSiswa = siswa.toDetailSiswa(),
                        isLoadingData = false,
                        isEntryValid = true
                    )
                } else {
                    uiState = uiState.copy(
                        isLoadingData = false,
                        error = "Data tidak ditemukan"
                    )
                }
            } catch (e: Exception) {
                uiState = uiState.copy(
                    isLoadingData = false,
                    error = e.message ?: "Terjadi kesalahan"
                )
            }
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiState = uiState.copy(
            detailSiswa = detailSiswa,
            isEntryValid = validateInput(detailSiswa)
        )
    }

    private fun validateInput(detailSiswa: DetailSiswa): Boolean {
        return with(detailSiswa) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    fun updateSiswa() {
        if (!validateInput(uiState.detailSiswa)) {
            return
        }

        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)

            try {
                repositorySiswa.updateSiswa(uiState.detailSiswa.toSiswa())
                uiState = uiState.copy(
                    isLoading = false,
                    isSuccess = true
                )
            } catch (exception: Exception) {
                uiState = uiState.copy(
                    isLoading = false,
                    error = exception.message ?: "Gagal mengupdate data"
                )
            }
        }
    }
}

