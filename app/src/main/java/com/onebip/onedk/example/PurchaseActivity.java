package com.onebip.onedk.example;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.onebip.onedk.OneDk;
import com.onebip.onedk.PurchasableItem;

import java.util.ArrayList;
import java.util.List;

public class PurchaseActivity extends Activity {

    private static final int REQUEST_CODE_PURCHASE = 100;

    private ListView mListView;

    private ProgressBar mProgressBar;

    private List<PurchasableItem> mPurchasableItems;

    private PurchasableItemsAdapter mPurchasableItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        mListView = (ListView) findViewById(R.id.listview);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        mPurchasableItems = new ArrayList<>();
        mPurchasableItemsAdapter = new PurchasableItemsAdapter();
        mListView.setAdapter(mPurchasableItemsAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startPurchase(mPurchasableItems.get(position));
            }
        });
        loadPurchasableItems();
    }

    private void loadPurchasableItems() {
        OneDk.getInstance().loadPurchasableItems(new OneDk.PurchasableItemsCallback() {
            @Override
            public void onCompleted(final List<PurchasableItem> purchasableItems) {
                mProgressBar.setVisibility(View.GONE);
                mPurchasableItems = purchasableItems;
                mPurchasableItemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError() {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(PurchaseActivity.this, "Error while loading Purchasable Items", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void startPurchase(PurchasableItem item) {
        OneDk.getInstance().startPurchase(PurchaseActivity.this, item, REQUEST_CODE_PURCHASE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PURCHASE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Purchase completed", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Purchase failed or cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class PurchasableItemsAdapter extends BaseAdapter {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.purchasable_item, parent);
            }
            TextView tvName = (TextView) convertView.findViewById(R.id.item_name);
            TextView tvDescription = (TextView) convertView.findViewById(R.id.item_description);
            TextView tvId = (TextView) convertView.findViewById(R.id.item_id);
            PurchasableItem item = mPurchasableItems.get(position);
            tvName.setText(item.getName());
            tvDescription.setText(item.getDescription());
            tvId.setText(item.getId());
            return convertView;
        }

        @Override
        public int getCount() {
            return mPurchasableItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mPurchasableItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

    }

}
