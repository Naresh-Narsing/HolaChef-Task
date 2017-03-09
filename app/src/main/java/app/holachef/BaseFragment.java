package app.holachef;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment {

    private int mLayoutId;
    private ProgressBar mProgressBar;

    public abstract void initViews();


    public interface ToolbarListener {
        void setToolbarTitle(String title);

        void setToolbarVisibility(int value);

        void setToolbar();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        return view;
    }

    protected void setLayout(int layoutId) {
        mLayoutId = layoutId;
    }

    protected void showSnackBar(Context context, String message) {
    }

    protected void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    protected boolean isAlive() {
        return getActivity() != null && !getActivity().isFinishing();
    }
}