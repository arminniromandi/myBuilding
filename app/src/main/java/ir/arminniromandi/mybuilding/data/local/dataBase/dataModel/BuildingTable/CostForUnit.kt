package ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler
@Entity(
    tableName = DBHandler.CostForUnit_TABLE,

    foreignKeys = [
        ForeignKey(
            entity = CostInMonth::class,
            parentColumns = ["CostInMonth_Id"],
            childColumns = ["C_MonthId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        ),

        ForeignKey(
            entity = BUser::class,
            parentColumns = ["U_Id"],
            childColumns = ["C_UnitId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ],

    indices = [
        Index(value = ["C_MonthId"]),
        Index(value = ["C_UnitId"]),

        /*
         * یک واحد در یک ماه فقط یک شارژ داشته باشد
         */
        Index(
            value = ["C_MonthId", "C_UnitId"],
            unique = true
        )
    ]
)
data class CostForUnit(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "C_Id")
    val C_id: Int = 0,

    /*
     * ماه مربوط به شارژ
     */
    @ColumnInfo(name = "C_MonthId")
    val C_MonthId: Int,

    /*
     * واحد مربوط به شارژ
     */
    @ColumnInfo(name = "C_UnitId")
    val C_UnitId: Int,

    /*
     * مبلغ شارژ
     */
    @ColumnInfo(name = "C_Cost")
    val C_Cost: Int,

    @ColumnInfo(name = "isPaid")
    val isPaid: Boolean = false
)
