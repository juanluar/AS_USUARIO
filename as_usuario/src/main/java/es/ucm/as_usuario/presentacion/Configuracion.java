package es.ucm.as_usuario.presentacion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import es.ucm.as_usuario.R;
import es.ucm.as_usuario.negocio.usuario.TransferUsuario;
import es.ucm.as_usuario.presentacion.controlador.Controlador;
import es.ucm.as_usuario.presentacion.controlador.ListaComandos;

/**
 * Clase asociada a la vista de personalizacion
 *
 * Created by Juan Lu on 24/02/2016.
 */
public class Configuracion extends Activity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int SELECT_FILE = 1;
    EditText editarNombre;
    Button aceptar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalizacion);
        Bundle bundle = getIntent().getExtras();
        editarNombre = (EditText)findViewById(R.id.editarNombre);
        aceptar = (Button)findViewById(R.id.envioNuevaConfig);
        if(bundle.getString("nombreConfiguracion")!= null){
           editarNombre.setText(bundle.getString("nombreConfiguracion"));
        }

        aceptar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TransferUsuario editarUsuario = new TransferUsuario();
                editarUsuario.setNombre("Jeff");
                Controlador.getInstancia().ejecutaComando(ListaComandos.EDITAR_USUARIO,editarUsuario);
            }
        });
    }

    public void volver(View v){
        Intent pantallaPrincipal = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(pantallaPrincipal);
    }

    public void ayuda(View v){
        //Esto debera llevar al pdf con la ayuda
    }

    public void cambiarColorApp(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(Configuracion.this);

        LayoutInflater inflater = Configuracion.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.edit_color, null));

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void cambiarTonoApp(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(Configuracion.this);

        LayoutInflater inflater = Configuracion.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.edit_tone, null));

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void cambiarImagenPerfil(View v) {
        final CharSequence[] items = { "Hacer foto", "Elegir de la galeria", "Salir" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Configuracion.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Hacer foto")) {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                } else if (items[item].equals("Elegir de la galeria")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Salir")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
}
