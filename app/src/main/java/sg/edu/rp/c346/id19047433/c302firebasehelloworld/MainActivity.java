package sg.edu.rp.c346.id19047433.c302firebasehelloworld;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class MainActivity extends AppCompatActivity {

    private TextView tvMessage;
    private EditText etMessage;
    private TextView tvPriority;
    private EditText etPriority;
    private Button btnUpdate;

    private FirebaseFirestore db;
    private CollectionReference colRef;
    private DocumentReference docRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMessage = findViewById(R.id.textViewMessage);
        etMessage = findViewById(R.id.editTextMessage);
        tvPriority = findViewById(R.id.textViewPriority);
        etPriority = findViewById(R.id.editTextPriority);

        btnUpdate = findViewById(R.id.buttonUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = etMessage.getText().toString();
                String priority = etPriority.getText().toString();
                Message msg = new Message(text, priority);
                //To set
                docRef.set(msg);
                //To update
                //docRef.update("text", text);
                //does not work the database never update the color
                //docRef.update("text", text, "color", "red")
                btnUpdateOnClick(v);
            }
        });

        db = FirebaseFirestore.getInstance();

        colRef = db.collection("message");
        docRef = colRef.document("message");
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Message msg = snapshot.toObject(Message.class);//use POJO
                    tvMessage.setText(msg.getText());
                    tvPriority.setText("Priority is " + msg.getPriority());
                }
            }
        });
    }

    private void btnUpdateOnClick(View v) {


    }
}
