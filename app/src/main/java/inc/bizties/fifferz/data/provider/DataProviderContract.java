package inc.bizties.fifferz.data.provider;

import android.content.UriMatcher;
import android.net.Uri;

import inc.bizties.fifferz.core.FifferzConfig;
import inc.bizties.fifferz.data.provider.tables.PlayerTable;

public class DataProviderContract {

    private static final String AUTHORITY = FifferzConfig.INSTANCE.getAppConfig().getPackageName();

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String CONTENT_BASE = "inc.bizties";

    static final UriMatcher URI_MATCHER;

    public static final int PLAYER = 0;
    public static final int PLAYER_ID = 1;

    private static final int MAX_URI_ID = 1;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

        URI_MATCHER.addURI(AUTHORITY, PlayerTable.Contract.TABLE_NAME, PLAYER);
        URI_MATCHER.addURI(AUTHORITY, PlayerTable.Contract.TABLE_NAME + "/#", PLAYER_ID);
    }

    public static boolean isValidUri(Uri uri) {
        final int result = DataProviderContract.URI_MATCHER.match(uri);
        return (result >= 0 && result <= MAX_URI_ID);
    }

    public static Uri getContentUriFromUri(Uri uri) {
        switch (DataProviderContract.URI_MATCHER.match(uri)) {
            case DataProviderContract.PLAYER:
                return PlayerTable.Contract.CONTENT_URI;

            default:
                throw new IllegalArgumentException("Unsupported Uri : " + uri);
        }
    }

    private DataProviderContract() {
    }
}
