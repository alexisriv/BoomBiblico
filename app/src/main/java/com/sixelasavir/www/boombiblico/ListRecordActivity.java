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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

    private static final String TAB = "tab";
    private static final String LEVEL = "level";
    private int level = 0;
    Bundle bundle;


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

        Bundle bundleAux = getIntent().getExtras();
        if (bundleAux != null) {
            TabLayout.Tab tab = tabLayout.getTabAt(bundleAux.getInt(TAB));
            tab.select();

            if (bundleAux.getInt(LEVEL) == 1) {
                level = 1;
                gamerRecordsAventureros = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_AVENTURERO), GamerRecordDao.Properties.Level.eq(1)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
                gamerRecordsConquistadores = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_CONQUISTADOR), GamerRecordDao.Properties.Level.eq(1)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
                gamerRecordsGuiasMayores = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_GUIA_MAYOR), GamerRecordDao.Properties.Level.eq(1)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
            } else if (bundleAux.getInt(LEVEL) == 2) {
                level = 2;
                gamerRecordsAventureros = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_AVENTURERO), GamerRecordDao.Properties.Level.eq(2)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
                gamerRecordsConquistadores = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_CONQUISTADOR), GamerRecordDao.Properties.Level.eq(2)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
                gamerRecordsGuiasMayores = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_GUIA_MAYOR), GamerRecordDao.Properties.Level.eq(2)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
            } else {
                level = 0;
                gamerRecordsAventureros = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_AVENTURERO), GamerRecordDao.Properties.Level.eq(0)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
                gamerRecordsConquistadores = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_CONQUISTADOR), GamerRecordDao.Properties.Level.eq(0)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
                gamerRecordsGuiasMayores = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_GUIA_MAYOR), GamerRecordDao.Properties.Level.eq(0)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
            }
        } else {
            level = 0;
            gamerRecordsAventureros = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_AVENTURERO), GamerRecordDao.Properties.Level.eq(0)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
            gamerRecordsConquistadores = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_CONQUISTADOR), GamerRecordDao.Properties.Level.eq(0)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();
            gamerRecordsGuiasMayores = gamerRecordDao.queryBuilder().where(GamerRecordDao.Properties.Type.eq(MenuActivity.TYPE_PLAYER_GUIA_MAYOR), GamerRecordDao.Properties.Level.eq(0)).orderDesc(GamerRecordDao.Properties.RecordGamer).list();

        }
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
                    listRecordFragment = ListRecordFragment.newInstance(orderPosition(gamerRecordsAventureros));
                    return listRecordFragment;
                case 1:
                    listRecordFragment = ListRecordFragment.newInstance(orderPosition(gamerRecordsConquistadores));
                    return listRecordFragment;
                case 2:
                    listRecordFragment = ListRecordFragment.newInstance(orderPosition(gamerRecordsGuiasMayores));
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
                newLevel(tabLayout.getSelectedTabPosition(), 0);
                return true;
            case R.id.level_two:
                newLevel(tabLayout.getSelectedTabPosition(), 1);
                return true;
            case R.id.level_three:
                newLevel(tabLayout.getSelectedTabPosition(), 2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void newLevel(int tab, int level) {
        Intent intent = new Intent(this, ListRecordActivity.class);
        intent.putExtra(TAB, tab);
        intent.putExtra(LEVEL, level);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(LEVEL, level);
        super.onSaveInstanceState(outState);
    }


    public List<GamerRecord> orderPosition(List<GamerRecord> grs) {
        GamerRecord grAux;

        String split1[];
        String split2[];
        for (GamerRecord record : grs) {
            record.setPosition(0);
        }
        for (int i = 0; i < grs.size(); i++) {
            for (int j = i + 1; j < grs.size(); j++) {
                split1 = grs.get(i).getTimerRecordGamer().split(":");
                split2 = grs.get(j).getTimerRecordGamer().split(":");

                if (grs.get(i).getRecordGamer() == grs.get(j).getRecordGamer()) {
                    if (Integer.valueOf(split1[0]) > Integer.valueOf(split2[0])) {
                        grAux = grs.get(i);
                        grs.set(i, grs.get(j));
                        grs.set(j, grAux);

                    } else if (Integer.valueOf(split1[0]) == Integer.valueOf(split2[0])) {
                        if (Integer.valueOf(split1[1]) > Integer.valueOf(split2[1])) {
                            grAux = grs.get(i);
                            grs.set(i, grs.get(j));
                            grs.set(j, grAux);

                        } else if (Integer.valueOf(split1[1]) == Integer.valueOf(split2[1])) {
                            if (Integer.valueOf(split1[2]) > Integer.valueOf(split2[2])) {
                                grAux = grs.get(i);
                                grs.set(i, grs.get(j));
                                grs.set(j, grAux);

                            }
                        }
                    }
                }
            }
        }
        if (grs.size() != 0) {
            int p = 0;
            int rgAux = grs.get(0).getRecordGamer();
            String trgAux = grs.get(0).getTimerRecordGamer();
            for (int i = 0; i < grs.size(); i++) {
                if (rgAux == grs.get(i).getRecordGamer() && trgAux == grs.get(i).getTimerRecordGamer()) {
                    grs.get(i).setPosition(1);
                } else {
                    p = i;
                    break;
                }
            }
            rgAux = grs.get(p).getRecordGamer();
            trgAux = grs.get(p).getTimerRecordGamer();
            for (int i = p; i < grs.size(); i++) {
                if (rgAux == grs.get(i).getRecordGamer() && trgAux == grs.get(i).getTimerRecordGamer() && grs.get(i).getPosition() != Integer.valueOf(1)) {
                    grs.get(i).setPosition(2);
                } else {
                    p = i;
                    break;
                }
            }
            rgAux = grs.get(p).getRecordGamer();
            trgAux = grs.get(p).getTimerRecordGamer();
            for (int i = p; i < grs.size(); i++) {
                if (rgAux == grs.get(i).getRecordGamer() && trgAux == grs.get(i).getTimerRecordGamer() && grs.get(i).getPosition() != Integer.valueOf(1) && grs.get(i).getPosition() != Integer.valueOf(2)) {
                    grs.get(i).setPosition(3);
                } else {
                    break;
                }
            }
        }

        return grs;
    }
}
