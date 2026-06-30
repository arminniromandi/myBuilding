package ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler


@Entity(tableName = DBHandler.User_TABLE)
data class BUser(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "U_Id") var U_id :Int = 0,
    @ColumnInfo(name = "U_Unit" )var U_Unit :Int

)
