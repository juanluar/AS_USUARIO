package es.ucm.as.negocio.suceso;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.List;

import es.ucm.as.negocio.usuario.TransferUsuario;

public interface SASuceso {


    // Reto

    public TransferReto consultarReto();

    public Integer responderReto(Integer respuesta);

    public void crearReto(TransferReto r);

    public void eliminarReto();

    public void cargarReto(TransferReto nuevoReto);


    // Tarea

    public List<TransferTarea> consultarTareas();

    public void cargarTareas(List<TransferTarea> tareas);

    public void responderTarea(TransferTarea transferTarea);

    public List<TransferTarea> consultarTareasNotificaciones();

    public void actualizarNotificaciones();

    public void actualizarNotificacionTarea(TransferTarea transferTarea);


    // Correo

    public String generarPDF() throws IOException, DocumentException;


    // Evento

    public void crearEventos(List<TransferEvento> eventosTutor);

    public void modificarEventos(List<TransferEvento> eventosRespuesta);

    public void eliminarEventos();

    public List<TransferEvento> consultarEventos();

    public List<TransferEvento> consultarEventosNotificaciones();

    public List<TransferTarea> consultarTareasNotificacionesFinde();
}