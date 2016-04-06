package com.eugene.lysak.attractgrouptest.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FilterQueryProvider;
import android.widget.ListView;

import com.eugene.lysak.attractgrouptest.DataBase.Contract;
import com.eugene.lysak.attractgrouptest.R;
import com.eugene.lysak.attractgrouptest.Utils.HeroesListAdapter;

public class HeroListFragment extends BaseFragment implements SearchView.OnQueryTextListener,LoaderManager.LoaderCallbacks<Cursor>{

    private OnHeroListFragmentListener mListener;
    private ListView listView;
    private HeroesListAdapter adapter;

    public HeroListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hero_list, container, false);

        listView = (ListView)rootView.findViewById(R.id.listView);
        adapter = new HeroesListAdapter(getActivity(),null);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onFragmentListItemClick(id);
            }
        });

        //Фильтр для поиска
        adapter.setFilterQueryProvider(new FilterQueryProvider() {

            @Override
            public Cursor runQuery(CharSequence constraint) {
                String partialValue = constraint.toString();
                return getActivity().getContentResolver().query(Contract.Heroes.CONTENT_URI,
                        null,
                        Contract.Heroes.NAME + " LIKE ?",
                        new String[]{"%" + partialValue + "%"},
                        Contract.Heroes.TIME);

            }
        });

        getActivity().getSupportLoaderManager().initLoader(1, null, this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        setVisibleFAB(View.VISIBLE);
        //скидываем поиск
        adapter.getFilter().filter("");
    }

    public void onFragmentListItemClick(long id) {
        if (mListener != null) {
            mListener.onFragmentInteraction(id);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnHeroListFragmentListener) {
            mListener = (OnHeroListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //Фильтруем список
        if (TextUtils.isEmpty(newText)) {
            adapter.getFilter().filter("");
        } else {
            adapter.getFilter().filter(newText);
        }
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(
                getActivity(),
                Contract.Heroes.CONTENT_URI,
                null,
                null,
                null,
                Contract.Heroes.TIME
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(!data.isClosed()) {
            adapter.swapCursor(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }

    public interface OnHeroListFragmentListener {
        void onFragmentInteraction(long id);
    }

    protected void onReceiveBackPressed() {
        //Если фрагмент главный то при нажатии на Back выходим из приложения
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        getActivity().finish();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //Показываем кнопку поиска
        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        myActionMenuItem.setVisible(true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }
}
