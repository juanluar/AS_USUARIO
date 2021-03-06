package es.ucm.as.presentacion.vista;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import es.ucm.as.R;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.Controlador;
import es.ucm.as.presentacion.controlador.ListaComandos;

/**
 * Clase asociada a la vista de activity_configuracion
 *
 */
public class Configuracion extends Activity {

    private static final int SELECCIONAR_GALERIA = 2;
    private static final int CAMARA = 1;

    private int DEFECTO;
    private int FROZEN;
    private int MARIO;
    private int STARWARS;
    private int TERMINATOR;

    private SoundPool sndPool;

    private EditText editarNombre;
    private Button aceptar;

    static String temaActual="AS_theme_azul";
    static int tonoActual;
    private String[] nombresColores={ "Azul", "Rojo", "Rosa", "Verde",
            "Negro"};
    private String[] nombresTonos = { "Defecto", "Frozen", "Mario Bros", "StarWars",
            "Terminator"};
    private Spinner spinnerColors;
    private Spinner spinnerTono;
    private String temaParcial;
    private int tonoParcial;
    private ImageView imagenConfiguracion;
    private String rutaImagen="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        cargarTema();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        Bundle bundle = getIntent().getExtras();
        TransferUsuario usuario = (TransferUsuario)bundle.getSerializable("usuario");
        editarNombre = (EditText)findViewById(R.id.editarNombre);
        aceptar = (Button)findViewById(R.id.envioNuevaConfig);
        spinnerColors = (Spinner) findViewById(R.id.cambiarColor);
        spinnerTono = (Spinner) findViewById(R.id.cambiarTono);
        imagenConfiguracion = (ImageView) findViewById(R.id.editarAvatar);
        tonoActual = usuario.getTono();
        tonoParcial=tonoActual;
        temaActual=usuario.getColor();
        temaParcial=temaActual;
        rutaImagen=usuario.getAvatar();

        // Para que se reproduzca el sonido al seleccionar el botón de aceptar
        // Se deben de cargar los sonidos aqui, en el metodo onCreate
        sndPool = new SoundPool(16, AudioManager.STREAM_MUSIC, 100);
        DEFECTO = sndPool.load(getApplicationContext(), R.raw.defecto, 1);
        FROZEN = sndPool.load(getApplicationContext(), R.raw.frozen, 1);
        MARIO = sndPool.load(getApplicationContext(), R.raw.mario, 1);
        STARWARS = sndPool.load(getApplicationContext(), R.raw.starwars, 1);
        TERMINATOR = sndPool.load(getApplicationContext(), R.raw.terminator, 1);

        ////////Spinner color ///////
        nombresColoresSistema();
        ArrayAdapter<String> adapter_colores= new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,nombresColores);
        adapter_colores
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerColors.setAdapter(adapter_colores);
        spinnerColors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerColors.setSelection(position);
                String colorSeleccionado = (String) spinnerColors.getSelectedItem();

                switch (colorSeleccionado){
                    case "Azul":
                        temaParcial="AS_theme_azul";
                    break;
                    case "Rojo":
                        temaParcial="AS_theme_rojo";
                        break;
                    case "Rosa":
                        temaParcial="AS_theme_rosa";
                        break;
                    case "Verde":
                        temaParcial="AS_theme_verde";
                        break;
                    case "Negro":
                        temaParcial="AS_theme_negro";
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //////////////Spinner tonos///////////////////

        nombresTonosSistema(usuario.getTono());
        ArrayAdapter<String> adapter_tonos = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, nombresTonos);
        adapter_tonos
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTono.setAdapter(adapter_tonos);
        spinnerTono.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerTono.setSelection(position);
                String selState = (String) spinnerTono.getSelectedItem();
                String tonoSeleccionado = (String) spinnerTono.getSelectedItem();
                switch (tonoSeleccionado){
                    case "Defecto":
                        tonoParcial = DEFECTO;
                        break;
                    case "Frozen":
                        tonoParcial = FROZEN;
                        break;
                    case "Mario Bros":
                        tonoParcial = MARIO;
                        break;
                    case "StarWars":
                        tonoParcial = STARWARS;
                        break;
                    case "Terminator":
                        tonoParcial = TERMINATOR;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        if(!usuario.getAvatar().equals(""))
            imagenConfiguracion.setImageBitmap(BitmapFactory.decodeFile(usuario.getAvatar()));
        else
            imagenConfiguracion.setImageResource(R.drawable.avatar);

        editarNombre.setText(usuario.getNombre());

        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editarNombre.getText().toString().matches("")) {
                    TransferUsuario editarUsuario = new TransferUsuario();
                    editarUsuario.setNombre(String.valueOf(editarNombre.getText()));
                    temaActual = temaParcial;
                    tonoActual = playidToBBDDId(tonoParcial);
                    editarUsuario.setColor(temaActual);
                    editarUsuario.setAvatar(rutaImagen);
                    editarUsuario.setTono(tonoActual);
                    sndPool.play(tonoParcial, 1.0f, 1.0f, 1, 0, 1.0f);
                    Toast.makeText(getApplicationContext(),
                            "Estás escuchando el tono que has elegido. Las notificaciones sonarán " +
                                    "así a partir de la próxima vez que sincronices con tu profesor"
                            , Toast.LENGTH_LONG).show();

                    Controlador.getInstancia().ejecutaComando(ListaComandos.EDITAR_USUARIO, editarUsuario);
                    Controlador.getInstancia().ejecutaComando(ListaComandos.CONSULTAR_USUARIO, null);
                }else{
                    Toast errorNombre =
                            Toast.makeText(getApplicationContext(),
                                    "El campo del nombre no puede estar vacío", Toast.LENGTH_SHORT);

                    errorNombre.show();
                }
            }
        });
    }

    // Este metodo ordena los elementos del spinner de tonos segun el que haya en BBDD
    private void nombresTonosSistema(int tono) {
        if(tono == R.raw.defecto) {
            nombresTonos[0] = "Defecto";
            nombresTonos[1] = "Frozen";
            nombresTonos[2] = "Mario Bros";
            nombresTonos[3] = "StarWars";
            nombresTonos[4] = "Terminator";
            return ;
        }
        if(tono == R.raw.frozen) {
            nombresTonos[0] = "Frozen";
            nombresTonos[1] = "Defecto";
            nombresTonos[2] = "Mario Bros";
            nombresTonos[3] = "StarWars";
            nombresTonos[4] = "Terminator";
            return ;
        }
        if(tono == R.raw.mario) {
            nombresTonos[0] = "Mario Bros";
            nombresTonos[1] = "Defecto";
            nombresTonos[2] = "Frozen";
            nombresTonos[3] = "StarWars";
            nombresTonos[4] = "Terminator";
            return ;
        }
        if(tono == R.raw.starwars) {
            nombresTonos[0] = "StarWars";
            nombresTonos[1] = "Defecto";
            nombresTonos[2] = "Frozen";
            nombresTonos[3] = "Mario Bros";
            nombresTonos[4] = "Terminator";
            return ;
        }
        if(tono == R.raw.terminator) {
            nombresTonos[0] = "Terminator";
            nombresTonos[1] = "Defecto";
            nombresTonos[2] = "Frozen";
            nombresTonos[3] = "Mario Bros";
            nombresTonos[4] = "StarWars";
            return;
        }
    }

    // Este metodo es necesario para guardar en BBDD el id correcto del sonido elegido
    private Integer playidToBBDDId(int tono) {
        if(tono == DEFECTO)
            return R.raw.defecto;
        if(tono == FROZEN)
            return R.raw.frozen;
        if(tono == MARIO)
            return R.raw.mario;
        if(tono == STARWARS)
            return R.raw.starwars;
        if(tono == TERMINATOR)
            return R.raw.terminator;
        else
            return R.raw.defecto;
    }

    private void nombresColoresSistema() {
        switch (temaActual){
            case "AS_theme_azul":
                nombresColores[0]="Azul"; nombresColores[1]="Rojo";nombresColores[2]="Rosa";nombresColores[3]="Verde";nombresColores[4]="Negro";
                break;
            case "AS_theme_rojo":
                nombresColores[0]="Rojo"; nombresColores[1]="Azul";nombresColores[2]="Rosa";nombresColores[3]="Verde";nombresColores[4]="Negro";
                break;
            case "AS_theme_rosa":
                nombresColores[0]="Rosa"; nombresColores[1]="Azul";nombresColores[2]="Rojo";nombresColores[3]="Verde";nombresColores[4]="Negro";
                break;
            case "AS_theme_verde":
                nombresColores[0]="Verde"; nombresColores[1]="Azul";nombresColores[2]="Rojo";nombresColores[3]="Rosa";nombresColores[4]="Negro";
                break;
            case "AS_theme_negro":
                nombresColores[0]="Negro"; nombresColores[1]="Azul";nombresColores[2]="Rojo";nombresColores[3]="Rosa";nombresColores[4]="Verde";
                break;
        }
    }

    public void cargarTema(){
        switch (temaActual){
            case "AS_theme_azul":
                setTheme(R.style.AS_tema_azul);
                break;
            case "AS_theme_rojo":
                setTheme(R.style.AS_tema_rojo);
                break;
            case "AS_theme_rosa":
                setTheme(R.style.AS_tema_rosa);
                break;
            case "AS_theme_verde":
                setTheme(R.style.AS_tema_verde);
                break;
            case "AS_theme_negro":
                setTheme(R.style.AS_tema_negro);
                break;
        }
    }


    public void volver(View v){
        finish();
    }

    public void ayuda(View v){
        Controlador.getInstancia().ejecutaComando(ListaComandos.AYUDA, "configuracion");
    }

    public void cambiarImagenPerfil(View v) {
        final CharSequence[] items = { "Hacer foto", "Elegir de la galeria", "Imagen por defecto" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Configuracion.this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Hacer foto")) {
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMARA);
                } else if (items[item].equals("Elegir de la galeria")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECCIONAR_GALERIA);
                } else if (items[item].equals("Imagen por defecto")) {
                    imagenConfiguracion.setImageResource(R.drawable.avatar);
                    rutaImagen="";
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMARA) {
                Bitmap imagen = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                imagen.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imagenConfiguracion.setImageBitmap(imagen);
                rutaImagen = destination.getPath();
            } else if (requestCode == SELECCIONAR_GALERIA) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null,
                        null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                bm = BitmapFactory.decodeFile(selectedImagePath, options);
                imagenConfiguracion.setImageBitmap(bm);
                rutaImagen=selectedImagePath;
            }
        }
    }
}
