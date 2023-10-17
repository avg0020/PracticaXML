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
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class JFPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JDmostrarXML jdXML = new JDmostrarXML(this, true);
	private JDmostrarText jdText = new JDmostrarText(this, true);
	private JPanel contentPane;
	String rutaXML = "", rutaText = "", rutaSave = "";

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

		// Botón procesar XML
		JButton btnProcesar = new JButton("Procesar XML");
		btnProcesar.setFont(new Font("Comic Sans MS", Font.PLAIN, 19));
		btnProcesar.setBounds(200, 10, 200, 50);
		contentPane.add(btnProcesar);
		btnProcesar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// Creamos la instancia de dbf
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					// Creamos un nuevo documento vacío a partir de dicha instancia
					DocumentBuilder db = dbf.newDocumentBuilder();
					// Creamos un objeto File con nuestro fichero XML
					File fichXML = new File("clases.xml");
					// Creamos el arbol DOM a partir del fichero
					Document doc = db.parse(fichXML);
					// normalize. Elimina nodos vacíos y combina nodos adyacentes en caso de que los
					// hubiera
					doc.getDocumentElement().normalize();

					// Elemento raíz
					System.out.println("Elemento raiz: " + doc.getDocumentElement().getNodeName() + "\n");
					jdXML.setTextAreaText("Elemento raiz: " + doc.getDocumentElement().getNodeName() + "\n");
					// Nodo clase
					NodeList listaClases = doc.getElementsByTagName("clase");

					// Recorremos los nodos de clase
					for (int i = 0; i < listaClases.getLength(); i++) {
						// Cada item de la lista es un nodo
						Node nodo = listaClases.item(i);
						// Hacemos un casting a Element ya que cada nodo es un Element
						Element elem = (Element) nodo;
						// Mostramos el valor del atributo de la etiqueta animal
						System.out.println("	ELemento: " + elem.getNodeName() + " id: " + elem.getAttribute("id"));
						jdXML.setTextAreaText(
								"	ELemento: " + elem.getNodeName() + " id: " + elem.getAttribute("id") + "\n");

						// clase tiene 4 nodos, nombre, icono, numSpecs y specs
						NodeList hijosClase = elem.getChildNodes();
						// Recorremos los hijos de nodos de equipo
						for (int j = 0; j < hijosClase.getLength(); j++) {
							Node hijoClase = hijosClase.item(j);

							// Comprobamos tipos de los nodos hijos. Si son element mostramos el valor
							if (hijoClase.getNodeType() == Node.ELEMENT_NODE
									&& "nombre".equals(hijoClase.getNodeName())) {
								System.out.println("		Etiqueta: " + hijoClase.getNodeName() + " Valor: "
										+ hijoClase.getTextContent());
								jdXML.setTextAreaText("		Etiqueta: " + hijoClase.getNodeName() + " Valor: "
										+ hijoClase.getTextContent() + "\n");
							}
							if (hijoClase.getNodeType() == Node.ELEMENT_NODE
									&& "icono".equals(hijoClase.getNodeName())) {
								System.out.println("		Etiqueta: " + hijoClase.getNodeName() + " Valor: "
										+ hijoClase.getTextContent());
								System.out
										.println("    		Atributo: " + ((Element) hijoClase).getAttribute("enlace"));
								jdXML.setTextAreaText("		Etiqueta: " + hijoClase.getNodeName() + " Valor: "
										+ hijoClase.getTextContent() + "\n");
								jdXML.setTextAreaText(
										"    		Atributo: " + ((Element) hijoClase).getAttribute("enlace") + "\n");
							}
							if (hijoClase.getNodeType() == Node.ELEMENT_NODE
									&& "numSpecs".equals(hijoClase.getNodeName())) {
								System.out.println("	Etiqueta: " + hijoClase.getNodeName() + " Valor: "
										+ hijoClase.getTextContent());
								jdXML.setTextAreaText("	Etiqueta: " + hijoClase.getNodeName() + " Valor: "
										+ hijoClase.getTextContent() + "\n");
							}
							if (hijoClase.getNodeType() == Node.ELEMENT_NODE
									&& "specs".equals(hijoClase.getNodeName())) {
								System.out.println("	Etiqueta: " + hijoClase.getNodeName());
								jdXML.setTextAreaText("	Etiqueta: " + hijoClase.getNodeName() + "\n");

								// Specs tiene varias spec
								Element elemSpecs = (Element) hijoClase;
								NodeList hijosSpecs = elemSpecs.getChildNodes();

								for (int k = 0; k < hijosSpecs.getLength(); k++) {
									Node hijoSpecs = hijosSpecs.item(k);

									// Comprobamos tipos de los nodos hijos. Si son element mostramos el valor
									if (hijoSpecs.getNodeType() == Node.ELEMENT_NODE
											&& "spec".equals(hijoSpecs.getNodeName())) {
										System.out.println("		Etiqueta: " + hijoSpecs.getNodeName());
										jdXML.setTextAreaText("		Etiqueta: " + hijoSpecs.getNodeName() + "\n");

										// Spec tiene nombre, rol, facilidad
										Element elemSpec = (Element) hijoSpecs;
										NodeList hijosSpec = elemSpec.getChildNodes();

										for (int l = 0; l < hijosSpec.getLength(); l++) {
											Node hijoSpec = hijosSpec.item(l);

											if (hijoSpec.getNodeType() == Node.ELEMENT_NODE
													&& "nombre".equals(hijoSpec.getNodeName())) {
												System.out.println("			Etiqueta: " + hijoSpec.getNodeName()
														+ " Valor: " + hijoSpec.getTextContent());
												jdXML.setTextAreaText("			Etiqueta: " + hijoSpec.getNodeName()
														+ " Valor: " + hijoSpec.getTextContent() + "\n");
											}
											if (hijoSpec.getNodeType() == Node.ELEMENT_NODE
													&& "rol".equals(hijoSpec.getNodeName())) {
												System.out.println("			Etiqueta: " + hijoSpec.getNodeName()
														+ " Valor: " + hijoSpec.getTextContent());
												jdXML.setTextAreaText("			Etiqueta: " + hijoSpec.getNodeName()
														+ " Valor: " + hijoSpec.getTextContent() + "\n");
											}
											if (hijoSpec.getNodeType() == Node.ELEMENT_NODE
													&& "facilidad".equals(hijoSpec.getNodeName())) {
												System.out.println("			Etiqueta: " + hijoSpec.getNodeName()
														+ " Valor: " + hijoSpec.getTextContent());
												jdXML.setTextAreaText("			Etiqueta: " + hijoSpec.getNodeName()
														+ " Valor: " + hijoSpec.getTextContent() + "\n");
											}
										}
									}
								}
							}
						}

						System.out.println(" ");
						jdXML.setTextAreaText("\n");
					}

					// Mensaje de exito al cargar
					JOptionPane.showMessageDialog(null, "Archivo cargado correctamente");
				} catch (ParserConfigurationException | SAXException | IOException ex) {
					ex.printStackTrace();
				}
			}
		});

		// Botón de cargar archivo xml
		JButton btnCargar = new JButton("Cargar XML");
		btnCargar.setFont(new Font("Comic Sans MS", Font.PLAIN, 19));
		btnCargar.setBounds(200, 70, 200, 50);
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
					rutaXML = fc.getSelectedFile().getAbsolutePath();

					// Comprueba que se haya puesto un xml
					if (!rutaXML.endsWith(".xml")) {
						// Mensaje de archivo incorrecto
						JOptionPane.showMessageDialog(null, "Archivo no valido", "Archivo incorrecto",
								JOptionPane.ERROR_MESSAGE);
					} else {
						try {
							// Creamos la instancia de dbf
							DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
							// Creamos un nuevo documento vacío a partir de dicha instancia
							DocumentBuilder db = dbf.newDocumentBuilder();
							// Creamos un objeto File con nuestro fichero XML
							File fichXML = new File(rutaXML);
							// Creamos el arbol DOM a partir del fichero
							Document doc = db.parse(fichXML);
							// normalize. Elimina nodos vacíos y combina nodos adyacentes en caso de que los
							// hubiera
							doc.getDocumentElement().normalize();

							// Obtenemos el elemento raiz
							jdXML.setTextAreaText("Elemento Raiz :" + doc.getDocumentElement().getNodeName());
							System.out.println("Elemento Raiz :" + doc.getDocumentElement().getNodeName());
							System.out.println("----------");

							// Este método va recorrer el arbol y nos va devolver su contenido
							if (doc.hasChildNodes()) {
								printXML(doc.getChildNodes(), jdXML);
							}

							// Mensaje de exito al cargar
							JOptionPane.showMessageDialog(null, "Archivo cargado correctamente");
						} catch (ParserConfigurationException | SAXException | IOException ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		});

		// Botón mostrar la información cargada
		JButton btnMostrar = new JButton("Mostrar Valores");
		btnMostrar.setFont(new Font("Comic Sans MS", Font.PLAIN, 19));
		btnMostrar.setBounds(200, 130, 200, 50);
		contentPane.add(btnMostrar);
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jdXML.getTextAreaText().isEmpty()) {
					// Mensaje que se mostrara si no se ha cargado un archivo previamente
					JOptionPane.showMessageDialog(null, "No hay ningun XML", "Alerta", JOptionPane.ERROR_MESSAGE);
				} else {
					// Muestra el JDialog
					jdXML.setVisible(true);
				}
			}
		});

		// Botón de para guardar el contenido a txt
		JButton btnGuardar = new JButton("Guardar Valores");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (jdXML.getTextAreaText().isEmpty()) {
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
							rutaSave = fc.getSelectedFile().getAbsolutePath() + ".txt";
							FileWriter fw = new FileWriter(new File(rutaSave));
							fw.write(jdXML.getTextAreaText());
							fw.close();
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		btnGuardar.setFont(new Font("Comic Sans MS", Font.PLAIN, 19));
		btnGuardar.setBounds(200, 205, 200, 50);
		contentPane.add(btnGuardar);

		// Botón mostrar valores del txt
		JButton btnLeerTxt = new JButton("Leer txt");
		btnLeerTxt.setFont(new Font("Comic Sans MS", Font.PLAIN, 19));
		btnLeerTxt.setBounds(200, 325, 200, 50);
		contentPane.add(btnLeerTxt);
		btnLeerTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Ventana de elección de archivos
				JFileChooser fc = new JFileChooser();

				// Filtro para que solo muestre archivos xml
				FileNameExtensionFilter filterXml = new FileNameExtensionFilter("Text", "txt");
				fc.setFileFilter(filterXml);

				// Muestra la ventana de selecionar fichero
				int resultado = fc.showOpenDialog(null);

				// Obtiene la ruta si se selecciona un fichero
				if (resultado == JFileChooser.APPROVE_OPTION) {
					rutaText = fc.getSelectedFile().getAbsolutePath();
				}
				// Comprueba que se haya puesto un xml
				if (!rutaText.endsWith(".txt")) {
					// Mensaje de archivo incorrecto
					JOptionPane.showMessageDialog(null, "Archivo no valido", "Archivo incorrecto",
							JOptionPane.ERROR_MESSAGE);
				} else {
					File file = new File(rutaText);
					try {
						BufferedReader br = new BufferedReader(new FileReader(file));
						String texto;

						// Comprueba si se puede leer y lee linea a linea
						if (file.canRead()) {
							texto = br.readLine();
							while (texto != null) {
								jdText.setTextAreaText(texto + "\n");
								texto = br.readLine();
							}
							br.close();
							jdText.setVisible(true);
						} else {
							System.out.println("No se puede leer");
						}

					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});

		// Muestra el txt anterior
		JButton btnTxtAnterior = new JButton("Mostrar txt");
		btnTxtAnterior.setFont(new Font("Comic Sans MS", Font.PLAIN, 19));
		btnTxtAnterior.setBounds(200, 265, 200, 50);
		contentPane.add(btnTxtAnterior);
		btnTxtAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = new File(rutaSave);
				try {
					BufferedReader br = new BufferedReader(new FileReader(file));
					String texto;

					// Comprueba si se puede leer y lee linea a linea
					if (file.canRead()) {
						texto = br.readLine();
						while (texto != null) {
							jdText.setTextAreaText(texto + "\n");
							texto = br.readLine();
						}
						br.close();
						jdText.setVisible(true);
					} else {
						System.out.println("No se puede leer");
					}

				} catch (IOException ex) {
					JOptionPane.showMessageDialog(null, "Archivo no disponible", "Archivo incorrecto",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// Botón Salir del programa
		JButton btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Comic Sans MS", Font.PLAIN, 19));
		btnSalir.setBounds(200, 403, 200, 50);
		contentPane.add(btnSalir);
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		this.setLocationRelativeTo(null);
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
