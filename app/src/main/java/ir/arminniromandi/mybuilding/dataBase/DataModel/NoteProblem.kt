package ir.arminniromandi.mybuilding.dataBase.DataModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.arminapp.myapp2.Room.DBHandler

@Entity(tableName = DBHandler.Note_TABLE)
data class NoteProblem(

@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Note_id") var Note_id :Int = 0,
@ColumnInfo(name = "Note" )var Note :String,
@ColumnInfo(name = "Checked" )var Checked :Boolean

)