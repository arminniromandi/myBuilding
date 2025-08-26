package ir.arminniromandi.mybuilding.data.local.dataBase.dataModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler


@Entity(tableName = DBHandler.CostForUnit_TABLE)
data class CostForUnit(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "C_Id") var B_id :Int = 0,
    @ColumnInfo(name = "CostInMonthAndYear" )var C_MonthAndYear :String,
    @ColumnInfo(name = "C_Unit" )var C_Unit :Int,
    @ColumnInfo(name = "C_Cost" )var C_Cost :String,
    @ColumnInfo(name = "isPaid" )var isPaid :Boolean
)
