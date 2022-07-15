package com.rick.thridweekapp.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rick.thridweekapp.bean.ContactBean;
import com.rick.thridweekapp.databinding.ItemContactBinding;

/**
 * Created by Rick on 2022/7/15 16:30.
 * God bless my code!
 */
public class ContactAdapter extends ListAdapter<ContactBean, ContactAdapter.InnerHolder> {
    public ContactAdapter() {
        super(new DiffUtil.ItemCallback<ContactBean>() {
            @Override
            public boolean areItemsTheSame(@NonNull ContactBean oldItem, @NonNull ContactBean newItem) {
                return oldItem.getPhone().equals(newItem.getPhone());
            }

            @Override
            public boolean areContentsTheSame(@NonNull ContactBean oldItem, @NonNull ContactBean newItem) {
                return oldItem.equals(newItem);
            }
        });
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InnerHolder(ItemContactBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.bind.setItem(getItem(position));
        holder.itemView.setOnClickListener((v) -> {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(getItem(position));
            }
        });
    }

    private OnItemClickListener mItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(ContactBean contact);
    }

    public static class InnerHolder extends RecyclerView.ViewHolder {
        ItemContactBinding bind;

        public InnerHolder(@NonNull ItemContactBinding bind) {
            super(bind.getRoot());
            this.bind = bind;
        }
    }
}
