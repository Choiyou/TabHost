package com.example.os150.tabhost;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.os150.tabhost.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

/**
 * Created by os150 on 2018-12-07.
 */

public class CMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap gMap;
    private Geocoder geocoder;
    private Button searchbutton;
    private EditText mapeditText;
    private TextView lanText;
    private TextView lonText;
    DatabaseReference mDatabase;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmaps);
        mapeditText = (EditText) findViewById(R.id.MapText);
        searchbutton = (Button) findViewById(R.id.MapButton);
        lanText = (TextView) findViewById(R.id.lanText);
        lonText = (TextView) findViewById(R.id.lonText);

        mDatabase = FirebaseDatabase.getInstance().getReference("townsetting").child(user.getDisplayName());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.cmap);
        mapFragment.getMapAsync(this);

//
//            mDatabase.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    double lat2 = dataSnapshot.child("lat2").getValue(double.class);
//                    double lng2 = dataSnapshot.child("lng2").getValue(double.class);
//                    String townname = dataSnapshot.child("townname").getValue(String.class);
//                    System.out.println("lat : " + lat2 + "/ lng : " + lng2 + "/townname : " + townname);
//
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }
//        else{
//            LatLng setting = new LatLng(37,127);
//            gMap.addMarker(new MarkerOptions().position(setting).title("설정되어있는 위치"));
//            gMap.moveCamera(CameraUpdateFactory.newLatLng(setting));
//            lanText.setText("위도 : "+37);
//            lonText.setText("경도 : "+127);



//        }

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        gMap = googleMap;
        geocoder = new Geocoder(this);

        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions mOption = new MarkerOptions();
                mOption.title("마커 좌표");
                Double latitude = latLng.latitude; //위도
                Double longitude = latLng.longitude; //경도

                mOption.snippet(latitude.toString()+","+longitude.toString());
                mOption.position(new LatLng(latitude,longitude));
                googleMap.addMarker(mOption);


            }
        });
        searchbutton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {

                String str = mapeditText.getText().toString();
                List<Address> addressList = null;
                if(str.length()!=0){
                    try{
                        addressList = geocoder.getFromLocationName(str,10);
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                    try {
                        System.out.println(addressList.get(0).toString());
                        String[] splitStr = addressList.get(0).toString().split(",");
                        String address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1, splitStr[0].length() - 2);
                        System.out.println(address);

                        String latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1);
                        String longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1);


                        lanText.setText("위도 : " + latitude);
                        double lat = Double.parseDouble(latitude);

                        System.out.println("latitude:" + latitude);
                        System.out.print("longitude: " + longitude);
                        LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                        lonText.setText("경도 : " + longitude);
                        double lng = Double.parseDouble(longitude);
                        MarkerOptions mOptions = new MarkerOptions();
                        mOptions.title("검색 위치");
                        mOptions.snippet(address);
                        mOptions.position(latLng);
                        gMap.addMarker(mOptions);

                        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));


                        final Towndata towndata = new Towndata(mapeditText.getText().toString(), user.getDisplayName(), lat, lng);
                        mDatabase.setValue(towndata);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"위치를 입력해주세요",Toast.LENGTH_SHORT).show();
                }



            }
        });


        LatLng setting = new LatLng(37,127);
        gMap.addMarker(new MarkerOptions().position(setting).title("설정되어있는 위치"));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(setting));
        lanText.setText("위도 : "+37);
        lonText.setText("경도 : "+127);



    }
}