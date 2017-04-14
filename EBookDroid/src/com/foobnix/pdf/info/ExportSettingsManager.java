package com.foobnix.pdf.info;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import com.foobnix.android.utils.LOG;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.widget.Toast;

public class ExportSettingsManager {
    public static final String PREFIX_BOOKMARKS_PREFERENCES = "ViewerPreferences";// DO
                                                                                  // NOT
                                                                                  // CHANGE!!!!
    public static final String PREFIX_BOOKS = "BOOKS";
    public static final String PREFIX_LASTPATH = "lastpath";
    public static final String PREFIX_PDF = "pdf";
    public static final String PREFIX_RESULTS = "search_results_1";

    public static final String RECURCIVE = "recurcive";
    private static final String PATH2 = "PATH";

    SharedPreferences lastPathSP;
    SharedPreferences booksSP;
    SharedPreferences viewerSP;
    SharedPreferences pdfSP;

    private static ExportSettingsManager instance;
    private Context c;

    private ExportSettingsManager(Context c) {
        this.c = c;
        lastPathSP = c.getSharedPreferences(PREFIX_LASTPATH, Context.MODE_PRIVATE);
        booksSP = c.getSharedPreferences(PREFIX_BOOKS, Context.MODE_PRIVATE);
        viewerSP = c.getSharedPreferences(PREFIX_BOOKMARKS_PREFERENCES, Context.MODE_PRIVATE);
        pdfSP = c.getSharedPreferences(PREFIX_PDF, Context.MODE_PRIVATE);
    }

    public boolean exportAll(File toFile) {
        if (toFile == null) {
            return false;
        }
        LOG.d("TEST", "Export all to" + toFile.getPath());

        try {
            JSONObject root = new JSONObject();

            root.put(PREFIX_PDF, exportToJSon(PREFIX_RESULTS, pdfSP, null));
            root.put(PREFIX_LASTPATH, exportToJSon(PREFIX_LASTPATH, lastPathSP, null));
            root.put(PREFIX_BOOKS, exportToJSon(PREFIX_BOOKS, booksSP, null));
            root.put(PREFIX_BOOKMARKS_PREFERENCES, exportToJSon(PREFIX_BOOKMARKS_PREFERENCES, viewerSP, null));

            String name = getSampleJsonConfigName(c, "export_all.json");
            File fileConfig = toFile;

            if (toFile.isDirectory()) {
                fileConfig = new File(toFile, name);
            }
            LOG.d("TEXT", "exoprt to " + name);

            FileWriter file = new FileWriter(fileConfig);
            LOG.d("TEXT", "exoprt to " + fileConfig.getPath());
            String string = root.toString(5);
            file.write(string);
            file.flush();
            file.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return false;
    }

    public static String getSampleJsonConfigName(Context a, String ext) {
        try {
            final String format = Settings.System.getString(a.getContentResolver(), Settings.System.DATE_FORMAT);
            String time;
            if (TextUtils.isEmpty(format)) {
                time = DateFormat.getMediumDateFormat(a).format(System.currentTimeMillis());
            } else {
                time = new SimpleDateFormat(format).format(System.currentTimeMillis());
            }

            String name = String.format("%s%s", time, ext);
            return name;
        } catch (Exception e) {
            return "pdf_reader" + ext;
        }
    }

    public boolean importAll(File file) {
        if (file == null) {
            return false;
        }
        LOG.d("TEST", "Import all from " + file.getPath());
        try {
            String json = new Scanner(file).useDelimiter("\\A").next();
            JSONObject jsonObject = new JSONObject(json);

            importFromJSon(jsonObject.optJSONObject(PREFIX_PDF), pdfSP, null);
            importFromJSon(jsonObject.optJSONObject(PREFIX_LASTPATH), lastPathSP, null);
            importFromJSon(jsonObject.optJSONObject(PREFIX_BOOKS), booksSP, null);
            importFromJSon(jsonObject.optJSONObject(PREFIX_BOOKMARKS_PREFERENCES), viewerSP, null);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return false;

    }

    public static JSONObject exportToJSon(String name, SharedPreferences sp, String exclude) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        Map<String, ?> all = sp.getAll();
        for (String key : all.keySet()) {
            if (exclude != null && key.startsWith(exclude)) {
                continue;
            }
            jsonObject.put(key, all.get(key));
        }
        return jsonObject;
    }

    public static void importFromJSon(JSONObject jsonObject, SharedPreferences sp, String exclude) throws JSONException {
        if (jsonObject == null) {
            LOG.d("TEST", "import null");
            return;
        }
        @SuppressWarnings("unchecked")
        Iterator<String> keys = jsonObject.keys();
        Editor edit = sp.edit();
        while (keys.hasNext()) {
            String name = keys.next();
            if (exclude != null && name.startsWith(exclude)) {
                continue;
            }
            Object res = jsonObject.get(name);
            if (res instanceof String) {
                edit.putString(name, (String) res);
            } else if (res instanceof Float) {
                edit.putFloat(name, (Float) res);
            } else if (res instanceof Integer) {
                edit.putInt(name, (Integer) res);
            } else if (res instanceof Boolean) {
                edit.putBoolean(name, (Boolean) res);
            }
        }
        edit.commit();
    }

    public static ExportSettingsManager getInstance(Context c) {
        if (instance == null) {
            instance = new ExportSettingsManager(c);
        }
        return instance;
    }

    public String getLastPath() {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        if (!externalStorageDirectory.exists()) {
            externalStorageDirectory = new File("/");
        }
        return lastPathSP.getString(PATH2, externalStorageDirectory.getPath());
    }

    public void saveLastPath(File file) {
        Editor edit = lastPathSP.edit();
        edit.putString(PATH2, file.getPath());
        edit.commit();
    }



}