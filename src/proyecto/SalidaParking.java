package proyecto;

public class SalidaParking extends Thread{
 
    private Parking parking;
    
    public SalidaParking(Parking p) {
            this.parking = p;
    } 
    
    @Override
    public void run() {    
            while (true){
                    try {
                    	//por defecto genera una plaza cada segundo
                            Thread.sleep(1000);
                            parking.salidaVehiculo();
                    } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                    }
            }
    }
}