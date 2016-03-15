package es.ucm.as_usuario.negocio.suceso.imp;

import android.util.Log;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.ucm.as_usuario.integracion.DBHelper;
import es.ucm.as_usuario.integracion.Parser;
import es.ucm.as_usuario.negocio.suceso.Evento;
import es.ucm.as_usuario.negocio.suceso.Reto;
import es.ucm.as_usuario.negocio.suceso.SASuceso;
import es.ucm.as_usuario.negocio.suceso.Tarea;
import es.ucm.as_usuario.negocio.suceso.TransferReto;
import es.ucm.as_usuario.negocio.suceso.TransferTarea;
import es.ucm.as_usuario.negocio.utils.Frecuencia;
import es.ucm.as_usuario.presentacion.Contexto;

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
    public List<Evento> consultarEventos() {

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
    public TransferReto verReto() {
        Dao<Reto, Integer> dao;
        Reto reto = new Reto();
        TransferReto tr = new TransferReto();
        try {
            dao = getHelper().getRetoDao();

            if (dao.idExists(1)) {  // comprueba si hay algun reto en la BBDD
                reto = (Reto) dao.queryForId(1);
                tr.setContador(reto.getContador());
                tr.setId(reto.getId());
                tr.setNombre(reto.getNombre());
                tr.setSuperado(reto.getSuperado());
            }else
                return null;

        } catch (SQLException e) {

        }
        return tr;
    }


    @Override
    public Integer responderReto(Integer respuesta) {
        Dao<Reto, Integer> dao;
        Reto reto = new Reto();
        try {
            dao = getHelper().getRetoDao();
            reto = (Reto) dao.queryForId(1);
            if (!reto.equals(null)) {
                //Si el reto no esta superado y se puede incrementar o decrementar aun se modifica
                if (!reto.getSuperado() && respuesta == -1 && reto.getContador() > 0 ||
                        !reto.getSuperado() && respuesta == 1 && reto.getContador() <= 30) {
                    reto.setContador(reto.getContador() + respuesta);
                    dao.update(reto);
                }
                if (reto.getContador() == 30) {
                    reto.setSuperado(true);
                    dao.update(reto);
                }
            }else {
                reto = new Reto();
                reto.setNombre("NINGUNO");
                reto.setContador(0);
                reto.setSuperado(false);
                dao.update(reto);
            }
        } catch (SQLException e) {

        }
        return reto.getContador();
    }


    @Override
    public List<TransferTarea> consultarTareas() {

        Dao<Tarea, Integer> tareasDao;
        List<Tarea> tareas = new ArrayList<Tarea>();
        List<TransferTarea> transferTareas = new ArrayList<TransferTarea>();

        try {
            Log.e("SASuceso", "consulta tareas");

            tareasDao = getHelper().getTareaDao();

            tareas = tareasDao.queryForAll();
            for(int i = 0; i < tareas.size(); i++){

                TransferTarea tt = new TransferTarea();
                tt.setId(tareas.get(i).getId());
                tt.setContador(tareas.get(i).getContador());
                tt.setTextoPregunta(tareas.get(i).getTextoPregunta());
                tt.setTextoAlarma(tareas.get(i).getTextoAlarma());
                tt.setHoraPregunta(tareas.get(i).getHoraPregunta());
                tt.setHoraAlarma(tareas.get(i).getHoraAlarma());
                tt.setMejorar(tareas.get(i).getMejorar());
                tt.setFrecuenciaTarea(tareas.get(i).getFrecuenciaTarea());
                tt.setNoSeguidos(tareas.get(i).getNoSeguidos());
                tt.setNumNo(tareas.get(i).getNumNo());
                tt.setNumSi(tareas.get(i).getNumSi());
                transferTareas.add(tt);
            }

        } catch (SQLException e) {

        }
        return transferTareas;
    }

    @Override
    public void responderTarea(String respuestaTarea) {
        Dao<Tarea, Integer> dao;
        Tarea tarea = new Tarea();
        Log.e("prueba", "Lo que llega es ..." + respuestaTarea);
        String[] sol = respuestaTarea.split("_");
        //sol[0] tiene la respuesta
        int respuesta = Integer.parseInt(sol[0]);
        //sol[1] tiene el id de la tarea a buscar
        int idTarea = Integer.parseInt(sol[1]);
        /*
        try {
            dao = getHelper().getTareaDao();
            Log.e("prueba", "Se busca en la database con el id " + idTarea);
            tarea = (Tarea) dao.queryForId(idTarea);
            if (!tarea.equals(null)) {
                //Si la tarea llega al maximo contador se tiene que cambiar la frecuencia de la tarea
                if((tarea.getContador() == 0 && respuesta != -1) ||
                        (tarea.getContador() > 0 && tarea.getContador() <= 30))
                    tarea.setContador(tarea.getContador() + respuesta);
                    dao.update(tarea);
                if(tarea.getContador() == 30){
                    // cambiar frecuencia
                    dao.update(tarea);
                }
            }else {
                Log.e("prueba", "No hay tareas en la database o ha pasado otra cosa");
            }
        } catch (SQLException e) {
        }*/
    }

    @Override
    public void cargarTareasBBDD() {
        Parser p = new Parser();
        Dao<Tarea, Integer> tareaDao;
        p.readTareas();   // lee del fichero y obtiene un ArrayList de tareas
        ArrayList<Tarea> tareasBBDD = p.getTareas();
        for (int i = 0; i < tareasBBDD.size(); i++){
            try {
                tareaDao = getHelper().getTareaDao();
                tareaDao.create(tareasBBDD.get(i));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void cargarRetoBBDD() {
        Parser p = new Parser();
        Dao<Reto, Integer> retoDao;
        String texto = p.readReto();   // lee del fichero y obtiene el texto del reto
        try {
            retoDao = getHelper().getRetoDao();
            Reto reto = p.toReto(texto);
            if (reto != null && !retoDao.idExists(1))
                retoDao.create(reto);
            else
                Log.e("IMPOSIBLE CARGAR RETO", "Ya hay uno");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
