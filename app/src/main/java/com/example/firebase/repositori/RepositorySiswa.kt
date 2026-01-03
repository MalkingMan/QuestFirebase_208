package com.example.firebase.repositori

import android.util.Log
import com.example.firebase.modeldata.Siswa
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

interface RepositorySiswa {
    fun getAllSiswa(): Flow<List<Siswa>>
    suspend fun getSiswa(id: String): Siswa?
    suspend fun insertSiswa(siswa: Siswa)
    suspend fun updateSiswa(siswa: Siswa)
    suspend fun deleteSiswa(id: String)
}

class FirebaseRepositorySiswa : RepositorySiswa {
    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("siswa")

    init {
        Log.d("FirebaseRepository", "FirebaseFirestore initialized")
        Log.d("FirebaseRepository", "Collection path: siswa")
    }

    override fun getAllSiswa(): Flow<List<Siswa>> = flow {
        try {
            val siswaList = collection.get().await().documents.map { doc ->
                Siswa(
                    id = doc.id,
                    nama = doc.getString("nama") ?: "",
                    alamat = doc.getString("alamat") ?: "",
                    telpon = doc.getString("telpon") ?: ""
                )
            }
            emit(siswaList)
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "getAllSiswa failed: ${e.message}", e)
            throw e
        }
    }

    override suspend fun getSiswa(id: String): Siswa? {
        return try {
            val doc = collection.document(id).get().await()
            if (doc.exists()) {
                Siswa(
                    id = doc.id,
                    nama = doc.getString("nama") ?: "",
                    alamat = doc.getString("alamat") ?: "",
                    telpon = doc.getString("telpon") ?: ""
                )
            } else null
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "getSiswa failed: ${e.message}", e)
            throw e
        }
    }

    override suspend fun insertSiswa(siswa: Siswa) {
        try {
            Log.d("FirebaseRepository", "Memulai insert siswa: $siswa")
            val docRef = if (siswa.id.isEmpty()) collection.document() else collection.document(siswa.id)
            Log.d("FirebaseRepository", "Document ID: ${docRef.id}")

            val data = hashMapOf(
                "nama" to siswa.nama,
                "alamat" to siswa.alamat,
                "telpon" to siswa.telpon
            )
            Log.d("FirebaseRepository", "Data to insert: $data")

            docRef.set(data).await()
            Log.d("FirebaseRepository", "Siswa berhasil disimpan dengan ID: ${docRef.id}")
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "Error insert siswa: ${e.message}", e)
            throw Exception("Gagal menyimpan data: ${e.message}")
        }
    }

    override suspend fun updateSiswa(siswa: Siswa) {
        try {
            val data = hashMapOf(
                "nama" to siswa.nama,
                "alamat" to siswa.alamat,
                "telpon" to siswa.telpon
            )
            collection.document(siswa.id).set(data).await()
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "updateSiswa failed: ${e.message}", e)
            throw e
        }
    }

    override suspend fun deleteSiswa(id: String) {
        try {
            collection.document(id).delete().await()
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "deleteSiswa failed: ${e.message}", e)
            throw e
        }
    }
}
