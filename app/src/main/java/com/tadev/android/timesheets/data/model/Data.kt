package com.tadev.android.timesheets.data.model

import com.google.gson.annotations.SerializedName

data class AccountLoginRequest(
    @SerializedName("phone") var phone: String = "",
    @SerializedName("password") var password: String = "",
)

data class AccountLoginResponse(
    @SerializedName("status") var status: String = "",
    @SerializedName("data") var data: Data,
)

data class Data(
    @SerializedName("token") var token: Token,
    @SerializedName("slot_socket_name") var slotSocketName: String = "",
    @SerializedName("slot_page_type") var slotSocketType: String = "",
)

data class Token(
    @SerializedName("access_token") var accessToken: String = "",
    @SerializedName("refresh_token") var refreshToken: String = "",
)


data class TodosResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("completed") val completed: Boolean
)

data class CommentsResponse(
    @SerializedName("postId") val postId: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("body") val body: Boolean
)

data class PostsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("userId") val userId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String
)

data class PostPostsResponse(
    @SerializedName("id") val id: Int,
)

data class UsersResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String? = null,
    @SerializedName("username") val username: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("address") val address: Address,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("website") val website: String? = null,
    @SerializedName("company") val company: Company
)

data class Geo(
    @SerializedName("lat") val lat: Double? = null,
    @SerializedName("lng") val lng: Double? = null
)

data class Company(
    @SerializedName("name") val name: String? = null,
    @SerializedName("catchPhrase") val catchPhrase: String? = null,
    @SerializedName("bs") val bs: String? = null
)

data class Address(
    @SerializedName("street") val street: String? = null,
    @SerializedName("suite") val suite: String? = null,
    @SerializedName("city") val city: String? = null,
    @SerializedName("zipcode") val zipcode: String? = null,
    @SerializedName("geo") val geo: Geo
)