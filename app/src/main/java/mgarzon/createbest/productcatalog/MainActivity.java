package mgarzon.createbest.productcatalog;

import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class MainActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextPrice;
    TextView textViewId;
    Button buttonAddProduct;
    Button buttonDeleteProduct;
    Button buttonFindProduct;
    ListView listViewProducts;

    DatabaseReference databaseProducts;

    List<Product> products;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPrice = (EditText) findViewById(R.id.editTextPrice);
        textViewId = findViewById(R.id.textViewId);
        listViewProducts = (ListView) findViewById(R.id.listViewProducts);
        buttonAddProduct = (Button) findViewById(R.id.addButton);
        buttonDeleteProduct = (Button) findViewById(R.id.deleteButton);
        buttonFindProduct = (Button) findViewById(R.id.findButton);

        //database instance and reference
        databaseProducts = FirebaseDatabase.getInstance().getReference("products");

        products = new ArrayList<>();

        //adding an onclicklistener to button
        buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newProduct();
            }
        });
        buttonDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct();
            }
        });
        buttonFindProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lookUpProduct();
            }
        });


//        listViewProducts.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Product product = products.get(i);
//                showUpdateDeleteDialog(String.valueOf(product.getId()), product.getProductName());
//                return true;
//            }
//        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseProducts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear previous artist list
                products.clear();

                for(DataSnapshot postSnapshot: snapshot.getChildren()){
                    //get product
                    Product product = postSnapshot.getValue(Product.class);
                    //adding product to list
                    products.add(product);

                }
                //creating adapter
                ProductList productsAdapter = new ProductList(MainActivity.this, products);
                //attaching adapter to the listview
                listViewProducts.setAdapter(productsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }


//    private void showUpdateDeleteDialog(final String productId, String productName) {
//
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = getLayoutInflater();
//        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
//        dialogBuilder.setView(dialogView);
//
//        final EditText editTextName = (EditText) dialogView.findViewById(R.id.editTextName);
//        final EditText editTextPrice  = (EditText) dialogView.findViewById(R.id.editTextPrice);
//        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateProduct);
//        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteProduct);
//
//        dialogBuilder.setTitle(productName);
//        final AlertDialog b = dialogBuilder.create();
//        b.show();
//
//        buttonUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = editTextName.getText().toString().trim();
//                String priceInput = editTextPrice.getText().toString();
//                double price;
//                if (!name.isEmpty() && !priceInput.isEmpty()) {
//                    price = Double.parseDouble(priceInput);
//                    updateProduct(productId, name, price);
//                    b.dismiss();
//                }else{
//                    Toast.makeText(MainActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
//
//        buttonDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deleteProduct(productId);
//                b.dismiss();
//            }
//        });
//    }

//    private void updateProduct(String id, String name, double price) {
//
//        //get specified product reference
//        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("products").child(id);
//
//        //update product
//        Product product = new Product(id, name, price);
//        dR.setValue(product);
//
//        Toast.makeText(this, "Product Updated", Toast.LENGTH_SHORT).show();
//    }

//    private boolean deleteProduct(String id) {
//        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("products").child(id);
//
//        dR.removeValue();
//        Toast.makeText(getApplicationContext(), "Product Deleted", Toast.LENGTH_SHORT).show();
//        return true;
//    }

//    private void addProduct() {
//
//         String name = editTextName.getText().toString().trim();
////       double price = Double.parseDouble(String.valueOf(editTextPrice.getText().toString()));
//
//        if(!TextUtils.isEmpty(name)){
//            //getting unique id
//            String id = databaseProducts.push().getKey();
//
//            //create a Product object
//            Product product = new Product(id, name, price);
//
//            //saving product to database
//            databaseProducts.child(id).setValue(product);
//
//            //setting text to blank
//            editTextName.setText("");
//            editTextPrice.setText("");
//
//            //display success message
//            Toast.makeText(this, "Product added", Toast.LENGTH_SHORT).show();
//        }else{
//
//            //if value is not given
//            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
//        }
//
//
//
//    }

        private void lookUpProduct(){
            MyDBHandler db = new MyDBHandler(this);
            Product product = db.findProduct(editTextName.getText().toString());
            if(product != null){
                textViewId.setText(String.valueOf(product.getId()));
                editTextName.setText(product.getProductName());
                editTextPrice.setText(String.valueOf(product.getPrice()));

            }
            else Toast.makeText(this, "Product does not exist.", Toast.LENGTH_SHORT).show();
        }

        private boolean deleteProduct(){
            MyDBHandler db = new MyDBHandler(this);

            boolean result = db.deleteProduct(editTextName.getText().toString());
            editTextName.setText("");
            editTextPrice.setText("");
            textViewId.setText("");

            Toast.makeText(this, "Product deleted.", Toast.LENGTH_SHORT).show();
            return result;
        }


        private void newProduct() {
            MyDBHandler db = new MyDBHandler(this);

         String name = editTextName.getText().toString().trim();
         String price = editTextPrice.getText().toString().trim();
        if(!TextUtils.isEmpty(name)){
            if(TextUtils.isEmpty(price)){
                price = "0";
            }

            //create a Product object
            Product product = new Product(name, Double.parseDouble(price));

            db.addProduct(product);

            //setting text to blank
            editTextName.setText("");
            editTextPrice.setText("");

            //display success message
            Toast.makeText(this, "Product added", Toast.LENGTH_SHORT).show();
        }else{

            //if value is not given
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
        }



    }


}