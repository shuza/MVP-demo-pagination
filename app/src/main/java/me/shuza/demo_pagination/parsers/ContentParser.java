package me.shuza.demo_pagination.parsers;

import com.eclipsesource.json.JsonObject;

import me.shuza.demo_pagination.Utils.JsonUtil;
import me.shuza.demo_pagination.ui.ViewModel;
import me.shuza.demo_pagination.constants.ParamsConstant;

/**
 * Created by Boka on 24-Aug-17.
 */

public class ContentParser {

    public static ViewModel parseContent(JsonObject obj) {
        ViewModel model = new ViewModel();
        model.setContentType(JsonUtil.getStringFromJson(obj, ParamsConstant.PARAMS_CONTENT_TYPE));
        model.setUrl(JsonUtil.getStringFromJson(obj, ParamsConstant.PARAMS_URL));
        model.setTitle(JsonUtil.getStringFromJson(obj, ParamsConstant.PARAMS_TITLE));
        return model;
    }

}
