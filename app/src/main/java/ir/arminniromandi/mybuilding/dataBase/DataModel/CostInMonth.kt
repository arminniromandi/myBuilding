package ir.arminniromandi.mybuilding.dataBase.DataModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.arminapp.myapp2.Room.DBHandler


@Entity(tableName = DBHandler.CostInMounth_TABLE)

data class CostInMonth(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "CostInMonth_Id") var C_MonthId:Int = 0,
    @ColumnInfo(name = "CostInMonthAndYear") var CostInMounthAndYear :String,
    @ColumnInfo(name = "costWater") var costWater  : Int,
    @ColumnInfo(name = "sumCost") var sum  : Int


)
