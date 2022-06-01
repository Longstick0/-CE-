package kr.ac.kpu.foodtukorea;


import android.graphics.PointF;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;
import java.util.List;

import kr.ac.kpu.foodtukorea.domain.FoodStoreApiResult;
import kr.ac.kpu.foodtukorea.domain.RestrtSanittnGradStu;
import kr.ac.kpu.foodtukorea.domain.Row;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int ACCESS_LOCATION_PERMISSION_REQUEST_CODE = 100;

    private MapView mapView;
    private NaverMap naverMap;
    private List<Marker> markerList = new ArrayList<Marker>();
    private FusedLocationSource locationSource;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //네이버 지도
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

    }

    //최초 실행 시 설정값
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {

        this.naverMap = naverMap;

        locationSource = new FusedLocationSource(this, ACCESS_LOCATION_PERMISSION_REQUEST_CODE);
        naverMap.setLocationSource(locationSource);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

        LatLng mapCenter = naverMap.getCameraPosition().target;
        fetchFoodStore("e9d760bbea604ab0900bcd74d4f95be6", 250, "json", 41390);


        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(37.377, 126.805),  // 위치 지정
                11                           // 줌 레벨
        );
        naverMap.setCameraPosition(cameraPosition);
    }

    private void fetchFoodStore(String key, int pSize, String type, int sigun) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://openapi.gg.go.kr/").addConverterFactory(GsonConverterFactory.create()).build();
        FoodStoreApi foodStoreApi = retrofit.create(FoodStoreApi.class);
        foodStoreApi.getStoreByGeo().enqueue(new Callback<FoodStoreApiResult>() {
            @Override
            public void onResponse(Call<FoodStoreApiResult> call, Response<FoodStoreApiResult> response) {
                if (response.code() == 200) {
                    FoodStoreApiResult result = response.body();
                    updateMarkers(result);
                }
            }

            @Override
            public void onFailure(Call<FoodStoreApiResult> call, Throwable t) {
                System.out.println("실패");
            }
        });
    }

    private void updateMarkers(FoodStoreApiResult result) {
        List<RestrtSanittnGradStu> stus = result.getRestrtSanittnGradStus();
        List<Row> rows = new ArrayList<>();
        for (RestrtSanittnGradStu restrtSanittnGradStu : stus) {
            if (restrtSanittnGradStu.getRow() == null){
                continue;
            }
            List<Row> row = restrtSanittnGradStu.getRow();
            for (Row temp : row) {
                rows.add(temp);
            }
        }

        if (result.getRestrtSanittnGradStus() != null && rows.size() > 0) {
            for (Row rowStore : rows) {
                Marker marker = new Marker();
                marker.setPosition(new LatLng(Double.parseDouble(rowStore.getRefineWgs84Lat()), Double.parseDouble(rowStore.getRefineWgs84Logt())));
                if ("매우우수".equalsIgnoreCase(rowStore.getAppontGrad())) {
                    marker.setIcon(OverlayImage.fromResource(R.drawable.marker_greena));
                } else if ("우수".equalsIgnoreCase(rowStore.getAppontGrad())) {
                    marker.setIcon(OverlayImage.fromResource(R.drawable.marker_greena));
                } else if ("보통".equalsIgnoreCase(rowStore.getAppontGrad())) {
                    marker.setIcon(OverlayImage.fromResource(R.drawable.marker_greena));
                } else {
                    marker.setIcon(OverlayImage.fromResource(R.drawable.marker_greena));
                }
                marker.setAnchor(new PointF(0.5f, 0f));
                marker.setMap(naverMap);
                markerList.add(marker);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case ACCESS_LOCATION_PERMISSION_REQUEST_CODE:
                locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults);
                return;
        }
    }

    private void resetMarkerList() {
        if (markerList != null && markerList.size() > 0) {
            for (Marker marker : markerList) {
                marker.setMap(null);
            }
            markerList.clear();
        }
    }
}