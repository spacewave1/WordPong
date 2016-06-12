package gui;

import infrastructure.ConnectionController;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import listeners.TextFieldListener;

/**
 * The dialogcard, that asks for the Port the Host want to use to get a
 * connection. When pressing the hostBtn, a Thread will be created and lets some
 * other player connect to the host. While waiting the Information to connect
 * will be output at the HostGUI
 * 
 * @author Wavy, Fjiz
 * @version 0.7
 */
public class HostCard extends JPanel {

	private JTextField addressField = new JTextField(15);
	private JButton hostBtn = new JButton("Host Server");
	private JTextArea hostInfo;
	private int port;

	/**
	 * Creates the Hostcard, that asks for a Port number and
	 * checks if the input is a valid port number. Afterwards
	 * a new ServerSocket is created.
	 * 
	 * @param the
	 *            parent frame
	 */
	public HostCard(GameFrame gframe) {

		// Sets Layout
		setLayout(new FlowLayout());

		// Adds Components
		add(hostBtn, FlowLayout.LEFT);
		add(addressField, FlowLayout.LEFT);
		// Adds Listeners
		addressField.addFocusListener(new TextFieldListener("Enter Port", addressField));
		hostBtn.addActionListener(new ActionListener() {
			// Bin zu bloed den Defaultfocus auf den Button zu legen
			public void actionPerformed(ActionEvent arg0) {
				try {
					port = Integer.parseInt(addressField.getText());
					if (port < 1025) {
						addressField.setText("invalid port");
					} else {
						host();
					}
				} catch (NumberFormatException f) {
					addressField.setText("invalid input");
				}

			}
		});
	}

	/**
	 * Changes the state of the Dialog to output the Information to connect to
	 * the server
	 */
	private void host() {

		try {

			// Adjust Dialog < funktioniert nicht 'Fjiz'
			hostBtn.setVisible(false);
			addressField.setVisible(false);

			// Create HostInfo < funktioniert nicht 'Fjiz'
			hostInfo = new JTextArea(2, 3);
			hostInfo.setEditable(false);
			add(hostInfo, FlowLayout.LEFT);
			String hostIP = InetAddress.getLocalHost().getHostAddress();
			hostInfo.setText("IP: " + hostIP + "\nPort: " + port);


			// Alles folgende findet erst mit Client statt 'fjiz'

			ConnectionController playerController = new ConnectionController();
			playerController.hostGame();
			// Threads!!

		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println("Unknown Host Socket");
		} catch (IOException e) {
			System.out.println("IO Exception Socket");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}