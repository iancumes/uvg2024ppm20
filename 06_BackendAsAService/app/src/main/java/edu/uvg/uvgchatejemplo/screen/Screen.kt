// Screen.kt
package edu.uvg.uvgchatejemplo.screen

sealed class Screen(val route: String) {
    object LoginScreen : Screen("loginscreen")
    object SignupScreen : Screen("signupscreen")
    object HomeScreen : Screen("homescreen")
    object ChatScreen : Screen("chatscreen")
}
