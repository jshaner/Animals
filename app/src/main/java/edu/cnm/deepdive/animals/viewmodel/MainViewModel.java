package edu.cnm.deepdive.animals.viewmodel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.animals.BuildConfig;
import edu.cnm.deepdive.animals.model.Animal;
import edu.cnm.deepdive.animals.service.AnimalService;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends AndroidViewModel {

  private MutableLiveData<List<Animal>> animals;
  private MutableLiveData<Throwable> throwable;
  private AnimalService animalService;

  public MainViewModel(@NonNull Application application) {
    super(application);
    animalService = AnimalService.getInstance();
    animals = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    loadAnimals();
  }

  public LiveData<List<Animal>> getAnimals() {
    return animals;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  private void loadAnimals() {

    animalService.getAnimals(BuildConfig.CLIENT_KEY)
        .subscribeOn(Schedulers.io())
        .subscribe(animals -> this.animals.postValue(animals),
            (throwable) -> this.throwable.postValue(throwable));
  }

}
