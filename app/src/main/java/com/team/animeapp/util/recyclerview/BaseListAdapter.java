package com.team.animeapp.util.recyclerview;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class BaseListAdapter
    <Item,Binding extends ViewDataBinding> extends
    ListAdapter<Item, BaseListAdapter.BaseViewHolder<Binding>>
{

    protected Bindable<Binding,Item> bindInterface;
    int layoutId;
    public BaseListAdapter(int layoutId,Bindable<Binding,Item> bindInterface)
    {
        super(new BaseItemCallback<Item>());
        this.bindInterface = bindInterface;
        this.layoutId = layoutId;

    }

    @NonNull
    @Override
    public BaseViewHolder<Binding> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return BaseViewHolder.from(parent,layoutId);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseListAdapter.BaseViewHolder<Binding> holder, int position) {
        bind(holder.binding,getItem(position),position);
    }

    protected void bind(Binding binding,Item item,int position)
    {
        bindInterface.bind(binding,item,position);
    }


    static class BaseViewHolder<Binding extends ViewDataBinding>
     extends RecyclerView.ViewHolder
    {
        Binding binding;

        BaseViewHolder(Binding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }

        static <Binding extends ViewDataBinding> BaseViewHolder<Binding> from (ViewGroup parent, int layoutId)
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            Binding binding = DataBindingUtil.inflate(inflater,layoutId,parent,false);
            return new BaseViewHolder<Binding>(binding);
        }
    }


    static class BaseItemCallback<T> extends DiffUtil.ItemCallback<T> {

        @Override
        public boolean areItemsTheSame(@NonNull T oldItem, @NonNull T newItem) {
            return oldItem.equals(newItem);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull T oldItem, @NonNull T newItem) {
            return oldItem.equals(newItem);
        }
    }


}