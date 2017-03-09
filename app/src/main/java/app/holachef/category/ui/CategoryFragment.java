package app.holachef.category.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import app.holachef.BaseFragment;
import app.holachef.R;
import app.holachef.category.interactor.CategoryInteractor;
import app.holachef.category.model.CategoryData;
import app.holachef.category.presenter.CategoryPresenter;

public class CategoryFragment extends BaseFragment implements CategoryInterface {
    public static final String TAG = "CategoryFragment";
    RecyclerView categoryView;
    ProgressDialog progressDialog;
    CategoryPresenter categoryPresenter;
    CategoryAdapter categoryAdapter;
    List<CategoryData> categoryDataList = new ArrayList<>();
    int pageId = 1;
    LinearLayoutManager linearLayoutManager;
    boolean isLoadingMoreItems = false;
    RelativeLayout loadMoreItemsLayout;
    View view;


    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category, container, false);
        initViews();
        return view;
    }

    @Override
    public void initViews() {
        progressDialog = new ProgressDialog(getActivity());
        categoryView = (RecyclerView) view.findViewById(R.id.categoryView);
        loadMoreItemsLayout = (RelativeLayout) view.findViewById(R.id.loadMoreItemsLayout);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        categoryView.setLayoutManager(linearLayoutManager);

        categoryPresenter = new CategoryPresenter(this, null);
        categoryPresenter.setInteractor(new CategoryInteractor(getActivity(), categoryPresenter));
        categoryPresenter.getListOfCategoryData(getActivity(), pageId, true);
        categoryAdapter = new CategoryAdapter(getActivity(), categoryDataList);
        categoryView.setAdapter(categoryAdapter);
        categoryView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
                int lastInScreen = firstVisibleItemPosition + visibleItemCount;
                if (lastInScreen >= totalItemCount) {
                    isLoadingMoreItems = true;
                    pageId++;
                    categoryPresenter.getListOfCategoryData(getActivity(), pageId, false);
                }
            }
        });
    }

    @Override
    public void showProgressDialog(String message, boolean indeterminate, boolean isCancelable) {
        if (isAlive() && progressDialog != null) {
            progressDialog.setIndeterminate(indeterminate);
            progressDialog.setCancelable(isCancelable);
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }

    @Override
    public void hideProgressDialog() {
        if (isAlive() && progressDialog != null && progressDialog.isShowing()) {
            progressDialog.hide();
        }
    }

    @Override
    public void showMoreItemProgressBar() {
        if (isAlive()) {
            loadMoreItemsLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideMoreItemsProgressBar() {
        if (isAlive()) {
            loadMoreItemsLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void categoryDataSuccess(List<CategoryData> data) {
        categoryDataList.addAll(data);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void categoryDataFailure() {
        showToast(getActivity(), getString(R.string.Something_not_right));
    }

    @Override
    public void loadingMoreItemsInAdapter(List<CategoryData> data) {
        categoryDataList.addAll(data);
        categoryAdapter.notifyDataSetChanged();
    }
}
