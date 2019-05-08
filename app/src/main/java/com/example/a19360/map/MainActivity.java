package com.example.a19360.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Songs> songsCollection;
    ListViewAdapter theListAdapter;
    //ListView listView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] tabs = {"地图", "歌曲", "新闻"};
    private String [] titles={"(1)","(2)","(3)","(4)","(5)"};
    private String [] texts={"荷塘月色","最炫民族风","天蓝蓝","最美天下","自由飞翔"};
    private int [] resIds={R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5};
    View [] itemViews;
    public final static int REQUEST_CODE = 1;
    public final static String TAG = "TAG";

    //创建菜单项
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("操作");
        menu.add(0, 1, 0, "添加");
        menu.add(0, 2, 0, "删除");
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    //响应菜单项点击事件
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // 获取当前被选择的菜单项的信息
        AdapterView.AdapterContextMenuInfo itemInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 1:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,BActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("text1","请输入您要添加的歌曲");
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_CODE);
                break;
            case 2:
                if(itemInfo.position>titles.length-1){
                    theListAdapter.removeItem(itemInfo.position);
                    theListAdapter.notifyDataSetChanged();
                    break;
                }
                else{
                    Toast.makeText(getApplicationContext(),"You cannot delete the initial data!", Toast.LENGTH_SHORT).show();
                    break;
                }
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode==REQUEST_CODE)
        {
            if (resultCode== BActivity.RESULT_OK)
            {
                Bundle bundle=data.getExtras();
                String str = bundle.getString("text2");
                theListAdapter.addItem(str);
                theListAdapter.notifyDataSetChanged();
            }
        }
    }

    public class ListViewAdapter extends BaseAdapter {
        ArrayList<View> itemViews;

        public ListViewAdapter(ArrayList<Songs> songsCollection){
            itemViews = new ArrayList<View>(songsCollection.size()+titles.length);

            for (int i=0; i<titles.length; ++i) {
                itemViews.add(makeItemView(titles[i], texts[i],
                        resIds[i]));
            }

            //itemViews = new ArrayList<View>(songsCollection.size());

            for (int i=0; i<songsCollection.size(); ++i){
                itemViews.add(makeItemView(songsCollection.get(i).getNum()
                        , songsCollection.get(i).getName()
                        ,songsCollection.get(i).getPictureId()));
            }
        }

        public void addItem(String itemTitle){
            Songs songs=new Songs();
            songs.setName(itemTitle);
            songs.setPictureId(R.drawable.icon);
            songs.setNum("new");
            songsCollection.add(songs);
            SongsCollectionOperator operator=new SongsCollectionOperator();
            operator.save(MainActivity.this.getBaseContext(),songsCollection);

            View view=makeItemView(songs.getNum()
                    ,itemTitle
                    ,songs.getPictureId());
            itemViews.add(view);
        }

        public void removeItem(int position){
            itemViews.remove(position);
            songsCollection.remove(position-5);//这里需要注意position参数指定的位置，默认数据为前5个
            SongsCollectionOperator operator=new SongsCollectionOperator();
            operator.save(MainActivity.this.getBaseContext(),songsCollection);
        }
        public int getCount() {
            return itemViews.size();
        }

        public View getItem(int position) {
            return itemViews.get(position);
        }

        public long getItemId(int position) {
            return position;
        }

        private View makeItemView(String strTitle, String strText, int resId) {
            LayoutInflater inflater = (LayoutInflater)MainActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            View itemView = inflater.inflate(R.layout.listview_item, null);

            // 通过findViewById()方法实例R.layout.item内各组件
            TextView title = (TextView)itemView.findViewById(R.id.itemTitle);
            title.setText(strTitle);
            TextView text = (TextView)itemView.findViewById(R.id.itemText);
            text.setText(strText);
            ImageView image = (ImageView)itemView.findViewById(R.id.itemImage);
            image.setImageResource(resId);

            return itemView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            //if (convertView == null)
            return itemViews.get(position);
            //return convertView;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("我的应用");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,BActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("text1","");
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        SongsCollectionOperator operator=new SongsCollectionOperator();
        songsCollection=operator.load(getBaseContext());
        if(songsCollection==null)
            songsCollection= new ArrayList<Songs>();
        theListAdapter= new ListViewAdapter(songsCollection);

        LayoutInflater inflater = (LayoutInflater)MainActivity.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View itemView = inflater.inflate(R.layout.content_songs, null);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setAdapter(myPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        //每条之间的分割线
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);

        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

    }

    private MapViewFragment mapViewFragment=null;
    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        @Override
        public Fragment getItem(int position) {
            if(position==0)
            {
                mapViewFragment= new MapViewFragment();
                return mapViewFragment;
            }
            if(position==1)
            {
                ListViewFragment fragment= new ListViewFragment(theListAdapter);
                return fragment;
            }
            else {
                WebViewFragment webViewFragment = new WebViewFragment();
                return webViewFragment;
            }
        }
        @Override
        public int getCount() {
            return tabs.length;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if(mapViewFragment!=null)mapViewFragment.MDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        if(mapViewFragment!=null)mapViewFragment.MResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        if(mapViewFragment!=null)mapViewFragment.MPause();
    }
}