package com.otaman.users_list.di

import com.otaman.users_list.domain.repository.UsersRepository
import com.otaman.users_list.networking.JWTManager
import com.otaman.users_list.networking.UsersRepositoryImpl
import com.otaman.users_list.networking.UsersService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://opn-interview-service.nn.r.appspot.com/"
    
    @Provides
    @Singleton
    fun provideUsersRepository(jwtManager: JWTManager): UsersRepository {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val service = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .client(client)
            .build()
            .create(UsersService::class.java)

        return UsersRepositoryImpl(service, jwtManager)
    }
}