package cn.itsite.amain.s1.common.clip;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

import cn.itsite.abase.log.ALog;
import cn.itsite.abase.mvp.view.base.BaseActivity;
import cn.itsite.amain.R;
import cn.itsite.amain.s1.utils.ImageTools;
import cn.itsite.amain.s1.utils.ImageUtils;
import cn.itsite.amain.s1.widget.ClipImageLayout;


public class ClipActivity extends BaseActivity {
    private static final String TAG = ClipActivity.class.getSimpleName();
    private Toolbar toolbar;
    private TextView toolbarTitle;
    private TextView toolbarMenu;
    private ClipImageLayout clipImageLayout;
    private String path;
    private ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clipimage);
        //这步必须要加
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initView();
        initToolbar();
        initData();


    }

    private void initView() {
        toolbar = ((Toolbar) findViewById(R.id.toolbar));
        toolbarTitle = ((TextView) findViewById(R.id.toolbar_title));
        toolbarMenu = ((TextView) findViewById(R.id.toolbar_menu));
        clipImageLayout = ((ClipImageLayout) findViewById(R.id.id_clipImageLayout));
        toolbarMenu.setOnClickListener(v -> {
            loadingDialog.show();

            new Thread(() -> {
                Bitmap bitmap = ImageUtils.zoomImage(clipImageLayout.clip());

                final String path = getCacheDir().getPath() + "/" + System.currentTimeMillis() + ".png";
                ImageTools.savePhotoToSDCard(bitmap, path);
                ALog.e("@@ 压缩后--> width:" + bitmap.getWidth() + " height:" + bitmap.getHeight() + " bytes:" + bitmap.getByteCount());
                Intent intent = new Intent();
                intent.putExtra("path", path);
                setResult(RESULT_OK, intent);
                finish();
            }).start();
        });
    }

    private void initToolbar() {
        toolbarTitle.setText("裁剪");
        toolbarMenu.setText("确定");
    }

    private void initData() {
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setTitle("请稍后...");
        path = getIntent().getStringExtra("path");
        Log.d("hehe", "path:" + path);
        if (TextUtils.isEmpty(path) || !(new File(path).exists())) {
            Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap bitmap = ImageTools.convertToBitmap(path, 600, 600);
        if (bitmap == null) {
            Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        clipImageLayout.setBitmap(bitmap);
    }
}