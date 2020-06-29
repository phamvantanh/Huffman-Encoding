package project;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.result.Row;



public class ConnectDB {
	
	// Khai báo biến tĩnh
	static java.sql.Connection con;
	static java.sql.Statement stmt;
	static	ResultSet resultSet;
	public void Connect()
	{
		try 
		{
			Class.forName("com.mysql.cj.jdbc.Driver");  // Kết nối Java và MySQL thông qua JDBC
			String hostname = "localhost";  // Database hoạt động trên máy chủ
			String databaseName = "huffman";// Tên database
			String user = "root";			// Tên người dùng để đăng nhập kết nối database
			String password = "123456";		// Mật khẩu		
			String url = "jdbc:mysql://"+hostname+"/"+databaseName+"?user="+user+"&password="+password+""; // Đường dẫn kết nối
			con = DriverManager.getConnection(url); // tạo kết nối theo đường dẫn
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE); // Tạo statement
		} catch (ClassNotFoundException ex) {
			System.out.print("Error: " + ex.getMessage());
		}catch(SQLException se){
			System.err.println("Error: "+se.getMessage());
		}
	}
	// hàm getDataEncode thực hiện lấy dữ liệu trong bảng Encode để đẩy lên bảng chứa lịch sử
	public void getDataEncode(JTable table, DefaultTableModel model)
    {

        String getData = "SELECT * FROM huffman.encode ORDER BY DATE_MODIFIED DESC;";
        Connect();
        try
        {
			resultSet = stmt.executeQuery(getData); // Trả về kết quả khi truy vấn
			
			model.setRowCount(0); // Reset bảng chứa lịch sự
			// Thêm các hàng chứa dữ liệu vào bảng
			while(resultSet.next())
			{
					model.addRow(new Object[]{resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)});
			}
		
		}catch (SQLException e) 
        	{
			JOptionPane.showMessageDialog(null, "Error: "+e);
        	}
    }
	// Hàm cập nhật dữ liệu khi thực hiện chức năng encode
	public void executeEncode(int indexRow, String ID, String input, String output)
	{	
		// Câu lệnh insert bảng ghi mới
		String insert = "INSERT INTO huffman.encode(INPUT,OUTPUT) VALUES ('"+input+"', '"+output+"');";
		// Câu lệnh update bảng ghi đã tồn tại
		String update = "UPDATE huffman.encode SET INPUT='"+input+"',OUTPUT='"+output+"',DATE_MODIFIED = default WHERE ID ="+ID+"";
		// Gọi class Connect() để kết nối Cơ sở dữ liệu
		Connect();
		
		if (indexRow != -1) {
				try {
		    	   stmt.execute(update);
		    	   
					}catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Error: "+e);
					}
		} else 
				try {
						stmt.execute(insert);
					} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Error: "+e);
					}		
	}

	// hàm getDataDecode thực hiện lấy dữ liệu trong bảng Decode để đẩy lên bảng chứa lịch sử
	public void getDataDecode(JTable table, DefaultTableModel model)
    {

        String getData = "SELECT * FROM huffman.decode ORDER BY DATE_MODIFIED DESC;";
        Connect();
        try
        {
			resultSet = stmt.executeQuery(getData); // Trả về kết quả khi truy vấn
			model.setRowCount(0);  // Reset bảng chứa lịch sự
			// Thêm các hàng chứa dữ liệu vào bảng
			while(resultSet.next())
			{
					model.addRow(new Object[]{resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)});
			}
		
		}catch (SQLException e) 
        	{
			JOptionPane.showMessageDialog(null, "Error: "+e);
        	}
    }
	// Hàm cập nhật dữ liệu khi thực hiện chức năng decode
	public void executeDecode(int indexRow, String ID, String input, String output,String frequency)
	{	
		// Câu lệnh insert bảng ghi mới
		String insert = "INSERT INTO huffman.decode(INPUT,OUTPUT,FREQUENCY) VALUES ('"+input+"', '"+output+"','"+frequency+"');";
		// Câu lệnh update bảng ghi đã tồn tại
		String update = "UPDATE huffman.decode SET INPUT='"+input+"',OUTPUT='"+output+"',FREQUENCY='"+frequency+"',"+
						"DATE_MODIFIED = default WHERE ID ="+ID+"";
		// Gọi class Connect() để kết nối Cơ sở dữ liệu
		Connect();
		if (indexRow != -1) {
				try {
		    	   stmt.execute(update);
		    	   
					}catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Error: "+e);
					}
		} else 
				try {
						stmt.execute(insert);
					} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Error: "+e);
					}		
	}

}
