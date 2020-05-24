package com.safa.laundryserviceapp.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.safa.laundryserviceapp.model.APICategoryModel
import com.safa.laundryserviceapp.model.Category
import com.safa.laundryserviceapp.service.CategoryDatabase
import com.safa.laundryserviceapp.service.LaundryAPIService
import com.safa.laundryserviceapp.utility.CustomSharedPreferences
import com.safa.laundryserviceapp.utility.VALID_MESSAGE
import com.safa.laundryserviceapp.utility.VALID_STATUS
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class MainActivityViewModel(application: Application): BaseViewModel(application) {
    val categories = MutableLiveData<List<Category>>()
    val loading = MutableLiveData<Boolean>()
    val language = MutableLiveData<Int>()
    val  disposable = CompositeDisposable()
    val service = LaundryAPIService()
    val customSharedPreferences = CustomSharedPreferences(getApplication())
    val expectedTime = 10*60.1000*1000*1000L

    fun refreshData(){
        val time = customSharedPreferences.returnTime()
        println("time: "+ time)
        if (time != null && time != 0L && System.nanoTime() - time< expectedTime){
            refreshDataFromDB()
        }else{
            refreshDataFromApi()
        }
    }

    fun refreshDataFromApi(){
        loading.value = true
        disposable.add(
            service.getCategoryListFromApi()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<APICategoryModel>(){
                    override fun onSuccess(t: APICategoryModel) {
                        if(t.status == VALID_STATUS && t.message.equals(VALID_MESSAGE)) {
                            t.data?.let {
                                insertDataIntoDataBase(it)
                            }
                        }else{
                            loading.value = false
                        }

                    }

                    override fun onError(e: Throwable) {
                       println("api error" + e.localizedMessage )
                        e.printStackTrace()
                    }

                })
        )

    }



    fun refreshDataFromDB(){
        loading.value = true
        launch {
            val categories = CategoryDatabase(getApplication()).categoryDao().getAllCategory()
            showCategories(categories)
        }
    }

    fun insertDataIntoDataBase(t: List<Category>){
        launch {
            val dao = CategoryDatabase(getApplication()).categoryDao()
            dao.deleteAllCatogries()
            val listLong = dao.insertCategoryAll(*t.toTypedArray())
            var i = 0
            while (i<t.size){
                t[i].uuid = listLong[i].toInt()
                i = i +1
            }

            showCategories(t)

        }
        customSharedPreferences.enterTime(System.nanoTime())

    }

    fun showCategories(t:List<Category>){
        categories.value = t
        loading.value = false
    }


    fun changeLanguage(languageId: Int){
        val selectedLanguage = customSharedPreferences.enterLanguage(languageId)
        language.value = languageId
    }





}