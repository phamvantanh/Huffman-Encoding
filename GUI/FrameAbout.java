package project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrameAbout extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameAbout frame = new FrameAbout();
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
	public FrameAbout() {
		setResizable(false);
		setTitle("ABOUT US");
		setBounds(100, 100, 393, 335);
		getContentPane().setLayout(null);
		
		// Tạo vùng Text hiển thị thông tin
		JTextArea textAbout = new JTextArea();
		textAbout.setLineWrap(true);
		textAbout.setWrapStyleWord(true);
		textAbout.setTabSize(1);
		textAbout.setRows(100);
		textAbout.setText("\t\t\t\t\t\tHUFFMAN - ỨNG DỤNG MÃ HÓA NÉN DỮ LIỆU\r\n\r\n\t\t\t\t\t\t\t\t"
				+ "Ứng dụng được thực hiện bởi :\r\n\r\n\t\t\t\t\t\t\t\t     "
				+ "Người hướng dẫn   : \r\n\r\n\t\t\t\t\t\t\t\t\t   "
				+ "Thầy Phạm Minh Tuấn\r\n\t\t\t\t\t\t "
				+ "Team Mentor công ty DAC Tech VietNam\r\n\t\t\t\t\t\t\t\t\t\t\r\n\t\t\t\t\t\t\t\t\t    "
				+ "Sinh viên thực hiện :\r\n  \r\n\t\t\t\t\t\t\tPhạm Văn Tánh - Lớp 18TCLC_Nhật\r\n\t\t\t           \t"
				+ "Phan Văn Ngọc Hiếu - Lớp 18TCLC_Nhật\r\n\t\t\t\t\t\t\t"
				+ "Lê Hoàng Bảo Long - Lớp 18TCLC_Nhật\r\n\r\n\r\n");
		textAbout.setDisabledTextColor(new Color(0, 0, 0));
		textAbout.setBackground(new Color(240, 240, 240));
		textAbout.setBounds(10, 10, 357, 241);
		getContentPane().add(textAbout);
		 
		// Tạo button Exit và set sự kiện
		JButton btExit = new JButton("EXIT");
		btExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btExit.setBounds(157, 262, 89, 23);
		getContentPane().add(btExit);
	}
}
