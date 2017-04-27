package com.example.chenz.fragmentdemo;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static class TitlesFragment extends ListFragment {
        boolean mDualPane;
        int mCurCheckPositon = 0;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            System.out.println("Fragment-->onCreate");
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            System.out.println("Fragment-->onCreateView");
            return super.onCreateView(inflater,container,savedInstanceState);
        }

        @Override
        public void onPause() {
            super.onPause();
            System.out.println("Fragment-->onPause");
        }

        @Override
        public void onStop() {
            super.onStop();
            System.out.println("Fragment-->onStop");
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            System.out.println("Fragment-->onAttach");
        }

        @Override
        public void onStart() {
            super.onStart();
            System.out.println("Fragment-->onStart");
        }

        @Override
        public void onResume() {
            super.onResume();
            System.out.println("Fragment-->onResume");
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            System.out.println("Fragment-->onDestroy");
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,array));
            View detailsFrame = getActivity().findViewById(R.id.details);

            mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

            if (savedInstanceState != null) {
                mCurCheckPositon = savedInstanceState.getInt("curChoice",0);
            }

            if (mDualPane) {
                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                showDetails(mCurCheckPositon);
            }
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putInt("curChoice",mCurCheckPositon);
        }

        @Override
        public  void onListItemClick(ListView l,View v,int position,long id) {
            super.onListItemClick(l,v,position,id);
            showDetails(position);
        }

        void showDetails(int index) {
            mCurCheckPositon = index;
            if (mDualPane) {
                getListView().setItemChecked(index,true);
                DetailsFragment details = (DetailsFragment) getFragmentManager().findFragmentById(R.id.details);
                if (details == null || details.getShownIndex()!=index) {
                    details = DetailsFragment.newInstance(mCurCheckPositon);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.details,details);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();
                }
            }else{
                new AlertDialog.Builder(getActivity()).setTitle(android.R.string.dialog_alert_title)
                        .setMessage(array[index])
                        .setPositiveButton(android.R.string.ok,null).show();
            }
        }
    }

    public static class DetailsFragment extends Fragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        public static DetailsFragment newInstance(int index) {
            DetailsFragment details = new DetailsFragment();
            Bundle args = new Bundle();
            args.putInt("index",index);
            details.setArguments(args);
            return details;
        }

        public int getShownIndex() {
            return getArguments().getInt("index",0);
        }

        @Override
        public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
            if (container == null)
                return null:
            text.setText(array[getShownIndex()]);
            return scroller;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            super.onCreateOptionsMenu(menu,inflater);
            menu.add("Menu 1a").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            menu.add("Menu 1b").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            Toast.makeText(getActivity(),"index id"+getShownIndex()+"&& menu text is"+item.getTitle(),1000).show();
            return super.onOptionsItemSelected(item);
        }
    }

}
