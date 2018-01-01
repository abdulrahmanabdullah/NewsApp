package abdulrahmanjavanrd.com.mynewsapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.StringJoiner;

import abdulrahmanjavanrd.com.mynewsapp.R;
import abdulrahmanjavanrd.com.mynewsapp.model.News;

/**
 * @author  Abdulrahman.A on 01/01/2018.
 */

public class MainAdapter extends BaseAdapter {

    private final String TAG = MainAdapter.class.getSimpleName();
    private Context context ;
    private ArrayList<News> mList ;


    public MainAdapter(Context context, ArrayList<News> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public News getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null ){
            convertView = LayoutInflater.from(context).inflate(R.layout.main_card,null);
           holder  = new MyViewHolder();
           holder.imageView = convertView.findViewById(R.id.articleImag);
           holder.txvTitle = convertView.findViewById(R.id.txvTitle);
            holder.txvSummary = convertView.findViewById(R.id.txvSummary);
            holder.txvSection = convertView.findViewById(R.id.txvSection);
            holder.txvDate = convertView.findViewById(R.id.txvDate);
            holder.txvAutho = convertView.findViewById(R.id.txvAuthor);
            convertView.setTag(holder);
        }else {
            holder = (MyViewHolder) convertView.getTag();
        }
       /** Current object of {@link News} */
       News currentObject = getItem(position);
       /** Use Picasso Library to get image from url .*/
        Picasso.with(context).load(currentObject.getImage()).into(holder.imageView);
        holder.txvTitle.setText(currentObject.getTitle());
        // TODO : create regular Expression to Get some text .
        int length = currentObject.getSummary().length();
        Log.i(TAG,"Current Length = "+ length);
        holder.txvSummary.setText(currentObject.getSummary());
        String tag = addTag(currentObject.getSection());
        holder.txvSection.setText(tag);
        //TODO: Convert Date to String .
        holder.txvDate.setText(currentObject.getDate());
        holder.txvAutho.setText(currentObject.getAuthor());
        return convertView;
    }


    static class MyViewHolder {
        ImageView imageView ;
        TextView txvTitle,txvSummary ,txvSection,txvDate,txvAutho  ;
    }

    private String addTag(String str){
        StringBuilder sb = new StringBuilder();
        sb.append("# ").append(str);
        return sb.toString();
    }
}
