package divakar.mad.ids.restaurant_finder;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class allRest extends AppCompatActivity {
    EditText e;
    public static TextView t;
    public static String output="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_search);


        t = (TextView) findViewById(R.id.textView5);
             process();
    }

    private void process() {
        FindRestaurants.restaurants();
        while (true) {
            if (!output.equals("")) {
                t.setText(output);
                break;
            }
        }
    }




}
