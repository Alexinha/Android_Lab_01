package algonquin.cst2335.chan0528.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

//    public String editString;

    public MutableLiveData<String> editString = new MutableLiveData<>();
    public MutableLiveData<Boolean> editedCheckBox = new MutableLiveData<>();
    public MutableLiveData<Boolean> editedRadioButton = new MutableLiveData<>();
    public MutableLiveData<Boolean> editedSwitch = new MutableLiveData<>();

}
