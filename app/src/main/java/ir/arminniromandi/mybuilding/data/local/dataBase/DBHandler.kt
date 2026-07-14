package ir.arminniromandi.mybuilding.data.local.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.arminniromandi.mybuilding.data.local.dataBase.dao.BuildingDao
import ir.arminniromandi.mybuilding.data.local.dataBase.dao.NoteDao
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.BUser
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.Building
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostForUnit
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostInMonth
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable.CostInYear
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.NoteTable.Note

@Database(
    entities =
    [
        Building::class,
        Note::class,
        BUser::class,
        CostInYear::class,
        CostForUnit::class,
        CostInMonth::class
    ],
    version = DBHandler.DATABASE_VERSION
)
abstract class DBHandler : RoomDatabase() {

    abstract fun buildingDao(): BuildingDao
    abstract fun NoteDao(): NoteDao


    companion object {

        const val DATABASE_NAME = "Building"
        const val DATABASE_VERSION = 2
        const val Building_TABLE = "Building"
        const val User_TABLE = "User_TABLE"
        const val CostForUnit_TABLE = "CostForUnit_TABLE"
        const val CostInMounth_TABLE = "CostInMounth_TABLE"
        const val CostInYear_TABLE = "CostInYear_TABLE"

        const val Note_TABLE = "Note_Table"

        const val B_Unit = "B_Unit"
        const val B_HostName = "B_HostName"

        const val U_Unit = "U_Unit"
        const val U_Id = "U_Id"

    }
}