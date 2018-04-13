package cn.itsite.aftersales.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;

import cn.itsite.abase.cache.SPCache;
import cn.itsite.abase.common.UserHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.acommon.event.SwitchStoreEvent;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;
import me.liujia95.aftersales.R;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/11 0011 8:54
 */
@Route(path = "/aftersales/aftersalesfragment")
public class AfterSalesFragment extends BaseFragment {

    public static final String TAG = AfterSalesFragment.class.getSimpleName();
    private RelativeLayout mRlToolbar;
    private ImageView mIvBack;
    private RecyclerView mRecyclerView;
    private ImageRVAdapter mAdapter;
    BaseMedia addMedia = new BaseMedia() {
        @Override
        public TYPE getType() {
            return TYPE.IMAGE;
        }
    };

    ArrayList<BaseMedia> mSelectedMedia = new ArrayList<>();
    private EditText mEtExplain;
    private TextView mTvBackprice;
    private TextView mTvReason;

    public static AfterSalesFragment newInstance() {
        return new AfterSalesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aftersafes, container, false);
        mRlToolbar = view.findViewById(R.id.rl_toolbar);
        mIvBack = view.findViewById(R.id.iv_back);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mTvBackprice = view.findViewById(R.id.tv_backprice);
        mEtExplain = view.findViewById(R.id.et_explain);
        mTvReason = view.findViewById(R.id.tv_reason);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStateBar(mRlToolbar);
        initData();
        initListener();
    }

    private void initData() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(_mActivity, 4));
        mAdapter = new ImageRVAdapter();
        mRecyclerView.setAdapter(mAdapter);

        //默认图
        addMedia.setPath("android.resource://" + _mActivity.getPackageName() + "/" + R.drawable.ic_aftermarket_200px);
        mAdapter.addData(addMedia);
    }

    private void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                selectPhoto();
            }
        });
        mTvReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReasonDialog();
            }
        });
    }

    private void showReasonDialog() {
        new BaseDialogFragment()
                .setLayoutId(R.layout.dialog_reason)
                .setConvertListener((holder, dialog) -> {
                    String[] arrays = _mActivity.getResources().getStringArray(R.array.goods_return_reason);
                    RecyclerView recyclerView = holder.getView(R.id.recyclerView);
                    ReasonRVAdapter adapter = new ReasonRVAdapter();
                    recyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
                    recyclerView.setAdapter(adapter);
                    adapter.setNewData(Arrays.asList(arrays));
                    adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
                            mTvReason.setText(arrays[position]);
                            dialog.dismiss();
                        }
                    });
                })
                .setDimAmount(0.3f)
                .setGravity(Gravity.BOTTOM)
                .show(getChildFragmentManager());
    }

    private void selectPhoto() {
        BoxingConfig config = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG); // Mode：Mode.SINGLE_IMG, Mode.MULTI_IMG, Mode.VIDEO
        config.needCamera(R.drawable.ic_boxing_camera_white).needGif().withMaxCount(6) // 支持gif，相机，设置最大选图数
                .withMediaPlaceHolderRes(R.drawable.ic_boxing_default_image); // 设置默认图片占位图，默认无
        Boxing.of(config).withIntent(_mActivity, BoxingActivity.class, mSelectedMedia).start(this, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            mSelectedMedia = Boxing.getResult(data);
            mAdapter.setNewData(null);
            mAdapter.addData(mSelectedMedia);
            mAdapter.addData(addMedia);
        }
    }

}
