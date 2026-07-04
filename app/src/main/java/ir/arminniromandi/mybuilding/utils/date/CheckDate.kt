package ir.arminniromandi.mybuilding.utils.date

import BuildingRepository
import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import ir.arminniromandi.mybuilding.data.local.dataBase.DBHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

//کلاسی که بررسی میکند ایا در این ماه شارژی موجود است یا نه
// useCase بشه بهتره
@Singleton
class CheckDate @Inject constructor(
    private val buildingRepository: BuildingRepository
) {
    suspend fun getAndCheck(date: Date): Boolean {
        return withContext(Dispatchers.IO) {
            val lastIndex = buildingRepository.getLastMonth()

            if (lastIndex != null) {
                lastIndex.CostInMounthAndYear != date.getDateForCalculate()
            } else {
                true
            }
        }
    }
}