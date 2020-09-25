import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class Txt {
	public static void main(String[] args) {
		
		String line;
		ArrayList <String> lineStrings=null;
		LinkedList <String> linkedString = null;
		try {
			BufferedReader in=new BufferedReader(new FileReader("D:\\debug.txt"));
			lineStrings=new ArrayList<String>();
			while((line=in.readLine())!=null) {
				lineStrings.add(line);
			}
			
			in.close();
			
		}     catch(IOException e) {
			e.printStackTrace();
		}
		
		
		//输出
		try {
			BufferedWriter out=new BufferedWriter(new FileWriter(new File("G:\\软件学院的日子\\java\\chapter11\\hello.txt"+ "")));
					while(linkedString.size()!=0) {
						line=linkedString.getLast();
						out.write(line);
                        out.newLine();
                        linkedString.removeLast();
						}
			out.close();
		}   catch(IOException e) {
			e.printStackTrace();
		}
		
	}

}

