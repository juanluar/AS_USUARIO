/**
 * 
 */
package es.ucm.as_usuario.negocio.usuario.imp;

import es.ucm.as_usuario.negocio.usuario.SAUsuario;
import es.ucm.as_usuario.negocio.usuario.TransferUsuario;
import es.ucm.as_usuario.negocio.usuario.Usuario;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author Jeffer
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class SAUsuarioImp implements SAUsuario {
	/** 
	 * (sin Javadoc)
	 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 * @param datos
	 */
	public TransferUsuario editarUsuario(TransferUsuario datos) {
		Usuario.getInstancia().setNombre(datos.getNombre());
		Usuario.getInstancia().setFrecuenciaRecibirInforme(datos.getFrecuenciaRecibirInforme());
		TransferUsuario ret = new TransferUsuario();
		ret.setNombre(Usuario.getInstancia().getNombre());
		ret.setFrecuenciaRecibirInforme(Usuario.getInstancia().getFrecuenciaRecibirInformes());
		return ret;
	}

	/** 
	 * (sin Javadoc)
	 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void ayudaUsuario() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}

	/** 
	 * (sin Javadoc)
	 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void consultarInforme() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}

	/** 
	 * (sin Javadoc)
	 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void consultarTareasPuntuales() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}

	/** 
	 * (sin Javadoc)
	 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void consultarReto() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}

	/** 
	 * (sin Javadoc)
	 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void mostrarAlarma() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}

	/** 
	 * (sin Javadoc)
	 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void responderPregunta() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}

	/** 
	 * (sin Javadoc)
	 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void responderReto() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}

	/** 
	 * (sin Javadoc)
	 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	 */
	public void sincronizar() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente

		// end-user-code
	}

	@Override
	public TransferUsuario usuarioActivo() {
		TransferUsuario ret = new TransferUsuario();
		ret.setNombre(Usuario.getInstancia().getNombre());
		ret.setFrecuenciaRecibirInforme(Usuario.getInstancia().getFrecuenciaRecibirInformes());
		return ret;
	}
}