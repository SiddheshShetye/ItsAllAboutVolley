package aboutvolley.sidroid.com.itsallaboutvolley.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;

import java.util.Arrays;
import java.util.List;

import aboutvolley.sidroid.com.itsallaboutvolley.R;
import aboutvolley.sidroid.com.itsallaboutvolley.VolleyRequestQueue;
import aboutvolley.sidroid.com.itsallaboutvolley.dataholder.Images;

/**
 * A simple {@link Fragment} subclass.
 */
public class NIVListFragment extends Fragment {

    private RecyclerView NIVRecyclerView;
    private int width;
    private NIVRecyclerAdapter mAdapter;
    private static final int NUMBER_OF_GRID_COLS = 3;

    public static NIVListFragment newInstance() {
        return new NIVListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calculateScreenWidth();
        mAdapter = new NIVRecyclerAdapter(Arrays.asList(Images.URLS),getActivity());
        mAdapter.setCellWidth(width / NUMBER_OF_GRID_COLS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_nivlist, container, false);
        initComponents(layout);
        return layout;
    }
    protected void calculateScreenWidth(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
    }

    private void initComponents(View layout) {
        NIVRecyclerView = (RecyclerView) layout.findViewById(R.id.NIVRecyclerView);
        NIVRecyclerView.setHasFixedSize(true);
        NIVRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), NUMBER_OF_GRID_COLS));
        NIVRecyclerView.setAdapter(mAdapter);
    }

    private class NIVRecyclerAdapter extends RecyclerView.Adapter<NIVRecyclerAdapter.NIVViewHolder>{

        private final Context mContext;
        private final List<String> mList;
        private int mCellWidth;
        private ImageLoader mImageLoader;

        public NIVRecyclerAdapter(List<String> list,Context context){
            mContext = context;
            mList = list;
            mImageLoader = VolleyRequestQueue.getInstance(context).getImageLoader();
        }

        @Override
        public NIVViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(mContext).inflate(R.layout.item_image_grid,parent,false);
            return new NIVViewHolder(item,mCellWidth);
        }

        @Override
        public void onBindViewHolder(NIVViewHolder holder, int position) {
            holder.nimvPic.setImageUrl(mList.get(position), mImageLoader);
        }

        @Override
        public int getItemCount() {
            return mList == null ? 0 : mList.size();
        }

        public void setCellWidth(int width){
            mCellWidth = width;
        }

        class NIVViewHolder extends RecyclerView.ViewHolder{

            NetworkImageView nimvPic;

            public NIVViewHolder(View itemView,int cellWidth) {
                super(itemView);
                nimvPic = (NetworkImageView) itemView.findViewById(R.id.imvPic);
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) nimvPic.getLayoutParams();
                params.height = cellWidth;
                params.width = cellWidth;
                nimvPic.setLayoutParams(params);
            }
        }
    }

}
