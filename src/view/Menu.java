package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import compiler.Starter;
import resources.Tag;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu {

	private JFrame frame;
	private JRadioButton rdbtnSuccesstest;
	private JRadioButton rdbtnSuccessTest2;
	private JRadioButton rdbtnSuccessTest3;
	private JRadioButton rdbtnFailtest1;
	private JRadioButton rdbtnFailtest2;
	private JRadioButton rdbtnFailtest3;
	private JButton btnTest;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.frame.setVisible(true);
					window.frame.setResizable(false);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblTrttt = new JLabel("Lexer/Parser Tester");
		lblTrttt.setBounds(165, 24, 137, 23);
		frame.getContentPane().add(lblTrttt);
		
		rdbtnSuccesstest = new JRadioButton("successTest1");
		rdbtnSuccesstest.setBounds(51, 90, 121, 23);
		frame.getContentPane().add(rdbtnSuccesstest);
		
		rdbtnSuccessTest2 = new JRadioButton("successTest2");
		rdbtnSuccessTest2.setBounds(51, 132, 121, 23);
		frame.getContentPane().add(rdbtnSuccessTest2);
		
		rdbtnSuccessTest3 = new JRadioButton("successTest3");
		rdbtnSuccessTest3.setBounds(51, 168, 121, 23);
		frame.getContentPane().add(rdbtnSuccessTest3);
		
		rdbtnFailtest1 = new JRadioButton("failTest1");
		rdbtnFailtest1.setBounds(279, 90, 121, 23);
		frame.getContentPane().add(rdbtnFailtest1);
		
		rdbtnFailtest2 = new JRadioButton("failTest2");
		rdbtnFailtest2.setBounds(279, 132, 121, 23);
		frame.getContentPane().add(rdbtnFailtest2);
		
		rdbtnFailtest3 = new JRadioButton("failTest3");
		rdbtnFailtest3.setBounds(279, 168, 121, 23);
		frame.getContentPane().add(rdbtnFailtest3);
		
		btnTest = new JButton("execute");
		btnTest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnExecuteMouseClicked(e);
			}
		});
		
		btnTest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnTest.setBounds(322, 230, 98, 25);
		frame.getContentPane().add(btnTest);
	}
	
	private void btnExecuteMouseClicked(java.awt.event.MouseEvent evt) {
				
		if(rdbtnSuccesstest.isSelected()) {
			System.out.println("\n-------Lexer-------\n");
			new Starter(Tag.class.getResource("test_file/success/test_success1").toString().replace("/bin", "/src").replace("file:", ""));
		}else if(rdbtnSuccessTest2.isSelected()) {
			new Starter(Tag.class.getResource("test_file/success/test_success2").toString().replace("/bin", "/src").replace("file:", ""));
		}else if(rdbtnSuccessTest3.isSelected()) {
			new Starter(Tag.class.getResource("test_file/success/test_success3").toString().replace("/bin", "/src").replace("file:", ""));
		}else if(rdbtnFailtest1.isSelected()) {
			new Starter(Tag.class.getResource("test_file/fail/test_fail1").toString().replace("/bin", "/src").replace("file:", ""));
		}else if(rdbtnFailtest2.isSelected()) {
			new Starter(Tag.class.getResource("test_file/fail/test_fail2").toString().replace("/bin", "/src").replace("file:", ""));
		}else if(rdbtnFailtest3.isSelected()) {
			new Starter(Tag.class.getResource("test_file/fail/test_fail3").toString().replace("/bin", "/src").replace("file:", ""));
		}
	}
}
