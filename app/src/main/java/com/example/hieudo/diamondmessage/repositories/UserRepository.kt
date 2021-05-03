package com.example.hieudo.diamondmessage.repositories

import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.example.hieudo.diamondmessage.data.models.UserModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser
import kotlinx.coroutines.tasks.await
import java.lang.Exception


class UserRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val qbUser: QBUser = QBUser()
    private val db = Firebase.firestore

    fun getCurrentUser (): FirebaseUser? {
        return auth.currentUser
    }

    fun signInWithEmailPassword (email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun loginWithEmailPassword (email: String, password: String): Task<AuthResult>? {
        return auth.signInWithEmailAndPassword(email, password)
    }

    suspend fun saveNewUser(): Boolean {

        val firebaseUser = auth.currentUser
        val userModel = UserModel(firebaseUser?.uid,
            firebaseUser?.email.toString(),
            qbUser.password,
            firebaseUser?.displayName)

        return try {
            val response = db.collection("users").document(userModel.id.toString()).set(userModel).await()
            true
        }catch (e: Exception){
            Log.d("saveUserFail", e.message)
            false
        }
    }


    fun signInQuickbloc(firebaseUser: FirebaseUser){
        var response : Task<Void>
        qbUser.login = firebaseUser.uid
        qbUser.password = "123456789"

        QBUsers.signUp(qbUser).performAsync(object : QBEntityCallback<QBUser?> {
            override fun onSuccess(user: QBUser?, args: Bundle) {

            }
            override fun onError(error: QBResponseException) {

            }
        })

    }


    fun logout() {
        auth.signOut()
    }
}