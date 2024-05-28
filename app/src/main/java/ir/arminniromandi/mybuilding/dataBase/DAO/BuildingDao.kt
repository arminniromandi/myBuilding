package ir.arminapp.myapp2.Room.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import ir.arminapp.myapp2.Room.DataModel.Building

@Dao
interface BuildingDao {


    @Insert
    fun insertData(building: Building)

    @Update
    fun editNotes(building: Building):Int

//
//    @Query("DELETE FROM ${DBHandler.NOTES_TABLE} WHERE ${DBHandler.NOTES_ID} = :id")
//    fun deleteNotes(id: Int)
//
//    @Query("SELECT * FROM ${DBHandler.NOTES_TABLE} WHERE ${DBHandler.NOTES_ID} = :id")
//    fun getNotesById(id: Int):Flow<List<Building>>
//
//    @Query("SELECT ${DBHandler.NOTES_ID} ,${DBHandler.NOTES_TITLE} FROM ${DBHandler.NOTES_TABLE} WHERE ${DBHandler.NOTES_DELETE_STATE} = :deleteState ")
//    fun getNotesForRecyclerView(deleteState:String):Flow<List<RecyclerNotesData>>
//
//    @Query("UPDATE ${DBHandler.NOTES_TABLE} SET ${DBHandler.NOTES_DELETE_STATE} = :state WHERE ${DBHandler.NOTES_ID} = :id ")
//    fun updateNotes(id :Int , state :String):Int
}