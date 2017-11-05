package com.application.cars.cars.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.application.cars.cars.utils.Constant;
import com.application.cars.cars.sqlite.DBHelper;
import com.application.cars.cars.activity.MainActivity;
import com.application.cars.cars.Model.Car;
import com.application.cars.cars.R;

/**
 * Created by kailash on 04-11-2017.
 */


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AcceptCarDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcceptCarDetails extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    EditText addNoOfCars;
    Button next, submit;
    TableLayout tableLayout;
    Context mContext;
    Long userId;

    public AcceptCarDetails() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AcceptCarDetails newInstance(String param1, String param2) {
        AcceptCarDetails fragment = new AcceptCarDetails();
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
        userId = Constant.getValueForPrefKey(getContext(), "userId");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_accept_car_details, container, false);
        addNoOfCars = v.findViewById(R.id.no_of_cars);
        next = v.findViewById(R.id.next_add_cars_layout);
        tableLayout = v.findViewById(R.id.add_views);
        submit = v.findViewById(R.id.submit_data);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableLayout.removeAllViews();
                init(Integer.valueOf(addNoOfCars.getText().toString()), getContext());
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getData()) {
                    getActivity().finish();
                    startActivity(new Intent(getContext(), MainActivity.class));
                }
            }
        });
        return v;
    }

    public void init(int val, Context context) {
        mContext = context;
        for (int i = 0; i < val; i++) {
            TableRow row = new TableRow(mContext);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setWeightSum(1f);
            row.setLayoutParams(lp);

            EditText carCompany = new EditText(mContext);
            lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.3f);
            carCompany.setLayoutParams(lp);
            carCompany.setHint("Car company");
            carCompany.setTextSize(15f);

            EditText model = new EditText(mContext);
            lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.3f);
            model.setLayoutParams(lp);
            model.setHint("Model");
            model.setTextSize(15f);

            EditText regNo = new EditText(mContext);
            lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.4f);
            regNo.setLayoutParams(lp);
            regNo.setHint("Registration number");
            regNo.setTextSize(15f);
            row.addView(carCompany);
            row.addView(model);
            row.addView(regNo);

            tableLayout.addView(row, i);
        }
    }

    public boolean getData() {
        int total = 0;
        boolean flag = false;
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            TableRow mRow = (TableRow) tableLayout.getChildAt(i);
            EditText carCompanyData = (EditText) mRow.getChildAt(0);
            EditText modelData = (EditText) mRow.getChildAt(1);
            EditText regNoData = (EditText) mRow.getChildAt(2);
            Car car = new Car(carCompanyData.getText().toString(), modelData.getText().toString(), regNoData.getText().toString(), userId);
            DBHelper dbHelper = DBHelper.getDatabase(getActivity());
            dbHelper.insertCarDetails(car);
            Log.i("cars", " company : " + carCompanyData.getText() + " model : " + modelData.getText() + " regNO : " + regNoData.getText());
            total++;
            Toast.makeText(getContext(), "Data inserted successfully", Toast.LENGTH_SHORT).show();
            flag = true;
        }

        Log.d("test", "Number of rows are " + total);
        return flag;
    }
}
