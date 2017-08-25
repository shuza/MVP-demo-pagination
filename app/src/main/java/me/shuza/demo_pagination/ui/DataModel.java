package me.shuza.demo_pagination.ui;

import android.content.Context;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import me.shuza.demo_pagination.R;
import me.shuza.demo_pagination.Utils.LogUtil;
import me.shuza.demo_pagination.parsers.ContentParser;

/**
 * Created by Boka on 24-Aug-17.
 */

public class DataModel implements MainActivityMVP.Model {
    private Context context;

    public DataModel(Context context) {
        this.context = context;
    }

    @Override
    public ArrayList<ViewModel> getDataFromJson() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = context.getResources().openRawResource(R.raw.data_source);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            LogUtil.printLogMessage(MainActivity.class.getSimpleName(), "get JSON data error", e.getMessage());
        }

        LogUtil.printLogMessage(MainActivity.class.getSimpleName(), "data from JSON", stringBuilder.toString());

        JsonArray objList = Json.parse(stringBuilder.toString()).asArray();
        ArrayList<ViewModel> viewModels = new ArrayList<ViewModel>();
        for (int i = 0; i < objList.size(); i++) {
            JsonObject viewModelObj = objList.get(i).asObject();
            ViewModel model = ContentParser.parseContent(viewModelObj);
            viewModels.add(model);
        }
        return viewModels;
    }
}
