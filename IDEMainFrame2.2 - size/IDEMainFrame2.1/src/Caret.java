import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class Caret {
	
	//光标所在行
	private int caretPosRow = 0;
	//光标所在列
	private int caretPosCol = 0;
	//光标所在文本位置
	private int caretPos = 0;
	//光标所属文本框
	private JTextArea textarea;
	
	//构造函数
	public Caret(JTextArea textarea) {
		this.textarea = textarea;
		getCaretPosition();
	}

	public void getCaretPosition() {
		int pos = 0, row = 0, col = 0;
		try {
			pos = textarea.getCaretPosition();
			System.out.println("caret pos:" + pos);
			row = textarea.getLineOfOffset(pos) + 1;
			System.out.println("caret row:" + row);
			col = pos - textarea.getLineStartOffset(row - 1) + 1;
			System.out.println("caret col:" + col);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.caretPos = pos;
		this.caretPosRow = row;
		this.caretPosCol = col;
		System.out.println();
	}
	
	public int getCaretPos() {
		return caretPos;
	}

	public int getCaretPosRow() {
		return caretPosRow;
	}

	public int getCaretPosCol() {
		return caretPosCol;
	}


}
