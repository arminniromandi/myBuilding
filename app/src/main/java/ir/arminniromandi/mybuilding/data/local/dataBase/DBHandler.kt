package ir.arminniromandi.mybuilding.data.local.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.arminniromandi.mybuilding.data.local.dataBase.dao.BuildingDao
import ir.arminniromandi.mybuilding.data.local.dataBase.dao.NoteDao
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BUser
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.Building
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.CostForUnit
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.CostInMonth
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.NoteProblem

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


            @Volatile
            private var INSTANCE: DBHandler? = null

            fun getDataBase(context: Context): DBHandler {
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext, // 👈 مهم
                        DBHandler::class.java,
                        "Building"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    instance
                }
            }
    }
}