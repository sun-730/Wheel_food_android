package wheelofeats.roxdesign.com.wheelofeats.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import wheelofeats.roxdesign.com.wheelofeats.Activity.SpinActivity;
import wheelofeats.roxdesign.com.wheelofeats.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrinkFragment extends android.app.Fragment {


    public DrinkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_drink, container, false);
        ImageButton bttLiquor = (ImageButton) view.findViewById(R.id.imgLiquor);
        ImageButton bttBeer = (ImageButton) view.findViewById(R.id.imgBeer);
        ImageButton bttTea = (ImageButton) view.findViewById(R.id.imgTea);
        ImageButton bttWine = (ImageButton) view.findViewById(R.id.imgWine);

        bttLiquor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SpinActivity.class);
                intent.putExtra("spinType", "LIQUOR");
                getActivity().startActivity(intent);

            }
        });
        bttBeer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SpinActivity.class);
                intent.putExtra("spinType", "BEER");
                getActivity().startActivity(intent);

            }
        });
        bttTea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SpinActivity.class);
                intent.putExtra("spinType", "TEA");
                getActivity().startActivity(intent);

            }
        });
        bttWine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SpinActivity.class);
                intent.putExtra("spinType", "WINE");
                getActivity().startActivity(intent);

            }
        });
        return view;
    }

}
