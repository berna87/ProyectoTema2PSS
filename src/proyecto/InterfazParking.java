package proyecto;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InterfazParking {

	private JFrame frmParking;
	private JLabel lblLibreOcupado;
	private JLabel CochesEnEspera;
	private JLabel lblPlazasDisponibles;
	private JTextArea textAreaLogs;
	private int factorLlegada =5;
	private int factorSalida = 5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfazParking window = new InterfazParking();
					window.frmParking.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InterfazParking() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmParking = new JFrame();
		frmParking.setTitle("Parking");
		frmParking.setResizable(false);
		frmParking.setBounds(100, 100, 632, 512);
		frmParking.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		lblLibreOcupado = new JLabel("Libre");
		CochesEnEspera = new JLabel("xxx");
		lblPlazasDisponibles = new JLabel("xxx");
		textAreaLogs = new JTextArea();
		JLabel lblSaleUnVehículo = new JLabel("Sale un veh\u00EDculo cada 5 segundos");
		JLabel lblLlegaUnVehculo = new JLabel("Llega un veh\u00EDculo cada 5 segundos");
		
		String capacidadParkingString = JOptionPane.showInputDialog(frmParking, "Introduce la capacidad del parking","Capacidad",JOptionPane.DEFAULT_OPTION); 
		
		//en el caso de que el usuario introduzca algo distinto de 1, 2 o 3 digitos el programa finalizará
		if(capacidadParkingString == null || !capacidadParkingString.matches("^\\d{1,3}")|| capacidadParkingString.equals("0")){
			JOptionPane.showMessageDialog(frmParking, "Debe introducir una capacidad \n entre 1 y 999");
			System.exit(0);
		}
		int capacidadParking = Integer.parseInt(capacidadParkingString);
		//Inicializa la clase parking con sus correspondientes atributos
		Parking p = new Parking(capacidadParking, lblLibreOcupado, CochesEnEspera, lblPlazasDisponibles, textAreaLogs);
		//Inicializa las clases SalidaParking y LlegadaVehiculo
		LlegadaVehiculo llegadavehiculo = new LlegadaVehiculo(p);
		SalidaParking salidaparking = new SalidaParking(p);
		
		
		JPanel panel = new JPanel();
		
		JPanel panel_1 = new JPanel();
		
		JPanel panel_2 = new JPanel();
		
		JPanel panel_3 = new JPanel();
		GroupLayout groupLayout = new GroupLayout(frmParking.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 377, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(22, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
						.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JLabel lblNewLabel_3 = new JLabel("Salida de coches");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblVelocidad = new JLabel("Velocidad");
		lblVelocidad.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnNewButton = new JButton("-");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(factorSalida<10){
					factorSalida++;
					salidaparking.setFactorVelocidadSalida(factorSalida);
					lblSaleUnVehículo.setText("Sale un vehículo cada "+ factorSalida+" segundos");
					if(factorSalida==10){
						lblSaleUnVehículo.setText("Han dejado de salir vehiculos");
						
					} 
				}
			}
		});
		
		JButton btnNewButton_1 = new JButton("+");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(factorSalida>1){
					factorSalida--;
					salidaparking.setFactorVelocidadSalida(factorSalida);
					lblSaleUnVehículo.setText("Sale un vehículo cada "+ factorSalida+" segundos");
				}
			}
		});
		
		
		
		JButton btnForzarSalida = new JButton("Forzar salida de veh\u00EDculo");
		btnForzarSalida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!lblPlazasDisponibles.getText().equals(capacidadParkingString)){
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							p.salidaVehiculo();
						}
					}).start();
				}
			}
		});
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSaleUnVehículo, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
						.addComponent(lblVelocidad, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
						.addComponent(lblNewLabel_3, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
						.addComponent(btnForzarSalida, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_3)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblVelocidad)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnNewButton_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblSaleUnVehículo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnForzarSalida)
					.addContainerGap(47, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		JLabel lblLogs = new JLabel("Logs");
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
						.addComponent(lblLogs, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblLogs)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		
		scrollPane.setViewportView(textAreaLogs);
		panel_3.setLayout(gl_panel_3);
		
		JLabel lblEstadoParking = new JLabel("Estado Parking : ");
		
		JLabel lbl1 = new JLabel("Coches esperando :");
		
		JLabel lblNewLabel_2 = new JLabel("Plazas disponibles :");
		
		
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(lblNewLabel_2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblEstadoParking, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(lbl1, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPlazasDisponibles, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
						.addComponent(CochesEnEspera, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
						.addComponent(lblLibreOcupado, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEstadoParking, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLibreOcupado, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(CochesEnEspera, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
						.addComponent(lbl1, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
						.addComponent(lblPlazasDisponibles, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		
		JLabel lblNewLabel = new JLabel("Llegada de veh\u00EDculos");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("Velocidad");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnDisminuirVelocidadLlegada = new JButton("-");
		btnDisminuirVelocidadLlegada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(factorLlegada<10){
					factorLlegada++;
					llegadavehiculo.setFactorVelocidadLlegada(factorLlegada);
					lblLlegaUnVehculo.setText("Llega un veh\u00EDculo cada "+factorLlegada+" segundos");
					if(factorLlegada==10){
						lblLlegaUnVehculo.setText("Han dejado de llegar vehiculos");
					}
					
				}
			}
		});
		
		JButton btnAumentarVelocidadLlegada = new JButton("+");
		btnAumentarVelocidadLlegada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(factorLlegada>1){
					factorLlegada--;
					lblLlegaUnVehculo.setText("Llega un veh\u00EDculo cada "+factorLlegada+" segundos");
					llegadavehiculo.setFactorVelocidadLlegada(factorLlegada);
				}
			}
		});
		
		
		lblLlegaUnVehculo.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnForzarLlegada = new JButton("Forzar llegada de vehiculo");
		btnForzarLlegada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						p.llegaVehiculo();
					}
				}).start();
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblLlegaUnVehculo, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnDisminuirVelocidadLlegada, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAumentarVelocidadLlegada, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
						.addComponent(btnForzarLlegada, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDisminuirVelocidadLlegada)
						.addComponent(btnAumentarVelocidadLlegada))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLlegaUnVehculo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnForzarLlegada)
					.addContainerGap(47, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		frmParking.getContentPane().setLayout(groupLayout);
		llegadavehiculo.start();
		salidaparking.start();

	}
	
}
