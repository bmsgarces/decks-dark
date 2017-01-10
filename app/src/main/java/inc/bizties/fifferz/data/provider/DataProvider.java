package inc.bizties.fifferz.data.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;
import net.sqlcipher.database.SQLiteQueryBuilder;

import inc.bizties.fifferz.data.cache.Preferences;
import inc.bizties.fifferz.data.provider.tables.PlayerTable;

public class DataProvider extends ContentProvider {

    private static final String DATABASE_NAME = "database";
    private static final String TAG = "DataProvider";
    private static final int INITIAL_VERSION = 1;
    private static final int PLAYER_TABLE_MATCHES_COL_ADDED_ = 2;
    private static final int VERSION = PLAYER_TABLE_MATCHES_COL_ADDED_;

    private static final String KEY_1 = "RrUkLl5f6e";
    private static final String KEY_2 = "Jok4wGAw1V";
    private static final String DATABASE_KEY = KEY_1 + KEY_2;

    private DataProviderHelper helper;
    private String dbKey;

    @Override

    public final boolean onCreate() {
        SQLiteDatabase.loadLibs(getContext());
        helper = new DataProviderHelper(getContext());

        if (!Preferences.hasDBKeySaved(getContext())) {
            SQLiteDatabase database = helper.getWritableDatabase(DATABASE_KEY);
            dbKey = Preferences.getDBKeyAndCreateIfMissing(getContext());
            database.changePassword(dbKey);
        } else {
            dbKey = Preferences.getDBKeyAndCreateIfMissing(getContext());
        }
        return true;
    }

    public static void clear(Context context) {
        clearTableData(context, PlayerTable.Contract.CONTENT_URI);
    }

    public static void clearTableData(Context context, Uri tableUri) {
        String selection = BaseColumns._ID + " != ?";
        String[] selectionArgs = {"-1"};

        int totalRowsDeleted = context.getContentResolver().delete(tableUri, selection, selectionArgs);
    }

    @Override
    public final String getType(@NonNull Uri uri) {
        switch (DataProviderContract.URI_MATCHER.match(uri)) {

            case DataProviderContract.PLAYER:
                return PlayerTable.Contract.CONTENT_TYPE;
            case DataProviderContract.PLAYER_ID:
                return PlayerTable.Contract.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public final Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (!DataProviderContract.isValidUri(uri)) {
            throw new IllegalArgumentException("Unsupported URI for query: " + uri);
        }

        final String tableName = getTableNameFromUri(uri);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(tableName);

        switch (DataProviderContract.URI_MATCHER.match(uri)) {
            case DataProviderContract.PLAYER_ID:
                queryBuilder.appendWhere(BaseColumns._ID + "=" + uri.getLastPathSegment());
                break;
            default:
                break;
        }

        Cursor cursor = queryBuilder.query(helper.getWritableDatabase(dbKey), projection, selection, selectionArgs, null,
                null, sortOrder);
        if (getContext() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
        return cursor;
    }

    @Override
    public final Uri insert(@NonNull Uri uri, ContentValues values) {
        if (!DataProviderContract.isValidUri(uri)) {
            throw new IllegalArgumentException("Unsupported URI for insert: " + uri);
        }

        final String tableName = getTableNameFromUri(uri);
        long row = helper.getWritableDatabase(dbKey).insertWithOnConflict(tableName, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        Uri newUri = null;

        if (row > 0) {
            newUri = ContentUris.withAppendedId(DataProviderContract.getContentUriFromUri(uri), row);
            if (getContext() != null) {
                getContext().getContentResolver().notifyChange(newUri, null);
            }
        }

        return newUri;
    }

    @Override
    public final int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        if (!DataProviderContract.isValidUri(uri)) {
            throw new IllegalArgumentException("Unsupported URI for insert: " + uri);
        }

        SQLiteDatabase database = helper.getWritableDatabase(dbKey);
        final String tableName = getTableNameFromUri(uri);

        int count = 0;

        switch (DataProviderContract.URI_MATCHER.match(uri)) {
            case DataProviderContract.PLAYER:
                count = database.delete(tableName, selection, selectionArgs);
                break;
            case DataProviderContract.PLAYER_ID:
                String id = uri.getLastPathSegment();
                count = database.delete(tableName,
                        BaseColumns._ID + " = " + id
                                + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                break;
        }

        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }

    @Override
    public final int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (!DataProviderContract.isValidUri(uri)) {
            throw new IllegalArgumentException("Unsupported URI for insert: " + uri);
        }

        SQLiteDatabase database = helper.getWritableDatabase(dbKey);
        final String tableName = getTableNameFromUri(uri);

        int count = 0;

        switch (DataProviderContract.URI_MATCHER.match(uri)) {
            case DataProviderContract.PLAYER:
                count = database.update(tableName, values, selection, selectionArgs);
                break;
            case DataProviderContract.PLAYER_ID:
                count = database.update(tableName, values,
                        BaseColumns._ID + " = " + uri.getLastPathSegment()
                                + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                break;
        }

        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return count;
    }

    private String getTableNameFromUri(Uri uri) {
        switch (DataProviderContract.URI_MATCHER.match(uri)) {
            case DataProviderContract.PLAYER:
            case DataProviderContract.PLAYER_ID:
                return PlayerTable.Contract.TABLE_NAME;
            default:
                throw new IllegalArgumentException("Unsupported Uri : " + uri);
        }
    }

    static class DataProviderHelper extends SQLiteOpenHelper {

        //private static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";
        private static final String ALTER_TABLE_ADD_COLUMN = "ALTER TABLE %s ADD COLUMN %s INTEGER";

        DataProviderHelper(Context context) {
            super(context, DATABASE_NAME, null, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(PlayerTable.Contract.CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion < PLAYER_TABLE_MATCHES_COL_ADDED_) {
                db.execSQL(String.format(ALTER_TABLE_ADD_COLUMN,
                        PlayerTable.Contract.TABLE_NAME,
                        PlayerTable.Contract.NUMBER_OF_MATCHES));
            }
        }
    }
}
