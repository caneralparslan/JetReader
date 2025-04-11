package com.amasmobile.jet_a_reader.models



data class MUser(
    val id: String?,
    val userId: String,
    val userName: String,
    val avatarUrl: String,
    val quote: String,
    val profession: String){

    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_id" to this.userId,
            "user_name" to this.userName,
            "quote" to this.quote,
            "avatar_url" to this.avatarUrl,
            "profession" to this.profession
        )
    }
}
