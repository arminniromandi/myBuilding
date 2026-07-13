package ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler

/*
* جدول اطلاعات کلی ساختمان
* شمال داده های
* نام مدیز
* تعداد تمامی واحد ها
* */

@Entity(tableName = DBHandler.Building_TABLE)
data class Building(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "B_Id")
    val B_id: Int = 0,

    @ColumnInfo(name = "B_HostName")
    val B_HostName: String,

    /*
     * تعداد کل واحدهای ساختمان
     */
    @ColumnInfo(name = "B_Unit")
    val B_Unit: Int
)