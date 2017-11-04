package com.application.cars.cars.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.application.cars.cars.utils.Constant;
import com.application.cars.cars.sqlite.DBHelper;
import com.application.cars.cars.Model.User;
import com.application.cars.cars.R;


/**
 * Created by kailash on 04-11-2017.
 */


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDetails extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    EditText customerName;
    Button next;

    public AddDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static AddDetails newInstance(String param1, String param2) {
        AddDetails fragment = new AddDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_details, container, false);
        customerName = v.findViewById(R.id.customer_name);
        next = v.findViewById(R.id.next_to_customer_name);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!customerName.getText().toString().isEmpty()) {
                    User user = new User(System.currentTimeMillis(), customerName.getText().toString());
                    DBHelper dbHelper = DBHelper.getDatabase(getActivity());
                    Constant.addToPreference(getContext(), "userId", user.getUserId());
                    dbHelper.insertUserDetails(user);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content, new AcceptCarDetails(), "AddCars").addToBackStack("AddCars").commit();
                } else {
                    Toast.makeText(getContext(), "Enter Customer name", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return v;
    }
}
