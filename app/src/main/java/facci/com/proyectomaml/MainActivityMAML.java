package facci.com.proyectomaml;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivityMAML extends AppCompatActivity {
    DBHelper dbSQLITE;
    EditText txtNombre, txtApellido, txtRecintoelectoral, txtID, txtAñonacimiento;

    Button btnIngresar, btnModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_maml);
        dbSQLITE = new DBHelper(this);
    }

    public void ingresarClick(View v) {

        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtRecintoelectoral = (EditText) findViewById(R.id.txtRecintoelectoral);
        txtAñonacimiento = (EditText) findViewById(R.id.txtAñonacimiento);
        boolean estaIngresado = dbSQLITE.ingresar(txtNombre.getText().toString(), txtApellido.getText().toString(), Integer.parseInt(txtRecintoelectoral.getText().toString()), Integer.parseInt(txtAñonacimiento.getText().toString()));

        if (estaIngresado)
            Toast.makeText(MainActivityMAML.this, "Datos Ingresados", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(MainActivityMAML.this, "Lo sentimos ocurrió un error", Toast.LENGTH_SHORT).show();
    }

    public void buscarTodosClick(View v){

        Cursor res = dbSQLITE.selectVerTodos();
        if(res.getCount() == 0){
            mostrarMensaje("Error","No se encontraron registros");
            return;
        }

        StringBuffer buffer = new StringBuffer();

        while(res.moveToNext()){
            buffer.append("Id : "+res.getString(0)+"\n");
            buffer.append("Nombre : "+res.getString(1)+"\n");
            buffer.append("Apellido : "+res.getString(2)+"\n");
            buffer.append("Recintoelectoral: "+res.getInt(3)+"\n\n");
            buffer.append("Añonacimiento: "+res.getInt(4)+"\n\n\n");
        }

        mostrarMensaje("Registros",buffer.toString());
    }

    public void mostrarMensaje(String titulo, String Mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(titulo);
        builder.setMessage(Mensaje);
        builder.show();

    }

    public void modificarRegistroClick(View v){


        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtRecintoelectoral = (EditText) findViewById(R.id.txtRecintoelectoral);
        txtAñonacimiento = (EditText) findViewById(R.id.txtAñonacimiento);
        txtID = (EditText) findViewById(R.id.txtID);

        boolean estaAcutalizado = dbSQLITE.modificarRegistro(txtID.getText().toString(),txtNombre.getText().toString(),txtApellido.getText().toString(),Integer.parseInt(txtAñonacimiento.getText().toString()),Integer.parseInt(txtRecintoelectoral.getText().toString()));


        if (estaAcutalizado == true){
            Toast.makeText(MainActivityMAML.this,"Registro Actualizado",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivityMAML.this,"ERROR: Registro NO Actualizado",Toast.LENGTH_SHORT).show();
        }
    }




    public void eliminarRegistroClick(View v){

        //obtenemos el valor del control txtID
        txtID = (EditText) findViewById(R.id.txtID);


        Integer registrosEliminados = dbSQLITE.eliminarRegistro(txtID.getText().toString());

        if(registrosEliminados > 0 ){
            Toast.makeText(MainActivityMAML.this,"Registro(s) Eliminado(s)",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivityMAML.this,"ERROR: Registro no eliminado",Toast.LENGTH_SHORT).show();
        }

    }
}

