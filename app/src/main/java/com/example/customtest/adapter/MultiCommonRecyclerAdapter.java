package com.example.customtest.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.hivescm.commonbusiness.adapter.SimpleCommonRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by ZQiong on 2017/9/7 13:55.
 */
public abstract class MultiCommonRecyclerAdapter<T> extends RecyclerView.Adapter<MultiCommonRecyclerAdapter.ViewHolder> {
    private Context context;
    public List<T> mObjects = new ArrayList<>();
    private boolean mNotifyOnChange;

    public MultiCommonRecyclerAdapter(Context context) {
        this(context, null);
        this.context = context;
    }

    public MultiCommonRecyclerAdapter(Context context, Collection<T> objects) {
        this.mNotifyOnChange = true;
        this.context = context;
        this.mObjects = new ArrayList();
        if (objects != null) {
            this.mObjects.addAll(objects);
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, getmLayoutId(viewType), parent, false);
        ViewHolder holder = new ViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        bindData(holder, position, mObjects.get(position));
    }

    public abstract void bindData(ViewHolder holder, int position, T obj);


    public static class ViewHolder extends RecyclerView.ViewHolder {
        //        private SimpleCommonRecyclerAdapter.OnSelfSignListener onSelfSignListener;
        private ViewDataBinding binding;

        public ViewDataBinding getBinding() {
            return this.binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public ViewHolder(View itemView, SimpleCommonRecyclerAdapter.OnItemClickListener onItemClickListener) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return this.mObjects.size();
    }

    public T getItem(int position) {
        return this.mObjects.get(position);
    }

    public int getPosition(T item) {
        return this.mObjects.indexOf(item);
    }


    public List<T> getmObjects() {
        return this.mObjects;
    }

    public void add(Collection<T> objects) {
        this.mObjects.addAll(objects);
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }

    public void replace(Collection<T> objects) {
        if (objects == null || objects.size() < 1) {
            return;
        }
        this.mObjects.clear();
        this.mObjects.addAll(objects);
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }

    public void add(T[] objects) {
        this.mObjects.addAll(Arrays.asList(objects));
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }

    public void add(T object) {
        this.mObjects.add(object);
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }

    public void insert(T object, int index) {
        this.mObjects.add(index, object);
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }

    public void remove(T object) {
        this.mObjects.remove(object);
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }

    public T remove(int position) {
        T t = this.mObjects.remove(position);
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

        return t;
    }

    public void remove(Collection<T> collection) {
        this.mObjects.removeAll(collection);
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }

    public void clear() {
        this.mObjects.clear();
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }

    public void sort(Comparator<? super T> comparator) {
        Collections.sort(this.mObjects, comparator);
        if (this.mNotifyOnChange) {
            this.notifyDataSetChanged();
        }

    }

    public void setNotifyOnChange(boolean notifyOnChange) {
        this.mNotifyOnChange = notifyOnChange;
    }

    public Context getContext() {
        if (this.context != null) {
            return this.context;
        } else {
            throw new IllegalArgumentException("请实现带Context的构造方法");
        }
    }

    public abstract int getmLayoutId(int viewType);


}
