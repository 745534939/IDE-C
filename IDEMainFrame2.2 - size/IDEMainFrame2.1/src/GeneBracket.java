import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class GeneBracket extends KeyAdapter {
	
	//������������ƥ���ϵ
    private Map<Character,Character> map = new HashMap<Character,Character>();
	
	public GeneBracket(JTextPane textpane) {
		map.put('(', ')');
	    map.put('{', '}');
	    map.put('[', ']');
	}
	
	//���ع�����������Ƿ�ƥ�䣬����chΪ������
	private boolean isMatched(JTextPane textpane, char ch) throws BadLocationException {
		
		//������������Ե�������
		char chMatch = map.get(ch);
		
		Document doc = textpane.getDocument();
		String content = doc.getText(0, doc.getLength());
		
		//����������Ϊ�գ�����һ��ƥ��
		if(content.isEmpty()) {
			return true;
		}
		
		//���������Ĳ�Ϊ�գ��ж��Ƿ�ƥ��
		int len = content.length();
        int num = -1;//�����Ÿ����������ż�һ�������ż�һ�����Ϊ0��ʾ�����Ѿ�ƥ��
        for (int i = 0;i < len;i++){
        	if(content.charAt(i) == ch) {
        		num++;
        	} else if(content.charAt(i) == chMatch) {
        		num--;
        	}
        }
        return num == 0;
		
	}
	
	//���������
	private void addBracket(JTextPane textpane, char ch) throws BadLocationException {
		
		if(!isMatched(textpane, ch)) {
			return;
		}
		
		//�������������Ų�ƥ�䣬��ȫ������
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
