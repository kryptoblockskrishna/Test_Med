package layout;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.view.View;


/**
 * Created by Kishore on 7/6/2017.
 */

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener m;

    public interface OnItemClickListener{
        public void OnItemClick(View v, int position);
    }

    GestureDetector gd;

    public RecyclerItemClickListener(Context context,OnItemClickListener listener){
        m = listener;
        gd = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(),e.getY());
        if(childView != null && m!=null && gd.onTouchEvent(e)){
            m.OnItemClick(childView, rv.getChildAdapterPosition(childView));
        }

        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
