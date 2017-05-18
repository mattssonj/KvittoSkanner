package corp.skaj.foretagskvitton.activities;

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
import corp.skaj.foretagskvitton.model.User;
import corp.skaj.foretagskvitton.view.ArchiveAdapter;
import corp.skaj.foretagskvitton.view.CompanyAdapter;
import corp.skaj.foretagskvitton.view.IController;
import corp.skaj.foretagskvitton.view.ListFragment;

public class MainActivity extends AbstractActivity implements IMain {

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

    }

    @Override
    public void goToCompany(String s) {

    }

    @Override
    public void goToPurchase(String s) {

    }

    @Override
    public void goToSupplier(String s) {

    }
}
