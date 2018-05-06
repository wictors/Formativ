package sk.upjs.vma.formativ;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


public class ZoznamSeriiUcitelFragment extends Fragment {

    String[] pole = {"feakfj","afaefgeagf","eagfneag",};

    public ZoznamSeriiUcitelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zoznam_serii_ucitel, container, false);
        Context context = view.getContext();

        return view;
    }

}
