package me.shuza.demo_pagination.ui;

import java.util.ArrayList;
import java.util.Random;

import me.shuza.demo_pagination.Utils.LogUtil;
import me.shuza.demo_pagination.constants.ContentConstant;

/**
 * Created by Boka on 24-Aug-17.
 */

public class MainActivityPresenter implements MainActivityMVP.Presenter {
    private MainActivityMVP.View view;
    private MainActivityMVP.Model model;

    public MainActivityPresenter(MainActivityMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(MainActivityMVP.View view) {
        this.view = view;
    }

    @Override
    public void loadContent(int pageNo) {
        LogUtil.printLogMessage(MainActivityPresenter.class.getSimpleName(), "load_content", "page_no:  " + pageNo);
        ArrayList<ViewModel> contentList = model.getDataFromJson();
        int startItemNo = ContentConstant.CONTENT_IN_A_PAGE * pageNo;
        for (int i = 0; i < ContentConstant.CONTENT_IN_A_PAGE && startItemNo < ContentConstant.CONTENT_IN_A_PAGE
                * ContentConstant.CONTENT_PAGE_SIZE; i++) {
            //view.contentInsertedAt(contentList.get(startItemNo), startItemNo);
            view.contentAddByPagination(contentList.get(startItemNo));
            startItemNo++;
        }
    }

    @Override
    public void contentRemoveAt(int position) {
        view.contentRemovedAt(position);
    }

    @Override
    public void contentUpdateAt(ViewModel model, int position) {
        view.contentUpdateAt(model, position);
    }

    @Override
    public void addPullContentOnTop() {
        ViewModel model = new ViewModel();
        Random random = new Random();
        int type = random.nextInt(3);
        if (type == 0) {
            model.setContentType(ContentConstant.CONTENT_TYPE_IMAGE);
            model.setTitle("random - " + ContentConstant.CONTENT_TYPE_IMAGE);
            model.setUrl("https://www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2016/08/ac-lloyd.jpg?itok=bb72IeLf");
        } else if (type == 1) {
            model.setContentType(ContentConstant.CONTENT_TYPE_AUDIO);
            model.setTitle("random - " + ContentConstant.CONTENT_TYPE_AUDIO);
            model.setUrl("http://programmerguru.com/android-tutorial/wp-content/uploads/2013/04/hosannatelugu.mp3");
        } else {
            model.setContentType(ContentConstant.CONTENT_TYPE_VIDEO);
            model.setTitle("random - " + ContentConstant.CONTENT_TYPE_VIDEO);
            model.setUrl("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp");
        }
        view.contentInsertedAt(model, 0);
        view.closeRefreshLayout();
    }

    @Override
    public void requestToOpenContent(int position) {
        view.openContent(position);
    }

    @Override
    public void requestForContentEdit(int position) {
        view.openContentEditDialog(position);
    }

    @Override
    public void addButtonClicked() {
        view.openAddContentDialog();
    }
}
