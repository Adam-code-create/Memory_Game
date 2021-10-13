package uz.gita.memorygame.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import uz.gita.memorygame.data.CardData
import uz.gita.memorygame.domain.AppRepository
import javax.inject.Inject

@HiltViewModel
class EasyViewModel @Inject constructor(private val repository: AppRepository) : ViewModel () {
  //  private val repository = AppRepository.getRepository()

    private val _imageLiveData = MutableLiveData<ArrayList<CardData>>()
    val imageLiveData : MutableLiveData<ArrayList<CardData>> get() = _imageLiveData


    fun loadImages( count : Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.loadImages(count).collect {
                _imageLiveData.postValue(it)
            }
        }
    }


}