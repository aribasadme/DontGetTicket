package com.ara.dontgetticket;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends FragmentActivity {
    Spinner Transport, Transport_train, Transport_metro, Origin, Destination, Controller;
    EditText dateBox, timeBox;
    Button sendButton,Button_Lucky;

    private static final String TAG = "com.example.dgt.dgt";

    String[] transports = {"TRANSPORT","Train", "Metro"};
    String[] traintypes = {"ICN","IR","RE","S1","S2","S4","S11","S30"};
    String[] metrotypes = {"M1","M2"};
    String[] metroStations = {"Lausanne-Gare","Lausanne-Flon","EPFL"};
    String[] trainStations = {"Cully","Cossonay-Penthalaz","Lausanne", "Renens VD","Vallorbe","Yverdon-les-Bains"};
    String[] controllers = {"CONTROLLERS","Yes","No"};

    DataBaseFile dataBase;
    ArrayList general = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Transport = (Spinner)findViewById(R.id.transportSpinner);
        ArrayAdapter<String> adapter_transports = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, transports);
        adapter_transports.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Transport.setAdapter(adapter_transports);
        Transport.setOnItemSelectedListener(new transportOnClickListener());

        Controller = (Spinner)findViewById(R.id.controllerSpinner);
        ArrayAdapter<String> adapter_controller = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, controllers);
        adapter_controller.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Controller.setAdapter(adapter_controller);

        Controller.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 1:
                        Toast to1 = Toast.makeText(getApplicationContext(), controllers[position], Toast.LENGTH_LONG);
                        to1.show();
                        break;
                    case 2:
                        Toast to2 = Toast.makeText(getApplicationContext(), controllers[position], Toast.LENGTH_LONG);
                        to2.show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button_Lucky = (Button)findViewById(R.id.button_lucky);
        Button_Lucky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                int tam = general.size();
                System.out.println(tam);
                double l = Lucky();
                System.out.println("l: " + l);
                double prob = l/tam;

                String s = String.valueOf(prob);

                Toast t_prob = Toast.makeText(getApplicationContext(),"The probability to get a tickey is:  " +s+"%", Toast.LENGTH_LONG);
                t_prob.show();
                general.clear();
            }
        });

    }
    public int Lucky(){
        double ret=0;
        int r;
        for(int i=0;i<general.size();++i){
            System.out.println(general.get(i));
            if(general.get(i)=="Yes"){
                ret++;
            }
        }
        ret = ret*100;
        r=(int)ret;
        return  r;
    }

    public void FillData(){
        Random rdn = new Random();
        for(int i=0;i<30;++i) {
            if (rdn.nextBoolean()) {
                general.add("Yes");
            } else {
                general.add("No");
            }
        }
    }

    public void pickDate(View view)
    {
        DatePickerFragment dateFragment = new DatePickerFragment();
        dateFragment.show(getSupportFragmentManager(),"datePicker");
        dateBox = (EditText)findViewById(R.id.dateEditText);
        dateBox.setText(dateFragment.GetDate());
//        Log.d(TAG,"Time Box" + timeBox.getText());
    }

    public void pickTime(View view)
    {
        TimePickerFragment timeFragment = new TimePickerFragment();
        timeFragment.show(getSupportFragmentManager(), "timePicker");
        timeBox = (EditText)findViewById(R.id.timeEditText);
        timeBox.setText(timeFragment.GetTime());
        Log.d(TAG,"Time Box" + timeBox.getText());

    }

    public void sendDataBase(View view){
        dataBase = new DataBaseFile(this);

        sendButton = (Button)findViewById(R.id.button_send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] str = new String[6];
                CharSequence dateText = dateBox.getText();
                CharSequence timeText = timeBox.getText();

                str[0] = dateText.toString();
                str[1] = timeText.toString();
                str[2] = Transport.getSelectedItem().toString();
                str[3] = Origin.getSelectedItem().toString();
                str[4] = Destination.getSelectedItem().toString();
                str[5] = Controller.getSelectedItem().toString();

                dataBase.write(str[0]+','+str[1]+','+str[2]+','+str[3]+','+str[4]+','+str[5]+'\n');

                Toast t_send = Toast.makeText(getApplicationContext(),"Saving database.csv", Toast.LENGTH_LONG);
                t_send.show();
                FillData();
                general.add(str[3]);

            }
        });
    }

    private void chooseTrain(){
        Transport_train = (Spinner)findViewById(R.id.subTransportSpinner);

        ArrayAdapter<String> trainAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, traintypes);
        trainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Transport_train.setAdapter(trainAdapter);
    }

    private void chooseMetro(){
        Transport_metro = (Spinner)findViewById(R.id.subTransportSpinner);

        ArrayAdapter<String> metroAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, metrotypes);
        metroAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Transport_metro.setAdapter(metroAdapter);
    }

    private void chooseMetroStations(){
        Origin = (Spinner)findViewById(R.id.originSpinner);
        Destination = (Spinner)findViewById(R.id.destinationSpinner);

        ArrayAdapter<String> adapter_metro = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, metroStations);
        Origin.setAdapter(adapter_metro);
        Destination.setAdapter(adapter_metro);
    }

    private void chooseTrainStations(){
        Origin = (Spinner)findViewById(R.id.originSpinner);
        Destination = (Spinner)findViewById(R.id.destinationSpinner);
        ArrayAdapter<String> adapter_trains = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, trainStations);
        Origin.setAdapter(adapter_trains);
        Destination.setAdapter(adapter_trains);
    }

    public class transportOnClickListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> parent, View v, int position,  long id) {

            parent.getItemAtPosition(position);
            if (position == 1)
            {
                chooseTrain();
                chooseTrainStations();
            }
            else if (position == 2)
            {
                chooseMetro();
                chooseMetroStations();
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");

        final EditText timeBox = (EditText) findViewById(R.id.timeEditText);
        CharSequence userText = timeBox.getText();
        outState.putCharSequence("savedText", userText);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");

        final EditText timeBox = (EditText) findViewById(R.id.timeEditText);
        CharSequence userText = savedInstanceState.getCharSequence("savedText");
        timeBox.setText(userText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
