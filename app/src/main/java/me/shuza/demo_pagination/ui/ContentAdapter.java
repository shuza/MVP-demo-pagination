package me.shuza.demo_pagination.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import me.shuza.demo_pagination.R;
import me.shuza.demo_pagination.Utils.LogUtil;
import me.shuza.demo_pagination.constants.ContentConstant;

/**
 * Created by Boka on 24-Aug-17.
 */

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentItemViewHolder> {
    private ArrayList<ViewModel> contentList;
    private MainActivityMVP.Presenter presenter;
    private Context context;

    public ContentAdapter(Context context, ArrayList<ViewModel> contentList, MainActivityMVP.Presenter presenter) {
        this.context = context;
        this.contentList = contentList;
        this.presenter = presenter;
    }

    @Override
    public ContentItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_content, parent, false);
        return new ContentItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContentItemViewHolder holder, int position) {
        if (position == contentList.size() - 2) {
            int currentPageNo = contentList.size() / ContentConstant.CONTENT_IN_A_PAGE - 1;
            presenter.loadContent(currentPageNo + 1);
        }
        ViewModel model = contentList.get(position);
        holder.tvTitle.setText(model.getTitle());
        if (model.getContentType().contentEquals(ContentConstant.CONTENT_TYPE_IMAGE)) {
            Picasso.with(context)
                    .load(model.getUrl())
                    .placeholder(R.drawable.ic_dummy_photo)
                    .error(R.drawable.ic_photo_error)
                    .into(holder.ivItemPhoto);
        } else if (model.getContentType().contentEquals(ContentConstant.CONTENT_TYPE_VIDEO)) {
            holder.ivItemPhoto.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_video_dummy));
        } else if (model.getContentType().contentEquals(ContentConstant.CONTENT_TYPE_AUDIO)) {
            holder.ivItemPhoto.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_audio_dummy));
        } else {
            holder.ivItemPhoto.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_error_content_type));
        }
        holder.bind(presenter, position);
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public void removeAt(int position) {
        LogUtil.printLogMessage("shuza", "adapter_remove_at", "position:  " + position);
        LogUtil.printLogMessage("shuza", "content_list", "size:  " + contentList.size());

        contentList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, contentList.size());
    }

    public void addContentAt(ViewModel model, int position) {
        contentList.add(position, model);
        notifyItemRangeChanged(position, contentList.size());
    }

    public class ContentItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivItemPhoto;
        public TextView tvTitle;
        public Button btnEdit;
        public Button btnDelete;

        public ContentItemViewHolder(View itemView) {
            super(itemView);
            ivItemPhoto = itemView.findViewById(R.id.ivItemPhoto);
            tvTitle = itemView.findViewById(R.id.tvItemTitle);
            btnEdit = itemView.findViewById(R.id.btnItemEdit);
            btnDelete = itemView.findViewById(R.id.btnItemDelete);
        }

        public void bind(final MainActivityMVP.Presenter presenter, final int position) {
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.requestForContentEdit(position);
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.contentRemoveAt(position);
                }
            });
            ivItemPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.requestToOpenContent(position);
                }
            });
        }
    }
}
