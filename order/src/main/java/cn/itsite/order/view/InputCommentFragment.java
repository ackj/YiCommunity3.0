package cn.itsite.order.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing_impl.ui.BoxingActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.itsite.abase.BaseApp;
import cn.itsite.abase.common.DialogHelper;
import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseFragment;
import cn.itsite.abase.network.http.BaseResponse;
import cn.itsite.abase.utils.ToastUtils;
import cn.itsite.acommon.OperateBean;
import cn.itsite.acommon.model.OrderDetailBean;
import cn.itsite.adialog.dialogfragment.BaseDialogFragment;
import cn.itsite.order.R;
import cn.itsite.order.contract.InputCommentContract;
import cn.itsite.order.model.PostCommentBean;
import cn.itsite.order.model.SubmitCommentBean;
import cn.itsite.order.presenter.InputCommentPresenter;
import in.srain.cube.views.ptr.PtrFrameLayout;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author liujia
 * @version v0.0.0
 * @E-mail liujia95me@126.com
 * @time 2018/4/13 0013 8:52
 */

public class InputCommentFragment extends BaseFragment<InputCommentContract.Presenter> implements InputCommentContract.View {


    public static final String TAG = InputCommentFragment.class.getSimpleName();
    private Toolbar mToolbar;
    private ImageView mIvBack;
    private RecyclerView mRecyclerView;
    private InputCommentRVAdapter mAdapter;
    private int mSelectedPosition;
    private int postPictureCount = 0;//上传含图片的评论数量
    private int responsePicCount = 0;//上传成功的含图片的评论数量

    BaseMedia mAddMedia = new BaseMedia() {
        @Override
        public BaseMedia.TYPE getType() {
            return TYPE.IMAGE;
        }
    };

    List<SubmitCommentBean> mSubmitCommentDatas;

    ArrayList<BaseMedia> mSelectedMedia = new ArrayList<>();
    private PtrFrameLayout mPtrFrameLayout;
    private OrderDetailBean mOrderDetailBean;
    private TextView mToolbarMenu;
    private TextView mToolbarTitle;

    public static InputCommentFragment newInstance(OrderDetailBean detailBean) {
        InputCommentFragment fragment = new InputCommentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("bean", detailBean);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    protected InputCommentContract.Presenter createPresenter() {
        return new InputCommentPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderDetailBean = (OrderDetailBean) getArguments().getSerializable("bean");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        mToolbar = view.findViewById(R.id.toolbar);
        mToolbarTitle = view.findViewById(R.id.toolbar_title);
        mToolbarMenu = view.findViewById(R.id.toolbar_menu);
        mIvBack = view.findViewById(R.id.iv_back);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mPtrFrameLayout = view.findViewById(R.id.ptrFrameLayout);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
        mPtrFrameLayout.setEnabled(false);
//        initPtrFrameLayout(mPtrFrameLayout, mRecyclerView);
    }

    private void initToolbar() {
        initStateBar(mToolbar);
        mToolbarTitle.setText("评价");
        mToolbarTitle.setTextColor(_mActivity.getResources().getColor(R.color.base_black));
        mToolbarMenu.setText("提交");
        mToolbarMenu.setTextColor(_mActivity.getResources().getColor(R.color.base_black));
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_left_gray_24dp);
        mToolbar.setNavigationOnClickListener(v -> ((SupportActivity) _mActivity).onBackPressedSupport());

    }

    private void initData() {
        mAddMedia.setPath("android.resource://" + BaseApp.mContext.getPackageName() + "/" + R.drawable.ic_aftermarket_200px);
        mToolbar.setBackgroundColor(_mActivity.getResources().getColor(R.color.white));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new InputCommentRVAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mSubmitCommentDatas = new ArrayList<>();
        for (int i = 0; i < mOrderDetailBean.getProducts().size(); i++) {
            OrderDetailBean.ProductsBean product = mOrderDetailBean.getProducts().get(i);
            SubmitCommentBean submitCommentBean = new SubmitCommentBean();
            submitCommentBean.setImgUrl(product.getImageUrl());
            submitCommentBean.setEvaLevel(-1);
            ArrayList<BaseMedia> medias = new ArrayList<>();
            medias.add(mAddMedia);
            submitCommentBean.setMedias(medias);
            mSubmitCommentDatas.add(submitCommentBean);
        }

        mAdapter.setNewData(mSubmitCommentDatas);
    }

    private void initListener() {
        mToolbarMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkComment()){
                    if(!submitPictures()){
                        submitComment();
                    }
                }
            }
        });
        mAdapter.setOnClickAddPicListener(new InputCommentRVAdapter.OnClickAddPicListener() {

            @Override
            public void clickAddPic(ArrayList<BaseMedia> medias, int position) {
                mSelectedMedia = new ArrayList<>(medias);
                mSelectedMedia.remove(medias.size() - 1);
                mSelectedPosition = position;
                selectPhoto();
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                SubmitCommentBean item = mAdapter.getItem(position);
                if (view.getId() == R.id.tv_input) {
                    showInputDialog(mAdapter.getItem(position));
                }else if(view.getId() == R.id.rb_level_good){
                    item.setEvaLevel(2);
                }else if(view.getId() ==R.id.rb_level_mid){
                    item.setEvaLevel(1);
                }else if(view.getId()==R.id.rb_level_bad){
                    item.setEvaLevel(0);
                }
            }
        });
    }

    //检查是否有
    private boolean checkComment() {
        for (int i = 0; i < mSubmitCommentDatas.size(); i++) {
            if(TextUtils.isEmpty(mSubmitCommentDatas.get(i).getEvaDescription())) {
                DialogHelper.warningSnackbar(getView(), "您还还有商品未填写评论哦~");
                return false;
            }
            if(mSubmitCommentDatas.get(i).getEvaLevel()==-1){
                DialogHelper.warningSnackbar(getView(), "您还还有商品未选择评级哦~");
                return false;
            }
        }
        return true;
    }

    private boolean submitPictures() {
        postPictureCount = 0;
        responsePicCount = 0;
        for (int i = 0; i < mSubmitCommentDatas.size(); i++) {
            ArrayList<BaseMedia> medias = mSubmitCommentDatas.get(i).getMedias();
            ALog.e(TAG,"submitPictures i:"+i+"  medias:"+medias.size());
            List<File> files= new ArrayList<>();
            for (int j = 0;j<medias.size()-1;j++){
                File file = new File(medias.get(j).getPath());
                files.add(file);
            }
            if(files.size()>0){
                postPictureCount ++;
                mPresenter.postPicture(files,i);
            }
        }
        if(postPictureCount>0){
            return true;
        }
        return false;
    }

    //提交评论流程
    //1、先将评论下的图片上传得到url集合，将其拼接到评论的表单中，
    //2、由于一次只能上传一个图片集合，针对多个评论都要上传图片的情况做分段上传处理。
    //3、即：post pic_list1 -->response list1's urls --> post pic_list2 --> response list2's urls
    //4、拼接：params[list1's urls,list2's urls] --> post params --> success
    private void submitComment(){
        PostCommentBean postCommentBean = new PostCommentBean();
        postCommentBean.setCategory("EVALUATE");
        postCommentBean.setUid(mOrderDetailBean.getUid());
        List<PostCommentBean.ProductsBean> productsBeans = new ArrayList<>();
        postCommentBean.setProducts(productsBeans);
        for (int i = 0; i < mSubmitCommentDatas.size(); i++) {
            SubmitCommentBean submitCommentBean = mSubmitCommentDatas.get(i);
            PostCommentBean.ProductsBean productsBean = new PostCommentBean.ProductsBean();
            OrderDetailBean.ProductsBean originalProduct = mOrderDetailBean.getProducts().get(i);
            productsBean.setUid(originalProduct.getUid());
            productsBean.setSku(originalProduct.getSku());
            productsBean.setEvaLevel(submitCommentBean.getEvaLevel());
            productsBean.setEvaDescription(submitCommentBean.getEvaDescription());
            productsBean.setPictures(submitCommentBean.getFiles());
            productsBean.setAmount(originalProduct.getAmount());
            productsBeans.add(productsBean);
        }
        mPresenter.postComments(postCommentBean);
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
            mSelectedMedia = null;
            mSelectedMedia = Boxing.getResult(data);
            mSelectedMedia.add(mAddMedia);
            mAdapter.getItem(mSelectedPosition).setMedias(mSelectedMedia);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void showInputDialog(SubmitCommentBean submitCommentBean) {
        new BaseDialogFragment()
                .setLayoutId(R.layout.dialog_input)
                .setConvertListener((holder, dialog) -> {
                    EditText etInput = holder.getView(R.id.et_input);
                    etInput.setText(submitCommentBean.getEvaDescription());
                    etInput.setSelection(etInput.getText().toString().length());
                    holder
                            .setOnClickListener(R.id.btn_cancel, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            })
                            .setOnClickListener(R.id.btn_comfirm, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String input = etInput.getText().toString().trim();
                                    if (TextUtils.isEmpty(input)) {
                                        ToastUtils.showToast(_mActivity, "请输入留言内容");
                                    } else {
                                        submitCommentBean.setEvaDescription(input);
                                        mAdapter.notifyDataSetChanged();
                                        dialog.dismiss();
                                    }
                                }
                            });
                })
                .setDimAmount(0.3f)
                .setMargin(40)
                .setGravity(Gravity.CENTER)
                .show(getChildFragmentManager());
    }

    @Override
    public void responsePostPicture(BaseResponse<List<OperateBean>> response, int position) {
        ToastUtils.showToast(_mActivity,"评论"+(position+1)+"的图片上传成功！");
        ALog.e(TAG,"图片上传成功："+position+"  个数："+response.getData().size());
        List<String> files = new ArrayList<>();
        for (int i = 0; i < response.getData().size(); i++) {
            files.add(response.getData().get(i).getUrl());
        }
        mSubmitCommentDatas.get(position).setFiles(files);

        //将图片全部上传完成，拼接完成后正式提交评论
        responsePicCount++;
        if(postPictureCount==responsePicCount){
            submitComment();
        }
    }

    @Override
    public void responsePostCommentsSuccess(BaseResponse response) {
        DialogHelper.successSnackbar(getView(),response.getMessage());
        pop();
    }
}
