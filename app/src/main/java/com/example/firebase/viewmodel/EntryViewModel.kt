package com.example.firebase.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.firebase.modeldata.DetailSiswa
import com.example.firebase.modeldata.UIStateSiswa
import com.example.firebase.modeldata.toSiswa
import com.example.firebase.repositori.RepositorySiswa

class EntryViewModel(private val repositorySiswa: RepositorySiswa): ViewModel() {
    var uiStateSiswa by mutableStateOf(UIStateSiswa())
        private set

    /* Fungsi untuk memvalidasi input */
    private fun validasiInput(uiState: DetailSiswa = uiStateSiswa.detailSiswa ): Boolean
    {
        return with(uiState) {
            nama.isNotBlank() && alamat.isNotBlank() && telpon.isNotBlank()
        }
    }

    fun updateUiState(detailSiswa: DetailSiswa) {
        uiStateSiswa =
            UIStateSiswa(detailSiswa = detailSiswa, isEntryValid = validasiInput(detailSiswa))
    }

    /* Fungsi untuk menyimpan data yang di-entry */
    suspend fun saveSiswa() {
        if (validasiInput()) {
            try {
                Log.d("EntryViewModel", "Menyimpan siswa: ${uiStateSiswa.detailSiswa}")
                repositorySiswa.insertSiswa(uiStateSiswa.detailSiswa.toSiswa())
                Log.d("EntryViewModel", "Siswa berhasil disimpan")
            } catch (e: Exception) {
                Log.e("EntryViewModel", "Error menyimpan siswa: ${e.message}", e)
                throw e
            }
        } else {
            Log.w("EntryViewModel", "Validasi gagal, data tidak lengkap")
        }
    }
}
