package me.shuza.demo_pagination.ui;

import java.util.ArrayList;

/**
 * Created by Boka on 23-Aug-17.
 */

public interface MainActivityMVP {

    interface View {

        void openAddContentDialog();

        void contentInsertedAt(ViewModel model, int position);

        void contentUpdateAt(ViewModel model, int position);

        void openContentEditDialog(int position);

        void contentRemovedAt(int position);

        void closeRefreshLayout();

        void openContent(int position);

        void contentAddByPagination(ViewModel model);
    }

    interface Presenter {
        void setView(MainActivityMVP.View view);

        void addButtonClicked();

        void loadContent(int pageNo);

        void contentRemoveAt(int position);

        void contentUpdateAt(ViewModel model, int position);

        void requestForContentEdit(int position);

        void addPullContentOnTop();

        void requestToOpenContent(int position);
    }

    interface Model {
        ArrayList<ViewModel> getDataFromJson();
    }

}
