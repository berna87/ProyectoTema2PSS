package proyecto;

import java.awt.Color;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;



public class Parking {
	private int plazasDisponibles;
	private int cochesEnEspera;
	private int capacidad;
	private boolean lleno;
	private boolean vacio;
	private JLabel etiquetaEstadoParking ;
	private JLabel etiquetacochesEnEspera;
	private JLabel etiquetaplazasDisponibles;
	private JTextArea logs;
	private int contadorLogs=1;
	private JButton botonVerde;
	private JButton botonRojo;
	
	/**
	 * 
	 * @param capacidad Cantidad de vehiculos que cogen en el parking
	 */
	public Parking(int capacidad, JLabel etiquetaEstadoParking, JLabel etiquetacochesEnEspera, JLabel etiquetaplazasDisponibles, JTextArea logs,
			JButton botonVerde, JButton botonRojo) {
		//partimos de que el parking esta vacio y que tiene todas sus plazas libres
		this.capacidad = capacidad;
		this.plazasDisponibles = capacidad/2;
		this.lleno = false;
		this.vacio = true;
		this.etiquetacochesEnEspera = etiquetacochesEnEspera;
		this.etiquetaEstadoParking = etiquetaEstadoParking;
		this.etiquetaplazasDisponibles = etiquetaplazasDisponibles;
		this.logs = logs;
		this.botonVerde=botonVerde;
		this.botonRojo=botonRojo;
	}
	/**
	 *  Genera una plaza disponible en el parking
	 */
	public synchronized void salidaVehiculo(){
		//en el caso de que el estado del parking sea vacio (tiene todas sus plazas disponibles)
		//el hilo esperará
		while(this.vacio){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//cuando haya entrado un vehiculo y por tanto, haya ocupado una plaza, el hilo que utilice este metodo
		//continuará generando plazas
		this.plazasDisponibles++;
		etiquetaEstadoParking.setText("Libre");
		botonVerde.setBackground(Color.GREEN);
		botonRojo.setBackground(Color.GRAY);
		this.lleno = false;
		//si el numero de plazas disponibles es igual a su capacidad significa que esta vacio y por tanto no puede generar mas plazas
		this.vacio = this.plazasDisponibles == this.capacidad;
		String aux = logs.getText();
		logs.setText(contadorLogs+"- Ha salido un vehiculo\n"+aux);
		contadorLogs++;
		if(cochesEnEspera>0){
			int vehiculosEntraron = plazasDisponibles;
			cochesEnEspera = cochesEnEspera - plazasDisponibles;
			plazasDisponibles = 0;
			this.lleno = true;
			botonVerde.setBackground(Color.GRAY);
			botonRojo.setBackground(Color.RED);
			this.vacio = false;
			etiquetaplazasDisponibles.setText(plazasDisponibles+"");
			etiquetaEstadoParking.setText("Lleno");
			etiquetacochesEnEspera.setText(cochesEnEspera+"");
			aux = logs.getText();
			logs.setText(contadorLogs+" - Vehiculos que entraron : "+vehiculosEntraron+"\n"+aux);
			contadorLogs++;
		}
		
		etiquetaplazasDisponibles.setText(""+this.plazasDisponibles);
	}
	
	/**
	 * 
	 * @return cantidad de vehiculos que entran al parking
	 */
	
	//el hilo que utilice este metodo no quedara parado, unicamente puede despertar a los demas hilos
	//puesto que en el caso de que haya algun sitio en el parking, entrará ocupando tantas plazas como pueda y tenga en espera
	//este metodo devolverá el numero de coches que consiguieron entrar al parking
	public synchronized void llegaVehiculo(){
		String aux = logs.getText();
		logs.setText(contadorLogs+" - Ha llegado un vehiculo \n"+aux);
		contadorLogs++;
		int cochesEntraron = 0;
		this.cochesEnEspera++;
		if(!this.lleno){
			//en el caso de que el numero de plazas disponibles sea mayor que los coches que haya en espera
			//entraran todos los que haya en espera y esta cantidad se restará del numero de plazas disponibles
			if(plazasDisponibles > cochesEnEspera){
				plazasDisponibles = plazasDisponibles - cochesEnEspera;
				cochesEntraron = cochesEnEspera;
				cochesEnEspera = 0;
				
				this.vacio = false;
			}else{
				//si el numero de coches en espera es igual o superior al numero de plazas disponibles
				//se ocuparan todas las plazas y estas se reduciran a 0, volviendo al estado de lleno
				//y los coches que quedaran en espera serán la cantidad anterior menos las plazas disponibles en el parking
				cochesEntraron = plazasDisponibles;
				cochesEnEspera = cochesEnEspera - plazasDisponibles;
				plazasDisponibles = 0;
				this.lleno = true;
				botonVerde.setBackground(Color.GRAY);
				botonRojo.setBackground(Color.RED);
				etiquetaEstadoParking.setText("Lleno");
				this.vacio = false;
			}
			//como ambas situaciones modifican el estado de vacio a false, despertarán si hay algun hilo intentando producir nuevas plazas para
			//que sigan haciendolo
			aux = logs.getText();
			logs.setText(contadorLogs+" - Vehiculos que entraron : "+ cochesEntraron+"\n"+aux);
			contadorLogs++;
			notifyAll();
		}
		etiquetacochesEnEspera.setText(""+this.cochesEnEspera);
		etiquetaplazasDisponibles.setText(""+this.plazasDisponibles);
	}
}
