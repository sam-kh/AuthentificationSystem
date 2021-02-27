package sam.ensat.authentificationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    TextView name,email,gender,phone,dop;
    FirebaseUser user;
    DatabaseReference ref;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name =findViewById(R.id.name);
        email = findViewById(R.id.email);
        gender = findViewById(R.id.sexe);
        phone = findViewById(R.id.phone);
        dop = findViewById(R.id.dop);
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();
        //get user information from database and store them in the variables
        ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User _user = snapshot.getValue(User.class);
                if(user != null){
                    name.setText(_user.getName());
                    email.setText(_user.getEmail());
                    dop.setText(_user.getDop().toString());
                    gender.setText(_user.getSexe());
                    phone.setText(_user.getMobile());
                    email.setText(_user.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
    //action of button logout
    public void LogOut(View view) {
        //destroy current user session
        FirebaseAuth.getInstance().signOut();
        //then lanch main activity
        startActivity(new Intent(ProfileActivity.this,MainActivity.class));
        finish();
    }
}