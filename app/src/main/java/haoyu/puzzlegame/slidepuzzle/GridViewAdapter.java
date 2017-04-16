package haoyu.puzzlegame.slidepuzzle;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by haoyu on 2/20/17.
 */

public class GridViewAdapter extends ArrayAdapter<GridItem> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<GridItem> mGridData = new ArrayList<>();

    public GridViewAdapter(Context context, int resource, ArrayList<GridItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.layoutResourceId = resource;
        this.mGridData = objects;
    }

    public void setGridData(ArrayList<GridItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            //(parent.getContext()
            convertView = LayoutInflater.from(parent.getContext()).inflate(layoutResourceId, parent, false);
            //convertView = inflater.inflate();
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.txt_item);
            holder.imageView = (ImageView) convertView.findViewById(R.id.img_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GridItem item = mGridData.get(position);
        holder.textView.setText(item.getTitle());
        holder.imageView.setImageResource(item.getImage());
        return convertView;
    }

    private static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
