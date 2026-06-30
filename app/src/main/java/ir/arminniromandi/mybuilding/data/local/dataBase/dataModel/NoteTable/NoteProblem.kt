package ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.NoteTable

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler

@Entity(tableName = DBHandler.Companion.Note_TABLE)
data class NoteProblem(

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "Note_id") var Note_id :Int = 0,
    @ColumnInfo(name = "Note" )var Note :String,
    @ColumnInfo(name = "Checked" )var Checked :Boolean

)