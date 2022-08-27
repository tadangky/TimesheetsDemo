package com.tadev.android.timesheets.data.api

import com.tadev.android.timesheets.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("todos")
    suspend fun listTodos(): List<TodosResponse>

    @GET("comments")
    suspend fun commentsByPostId(@Query("postId") postId: Int): List<CommentsResponse>

    @POST("posts")
    suspend fun postPosts(): Response<PostPostsResponse>

    @GET("posts")
    suspend fun getPosts(): List<PostsResponse>

    @GET("v3/ee38aae3-8eeb-491c-8aaa-d376dc9f7131/")
    suspend fun getJobs(): List<Job>

//    @GET("current")
//    suspend fun current(
//        @Query("access_key") access_key: String,
//        @Query("query") query: String
//    ): WeatherDataResponse

    @POST
    suspend fun login(@Url url: String, @Body accountLoginRequest: AccountLoginRequest): Response<AccountLoginResponse>

    @GET("users")
    suspend fun listUsers(): List<UsersResponse>

}

