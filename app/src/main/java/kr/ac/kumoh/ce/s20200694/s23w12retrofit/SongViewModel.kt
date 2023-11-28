package kr.ac.kumoh.ce.s20200694.s23w12retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class SongViewModel : ViewModel() {
    companion object{
        const val SERVER_URL = "https://port-0-s23w10backend-3yl7k2blonrwyz9.sel5.cloudtype.app/"
    }

    private val _songList = MutableLiveData<List<Song>>()
    val songList: LiveData<List<Song>>
        get() = _songList

    private val songApi: SongApi

    init{
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        songApi = retrofit.create(SongApi::class.java)
        fetchData()
    }
    private fun fetchData(){
        viewModelScope.launch{
            try{
                val response = songApi.getSongs()
                _songList.value = response
            }catch (e: Exception){
                Log.e("fetchData()", e.toString())
            }
        }
    }
}