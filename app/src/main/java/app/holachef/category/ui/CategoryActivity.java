package app.holachef.category.ui;

import android.os.Bundle;

import app.holachef.BaseActivity;
import app.holachef.R;

public class CategoryActivity extends BaseActivity {

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void initViews() {
        getSupportFragmentManager().beginTransaction().add(R.id.frameContainer, CategoryFragment.newInstance()
                , CategoryFragment.TAG).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initViews();
    }
}
