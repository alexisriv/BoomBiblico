package com.sixelasavir.www.boombiblico;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sixelasavir.www.boombiblico.greendao.model.DaoSession;
import com.sixelasavir.www.boombiblico.greendao.model.GamerRecord;
import com.sixelasavir.www.boombiblico.greendao.model.GamerRecordDao;

import java.util.List;

public class ListRecordActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, ListRecordFragment.OnFragmentInteractionListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    private DaoSession daoSession;
    GamerRecordDao gamerRecordDao;

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

        tabLayout.addTab(tabLayout.newTab().setText(R.string.name_aventureros).setIcon(R.mipmap.ic_aventureros));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.name_conquistadores).setIcon(R.mipmap.ic_conquistadores));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.name_guias_mayores).setIcon(R.mipmap.ic_guias_mayores));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        tabLayout.setOnTabSelectedListener(this);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

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
                    List<GamerRecord> gamerRecordsAventureros = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_AVENTURERO)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
                    listRecordFragment = ListRecordFragment.newInstance(gamerRecordsAventureros);
                    return listRecordFragment;
                case 1:
                    List<GamerRecord> gamerRecordsConquistadores = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_CONQUISTADOR)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
                    listRecordFragment = ListRecordFragment.newInstance(gamerRecordsConquistadores);
                    return listRecordFragment;
                case 2:
                    List<GamerRecord> gamerRecordsGuiasMayores = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_GUIA_MAYOR)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
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
}
