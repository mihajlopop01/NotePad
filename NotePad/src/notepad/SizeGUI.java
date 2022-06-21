package notepad;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SizeGUI extends JFrame {

	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JTextField txtSize;
	private JButton btnEnter;

	

	/**
	 * Create the frame.
	 */
	public SizeGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 203, 125);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getLblNewLabel());
		contentPane.add(getTxtSize());
		contentPane.add(getBtnEnter());
	}
	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Set size:");
			lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 16));
			lblNewLabel.setBounds(42, 34, 72, 16);
		}
		return lblNewLabel;
	}
	private JTextField getTxtSize() {
		if (txtSize == null) {
			txtSize = new JTextField();
			txtSize.setBounds(118, 30, 42, 26);
			txtSize.setColumns(10);
		}
		return txtSize;
	}
	private JButton getBtnEnter() {
		if (btnEnter == null) {
			btnEnter = new JButton("Enter");
			btnEnter.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				    int x = Integer.parseInt(txtSize.getText());
				    if (x < 0 || x > 100)
				    	JOptionPane.showConfirmDialog(null, "Enter a size between 0 and 100.");
				    NotePadGUI.s = x;
				    NotePadGUI.textArea.setFont(new Font(NotePadGUI.textArea.getFont().getFamily(), Font.PLAIN, NotePadGUI.s));
				    dispose();
				}
			});
			btnEnter.setBounds(42, 68, 117, 29);
		}
		return btnEnter;
	}
}
