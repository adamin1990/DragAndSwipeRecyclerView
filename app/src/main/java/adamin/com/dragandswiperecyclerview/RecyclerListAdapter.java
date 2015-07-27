package adamin.com.dragandswiperecyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import adamin.com.dragandswiperecyclerview.helper.ItemTouchHelperAdapter;
import adamin.com.dragandswiperecyclerview.helper.ItemTouchHelperViewHolder;
import adamin.com.dragandswiperecyclerview.helper.OnStartDragListener;

/**
 * Created by Administrator on 2015/7/27.
 */
public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder> implements ItemTouchHelperAdapter{
    private final List<String> mItems = new ArrayList<>();

    private final OnStartDragListener mDragStartListener;

    public RecyclerListAdapter(Context context,OnStartDragListener mDragStartListener) {
        this.mDragStartListener = mDragStartListener;
        mItems.addAll(Arrays.asList(context.getResources().getStringArray(R.array.dummy_items)));
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main,viewGroup, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder itemViewHolder, int i) {
        itemViewHolder.textView.setText(mItems.get(i));

        // Start a drag whenever the handle view it touched
        itemViewHolder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(itemViewHolder);
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);

    }

    public static  class ItemViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder{

        public final TextView textView;
        public final ImageView handleView;
        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            handleView = (ImageView) itemView.findViewById(R.id.handle);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);

        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);

        }
    }
}
