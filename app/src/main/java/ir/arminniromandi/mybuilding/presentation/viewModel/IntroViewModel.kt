package ir.arminniromandi.mybuilding.presentation.viewModel


import BuildingRepository
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class IntroViewModel @Inject constructor(
    private val buildingRepository : BuildingRepository
) : ViewModel() {
//todo: should be thinck about that


    private val _name = MutableLiveData("")
    private val _count = MutableLiveData(0)

    val name: LiveData<String> = _name
    val count: LiveData<Int> = _count

    //todo: better become shared flow
    val isAdded = mutableStateOf(false)


    fun isUserAdded() {
        viewModelScope.launch(Dispatchers.IO) {
            val count = buildingRepository.countOfBuilding()
            withContext(Dispatchers.Main) {
                isAdded.value = count != 0
            }

        }
    }

    fun getOldData() {

        viewModelScope.launch(Dispatchers.IO) {

            val userName =   buildingRepository.getBHostName()
            val count = buildingRepository.getBUnitCount()

            withContext(Dispatchers.Main) {
                _name.value = userName
                _count.value = count
            }


        }

    }


}