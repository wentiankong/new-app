package com.example.myapplication;

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

public class MapViewFragment extends Fragment {
    private com.tencent.tencentmap.mapsdk.maps.MapView mapView = null;
    public MapViewFragment() {

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mapview, container, false);
        mapView = view.findViewById(R.id.map_view);
        TencentMap tencentMap = mapView.getMap();

        LatLng point1 = new LatLng(22.252731, 113.535649);
        tencentMap.moveCamera(CameraUpdateFactory.newLatLng(point1));

        MarkerOptions markerOptions = new MarkerOptions(point1).title("暨南大学珠海校区");
        Marker marker = tencentMap.addMarker(markerOptions);
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
