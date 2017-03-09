package app.holachef.category.interactor;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import app.holachef.R;
import app.holachef.category.model.CategoryData;
import app.holachef.category.presenter.CategoryPresenterInterface;
import app.holachef.constants.UrlConstants;
import app.holachef.networkutils.VolleyUtils;
import app.holachef.utils.Connectivity;
import app.holachef.utils.DialogUtil;

public class CategoryInteractor implements CategoryInteractorInterface {
    private static final String TAG = "CategoryInteractor";
    Context context;
    CategoryPresenterInterface categoryPresenterInterface;
    List<CategoryData> data;

    public CategoryInteractor(Context context, CategoryPresenterInterface categoryPresenterInterface) {
        this.context = context;
        this.categoryPresenterInterface = categoryPresenterInterface;
    }

    @Override
    public void getCategoryDataResponse(Context context, int pageId, final boolean firstLoad) {
        try {
            if (Connectivity.isConnected(context)) {
                if (firstLoad) {
                    categoryPresenterInterface.showProgressDialog(context.getString(R.string.FetchingData), true, false);
                } else {
                    categoryPresenterInterface.showMoreItemProgressBar();
                }

                JsonArrayRequest categoryListDataReq = new JsonArrayRequest(UrlConstants.CATEGORY_LIST, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (firstLoad) {
                            if (response != null) {
                                data = new ArrayList<>();
                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();
                                Type listType = new TypeToken<List<CategoryData>>() {
                                }.getType();
                                data = gson.fromJson(response.toString(), listType);
                                categoryPresenterInterface.categoryDataSuccess(data);
                                categoryPresenterInterface.hideProgressDialog();
                            }
                        } else {
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();
                            Type listType = new TypeToken<List<CategoryData>>() {
                            }.getType();
                            data = gson.fromJson(response.toString(), listType);
                            categoryPresenterInterface.loadingMoreItemsInAdapter(data);
                            categoryPresenterInterface.hideMoreItemsProgressBar();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        categoryPresenterInterface.hideProgressDialog();
                        categoryPresenterInterface.categoryDataFailure();
                    }
                });
                VolleyUtils.getInstance(context).addToRequestQueue(categoryListDataReq);
            } else {
                DialogUtil.showNoNetworkAlert(context);
            }
        } catch (Exception e) {
            Log.i(TAG, "getCategoryDataResponse: " + e.getMessage());
        }
    }
}
