package com.wesleypanaino.moviemadness.di

import com.wesleypanaino.moviemadness.common.Constants
import com.wesleypanaino.moviemadness.data.remote.TheMovieDataBaseApi
import com.wesleypanaino.moviemadness.data.repository.MovieRepositoryImpl
import com.wesleypanaino.moviemadness.domain.repository.MovieRepository
import com.wesleypanaino.moviemadness.domain.use_case.get_movie.GetMovieDetailUseCase
import com.wesleypanaino.moviemadness.domain.use_case.get_movies.GetMovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val interceptor = Interceptor { chain ->
        //todo store API key securely
        val API_KEY="YOUR_API_KEY_HERE"
        val originalRequest: Request = chain.request()

        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $API_KEY") // Use "Bearer" for OAuth tokens
            .build()

        chain.proceed(newRequest)
    }
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideTheMovieDataBaseApi(client: OkHttpClient): TheMovieDataBaseApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TheMovieDataBaseApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieRepository(api: TheMovieDataBaseApi): MovieRepository {
        return MovieRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideGetMovieUseCase(repo: MovieRepository): GetMovieUseCase {
        return GetMovieUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetMovieDetailUseCase(repo: MovieRepository): GetMovieDetailUseCase {
        return GetMovieDetailUseCase(repo)
    }
}