package corp.skaj.foretagskvitton.activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;

import corp.skaj.foretagskvitton.controllers.DataHolder;
import corp.skaj.foretagskvitton.model.User;

public abstract class AbstractActivity extends AppCompatActivity {

    public void writeData() {
        SharedPreferences sharedPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPref.edit();
        Gson gson = new Gson();
        DataHolder dataHolder = (DataHolder)getApplicationContext();
        String saveData = gson.toJson(dataHolder.getUser());
        prefsEditor.putString(User.class.getName().toString(), saveData);
        prefsEditor.apply();
    }
}
