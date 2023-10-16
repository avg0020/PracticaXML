import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class JDmostrarText extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextArea textArea;

	/**
	 * Create the dialog.
	 */
	public JDmostrarText(JFPrincipal parent, boolean modal) {
		//Parametros del JDialog
		super(parent, modal);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			//Panel de bootnes
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				//Botón
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					//Cierra el JDialog
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			
			//TextArea
			textArea = new JTextArea();
			textArea.setLineWrap(true);
			
			//ScrollPane para que se visualice todo el texto del textArea
			JScrollPane scrollPane = new JScrollPane(textArea);
			getContentPane().add(scrollPane, BorderLayout.CENTER);
		}
	}
	
	//Método para pasarle información del Frame principal al JDialog
	public void setTextAreaText(String text) {
		textArea.setText(textArea.getText()+text);
	}
	
	//Método para pasar al frame principal el contenido del textArea
	public String getTextAreaText() {
		return textArea.getText();
	}
}
