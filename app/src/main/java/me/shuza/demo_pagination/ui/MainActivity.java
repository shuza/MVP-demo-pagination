package me.shuza.demo_pagination.ui;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import me.shuza.demo_pagination.R;
import me.shuza.demo_pagination.Utils.LogUtil;
import me.shuza.demo_pagination.root.App;

public class MainActivity extends AppCompatActivity implements MainActivityMVP.View {
    private SwipeRefreshLayout swipePullToRefresh;
    private RecyclerView rvContentList;
    private Button btnAdd;

    private ArrayList<ViewModel> contentList;
    private ContentAdapter adapter;

    @Inject
    public MainActivityMVP.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((App) getApplication()).getComponent().inject(this);

        setContentView(R.layout.activity_main);
        swipePullToRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        rvContentList = (RecyclerView) findViewById(R.id.rvContentList);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        contentList = new ArrayList<ViewModel>();
        adapter = new ContentAdapter(MainActivity.this, contentList, presenter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvContentList.setLayoutManager(linearLayoutManager);
        rvContentList.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addButtonClicked();
            }
        });

        swipePullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipePullToRefresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.addPullContentOnTop();
                    }
                }, 2 * 1000);
            }
        });
    }

    @Override
    public void contentInsertedAt(ViewModel model, int position) {
        LogUtil.printLogMessage(MainActivity.class.getSimpleName(), "content_inserted_at", "position:  " + position);
        LogUtil.printLogMessage(MainActivity.class.getSimpleName(), "content_inserted_at", "content_list size:  " + contentList.size());
        adapter.addContentAt(model, position);
        if (position == 0) {
            rvContentList.scrollToPosition(0);
        }
    }

    @Override
    public void contentAddByPagination(ViewModel model) {
        contentList.add(model);
    }

    @Override
    public void contentUpdateAt(ViewModel model, int position) {
        contentList.set(position, model);
        adapter.notifyItemChanged(position);
        rvContentList.scrollToPosition(position);
    }

    @Override
    public void contentRemovedAt(int position) {
        adapter.removeAt(position);
    }

    @Override
    public void openAddContentDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.dialog_add_content);
        dialog.setTitle(getResources().getString(R.string.add_content));
        dialog.setCancelable(false);

        final EditText etTitle = dialog.findViewById(R.id.etAddContentTitle);
        final EditText etUrl = dialog.findViewById(R.id.etAddContentUrl);
        Button btnSave = dialog.findViewById(R.id.btnAddContentSave);
        Button btnCancel = dialog.findViewById(R.id.btnAddContentCancel);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewModel model = new ViewModel();
                model.setTitle(etTitle.getText().toString().trim());
                model.setUrl(etUrl.getText().toString().trim());
                if (model.getTitle().isEmpty() || model.getUrl().isEmpty()) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.invalid_data), Toast.LENGTH_SHORT).show();
                    return;
                }
                contentInsertedAt(model, 0);
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void openContentEditDialog(final int position) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setTitle(getResources().getString(R.string.update_content));
        dialog.setContentView(R.layout.dialog_add_content);
        dialog.setCancelable(false);

        final EditText etTitle = dialog.findViewById(R.id.etAddContentTitle);
        final EditText etUrl = dialog.findViewById(R.id.etAddContentUrl);
        Button btnSave = dialog.findViewById(R.id.btnAddContentSave);
        Button btnCancel = dialog.findViewById(R.id.btnAddContentCancel);

        etTitle.setText(contentList.get(position).getTitle());
        etUrl.setText(contentList.get(position).getUrl());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewModel model = new ViewModel();
                model.setTitle(etTitle.getText().toString().trim());
                model.setUrl(etUrl.getText().toString().trim());
                if (model.getTitle().isEmpty() || model.getUrl().isEmpty()) {
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.invalid_data), Toast.LENGTH_SHORT).show();
                    return;
                }
                contentUpdateAt(model, position);
                dialog.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void closeRefreshLayout() {
        if (swipePullToRefresh.isRefreshing()) {
            swipePullToRefresh.setRefreshing(false);
        }
    }

    @Override
    public void openContent(int position) {
        ViewModel model = contentList.get(position);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(model.getUrl()));
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.setView(this);
        presenter.loadContent(0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        contentList.clear();
        adapter.notifyDataSetChanged();
        closeRefreshLayout();
    }
}
