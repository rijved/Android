package simple.musicgenie;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


/**
 * Create Local Cache Trending (temp)
 */
public class DbHelper extends SQLiteOpenHelper {

    private static Context context;
    private static DbHelper mInstance;
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "musicegenieDB";
    private static final String TABLE_TRENDING = "trendingTable";
    private static final String TABLE_RESULTS = "resultsTable";
    private static final String COL_TITLE = "TITLE";
    private static final String COL_TRACK_DURATION = "TRACK_DURATION";
    private static final String COL_UPLOADED_BY = "UPLOADED_BY";
    private static final String COL_THUMB_URL = "THUMB_URL";
    private static final String COL_VIDEO_ID = "VIDEO_ID";
    private static final String COL_TIME_SINCE_UPLOADED = "TIME_SINCE_UPLOADED";
    private static final String COL_USER_VIEWS = "USER_VIEWS";
    private static final String COL_TYPE = "TYPE";

    //String title, String trackDuration, String uploadedBy,
    // String thumbnail_url, String video_id, String timeSinceUploaded, String userViews, String type
    // create table trending ()

    private static final String CREATE_TRENDING_TABLE = "CREATE TABLE " + TABLE_TRENDING + "( " +
            "TEXT " + COL_TITLE + "," +
            "TEXT " + COL_TRACK_DURATION + "," +
            "TEXT " + COL_UPLOADED_BY + "," +
            "TEXT " + COL_THUMB_URL + "," +
            "TEXT " + COL_VIDEO_ID + "," +
            "TEXT " + COL_TIME_SINCE_UPLOADED + "," +
            "TEXT " + COL_USER_VIEWS + "," +
            "TEXT " + COL_TRACK_DURATION + "," +
            "TEXT " + COL_TYPE + "" +
            ")";

    private static final String CREATE_RESULTS_TABLE = "CREATE TABLE " + TABLE_RESULTS + " ( " +
            "TEXT " + COL_TITLE + "," +
            "TEXT " + COL_TRACK_DURATION + "," +
            "TEXT " + COL_UPLOADED_BY + "," +
            "TEXT " + COL_THUMB_URL + "," +
            "TEXT " + COL_VIDEO_ID + "," +
            "TEXT " + COL_TIME_SINCE_UPLOADED + "," +
            "TEXT " + COL_USER_VIEWS + "," +
            "TEXT " + COL_TRACK_DURATION + ")";

    private static final String DROP_TRENDING_TABLE = "DROP TABLE " + TABLE_TRENDING;
    private static final String DROP_RESULTS_TABLE = "DROP TABLE " + TABLE_RESULTS;

    private DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public static DbHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DbHelper(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TRENDING_TABLE);
        sqLiteDatabase.execSQL(CREATE_RESULTS_TABLE);

    }

    /**
     * @param sqLiteDatabase database object
     * @param oldVersion     older version of database
     * @param newVersion     newer version of database
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL(DROP_RESULTS_TABLE);
        sqLiteDatabase.execSQL(DROP_TRENDING_TABLE);
        onCreate(sqLiteDatabase);

    }

    private ContentValues getCVObject(ItemModel data) {

        ContentValues temp = new ContentValues();
        temp.put(COL_TITLE, data.Title);
        temp.put(COL_TIME_SINCE_UPLOADED, data.TimeSinceUploaded);
        temp.put(COL_THUMB_URL, data.Thumbnail_url);
        temp.put(COL_TYPE, data.type);
        temp.put(COL_TRACK_DURATION, data.TrackDuration);
        temp.put(COL_UPLOADED_BY, data.UploadedBy);
        temp.put(COL_VIDEO_ID, data.Video_id);
        temp.put(COL_USER_VIEWS, data.UserViews);

        return temp;
    }

    public void addTrendingList(ArrayList<SectionModel> list) {

        SQLiteDatabase database = getWritableDatabase();
        ContentValues values;

        for (int i = 0; i < list.size(); i++) {

            ArrayList<ItemModel> itemModels = list.get(i).getList();

            for (int j = 0; j < itemModels.size(); j++) {

                values = getCVObject(itemModels.get(j));
                values.put(COL_TYPE, list.get(i).sectionTitle);
                long id = database.insert(TABLE_TRENDING, null, values);

                if (id < 0) {
                    Log.d("DBHelper (Trending)", "Cannot Add Row");
                } else {
                    Log.d("DBHelper (Trending)", "Added Successfully ");
                }
            }
        }


    }



    public void addResultsList(ArrayList<SectionModel> list) {

        SQLiteDatabase database = getWritableDatabase();
        ContentValues values;

        for (int i = 0; i < list.size(); i++) {

            ArrayList<ItemModel> itemModels = list.get(i).getList();

            for (int j = 0; j < itemModels.size(); j++) {

                values = getCVObject(itemModels.get(j));
                long id = database.insert(TABLE_RESULTS, null, values);

                if (id < 0) {
                    Log.d("DBHelper (Results)", "Cannot Add Row");
                } else {
                    Log.d("DBHelper (Results)", "Added Successfully ");
                }
            }
        }


    }

}
