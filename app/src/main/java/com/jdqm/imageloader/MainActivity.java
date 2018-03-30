package com.jdqm.imageloader;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jdqm.imageloader.loader.ImageLoader;
import com.jdqm.imageloader.util.DisplayUtil;

import java.util.Arrays;
import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.jdqm.imageloader.util.DisplayUtil.dpToPixel;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private List<String> mImageUrls;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mImageAdapter;
    private int mImageWidth;

    private ImageLoader mImageLoader;

    private boolean isRecyclerViewIdle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        mImageLoader = ImageLoader.build(this);
    }

    private void initData() {
        mImageUrls = Arrays.asList(
                "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3402737307,2620266250&fm=27&gp=0.jpg",
                "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3434835309,625095034&fm=27&gp=0.jpg",
                "http://b.hiphotos.baidu.com/zhidao/pic/item/a6efce1b9d16fdfafee0cfb5b68f8c5495ee7bd8.jpg",
                "http://pic47.nipic.com/20140830/7487939_180041822000_2.jpg",
                "http://pic41.nipic.com/20140518/4135003_102912523000_2.jpg",
                "http://img2.imgtn.bdimg.com/it/u=1133260524,1171054226&fm=21&gp=0.jpg",
                "http://h.hiphotos.baidu.com/image/pic/item/3b87e950352ac65c0f1f6e9efff2b21192138ac0.jpg",
                "http://pic42.nipic.com/20140618/9448607_210533564001_2.jpg",
                "http://pic10.nipic.com/20101027/3578782_201643041706_2.jpg",
                "http://img3.3lian.com/2013/c1/34/d/93.jpg",
                "http://b.zol-img.com.cn/desk/bizhi/image/3/960x600/1375841395686.jpg",
                "http://imgrt.pconline.com.cn/images/upload/upc/tx/wallpaper/1210/17/c1/spcgroup/14468225_1350443478079_1680x1050.jpg",
                "http://pic41.nipic.com/20140518/4135003_102025858000_2.jpg",
                "http://pic.58pic.com/58pic/13/00/22/32M58PICV6U.jpg",
                "http://h.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=429e7b1b92ef76c6d087f32fa826d1cc/7acb0a46f21fbe09cc206a2e69600c338744ad8a.jpg",
                "http://pica.nipic.com/2007-12-21/2007122115114908_2.jpg",
                "http://cdn.duitang.com/uploads/item/201405/13/20140513212305_XcKLG.jpeg",
                "http://img4.duitang.com/uploads/item/201404/17/20140417105820_GuEHe.thumb.700_0.jpeg",
                "http://cdn.duitang.com/uploads/item/201204/21/20120421155228_i52eX.thumb.600_0.jpeg",
                "http://img4.duitang.com/uploads/item/201404/17/20140417105856_LTayu.thumb.700_0.jpeg",
                "http://img04.tooopen.com/images/20130723/tooopen_20530699.jpg",
                "http://pic.dbw.cn/0/01/33/59/1335968_847719.jpg",
                "http://a.hiphotos.baidu.com/image/pic/item/a8773912b31bb051a862339c337adab44bede0c4.jpg",
                "http://h.hiphotos.baidu.com/image/pic/item/f11f3a292df5e0feeea8a30f5e6034a85edf720f.jpg",
                "http://img0.pconline.com.cn/pconline/bizi/desktop/1412/ER2.jpg",
                "http://pic.58pic.com/58pic/11/25/04/91v58PIC6Xy.jpg",
                "http://img3.3lian.com/2013/c2/32/d/101.jpg",
                "http://pic25.nipic.com/20121210/7447430_172514301000_2.jpg",
                "http://img02.tooopen.com/images/20140320/sy_57121781945.jpg"

        );
        int cap = dpToPixel(20);
        mImageWidth = (DisplayUtil.getScreenWidth() - cap) / 3;
        Log.d(TAG, "mImageWidth: " + mImageWidth);
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set((int)dpToPixel(5), (int) dpToPixel(5), 0, 0);
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mImageAdapter = new ImageAdapter(this);
        mRecyclerView.setAdapter(mImageAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE) {
                    isRecyclerViewIdle = true;
                    mImageAdapter.notifyDataSetChanged();
                } else {
                    isRecyclerViewIdle = false;
                }
            }
        });
    }

    private class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {
        private LayoutInflater mInflater;
        private Drawable mDefaultDrawable;

        public ImageAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
            mDefaultDrawable = context.getResources().getDrawable(R.drawable.image_default);
        }

        @Override
        public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ImageViewHolder(mInflater.inflate(R.layout.image_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ImageViewHolder holder, int position) {
            String url = mImageUrls.get(position);
            String tag = (String) holder.imageView.getTag();
            if (!url.equals(tag)) {
                holder.imageView.setImageDrawable(mDefaultDrawable);
            }
            if (isRecyclerViewIdle) {
                holder.imageView.setTag(url);
                mImageLoader.bindBitmap(url, holder.imageView, mImageWidth, mImageWidth);
            }
        }


        @Override
        public int getItemCount() {
            return mImageUrls.size();
        }
    }

    private class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            imageView.getLayoutParams().width = mImageWidth;
            imageView.getLayoutParams().height = mImageWidth;
        }
    }

}
