package me.shuza.demo_pagination.Utils;

import com.eclipsesource.json.JsonObject;

/**
 * Created by Boka on 24-Aug-17.
 */

public class JsonUtil {

    public static String getStringFromJson(JsonObject obj, String key) {
        try {
            return obj.getString(key, "");
        } catch (Exception e) {
            LogUtil.printLogMessage(JsonUtil.class.getSimpleName(), "get_string_from_json_error", e.getMessage());
            return "";
        }
    }

    public static int getIntFromJson(JsonObject obj, String key) {
        try {
            return obj.getInt(key, 0);
        } catch (Exception e) {
            LogUtil.printLogMessage(JsonUtil.class.getSimpleName(), "get_int_from_json_error", e.getMessage());
            return 0;
        }
    }

}
