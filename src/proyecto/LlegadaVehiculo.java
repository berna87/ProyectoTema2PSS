package proyecto;



public class LlegadaVehiculo extends Thread {
	private Parking parking;
	private int factorVelocidadLlegada;
	public LlegadaVehiculo(Parking parking) {
		this.parking = parking;
		this.factorVelocidadLlegada = 5;
	}
	
	 @Override
    public void run() {    
	    while (true){
	            try {
	            	if(factorVelocidadLlegada==10){
	            		Thread.sleep(1000);
	            	}
	            	else{
	                    parking.llegaVehiculo();
	                    Thread.sleep(1000*factorVelocidadLlegada);
	            	}                          
	            } catch (InterruptedException e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	            }
	    }
    }
	public void setFactorVelocidadLlegada(int factorVelocidadLlegada) {
		this.factorVelocidadLlegada = factorVelocidadLlegada;
	}
}
