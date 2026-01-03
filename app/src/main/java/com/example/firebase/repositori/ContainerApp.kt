package com.example.firebase.repositori

import android.app.Application
import com.google.firebase.FirebaseApp

interface ContainerApp {
    val repositorySiswa: RepositorySiswa
}
class DefaultContainerApp : ContainerApp {
    override val repositorySiswa: RepositorySiswa by lazy {
        FirebaseRepositorySiswa()
    }
}

class AplikasiDataSiswa : Application() {
    lateinit var container: ContainerApp
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        this.container = DefaultContainerApp()
    }
}