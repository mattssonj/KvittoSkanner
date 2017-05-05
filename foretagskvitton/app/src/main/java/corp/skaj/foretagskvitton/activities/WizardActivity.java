
/*
 * Copyright 2012 Roman Nurik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package corp.skaj.foretagskvitton.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.tech.freak.wizardpager.model.AbstractWizardModel;
import com.tech.freak.wizardpager.model.ModelCallbacks;
import com.tech.freak.wizardpager.model.Page;
import com.tech.freak.wizardpager.ui.PageFragmentCallbacks;
import com.tech.freak.wizardpager.ui.ReviewFragment;
import com.tech.freak.wizardpager.ui.StepPagerStrip;

import java.util.ArrayList;
import java.util.List;

import corp.skaj.foretagskvitton.R;
import corp.skaj.foretagskvitton.controllers.IWizardActivity;
import corp.skaj.foretagskvitton.controllers.IWizardController;
import corp.skaj.foretagskvitton.controllers.WizardController;
import corp.skaj.foretagskvitton.view.MyPagerAdapter;

public class WizardActivity extends AppCompatActivity implements
        PageFragmentCallbacks, ReviewFragment.Callbacks, ModelCallbacks, IWizardActivity {

    private IWizardController wizardController;
    private AbstractWizardModel mWizardModel;
    private MyPagerAdapter mPagerAdapter;
    private ViewPager mPager;
    private StepPagerStrip mStepPagerStrip;
    private Button mNextButton;
    private Button mPrevButton;
    private List<String> strings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wizard);

        // Getting list from InitWizardActivity
        List<String> strings = getIntent()
                .getExtras()
                .getStringArrayList(InitWizardActivity.KEY_FOR_WIZARD_CONTROLLER);

        this.strings = strings;

        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }

        // Set instances
        mNextButton = (Button) findViewById(R.id.wizardNextButton);
        mPrevButton = (Button) findViewById(R.id.wizardBackButton);
        mPager = (ViewPager) findViewById(R.id.pager);
        mStepPagerStrip = (StepPagerStrip) findViewById(R.id.wizard_strip);

        WizardController wizardController = new WizardController(this, this);
        this.mWizardModel = wizardController.getWizardModel();

        mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), mWizardModel.getCurrentPageSequence());

        //TODO Figure out what this does.
        // Set listener to pagerstrip
        mStepPagerStrip.setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
            @Override
            public void onPageStripSelected(int position) {
                position = Math.min(mPagerAdapter.getCount() - 1, position);
                if (mPager.getCurrentItem() != position) {
                    mPager.setCurrentItem(position);
                }
            }
        });

        // Setting listeners to instances
        wizardController.initNextButton(mNextButton, mPager, mPagerAdapter, getSupportFragmentManager());
        wizardController.initBackButton(mPrevButton, mPager);
        wizardController.initViewPagerListener(mPager, mStepPagerStrip);
        wizardController.initPagerStrip(mStepPagerStrip, mPagerAdapter, mPager);

        // Set PagerAdapter
        mPager.setAdapter(mPagerAdapter);

        // Set the normal actionbar to custom toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.wizard_action_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
        actionBar.setDisplayHomeAsUpEnabled(true);

        // Register the listener
        mWizardModel.registerListener(this);
        this.wizardController = wizardController;

        onPageTreeChanged();
        updateBottomBar();
    }

    @Override
    public void onPageTreeChanged() {


        //TODO Check if this works.


        //mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        recalculateCutOffPage();
        int size = mWizardModel.getCurrentPageSequence().size() + 1;
        mStepPagerStrip.setPageCount(size); // + 1 = review, step

        mPagerAdapter.notifyDataSetChanged();
        updateBottomBar();
    }

    public void updateBottomBar() {
        mPrevButton.setVisibility(View.VISIBLE);

        int position = mPager.getCurrentItem();
        if (position == mWizardModel.getCurrentPageSequence().size()) {
            mNextButton.setText(R.string.wizard_complete);
        } else if (position <= 0) {
            mPrevButton.setVisibility(View.GONE);
            // TODO set next button as bigger if possible
        } else {
            mNextButton.setText(R.string.nextButtonText);
        }

        // Uncomment this and remove first mPrevButton and the else if, if its not possible or dont want
        // to have a bigger next button on the first page
        //mPrevButton.setVisibility(mPager.getCurrentItem() <= 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWizardModel.unregisterListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("model", mWizardModel.save());
    }

    @Override
    public AbstractWizardModel onGetModel() {
        return mWizardModel;
    }

    @Override
    public void onEditScreenAfterReview(String key) {

        //TODO Check if this works

        List<Page> currentPageSequenceList = mWizardModel.getCurrentPageSequence();
        int size = currentPageSequenceList.size() - 1;
        for (int i = size; i >= 0; i--) {
            if (currentPageSequenceList.get(i).getKey().equals(key)) {
                wizardController.updateConsumePageSelectedEvent(true);
                wizardController.updateEditingAfterReview(true);
                mPager.setCurrentItem(i);
                updateBottomBar();
                break;
            }
        }
    }

    @Override
    public void onPageDataChanged(Page page) {
        if (page.isRequired()) {
            if (recalculateCutOffPage()) {
                mPagerAdapter.notifyDataSetChanged();
                updateBottomBar();
            }
        }
    }

    @Override
    public Page onGetPage(String key) {
        return mWizardModel.findByKey(key);
    }

    private boolean recalculateCutOffPage() {

        //TODO Check if this works.

        // Cut off the pager adapter at first required page that isnt completed
        List<Page> currentPageSequenceList = mWizardModel.getCurrentPageSequence();
        int cutOffPage = currentPageSequenceList.size() + 1;

        for (int i = 0; i < currentPageSequenceList.size(); i++) {
            Page page = currentPageSequenceList.get(i);
            if (page.isRequired() && !page.isCompleted()) {
                cutOffPage = i;
                break;
            }
        }

        if (mPagerAdapter.getCutOffPage() != cutOffPage) {
            mPagerAdapter.setCutOffPage(cutOffPage);
            return true;
        }
        return false;
    }

    public List<String> getStrings() {
        return strings;
    }

}
