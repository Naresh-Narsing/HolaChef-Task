package app.holachef.category.ui;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.util.List;

import app.holachef.HolachefApplication;
import app.holachef.R;
import app.holachef.category.model.CategoryData;
import app.holachef.utils.StringUtils;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    List<CategoryData> data;
    ImageLoader imageLoader;
    ImageViewAware imageViewAware;

    public CategoryAdapter(Context context, List<CategoryData> categoryDataList) {
        this.context = context;
        this.data = categoryDataList;
        imageLoader = HolachefApplication.getImageLoader();
    }

    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_items_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CategoryAdapter.CategoryViewHolder holder, final int position) {
        CategoryData categoryDataItem = data.get(position);
        imageViewAware = new ImageViewAware(holder.categoryItemView, false);
        imageLoader.displayImage(categoryDataItem.getImage(), imageViewAware);
        holder.categoryName.setText(categoryDataItem.getName());
        holder.chefName.setText(categoryDataItem.getChef());
        holder.price.setText(StringUtils.formatRsString(categoryDataItem.getPrice()));
        holder.rating.setText(String.valueOf(categoryDataItem.getRating()));


        if (categoryDataItem.getQuantity() > 0) {
            holder.addToCart.setVisibility(View.GONE);
            holder.countLayout.setVisibility(View.VISIBLE);
            holder.itemsInCart.setVisibility(View.VISIBLE);
            holder.count.setText(String.valueOf(categoryDataItem.getQuantity()));
            holder.itemsInCart.setText(String.format(context.getString(R.string.items_in_cart), categoryDataItem.getQuantity()));
        } else {
            holder.itemsInCart.setVisibility(View.GONE);
            holder.addToCart.setVisibility(View.VISIBLE);
            holder.countLayout.setVisibility(View.GONE);
        }

        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.addToCart.setVisibility(View.GONE);
                holder.countLayout.setVisibility(View.VISIBLE);
                addOrRemoveFromCart(position, true);
                holder.itemsInCart.setVisibility(View.VISIBLE);
                holder.count.setText(String.valueOf(1));
                holder.itemsInCart.setText(String.format(context.getString(R.string.items_in_cart), 1));
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = addOrRemoveFromCart(position, true);
                if (quantity > 0) {
                    holder.itemsInCart.setVisibility(View.VISIBLE);
                    holder.count.setText(String.valueOf(quantity));
                    holder.itemsInCart.setText(String.format(context.getString(R.string.items_in_cart), quantity));
                }
            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = addOrRemoveFromCart(position, false);
                if (quantity == 0) {
                    holder.itemsInCart.setVisibility(View.GONE);
                    holder.addToCart.setVisibility(View.VISIBLE);
                    holder.countLayout.setVisibility(View.GONE);
                } else {
                    holder.itemsInCart.setVisibility(View.VISIBLE);
                    holder.count.setText(String.valueOf(quantity));
                    holder.itemsInCart.setText(String.format(context.getString(R.string.items_in_cart), quantity));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public int addOrRemoveFromCart(int position, boolean isAdded) {
        int quantity = 0;
        CategoryData categoryData;
        if (isAdded) {
            categoryData = data.get(position);
            categoryData.setQuantity(categoryData.getQuantity() + 1);
            data.remove(position);
            data.add(position, categoryData);
        } else {
            categoryData = data.get(position);
            if (categoryData.getQuantity() > 0) {
                categoryData.setQuantity(categoryData.getQuantity() - 1);
            }
            data.remove(position);
            data.add(position, categoryData);
        }

        if (categoryData != null) {
            quantity = categoryData.getQuantity();
        }
        return quantity;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView categoryName;
        AppCompatImageView categoryItemView;
        AppCompatTextView chefName;
        AppCompatTextView price;
        AppCompatButton addToCart;
        LinearLayout countLayout;
        AppCompatTextView add;
        AppCompatTextView remove;
        AppCompatTextView itemsInCart;
        AppCompatTextView count;
        AppCompatTextView rating;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            categoryName = (AppCompatTextView) itemView.findViewById(R.id.categoryName);
            chefName = (AppCompatTextView) itemView.findViewById(R.id.chefName);
            add = (AppCompatTextView) itemView.findViewById(R.id.add);
            remove = (AppCompatTextView) itemView.findViewById(R.id.remove);
            itemsInCart = (AppCompatTextView) itemView.findViewById(R.id.itemsInCart);
            count = (AppCompatTextView) itemView.findViewById(R.id.count);
            price = (AppCompatTextView) itemView.findViewById(R.id.price);
            rating = (AppCompatTextView) itemView.findViewById(R.id.rating);
            categoryItemView = (AppCompatImageView) itemView.findViewById(R.id.categoryItemView);
            addToCart = (AppCompatButton) itemView.findViewById(R.id.addToCart);
            countLayout = (LinearLayout) itemView.findViewById(R.id.countLayout);
        }
    }
}
