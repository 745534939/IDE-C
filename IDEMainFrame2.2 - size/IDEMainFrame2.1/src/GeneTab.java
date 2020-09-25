import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;

public class GeneTab extends KeyAdapter {
	
	static final char tab = '\t';
	
	public GeneTab(JTextPane textpane) {
		
	}
	
    //回车键按下后调用的函数
	private void onEnterReleased(JTextPane textpane) throws BadLocationException{
		
		//检查光标之前是否为左大括号，如果是设置为true
		Document doc = textpane.getDocument();
		int pos = textpane.getCaretPosition();
		if(pos < 2) {
			return;
		}
		char ch = textpane.getDocument().getText(pos - 2, 1).charAt(0);
		
		//获得光标所在行数
        Element map = doc.getDefaultRootElement();
        int row = map.getElementIndex(pos);
		
    	//获得光标所在行的内容存储为字符串
        String[] text = textpane.getText().split("\\n");
        String content = text[row - 1];
		
		//字符串中含有的空格数转化为tab
        String str = "";
        int j, count = 0;
		for(j = 0;content.length() > j && content.charAt(j) == tab; j++) {
			str += tab;
			count++;
		}
		if(ch == '{') {
			count++;
			str += tab;
			str += "\n";
		}
		for(j = 0;content.length() > j && content.charAt(j) == tab; j++) {
			str += tab;
		}
		
		textpane.getDocument().insertString(pos, str, null);
		textpane.setCaretPosition(pos + count);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			try {
				onEnterReleased((JTextPane) e.getSource());
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}
