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
                    	if(factorVelocidadSalida==10){}
                    	else{
                    	//por defecto genera una plaza cada segundo
                            Thread.sleep(1000*factorVelocidadSalida);
                            parking.salidaVehiculo();}
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