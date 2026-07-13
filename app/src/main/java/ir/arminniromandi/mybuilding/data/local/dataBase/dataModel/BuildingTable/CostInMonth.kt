package ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler


@Entity(
    tableName = DBHandler.CostInMounth_TABLE,

    foreignKeys = [
        ForeignKey(
            entity = CostInYear::class,
            parentColumns = ["C_YearId"],
            childColumns = ["M_YearId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ],

    indices = [
        Index(value = ["M_YearId"]),

        /*
         * در یک سال، ماه تکراری ثبت نشود
         */
        Index(
            value = ["M_YearId", "M_MonthNumber"],
            unique = true
        )
    ]
)
data class CostInMonth(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "CostInMonth_Id")
    val C_MonthId: Int = 0,

    /*
     * سال مربوط به این ماه
     */
    @ColumnInfo(name = "M_YearId")
    val M_YearId: Int,

    /*
     * عدد ماه از 1 تا 12
     */
    @ColumnInfo(name = "M_MonthNumber")
    val M_MonthNumber: Int,

    /*
     * فقط برای نمایش به کاربر
     * مثلاً فروردین 1405
     */
    @ColumnInfo(name = "CostInMonthAndYear")
    val CostInMounthAndYear: String,

    @ColumnInfo(name = "costWater")
    val costWater: Int,

    @ColumnInfo(name = "Cost")
    val cost: Int,

    /*
     * کل شارژ همان ماه
     */
    @ColumnInfo(name = "sumCost")
    val sumOfAll: Int
)