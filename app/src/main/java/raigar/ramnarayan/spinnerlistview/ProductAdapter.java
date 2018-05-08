package raigar.ramnarayan.spinnerlistview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kriscent on 7/5/18.
 */

public class ProductAdapter extends BaseAdapter {
    private static final String TAG = ProductAdapter.class.getSimpleName();
    private ArrayList<ProductBean> mProduct;
    private Context mContext;
    private LayoutInflater mLayoutInflator;
    private ArrayAdapter<CharSequence> adapter;
    private String selVal = "";
    int pos = 0, pos1 = 0;
    ProductAdapter(Context context, ArrayList<ProductBean> product) {
        this.mContext = context;
        this.mProduct =  product;
        mLayoutInflator = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }
    @Override
    public int getCount() {
        return mProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class Holder {
        Spinner weight;
        TextView price;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView;
        final ProductAdapter.Holder holder = new ProductAdapter.Holder();

        rowView = mLayoutInflator.inflate(R.layout.item_product, null);

        holder.weight = rowView.findViewById(R.id.spn);
        holder.price = rowView.findViewById(R.id.text);


        ArrayAdapter<String> startColorsAdapter = new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_1,mProduct.get(position).getWeight());
        holder.weight.setAdapter(startColorsAdapter);
        holder.weight.setSelection(0);

        holder.weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position2, long id) {
               holder.price.setText(mProduct.get(position).getPrice().get(position2));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return rowView;
    }
}
