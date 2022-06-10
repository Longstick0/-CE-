package kr.ac.kpu.foodtukorea;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import kr.ac.kpu.foodtukorea.databaseConfig.DatabaseHelper;
import kr.ac.kpu.foodtukorea.databaseConfig.RestaurantAdminDisp;

public class SearchActivity2 extends AppCompatActivity {

    ListView listview = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // DB연동
        List<RestaurantAdminDisp> marketList = initLoadMarketDatabase();
        List<String> resName = new ArrayList<>();
        for (RestaurantAdminDisp restaurantAdminDisp : marketList) {
            resName.add(restaurantAdminDisp.getPrcscitypoint_bsshnm());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);

        getSupportActionBar().setTitle("행정처분 이력 내역 조회");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);

        addListToAdapter(adapter, marketList);

        EditText editTextFilter = (EditText)findViewById(R.id.editTextFilter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(SearchActivity2.this, + position +" " + id + "번 선택! ", Toast.LENGTH_LONG).show();
            }
        });
        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString();
                if (filterText.length() > 0) {
                    listview.setFilterText(filterText);

                } else {
                    listview.clearTextFilter();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
    }

    public void addListToAdapter(ArrayAdapter adapter, List<RestaurantAdminDisp> marketList) {
        for (RestaurantAdminDisp restaurantAdminDisp : marketList) {
            adapter.add(restaurantAdminDisp);
        }

    }

    private String search(String query, List<RestaurantAdminDisp> radList) {
        StringBuilder sb = new StringBuilder();
        for (RestaurantAdminDisp disp : radList) {
            if (disp.getPrcscitypoint_bsshnm().contains(query)) {
                sb.append(disp.getPrcscitypoint_bsshnm());
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private String getResult(List<RestaurantAdminDisp> radList) {
        StringBuilder sb = new StringBuilder();

        for (RestaurantAdminDisp disp : radList) {
            sb.append(disp.getPrcscitypoint_bsshnm());
            sb.append("\n");
        }
        return sb.toString();
    }

    public List<RestaurantAdminDisp> initLoadMarketDatabase(){
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        databaseHelper.OpenDatabaseFile();

        List<RestaurantAdminDisp> marketList = databaseHelper.getTableData();
        Log.e("test", String.valueOf(marketList.size()));

        databaseHelper.close();
        return marketList;
    }


}