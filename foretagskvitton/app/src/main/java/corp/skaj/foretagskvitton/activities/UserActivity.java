package corp.skaj.foretagskvitton.activities;

import android.os.Bundle;

import corp.skaj.foretagskvitton.R;

public class UserActivity extends AbstractActivity {

        public static final String STATE_FOR_BOTTOM_MENU = "UserActivity";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_company_listing);

            initBottomBar(STATE_FOR_BOTTOM_MENU, this);
    }
}
