package buchung;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * View-Klasse, dient als GUI und Schnittstelle zum Model.
 * 
 * @author Gurparkash Singh, Viktor Mandelbauer
 * @since 26/09/17
 */
public class FlugView extends JPanel {
	private JPanel contentPane;
	private JTextField tfHost;
	private JTextField tfPort;
	private JTextField tfUname;
	private JTextField tfPw;
	private JTextField tfVn;
	private JTextField tfNn;
	private JTextField tfSR;
	private JTextField tfSP;

	/**
	 * Konstruktor Frames mittels WindowBuilder erstellt.
	 */
	public FlugView() {
		// 1. Frame fuers Einloggen auf die DB.
		JFrame login = new JFrame("Login");
		login.setAlwaysOnTop(true);
		login.setResizable(false);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.setBounds(100, 100, 350, 300);
		// Main Panel fuers Interface
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		login.setContentPane(contentPane);
		contentPane.setLayout(null);
		// JLabel fuer 1. Frame Login
		JLabel lblHostName = new JLabel("Host:");
		lblHostName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblHostName.setBounds(115, 59, 46, 14);
		contentPane.add(lblHostName);

		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPort.setBounds(116, 84, 46, 14);
		contentPane.add(lblPort);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblUsername.setBounds(77, 109, 78, 29);
		contentPane.add(lblUsername);

		JLabel lblPasswort = new JLabel("Passwort:");
		lblPasswort.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPasswort.setBounds(81, 148, 78, 14);
		contentPane.add(lblPasswort);
		// JTextField fuer 1. Frame Login
		tfHost = new JTextField("localhost");
		tfHost.setBounds(163, 58, 101, 20);
		contentPane.add(tfHost);
		tfHost.setColumns(10);

		tfPort = new JTextField("3306");
		tfPort.setColumns(10);
		tfPort.setBounds(163, 84, 101, 20);
		contentPane.add(tfPort);

		tfUname = new JTextField("testing");
		tfUname.setColumns(10);
		tfUname.setBounds(163, 115, 101, 20);
		contentPane.add(tfUname);

		tfPw = new JPasswordField("12345");
		tfPw.setColumns(10);
		tfPw.setBounds(163, 147, 101, 20);
		contentPane.add(tfPw);
		//// Buttons fuer 1. Frame Login
		JButton btnBestaetigen = new JButton("Best\u00E4tigen");
		btnBestaetigen.setBounds(163, 189, 101, 29);
		contentPane.add(btnBestaetigen);

		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(57, 189, 101, 29);
		contentPane.add(btnClear);
		// Leert die TextFelder
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfHost.setText("");
				tfPort.setText("");
				tfUname.setText("");
				tfPw.setText("");
			}
		});
		// Fuehrt den Login aus und oeffnet das Frame fuer das Flug Buchen.
		btnBestaetigen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Zuerst wird das Login Fenster versteckt und erstellt das 2.
				// Frame flugziel.
				login.dispose();
				FlugModel model = new FlugModel(getTfUname(), getTfPw(), getTfHost(), getTfPort());
				JFrame flugziel = new JFrame("Buchen");
				flugziel.setResizable(false);
				flugziel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				flugziel.setBounds(100, 100, 900, 550);
				// Erstellt das 3. Frame booking wird benoetig, als GUI fuers
				// Buchen eines Fluges
				JFrame booking = new JFrame("Booking");
				booking.setResizable(false);
				booking.setBounds(100, 100, 350, 300);

				contentPane = new JPanel();
				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				flugziel.setContentPane(contentPane);
				contentPane.setLayout(null);
				// das Fenster fuer booking
				JPanel bookingPane = new JPanel();
				bookingPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				booking.setContentPane(bookingPane);
				bookingPane.setLayout(null);
				// booking jlabel
				JLabel lblVorName = new JLabel("Vorname:");
				lblVorName.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblVorName.setBounds(75, 59, 76, 14);
				bookingPane.add(lblVorName);

				JLabel lblNachName = new JLabel("Nachname:");
				lblNachName.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblNachName.setBounds(65, 84, 85, 14);
				bookingPane.add(lblNachName);

				JLabel lblSeatRow = new JLabel("Sitzreihe:");
				lblSeatRow.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblSeatRow.setBounds(77, 109, 78, 29);
				bookingPane.add(lblSeatRow);

				JLabel lblSeatPos = new JLabel("Sitzplatzposition:");
				lblSeatPos.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblSeatPos.setBounds(23, 148, 130, 14);
				bookingPane.add(lblSeatPos);
				// booking JTextFields
				tfVn = new JTextField("Max");
				tfVn.setBounds(163, 58, 101, 20);
				bookingPane.add(tfVn);
				tfVn.setColumns(10);

				tfNn = new JTextField("Mustermann");
				tfNn.setColumns(10);
				tfNn.setBounds(163, 84, 101, 20);
				bookingPane.add(tfNn);

				tfSR = new JTextField("36");
				tfSR.setColumns(10);
				tfSR.setBounds(163, 115, 101, 20);
				bookingPane.add(tfSR);

				tfSP = new JTextField("A");
				tfSP.setColumns(10);
				tfSP.setBounds(163, 147, 101, 20);
				bookingPane.add(tfSP);
				// booking Buttons
				JButton btnBuchen = new JButton("Best\u00E4tigen");
				btnBuchen.setBounds(163, 189, 101, 29);
				bookingPane.add(btnBuchen);

				JButton btnBookingClear = new JButton("Clear");
				btnBookingClear.setBounds(57, 189, 101, 29);
				bookingPane.add(btnBookingClear);
				btnBookingClear.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						tfVn.setText("");
						tfNn.setText("");
						tfSR.setText("");
						tfSP.setText("");
					}
				});

				JLabel lblLand = new JLabel("Land");
				lblLand.setBounds(29, 105, 46, 14);
				contentPane.add(lblLand);

				JLabel lblFlughafen = new JLabel("Flughafen");
				lblFlughafen.setBounds(191, 105, 66, 14);
				contentPane.add(lblFlughafen);

				JLabel lblLandZiel = new JLabel("Land");
				lblLandZiel.setBounds(406, 105, 46, 14);
				contentPane.add(lblLandZiel);

				JLabel lblFlughafenZiel = new JLabel("Flughafen");
				lblFlughafenZiel.setBounds(568, 105, 71, 14);
				contentPane.add(lblFlughafenZiel);

				JLabel lblStart = new JLabel("Start:");
				lblStart.setFont(new Font("Tahoma", Font.BOLD, 16));
				lblStart.setBounds(119, 50, 76, 14);
				contentPane.add(lblStart);

				JLabel lblZiel = new JLabel("Ziel:");
				lblZiel.setFont(new Font("Tahoma", Font.BOLD, 16));
				lblZiel.setBounds(552, 50, 76, 14);
				contentPane.add(lblZiel);
				flugziel.setVisible(true);

				flugziel.addWindowListener(new WindowListener() {
					public void windowOpened(WindowEvent e) {

					}

					@Override
					public void windowActivated(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowClosed(WindowEvent e) {
						System.exit(0);

					}

					@Override
					public void windowClosing(WindowEvent e) {
						model.dbClose();

					}

					@Override
					public void windowDeactivated(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowDeiconified(WindowEvent e) {
						// TODO Auto-generated method stub

					}

					@Override
					public void windowIconified(WindowEvent e) {
						// TODO Auto-generated method stub

					}
				});

			}
		});

		JLabel lblBitteMitDer = new JLabel("Bitte mit der Datenbank verbinden!");
		lblBitteMitDer.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBitteMitDer.setBounds(35, 11, 299, 14);
		contentPane.add(lblBitteMitDer);

		login.setVisible(true);
	}

	public String getTfHost() {
		return tfHost.getText();
	}

	public int getTfPort() {
		int port = 0;
		if (tfPort.getText().equals(""))
			JOptionPane.showMessageDialog(null, "Bitte Port eingeben!");
		else
			port = Integer.parseInt(tfPort.getText());
		return port;
	}

	public String getTfUname() {
		return tfUname.getText();
	}

	public String getTfPw() {
		return tfPw.getText();
	}

}