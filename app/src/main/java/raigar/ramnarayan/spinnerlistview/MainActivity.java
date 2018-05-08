package raigar.ramnarayan.spinnerlistview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context mContext;
    private ListView mListView;
    private ArrayList<ProductBean> productBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mListView = findViewById(R.id.list);
        productBeans = new ArrayList<>();
        productWebService();
    }

    /*
    Fees web service to get children fees details */

    private void productWebService() {
        Map<String, String> params = new HashMap<String, String>();

        params.put("catid", "2");

        JSONObject parameters = new JSONObject(params);

        Log.v(TAG, " Fees Web Service Parameters " + parameters.toString());

        JsonObjectRequest mJsonObjReq = new JsonObjectRequest(Request.Method.POST,
                "http://192.168.0.121/store/authen/api/api_products.php", parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v(TAG, " Fees Web Service Response " + response.toString());

                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        JSONArray jsonArray1 = jsonObject.getJSONArray("data");


                        ProductBean productBean = new ProductBean();
                        ArrayList<String> weight = new ArrayList<>();
                        ArrayList<String> price = new ArrayList<>();


                        for (int j = 0; j < jsonArray1.length(); j++) {
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                            weight.add(jsonObject1.getString("weight"));
                            price.add(jsonObject1.getString("price"));
                        }
                        productBean.setPrice(price);
                        productBean.setWeight(weight);
                        productBeans.add(productBean);
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "Json Format wrong " + e.getMessage());
                    Toast.makeText(mContext, "Working on updates, please try again later", Toast.LENGTH_LONG).show();
                }

                ProductAdapter adapter = new ProductAdapter(mContext, productBeans);
                mListView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Log.e(TAG, error.getMessage());
                    //This indicates that the request has either time out or there is no connection
                    Toast.makeText(mContext, "Internet connection is too slow to handle request", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Log.e(TAG, error.getMessage());
                    // Error indicating that there was an Authentication Failure while performing the request
                } else if (error instanceof ServerError) {
                    Log.e(TAG, error.getMessage());
                    //Indicates that the server responded with a error response
                    Toast.makeText(mContext, "Server error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Log.e(TAG, error.getMessage());
                    //Indicates that there was network error while performing the request
                    Toast.makeText(mContext, "Network error", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Log.e(TAG, error.getMessage());
                    // Indicates that the server response could not be parsed
                    Toast.makeText(mContext, "Network error", Toast.LENGTH_SHORT).show();
                } else if (error != null) {
                    Log.e(TAG, error.getMessage());
                    Toast.makeText(mContext, " Server error, please try again later", Toast.LENGTH_LONG).show();
                }

            }
        });

            /* To increase response time of volley */
       // mJsonObjReq.setRetryPolicy(new DefaultRetryPolicy(300000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(mContext).add(mJsonObjReq);
    }

    /* response */
    /* {
  "success": "true",
  "data": [
    {
      "pro_id": "p101",
      "pro_name": "Apple",
      "pro_img": "asdadd.jpg",
      "pro_description": "df f sdf sfd sdf sf sdf f",
      "pro_cat": "2",
      "data": [
        {
          "price": "100",
          "weight": "200 gm",
          "vid": "1"
        },
        {
          "price": "200",
          "weight": "500 gm",
          "vid": "2"
        },
        {
          "price": "500",
          "weight": "1 kg",
          "vid": "3"
        }
      ]
    },
    {
      "pro_id": "p102",
      "pro_name": "sweet lime",
      "pro_img": "hhkkjkkjh.jpg",
      "pro_description": " jkgf jdgjdhsjhgfsjdhgsdjf gjgj",
      "pro_cat": "2",
      "data": [
        {
          "price": "50",
          "weight": "300 gm",
          "vid": "4"
        },
        {
          "price": "70",
          "weight": "400 gm",
          "vid": "5"
        },
        {
          "price": "90",
          "weight": "600 gm",
          "vid": "6"
        }
      ]
    }
  ]
}

*/

}
