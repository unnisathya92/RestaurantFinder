package divakar.mad.ids.restaurant_finder;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    Button search;
    EditText e ;
    static String output="";
    public static TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_restaurant);

        search = (Button)findViewById(R.id.button5);
        e =(EditText)findViewById(R.id.editText);
        textView = (TextView)findViewById(R.id.textView6);
        search.setOnClickListener(serRest);

    }
    private Button.OnClickListener serRest
            = new Button.OnClickListener(){

        @Override
        public void onClick(View view) {
            FindRestaurants.searchRestaurants(e.getText().toString());
            while(true){
                if(!output.equals("")){
                    textView.setText(output);
                    break;
                }

            }
        }

    };
}
