package com.example.webonise.mvpmvvmdemo.view;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.webonise.mvpmvvmdemo.R;
import com.example.webonise.mvpmvvmdemo.model.UserModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class to show list.
 */
@SuppressWarnings("ALL")
public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<UserModel> userModelList;
    private LayoutInflater mInflater;
    private final int VIEW_TYPE_ITEM = 0;
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public UserListAdapter(Context mContext, List<UserModel> userModelList, RecyclerView recyclerView) {
        this.mContext = mContext;
        this.userModelList = new ArrayList<>();
        this.userModelList = userModelList;
        this.mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();


            recyclerView
                    .addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView,
                                               int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            totalItemCount = linearLayoutManager.getItemCount();
                            lastVisibleItem = linearLayoutManager
                                    .findLastVisibleItemPosition();
                            if (!loading
                                    && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                                // End has been reached
                                // Do something
                                if (onLoadMoreListener != null) {
                                    onLoadMoreListener.onLoadMore();
                                }
                                loading = true;
                            }
                        }
                    });
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == VIEW_TYPE_ITEM) {
            View layoutView = mInflater.inflate(R.layout.user_list_row_item, null);
            viewHolder = new ViewHolders(layoutView);
        } else {
            View progressView = mInflater.inflate(R.layout.footer_layout, null);
            viewHolder = new LoadingViewHolder(progressView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolders) {
            ViewHolders viewHolder = (ViewHolders) holder;
            viewHolder.textViewName.setText(userModelList.get(position).getName());
            viewHolder.textViewEmail.setText(userModelList.get(position).getEmail());
            viewHolder.textViewBody.setText(userModelList.get(position).getBody());
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
            // fixes pre-Lollipop progressBar indeterminateDrawable tinting
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

                Drawable wrapDrawable = DrawableCompat.wrap(loadingViewHolder.progressBar.getIndeterminateDrawable());
                DrawableCompat.setTint(wrapDrawable, mContext.getResources().getColor(R.color.colorAccent));
                loadingViewHolder.progressBar.setIndeterminateDrawable(DrawableCompat.unwrap(wrapDrawable));
            } else {
                loadingViewHolder.progressBar.getIndeterminateDrawable().setColorFilter(mContext.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
            }
        }
    }


    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public void setLoaded() {
        loading = false;
    }


    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        int VIEW_TYPE_LOADING = 1;
        return userModelList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        public TextView textViewName, textViewEmail, textViewBody;

        public ViewHolders(View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewEmail = (TextView) itemView.findViewById(R.id.textViewEmail);
            textViewBody = (TextView) itemView.findViewById(R.id.textViewBody);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar1);
        }
    }
}
