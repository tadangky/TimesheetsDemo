package com.tadev.android.timesheets.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.tadev.android.timesheets.BuildConfig
import com.tadev.android.timesheets.data.api.ApiService
import com.tadev.android.timesheets.data.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://run.mocky.io")
//        .baseUrl("https://jsonplaceholder.typicode.com")
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideClient(pref: SharedPreferences): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        builder.addInterceptor(BasicAuthInterceptor(pref))

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    class BasicAuthInterceptor(private val pref: SharedPreferences) : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val newRequest = chain.request().newBuilder()
                //For test authen
                .addHeader("TATA", pref.getString("testing", "testing abc") ?: "JJJJJ")
                .build()
            return chain.proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, AppDatabase::class.java, "DBNAME").build()

    @Provides
    @Singleton
    fun provideTodosDao(db: AppDatabase) = db.todosDao

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext app: Context): SharedPreferences {
        return app.getSharedPreferences(app.packageName, MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSharedPreferenceEditor(sharedPreferences: SharedPreferences): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }
}