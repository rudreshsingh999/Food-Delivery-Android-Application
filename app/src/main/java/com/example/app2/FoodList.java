package com.example.app2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodlist;
    String CategoryId = "";
    FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        database = FirebaseDatabase.getInstance();
        foodlist = database.getReference("Foods");

        recycler_menu = (RecyclerView) findViewById(R.id.recycler_food);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        if(getIntent() != null) {
            CategoryId = getIntent().getStringExtra("CategoryId");
        }

        if(!CategoryId.isEmpty() && CategoryId != null) {
            loadfoodlist(CategoryId);
        }
    }

    private void loadfoodlist(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder> (Food.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodlist.orderByChild("menuId").equalTo(categoryId)) {

                @Override
                protected void populateViewHolder(FoodViewHolder viewHolder, Food model, int position) {
                    viewHolder.foodName.setText(model.getName());
                    Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.foodImage);
                    final Food local = model;
                    viewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Toast.makeText(FoodList.this, ""+local.getName(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }

        };
        recycler_menu.setAdapter(adapter);
    }
}
