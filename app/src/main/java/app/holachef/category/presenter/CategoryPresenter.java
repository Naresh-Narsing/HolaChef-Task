package app.holachef.category.presenter;

import android.content.Context;

import java.util.List;

import app.holachef.category.interactor.CategoryInteractorInterface;
import app.holachef.category.model.CategoryData;
import app.holachef.category.ui.CategoryInterface;

public class CategoryPresenter implements CategoryPresenterInterface {
    CategoryInterface view;
    CategoryInteractorInterface interactorInterface;

    public CategoryPresenter(CategoryInterface view, CategoryInteractorInterface interactorInterface) {
        this.view = view;
        this.interactorInterface = interactorInterface;
    }

    @Override
    public void getListOfCategoryData(Context context, int pageId, boolean firstLoad) {
        interactorInterface.getCategoryDataResponse(context, pageId, firstLoad);
    }

    @Override
    public void loadingMoreItemsInAdapter(List<CategoryData> data) {
        view.loadingMoreItemsInAdapter(data);
    }

    @Override
    public void showProgressDialog(String message, boolean indeterminate, boolean isCancelable) {
        view.showProgressDialog(message, indeterminate, isCancelable);
    }

    @Override
    public void hideProgressDialog() {
        view.hideProgressDialog();
    }

    @Override
    public void showMoreItemProgressBar() {
        view.showMoreItemProgressBar();
    }

    @Override
    public void hideMoreItemsProgressBar() {
        view.hideMoreItemsProgressBar();
    }

    @Override
    public void categoryDataSuccess(List<CategoryData> data) {
        view.categoryDataSuccess(data);
    }

    @Override
    public void categoryDataFailure() {
        view.categoryDataFailure();
    }

    public void setInteractor(CategoryInteractorInterface interactor) {
        this.interactorInterface = interactor;
    }
}
