// MessageViewModel.kt
package edu.uvg.uvgchatejemplo.viewmodel

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import edu.uvg.uvgchatejemplo.Injection
import edu.uvg.uvgchatejemplo.data.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MessageViewModel : ViewModel() {

    private val messageRepository: MessageRepository
    private val userRepository: UserRepository

    init {
        messageRepository = MessageRepository(Injection.instance())
        userRepository = UserRepository(
            FirebaseAuth.getInstance(),
            Injection.instance()
        )
        loadCurrentUser()
    }

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> get() = _messages

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> get() = _currentUser

    private val _chatUser = MutableLiveData<User>()
    val chatUser: LiveData<User> get() = _chatUser

    private var encryptionShift: Int = 0

    fun loadChatUser(email: String, shift: Int) {
        encryptionShift = shift
        viewModelScope.launch {
            when (val result = userRepository.getUserByEmail(email)) {
                is Result.Success -> {
                    _chatUser.value = result.data
                    loadMessages()
                }
                is Result.Error -> { /* Handle error */ }
            }
        }
    }

    fun sendMessage(text: String) {
        val senderId = _currentUser.value?.email ?: return
        val receiverId = _chatUser.value?.email ?: return
        val encryptedText = CaesarCipherUtil.encrypt(text, encryptionShift)
        val message = Message(
            senderId = senderId,
            receiverId = receiverId,
            text = encryptedText
        )
        viewModelScope.launch {
            messageRepository.sendMessage(message)
        }
    }

    private fun loadMessages() {
        val senderId = _currentUser.value?.email ?: return
        val receiverId = _chatUser.value?.email ?: return
        viewModelScope.launch {
            messageRepository.getChatMessages(senderId, receiverId)
                .collect { messages ->
                    val decryptedMessages = messages.map { message ->
                        val decryptedText = CaesarCipherUtil.decrypt(message.text, encryptionShift)
                        message.copy(text = decryptedText)
                    }
                    _messages.value = decryptedMessages
                }
        }
    }

    private fun loadCurrentUser() {
        viewModelScope.launch {
            when (val result = userRepository.getCurrentUser()) {
                is Result.Success -> _currentUser.value = result.data
                is Result.Error -> { /* Handle error */ }
            }
        }
    }
}
