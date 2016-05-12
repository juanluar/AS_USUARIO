package es.ucm.as.negocio.suceso;

import com.itextpdf.text.DocumentException;

import java.io.IOException;
import java.util.List;

public interface SASuceso {

    // Evento

    public List<TransferEvento> consultarEventos();

    public void guardarEventos(List<TransferEvento> eventosRespuesta);


    // Reto

    public TransferReto consultarReto();

    public Integer responderReto(Integer respuesta);

    public void crearReto(TransferReto r);

    public void eliminarReto();

    public void cargarReto(TransferReto nuevoReto);


    // Tarea

    public List<TransferTarea> consultarTareas();

    public void cargarTareas(List<TransferTarea> tareas);


    // Correo

    public String generarPDF() throws IOException, DocumentException;

}