package com.example.firebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.firebase.ui.theme.FirebaseTheme
import com.example.firebase.view.controllNavigasi.PetaNavigasi

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirebaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    PetaNavigasi()
                }
            }
        }
    }
}
