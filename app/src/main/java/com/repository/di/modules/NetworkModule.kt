package com.repository.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.repository.Application
import com.repository.BuildConfig
import com.repository.R
import com.repository.repository.ApiClient
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * DI modules for the network calls
 */
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
        if (BuildConfig.DEBUG) builder.addInterceptor(interceptor)
        return builder.connectTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .followRedirects(false).build()
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

    /**
     *  @return ApiClient has the methods to make network calls
     *  provides the Instance for the apiCalls
     */
    @Provides
    @Singleton
    fun provideApiCalls(retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }
}
