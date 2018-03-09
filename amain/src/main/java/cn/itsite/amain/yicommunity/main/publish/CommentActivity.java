package cn.itsite.amain.yicommunity.main.publish;

import android.os.Bundle;

import cn.itsite.abase.mvp.view.base.BaseActivity;
import cn.itsite.amain.R;
import cn.itsite.amain.yicommunity.common.Constants;
import cn.itsite.amain.yicommunity.main.publish.view.CommentFragment;

/**
 * Author: LiuJia on 2017/5/16 0016 17:55.
 * Email: liujia95me@126.com
 * 评论的父容器
 */

public class CommentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            String fid = getIntent().getStringExtra(Constants.KEY_FID);
            int type = getIntent().getIntExtra(Constants.KEY_TYPE, 0);
            loadRootFragment(R.id.fl_main_activity, CommentFragment.newInstance(fid, type));
        }
    }
}
