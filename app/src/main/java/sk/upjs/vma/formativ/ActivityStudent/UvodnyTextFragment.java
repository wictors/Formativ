package sk.upjs.vma.formativ.ActivityStudent;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sk.upjs.vma.formativ.R;

public class UvodnyTextFragment extends Fragment {


    public UvodnyTextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_uvodny_text, container, false);
    }

}
