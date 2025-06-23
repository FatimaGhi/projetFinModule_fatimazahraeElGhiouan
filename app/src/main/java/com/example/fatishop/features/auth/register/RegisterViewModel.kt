package com.example.fatishop.features.auth.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fatishop.shared.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.State




class RegisterViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val _registerState = mutableStateOf<RegisterUiState>(RegisterUiState.Idle)
    val registerState: State<RegisterUiState> = _registerState

    fun register(email: String, password: String, fullName: String) {
        _registerState.value = RegisterUiState.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = task.result?.user?.uid
                    val user = User(id = userId ?: "", fullName = fullName, email = email)

                    firestore.collection("users")
                        .document(userId!!)
                        .set(user)
                        .addOnSuccessListener {
                            _registerState.value = RegisterUiState.Success
                        }
                        .addOnFailureListener { e ->
                            _registerState.value = RegisterUiState.Error("Failed to save user: ${e.message}")
                        }
                } else {
                    _registerState.value = RegisterUiState.Error("Registration failed: ${task.exception?.message}")
                }
            }
    }
}



sealed class RegisterUiState {
    object Idle : RegisterUiState()
    object Loading : RegisterUiState()
    object Success : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}
