package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapViewFragment extends Fragment {
    private com.tencent.tencentmap.mapsdk.maps.MapView mapView = null;
    public class DataDownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return new ShowDownLoader().download(urls[0]);
        }
        @Override
        protected void onPostExecute(String responseData) {
            if (responseData != null) {
                ArrayList<LocationMessage> shopLocations= new ShowDownLoader().parseJsonObjects(responseData);
                TencentMap tencentMap = mapView.getMap();
                for (LocationMessage shopLocation : shopLocations) {
                    LatLng point1 = new LatLng(shopLocation.getLatitude(), shopLocation.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions(point1)
                            .title(shopLocation.getName());
                    Marker marker = tencentMap.addMarker(markerOptions);
                }
            }
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mapview, container, false);
        mapView = view.findViewById(R.id.map_view);
        TencentMap tencentMap = mapView.getMap();

        new DataDownloadTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
                "http://file.nidama.net/class/mobile_develop/data/bookstore.json");

        LatLng point1 = new LatLng(22.252731, 113.535649);
        tencentMap.moveCamera(CameraUpdateFactory.newLatLng(point1));

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
