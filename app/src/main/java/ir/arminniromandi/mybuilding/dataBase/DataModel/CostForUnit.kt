package ir.arminniromandi.mybuilding.dataBase.DataModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.arminapp.myapp2.Room.DBHandler


@Entity(tableName = DBHandler.CostForUnit_TABLE)
data class CostForUnit(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "C_Id") var B_id :Int = 0,
    @ColumnInfo(name = "CostInMonthAndYear" )var C_MonthAndYear :String,
    @ColumnInfo(name = "C_Cost" )var C_Cost :String,
    @ColumnInfo(name = "isPaid" )var isPaid :Boolean
)
