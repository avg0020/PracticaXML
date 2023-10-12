import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class JFPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	String ruta="", xml="";

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
	 */
	public JFPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JDmostrarXML jd = new JDmostrarXML(this, true);
		
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
				if (ruta.isEmpty()) {					
					JOptionPane.showMessageDialog(null,"No hay ningun XML","Alerta",JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						// Creamos la instancia de dbf
						DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
						// Creamos un nuevo documento vacío a partir de dicha instancia
						DocumentBuilder db = dbf.newDocumentBuilder();
						// Creamos un objeto File con nuestro fichero XML
						File fichXML = new File(ruta);
						// Creamos el arbol DOM a partir del fichero
						Document doc = db.parse(fichXML);
						// normalize. Elimina nodos vacíos y combina nodos adyacentes en caso de que los
						// hubiera
						doc.getDocumentElement().normalize();
						
						xml="Elemento Raiz :" + doc.getDocumentElement().getNodeName()+"\n"+"----------";
						System.out.println("Elemento Raiz :" + doc.getDocumentElement().getNodeName());
						System.out.println("----------");

						if (doc.hasChildNodes()) {
							printNote(doc.getChildNodes(),xml);
						}
						
						//Muestra el JDialog
						jd.setVisible(true);
					} catch (ParserConfigurationException | SAXException | IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		
		JButton btnGuardar = new JButton("Guardar Valores");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("A partir de aquí");
				System.out.println(xml);
			}
		});
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
	
	public static String printNote(NodeList nodeList, String text) {

		for (int count = 0; count < nodeList.getLength(); count++) {

			Node tempNode = nodeList.item(count);
			
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				text+="\nElemento: "+tempNode.getNodeName();
				System.out.println("Elemento: "+tempNode.getNodeName());
				// get node name and value
				if (!tempNode.getTextContent().contains("\n")) {
					text+="\n	Valor: " + tempNode.getTextContent();
					System.out.println("	Valor: " + tempNode.getTextContent());
				}
				
				if (tempNode.hasAttributes()) {
					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
						text+="\nAtributo: " + node.getNodeName() + "\n	Valor: " + node.getNodeValue();
						System.out.println(
								"Atributo: " + node.getNodeName() + "\n	Valor: " + node.getNodeValue());
					}
				}
				
				if (tempNode.hasChildNodes()) {
					// loop again if has child nodes
					printNote(tempNode.getChildNodes(),text);
				}
			}

		}
		return text;
	}
}
