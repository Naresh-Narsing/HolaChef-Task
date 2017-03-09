package app.holachef.category.ui;

import java.util.List;

import app.holachef.category.model.CategoryData;

/**
 * Created by craftsvilla on 24/2/17.
 */

public interface CategoryInterface {

    void showProgressDialog(String message, boolean indeterminate, boolean isCancelable);

    void hideProgressDialog();

    void showMoreItemProgressBar();

    void hideMoreItemsProgressBar();

    void categoryDataSuccess(List<CategoryData> data);

    void categoryDataFailure();

    void loadingMoreItemsInAdapter(List<CategoryData> data);
}
