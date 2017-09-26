package buchung;

import java.awt.EventQueue;

/**
 * Main Klasse, fuehrt die View aus.
 * 
 * @author Gurparkash
 *
 */
public class FlugMain {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlugView frame = new FlugView();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
