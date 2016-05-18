package es.ucm.as.presentacion.controlador.comandos.imp;

import android.util.Log;

import java.util.List;

import es.ucm.as.negocio.conexion.ConectionManager;
import es.ucm.as.negocio.conexion.msg.Mensaje;
import es.ucm.as.negocio.factoria.FactoriaSA;
import es.ucm.as.negocio.suceso.SASuceso;
import es.ucm.as.negocio.suceso.TransferEvento;
import es.ucm.as.negocio.suceso.TransferReto;
import es.ucm.as.negocio.suceso.TransferTarea;
import es.ucm.as.negocio.usuario.SAUsuario;
import es.ucm.as.negocio.usuario.TransferUsuario;
import es.ucm.as.presentacion.controlador.comandos.Command;
import es.ucm.as.presentacion.controlador.comandos.exceptions.commandException;

/**
 * Created by msalitu on 10/03/2016.
 */
public class SincronizarRegistroComando implements Command{
    @Override
    public Object ejecutaComando(Object datos) throws commandException {

        boolean terminado = false;
        Mensaje msg = (Mensaje) datos;
        ConectionManager conectionManager;
        conectionManager = new ConectionManager(msg);

        if(conectionManager.getResponse() != null) {
            // Se procesa cada parte del mensaje
            Mensaje respuestaTutor = conectionManager.getResponse();
            SASuceso saSuceso = FactoriaSA.getInstancia().nuevoSASuceso();
            saSuceso.cargarReto(respuestaTutor.getReto());
            saSuceso.cargarTareas(respuestaTutor.getTareas());
            saSuceso.crearEventos(respuestaTutor.getEventos());

            terminado = conectionManager.getResponse() != null;
            conectionManager.reset();
        }
        if(terminado)
            return msg.getUsuario();
        else
            return null;
    }
}
