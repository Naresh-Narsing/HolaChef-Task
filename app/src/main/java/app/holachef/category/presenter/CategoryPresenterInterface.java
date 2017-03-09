package app.holachef.category.presenter;

import android.content.Context;

import java.util.List;

import app.holachef.category.model.CategoryData;
import app.holachef.category.ui.CategoryInterface;

/**
 * Created by craftsvilla on 24/2/17.
 */

public interface CategoryPresenterInterface extends CategoryInterface {

    void getListOfCategoryData(Context context,int pageId,boolean firstLoad);

    void loadingMoreItemsInAdapter(List<CategoryData> data);
}
