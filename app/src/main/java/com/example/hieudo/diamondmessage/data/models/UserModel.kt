package com.example.hieudo.diamondmessage.data.models

data class UserModel(val id: String ?= null,
                     val email: String ?= null,
                     val password: String ?= null,
                     val fullName: String ?= null,
                     val imageUrl: String ?= null,
                     val quickbloxId: String ?= null) {
}