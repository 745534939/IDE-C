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
	
    //�س������º���õĺ���
	private void onEnterReleased(JTextPane textpane) throws BadLocationException{
		
		//�����֮ǰ�Ƿ�Ϊ������ţ����������Ϊtrue
		Document doc = textpane.getDocument();
		int pos = textpane.getCaretPosition();
		if(pos < 2) {
			return;
		}
		char ch = textpane.getDocument().getText(pos - 2, 1).charAt(0);
		
		//��ù����������
        Element map = doc.getDefaultRootElement();
        int row = map.getElementIndex(pos);
		
    	//��ù�������е����ݴ洢Ϊ�ַ���
        String[] text = textpane.getText().split("\\n");
        String content = text[row - 1];
		
		//�ַ����к��еĿո���ת��Ϊtab
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
