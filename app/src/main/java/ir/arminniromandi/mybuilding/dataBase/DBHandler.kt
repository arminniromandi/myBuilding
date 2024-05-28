package ir.arminapp.myapp2.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.arminapp.myapp2.Room.DAO.BuildingDao
import ir.arminapp.myapp2.Room.DataModel.Building

@Database(
    entities = [Building::class],
    version = DBHandler.DATABASE_VERSION
)
abstract class DBHandler : RoomDatabase() {

    abstract fun buildingDao(): BuildingDao

    companion object {

        private const val DATABASE_NAME = "Building"
        const val DATABASE_VERSION = 1
        const val Building_TABLE = "Building"


        private var INSTANTCE : DBHandler? = null

        fun getDataBase(context: Context): DBHandler {
            if (INSTANTCE == null) {
                INSTANTCE = Room.databaseBuilder(
                    context,
                    DBHandler::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration()
                    .build()
            }

            return INSTANTCE!!
        }
    }
}