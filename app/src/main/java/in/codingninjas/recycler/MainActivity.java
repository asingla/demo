package in.codingninjas.recycler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.RemoteViews;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RCVAdapter.BatchListListener{

    RecyclerView recyclerView;
    ArrayList<Batch> batches;
    RCVAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        batches = new ArrayList<>();
        batches.add(new Batch("Java", 40));
        batches.add(new Batch("cpp", 40));
        batches.add(new Batch("android", 40));
        batches.add(new Batch("ror", 40));
        batches.add(new Batch("Java", 40));
        batches.add(new Batch("cpp", 40));
        batches.add(new Batch("android", 40));
        batches.add(new Batch("ror", 40));
        batches.add(new Batch("Java", 40));
        batches.add(new Batch("cpp", 40));
        batches.add(new Batch("android", 40));
        batches.add(new Batch("ror", 40));
        adapter = new RCVAdapter(this, batches, this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN ,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int i = viewHolder.getAdapterPosition();
                int j = target.getAdapterPosition();
                Batch first = batches.get(i);
                Batch second = batches.get(j);
                batches.set(i,second);
                batches.set(j, first);
                adapter.notifyItemMoved(j, i);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder vh, int direction) {
                int position = vh.getAdapterPosition();
                batches.remove(position);
                adapter.notifyItemRemoved(position);
            }
        };
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);
    }

    boolean showing = false;

    @Override
    public void batchClicked(Batch b) {
       int i = batches.indexOf(b);
//        batches.remove(b);
//        //adapter.notifyDataSetChanged();
//        adapter.notifyItemRemoved(i);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (!showing) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            Intent intent = new Intent(this, MainActivity.class);
            PendingIntent pi = PendingIntent.getActivity(this,1, intent, 0);
            builder.setSmallIcon(android.R.drawable.ic_dialog_email)
                    .setTicker("blha blah blah blha")
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_dialog_info))
                    .setContentTitle("title")
                    .setContentText("text text text text text text")
                    .setAutoCancel(true)
                    .setContentIntent(pi);
            builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE | NotificationCompat.DEFAULT_SOUND);
            //RemoteViews rv = new RemoteViews();
            //builder.setContent()
            //builder.setStyle(new NotificationCompat.BigTextStyle().bigText("dbdhbdhbdhbshhsvhvdhvhfvhvhvfhvfhvfhvfhvhfvhfvhfvhvhsbmnsmd"));
            builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(),android.R.drawable.star_big_on)));
            manager.notify(i, builder.build());
        } else {
            manager.cancel(i);
        }
        //showing = !showing;
    }
}
