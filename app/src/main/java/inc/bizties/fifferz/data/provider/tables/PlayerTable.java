package inc.bizties.fifferz.data.provider.tables;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import inc.bizties.fifferz.data.models.Player;
import inc.bizties.fifferz.data.provider.DataProviderContract;

public class PlayerTable extends BaseTable<PlayerTable.Contract> {

    public PlayerTable() {
        super(new Contract());
    }

    @Nullable
    public Player getPlayer(Context context, String name) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }

        if (name == null) {
            throw new IllegalArgumentException("name must not be null");
        }

        String selection = Contract.NAME + " = ?";
        String[] selectionArgs = {name};
        Player player = null;

        final ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(Contract.CONTENT_URI, null, selection, selectionArgs, null);

        if (cursor != null && cursor.moveToFirst()) {
            player = getContract().fromCursor(cursor);
        }

        if (cursor != null) {
            cursor.close();
        }
        return player;
    }

    public List<Player> getPlayers(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }

        String orderBy = Contract.SCORE + " desc";
        List<Player> players = new ArrayList<>();

        final ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(Contract.CONTENT_URI, null, null, null, orderBy);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                players.add(getContract().fromCursor(cursor));
                cursor.moveToNext();
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        return players;
    }

    public int addPlayer(Context context, Player player) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }

        if (player == null) {
            throw new IllegalArgumentException("player must not be null");
        }

        String[] projection = new String[]{Contract.NAME};
        String selection = Contract._ID + " = ? or " + Contract.NAME + " = ?";
        String[] selectionArgs = {Integer.toString(player.getId()), player.getName()};

        final ContentResolver contentResolver = context.getContentResolver();
        ContentValues values = getContract().toContentValues(player);

        final Cursor cursor = contentResolver.query(Contract.CONTENT_URI, projection, selection, selectionArgs, null);
        boolean hasRecord = false;
        if (cursor != null) {
            hasRecord = (cursor.getCount() > 0);

            cursor.close();
        }
        int recordsModified;

        if (hasRecord) {
            recordsModified = contentResolver.update(Contract.CONTENT_URI, values, selection, selectionArgs);
        } else {
            final Uri uri = contentResolver.insert(Contract.CONTENT_URI, values);
            recordsModified = (uri != null ? 1 : 0);
        }

        return recordsModified;
    }

    public int removePlayer(Context context, Player player) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }

        if (player == null) {
            throw new IllegalArgumentException("player must not be null");
        }

        final ContentResolver contentResolver = context.getContentResolver();

        String selection = Contract.NAME + " LIKE '%" + player.getName() + "%'";

        return contentResolver.delete(Contract.CONTENT_URI, selection,null);
    }

    public static final class Contract extends BaseContract {
        public static final String TABLE_NAME = "player";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(DataProviderContract.CONTENT_URI, TABLE_NAME);
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + '/' + DataProviderContract.CONTENT_BASE + "."
                + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + '/' + DataProviderContract.CONTENT_BASE + "."
                + TABLE_NAME;

        public static final String NAME = "name";
        public static final String SCORE = "score";
        public static final String NUMBER_OF_MATCHES = "number_of_matches";

        public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" //
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " //
                + NAME + " TEXT NOT NULL, " //
                + SCORE + " INTEGER, " //
                + NUMBER_OF_MATCHES + " INTEGER)";

        public ContentValues toContentValues(Player player) {
            ContentValues contentValues = new ContentValues();

            contentValues.put(NAME, player.getName());
            contentValues.put(SCORE, player.getScore());
            contentValues.put(NUMBER_OF_MATCHES, player.getNumberOfMatches());

            return contentValues;
        }

        public Player fromCursor(Cursor cursor) {
            int id = cursor.getInt(getIndex(cursor, _ID));
            String name = cursor.getString(getIndex(cursor, NAME));
            int score = cursor.getInt(getIndex(cursor, SCORE));
            int numberOfMatches = cursor.getInt(getIndex(cursor, NUMBER_OF_MATCHES));

            return new Player(id, name, score, numberOfMatches);
        }
    }
}
