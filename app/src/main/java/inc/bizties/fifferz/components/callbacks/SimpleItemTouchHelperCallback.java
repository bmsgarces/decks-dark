package inc.bizties.fifferz.components.callbacks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import inc.bizties.fifferz.R;
import inc.bizties.fifferz.components.listeners.PlayerInteractionListener;

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.SimpleCallback {

    private static final String DIM_GRAY_COLOR = "#696969";
    private static final String YELLOW_COLOR = "#FFD500";
    private static final String GREEN_COLOR = "#388E3C";

    private Context context;
    private PlayerInteractionListener listener;
    private static Paint paint = new Paint();

    private SimpleItemTouchHelperCallback(Context context, int dragDirs, int swipeDirs, PlayerInteractionListener listener) {
        super(dragDirs, swipeDirs);
        this.context = context;
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();

        if (direction == ItemTouchHelper.RIGHT) {
            listener.onAddPoints(position);
        } else {
            listener.onAddMatch(position);
        }
    }

    @Override
    public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        Bitmap icon;
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

            View itemView = viewHolder.itemView;
            float span = itemView.getRight() * .12f;
            float height = (float) itemView.getBottom() - (float) itemView.getTop();
            float width = height / 3;

            if (dX > 0) {
                int color = (dX > span) ? Color.parseColor(GREEN_COLOR) : Color.parseColor(DIM_GRAY_COLOR);
                paint.setColor(color);
                RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                canvas.drawRect(background, paint);
                icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_add_white);
                RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                canvas.drawBitmap(icon, null, icon_dest, paint);
            } else {
                int color = (Math.abs(dX) > span) ? Color.parseColor(YELLOW_COLOR) : Color.parseColor(DIM_GRAY_COLOR);
                paint.setColor(color);
                RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                canvas.drawRect(background, paint);
                icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_exposure_plus_1_white);
                RectF icon_dest = new RectF((float) itemView.getRight() - 2 * width, (float) itemView.getTop() + width, (float) itemView.getRight() - width, (float) itemView.getBottom() - width);
                canvas.drawBitmap(icon, null, icon_dest, paint);
            }
        }
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return .12f;
    }

    public static class Builder {

        private Context context;
        private PlayerInteractionListener listener;
        private int dragDirs;
        private int swipeDirs;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setDragDirs(int dragDirs) {
            this.dragDirs = dragDirs;
            return this;
        }

        public Builder setSwipeDirs(int swipeDirs) {
            this.swipeDirs = swipeDirs;
            return this;
        }

        public Builder setListener(PlayerInteractionListener listener) {
            this.listener = listener;
            return this;
        }

        public SimpleItemTouchHelperCallback build() {
            return new SimpleItemTouchHelperCallback(context, dragDirs, swipeDirs, listener);
        }
    }
}
