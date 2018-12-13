package com.example.os150.tabhost;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng latlng;
    MarkerOptions Immarker;
    final ArrayList<LatLng> latLngs = new ArrayList<>();
    final ArrayList<String> titles = new ArrayList<>();
    final ArrayList<String> prices = new ArrayList<>();
    final ArrayList<String> categorys = new ArrayList<>();
    final ArrayList<String> contents = new ArrayList<>();
    final ArrayList<String> userNames = new ArrayList<>();
    final ArrayList<String> emails = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




        FirebaseDatabase database =FirebaseDatabase.getInstance();
        DatabaseReference databaseReference =database.getReference("Market");
        databaseReference.child("Main").addValueEventListener(new ValueEventListener() {

            //상품 판매 마커 표시.
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                latLngs.clear();                        //올릴 데이터 초기화.
                titles.clear();
                for(DataSnapshot fileSnapshot : dataSnapshot.getChildren()){
                    //하위키들 value 가져오기
                    double lat = fileSnapshot.child("lat").getValue(double.class);
                    double lng = fileSnapshot.child("lng").getValue(double.class);
                    String category = fileSnapshot.child("category").getValue(String.class);
                    String content = fileSnapshot.child("contents").getValue(String.class);
                    String userName = fileSnapshot.child("userName").getValue(String.class);
                    String title = fileSnapshot.child("title").getValue(String.class);
                    String price = fileSnapshot.child("price").getValue(String.class);
                    String email = fileSnapshot.child("userEmail").getValue(String.class);

                    if (lat == 0 && lng == 0)
                        continue;
                    LatLng latlng = new LatLng(lat, lng);
                    System.out.println("저장되는 위도 경도 값 "+latlng.toString());

                    emails.add(email);
                    latLngs.add(latlng);
                    titles.add(title);
                    prices.add(price);
                    categorys.add(category);
                    contents.add(content);
                    userNames.add(userName);
                }
                MarkerOptions markerOptions = new MarkerOptions();
                for(int i=0; i<latLngs.size(); i++) {
                    markerOptions
                            .position(latLngs.get(i))
                            .title(titles.get(i))
                            .snippet(prices.get(i))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin));
                    System.out.println((i+1)+"번째 위도 경도 타이틀명"+latLngs.get(i).toString()+" / " + titles.get(i));
                    mMap.addMarker(markerOptions);
                    mMap.setOnMarkerClickListener(markerClickListener);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            String markerindex = new String();
            String markerid = marker.getId();
            for(int i = 0 ; i < markerid.length(); i ++)
            {
                if(48 <= markerid.charAt(i) && markerid.charAt(i) <= 57)
                    markerindex += markerid.charAt(i);
            }
            Intent intent =new Intent(getApplicationContext(),ViewActivity.class);

            intent.putExtra("title",titles.get(Integer.parseInt(markerindex)-1));
            intent.putExtra("contents",contents.get(Integer.parseInt(markerindex)-1));
            intent.putExtra("price",prices.get(Integer.parseInt(markerindex)-1));
            intent.putExtra("category",categorys.get(Integer.parseInt(markerindex)-1));
            intent.putExtra("userName",userNames.get(Integer.parseInt(markerindex)-1));
            intent.putExtra("userEmail",emails.get(Integer.parseInt(markerindex)-1));
            startActivity(intent);

            System.out.println("누른 아이디 : " + markerindex);

            return false;
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        System.out.println("지도 활성화");
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        getLocation();

    }

    public void getLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MapsActivity.this, "앱을 재시작하여 권한을 설정해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        String provider = location.getProvider();
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        latlng = new LatLng(latitude, longitude);
        //System.out.println("처음 받은 위도 : " + longitude +" 경도 : " + latitude);
        //System.out.println("저장된 받은 위도 : " + latlng.longitude +" 경도 : " + latlng.latitude);

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000000, 1000, gpsLocationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000000, 1000, gpsLocationListener);
        ImHere();
    }

    public void ImHere() {
        System.out.println("시작 화면 이동"+latlng.latitude +" / "+ latlng.longitude);
        CameraUpdate cameraUpdate =  CameraUpdateFactory.newLatLngZoom(latlng, 15);
        mMap.moveCamera(cameraUpdate);
        Immarker = new MarkerOptions()
                .position(latlng)
                .title("현재 위치")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.imhere));
        mMap.addMarker(Immarker);
    }


    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            double longitude = location.getLongitude(); //위도
            double latitude = location.getLatitude();   //경도
            //System.out.println("위도 : " + longitude + "경도 : " + latitude);
        }
        public void onStatusChanged(String provider, int status, Bundle extras) { }
        public void onProviderEnabled(String provider) { }
        public void onProviderDisabled(String provider) { } };
}
