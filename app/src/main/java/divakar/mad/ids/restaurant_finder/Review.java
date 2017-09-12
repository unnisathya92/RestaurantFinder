package divakar.mad.ids.restaurant_finder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Review extends AppCompatActivity {
    Button search;
    EditText e ;
    static String output="";
    public static TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reviews_layout);

        search = (Button)findViewById(R.id.showreview);
        e =(EditText)findViewById(R.id.rname);
        textView = (TextView)findViewById(R.id.reviewresults);
        search.setOnClickListener(serRest);

    }
    private Button.OnClickListener serRest
            = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {
            FindRestaurants.reviews(e.getText().toString());
            while(true){
                if(!output.equals("")){
                    textView.setText(output);
                    break;
                }

            }
        }

    };
}
