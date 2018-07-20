package jsp.co.za.jspandroid.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import jsp.co.za.jspandroid.R;


public class GalleryAdapter extends BaseAdapter {

    LayoutInflater inflater;

    private Context mContext = null;
    private String[] images;


    public GalleryAdapter(Context context, String[] images) {
        this.mContext = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return images.length;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 4
    @Override
    public Object getItem(int position) {
        return images[position];
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder;

        if (convertView == null) {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.custom_image_layout, null);
            holder.imageView = convertView.findViewById(R.id.imgZoom);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        String url = images[position];
        Glide
                .with(mContext)
                .load(url)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(holder.imageView);
        return convertView;

    }

    private class Holder {
        ImageView imageView;
    }

}
