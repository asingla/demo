package in.codingninjas.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by asingla on 22/07/16.
 */
public class RCVAdapter extends RecyclerView.Adapter<RCVAdapter.OurHolder> {

    public class OurHolder extends RecyclerView.ViewHolder {

        TextView tv1;
        TextView tv2;
        Button b;

        public OurHolder(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.batchName);
            tv2 = (TextView) itemView.findViewById(R.id.batchStrength);
            b = (Button) itemView.findViewById(R.id.button);
        }
    }

    Context mContext;
    ArrayList<Batch> batches;
    BatchListListener listener;

    public interface BatchListListener {
        void batchClicked(Batch b);
    }

    public RCVAdapter(Context context, ArrayList<Batch> batches, BatchListListener listener) {
        mContext = context;
        this.batches = batches;
        this.listener = listener;
    }

    @Override
    public RCVAdapter.OurHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
        return new OurHolder(v);
    }

    @Override
    public void onBindViewHolder(RCVAdapter.OurHolder holder, final int position) {
        final Batch b = batches.get(position);
        holder.tv1.setText(b.name);
        holder.tv2.setText(b.strength + "");

        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.batchClicked(b);
            }
        });
    }

    @Override
    public int getItemCount() {
        return batches.size();
    }
}
