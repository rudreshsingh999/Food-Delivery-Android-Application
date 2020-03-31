package com.example.app2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetails extends AppCompatActivity {

    TextView food_name, food_price, food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
//    FloatingActionButton floatingActionButton;
    Button btnCart;
    ElegantNumberButton elegantNumberButton;
    String foodId;
    Food currentFood;
    FirebaseDatabase database;
    DatabaseReference foods;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        database = FirebaseDatabase.getInstance();
        foods = database.getReference("Foods");

        elegantNumberButton = findViewById(R.id.number_button);
        btnCart = findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        foodId,
                        currentFood.getName(),
                        elegantNumberButton.getNumber(),
                        currentFood.getPrice(),
                        currentFood.getDiscount()
                ));
                Toast.makeText(FoodDetails.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });
        food_description = findViewById(R.id.food_description);
        food_image = findViewById(R.id.img_food);
        food_name = findViewById(R.id.food_name);
        food_price = findViewById(R.id.food_price);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBAr);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        if(getIntent() != null) {
            foodId = getIntent().getStringExtra("FoodId");
        }

        if(!foodId.isEmpty()) {
            getDetailFood(foodId);
        }
    }

    private void getDetailFood(String foodId) {
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentFood = dataSnapshot.getValue(Food.class);
                Picasso.with(getBaseContext()).load(currentFood.getImage()).into(food_image);
                collapsingToolbarLayout.setTitle(currentFood.getName());
                food_price.setText(currentFood.getPrice());
                food_name.setText(currentFood.getName());
                food_description.setText(currentFood.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
