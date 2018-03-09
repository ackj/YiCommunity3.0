package cn.itsite.amain.s1.scene.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.common.Params;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Author：leguang on 2017/4/12 0009 14:23
 * Email：langmanleguang@qq.com
 * <p>
 * 场景模块。
 */

public class SceneEditFragment extends BaseFragment {
    public static final String TAG = SceneEditFragment.class.getSimpleName();
    TextView toolbarTitle;
    Toolbar toolbar;
    RecyclerView recyclerViewDevices;
    RecyclerView recyclerViewMovement;
    private Params params = Params.getInstance();
    AddDeviceRVAdapter deviceAdapter;
    DeviceMovementRVAdapter movementAdapter;

    public static SceneEditFragment newInstance() {
        return new SceneEditFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_scene1, container, false);
        toolbarTitle = ((TextView) view.findViewById(R.id.toolbar_title));
        toolbar = ((Toolbar) view.findViewById(R.id.toolbar));
        recyclerViewDevices = ((RecyclerView) view.findViewById(R.id.recyclerView_devices));
        recyclerViewMovement = ((RecyclerView) view.findViewById(R.id.recyclerView_movement));
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("场景编辑");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());
    }

    private void initData() {
        recyclerViewDevices.setLayoutManager(new GridLayoutManager(_mActivity, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        deviceAdapter = new AddDeviceRVAdapter();
        recyclerViewDevices.setAdapter(deviceAdapter);
        List<String> data = new ArrayList<>();
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        deviceAdapter.setNewData(data);

        recyclerViewMovement.setLayoutManager(new LinearLayoutManager(_mActivity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        movementAdapter = new DeviceMovementRVAdapter();
        recyclerViewMovement.setAdapter(movementAdapter);
        movementAdapter.setNewData(data);
    }

    private void initListener() {
    }
}
