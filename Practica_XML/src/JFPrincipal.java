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
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class JFPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JDmostrarXML jd = new JDmostrarXML(this, true);
	private JPanel contentPane;
	String ruta = "", xml = "";

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
		// Parametros del JFrame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Botón de cargar archivo xml
		JButton btnCargar = new JButton("Cargar XML");
		btnCargar.setFont(new Font("Comic Sans MS", Font.PLAIN, 19));
		btnCargar.setBounds(200, 101, 200, 50);
		contentPane.add(btnCargar);
		btnCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Ventana de elección de archivos
				JFileChooser fc = new JFileChooser();

				// Filtro para que solo muestre archivos xml
				FileNameExtensionFilter filterXml = new FileNameExtensionFilter("XML", "xml");
				fc.setFileFilter(filterXml);

				// Muestra la ventana de selecionar fichero
				int resultado = fc.showOpenDialog(null);

				// Obtiene la ruta si se selecciona un fichero
				if (resultado == JFileChooser.APPROVE_OPTION) {
					ruta = fc.getSelectedFile().getAbsolutePath();
				}
				//Comprueba que se haya puesto un xml
				if (!ruta.endsWith(".xml")) {
					//Mensaje de archivo incorrecto
					JOptionPane.showMessageDialog(null, "Archivo no valido", "Archivo incorrecto", JOptionPane.ERROR_MESSAGE);
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

						// Obtenemos el elemento raiz
						jd.setTextAreaText("Elemento Raiz :" + doc.getDocumentElement().getNodeName());
						System.out.println("Elemento Raiz :" + doc.getDocumentElement().getNodeName());
						System.out.println("----------");

						// Este método va recorrer el arbol y nos va devolver su contenido
						if (doc.hasChildNodes()) {
							printXML(doc.getChildNodes(), jd);
						}
						
						//Mensaje de exito al cargar
						JOptionPane.showMessageDialog(null, "Archivo cargado correctamente");
					} catch (ParserConfigurationException | SAXException | IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});

		// Botón mostrar la información cargada
		JButton btnMostrar = new JButton("Mostrar Valores");
		btnMostrar.setFont(new Font("Comic Sans MS", Font.PLAIN, 19));
		btnMostrar.setBounds(200, 241, 200, 50);
		contentPane.add(btnMostrar);
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ruta.isEmpty()) {
					// Mensaje que se mostrara si no se ha cargado un archivo previamente
					JOptionPane.showMessageDialog(null, "No hay ningun XML", "Alerta", JOptionPane.ERROR_MESSAGE);
				} else {
					// Muestra el JDialog
					jd.setVisible(true);
				}
			}
		});

		// Botón de para guardar el contenido a txt
		JButton btnGuardar = new JButton("Guardar Valores");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ruta.isEmpty()) {
					// Mensaje que se mostrara si no se ha cargado un archivo previamente
					JOptionPane.showMessageDialog(null, "No hay ningun XML", "Alerta", JOptionPane.ERROR_MESSAGE);
				} else {
					try {
						JFileChooser fc = new JFileChooser();
						// Filtro para que se guarde txt
						FileNameExtensionFilter filtertxt = new FileNameExtensionFilter("Text", "txt");
						fc.setFileFilter(filtertxt);
						// Hacemos que aparezca un fichero por defecto
						fc.setSelectedFile(null);
						// Mostramos la ventana de seleccionar el fichero
						int resultado = fc.showSaveDialog(null);
						// Si se ha seleccionado un fichero, si se pulsa cancelar no se ejecuta
						if (resultado == JFileChooser.APPROVE_OPTION) {
							// Obtenemos la ruta del fichero
							String ruta2 = fc.getSelectedFile().getAbsolutePath();
							FileWriter fw = new FileWriter(new File(ruta2 + ".txt"));
							fw.write(jd.getTextAreaText());
							fw.close();
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		btnGuardar.setFont(new Font("Comic Sans MS", Font.PLAIN, 19));
		btnGuardar.setBounds(200, 171, 200, 50);
		contentPane.add(btnGuardar);

		// Botón Salir del programa
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

	// Método para recorrer el xml y obtener los datos
	public static void printXML(NodeList nodeList, JDmostrarXML jd) {
		// for para recorrer todos los nodos
		for (int i = 0; i < nodeList.getLength(); i++) {

			Node tempNode = nodeList.item(i);

			// Asegura que es un elemento
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				// Obtiene el nombre del nodo
				jd.setTextAreaText("\nElemento: " + tempNode.getNodeName());
				System.out.println("Elemento: " + tempNode.getNodeName());
				// Comprueba que no haya más nodos en el contenido
				if (!tempNode.getTextContent().contains("\n")) {
					// Muestra el contenido del nodo
					jd.setTextAreaText("\n Valor: " + tempNode.getTextContent());
					System.out.println("	Valor: " + tempNode.getTextContent());
				}

				// Comprueba si tiene atributos
				if (tempNode.hasAttributes()) {
					// Devuelve los nombres de atributo y su valor
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int j = 0; j < nodeMap.getLength(); j++) { // Por si hay varios atributos
						Node node = nodeMap.item(j);
						jd.setTextAreaText("\nAtributo: " + node.getNodeName() + "\n Valor: " + node.getNodeValue());
						System.out.println("Atributo: " + node.getNodeName() + "\n	Valor: " + node.getNodeValue());
					}
				}

				// Comprueba si hay más nodos hijos que recorrer
				if (tempNode.hasChildNodes()) {
					// Se ejcuta a si mismo de nuevo
					printXML(tempNode.getChildNodes(), jd);
				}
			}
		}
	}
}
