package com.sixelasavir.www.boombiblico;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.sixelasavir.www.boombiblico.greendao.model.DaoSession;
import com.sixelasavir.www.boombiblico.greendao.model.GamerRecord;
import com.sixelasavir.www.boombiblico.greendao.model.GamerRecordDao;

import java.util.List;

public class ListRecordActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, ListRecordFragment.OnFragmentInteractionListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    private DaoSession daoSession;
    GamerRecordDao gamerRecordDao;
    List<GamerRecord> gamerRecordsAventureros;
    List<GamerRecord> gamerRecordsConquistadores;
    List<GamerRecord> gamerRecordsGuiasMayores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_record);
        Toolbar toolbar = (Toolbar) findViewById(R.id.list_record_toolbar);
        setSupportActionBar(toolbar);

        daoSession = ((AppBoomBiblico) getApplication()).getDaoSession();
        gamerRecordDao = daoSession.getGamerRecordDao();

        tabLayout = (TabLayout) findViewById(R.id.club_tab_layout);
        viewPager = (ViewPager) findViewById(R.id.list_record_view_pager);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_aventureros_circle));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_conquistadores_circle));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.ic_guias_mayores_circle));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        tabLayout.setOnTabSelectedListener(this);

        gamerRecordsAventureros = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_AVENTURERO), GamerRecordDao.Properties.Level.eq(0)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
        gamerRecordsConquistadores = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_CONQUISTADOR), GamerRecordDao.Properties.Level.eq(0)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
        gamerRecordsGuiasMayores = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_GUIA_MAYOR), GamerRecordDao.Properties.Level.eq(0)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        Toast.makeText(getApplicationContext(), "onTabUnselected".concat(Integer.toString(tab.getPosition())), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        Log.d("onTabReselected", Integer.toString(tab.getPosition()));
    }

    public class Pager extends FragmentStatePagerAdapter {

        private int tabCount;

        public Pager(FragmentManager fragmentManager, int tabCount) {
            super(fragmentManager);
            this.tabCount = tabCount;
        }

        @Override
        public Fragment getItem(int position) {
            ListRecordFragment listRecordFragment;
            switch (position) {
                case 0:
                    listRecordFragment = ListRecordFragment.newInstance(gamerRecordsAventureros);
                    return listRecordFragment;
                case 1:
                    listRecordFragment = ListRecordFragment.newInstance(gamerRecordsConquistadores);
                    return listRecordFragment;
                case 2:
                    listRecordFragment = ListRecordFragment.newInstance(gamerRecordsGuiasMayores);
                    return listRecordFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select_level, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.level_one:
                gamerRecordsAventureros = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_AVENTURERO), GamerRecordDao.Properties.Level.eq(0)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
                gamerRecordsConquistadores = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_CONQUISTADOR), GamerRecordDao.Properties.Level.eq(0)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
                gamerRecordsGuiasMayores = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_GUIA_MAYOR), GamerRecordDao.Properties.Level.eq(0)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
                return true;
            case R.id.level_two:
                gamerRecordsAventureros = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_AVENTURERO), GamerRecordDao.Properties.Level.eq(1)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
                gamerRecordsConquistadores = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_CONQUISTADOR), GamerRecordDao.Properties.Level.eq(1)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
                gamerRecordsGuiasMayores = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_GUIA_MAYOR), GamerRecordDao.Properties.Level.eq(1)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
                return true;
            case R.id.level_three:
                gamerRecordsAventureros = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_AVENTURERO), GamerRecordDao.Properties.Level.eq(2)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
                gamerRecordsConquistadores = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_CONQUISTADOR), GamerRecordDao.Properties.Level.eq(2)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
                gamerRecordsGuiasMayores = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_GUIA_MAYOR), GamerRecordDao.Properties.Level.eq(2)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void selectedLevel(int level){
        
    }
}
