package corp.skaj.foretagskvitton.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.List;

import corp.skaj.foretagskvitton.R;
import corp.skaj.foretagskvitton.controllers.IMain;
import corp.skaj.foretagskvitton.controllers.MainController;
import corp.skaj.foretagskvitton.model.Company;
import corp.skaj.foretagskvitton.model.Supplier;
import corp.skaj.foretagskvitton.model.User;
import corp.skaj.foretagskvitton.view.ArchiveAdapter;
import corp.skaj.foretagskvitton.view.CompanyAdapter;
import corp.skaj.foretagskvitton.view.IController;
import corp.skaj.foretagskvitton.view.ListFragment;
import corp.skaj.foretagskvitton.view.SupplierAdapter;

public class MainActivity extends AbstractActivity implements IMain {

    public static final String COMPANY_KEY = "key_for_company_ofc";
    public static final String ARCHIVE_KEY = "key_for_archive_ofc";

    private IController mAdapterController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataHandler().initDefaultUser();
        initBottomBar();

        mAdapterController = new MainController(this);

        archiveList();
    }

    private void initBottomBar () {
        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (bottomBar.getCurrentTabId() != tabId) {
                    switch (tabId) {
                        case R.id.action_business:
                            companyList();
                            return;
                        case R.id.action_archive:
                            archiveList();
                            return;
                        case R.id.action_user:
                            //TODO
                            return;
                        default:
                            return;
                    }
                }
            }
        });
    }

    private void companyList () {
        List<Company> companies = getDataHandler().readData(User.class.getName(), User.class).getCompanies();
        ListFragment listFragment = ListFragment.create(new CompanyAdapter(companies, mAdapterController));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, listFragment).commit();

    }

    private void archiveList () {
        ListFragment listFragment = ListFragment.create(new ArchiveAdapter(getDataHandler(), mAdapterController));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, listFragment).commit();

    }

    private void supplierList () {
        ListFragment listFragment = ListFragment.create(new SupplierAdapter(getDataHandler()));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, listFragment).commit();
    }

    @Override
    public void goToCompany(String s) {
        Intent intent = new Intent(this, CompanyActivity.class);
        intent.putExtra(COMPANY_KEY, s);
        startActivity(intent);

    }

    @Override
    public void goToPurchase(String s) {
        Intent intent = new Intent(this, ArchiveReceiptActivity.class);
        intent.putExtra(ARCHIVE_KEY, s);
        startActivity(intent);
    }

    @Override
    public void goToSupplier(String s) {

    }
}