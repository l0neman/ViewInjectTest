package com.runing.example.sample_simpleviewinject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ViewInject;
import com.runing.example.simpleviewinject_api.SimpleViewInjector;

public final class MainActivity extends AppCompatActivity {

    @ViewInject(R.id.tv_bottom)
    TextView mTextView;

    @ViewInject(R.id.btn_bottom)
    Button mButton;

    @ViewInject(R.id.lv_content)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleViewInjector.inject(this);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTextView.setText(getResources().getText(R.string.app_name));
            }
        });
        mListView.setAdapter(new MyAdapter());
    }

    static class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(parent.getContext(), R.layout.item_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText(String.valueOf(position));
            return convertView;
        }

        static class ViewHolder {

            private View itemView;

            @ViewInject(R.id.tv_item)
            TextView textView;

            ViewHolder(View itemView) {
                SimpleViewInjector.inject(this, itemView);
            }
        }
    }
}
