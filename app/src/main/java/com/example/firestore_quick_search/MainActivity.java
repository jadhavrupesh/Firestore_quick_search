package com.example.firestore_quick_search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.firestore_quick_search.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    SearchAdapter searchAdapter;
    ActivityMainBinding binding;
    private HashMap<String, Name> search;
    List<Name> nameList;
    List<Name> filtered;
    FirebaseAuth mAuth;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getUid() == null) {
            mAuth.signInAnonymously();
        }
        Log.e(TAG, "User_id: " + mAuth.getUid());


        search = new HashMap<>();
        nameList = new ArrayList<>();
        filtered = new ArrayList<>();

        searchAdapter = new SearchAdapter(this, filtered);
        FirebaseFirestore.getInstance().collection("names").orderBy("name", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        String nameString = document.getString("name");
                        String sname = document.getString("sname");
                        Name name = new Name(nameString, sname);
                        filtered.add(name);
                        search.put(nameString, name);
                        searchAdapter.notifyDataSetChanged();
                    }

                }
            }
        });

        binding.recyclerview.setAdapter(searchAdapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setHasFixedSize(true);

    }

    private void CreateName() {
        for (int i = 0; i < 500; i++) {
            int count = i;
            final String nameId = FirebaseFirestore.getInstance().collection("names").document().getId();
            Random r = new Random();
            int low = 5;
            int high = 10;
            int result = r.nextInt(high - low) + low;
            String nameString = getSaltString(result);
            String sname = getSaltString(result);
            Name name = new Name(nameString, sname);
            FirebaseFirestore.getInstance().collection("names").document(nameId).set(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        searchAdapter.notifyDataSetChanged();
                        Log.e(TAG, "All Value Added: " + count);
                    }
                }
            });


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchInRecycler(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.action_add) {
            Toast.makeText(this, "add is clicked", Toast.LENGTH_SHORT).show();
            CreateName();
        }

        if (id == R.id.action_search) {
            Toast.makeText(this, "search is clicked", Toast.LENGTH_SHORT).show();
        }


        return true;
    }

    private void SearchInRecycler(String query) {
        filtered.clear();
        filtered = SearchFirestore.getFieldValue(search, query);
        searchAdapter = new SearchAdapter(MainActivity.this, filtered);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        binding.recyclerview.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();
    }


    protected String getSaltString(int no) {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < no) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
}