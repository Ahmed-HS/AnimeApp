package com.team.animeapp.ui.searchfragment;

import com.google.android.material.chip.Chip;
import com.team.animeapp.data.models.network.Genre;
import com.team.animeapp.databinding.ItemFilterBinding;
import com.team.animeapp.databinding.ItemFilterBindingImpl;
import com.team.animeapp.util.recyclerview.BaseListAdapter;
import com.team.animeapp.util.recyclerview.Bindable;

import java.util.HashSet;
import java.util.Set;

@FunctionalInterface
interface OnFilterClickListener{

    void onClick(Genre genre,boolean isChecked,Set<Genre> checkedFilters);
}

class TagsAdapter extends BaseListAdapter<Genre, ItemFilterBinding> {


    private Set<Genre> checkedFilters;

    private OnFilterClickListener listener;

    public TagsAdapter(int layoutId, OnFilterClickListener onFilterClick) {
        super(layoutId, null);
        checkedFilters = new HashSet<>();
        listener = onFilterClick;
    }

    @Override
    protected void bind(ItemFilterBinding binding,Genre item,int position)
    {
        binding.tag.setText(item.name);

        binding.tag.setChecked(checkedFilters.contains(item));

        binding.tag.setOnClickListener (v ->{
            Chip tag = (Chip) v;
            if(tag.isChecked())
            {
                checkedFilters.add(item);
            }
            else
            {
                checkedFilters.remove(item);
            }

            listener.onClick(item,tag.isChecked(),checkedFilters);
        });
    }


}