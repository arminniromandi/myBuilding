package ir.arminniromandi.mybuilding.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler
import ir.arminniromandi.mybuilding.data.local.dataBase.dao.BuildingDao
import ir.arminniromandi.mybuilding.data.local.dataBase.dao.NoteDao
import javax.inject.Singleton




/*
* room database module
* priovide database and dao for Building and note table
*
* */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): DBHandler {
        return Room.databaseBuilder(
            context,
            DBHandler::class.java,
            DBHandler.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideBuildingDao(database: DBHandler): BuildingDao {
        return database.buildingDao()
    }

    @Provides
    @Singleton
    fun provideNotegDao(database: DBHandler): NoteDao {
        return database.NoteDao()
    }



}