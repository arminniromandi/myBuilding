package ir.arminniromandi.mybuilding.data.repository

import ir.arminniromandi.mybuilding.data.local.dataBase.dao.NoteDao
import ir.arminniromandi.mybuilding.data.local.dataBase.dataModel.NoteTable.Note
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NoteRepository @Inject constructor(
    private val dao : NoteDao
) {
    suspend fun insertNote(note: Note){
        dao.insertNote(note)
    }

     fun getNotes() =
        dao.getNotes()


    suspend fun deleteAllNotes() =
        dao.deleteAllNotes()


    suspend fun updateNote(note: Note){
        dao.updateNotes(note)
    }


}