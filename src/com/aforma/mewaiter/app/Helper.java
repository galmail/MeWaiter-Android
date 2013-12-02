package com.aforma.mewaiter.app;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.widget.ListAdapter;
import android.widget.ListView;


/** 
 * 
 * Expande la ListView a su tama–o m‡ximo de tal forma que muestra todos los elementos sin Scroll.
 *
 * El Scroll utilizado seria el del layout en este caso.
 */
public class Helper {
    private static final String myListAdapter = null;
	public static void getListViewSize(ListView myListView) {
        //ListAdapter myListAdapter = myListView.getAdapter();
        AdapterItems myListAdpater =  (AdapterItems) myListView.getAdapter();
        
       
        if (myListAdpater == null || myListAdpater.isEmpty() ) {
          
            return;
        }
       
        
        View childView = myListAdpater.getView(0, null, myListView);  
        
        childView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        
        int totalHeight = 0;
        int tamanoItem = childView.getMeasuredHeight();
        for (int i = 0; i < myListAdpater.getCount(); i++) {
            
            totalHeight += tamanoItem;
        }
        totalHeight += 50;
        
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdpater.getCount() - 1));
        //params.height = totalHeight;
        myListView.setLayoutParams(params);
        myListView.requestLayout();
        
        Log.i("height of listItem:", String.valueOf(totalHeight));
}
    public static void getListViewSize2(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight = 70;
        for (int size = 0; size <= myListAdapter.getCount()+1; size++) {
         
            totalHeight += 70;
        }
       totalHeight += 140;
      
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        //params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() + 2));
        myListView.setLayoutParams(params);
        // print height of adapter on log
        Log.i("height of listItem:", String.valueOf(totalHeight));
}

}

// Read more: http://www.androidhub4you.com/2012/12/listview-into-scrollview-in-android.html#ixzz2chVVvFIF