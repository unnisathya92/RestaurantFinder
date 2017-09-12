package divakar.mad.ids.restaurant_finder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    Button searchRest;
    Button allRest;
    Button Reviews;
    public static final int MY_ACCESS_FINE_LOCATION  = 0;
    public static double latitude = new Double("0")
            ,longitude= new Double("0");
    public static boolean flagForLocation = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchRest = (Button)findViewById(R.id.button);
        allRest = (Button)findViewById(R.id.button2);
        Reviews = (Button)findViewById(R.id.button4);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                MY_ACCESS_FINE_LOCATION);



        searchRest.setOnClickListener(serRest);
        allRest.setOnClickListener(allRes);
        Reviews.setOnClickListener(revi);


    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    GPSTRACKER gps = new GPSTRACKER(this);

                    // check if GPS enabled
                    if(gps.canGetLocation()) {

                         latitude = gps.getLatitude();
                         longitude = gps.getLongitude();
                         flagForLocation = true;
                    }

                }

                return;
            }


        }
    }

    private Button.OnClickListener serRest
            = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {
            //FindRestaurants.restaurants();
                Intent myIntent = new Intent(view.getContext(), Main2Activity.class);
                startActivityForResult(myIntent, 0);
            }



    };
    private Button.OnClickListener allRes
            = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {
            //FindRestaurants.restaurants();
            Intent myIntent = new Intent(view.getContext(), allRest.class);
            startActivityForResult(myIntent, 0);
        }



    };
    private Button.OnClickListener revi
            = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {
            //FindRestaurants.restaurants();
            Intent myIntent = new Intent(view.getContext(), Review.class);
            startActivityForResult(myIntent, 0);
        }



    };
}
