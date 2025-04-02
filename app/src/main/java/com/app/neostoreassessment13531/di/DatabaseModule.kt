package com.app.neostoreassessment13531.di

import android.content.Context
import androidx.room.Room
import com.app.neostoreassessment13531.neostore.data.local.AppDatabase
import com.app.neostoreassessment13531.neostore.data.local.dao.DaoUser
import com.app.neostoreassessment13531.neostore.data.local.repository.LocalDataSourceUser
import com.app.neostoreassessment13531.neostore.data.local.repository.LocalDataSourceUserImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            AppDatabase::class.java,
            "neostore.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideDaoUser(db: AppDatabase): DaoUser {
        return db.getDao()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(daoUser: DaoUser):LocalDataSourceUser{
        return LocalDataSourceUserImpl(daoUser)
    }

}