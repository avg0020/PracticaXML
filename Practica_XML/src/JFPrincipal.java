import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JFPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	String ruta;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFPrincipal frame = new JFPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 *holaaaaa
	 *soy albeerto
	 */
	public JFPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCargar = new JButton("Cargar XML");
		btnCargar.setFont(new Font("Comic Sans MS", Font.PLAIN, 19));
		btnCargar.setBounds(200, 101, 200, 50);
		contentPane.add(btnCargar);
		btnCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser();
				
				FileNameExtensionFilter filterXml = new FileNameExtensionFilter("XML","xml");
				fc.setFileFilter(filterXml);
		        
				//Muestra la ventana de selecionar fichero
		        int resultado = fc.showOpenDialog(null);
		        
		        //Obtiene la ruta si se selecciona un fichero
		        if (resultado == JFileChooser.APPROVE_OPTION)
		        {
		            ruta = fc.getSelectedFile().getAbsolutePath();
		        }
			}
		});
		
		JButton btnMostrar = new JButton("Mostrar Valores");
		btnMostrar.setFont(new Font("Comic Sans MS", Font.PLAIN, 19));
		btnMostrar.setBounds(200, 241, 200, 50);
		contentPane.add(btnMostrar);
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(ruta);
			}
		});
		
		JButton btnGuardar = new JButton("Guardar Valores");
		btnGuardar.setFont(new Font("Comic Sans MS", Font.PLAIN, 19));
		btnGuardar.setBounds(200, 171, 200, 50);
		contentPane.add(btnGuardar);
		
		//Botón Salir
		JButton btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Comic Sans MS", Font.PLAIN, 19));
		btnSalir.setBounds(200, 311, 200, 50);
		contentPane.add(btnSalir);
		
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	
}
