package ir.arminniromandi.mybuilding.viewModel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IntroViewModel(
    context: Application
) : AndroidViewModel(context) {

    private val db = DBHandler.getDataBase(context)


    private val _name = MutableLiveData("")
    private val _count = MutableLiveData(0)

    val name: LiveData<String> = _name
    val count: LiveData<Int> = _count

    val isAdded = mutableStateOf(false)


    val scope = CoroutineScope(Dispatchers.IO)
    fun isUserAdded() {
        scope.launch() {
            val count = db.buildingDao().countOfBuilding()
            withContext(Dispatchers.Main) {
                isAdded.value = count != 0
            }

        }
    }

    fun getOldData() {

        scope.launch(Dispatchers.IO) {
            val userName = db.buildingDao().getBHostName()
            val count = db.buildingDao().getBUnitCount()

            withContext(Dispatchers.Main) {
                _name.value = userName
                _count.value = count
            }


        }

    }


}