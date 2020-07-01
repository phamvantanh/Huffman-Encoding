package project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.border.EtchedBorder;

public class FrameDecode extends JFrame implements ActionListener{
	
	// Khai báo biến
	private JPanel contentPane;
	private JTextField textFrequency;
	private String textFileTanso;
	private String textFileInput;
	private JTable tableHistory;
	private JTextArea textDecodeString;
	private JTextArea textResultDecode;
	private JMenuItem menuItemSave;
	private JMenuItem menuItemHelp;
	private JMenuItem menuItemExit;
	private JButton buttonBrowse;
	private JButton buttonOpenFile;
	private JButton buttonBack;
	private JButton buttonDecode;
	private DefaultTableModel model;
	
	// Kết nối Cơ sở dữ liệu thông qua Class ConnectDB();
	ConnectDB cn = new ConnectDB();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameDecode frame = new FrameDecode();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	 * Tạo frame Decode
	 */
	
	public FrameDecode() {
		setTitle("DECODE"); 		// Set title cho Frame
		setSize(500,500);			// Set size của Frame ( cửa sổ Encode )
		setLocationRelativeTo(null);// Hiển thị cửa sổ ứng dụng lên chính giữa màn hình
		
/*===========================================TẠO MENU BAR=======================================*/			
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		//Tạo menuItem "SaveResult" dùng để lưu chuỗi sau giải mã vào File và set sự kiện cho menuItem "Save Result"
		menuItemSave = new JMenuItem("Save Result");
		menuItemSave.addActionListener(this);
		menuFile.add(menuItemSave);

		//Tạo menuItem "Help" dùng để hiển thị cách sử dụng chức năng encode để mã hóa kí tự và set sự kiện cho menuItem "Help"
		menuItemHelp = new JMenuItem("Help");
		menuItemHelp.addActionListener(this);
		menuFile.add(menuItemHelp);
		
		
		//Tạo menuItem "Exit" dùng để thoát khỏi cửa sổ Decode và set sự kiện cho menuItem "Exit"
		menuItemExit = new JMenuItem("Exit");
		menuItemExit.addActionListener(this);
		menuFile.add(menuItemExit);
		
		
/*=====================================TẠO CÁC THÀNH PHẦN TRONG GIAO DIỆN CỦA CỬA SỔ DECODE =======================================*/				
		
		// Tạo JPanel chính chứa các thành phần trong cửa sổ giao diện Decode
		contentPane = new JPanel();
		contentPane.setAutoscrolls(true);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{92, 76, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 103, 13, 108, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 1.0, 1.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		// Tạo nhãn labelTanso
		JLabel labelTanso = new JLabel("Get frequences from file");
		GridBagConstraints gbc_labelTanso = new GridBagConstraints();
		gbc_labelTanso.anchor = GridBagConstraints.WEST;
		gbc_labelTanso.insets = new Insets(0, 0, 5, 5);
		gbc_labelTanso.gridx = 0;
		gbc_labelTanso.gridy = 0;
		contentPane.add(labelTanso, gbc_labelTanso);
		
		// Tạo textFrequency chứa đường dẫn file tần số
		textFrequency = new JTextField();
		GridBagConstraints gbc_textFrequency = new GridBagConstraints();
		gbc_textFrequency.gridwidth = 2;
		gbc_textFrequency.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFrequency.insets = new Insets(0, 0, 5, 5);
		gbc_textFrequency.gridx = 1;
		gbc_textFrequency.gridy = 0;
		contentPane.add(textFrequency, gbc_textFrequency);
		textFrequency.setColumns(18);
		
		// Tạo button Browse để lấy file chứa tần số và set sự kiện cho button
		buttonBrowse = new JButton("Browse...");
		GridBagConstraints gbc_buttonBrowse = new GridBagConstraints();
		gbc_buttonBrowse.fill = GridBagConstraints.HORIZONTAL;
		gbc_buttonBrowse.insets = new Insets(0, 0, 5, 0);
		gbc_buttonBrowse.gridx = 3;
		gbc_buttonBrowse.gridy = 0;
		contentPane.add(buttonBrowse, gbc_buttonBrowse);
		buttonBrowse.addActionListener(this);
				

		// Tạo scrollPaneInput chứa JTextArea để nhập input
		JScrollPane scrollPaneInput = new JScrollPane();
		scrollPaneInput.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Enter the Decode string", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_scrollPaneInput = new GridBagConstraints();
		gbc_scrollPaneInput.gridwidth = 4;
		gbc_scrollPaneInput.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPaneInput.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneInput.gridx = 0;
		gbc_scrollPaneInput.gridy = 1;
		contentPane.add(scrollPaneInput, gbc_scrollPaneInput);
		
		// Tạo textDecodeString để hiển thị Input
		textDecodeString = new JTextArea();
		textDecodeString.setLineWrap(true);
		scrollPaneInput.setViewportView(textDecodeString);
		
		// Tạo button OpenFile để có thể lấy dữ liệu input từ file
		buttonOpenFile = new JButton("Get Decode string from File");
		buttonOpenFile.addActionListener(this);
		scrollPaneInput.setColumnHeaderView(buttonOpenFile);
		
		// Tạo button Decode và set sự kiện để thực hiện chức năng giải mã chuỗi kí tự được mã hóa
		buttonDecode = new JButton("Decode");
		buttonDecode.addActionListener(this);
		GridBagConstraints gbc_buttonDecode = new GridBagConstraints();
		gbc_buttonDecode.insets = new Insets(0, 0, 5, 5);
		gbc_buttonDecode.gridx = 1;
		gbc_buttonDecode.gridy = 2;
		contentPane.add(buttonDecode, gbc_buttonDecode);
		
		// Tạo button Back có chức năng thoát khỏi cửa sổ Encode để quay lại cửa sổ chính của ứng dụng và set sự kiện cho button
		buttonBack = new JButton("Back");
		GridBagConstraints gbc_buttonBack = new GridBagConstraints();
		gbc_buttonBack.insets = new Insets(0, 0, 5, 5);
		gbc_buttonBack.gridx = 2;
		gbc_buttonBack.gridy = 2;
		contentPane.add(buttonBack, gbc_buttonBack);
		buttonBack.addActionListener(this);
		
		// Tạo panelResult chứa các thành phần hiển thị kết quả giải mã
		JPanel panelResult = new JPanel();
		panelResult.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Result Decode", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panelResult = new GridBagConstraints();
		gbc_panelResult.insets = new Insets(0, 0, 5, 0);
		gbc_panelResult.gridwidth = 4;
		gbc_panelResult.fill = GridBagConstraints.BOTH;
		gbc_panelResult.gridx = 0;
		gbc_panelResult.gridy = 3;
		contentPane.add(panelResult, gbc_panelResult);
		panelResult.setLayout(new GridLayout(1, 0, 0, 0));
		
		// Tạo textResultDecode hiển thị kết quả giải mã
		textResultDecode = new JTextArea();
		textResultDecode.setLineWrap(true);
		panelResult.add(textResultDecode);
		
		// Tạo JScrollPane chứa table lịch sử
		JScrollPane scrollPaneHistory = new JScrollPane();
		GridBagConstraints gbc_scrollPaneHistory = new GridBagConstraints();
		gbc_scrollPaneHistory.gridwidth = 4;
		gbc_scrollPaneHistory.fill = GridBagConstraints.BOTH;
		gbc_scrollPaneHistory.gridx = 0;
		gbc_scrollPaneHistory.gridy = 4;
		contentPane.add(scrollPaneHistory, gbc_scrollPaneHistory);
		
		model = new DefaultTableModel();
		
		// Tạo table chứa lịch sử decode
		tableHistory = new JTable(model);
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
		// thêm các column vào table chứa lịch sử
		model.addColumn("ID");
		model.addColumn("Input");
	    model.addColumn("Output");
	    model.addColumn("Frequency");
	    model.addColumn("Date Modified");
	    cn.getDataDecode(tableHistory, model); 	   // lấy dữ liệu từ cơ sở dữ liệu hiển thị lên bảng
	    tableHistory.setAutoCreateRowSorter(true); // thêm chức năng sort theo từng column
	  
	}
	
	
/*==================Hàm getInfo để lấy thông tin từ bảng History và hiện lên input và output và text chứa tần số =================*/
	private void getInfo(JTable table)
	{
		int index = table.getSelectedRow();
		textDecodeString.setText(table.getValueAt(index, 1).toString()); // hiển thị input
		textResultDecode.setText(table.getValueAt(index, 2).toString()); // hiển thị output
		textFrequency.setText(table.getValueAt(index, 3).toString().replace("\\", "\\\\"));    // hiển thị đường dẫn tần số
	}
	
/*============================Hàm xử lý sự kiện cho các thành phần==========================================*/
	public void actionPerformed(ActionEvent e) {
		
		// Sự kiện cho chức năng Save Result
		if(e.getSource()== menuItemSave) {
			// Chọn 1 file có sẵn hoặc tạo mới một file để lưu kết quả ( chuỗi ký tự sau giải mã )
			JFileChooser fileChooser = new JFileChooser();
			int select = fileChooser.showSaveDialog(null);
			if(select == JFileChooser.APPROVE_OPTION) {			
				try {
					//Ghi kết quả vào file
					FileWriter fileResult = new FileWriter(fileChooser.getSelectedFile());
					fileResult.write(textResultDecode.getText());
					fileResult.close();
					JOptionPane.showMessageDialog(null,"Đã lưu kết quả vào file");
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null,"Error:"+e1);
				}
			}
			else {
				// Hiển thị thông báo Cancel nếu hủy việc lưu dữ liệu
				JOptionPane.showMessageDialog(null,"Cancel");
			}
		}
		
		
		if(e.getSource()== menuItemHelp) {
			String helpString = "Bước 1:  Nhập file chứa tần số xuất hiện của kí tự trong chuỗi cần giải mã.\r\n" + 
					"Bước 2: Nhập chuỗi bit mã hóa, bằng 2 cách\r\n" + 
					"--------Cách 1 : Nhập trực tiếp trong ô : “Enter the Decode String”\r\n" + 
					"--------Cách 2 : Nhập từ file bằng cách chọn Get Decode String from file \r\n" + 
					"Bước 3 : Nhấn Decode để giải mã.\r\n"+
					"--- Có thể thực hiện lưu kết quả sau giải mã bằng cách chọn Save Result";
			JOptionPane.showMessageDialog(null, helpString);
		}
		
		
		if(e.getSource()==menuItemExit) {
			setVisible(false);
		}
		
	
		if(e.getSource()==buttonBrowse) {
			JFileChooser fileChooser = new JFileChooser();
			int select = fileChooser.showOpenDialog(null);
			String filePath = ""; // Chuỗi chứa đường dẫn
			if (select == JFileChooser.APPROVE_OPTION) {
			     String filename = fileChooser.getSelectedFile().getName();
			     String directory = fileChooser.getCurrentDirectory().toString(); // chuỗi chứa địa chỉ thư mục
			     filePath = directory +"\\"+filename;


			
			     textFrequency.setText(filePath.replace("\\", "\\\\"));
			     
				} else {
						JOptionPane.showMessageDialog(null, "Cancel get frequency from file !!!");
						}
		}
		
		
		if(e.getSource()==buttonOpenFile) {
			JFileChooser fileChooser = new JFileChooser();
			int select = fileChooser.showOpenDialog(null);
			String filePath = "";
			if (select == JFileChooser.APPROVE_OPTION) {
			     String filename = fileChooser.getSelectedFile().getName();
			     String directory = fileChooser.getCurrentDirectory().toString();
			     filePath = directory+"\\"+filename;
			   //Đọc dữ liệu File
				BufferedReader br = null;
			    try {   
			            br = new BufferedReader(new FileReader(filePath));       	             
			            textFileInput = br.readLine();
			            
			            //Hiển thị chuỗi input lên textFileInput
			            textDecodeString.setText(textFileInput);
			            
			        }
			        catch (IOException e1) {
			        	JOptionPane.showMessageDialog(null, "Error:"+e1);
			        } finally {
			            try {
			                br.close();
			            } catch (IOException e1) {
			            	JOptionPane.showMessageDialog(null, "Error:"+e1);
			            }
			        }				     
			 }
			else {
				JOptionPane.showMessageDialog(null, "Cancel Open file");
			}	
		}
	
		
	
	if(e.getSource()==buttonBack) {
		setVisible(false);
	}
	
	
	if(e.getSource()==buttonDecode) {
		if(textFrequency.getText().equals("")==false && textDecodeString.getText().equals("")==false) 
		{		
				
			
			BufferedReader bufferedReader = null;
			// Đọc file tần số từ đường dẫn hiển thị ở textFrequecy
	        try {   
	            bufferedReader = new BufferedReader(new FileReader(textFrequency.getText()));       

	             textFileTanso = ""; 
	             String line ;		
	             
	             /*Đọc dữ liệu theo dòng và cộng dữ liệu từng dòng vào textFile ta được toàn bộ file*/
	  	         while((line = bufferedReader.readLine()) != null){
	  	        	textFileTanso = textFileTanso + line + "\n";
	  	        }
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
	        
	    	 	// Thực hiện giải mã
	        try {
	        	Huffman.buildDecode(textFileTanso, textDecodeString.getText());
	        	// Hiển thị kết quả lên textResultDecode
				textResultDecode.setText(Huffman.code.toString());
				
				/*
				 * Insert kết quả ( input và output ) vào cơ sở dữ liệu
				 * Nếu indexRow = -1 thì thực hiện insert dữ liệu
				 * Nếu indexROw khác -1 thì thực hiện update dữ liệu tại ví trí ID đang được chọn
				 */
				int indexRow = tableHistory.getSelectedRow(); // số thứ tự hàng đang được chọn trong table lịch sử 
				String ID = "";
				if (indexRow != -1) { // indexRow = -1 khi không hàng nào được chọn
					ID = tableHistory.getValueAt(indexRow,0).toString();
				}
				// Gọi class thực hiện truy vấn cơ sở dữ liệu
				cn.executeDecode(indexRow,ID,textDecodeString.getText(), Huffman.code.toString(),textFrequency.getText());
				JOptionPane.showMessageDialog(null, "Giải mã thành công !!!");
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "Giải mã thất bại !!! Error :"+e2.getMessage());
			}
	    	 	
			}
			
			else {
	 		
	 		JOptionPane.showMessageDialog(null, "Kiểm tra dữ liệu vào ( tần số , chuỗi cần giải mã ) !!!");
	 	}
		cn.getDataDecode(tableHistory, model);
	}
			
	}
}
