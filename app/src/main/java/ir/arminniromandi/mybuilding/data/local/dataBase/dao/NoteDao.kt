package ir.arminniromandi.mybuilding.data.local.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.NoteProblem
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    fun insertNote(Note: NoteProblem)

    @Query("SELECT * FROM ${DBHandler.Note_TABLE}")
    fun getNotes(): Flow<List<NoteProblem>>


    @Query("DELETE FROM ${DBHandler.Note_TABLE}")
    fun deleteAllNotes()

    @Update
    fun updateNotes(noteProblem: NoteProblem)
}