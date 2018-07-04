package com.andygu.sample_02.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.andygu.sample_02.R;

public class IconAdapter extends BaseAdapter{

  private Context context;
  private String[] func;
  private int[] icons ;

  public IconAdapter(Context context,String[] func,int[] icons) {
    this.context = context;
    this.func = func;
    this.icons = icons;
  }

  @Override public int getCount() {
    if (icons!=null) {
      return icons.length;
    }else {
      return 0;
    }
  }

  @Override public Object getItem(int position) {
    return func[position];
  }

  @Override public long getItemId(int position) {
    return icons[position];
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    View row = convertView;
    if (row == null) {
      /**
            * 外部取得Layout到View方法,
            * https://stackoverflow.com/questions/7803771/call-to-getlayoutinflater-in-places-not-in-activity
            */
      LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
      row = inflater.inflate(R.layout.item_row ,null);
      ImageView image = row.findViewById(R.id.item_image);
      TextView text = row.findViewById(R.id.item_text);
      image.setImageResource(icons[position]);
      text.setText(func[position]);
    }
    return row;
  }
}
