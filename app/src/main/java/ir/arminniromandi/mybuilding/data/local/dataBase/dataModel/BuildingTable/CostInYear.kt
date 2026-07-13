package ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler

@Entity(
    tableName = DBHandler.CostInYear_TABLE,

    foreignKeys = [
        ForeignKey(
            entity = Building::class,
            parentColumns = ["B_Id"],
            childColumns = ["Y_BuildingId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ],

    indices = [
        Index(value = ["Y_BuildingId"]),

        /*
         * یک ساختمان نمی‌تواند دوبار سال 1405 داشته باشد
         */
        Index(
            value = ["Y_BuildingId", "Y_Year"],
            unique = true
        )
    ]
)
data class CostInYear(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "C_YearId")
    val C_YearId: Int = 0,

    /*
     * ساختمان مربوط به این سال
     * Y = year
     */
    @ColumnInfo(name = "Y_BuildingId")
    val Y_BuildingId: Int,

    /*
     * مثلاً 1405
     */
    @ColumnInfo(name = "Y_Year")
    val Y_Year: Int
)
