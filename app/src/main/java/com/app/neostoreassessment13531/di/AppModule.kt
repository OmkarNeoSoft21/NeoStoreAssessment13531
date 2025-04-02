package com.app.neostoreassessment13531.di

import com.app.neostoreassessment13531.neostore.data.local.repository.LocalDataSourceUser
import com.app.neostoreassessment13531.neostore.data.repository.RepositoryUserImpl
import com.app.neostoreassessment13531.neostore.domain.repo.RepositoryUser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepositoryUser(localDataSourceUser: LocalDataSourceUser):RepositoryUser{
        return RepositoryUserImpl(localDataSourceUser)
    }

}