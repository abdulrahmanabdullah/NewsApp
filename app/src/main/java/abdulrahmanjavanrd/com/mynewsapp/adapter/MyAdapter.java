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
import java.util.List;

import abdulrahmanjavanrd.com.mynewsapp.R;
import abdulrahmanjavanrd.com.mynewsapp.model.News;

/**
 * @author Abdulrahman.A on 01/01/2018.
 */

public class MyAdapter extends BaseAdapter {

    private final String TAG = MyAdapter.class.getSimpleName();
    private Context context;
    private List<News> listOfNews = new ArrayList<>();

    public MyAdapter(Context context, List<News> listOfNews) {
        this.context = context;
        this.listOfNews = listOfNews;
    }

    @Override
    public int getCount() {
        return listOfNews.size();
    }

    @Override
    public News getItem(int position) {
        return listOfNews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.main_card, null);
            holder = new MyViewHolder();
            holder.imageView = convertView.findViewById(R.id.articleImag);
            holder.txvTitle = convertView.findViewById(R.id.txvTitle);
            holder.txvSummary = convertView.findViewById(R.id.txvSummary);
            holder.txvSection = convertView.findViewById(R.id.txvSection);
            holder.txvDate = convertView.findViewById(R.id.txvDate);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        /** Current object of {@link News} */
        News currentObject = getItem(position);
        /** Use Picasso Library to get image from url .*/
        Picasso.with(context).load(currentObject.getImage()).into(holder.imageView);
        holder.txvTitle.setText(currentObject.getTitle());
        int length = currentObject.getSummary().length();
        Log.i(TAG, "Current Length of " + getItem(position).getSection() + " = " + (int) Math.floor(length));
        String quarterArticle = divideSummaryToQuarter(currentObject.getSummary());
        holder.txvSummary.setText(quarterArticle);
        Log.i(TAG, "After divide text  = " + quarterArticle.length());
        // insert # Before any section name .
        String tag = context.getResources().getString(R.string.hash_tag) + currentObject.getSection();
        holder.txvSection.setText(tag);
        holder.txvDate.setText(currentObject.getDate());
        return convertView;
    }


    /**
     * <p>In This method i try to convert Long text to small </p>
     * I wrote some functions to help me , I hope it's true.
     * @param summary of article
     * @return small text .
     */
    private String divideSummaryToQuarter(String summary) {
        String quarterText;
        int length = (int) Math.floor(summary.length()); // to get only int numbers .
        boolean MinVal = length >= 0 && length <= 6000;  // from  0 to 6000 char
        boolean MaxVal = length >= 6000 && length <= 11000; // from  6000 to 110000 char .
        boolean OverMaxVal = length >= 11000; // if length of String > 1100, Like 12000 ... etc  .
        if (MinVal) {
            quarterText = summary.substring(0, length / 16);
        } else if (MaxVal) {
            quarterText = summary.substring(0, length / 32);
        } else if (OverMaxVal) {
            quarterText = summary.substring(0, length / 64);
        }
        // if any text summary  under 6000 char .
        else {
            quarterText = summary.substring(0, length / 2);
        }
        return quarterText + " ...";
    }


    /**
     * @param mList List of news I call this method in onLoaderRest .
     */
    public void setListOfNews(List<News> mList) {
       listOfNews.addAll(mList);
//       notifyDataSetChanged();
    }

    // ViewHolder Class .
    static class MyViewHolder {
        ImageView imageView;
        TextView txvTitle, txvSummary, txvSection, txvDate;
    }
}
