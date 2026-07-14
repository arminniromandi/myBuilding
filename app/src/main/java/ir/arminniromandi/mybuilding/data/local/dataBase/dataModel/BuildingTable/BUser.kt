package ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.BuildingTable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler


/*جدول مربوط به واحد های ساختمان
* که ارتباط ها در پایین مشخص است
* */


@Entity(
    tableName = DBHandler.User_TABLE,

    foreignKeys = [
        ForeignKey(
            entity = Building::class,
            parentColumns = ["B_Id"],
            childColumns = ["U_BuildingId"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ],

    indices = [
        Index(value = ["U_BuildingId"]),

        /*
         * در یک ساختمان، شماره واحد تکراری نباشد
         */
        Index(
            value = ["U_BuildingId", "U_Number"],
            unique = true
        )
    ]
)
data class BUser(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "U_Id")
    val U_id: Int = 0,

    /*
     * ساختمان مربوط به این واحد
     * چون در این پروژه فقط یک ساختمان مطرحه
     */
    @ColumnInfo(name = "U_BuildingId")
    val U_BuildingId: Int = 1,

    /*
     * شماره واقعی واحد، مثلاًواحد1
     */
    @ColumnInfo(name = "U_Number")
    val U_Number: Int,

    /*
     * تعداد ساکنان واحد،
     * مثلا 3 نفز
     */
    @ColumnInfo(name = "U_Unit")
    val U_Unit: Int
)