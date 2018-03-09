package cn.itsite.amain.yicommunity.main.guide;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import cn.itsite.abase.mvp.view.base.BaseActivity;
import cn.itsite.amain.R;

/**
 * Author: LiuJia on 2017/6/8 0008 09:14.
 * Email: liujia95me@126.com
 * 引导页
 */
public class GuideActivity extends BaseActivity {
    private static final String TAG = GuideActivity.class.getSimpleName();
    private ImageView ivGuide1;
    private ImageView ivGuide2;
    private int[] guides;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
        initListener();
    }

    private void initListener() {
        ivGuide1.setOnClickListener(v -> {
            loadGuides();
        });
    }

    private void initView() {
        ivGuide1 = ((ImageView) findViewById(R.id.image_guide_1));
        ivGuide2 = ((ImageView) findViewById(R.id.image_guide_2));
    }

    @Override
    public boolean swipeBackPriority() {
        return false;
    }

    private void initData() {
        Intent intent = getIntent();
        guides = intent.getIntArrayExtra("guide");
        for (int i = 0; i < guides.length; i++) {
            Glide.with(this).load(guides[i])
                    .preload();
        }
        loadGuides();
    }

    /**
     * 加载指引页
     * 指引页是以两层ImageView实现的，目的是为了让两张以上引导页切换时自然过渡
     */
    private void loadGuides() {
        if (currentPosition < guides.length) {
            Glide.with(this)
                    .load(guides[currentPosition])
                    .transition(new DrawableTransitionOptions().dontTransition())
                    .into(ivGuide1);
            if (currentPosition + 1 < guides.length) {
                Glide.with(this)
                        .load(guides[currentPosition + 1])
                        .transition(new DrawableTransitionOptions().dontTransition())
                        .into(ivGuide2);
            }
            currentPosition++;
        } else {
            finish();
        }
    }
}
