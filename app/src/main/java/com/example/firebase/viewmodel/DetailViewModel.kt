package com.example.firebase.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase.modeldata.Siswa
import com.example.firebase.repositori.RepositorySiswa
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class DetailUiState(
    val detailSiswa: Siswa? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositorySiswa: RepositorySiswa
) : ViewModel() {

    private val siswaId: String = checkNotNull(savedStateHandle["siswaId"])

    private val _detailUiState = MutableStateFlow(DetailUiState(isLoading = true))
    val detailUiState: StateFlow<DetailUiState> = _detailUiState.asStateFlow()

    init {
        getSiswaById()
    }

    private fun getSiswaById() {
        viewModelScope.launch {
            _detailUiState.update { it.copy(isLoading = true, error = null) }

            try {
                val siswa = repositorySiswa.getSiswa(siswaId)
                _detailUiState.update {
                    it.copy(
                        detailSiswa = siswa,
                        isLoading = false,
                        error = if (siswa == null) "Data tidak ditemukan" else null
                    )
                }
            } catch (e: Exception) {
                _detailUiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Terjadi kesalahan"
                    )
                }
            }
        }
    }
}