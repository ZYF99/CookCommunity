package com.lxh.cookcommunity.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.lxh.cookcommunity.databinding.ItemFooterProgressbarBinding;

import java.util.List;

public abstract class LoadMoreRecyclerAdapter<Bean, Binding extends ViewDataBinding> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ITEM_TYPE_HEADER = 0;
    private static final int ITEM_TYPE_CONTENT = 1;
    private static final int ITEM_TYPE_LOAD_MORE = 2;
    private static final int FOOTER_SIZE = 1;

    private int layoutRes;
    private Boolean hasLoadMore;
    public List<Bean> baseList;
    private OnCellClickListener<Binding, Bean> onCellClickListener;
    private View headerView;
    public MutableLiveData<Boolean> onLoadMore = new MutableLiveData<>(false);

    public interface OnCellClickListener<Binding, Bean> {
        void onCellClick(Binding binding, Bean bean);
    }

    public void setOnCellClickListener(OnCellClickListener<Binding, Bean> onCellClickListener) {
        this.onCellClickListener = onCellClickListener;
    }

    public LoadMoreRecyclerAdapter(
            LifecycleOwner lifecycleOwner,
            int layoutRes,
            Boolean hasLoadMore,
            final List<Bean> baseList
    ) {
        super();
        this.layoutRes = layoutRes;
        this.hasLoadMore = hasLoadMore;
        this.baseList = baseList;
        onLoadMore.observe(lifecycleOwner, aBoolean -> notifyItemChanged(baseList.size() - 1));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ITEM_TYPE_CONTENT:
                return new ContentViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(
                                layoutRes,
                                parent,
                                false
                        )
                );
            case ITEM_TYPE_HEADER:
                if (headerView != null) return new HeaderViewHolder(headerView);
                else throw new RuntimeException("no headerView");
            case ITEM_TYPE_LOAD_MORE:
                return new FooterViewHolder(
                        ItemFooterProgressbarBinding.inflate(
                                LayoutInflater.from(parent.getContext()), parent, false
                        ).getRoot()
                );
            default:
                throw new RuntimeException("no such ViewType");
        }
    }


    class ContentViewHolder extends RecyclerView.ViewHolder {
        Binding binding;

        public ContentViewHolder(View itemView) {
            super(itemView);
            this.binding = DataBindingUtil.bind(itemView);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        Binding binding;

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        Binding binding;

        public FooterViewHolder(View itemView) {
            super(itemView);
        }

        void onLoadMore() {
            if (onLoadMore.getValue()) itemView.setVisibility(View.VISIBLE);
            else itemView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {

        if (headerView == null) {
            if (hasLoadMore) {
                return baseList.size() + FOOTER_SIZE;
            } else {
                return baseList.size();
            }
        } else {
            if (hasLoadMore)
                return baseList.size() + 1 + FOOTER_SIZE;
            else return baseList.size() + 1;
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (headerView != null) {
            if (position == 0) return ITEM_TYPE_HEADER;
            if (position >= baseList.size() + 1) return ITEM_TYPE_LOAD_MORE;
            return ITEM_TYPE_CONTENT;
        } else {
            if (position >= baseList.size()) return ITEM_TYPE_LOAD_MORE;
            else return ITEM_TYPE_CONTENT;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case ITEM_TYPE_CONTENT:
                final int pos;
                if (headerView != null) pos = position - 1;
                else pos = position;
                ContentViewHolder holder1 = (ContentViewHolder) holder;
                holder1.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onCellClickListener != null) {
                            onCellClickListener.onCellClick(holder1.binding, baseList.get(pos));
                            //notifyItemChanged(position);
                        }
                    }
                });
                bindData(holder1.binding, pos);
                break;
            case ITEM_TYPE_HEADER:
                break;
            case ITEM_TYPE_LOAD_MORE:
                FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
                footerViewHolder.onLoadMore();
                break;
        }

    }

    public void replaceData(List<Bean> newList) {
         if (baseList.isEmpty()) {
            baseList = newList;
            notifyDataSetChanged();
        } else {
            if (newList.size() != 0) {
                DiffUtil.DiffResult diffResult =
                        DiffUtil.calculateDiff(
                                new SingleBeanDiffCallBack<>(
                                        baseList,
                                        newList
                                ),
                                true
                        );
                baseList = newList;
                diffResult.dispatchUpdatesTo(this);
            } else {
                baseList = newList;
                notifyDataSetChanged();
            }
        }
    }

    public abstract void bindData(Binding binding, int position);



}
