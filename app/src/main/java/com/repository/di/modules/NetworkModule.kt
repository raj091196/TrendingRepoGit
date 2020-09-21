package com.repository.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.repository.Application
import com.repository.R
import com.repository.repository.ApiClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Named("baseUrl")
    fun provideBaseUrl(application: Application): String =
        application.getString(R.string.api_domain)

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder = OkHttpClient.Builder()

    @Provides
    @Singleton
    fun provideGSONFactory(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        builder: OkHttpClient.Builder
    ): OkHttpClient {
        return builder.addInterceptor(interceptor)
            .followRedirects(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        gson: Gson,
        @Named("baseUrl") baseUrl: String
    ): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideApiCalls(retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }
}
