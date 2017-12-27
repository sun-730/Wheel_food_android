package wheelofeats.roxdesign.com.wheelofeats.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import wheelofeats.roxdesign.com.wheelofeats.Activity.MyWheelsActivity;
import wheelofeats.roxdesign.com.wheelofeats.Activity.RestaurantActivity;
import wheelofeats.roxdesign.com.wheelofeats.Activity.SpinActivity;
import wheelofeats.roxdesign.com.wheelofeats.R;
import wheelofeats.roxdesign.com.wheelofeats.Utils.Global;


public class FoodFragment extends android.app.Fragment {

    public FoodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_food, container, false);
        ImageButton bttFood = (ImageButton) view.findViewById(R.id.imgDining);
        ImageButton bttCustom = (ImageButton) view.findViewById(R.id.imgCustom);
        ImageButton bttCulture = (ImageButton) view.findViewById(R.id.imgCulture);
        ImageButton bttRestaurant = (ImageButton) view.findViewById(R.id.imgRestaurant);
        bttFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SpinActivity.class);
                intent.putExtra("spinType", "dining");
                getActivity().startActivity(intent);

            }
        });
        bttCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyWheelsActivity.class));

            }
        });
        bttCulture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SpinActivity.class);
                intent.putExtra("spinType", "cuisines");
                startActivity(intent);

            }
        });
        bttRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RestaurantActivity.class));
            }
        });
        return view;
    }

}
