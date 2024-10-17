// MessageRepository.kt
package edu.uvg.uvgchatejemplo.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class MessageRepository(private val firestore: FirebaseFirestore) {

    suspend fun sendMessage(message: Message): Result<Unit> = try {
        val chatId = getChatId(message.senderId, message.receiverId)
        firestore.collection("chats").document(chatId)
            .collection("messages").add(message).await()
        Result.Success(Unit)
    } catch (e: Exception) {
        Result.Error(e)
    }

    fun getChatMessages(senderId: String, receiverId: String): Flow<List<Message>> = callbackFlow {
        val chatId = getChatId(senderId, receiverId)
        val subscription = firestore.collection("chats").document(chatId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                querySnapshot?.let {
                    val messages = it.documents.mapNotNull { doc ->
                        doc.toObject(Message::class.java)
                    }
                    trySend(messages).isSuccess
                }
            }

        awaitClose { subscription.remove() }
    }

    private fun getChatId(userId1: String, userId2: String): String {
        return if (userId1 < userId2) "${userId1}_${userId2}" else "${userId2}_${userId1}"
    }
}
