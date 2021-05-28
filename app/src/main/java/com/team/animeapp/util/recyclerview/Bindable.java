package com.team.animeapp.util.recyclerview;

import androidx.databinding.ViewDataBinding;

@FunctionalInterface
public interface Bindable<Binding extends ViewDataBinding,Item> {

    void bind(Binding binding,Item item, int position);
}
