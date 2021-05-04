package com.example.githubers.data.models

import com.example.mvvm.data.models.User

data class UserResponse(
	val totalCount: Int? = null,
	val incompleteResults: Boolean? = null,
	val items: List<User>? = null
)


