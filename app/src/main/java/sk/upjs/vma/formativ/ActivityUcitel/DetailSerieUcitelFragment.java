package sk.upjs.vma.formativ.ActivityUcitel;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sk.upjs.vma.formativ.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailSerieUcitelFragment extends Fragment {


    public DetailSerieUcitelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_serie_ucitel, container, false);
    }

}