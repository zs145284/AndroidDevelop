package com.rn.netdetail.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rn.netdetail.R;
import com.rn.netdetail.db.ObjectBoxNetEntity;
import com.rn.netdetail.db.ObjectBoxUtils;

import java.util.Collections;
import java.util.List;

public class NetDetailListActivity extends AppCompatActivity {

    private NetDetailListAdapter netDetailListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_detail_list);
        setTitle("网络请求列表");
        findViewById(R.id.delete_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectBoxUtils.getListBox().removeAll();
                netDetailListAdapter.deleteAll();
            }
        });
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL));
        netDetailListAdapter = new NetDetailListAdapter();
        recyclerView.setAdapter(netDetailListAdapter);
    }


    private static class NetDetailListAdapter extends RecyclerView.Adapter<NetDetailListAdapter.ViewHolder> {

        private List<ObjectBoxNetEntity> entity = ObjectBoxUtils.getListBox().getAll();

        NetDetailListAdapter() {
            Collections.reverse(entity);
        }

        void deleteAll() {
            entity.clear();
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_net_detail, parent, false));
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final ObjectBoxNetEntity objectBoxNetEntity = entity.get(position);
            holder.url.setText(objectBoxNetEntity.url);
            holder.content.setText(objectBoxNetEntity.content);
            holder.parameter.setVisibility(TextUtils.equals(objectBoxNetEntity.method, "get") ? View.GONE : View.VISIBLE);
            holder.parameter.setText(TextUtils.isEmpty(objectBoxNetEntity.parameter) ? "{}" : objectBoxNetEntity.parameter);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), NetDetailActivity.class);
                    intent.putExtra(NetDetailActivity.ID, objectBoxNetEntity.id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return entity.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private AppCompatTextView url;
            private AppCompatTextView content;
            private AppCompatTextView parameter;

            ViewHolder(View itemView) {
                super(itemView);
                url = (AppCompatTextView) itemView.findViewById(R.id.url);
                content = (AppCompatTextView) itemView.findViewById(R.id.content);
                parameter = (AppCompatTextView) itemView.findViewById(R.id.parameter);
            }
        }
    }
}
