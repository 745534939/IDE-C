import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class SyntaxHighlighter implements DocumentListener {
    private Style dataKeywordStyle;
    private Style gramKeywordStyle;
    private Style funcKeywordStyle;
    private Style headerStyle;
    private Style normalStyle;
    private Style annoStyle;
 
    public SyntaxHighlighter(JTextPane textArea) {
        // 准备着色使用的样式
        dataKeywordStyle = ((StyledDocument) textArea.getDocument()).addStyle("dataKeyword_Style", null);
        gramKeywordStyle = ((StyledDocument) textArea.getDocument()).addStyle("gramKeyword_Style", null);
        funcKeywordStyle = ((StyledDocument) textArea.getDocument()).addStyle("funcKeyword_Style", null);
        headerStyle = ((StyledDocument) textArea.getDocument()).addStyle("header_Style", null);
        normalStyle = ((StyledDocument) textArea.getDocument()).addStyle("normal_Style", null);
        annoStyle = ((StyledDocument) textArea.getDocument()).addStyle("anno_Style", null);
        textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        StyleConstants.setForeground(dataKeywordStyle, Color.BLUE);
        StyleConstants.setForeground(gramKeywordStyle, Color.ORANGE);
        StyleConstants.setForeground(funcKeywordStyle, Color.ORANGE);
        StyleConstants.setForeground(headerStyle, Color.MAGENTA);
        StyleConstants.setForeground(normalStyle, Color.BLACK);
        StyleConstants.setForeground(annoStyle, Color.GREEN);
    }
 
    private void colouring(StyledDocument doc, int pos, int len) throws BadLocationException {
        int start = indexOfWordStart(doc, pos);
        int end = indexOfWordEnd(doc, pos + len);
 
        char ch;
        while (start < end) {
            ch = getCharAt(doc, start);
            if (Character.isLetter(ch) || ch == '_') {
                // pos为处理后的最后一个下标
                start = colouringWord(doc, start);
            } else {
                SwingUtilities.invokeLater(new ColouringTask(doc, start, 1, normalStyle));
                ++start;
            }
        }
        
        colouringAnno(doc);
    }
    
    private void colouringAnno(StyledDocument doc) throws BadLocationException {
    	int docLength = doc.getLength();
    	String text = doc.getText(0, docLength);
    	String[] textLine = text.split("\\n");
    	
    	for(int count = 0; count < docLength; count++) {
    		
    		if(text.charAt(count) == '/') {

		        if(docLength <= count + 1) {
		        	continue;
		        }
		        
    			if(text.charAt(count + 1) == '/') {
    				
    				//行注释
        			Element map = doc.getDefaultRootElement();
    		        int row = map.getElementIndex(count);//行数
    		        Element lineElem = map.getElement(row);
    	            int lineStart = lineElem.getStartOffset();//行首
    	            int lineEnd = lineStart + textLine[row].length(); 
    		        
    		        SwingUtilities.invokeLater(new ColouringTask(doc, count, lineEnd - count, annoStyle));
    		        
    		        count = lineEnd;
    		        
    			} else if(text.charAt(count + 1) == '*') {
    				
    				//段落注释
    				int end = count + 2;
    				
    				if(docLength <= end) {
    					end = docLength;
    				} else {
    					for(;end < docLength; end++) {
    						if(text.charAt(end) == '/' && text.charAt(end - 1) == '*') {
    							break;
    						}
    					}
    				}
    				
    				SwingUtilities.invokeLater(new ColouringTask(doc, count, end - count + 1, annoStyle));
    				
    				count = end;
    				
    			}
    		}
    	}
    	
    }
 
    private int colouringWord(StyledDocument doc, int pos) throws BadLocationException {
        int wordEnd = indexOfWordEnd(doc, pos);
        String word = doc.getText(pos, wordEnd - pos);
 
        if (KeyWord.dataKeywords.contains(word)) {
            SwingUtilities.invokeLater(new ColouringTask(doc, pos, wordEnd - pos, dataKeywordStyle));
        } else if(KeyWord.gramKeywords.contains(word)){
        	SwingUtilities.invokeLater(new ColouringTask(doc, pos, wordEnd - pos, gramKeywordStyle));
        } else if(KeyWord.funcKeywords.contains(word)) {
        	SwingUtilities.invokeLater(new ColouringTask(doc, pos, wordEnd - pos, funcKeywordStyle));
        } else if(KeyWord.headers.contains(word) && getCharAt(doc, pos - 1) == '#'){
        	SwingUtilities.invokeLater(new ColouringTask(doc, pos - 1, wordEnd - pos + 1, headerStyle));
        } else {
            SwingUtilities.invokeLater(new ColouringTask(doc, pos, wordEnd - pos, normalStyle));
        }
 
        return wordEnd;
    }
 

    private char getCharAt(Document doc, int pos) throws BadLocationException {
        return doc.getText(pos, 1).charAt(0);
    }
   
    int indexOfWordStart(Document doc, int pos) throws BadLocationException {
        // 从pos开始向前找到第一个非单词字符.
        for (; pos > 0 && isWordCharacter(doc, pos - 1); --pos);
 
        return pos;
    }
 
    public int indexOfWordEnd(Document doc, int pos) throws BadLocationException {
        // 从pos开始向前找到第一个非单词字符.
        for (; isWordCharacter(doc, pos); ++pos);
 
        return pos;
    }
 
    public boolean isWordCharacter(Document doc, int pos) throws BadLocationException {
        char ch = getCharAt(doc, pos);
        if (Character.isLetter(ch) || Character.isDigit(ch) || ch == '_') { return true; }
        return false;
    }
 
    @Override
    public void changedUpdate(DocumentEvent e) {
 
    }
 
    @Override
    public void insertUpdate(DocumentEvent e) {
        try {
//            colouring((StyledDocument) e.getDocument(), e.getOffset(), e.getLength());
        	colouring((StyledDocument) e.getDocument(), 0, e.getDocument().getLength());
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }
 
    @Override
    public void removeUpdate(DocumentEvent e) {
        try {
            // 因为删除后光标紧接着影响的单词两边, 所以长度就不需要了
//            colouring((StyledDocument) e.getDocument(), e.getOffset(), 0);
        	colouring((StyledDocument) e.getDocument(), 0, e.getDocument().getLength());
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }
 
    private class ColouringTask implements Runnable {
    	//private JTextArea textArea;
        private StyledDocument doc;
        private Style style;
        private int pos;
        private int len;
 
        public ColouringTask(StyledDocument doc, int pos, int len, Style style) {
            //this.textArea = textArea;
        	this.doc = doc;
            this.pos = pos;
            this.len = len;
            this.style = style;
        }
 
        public void run() {
            try {
                // 这里就是对字符进行着色
                doc.setCharacterAttributes(pos, len, style, true);
            } catch (Exception e) {}
        }
    }
}