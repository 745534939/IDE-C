import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class BracketMatch implements CaretListener{
	
	private char ch;
	private Style selectedBracketStyle;
	private Style normalBracketStyle;
	
	//∂®“Â◊Û”“¿®∫≈∆•≈‰πÿœµ
    private Map<Character,Character> map = new HashMap<Character,Character>();
	
	public BracketMatch(JTextPane textPane) {
		selectedBracketStyle = ((StyledDocument) textPane.getDocument()).addStyle("Bracket_Style", null);
		StyleConstants.setForeground(selectedBracketStyle, Color.WHITE);
        StyleConstants.setBackground(selectedBracketStyle, Color.BLACK);
        
		normalBracketStyle = ((StyledDocument) textPane.getDocument()).addStyle("Normal_Style", null);
        StyleConstants.setForeground(normalBracketStyle, Color.BLACK);
        StyleConstants.setBackground(normalBracketStyle, Color.WHITE);
        
		map.put('(', ')');
	    map.put('{', '}');
	    map.put('[', ']');
	}
	
	private void select(StyledDocument doc, int left,int right) {
		SwingUtilities.invokeLater(new ClearTask(doc, left, selectedBracketStyle));
		SwingUtilities.invokeLater(new ClearTask(doc, right, selectedBracketStyle));
	}
	
	private void clear(StyledDocument doc) throws BadLocationException {
		
		String str = doc.getText(0, doc.getLength());
		
		for(int i = 0;i < doc.getLength(); i++) {
			if(map.containsKey(str.charAt(i)) || map.containsValue(str.charAt(i))) {
				SwingUtilities.invokeLater(new ClearTask(doc, i, normalBracketStyle));
			}
		}
	}
	
	private void findNextBracket(StyledDocument doc, int start) throws BadLocationException {
		
		int end = doc.getLength();
		String str = doc.getText(start, end - start);
		int temp = findMatch(str);
		
		if(temp != 0) {
			select(doc, start, start + temp);
		}
		
		
	}
	
    private int findMatch(String str){
    	
        int length = str.length();//◊÷∑˚¥Æ≥§∂»
        int num = 0;//∞Î¿®∫≈∏ˆ ˝£¨◊Û¿®∫≈º”“ª£¨”“¿®∫≈ºı“ª
        for (int i = 0;i < length;i++){
        	if(ch == str.charAt(i)) {
        		num++;
        	} else if(str.charAt(i) == map.get(ch)) {
        		num--;
        	}
        	if(num == 0) {
        		return i;
        	}
        }
        return 0;
    }

	@Override
	public void caretUpdate(CaretEvent e) {
		try {
			int pos = e.getDot();
			if(pos > 0) {
				StyledDocument doc = ((JTextPane) e.getSource()).getStyledDocument();
				clear(doc);
				ch = doc.getText(pos - 1, 1).charAt(0);
				if(map.containsKey(ch)) {
					findNextBracket(doc, pos - 1);
				}
			}	
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	
	private class ClearTask implements Runnable {
        private StyledDocument doc;
        int pos;
        private Style style;
 
        public ClearTask(StyledDocument doc, int pos, Style style) {
        	this.doc = doc;
            this.pos = pos;
            this.style = style;
        }
 
        public void run() {
            try {
                doc.setCharacterAttributes(pos, 1, style, true);
            } catch (Exception e) {}
        }
    }
}