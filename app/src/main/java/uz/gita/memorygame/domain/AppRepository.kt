package uz.gita.memorygame.domain


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.gita.memorygame.R
import uz.gita.memorygame.data.CardData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton

class AppRepository @Inject constructor() {

    companion object{
        private lateinit var instance : AppRepository

        fun getRepository () : AppRepository{
            if (!::instance.isInitialized){
                instance = AppRepository()
            }
            return instance
        }
    }

    private val data = ArrayList<CardData>()

    init {
        loadData()
    }

    private fun loadData(){
        data.add(CardData(R.drawable.image_1, 1))
        data.add(CardData(R.drawable.image_2, 2))
        data.add(CardData(R.drawable.image_3, 3))
        data.add(CardData(R.drawable.image_4, 4))
        data.add(CardData(R.drawable.image_5, 5))
        data.add(CardData(R.drawable.image_6, 6))
        data.add(CardData(R.drawable.image_7, 7))
        data.add(CardData(R.drawable.image_8, 8))
        data.add(CardData(R.drawable.image_9, 9))
        data.add(CardData(R.drawable.image_10, 10))
        data.add(CardData(R.drawable.image_11, 11))
        data.add(CardData(R.drawable.image_12, 12))
        data.add(CardData(R.drawable.image_13, 13))
        data.add(CardData(R.drawable.image_14, 14))
        data.add(CardData(R.drawable.image_15, 15))
        data.add(CardData(R.drawable.image_16, 16))
        data.add(CardData(R.drawable.image_17, 17))
        data.add(CardData(R.drawable.image_18, 18))
        data.add(CardData(R.drawable.image_19, 19))
        data.add(CardData(R.drawable.image_20, 20))
        data.add(CardData(R.drawable.image_21, 21))
        data.add(CardData(R.drawable.image_22, 22))
        data.add(CardData(R.drawable.image_23, 23))
        data.add(CardData(R.drawable.image_24, 24))
        data.add(CardData(R.drawable.image_25, 25))
        data.add(CardData(R.drawable.image_26, 26))
        data.add(CardData(R.drawable.image_27, 27))
        data.add(CardData(R.drawable.image_28, 28))
        data.add(CardData(R.drawable.image_28, 29))
        data.add(CardData(R.drawable.image_30, 30))
        data.add(CardData(R.drawable.image_31, 31))
        data.add(CardData(R.drawable.image_32, 32))
        data.add(CardData(R.drawable.image_33, 33))
        data.add(CardData(R.drawable.image_34, 34))
        data.add(CardData(R.drawable.image_35, 35))
        data.add(CardData(R.drawable.image_36, 36))
        data.add(CardData(R.drawable.image_37, 37))
        data.add(CardData(R.drawable.image_38, 38))
        data.add(CardData(R.drawable.image_39, 39))
        data.add(CardData(R.drawable.image_40, 40))
        data.add(CardData(R.drawable.image_41, 41))
        data.add(CardData(R.drawable.image_42, 42))
        data.add(CardData(R.drawable.image_43, 43))
        data.add(CardData(R.drawable.image_44, 44))
        data.add(CardData(R.drawable.image_45, 45))
        data.add(CardData(R.drawable.image_46, 46))
        data.add(CardData(R.drawable.image_47, 47))
        data.add(CardData(R.drawable.image_48, 48))
        data.add(CardData(R.drawable.image_49, 49))
        data.add(CardData(R.drawable.image_50, 50))
        data.add(CardData(R.drawable.image_51, 51))
        data.add(CardData(R.drawable.image_52, 52))
        data.add(CardData(R.drawable.image_53, 53))
    }

   fun loadImages(count :Int) : Flow<ArrayList<CardData>> = flow{
       data.shuffle()
       val resultData = ArrayList<CardData>()
       resultData.addAll(data.subList(0, count/2))
       resultData.addAll(data.subList(0, count/2))
       resultData.shuffle()
       emit(resultData)
    }



}