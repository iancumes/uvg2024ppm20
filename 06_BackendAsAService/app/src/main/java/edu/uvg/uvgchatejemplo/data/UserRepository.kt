// UserRepository.kt
package edu.uvg.uvgchatejemplo.data

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    suspend fun signUp(
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ): Result<Boolean> = try {
        auth.createUserWithEmailAndPassword(email, password).await()
        val user = User(firstName, lastName, email)
        saveUserToFirestore(user)
        Result.Success(true)
    } catch (e: Exception) {
        Result.Error(e)
    }

    suspend fun login(email: String, password: String): Result<Boolean> = try {
        auth.signInWithEmailAndPassword(email, password).await()
        Result.Success(true)
    } catch (e: Exception) {
        Result.Error(e)
    }

    private suspend fun saveUserToFirestore(user: User) {
        firestore.collection("users").document(user.email).set(user).await()
    }

    suspend fun getCurrentUser(): Result<User> = try {
        val email = auth.currentUser?.email
        if (email != null) {
            val userDocument = firestore.collection("users").document(email).get().await()
            val user = userDocument.toObject(User::class.java)
            if (user != null) {
                Result.Success(user)
            } else {
                Result.Error(Exception("User data not found"))
            }
        } else {
            Result.Error(Exception("User not authenticated"))
        }
    } catch (e: Exception) {
        Result.Error(e)
    }

    suspend fun getUserByEmail(email: String): Result<User> = try {
        val userDocument = firestore.collection("users").document(email).get().await()
        val user = userDocument.toObject(User::class.java)
        if (user != null) {
            Result.Success(user)
        } else {
            Result.Error(Exception("User not found"))
        }
    } catch (e: Exception) {
        Result.Error(e)
    }

    suspend fun getAllUsers(): Result<List<User>> = try {
        val querySnapshot = firestore.collection("users").get().await()
        val users = querySnapshot.documents.mapNotNull { it.toObject(User::class.java) }
        Result.Success(users)
    } catch (e: Exception) {
        Result.Error(e)
    }
}
