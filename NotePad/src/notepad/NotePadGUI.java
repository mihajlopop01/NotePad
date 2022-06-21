package notepad;

import java.awt.event.*;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuKeyEvent;
import javax.swing.event.MenuKeyListener;
import javax.swing.plaf.FontUIResource;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.FlowLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;

public class NotePadGUI extends JFrame implements ActionListener, KeyListener {

	private JPanel contentPane;
	private JMenuBar bar;
	private JMenu file;
	private JMenuItem save;
	private JMenuItem open;
	private JMenuItem exit;
	private JMenu format;
	private JMenu font;
	private JMenuItem color;
	private JMenuItem size;
	public static JTextArea textArea;
	private JMenu edit;
	private JMenu help;
	private JMenuItem shortcuts;
	private JMenuItem undo;
	private JMenuItem redo;
	private JMenuItem ariel;
	private JMenuItem monaco;
	private JMenuItem comicSansMS;
	private JMenuItem tahoma;
	public Stack<String> redoStack;
	public String xNew = null; // sluzi za redo i undo
	public String x;
	public String[] xArray;
	public List<String> xList;
	public static int s = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NotePadGUI frame = new NotePadGUI();
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
	public NotePadGUI() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 911, 461);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.add(getTextArea());

		bar = new JMenuBar();
		file = new JMenu("File");
		format = new JMenu("Format");
		edit = new JMenu("Edit");
		help = new JMenu("Help");
		bar.add(file);
		bar.add(format);
		bar.add(edit);
		bar.add(help);
		save = new JMenuItem("Save");
		save.addActionListener(this);
		open = new JMenuItem("Open");
		open.addActionListener(this);
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		size = new JMenuItem("Size");
		size.addActionListener(this);
		color = new JMenuItem("Color");
		color.addActionListener(this);
		font = new JMenu("Font");
		ariel = new JMenuItem("Ariel");
		ariel.addActionListener(this);
		comicSansMS = new JMenuItem("Comic Sans MS");
		comicSansMS.addActionListener(this);
		tahoma = new JMenuItem("Tahoma");
		tahoma.addActionListener(this);
		monaco = new JMenuItem("Monaco");
		monaco.addActionListener(this);

		undo = new JMenuItem("Undo");
		undo.addActionListener(this);
		redo = new JMenuItem("Redo");
		redo.addActionListener(this);

		
		shortcuts = new JMenuItem("Shortcuts");
		shortcuts.addActionListener(this);

		file.add(save);
		file.add(open);
		file.add(exit);
		format.add(font);
		font.add(ariel);
		font.add(comicSansMS);
		font.add(tahoma);
		font.add(monaco);
		help.add(shortcuts);

		format.add(size);
		format.add(color);
		edit.add(undo);
		edit.add(redo);

		setJMenuBar(bar);

		redoStack = new Stack<String>();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == open) {

			open();

		}

		if (e.getSource() == color) {
			JColorChooser colorChooser = new JColorChooser();
			Color color = colorChooser.showDialog(null, "Choose color: ", Color.black); // boja koju bira korisnik
			textArea.setForeground(color);
		}
		if (e.getSource() == save) {
			save();

		}

		if (e.getSource() == exit) {
			System.exit(0);
		}

		if (e.getSource() == ariel) {
			textArea.setFont(new Font("Ariel", Font.PLAIN, textArea.getFont().getSize()));
		}
		if (e.getSource() == comicSansMS) {
			textArea.setFont(new Font("Comic Sans MS", Font.PLAIN, textArea.getFont().getSize()));
		}
		if (e.getSource() == tahoma) {
			textArea.setFont(new Font("Tahoma", Font.PLAIN, textArea.getFont().getSize()));
		}
		if (e.getSource() == monaco) {
			textArea.setFont(new Font("Monaco", Font.PLAIN, textArea.getFont().getSize()));
		}

		if (e.getSource() == size) {
			SizeGUI sGui = new SizeGUI();
			sGui.setVisible(true);
			textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, s));

		}
		if (e.getSource() == shortcuts) {
			ShortcutsGUI shortcutsGUI = new ShortcutsGUI();
			shortcutsGUI.setVisible(true);
			

		}
		

		if (e.getSource() == undo) {

			x = textArea.getText();
			xArray = x.split(" ");
			xList = new ArrayList<String>();
			xList = ArrayToListConversion(xArray);
			redoStack.push(xList.get(xList.size() - 1));
			xList.remove(xList.size() - 1);
			xNew = String.join(" ", xList);
			textArea.setText(xNew);

		}
		if (e.getSource() == redo) {
			StringBuilder builder = new StringBuilder();
			builder.append(xNew);
			if (!redoStack.isEmpty())
				builder.append(" " + redoStack.pop());

			textArea.setText(builder.toString());

		}

	}

	private List<String> ArrayToListConversion(String[] xArray) {
		List<String> list = new ArrayList<String>();
		for (String x : xArray) {
			list.add(x);

		}
		System.out.println(list);
		return list;
	}

	private JTextArea getTextArea() {
		if (textArea == null) {
			textArea = new JTextArea();
			textArea.setBounds(0, 0, 911, 417);
			textArea.addKeyListener(this);
		}
		return textArea;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S)
			save();
		if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_O)
			open();
		if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_E)
			System.exit(0);
			

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void save() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("."));
		int responce = fileChooser.showSaveDialog(null);
		if (responce == JFileChooser.APPROVE_OPTION) {
			File file;
			PrintWriter fileOut = null;

			file = new File(fileChooser.getSelectedFile().getAbsolutePath());
			try {
				fileOut = new PrintWriter(file);
				fileOut.println(textArea.getText());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				fileOut.close();
			}
		}
	}

	public void open() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File(".")); // vodi ga na project folder

		int responce = fileChooser.showOpenDialog(null);
		if (responce == JFileChooser.APPROVE_OPTION) {
			File file;
			Scanner reader;

			file = new File(fileChooser.getSelectedFile().getAbsolutePath());
			try {
				reader = new Scanner(file);
				while (reader.hasNextLine()) {
					String line = reader.nextLine() + "\n";
					textArea.append(line);
				}

			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}
}
