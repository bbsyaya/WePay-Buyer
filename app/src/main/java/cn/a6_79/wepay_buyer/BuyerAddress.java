package cn.a6_79.wepay_buyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.a6_79.wepay_buyer.NetPack.HttpThreadTask;

public class BuyerAddress extends AppCompatActivity {
    @Override
    protected void onResume() {
        super.onResume();
        initInterFace();
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        initInterFace();
//    }

    private void initInterFace () {
        setContentView(R.layout.buyer_address);
        HttpThreadTask task = API.getAddress(getAddressListener);
        if (task != null)
            task.execute();

        ImageView mReturnImage = (ImageView)findViewById(R.id.return_personal_info);
        mReturnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button mEditBuyerAddress = (Button)findViewById(R.id.edit_buyer_address);
        mEditBuyerAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView recipientNameView = (TextView)findViewById(R.id.recipient_name);
                String realName = recipientNameView.getText().toString();
                TextView addressView = (TextView)findViewById(R.id.address);
                String address = addressView.getText().toString();

                Intent intent = new Intent(getApplicationContext(), EditBuyerAddress.class);
                intent.putExtra("recipient_name", realName);
                intent.putExtra("address", address);
                startActivity(intent);
            }
        });
    }

    ResponseListener getAddressListener = new ResponseListener() {
        @Override
        public void callback(String response) throws JSONException{
            JSONObject jsonObject = API.ResponseShow(getApplicationContext(), response);
            JSONObject body = jsonObject.getJSONObject("body");
            if (body != null) {
                String realName = body.getString("real_name");
                String address = body.getString("address");

                TextView recipientNameView = (TextView)findViewById(R.id.recipient_name);
                if (!realName.equals("null") && !realName.equals("")) {
                    recipientNameView.setText(realName);
                }
                else {
                    recipientNameView.setHint("请添加收货人");
                }

                TextView addressView = (TextView)findViewById(R.id.address);
                if (!address.equals("null") && !address.equals("")) {
                    addressView.setText(address);
                }
                else {
                    addressView.setHint("请添加收货地址");
                }

            }
        }
    };

}
