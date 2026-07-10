package ir.arminniromandi.mybuilding.data.local.dataBase.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.NoteTable.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote(Note: Note)

    @Query("SELECT * FROM ${DBHandler.Note_TABLE}")
    fun getNotes(): Flow<List<Note>>


    @Query("DELETE FROM ${DBHandler.Note_TABLE}")
    suspend fun deleteAllNotes()

    @Update
    suspend fun updateNotes(note: Note)
}