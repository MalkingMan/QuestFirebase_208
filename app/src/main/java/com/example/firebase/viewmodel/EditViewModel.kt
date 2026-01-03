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

