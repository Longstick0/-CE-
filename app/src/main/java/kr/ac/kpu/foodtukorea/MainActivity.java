package kr.ac.kpu.foodtukorea;


import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.ArrayList;
import java.util.List;

import kr.ac.kpu.foodtukorea.databaseConfig.DatabaseHelper;
import kr.ac.kpu.foodtukorea.databaseConfig.RestaurantAdminDisp;
import kr.ac.kpu.foodtukorea.domain.FoodStoreApiResult;
import kr.ac.kpu.foodtukorea.domain.RestrtSanittnGradStu;
import kr.ac.kpu.foodtukorea.domain.Row;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, Overlay.OnClickListener {

    private static final int ACCESS_LOCATION_PERMISSION_REQUEST_CODE = 100;

    private MapView mapView;
    private NaverMap naverMap;
    private List<Marker> markerList = new ArrayList<Marker>();
    private FusedLocationSource locationSource;
    private InfoWindow infoWindow;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {

        switch(item.getItemId())
        {
            case R.id.menu1:
                Intent intent = new Intent(getApplicationContext(), SearchActivity2.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
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

        infoWindow = new InfoWindow();
        //마커 클릭 시 표시되는 내용
        infoWindow.setAdapter(new InfoWindow.DefaultViewAdapter(this) {
            @NonNull
            @Override
            protected View getContentView(@NonNull InfoWindow infoWindow) {
                Marker marker = infoWindow.getMarker();
                Row store = (Row) marker.getTag();
                View view = View.inflate(MainActivity.this, R.layout.view_info_window, null);
                ((TextView) view.findViewById(R.id.name)).setText(store.getAppontDe());
                //if문 안에 equalsIgnoreCase는 == 비교와 같다고 생각하시면 될 거 같아요
                //즉 아래 코드는 store.getAppontGrad와 "plenty"가 같다면 아래
                //((TextView) view.findViewById(R.id.stock)).setText("a");가 실행됩니다.
                //저희 프로그램은 이미 마커의 색으로 식품등급을 판별할 수 있기에 따로 if문에 따라 view에 보여지는 내용이
                //변경될 필요가 없을 것 같아요.
                if ("plenty".equalsIgnoreCase(store.getAppontGrad())) {
                    ((TextView) view.findViewById(R.id.stock)).setText("a");
                } else if ("some".equalsIgnoreCase(store.getAppontInstDivNm())) {
                    ((TextView) view.findViewById(R.id.stock)).setText("b");
                } else if ("few".equalsIgnoreCase(store.getRefineWgs84Logt())) {
                    ((TextView) view.findViewById(R.id.stock)).setText("c");
                } else if ("empty".equalsIgnoreCase(store.getRefineWgs84Logt())) {
                    ((TextView) view.findViewById(R.id.stock)).setText("d");
                } else if ("break".equalsIgnoreCase(store.getRefineWgs84Lat())) {
                    ((TextView) view.findViewById(R.id.stock)).setText("e");
                } else {
                    ((TextView) view.findViewById(R.id.stock)).setText(null);
                }
                ((TextView) view.findViewById(R.id.time)).setText("f " + store.getRefineWgs84Lat());
                return view;
            }
        });

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
                marker.setTag(rowStore);

                marker.setPosition(new LatLng(Double.parseDouble(rowStore.getRefineWgs84Lat()), Double.parseDouble(rowStore.getRefineWgs84Logt())));
                if ("매우우수".equalsIgnoreCase(rowStore.getAppontGrad())) {
                    marker.setIcon(OverlayImage.fromResource(R.drawable.marker_greena));
                } else if ("우수".equalsIgnoreCase(rowStore.getAppontGrad())) {
                    marker.setIcon(OverlayImage.fromResource(R.drawable.marker_greena)); //노란색
                } else if ("보통".equalsIgnoreCase(rowStore.getAppontGrad())) {
                    marker.setIcon(OverlayImage.fromResource(R.drawable.marker_greena)); //빨간
                } else {
                    marker.setIcon(OverlayImage.fromResource(R.drawable.marker_greena));
                }
                marker.setAnchor(new PointF(0.5f, 0f));
                marker.setMap(naverMap);
                marker.setOnClickListener(this);
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

    @Override
    public boolean onClick(@NonNull Overlay overlay) {
        Marker marker = (Marker) overlay;
        infoWindow.open(marker);

        return false;
    }
}