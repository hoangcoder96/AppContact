package nhatto.com.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import nhatto.com.R;
import nhatto.com.model.LienHe;

/**
 * Created by User name on 11/06/2017.
 */

public class AdapterLienHe extends BaseAdapter {

    Context context;
    ArrayList<LienHe>list;

    public AdapterLienHe(Context context, ArrayList<LienHe> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.listview_row, null);
        CircleImageView imgAvatar = (CircleImageView) row.findViewById(R.id.imgAvatar_list);
//        TextView tvId = (TextView) row.findViewById(R.id.tv_id);
        TextView tvName = (TextView) row.findViewById(R.id.tv_name);
        TextView tvNumber = (TextView) row.findViewById(R.id.tv_number);

        LienHe lienHe = list.get(i);
//        tvId.setText(lienHe.id + "");
        tvName.setText(lienHe.name);
        tvNumber.setText(lienHe.phone);

        Bitmap bitmapAvatar = BitmapFactory.decodeByteArray(lienHe.image, 0, lienHe.image.length);

        imgAvatar.setImageBitmap(bitmapAvatar);
        return row;

    }


}
