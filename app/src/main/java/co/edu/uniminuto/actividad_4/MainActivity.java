package co.edu.uniminuto.actividad_4;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;

import co.edu.uniminuto.actividad_4.entities.User;
import co.edu.uniminuto.actividad_4.repository.UserRepository;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private EditText etDocumento;
    private EditText etUsuario;
    private EditText etNombres;
    private EditText etApellidos;
    private EditText etContraseña;
    private Button btnGuardar;
    private Button btnBuscar;
    private Button btnListar;
    private Button btnBorrar;
    private ListView listUsers;
    SQLiteDatabase sqLiteDatabase;
    private int documento;
    private String usuario;
    private String nombres;
    private String apellidos;
    private String pass;

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        begin();

        btnGuardar.setOnClickListener(this::createUsers);
        btnBuscar.setOnClickListener(this::seachUsers);
        btnListar.setOnClickListener(this::listUsers);
        btnBorrar.setOnClickListener(this::eraseUsers);


        }



    private void listUsers(View view) {
        UserRepository userRepository = new UserRepository(context, view);
        ArrayList<User> list = userRepository.getUserList();
        ArrayAdapter<User> arrayAdapter = new ArrayAdapter<>
                (context,android.R.layout.simple_list_item_1, list);
        this.listUsers.setAdapter(arrayAdapter);
        limpiar();
    }
    private void createUsers(View view) {
        capturarDato();
        User user = new User(documento, nombres, usuario, apellidos, pass);
        UserRepository userRepository = new UserRepository(context, view);
        userRepository.insertUser(user);



        limpiar();
        //listUsers(view);
        ArrayList<User> singleUserList = new ArrayList<>();
        singleUserList.add(user);
        ArrayAdapter<User> adapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_list_item_1,
                singleUserList
        );
        this.listUsers.setAdapter(adapter);

    }

    private  void seachUsers(View view) {
        if (etDocumento.getText().toString().isEmpty()) {
            Snackbar.make(view, "Ingrese un documento para buscar", Snackbar.LENGTH_LONG).show();
            return;
        }

        try {
            documento = Integer.parseInt(etDocumento.getText().toString());
            userRepository = new UserRepository(context, view);
            User user = userRepository.getUserByDocument(documento);

            if (user != null) {
                etNombres.setText(user.getName());
                etApellidos.setText(user.getLastname());
                etUsuario.setText(user.getUser());
                etContraseña.setText(user.getPass());


                ArrayList<User> userList = new ArrayList<>();
                userList.add(user);
                ArrayAdapter<User> adapter = new ArrayAdapter<>(
                        context,
                        android.R.layout.simple_list_item_1,
                        userList
                );
                listUsers.setAdapter(adapter);
            } else {
                Snackbar.make(view, "Usuario no encontrado", Snackbar.LENGTH_LONG).show();
            }
        } catch (NumberFormatException e) {
            Snackbar.make(view, "Formato de documento inválido", Snackbar.LENGTH_LONG).show();
        }


    }
    private void eraseUsers(View view) {
        if (etDocumento.getText().toString().isEmpty()) {
            Snackbar.make(view, "Ingrese un documento para eliminar", Snackbar.LENGTH_LONG).show();
            return;
        }

        try {
            documento = Integer.parseInt(etDocumento.getText().toString());
            userRepository = new UserRepository(context, view);
            boolean success = userRepository.deleteUser(documento);


            if (success) {
                Snackbar.make(view, "Usuario eliminado correctamente", Snackbar.LENGTH_LONG).show();
                limpiar();
                // Actualizo matriz
                listUsers(view);
            } else {
                Snackbar.make(view, "No se pudo eliminar el usuario", Snackbar.LENGTH_LONG).show();
            }
        } catch (NumberFormatException e) {
            Snackbar.make(view, "Formato de documento inválido", Snackbar.LENGTH_LONG).show();
        }
    }

    //capturar datos
    private void capturarDato(){
        //validaciones de regex
        this.documento = Integer.parseInt(this.etDocumento.getText().toString());
        this.usuario = this.etUsuario.getText().toString();
        this.nombres = this.etNombres.getText().toString();
        this.apellidos = this.etApellidos.getText().toString();
        this.pass = this.etContraseña.getText().toString();
    }


    //limpia campos
    private void limpiar(){
        this.etDocumento.setText("");
        this.etUsuario.setText("");
        this.etNombres.setText("");
        this.etApellidos.setText("");
        this.etContraseña.setText("");
    }
    private void begin(){
        this.etNombres = findViewById(R.id.etNombres);
        this.etApellidos = findViewById(R.id.etApellidos);
        this.etDocumento = findViewById(R.id.etDocumento);
        this.etUsuario = findViewById(R.id.etUsuario);
        this.etContraseña = findViewById(R.id.etContraseña);
        this.listUsers = findViewById(R.id.lvLista);
        this.context = this;
        this.btnGuardar = findViewById(R.id.btnGuardar);
        this.btnBuscar = findViewById(R.id.btnBuscar);
        this.btnListar = findViewById(R.id.btnListar);
        this.btnBorrar = findViewById(R.id.btnBorrar);


    }



}