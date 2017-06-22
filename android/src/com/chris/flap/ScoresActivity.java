package com.chris.flap;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chris.flap.realm.HighScoresModel;
import com.chris.helpers.AssetLoader;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.chris.helpers.AssetLoader.prefs;


public class ScoresActivity extends Activity{

    private RealmResults<HighScoresModel> scores;
    private MyAdapter adapter;
    private Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

//        int i = 0;
//        if (prefs!=null)
//            i= AssetLoader.getHighScore();

        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String sharedName = settings.getString(getString(R.string.pref_nickname),"Default Flapper");



        Realm.init(this);
        realm = Realm.getDefaultInstance();
        if (prefs != null && AssetLoader.getCurrentScore() != 0){

        Number currentIdNum = realm.where(HighScoresModel.class).max("id");
        int nextId;
        if(currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;}


        HighScoresModel hs = new HighScoresModel();
        hs.setId(nextId);
        hs.setName(sharedName);
        hs.setScore(AssetLoader.getCurrentScore()); AssetLoader.setCurrentScore(0);


        realm.beginTransaction();
        realm.copyToRealmOrUpdate(hs);
        realm.commitTransaction();}

        try{scores = realm.where(HighScoresModel.class).findAllSorted("score", Sort.DESCENDING);
            adapter = new MyAdapter(this, android.R.layout.simple_list_item_1, scores.subList(0,10 > scores.size() ? scores.size() : 10));
            realm.beginTransaction();
            if(scores.size() == 11){scores.get(11).deleteFromRealm();}
            realm.commitTransaction();


        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(adapter);}catch (NullPointerException e){}

        /** na to dw meta */
        //adapter.notifyDataSetChanged();

    }


    private class MyAdapter extends ArrayAdapter<HighScoresModel> {
        public MyAdapter(Context context, int resource, List<HighScoresModel> objects) {
            super(context, resource, objects);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.sample_list_row, parent, false);

                Holder holder = new Holder();
                holder.idTextView = (TextView) rowView.findViewById(R.id.txt_list_row_score);
                holder.nameTextView = (TextView) rowView.findViewById(R.id.txt_list_row_name);
                rowView.setTag(holder);
            }

            Holder holder = (Holder) rowView.getTag();
            holder.idTextView.setText(Integer.toString(getItem(position).getScore()));
            holder.nameTextView.setText(getItem(position).getName());
            return rowView;
        }
}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
    class Holder {
        TextView idTextView;
        TextView nameTextView;
    }
}


