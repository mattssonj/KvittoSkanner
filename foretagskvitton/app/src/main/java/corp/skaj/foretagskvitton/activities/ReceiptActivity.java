package corp.skaj.foretagskvitton.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import corp.skaj.foretagskvitton.R;
import corp.skaj.foretagskvitton.controllers.ReceiptController;
import corp.skaj.foretagskvitton.view.IImage;
import corp.skaj.foretagskvitton.view.ImageFragment;
import corp.skaj.foretagskvitton.view.ReceiptFragment;

public class ReceiptActivity extends AbstractActivity implements IImage {
    private FragmentManager mFragmentManager;
    private boolean isShowingImage;
    private String mPurchaseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
        mFragmentManager = getSupportFragmentManager();
        mPurchaseId = getIntent().getStringExtra(MainActivity.ARCHIVE_KEY);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        isShowingImage = false;
        changeFragment(createArchiveFragment());
    }

    private ReceiptFragment createArchiveFragment() {
        ReceiptFragment af = ReceiptFragment.create(mPurchaseId);
        ReceiptController controller = new ReceiptController(getDataHandler(), mPurchaseId, af, this);
        af.setBinder(controller);
        return af;
    }

    @Override
    public void setImagePressed(Uri address) {
        String s = address.toString();
        ImageFragment fragment = ImageFragment.create(s);
        isShowingImage = true;
        changeFragment(fragment);
    }

    private void changeFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        mFragmentManager = getSupportFragmentManager();
        boolean fragmentPopped = mFragmentManager.popBackStackImmediate (backStateName, 0);

        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.replace(R.id.archive_fragment_container, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return goBack();
            default:
                return false;
        }
    }

    private boolean goBack() {
        if (isShowingImage) {
            isShowingImage = false;
            changeFragment(createArchiveFragment());
            return true;
        }
        finish();
        return true;
    }
}