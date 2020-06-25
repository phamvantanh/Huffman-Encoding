package project;

import java.util.*;

import javax.swing.JFileChooser;

import java.io.*;
	
// tạo note (nút gốc của cây mã hóa Huffman)
class Note
{
	// Khai báo biến
	char ch;
	int tanso;
	Note left = null , right = null;
	Note(char ch , int tanso)
	{
		this.ch = ch;
		this.tanso = tanso;
	}
	
	public Note(char ch ,int tanso ,Note left ,Note right)
	{
		this.ch = ch;
		this.tanso = tanso;
		this.left = left;
		this.right = right;
	}
}

class huffman
{
	/*
	 * chuỗi kq dùng để chứa chuỗi mã hóa(010110..)
	 * chuỗi code dùng để chứa chuỗi đã giải mã
	 * Map huffmancode dùng để chứa các kí tự và từ mã biểu diễn kí tự (a-01,b-10..)
	 * Map tanso dùng để chứa kí tự và tần số xuất hiện tương ứng của từng kí tự(a-6,b-1..)
	*/
	 	 
	static StringBuilder kq;
	static StringBuilder code;
	static Map<Character, String> huffmanCode;
	static Map<Character, Integer> tanso;
	
	//định nghĩa các hàm trả kiểu dữ liệu về tương ứng
	public StringBuilder getkq()
	{
		return huffman.kq;
	}
	//------------------------
	public StringBuilder getcode()
	{
		return huffman.code;
	}
	//------------------------
	
	public Object getmap()
	{
		
		return huffman.huffmanCode.entrySet();
	}
	//------------------------
	
	public Object gettanso()
	{
		
		return huffman.tanso.entrySet();
	}
	//------------------------
	
	
	// hàm encode dùng để gắn 0 vs 1 lên cây hufffman
	public static void encode(Note root ,String str ,Map<Character, String> huffmanCode)
	{
		if(root == null)
			return ;
		// tìm thấy 1 nút lá
		if(root.left == null && root.right == null)
			huffmanCode.put(root.ch, str);
		encode(root.left , str + '0' ,huffmanCode);
		encode(root.right, str + '1' ,huffmanCode);
	}
	
	
	
	
	// duyệt cây huffman và giải mã chuỗi được mã hóa
	public static int decode(Note root ,int Index ,StringBuilder kq)
	{
		if(root == null)
			return Index;
		// tìm thấy 1 nút lá
		if(root.left == null && root.right == null)
		{
			//thêm từ mã hóa được vào chuỗi code
			code.append(root.ch);
			return Index;
		}
		Index++;
		if(kq.charAt(Index) == '0')
			Index = decode(root.left, Index, kq);
		else
			Index = decode(root.right, Index, kq);
		return Index;
	}
	
	

 //----------------------------------------------------------------------------------	
	
	// xây dựng cây huffman từ file chuỗi kí tự truyền vào -> mã hóa chúng
	public static void buildHuffman(String input)
	{
		// đếm tần số xuất hiện mỗi ký tự
		// và lưu trữ nó trong map
	    tanso = new HashMap<>();
	      
		for(char c: input.toCharArray())
		{
			tanso.put(c, tanso.getOrDefault(c, 0) + 1);
		}
		
		
		// tạo 1 hàng đợi ưu tiên để lưu trữ các nút của cây
		// mục ưu tiên cao nhất có tần số thấp nhất
		PriorityQueue<Note> hd;
				
		hd = new PriorityQueue<>(Comparator.comparingInt(k -> k.tanso));
		
		// tạo 1 nút lá cho mỗi ký tự 
		// và thềm nó vào hàng đợi
		
		for(var entry : tanso.entrySet())
		{
			hd.add(new Note(entry.getKey() , entry.getValue()));
		}
		
		
		while(hd.size() != 1)
		{
			// loại bỏ 2 nút có ưu tiên cao nhất
			// hay tần số thấp nhất trong hàng đợi
			Note left = hd.poll();
			Note right = hd.poll();
			// tạo 1 nút mới (cha 2 nút con)
			// với tần số bằng tổng 2 nút con
			// thêm nút mới vào hàng đợi
			int sum = left.tanso + right.tanso;
			hd.add(new Note('\0', sum, left , right));
		}
		
		
		// root lưu trữ con trỏ đến root của huffman tree
		Note root = hd.peek();
		
		
		// Duyệt cây huffman và lưu trữ mã huffman trong Map
		  huffmanCode = new HashMap<>();
		  encode(root, "", huffmanCode);
		

		// chuỗi mã hóa
		kq = new StringBuilder();
		for(char c: input.toCharArray())
		{
			kq.append(huffmanCode.get(c));
		}
				
	}
	
 //----------------------------------------------------------------------------------
	
	// xây dựng lại cây huffman với flie tần số được truyền vào và file chứa 
	// chuỗi mã hóa -> kết hợp 2 file để giải mã

	public static void builddecode(String input,String mahoa)
	{
		//lấy dữ liệu tần số và cho vào hashmap như ban đầu tương tự như mã hóa
		// và build lại cây huffman
		
		String[] sc = input.split("\n");
		
		tanso = new HashMap<>();
		for(String line : sc)
		{
			String[] parts = new String[2];
			parts[0] = line.substring(0, line.indexOf(" ", 1));
			parts[1] = line.substring(line.indexOf(" ", 1) + 1);

			// Chuyển chuỗi "NewLine" thành kí tự "\n" để trả lại đúng chuỗi mã hóa

			if (parts[0].equals("NewLine")) tanso.put('\n', Integer.parseInt(parts[1]));
			else tanso.put(parts[0].charAt(0), Integer.parseInt(parts[1]));
		}
		
		
		// tạo 1 hàng đợi ưu tiên để lưu trữ các nút của cây
		// mục ưu tiên cao nhất có tần số thấp nhất

		PriorityQueue<Note> hd;
		hd = new PriorityQueue<>(Comparator.comparingInt(k -> k.tanso));

		// tạo 1 nút lá cho mỗi ký tự 
		// và thềm nó vào hàng đợi
		for(var entry : tanso.entrySet())
		{
			hd.add(new Note(entry.getKey() , entry.getValue()));
		}
		
		
		while(hd.size() != 1)
		{
			// loại bỏ 2 nút có ưu tiên cao nhất
			// hay tần số thấp nhất trong hàng đợi
			Note left = hd.poll();
			Note right = hd.poll();
			// tạo 1 nút mới (cha 2 nút con)
			// với tần số bằng tổng 2 nút con
			// thêm nút mới vào hàng đợi
			int sum = left.tanso + right.tanso;
			hd.add(new Note('\0', sum, left , right));
		}
		
		
		// root lưu trữ con trỏ đến root của huffman tree
		Note root = hd.peek();
		// đi qua cây huffman và lưu trữ mã huffman trong map
		  huffmanCode = new HashMap<>();
		encode(root, "", huffmanCode);
		

    		//thêm chuỗi mã hóa(01011..) vào chuỗi kq

		kq = new StringBuilder();
		kq.append(mahoa);
		
		// Duyệt qua cây huffman 1 lần nữa và giải mã chuỗi được mã hóa 

		code = new StringBuilder();
		int index = -1;
		while (index < kq.length() - 2)
		{
			index = decode(root, index, kq);
		}
		
	}


	
}
