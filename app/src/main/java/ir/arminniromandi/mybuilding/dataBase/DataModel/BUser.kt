package ir.arminniromandi.mybuilding.dataBase.DataModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.arminapp.myapp2.Room.DBHandler

@Entity(tableName = DBHandler.User_TABLE)
data class BUser(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "U_Id") var B_id :Int = 0,
    @ColumnInfo(name = "U_Unit" )var B_Unit :Int

)
