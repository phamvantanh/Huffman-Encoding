package project;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

public class FrameEncode extends JFrame implements ActionListener {
	
	// Khai báo biến
	private JPanel panelResult;
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenuItem menuItemExit;
	private JScrollPane scrollPaneResult;
	private JTextArea txtResultEncode;
	private JScrollPane scrollPaneTable;
	private JTable tableEncode;
	private JMenuItem menuItemOpen;
	private JScrollPane scrollPaneInput;
	private JTextArea textEncodeString;
	private JPanel panelButton;
	private JButton buttonEncode;
	private JButton buttonBack;
	private JScrollPane scrollPaneHistory;
	private JTable tableHistory;
	private JMenuItem menuItemHelp;
	private DefaultTableModel model,modelHistory;
	
	// Kết nối Cơ sở dữ liệu thông qua Class ConnectDB();
	ConnectDB cn=new ConnectDB();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameEncode frame = new FrameEncode();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * Tạo Frame Encode
	 */
	public FrameEncode() {
		
		setTitle("ENCODE");			// Set title cho Frame
		setSize(600,558);			// Set size của Frame ( cửa sổ Encode )
		setLocationRelativeTo(null);// Hiển thị cửa sổ ứng dụng lên chính giữa màn hình
		
/*===========================================TẠO MENU BAR=======================================*/		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		//Tạo menuItem "Open" dùng để đọc file input chứa chuỗi cần mã hóa và set sự kiện cho menuItem "Open"
		menuItemOpen = new JMenuItem("Open File");
		menuFile.add(menuItemOpen);
		menuItemOpen.addActionListener(this);
		
		//Tạo menuItem "Help" dùng để hiển thị cách sử dụng chức năng encode để mã hóa kí tự và set sự kiện cho menuItem "Help"
		menuItemHelp = new JMenuItem("Help");
		menuFile.add(menuItemHelp);
		menuItemHelp.addActionListener(this);		
		
		//Tạo menuItem "Exit" dùng để thoát khỏi cửa sổ Encode và set sự kiện cho menuItem "Exit"
		menuItemExit = new JMenuItem("Exit");
		menuFile.add(menuItemExit);
		menuItemExit.addActionListener(this);
		
/*===========================================TẠO CÁC THÀNH PHẦN TRONG GIAO DIỆN CỦA CỬA SỔ ENCODE =======================================*/		
		
		// Tạo JPanel chính chứa các thành phần trong cửa sổ giao diện Encode
		JPanel contentPane = new JPanel();
		contentPane.setAutoscrolls(true);
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 61, 232, 0, 35, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 73, 42, 82, 119, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		// Tạo scrollPaneInput chứa JTextArea để nhập input
		scrollPaneInput = new JScrollPane();
		scrollPaneInput.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Enter the Encode string", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_scrollPaneInput = new GridBagConstraints();
		gbc_scrollPaneInput.gridwidth = 9;
		gbc_scrollPaneInput.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPaneInput.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneInput.gridx = 0;
		gbc_scrollPaneInput.gridy = 1;
		contentPane.add(scrollPaneInput, gbc_scrollPaneInput);
		
		// Tạo textEncodeString chứa input cần mã hóa
		textEncodeString = new JTextArea();
		textEncodeString.setLineWrap(true);
		scrollPaneInput.setViewportView(textEncodeString);
		
		// Tạo panel chứa 2 button "Encode" và "Back"
		panelButton = new JPanel();
		GridBagConstraints gbc_panelButton = new GridBagConstraints();
		gbc_panelButton.gridwidth = 2;
		gbc_panelButton.insets = new Insets(0, 0, 5, 5);
		gbc_panelButton.fill = GridBagConstraints.BOTH;
		gbc_panelButton.gridx = 2;
		gbc_panelButton.gridy = 2;
		contentPane.add(panelButton, gbc_panelButton);
		
		// Tạo button Encode và set sự kiện cho button Encode
		buttonEncode = new JButton("Encode");
		buttonEncode.addActionListener(this);
		panelButton.add(buttonEncode);
		
		// Tạo button Back có chức năng thoát khỏi cửa sổ Encode để quay lại cửa sổ chính của ứng dụng và set sự kiện cho button
		buttonBack = new JButton("Back");
		buttonBack.addActionListener(this);
		panelButton.add(buttonBack);
		
		// Tạo panelResult chứa các thành phần để hiện thị kết quả của quá trình mã hóa dữ liệu
		panelResult = new JPanel();
		panelResult.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Result Encode", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panelResult = new GridBagConstraints();
		gbc_panelResult.insets = new Insets(0, 0, 5, 0);
		gbc_panelResult.gridwidth = 9;
		gbc_panelResult.fill = GridBagConstraints.BOTH;
		gbc_panelResult.gridx = 0;
		gbc_panelResult.gridy = 3;
		contentPane.add(panelResult, gbc_panelResult);
		panelResult.setLayout(new BorderLayout(0, 0));
		
		scrollPaneResult = new JScrollPane();
		panelResult.add(scrollPaneResult, BorderLayout.CENTER);
		
		// Tạo textResult chứa output sau mã hóa
		txtResultEncode = new JTextArea();
		txtResultEncode.setLineWrap(true);
		txtResultEncode.setForeground(Color.BLACK);
		scrollPaneResult.setViewportView(txtResultEncode);
		
		// Tạo scrollPane chứa table để hiển thị kết quả 
		scrollPaneTable = 	new JScrollPane();
		GridBagConstraints gbc_scrollPaneTable = new GridBagConstraints();
		gbc_scrollPaneTable.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPaneTable.gridwidth = 9;
		gbc_scrollPaneTable.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneTable.gridx = 0;
		gbc_scrollPaneTable.gridy = 4;
		contentPane.add(scrollPaneTable, gbc_scrollPaneTable);
		
		// Tạo table hiển thị kết quả ( kí tự, tần số, từ mã biểu diễn kí tự )
		tableEncode = new JTable();
		
		tableEncode.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Symbol", "Frequency", "Codeword"
			}
		));
		scrollPaneTable.setViewportView(tableEncode);
		model = (DefaultTableModel) tableEncode.getModel();
		tableEncode.setAutoCreateRowSorter(true); // thêm chức năng sort theo từng column
		
		// Tạo scrollPane chứa table hiển thị Lịch sử
		scrollPaneHistory = new JScrollPane();
		scrollPaneHistory.setBorder(new TitledBorder(null, "History", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_scrollPaneHistory = new GridBagConstraints();
		gbc_scrollPaneHistory.gridwidth = 9;
		gbc_scrollPaneHistory.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneHistory.gridx = 0;
		gbc_scrollPaneHistory.gridy = 6;
		contentPane.add(scrollPaneHistory, gbc_scrollPaneHistory);
		
		// Table hiển thị Lịch sử
		modelHistory = new DefaultTableModel();
		tableHistory = new JTable(modelHistory);
		// Set sự kiện cho table, với sự kiện ClickMouse ,bấm phím UP hay DOWN sẽ lấy thông tin từ table chứa lịch sử
		tableHistory.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode()== KeyEvent.VK_UP || 
						e.getKeyCode() == KeyEvent.VK_DOWN) {
					getInfo(tableHistory);
				}
			}
		});
		tableHistory.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				getInfo(tableHistory);
			}
		});
	
		scrollPaneHistory.setViewportView(tableHistory);
		// thêm các column vào bảng 
		modelHistory.addColumn("ID");
		modelHistory.addColumn("Input");
        modelHistory.addColumn("Output");
        modelHistory.addColumn("Date Modified");
        cn.getDataEncode(tableHistory, modelHistory); // lấy dữ liệu từ cơ sở dữ liệu hiển thị lên bảng
        tableHistory.setAutoCreateRowSorter(true); //thêm chức năng sort theo từng column
	
	}
	
	/*Hàm getInfo để lấy thông tin từ bảng History và hiện lên input và output */
	private void getInfo(JTable table)
	{
		int index = table.getSelectedRow();
		textEncodeString.setText(table.getValueAt(index, 1).toString()); // hiển thị input
		txtResultEncode.setText(table.getValueAt(index, 2).toString());  // hiển thị output
	}
/*===============================HÀM XỬ LÝ SỰ KIỆN CHO CÁC THÀNH PHẦN ================================*/
	public void actionPerformed(ActionEvent e) {
		
		// Sự kiện cho chức năng Open File
		if(e.getSource()==menuItemOpen) {
			JFileChooser fileChooser = new JFileChooser();
			int rVal = fileChooser.showOpenDialog(null);
			String filePath = "";
			if (rVal == JFileChooser.APPROVE_OPTION) {
			     String filename = fileChooser.getSelectedFile().getName();
			     String directory = fileChooser.getCurrentDirectory().toString();
			     filePath = directory +"\\"+filename;
			  // Đọc file theo đường dẫn (filePath)
					BufferedReader bufferedReader = null;
					
			        try {   
			            bufferedReader = new BufferedReader(new FileReader(filePath));       
			    
			            String textFile=""; // khởi tạo chuỗi String chứa dữ liệu trong file
			            String line;		// khởi tạo chuỗi String chứa dữ liệu theo từng dòng trong file
			            
			            /*Đọc dữ liệu theo dòng và cộng dữ liệu từng dòng vào textFile ta được toàn bộ file*/
			            while ((line = bufferedReader.readLine()) != null) {
			                textFile += line +"\n";
			            }		           
			            textEncodeString.setText(textFile); // Hiển thị dữ liệu trong File lên textEncodeString chứa input cần mã hóa
			        }
			        catch (IOException e1) {
			            JOptionPane.showMessageDialog(null, "Error :"+e1); 
			        } finally {
			            try {
			                bufferedReader.close();
			            } catch (IOException e1) {
			            	JOptionPane.showMessageDialog(null, "Error :"+e1); 
			            }
			        }	     
			 }else {
				 JOptionPane.showMessageDialog(null, "Cancel Open file !!!"); 
			}
		}
		
		// Sự kiện cho chức năng Help
		if(e.getSource()==menuItemHelp) {
			String helpString = "Bước 1 : Nhập chuỗi dữ liệu cần mã hóa bằng một trong các cách sau :\r\n" + 
					"- Nhâp trực tiếp từ mục “ Enter the Encode string”.\r\n" + 
					"- Nhập từ file bằng cách chọn File -> Open File.\r\n" + 
					"- Sử dụng lại input trước đây : \r\n" + 
					"                      Chọn từ bảng lịch sử để có thể xem và sửa lại chuỗi string đã từng sử dụng\r\n" + 
					"Bước 2 : Nhấn nút “Encode” để sử dụng chức năng Encode của ứng dụng, mã hóa chuỗi kí tự thành dãy bit\r\n" + 
					"-----Chuỗi Result Encode được lưu trong file .txt theo đường dẫn-----\r\n"+
					"D:\\Study\\Nam2\\HocKy2\\DALTUD\\DoAn\\History\\tenfile.txt\r\n" + 
					"------Danh sách tần số được lưu trong file .bin theo đường dẫn-------\r\n" + 
					"D:\\Study\\Nam2\\HocKy2\\DALTUD\\DoAn\\History\\tenfile.bin\r\n" +
					"Lưu ý : tenfile được lưu mặc định theo ngày giờ thực hiện encode" ;
			JOptionPane.showMessageDialog(null, helpString);
		}
		
		// Sự kiện cho chức năng Exit
		if(e.getSource()==menuItemExit) {
			setVisible(false);
		}
		
		// Sự kiện cho button Back
		if(e.getSource()==buttonBack) {
			setVisible(false);
		}
		
		//Sự kiện cho button Encode
		if(e.getSource()==buttonEncode) {
			model.setRowCount(0);
			try {
				Huffman.buildHuffman(textEncodeString.getText());
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "Mã hóa thất bại !! Thử lại");
			}
			 // thực hiện mã hóa
			txtResultEncode.setText(Huffman.kq.toString());	  // hiển thị kết quả mã hóa lên textResultEncode
			for(var entry : Huffman.huffmanCode.entrySet())
			{
				model.addRow(new Object[]
						{
							entry.getKey(),null,entry.getValue()
						});
			}
			// Hiển thị tần số các kí tự lên column Frequency trong bảng chứa kết quả encode
			int i = 0;
			for(var entry : Huffman.tanso.entrySet()) {
				model.setValueAt(entry.getValue(), i++, 1);
			}
			/* Lưu kết quả tần số và chuỗi sau mã hóa vào file để sử dụng cho decode
			 * Sử dụng hàm random để tạo các tên file tự động.
			 * file .bin dùng để lưu tần số của các kí tự
			 * file .text dùng để lưu chuỗi sau mã hóa
			 */
			try {
				SimpleDateFormat formatter = new SimpleDateFormat("dd_MM_yyyy HH-mm-ss ");  
			    Date date = new Date();  
			    Random generator = new Random();
			    String tenfile = "encode " + formatter.format(date) + generator.nextInt();
				FileWriter fileWriteTanso = new FileWriter("D:\\Study\\Nam2\\HocKy2\\DALTUD\\DoAn\\History\\" + tenfile +".bin");
				FileWriter fileWriteResult = new FileWriter("D:\\Study\\Nam2\\HocKy2\\DALTUD\\DoAn\\History\\" + tenfile +".txt");
				/* 
					Xử lý ngoại lệ với chuỗi chứa kí tự Enter(\n) để lúc ghi file không tự động xuống dòng, 
					gây ra lỗi trong quá trình decode sau này. Đổi kí tự Enter thành "NewLine"
				*/
				for(var entry : Huffman.tanso.entrySet()) 
				{
					if (entry.getKey().equals('\n')) fileWriteTanso.write("NewLine" + " "+ entry.getValue() + "\n");
					else fileWriteTanso.write(entry.getKey() + " " + entry.getValue() + "\n");
				}
				fileWriteTanso.close();
				fileWriteResult.write(Huffman.kq.toString());
				fileWriteResult.close();
				
				/*
				 * Insert kết quả ( input và output ) vào cơ sở dữ liệu
				 * Nếu indexRow = -1 thì thực hiện insert dữ liệu
				 * Nếu indexROw khác -1 thì thực hiện update dữ liệu tại ví trí ID đang được chọn
				 */
				int indexRow = tableHistory.getSelectedRow();
				String ID = "";
				if (indexRow != -1) {
					ID = tableHistory.getValueAt(indexRow,0).toString();
				}
				cn.executeEncode(indexRow,ID,textEncodeString.getText(), Huffman.kq.toString());
				JOptionPane.showMessageDialog(null, "Mã hóa thành công");
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "Error: "+e2);
			}
			// Thực hiện đẩy dữ liệu từ cơ sở dữ liệu lên table chứa lịch sử
			cn.getDataEncode(tableHistory, modelHistory);
		}
	}
}
