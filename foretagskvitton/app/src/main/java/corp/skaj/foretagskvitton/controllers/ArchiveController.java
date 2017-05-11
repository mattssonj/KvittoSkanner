package corp.skaj.foretagskvitton.controllers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import corp.skaj.foretagskvitton.activities.CompanyActivity;
import corp.skaj.foretagskvitton.model.Company;
import corp.skaj.foretagskvitton.model.Employee;
import corp.skaj.foretagskvitton.model.Purchase;
import corp.skaj.foretagskvitton.model.Receipt;
import corp.skaj.foretagskvitton.model.DataHolder;

public class ArchiveController {
    private Context mContext;
    private List<Receipt> receipts;
    private List<Company> companies;
    private DataHolder dataHolder = (DataHolder) mContext.getApplicationContext();
    private RecyclerView recyclerView;

    public static final String COMPANY_KEY = "ArchiveKey";

    private void getAllReceipts() {
        companies = dataHolder.getUser().getCompanies();
        for (int i = 0; i < companies.size(); i++) {
            List<Employee> employees = companies.get(i).getEmployees();
            for (int j = 0; j < employees.size(); i++) {
                List<Purchase> purchases = employees.get(j).getPurchases();
                for (int k = 0; k < purchases.size(); k++) {
                    receipts.add(purchases.get(k).getReceipt());
                }
            }
        }
    }
}