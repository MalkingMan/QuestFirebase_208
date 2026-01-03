package com.example.firebase.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebase.modeldata.Siswa
import com.example.firebase.repositori.RepositorySiswa
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    data class Success(val data: List<Siswa>) : HomeUiState
    object Error : HomeUiState
    object Loading : HomeUiState
}

class HomeViewModel(private val repositorySiswa: RepositorySiswa): ViewModel() {
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    init{
        loadSiswa()
    }

    fun loadSiswa(){
        viewModelScope.launch {
            repositorySiswa.getAllSiswa()
                .onStart { homeUiState = HomeUiState.Loading }
                .catch {
                    homeUiState = HomeUiState.Error
                }
                .collect{
                    homeUiState = if (it.isEmpty()){
                        HomeUiState.Error
                    } else {
                        HomeUiState.Success(it)
                    }
                }
        }
    }
}
