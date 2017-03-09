package app.holachef.category.interactor;

import android.content.Context;

/**
 * Created by craftsvilla on 24/2/17.
 */

public interface CategoryInteractorInterface {

    void getCategoryDataResponse(Context context, int pageId, boolean firstLoad);
}
