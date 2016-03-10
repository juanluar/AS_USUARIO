package es.ucm.as_usuario.negocio.suceso.imp;

import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import es.ucm.as_usuario.presentacion.Contexto;
import es.ucm.as_usuario.integracion.DBHelper;
import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.negocio.suceso.Reto;
import es.ucm.as_usuario.negocio.suceso.SASuceso;

/**
 * Created by Jeffer on 02/03/2016.
 */
public class SASucesoImp implements SASuceso {

    private DBHelper mDBHelper;

    private DBHelper getHelper() {
        if (mDBHelper == null) {
            mDBHelper = OpenHelperManager.getHelper(Contexto.getInstancia().getContext().getApplicationContext(), DBHelper.class);
        }
        return mDBHelper;
    }
    @Override
    public void editarUsuario() {

    }

    @Override
    public void ayudaUsuario() {

    }

    @Override
    public void consultarInforme() {

    }

    @Override
    public List<Evento> consultarEventos() {
        Log.d("Info", "consultaEventos");
        Dao<Evento, Integer> eventos;
        List<Evento> listaEventos = null;
        try {
            eventos = getHelper().getEventoDao();

            listaEventos= eventos.queryForAll();
        } catch (SQLException e) {

        }
        return listaEventos;
    }

    @Override
    public void consultarReto() {

    }

    @Override
    public void mostrarAlarma() {

    }

    @Override
    public void responderPregunta() {

    }

    @Override
    public void responderReto(Integer respuesta) {
        Dao<Reto, Integer> dao;
        //Dao dao;
        try {
            dao = getHelper().getRetoDao();
            Reto nuevojiji= new Reto();
            nuevojiji.setNombre("LO QUE SEA");
            nuevojiji.setContador(0);
            dao.create(nuevojiji);


            Reto nuevoReto= dao.queryForId(1);
            nuevoReto.setContador(nuevoReto.getContador()+respuesta);
            dao.update(nuevoReto);
        } catch (SQLException e) {
        }
    }

    @Override
    public void sincronizar() {

    }
}
