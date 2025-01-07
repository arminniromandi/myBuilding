package ir.arminniromandi.mybuilding.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.arminniromandi.mybuilding.dataBase.dao.BuildingDao
import ir.arminniromandi.mybuilding.dataBase.dao.NoteDao
import ir.arminniromandi.mybuilding.dataBase.dataModel.BUser
import ir.arminniromandi.mybuilding.dataBase.dataModel.Building
import ir.arminniromandi.mybuilding.dataBase.dataModel.CostForUnit
import ir.arminniromandi.mybuilding.dataBase.dataModel.CostInMonth
import ir.arminniromandi.mybuilding.dataBase.dataModel.NoteProblem

@Database(
    entities =
    [
        Building::class,
        NoteProblem::class,
        BUser::class,
        CostForUnit::class,
        CostInMonth::class
    ],
    version = DBHandler.DATABASE_VERSION
)
abstract class DBHandler : RoomDatabase() {

    abstract fun buildingDao(): BuildingDao
    abstract fun NoteDao(): NoteDao


    companion object {

        private const val DATABASE_NAME = "Building"
        const val DATABASE_VERSION = 1
        const val Building_TABLE = "Building"
        const val User_TABLE = "User_TABLE"
        const val CostForUnit_TABLE = "CostForUnit_TABLE"
        const val CostInMounth_TABLE = "CostInMounth_TABLE"
        const val Note_TABLE = "Note_Table"

        const val B_Unit = "B_Unit"
        const val B_HostName = "B_HostName"

        const val U_Unit = "U_Unit"
        const val U_Id = "U_Id"


        private var INSTANTCE: DBHandler? = null

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