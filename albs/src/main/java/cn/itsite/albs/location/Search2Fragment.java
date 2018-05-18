package cn.itsite.albs.location;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.albs.R;

/**
 * Created by liujia on 2018/5/17.
 */

public class Search2Fragment extends BaseFragment{
    public static Search2Fragment newInstance() {
        return new Search2Fragment();
    }

    public static Search2Fragment newInstance(Bundle bundle) {
        Search2Fragment fragment = new Search2Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        return attachToSwipeBack(view);
    }
}
