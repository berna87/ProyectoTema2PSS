package proyecto;



public class LlegadaVehiculo extends Thread {
	private Parking parking;

	public LlegadaVehiculo(Parking parking) {
		this.parking = parking;
	}
	 @Override
	    public void run() {    
	            while (true){
	                    try {
	                    	//por defecto llega un vehiculo cada segundo
	                            Thread.sleep(1000);
	                            parking.llegaVehiculo();
	                           
	                    } catch (InterruptedException e) {
	                            // TODO Auto-generated catch block
	                            e.printStackTrace();
	                    }
	            }
	    }
}
