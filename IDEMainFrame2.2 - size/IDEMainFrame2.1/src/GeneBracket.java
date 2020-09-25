import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class GeneBracket extends KeyAdapter {
	
	//定义左右括号匹配关系
    private Map<Character,Character> map = new HashMap<Character,Character>();
	
	public GeneBracket(JTextPane textpane) {
		map.put('(', ')');
	    map.put('{', '}');
	    map.put('[', ']');
	}
	
	//返回光标下文括号是否匹配，参数ch为左括号
	private boolean isMatched(JTextPane textpane, char ch) throws BadLocationException {
		
		//与左括号相配对的右括号
		char chMatch = map.get(ch);
		
		Document doc = textpane.getDocument();
		String content = doc.getText(0, doc.getLength());
		
		//如果光标下文为空，括号一定匹配
		if(content.isEmpty()) {
			return true;
		}
		
		//如果光标下文不为空，判断是否匹配
		int len = content.length();
        int num = -1;//半括号个数，左括号加一，右括号减一，最后为0表示括号已经匹配
        for (int i = 0;i < len;i++){
        	if(content.charAt(i) == ch) {
        		num++;
        	} else if(content.charAt(i) == chMatch) {
        		num--;
        	}
        }
        return num == 0;
		
	}
	
	//添加右括号
	private void addBracket(JTextPane textpane, char ch) throws BadLocationException {
		
		if(!isMatched(textpane, ch)) {
			return;
		}
		
		//如果光标下文括号不匹配，补全右括号
		int pos = textpane.getCaretPosition();
		textpane.getDocument().insertString(pos, ((Character)map.get(ch)).toString(), null);
		textpane.setCaretPosition(pos);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		String modi = InputEvent.getModifiersExText(e.getModifiersEx());
		if(key == KeyEvent.VK_9 && modi.equals("Shift")) {
			try {
				addBracket((JTextPane) e.getSource(), '(');
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		} else if(key == KeyEvent.VK_OPEN_BRACKET && modi.equals("Shift")) {
			try {
				addBracket((JTextPane) e.getSource(), '{');
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		} else if(key == KeyEvent.VK_OPEN_BRACKET) {
			try {
				addBracket((JTextPane) e.getSource(), '[');
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	}


	
}
