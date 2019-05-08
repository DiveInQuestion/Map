package com.example.a19360.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListViewFragment extends Fragment {
    private MainActivity.ListViewAdapter listViewAdapter;
    public ListViewFragment(MainActivity.ListViewAdapter theListViewAdapter) {
        listViewAdapter=theListViewAdapter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_list_view, container, false);
        ListView listView= (ListView) contentView.findViewById(R.id.listView_songs);

        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new mListViewItemClick2());

        registerForContextMenu(listView);
        return contentView;
    }
    class mListViewItemClick2 implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TextView txt1=(TextView)view.findViewById(R.id.itemTitle);
            TextView txt2=(TextView)view.findViewById(R.id.itemText);
            Toast.makeText(getActivity()
                          , "您选择的项目是："+txt1.getText().toString()+txt2.getText().toString()
                          , Toast.LENGTH_SHORT).show();
        }
    }
}
