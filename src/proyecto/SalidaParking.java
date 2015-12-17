package proyecto;

public class SalidaParking extends Thread{
 
    private Parking parking;
    private int factorVelocidadSalida;
    public SalidaParking(Parking p) {
            this.parking = p;
            this.factorVelocidadSalida = 5;
    } 

    @Override
    public void run() {    
            while (true){
                    try {
                    	if(factorVelocidadSalida==10){
                    		Thread.sleep(1000);
                    	}else{
                            parking.salidaVehiculo();
                            Thread.sleep(1000*factorVelocidadSalida);
                    	}
                    } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                    }
            }
    }
	public void setFactorVelocidadSalida(int factorVelocidadSalida) {
		this.factorVelocidadSalida = factorVelocidadSalida;
	}
}