package ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler

@Entity(tableName = DBHandler.Building_TABLE)
data class Building(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "B_Id") var B_id: Int = 0,
    @ColumnInfo(name = "B_HostName" ) var B_HostName: String,
    @ColumnInfo(name = "B_Unit" ) var B_Unit: Int

)
