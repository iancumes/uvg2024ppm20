// UserViewModel.kt
package edu.uvg.uvgchatejemplo.viewmodel

import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import edu.uvg.uvgchatejemplo.Injection
import edu.uvg.uvgchatejemplo.data.*
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val userRepository: UserRepository

    init {
        userRepository = UserRepository(
            FirebaseAuth.getInstance(),
            Injection.instance()
        )
        loadCurrentUser()
    }

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> get() = _currentUser

    fun loadUsers() {
        viewModelScope.launch {
            when (val result = userRepository.getAllUsers()) {
                is Result.Success -> _users.value = result.data
                is Result.Error -> { /* Handle error */ }
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
