package corp.skaj.foretagskvitton.services;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import corp.skaj.foretagskvitton.model.Company;
import corp.skaj.foretagskvitton.model.Employee;
import corp.skaj.foretagskvitton.model.IDataHandler;
import corp.skaj.foretagskvitton.model.PurchaseList;
import corp.skaj.foretagskvitton.model.User;

/**
 * FileHandler class, allowing data to be saved locally.
 * Holds an instance of user, allowing model data to be saved.
 */
public class DataHandler extends Application implements IDataHandler {
    private User mUser;

    /**
     * This method is to write data to shared preferences.
     * <h1>Dont use this to write user</h1> instead user saveUser()
     * @param key
     * @param writeT
     * @param <T>
     */
    @Override
    public <T> void writeData(String key, T writeT) {
        getEditor().putString(key, toJson(writeT)).apply();
    }

    /**
     * This method is to read data saved in shared preference.
     * <h1>Dont use this to get user from other classes</h1> instead use getUser()
     * @param key
     * @param classOfT
     * @param <T>
     * @return
     */
    @Override
    public <T> T readData(String key, Class<T> classOfT) {
        return new Gson().fromJson(getString(key), classOfT);
    }

    private <T> String toJson(T translateT) {
        return new Gson().toJson(translateT);
    }

    private SharedPreferences.Editor getEditor() {
        return PreferenceManager.getDefaultSharedPreferences(this).edit();
    }

    private String getString(String key) {
        return PreferenceManager.getDefaultSharedPreferences(this).getString(key, "");
    }

    @Override
    public void clearData() {
        getEditor().clear().apply();
    }

    @Override
    public void removeData(String key) {
        getEditor().remove(key).apply();
    }

    @Override
    public boolean initDefaultUser() {
        if (getUser() == null) {
            User user = new User("Batman");
            Company company = new Company("SKAJ Corp.");
            company.addEmployee(new Employee(user.getName()));
            user.addCompany(company);
            mUser = user;
            saveUser();
            return true;
        }
        return false;
    }

    @Override
    public PurchaseList getPurchases() {
        PurchaseList purchases = new PurchaseList(getUser());
        for (Company c : getUser().getCompanies()) {
            for (Employee e : c.getEmployees()) {
                purchases.addAll(e.getPurchases());
            }
        }
        return purchases;
    }

    @Override
    public User getUser() {
        if (mUser == null) {
            mUser = readData(User.class.getName(), User.class);
        }
        return mUser;
    }

    @Override
    public boolean saveUser() {
        if (mUser != null) {
            System.out.println("Saving user...");
            writeData(User.class.getName(), mUser);
            System.out.println("User saved");
            return true;
        }
        return false;
    }
}