package sk.upjs.vma.formativ.Admin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sk.upjs.vma.formativ.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailPouzivatelaAdminFragment extends Fragment {


    public DetailPouzivatelaAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_pouzivatela_admin, container, false);
    }

}
