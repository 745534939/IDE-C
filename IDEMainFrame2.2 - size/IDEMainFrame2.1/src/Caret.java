import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

public class Caret {
	
	//���������
	private int caretPosRow = 0;
	//���������
	private int caretPosCol = 0;
	//��������ı�λ��
	private int caretPos = 0;
	//��������ı���
	private JTextArea textarea;
	
	//���캯��
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
