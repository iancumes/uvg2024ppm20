package edu.uvg.uvgchatejemplo.data

data class Message(
    val senderId:String = "",
    val receiverId: String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis(),


)
