import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.StyledDocument;
import javax.swing.undo.UndoManager;



import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.text.StyledEditorKit;
import javax.swing.undo.UndoManager;

public class IDEMainFrame extends JFrame implements ActionListener,FocusListener, 
MouseListener,CaretListener,UndoableEditListener{
	
	/*//�����ʽͷ����
		   private final Color colorvalues[] ={ Color.black, Color.blue, Color.red, Color.green };   
		   String styleNames[] = { "Bold", "Italic" };
		   String fontNames[] = { "����", "����","����","�����п�", "����","��Բ","��������","����","������","����ϸ��","��������","���Ĳ���","����ϸ��" };
		   String[] sizeString = new String[30];
		   int[] size = new int[30];
		   private JRadioButtonMenuItem colorItems[], fonts[];
		   private JCheckBoxMenuItem styleItems[];
		   private ButtonGroup fontGroup, colorGroup;
		   private int style;
		   private String selectText = "";
		   private JComboBox styleBox,fontBox,sizeBox;
		   private JPanel toolPanel;
		   private int rowNumber = 0;
		   private FileDialog fd = new FileDialog(this);
		*/   
		   
	
	//���к�
	private static final long serialVersionUID = 1L;
	
	//���ֹ�����
	private GridBagLayout gridBag = new GridBagLayout();
	private GridBagConstraints c = null;
	
	//�������
     private JPanel contentPane;
     
     
     //ѡ����
     private int TabNum;                             //һ�������õ��Ĳ���:��ǩ���
     private JTextPane  jta[]=new JTextPane[20],jep; //���ļ��༭����ǩ��
     private JScrollPane jsp[]=new JScrollPane[20];  //ת����Ŀɹ����ĵ�
     private JTabbedPane tab;                        //����                        
     
     private Hnote hnote[] = new Hnote[100];			//ע��
     private int cnt = 0;
   
    //�༭��
    private JTextPane textArea;
    
    //������
    private JTextArea   debugArea;
   // private  JTextArea debugArea;
    //�����
    private JPanel printArea;
 
    
    //����������
    public UndoManager undoMgr = new UndoManager(); 
    
    //1���½� 
    //2���޸Ĺ�
    //3���������
    int flag=0;
    
    //��ǰ�ļ���
    String currentFileName=null;
    //��ǰ�ļ�·��
    String currentPath=null;
	
   
    private JMenuItem itemNew;                 //�½�
    private JMenuItem itemOpen;		           //����
	private JMenuItem itemSave;				   //����
	private JMenuItem itemSaveAs;			   //���Ϊ
	private JSeparator separator;			   //�ָ���
	private JSeparator separator1;			   //�ָ���
	private JSeparator separator2;			   //�ָ���
	private JSeparator separator3;			   //�ָ���
	private JMenuItem itemExit;				   //�˳�
	private JMenu itemEdit;					   //�༭
	private JMenu itemRun;					   //����
	private JMenu itemHelp;                    //����
	private JMenu itemDebug;                   //����
	private JMenuItem itemBreak;               //���öϵ�
	private JMenuItem itemRstart;              //��ͷִ�е���һ���ϵ�
	private JMenuItem itemStep;                //ִ����һ�����������У�
	private JMenuItem itemPoint;               //�鿴������ֵ
	private JMenuItem itemDebug0;              //���ԣ�����gdb��
	private JMenuItem itemQuit;                //�뿪���Թ���
	private JMenuItem itemUndo;				   //����
	private JMenuItem itemRedo;			  	   //�ָ�
	private JMenuItem itemCut;				   //����
    private JMenuItem itemCopy;				   //����
    private JMenuItem itemPaste;			   //ճ��
    private JMenuItem itemDelete;			   //ɾ��
	private JMenuItem itemFind;				   //����
	private JMenuItem itemFindNext;			   //������һ��
    private JMenuItem itemReplace;			   //�滻
    private JMenuItem itemTurnTo;			   //ת��
    private JMenuItem itemSelectAll;		   //ȫѡ
    private JMenuItem itemCompile;			   //����
    private JMenuItem itemRun0;				   //����
    private JScrollPane scrollPane;			   //������
    private JCheckBoxMenuItem itemHide;		   //����ע��
    
	
    
    /////////////////////////////////////////////////////////////////////////////////
	   private boolean debug = false;
	   private Action boldAction = new StyledEditorKit.BoldAction();
	   private Action underlineAction = new StyledEditorKit.UnderlineAction();
	   private Action italicAction = new StyledEditorKit.ItalicAction();
	   //////////////////////////////////////////////////////////////////////////////////////
    
  
	/**
     *  	������
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    IDEMainFrame frame = new IDEMainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    /**
     * Create the frame.
     * ���캯��
     */
    
    public IDEMainFrame() {
    	try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (UnsupportedLookAndFeelException e1) {
            e1.printStackTrace();
        }
    	setTitle("demo");    
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setBounds(200, 100, 1500, 900);
        setLayout(new BorderLayout());
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu itemFile = new JMenu("�ļ�(F)");
        itemFile.setMnemonic('F');	//���ÿ�ݼ�"F"
        menuBar.add(itemFile);
        
        itemNew = new JMenuItem("�½�(N)",'N');
        itemNew.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N,
                java.awt.Event.CTRL_MASK));  //���ÿ�ݼ�Ctrl+"N"
        //itemNew.addActionListener(this);
        itemFile.add(itemNew);
            
        itemOpen = new JMenuItem("��(O)",'O');
        itemOpen.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O,
                java.awt.Event.CTRL_MASK));  //���ÿ�ݼ�Ctrl+"O"
        itemOpen.addActionListener(this);
        itemFile.add(itemOpen);
        
        itemSave = new JMenuItem("����(S)");
        itemSave.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
                java.awt.Event.CTRL_MASK));   //���ÿ�ݼ�Ctrl+"S"
        itemSave.addActionListener(this);
        itemFile.add(itemSave);
        
        itemSaveAs = new JMenuItem("���Ϊ(A)");
        itemSaveAs.addActionListener(this);
        itemFile.add(itemSaveAs);
        
        separator = new JSeparator();  //��ӷָ���
        itemFile.add(separator);
        
        itemExit = new JMenuItem("�˳�(X)",'X');
        //itemExit.addActionListener(this);
        itemFile.add(itemExit);
        
        itemEdit = new JMenu("�༭(E)");
        itemEdit.setMnemonic('E');
        menuBar.add(itemEdit);
        
        itemUndo = new JMenuItem("����(U)",'U');
        itemUndo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z,
                java.awt.Event.CTRL_MASK));  //���ÿ�ݼ�Ctrl+"Z"
        itemUndo.addActionListener(this);
        itemEdit.add(itemUndo);
        
        itemRedo = new JMenuItem("�ָ�(R)");
        itemRedo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R,
                java.awt.Event.CTRL_MASK));  //���ÿ�ݼ�Ctrl+"R"
        itemRedo.addActionListener(this);
        itemEdit.add(itemRedo);
        
        itemCut = new JMenuItem("����(T)",'T');
        itemCut.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X,
                java.awt.Event.CTRL_MASK));  //���ÿ�ݼ�Ctrl+"X"
        itemCut.addActionListener(this);
        
        separator1 = new JSeparator();  //��ӷָ���
        itemEdit.add(separator1);
        itemEdit.add(itemCut);
        
        itemCopy = new JMenuItem("����(C)",'C');
        itemCopy.addActionListener(this);
        itemCopy.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C,
                java.awt.Event.CTRL_MASK));  //���ÿ�ݼ�Ctrl+"C"
        itemEdit.add(itemCopy);
        
        itemPaste = new JMenuItem("ճ��(P)",'P');
        itemPaste.addActionListener(this);
        itemPaste.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V,
                java.awt.Event.CTRL_MASK));  //���ÿ�ݼ�Ctrl+"V"
        itemEdit.add(itemPaste);
        
        itemDelete = new JMenuItem("ɾ��(L)",'L');
        itemDelete.addActionListener(this);
        itemDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,  
                InputEvent.CTRL_MASK));    //���ÿ�ݼ�Ctrl+"D"
        itemEdit.add(itemDelete);
        
        separator2 = new JSeparator();  //��ӷָ���
        itemEdit.add(separator2);
        
        itemFind = new JMenuItem("����(F)",'F');
        itemFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
                Event.CTRL_MASK));   //���ÿ�ݼ�Ctrl+"F"
        itemFind.addActionListener(this);
        itemEdit.add(itemFind);
        
        itemFindNext = new JMenuItem("������һ��(N)",'N');
        itemFindNext.setAccelerator(KeyStroke.getKeyStroke("F3"));
        itemFindNext.addActionListener(this);
        itemEdit.add(itemFindNext);
        
        itemReplace = new JMenuItem("�滻(R)",'R');
        itemReplace.addActionListener(this);
        itemReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
                Event.CTRL_MASK));  //���ÿ�ݼ�Ctrl+"H"
        itemEdit.add(itemReplace);
        
        itemTurnTo = new JMenuItem("ת��(G)",'G');
        itemTurnTo.addActionListener(this);
        itemTurnTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
                Event.CTRL_MASK));  //���ÿ�ݼ�Ctrl+"G"
        itemEdit.add(itemTurnTo);
        
        itemSelectAll = new JMenuItem("ȫѡ(A)",'A');
        itemSelectAll.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A,
                java.awt.Event.CTRL_MASK));  //���ÿ�ݼ�Ctrl+"A"
        //itemSelectAll.addActionListener(this);    
        
        separator3 = new JSeparator();  //��ӷָ���
        itemEdit.add(separator3);
        
        itemHide = new JCheckBoxMenuItem("����ע��");
        itemHide.addActionListener(this);
        itemEdit.add(itemHide);
        
        itemRun = new JMenu("����(R)");
        itemRun.setMnemonic('R');
        menuBar.add(itemRun);
        itemRun.addActionListener(this);
        
        itemCompile = new JMenuItem("����");
        itemCompile.setAccelerator(KeyStroke.getKeyStroke("F10"));
        itemCompile.addActionListener(this);
        itemRun.add(itemCompile);
        
        itemRun0 = new JMenuItem("����");
        itemRun0.setAccelerator(KeyStroke.getKeyStroke("F11"));
        itemRun0.addActionListener(this);
        itemRun.add(itemRun0);
        
        
        itemDebug = new JMenu("����(D)");
        itemDebug.setMnemonic('D');
        menuBar.add(itemDebug);
        
        itemDebug0= new JMenuItem("Debug");
        itemDebug0.setAccelerator(KeyStroke.getKeyStroke("F4"));
        itemDebug0.addActionListener(this);
        itemDebug.add(itemDebug0);
        
        
        itemBreak= new JMenuItem("���öϵ�");
        itemBreak.setAccelerator(KeyStroke.getKeyStroke("F5"));
        itemBreak.addActionListener(this);
        itemDebug.add(itemBreak);
        
        itemRstart= new JMenuItem("��ʼ����");
        itemRstart.setAccelerator(KeyStroke.getKeyStroke("F6"));
        itemRstart.addActionListener(this);
        itemDebug.add(itemRstart);
        
        itemStep= new JMenuItem("��������");
        itemStep.setAccelerator(KeyStroke.getKeyStroke("F7"));
        itemStep.addActionListener(this);
        itemDebug.add(itemStep);
        
        itemPoint= new JMenuItem("����ֵ");
        itemPoint.setAccelerator(KeyStroke.getKeyStroke("F8"));
        itemPoint.addActionListener(this);
        itemDebug.add(itemPoint);
        
        itemQuit= new JMenuItem("�뿪");
        itemQuit.setAccelerator(KeyStroke.getKeyStroke("F9"));
        itemQuit.addActionListener(this);
        itemDebug.add(itemQuit);
        
        
        
        
        ///////////////////////////////////////////////////////////////////////////
        JMenu colorMenu = new JMenu("��ɫ");
        JMenu fontMenu = new JMenu("��ʽ(S)");
		JMenu styleMenu = new JMenu("����");
		menuBar.add(colorMenu);
		menuBar.add(fontMenu);
		menuBar.add(styleMenu);
		JMenu fontTypeMenu = new JMenu("����(Q)");
		fontMenu.add(fontTypeMenu);
		
		
		JMenuItem redTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("��", Color.red));
		JMenuItem orangeTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("��", Color.orange));
		JMenuItem yellowTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("��", Color.yellow));
		JMenuItem greenTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("��", Color.green));
		JMenuItem blueTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("��", Color.blue));
		JMenuItem cyanTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("��", Color.cyan));
		JMenuItem magentaTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("��", Color.magenta));
		JMenuItem blackTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("��", Color.black));
		
		redTextItem.setIcon(new ImageIcon("red.gif"));
		orangeTextItem.setIcon(new ImageIcon("orange.gif"));
		yellowTextItem.setIcon(new ImageIcon("yellow.gif"));
		greenTextItem.setIcon(new ImageIcon("green.gif"));
		blueTextItem.setIcon(new ImageIcon("blue.gif"));
		cyanTextItem.setIcon(new ImageIcon("cyan.gif"));
		magentaTextItem.setIcon(new ImageIcon("magenta.gif"));
		blackTextItem.setIcon(new ImageIcon("black.gif"));
		
		colorMenu.add(redTextItem);
		colorMenu.add(orangeTextItem);
		colorMenu.add(yellowTextItem);
		colorMenu.add(greenTextItem);
		colorMenu.add(blueTextItem);
		colorMenu.add(cyanTextItem);
		colorMenu.add(magentaTextItem);
		colorMenu.add(blackTextItem);
		
		
		String[] fontTypes =
		{ "����","����", "����","�����п�", "����","��Բ","��������","����","������","����ϸ��","��������","���Ĳ���","����ϸ��" };
		for(int i = 0; i < fontTypes.length; i++)
		{
			if(debug)
				System.out.println(fontTypes[i]);
			JMenuItem nextTypeItem = new JMenuItem(fontTypes[i]);
			nextTypeItem.setAction(new StyledEditorKit.FontFamilyAction(fontTypes[i], fontTypes[i]));
			fontTypeMenu.add(nextTypeItem);
		}
		
		JMenu fontSizeMenu = new JMenu("�ֺ�");
		fontMenu.add(fontSizeMenu);
 
		int[] fontSizes =
		{ 6, 8, 10, 12, 14, 16, 20, 24, 32, 36, 48, 72 };
		for(int i = 0; i < fontSizes.length; i++)
		{
			if(debug)
				System.out.println(fontSizes[i]);
			JMenuItem nextSizeItem = new JMenuItem(String.valueOf(fontSizes[i]));
			nextSizeItem.setAction(new StyledEditorKit.FontSizeAction(String.valueOf(fontSizes[i]), fontSizes[i]));
			fontSizeMenu.add(nextSizeItem);
		}
 
		JMenuItem boldMenuItem = new JMenuItem(boldAction);
		JMenuItem underlineMenuItem = new JMenuItem(underlineAction);
		JMenuItem italicMenuItem = new JMenuItem(italicAction);
 
		boldMenuItem.setText("�Ӵ�");
		underlineMenuItem.setText("�»���");
		italicMenuItem.setText("б��");
 
		boldMenuItem.setIcon(new ImageIcon("bold.gif"));
		underlineMenuItem.setIcon(new ImageIcon("underline.gif"));
		italicMenuItem.setIcon(new ImageIcon("italic.gif"));
 
		styleMenu.add(boldMenuItem);
		styleMenu.add(underlineMenuItem);
		styleMenu.add(italicMenuItem);
		
		////////////////////////////////////////////////////////////////////////////
        
        
        
        
        
        
        
        
        
        
        
        itemHelp = new JMenu("����(H)");
        itemHelp.setMnemonic('H');
        menuBar.add(itemHelp);
        
   /*     contentPane = new JPanel();
        add(contentPane, BorderLayout.CENTER);
        contentPane.setLayout(gridBag);
        //���ñ߿򲼾�
    */  
       
       
   
        
        contentPane = new JPanel();
        add(contentPane, BorderLayout.CENTER);
        contentPane.setLayout(gridBag);
        //���ñ߿򲼾�
        
        textArea = new JTextPane();
        //textArea.setLineWrap(true);
        
   //     textArea.setForeground( colorvalues[ 0 ] );
        textArea.setFont( new Font( "Serif", Font.PLAIN, 24 ) );
       
        
        
        TestLine view = new TestLine(); //����к�
        scrollPane = new JScrollPane(textArea);
        scrollPane.setRowHeaderView(view);     
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("test.c", scrollPane);
         
        //��ӳ���������
        textArea.getDocument().addUndoableEditListener(undoMgr);
        
        //VERTICAL��ֱ    HORIZONTALˮƽ
      //  scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
          
      //  TestLine view = new TestLine(); //����к�
      //  scrollPane.setRowHeaderView(view);
        
        JTabbedPane tabbpane = new JTabbedPane();
        tabbpane.addTab("test.c", scrollPane);
        
        
        
        
       
       
        //tabbedPane    �������
               
        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.weightx = 0.9;
        c.weighty = 0.9;
        c.fill = GridBagConstraints.BOTH;
        contentPane.add(tabbpane, c);
        
         itemNew.addActionListener(this);
         
         contentPane =new JPanel(); 
         contentPane.setLayout(gridBag);      //����Ḳ�ǵ�֮ǰ�ı༭��������һ���µĽ���
         setContentPane(contentPane);
        
         
          
         tab = new JTabbedPane(JTabbedPane.TOP); 
         contentPane.add(tab,c); 
         contentPane.setVisible(true);
        
        
        //dubugArea   ���Խ���
        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 2;
        c.weightx = 0.1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        //debugArea = new  JPanel();
        debugArea =new JTextArea();
        debugArea.setEditable(false);
        debugArea.add(new JLabel("������"));
        contentPane.add(debugArea, c);
         
        
     /*//printArea   �������
        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.9;
        c.weighty = 0.1;
        c.fill = GridBagConstraints.BOTH;
        printArea = new JPanel();
        printArea.add(new JLabel("�����"));
        contentPane.add(printArea, c);*/
        
       // textArea.getDocument().addDocumentListener(new SyntaxHighlighter(textArea));
        
        this.MainFrameWidowListener();
        
    }
    
  

	/*====================================================================*/
    
  /*  private class ItemHandler implements ActionListener {
        public void actionPerformed( ActionEvent event )
        {
           for ( int count = 0; count < colorItems.length; count++ )
              if ( colorItems[ count ].isSelected() ) {
            	  textArea.setForeground( colorvalues[ count ] );
                 break;
              }
           for ( int count = 0; count < fonts.length; count++ )
              if ( event.getSource() == fonts[ count ] ) {
            	  textArea.setFont(new Font( fonts[ count ].getText(), style, 72 ) );
                 break;
              }
           repaint();
        } }
    
    private class StyleHandler implements ItemListener {
        public void itemStateChanged( ItemEvent e )
        {
          style = 0;
          if ( styleItems[ 0 ].isSelected() )
              style += Font.BOLD;
          if ( styleItems[ 1 ].isSelected() )
              style += Font.ITALIC;
          textArea.setFont(
              new Font( textArea.getFont().getName(), style, 72 ) );
          repaint();
        }}
    */
    
    /**
     * �˳�
     */
    private void MainFrameWidowListener() {
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
            	int result=JOptionPane.showConfirmDialog(IDEMainFrame.this, "ȷ���رգ�", "ϵͳ��ʾ", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                if(result==JOptionPane.OK_OPTION){
                	IDEMainFrame.this.dispose();
                    IDEMainFrame.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                }
            }
        });
    }
    /*===================================================================*/

    /**
     * ��Ϊ����
     */
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource()==itemSave){        //����
            //������ļ��Ǵ򿪵ģ��Ϳ���ֱ�ӱ���
            save();
        }else if(e.getSource()==itemSaveAs){    //���Ϊ
            saveAs();
        }else if(e.getSource()==itemUndo){        //����
            if(undoMgr.canUndo()){
                undoMgr.undo();
            }
        }else if(e.getSource()==itemRedo){        //�ָ�
            if(undoMgr.canRedo()){
                undoMgr.redo();
            }
        }else if(e.getSource()==itemFind){        //����
            mySearch();
        }else if(e.getSource()==itemFindNext){    //������һ��
            mySearch();
        }else if(e.getSource()==itemReplace){    //�滻
            mySearch();
        }else if(e.getSource()==itemTurnTo){    //ת��
            turnTo();
        }else if(e.getSource()==itemNew) {     //�½�
        	     
              New();
        }else if(e.getSource()==itemOpen) {    //��
        	openFile();
        }else if(e.getSource()==itemCompile) {//����
        	compile();
        }else if(e.getSource()==itemRun0)    //����
        {
        	run();
        }else if(e.getSource()==itemDebug0) {  //����
        	Debug();
        }else if(e.getSource()==itemHide){
        	if(itemHide.isSelected())
        	{
        		try {
            		hidenote();
            	} catch (BadLocationException e1) {
            		System.out.println("BadLocationException: " + e1);
            	}
        	}else {
        		try {
            		displaynote();
            	} catch (BadLocationException e1) {
            		System.out.println("BadLocationException: " + e1);
            	}
        	}
        }
    }  
	  
    /*===================================================================*/
    public class Hnote {
    	private String not;
    	private int pos;
    	private int len;
    	public String getNot() {
            return not;
        }
        public void setNot(String not) {
            this.not = not;
        }
        public int getPos() {
            return pos;
        }
        public void setPos(int pos) {
            this.pos = pos;
        }
        public int getLen() {
        	return len;
        }
        public void setLen(int len) {
        	this.len = len;
        }
    }
    
    private void hidenote() throws BadLocationException {
    	Document doc = jta[0].getDocument();
    	int i=0;
    	while(i<doc.getLength()) {
    		if(doc.getText(i, 2).equals("/*")) {
    			hnote[cnt] = new Hnote();
    			hnote[cnt].setPos(i);
    			int j=i+2;
    			while(!doc.getText(j,2).equals("*/")) j++;
    			if(i==0||doc.getText(i-1, 1).equals("\n")) {
    				hnote[cnt].setNot(doc.getText(i, j-i+3));
        			hnote[cnt].setLen(j-i+3);
        			doc.remove(i, j-i+3);
        			cnt++;
    			}else {
    				hnote[cnt].setNot(doc.getText(i, j-i+2));
        			hnote[cnt].setLen(j-i+2);
        			doc.remove(i, j-i+2);
        			cnt++;
    			}
    		}else if(doc.getText(i, 2).equals("//")) {
    			hnote[cnt] = new Hnote();
    			hnote[cnt].setPos(i);
    			int j=i+2;
    			while(!doc.getText(j,1).equals("\n")) j++;
    			if(i==0||doc.getText(i-1, 1).equals("\n")) {
    				hnote[cnt].setNot(doc.getText(i, j-i+1));
        			hnote[cnt].setLen(j-i+1);
        			doc.remove(i, j-i+1);
        			cnt++;
    			}else {
    				hnote[cnt].setNot(doc.getText(i, j-i));
        			hnote[cnt].setLen(j-i);
        			doc.remove(i, j-i);
        			cnt++;
    			}
    		}else i++;
    	}
    	//doc.getText(0, doc.getLength()).replaceAll("\\/\\/[^\\n]*|\\/\\*([^\\*^\\/]*|[\\*^\\/*]*|[^\\**\\/]*)*\\*+\\/", "");
    	//System.out.println(doc.getText(0, 2));
    }
    
    private void displaynote() throws BadLocationException {
    	Document doc = jta[0].getDocument();
    	int cnt_ = cnt-1;
    	while(cnt_>=0) {
    		doc.insertString(hnote[cnt_].getPos(), hnote[cnt_].getNot(), null);
    		cnt_--;
    	}
    	cnt=0;
    }
    
    public class HideNote implements DocumentListener  {
    	
    	public void insertnote(StyledDocument doc, int pos, int len) throws BadLocationException {
    		//System.out.println("insert="+doc.getText(pos, len)+"pos="+pos);
    		int cnt_ = 0;
    		while(cnt_<cnt) {
    			if(hnote[cnt_].getPos()>=pos) {
    				hnote[cnt_].setPos(hnote[cnt_].getPos()+len);
    			}
    			cnt_++;
    		}
    	}
    	
    	public void removenote(StyledDocument doc, int pos) throws BadLocationException {
    		int cnt_ = 0;
    		while(cnt_<cnt) {
    			if(hnote[cnt_].getPos()>=pos) {
    				hnote[cnt_].setPos(hnote[cnt_].getPos()-1);
    			}
    			cnt_++;
    		}
    	}
    	
    	@Override
        public void changedUpdate(DocumentEvent e) {
     
        }
     
        @Override
        public void insertUpdate(DocumentEvent e) {
        	try {
        		insertnote((StyledDocument) e.getDocument(), e.getOffset(), e.getLength());
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
        }
     
        @Override
        public void removeUpdate(DocumentEvent e) {
        	try {
                removenote((StyledDocument) e.getDocument(), e.getOffset());
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
        }
    }
    /*===================================================================*/
    private void Debug()    //����
    {
     	StringBuffer sb=new StringBuffer();
    	BufferedReader br = null;
		try {
			//Process p = Runtime.getRuntime().exec("net user");
			Process p = Runtime.getRuntime().exec("cmd.exe /c start gdb ");
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				
				sb.append(line+"\n");
			
				debugArea.setText(sb.toString());
				//System.out.println(sb.toString());
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}  
    	
    	

    }                                                                                                         
 
    
    /*===================================================================*/    
    
    
    
    private void New()   //�½�
    {
    	TabNum++;
        jta[TabNum-1]=new JTextPane();
        jta[TabNum-1].addFocusListener(this);
        jsp[TabNum-1]=new JScrollPane(jta[TabNum-1],JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);         
        //jta[TabNum-1].getDocument().addUndoableEditListener(undoMgr);
        jta[TabNum-1].addCaretListener(this);
        TestLine view = new TestLine(); //����к�
		//jta[TabNum-1].setLineWrap(true);
        ((JScrollPane) jsp[TabNum-1]).setRowHeaderView(view);
        jsp[TabNum-1].setViewportView(jta[TabNum-1]);
        tab.addTab("text"+TabNum+".c",jsp[TabNum-1]);
        jep=jta[0];     
        
      //�ؼ��ָ���
      		jta[TabNum - 1].getDocument().addDocumentListener(new SyntaxHighlighter(jta[TabNum - 1]));
      		
      		//����ƥ��
      		jta[TabNum - 1].addCaretListener(new BracketMatch(jta[TabNum - 1]));
      		
      		//�Զ�����
      		jta[TabNum - 1].addKeyListener(new GeneTab(jta[TabNum - 1]));
      		
      		//�����Զ�����
      		jta[TabNum - 1].addKeyListener(new GeneBracket(jta[TabNum - 1]));
      		
      	//ע��
      		jta[TabNum - 1].getDocument().addDocumentListener(new HideNote());
    }
    
  /*===================================================================*/
    //��������    
    private void compile() {
    	String cPath = currentPath.replaceAll("\\\\", "/");
    	String exePath = cPath.substring(0,cPath.lastIndexOf("."))+".exe";
    	try {
    		Runtime.getRuntime().exec("cmd.exe /c gcc  -g "+cPath+" -o "+ exePath);
		} catch(Exception e) {
			e.printStackTrace();
		}
    }
    private void run(){
    	String cPath = currentPath.replaceAll("\\\\", "/");
    	String exePath = cPath.substring(0,cPath.lastIndexOf("."))+".exe";
    	try {
            Runtime.getRuntime().exec("cmd.exe /c start "+ exePath);     
        } catch (IOException e)  {
            e.printStackTrace();
        }
    }
    /**
     * ���ļ�
     */
    private void openFile() {
        if(flag==2 && this.currentPath==null){
            //1�������������±�Ϊ0�����½��ĵ�Ϊ1���������޸ĺ�
            int result=JOptionPane.showConfirmDialog(IDEMainFrame.this, "�Ƿ񽫸��ı��浽�ޱ���?", "���±�", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(result==JOptionPane.OK_OPTION){
                this.saveAs();
            }
        }else if(flag==2 && this.currentPath!=null){
            //2�����򿪵��ļ�2��������ļ�3���������޸�
            int result=JOptionPane.showConfirmDialog(IDEMainFrame.this, "�Ƿ񽫸��ı��浽"+this.currentPath+"?", "���±�", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(result==JOptionPane.OK_OPTION){
                this.save();
            }
        }
        //���ļ�ѡ���
        JFileChooser choose=new JFileChooser();
        //ѡ���ļ�
        int result=choose.showOpenDialog(this);
        if(result==JFileChooser.APPROVE_OPTION){
            //ȡ��ѡ����ļ�
            File file=choose.getSelectedFile();
            //���Ѵ��ڵ��ļ�����ǰ���ļ���������
            currentFileName=file.getName();
            //�����ļ�ȫ·��
            currentPath=file.getAbsolutePath();
            flag=3;
            this.setTitle(this.currentPath);
            BufferedReader br=null;
            try {
                //�����ļ���[�ַ���]
                InputStreamReader isr=new InputStreamReader(new FileInputStream(file),"GBK");
                br=new BufferedReader(isr);//��̬��
                //��ȡ����
                StringBuffer sb=new StringBuffer();
                String line=null;
                while((line=br.readLine())!=null){
                    //sb.append(line+SystemParam.LINE_SEPARATOR);
                    sb.append(line + "\n");
                }
                New();
                //��ʾ���ı���[���]
                jta[TabNum-1].setText(sb.toString());
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally{
                try {
                    if(br!=null) br.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    /*================================================================*/
 
    
    
    /**
     * ���Ϊ
     */
    private void saveAs() {
        //�򿪱����
        JFileChooser choose=new JFileChooser();
        //ѡ���ļ�
        int result=choose.showSaveDialog(this);
        if(result==JFileChooser.APPROVE_OPTION){
            //ȡ��ѡ����ļ�[�ļ������Լ������]
            File file=choose.getSelectedFile();
            FileWriter fw=null;
            //����
            try {
                fw=new FileWriter(file);
                fw.write(jta[TabNum-1].getText());
                currentFileName=file.getName();
                currentPath=file.getAbsolutePath();
                //����Ƚ��٣���Ҫд
                fw.flush();
                this.flag=3;
                this.setTitle(currentPath);
            } catch (IOException e1) {
                e1.printStackTrace();
            }finally{
                try {
                    if(fw!=null) fw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
    /*===================================================================*/
    
    /**
     * ����
     */
    private void save() {
        if(this.currentPath==null){
            this.saveAs();
            if(this.currentPath==null){
                return;
            }
        }
        FileWriter fw=null;
        //����
        try {
            fw=new FileWriter(new  File(currentPath));
            fw.write(jta[TabNum-1].getText());
            //����Ƚ��٣���Ҫд
            fw.flush();
            flag=3;
            this.setTitle(this.currentPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }finally{
            try {
                if(fw!=null) fw.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    /*===================================================================*/
    private void turnTo() {
        final JDialog gotoDialog = new JDialog(this, "ת��������");
        JLabel gotoLabel = new JLabel("����(L):");
        final JTextField linenum = new JTextField(5);
        linenum.setText("1");
        linenum.selectAll();

        JButton okButton = new JButton("ȷ��");
        okButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
               int totalLine = jta[TabNum-1].getText().split("\\n").length;
            	//int totalLine = jta[TabNum-1].getLineCount();
            	
                int[] lineNumber = new int[totalLine + 1];
                String s = jta[TabNum-1].getText();
                //String s = jta[TabNum-1].getText();
                int pos = 0, t = 0;

                while (true) {
                    pos = s.indexOf('\12', pos);
                    // System.out.println("����pos:"+pos);
                    if (pos == -1)
                        break;
                    lineNumber[t++] = pos++;
                }

                int gt = 1;
                try {
                    gt = Integer.parseInt(linenum.getText());
                } catch (NumberFormatException efe) {
                    JOptionPane.showMessageDialog(null, "����������!", "��ʾ", JOptionPane.WARNING_MESSAGE);
                    linenum.requestFocus(true);
                    return;
                }

                if (gt < 2 || gt >= totalLine) {
                    if (gt < 2)
                    	jta[TabNum-1].setCaretPosition(0);
                    	//jta[TabNum].setCaretPosition(0);
                    else
                    	jta[TabNum-1].setCaretPosition(s.length());
                    	//jta[TabNum].setCaretPosition(s.length());
                } else
                	jta[TabNum-1].setCaretPosition(lineNumber[gt - 2] + 1);
                	//jta[TabNum].setCaretPosition(lineNumber[gt - 2] + 1);

                gotoDialog.dispose();//�رմ���
            }

        });

        JButton cancelButton = new JButton("ȡ��");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gotoDialog.dispose();
            }
        });

        //�������ӵ�������
        Container con = gotoDialog.getContentPane();
        con.setLayout(new FlowLayout());
        con.add(gotoLabel);
        con.add(linenum);
        con.add(okButton);
        con.add(cancelButton);

        gotoDialog.setSize(200, 100);
        gotoDialog.setResizable(false);
        gotoDialog.setLocation(300, 280);
        gotoDialog.setVisible(true);
    }
    /*====================================================================*/
    public void mySearch() {
        final JDialog findDialog = new JDialog(this, "�������滻", true);
        Container con = findDialog.getContentPane();
        con.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel searchContentLabel = new JLabel("��������(N) :");
        JLabel replaceContentLabel = new JLabel("�滻Ϊ(P)�� :");
        final JTextField findText = new JTextField(22);
        final JTextField replaceText = new JTextField(22);
        final JCheckBox matchcase = new JCheckBox("���ִ�Сд");
        ButtonGroup bGroup = new ButtonGroup();
        final JRadioButton up = new JRadioButton("����(U)");
        final JRadioButton down = new JRadioButton("����(D)");
        down.setSelected(true);
        bGroup.add(up);
        bGroup.add(down);
        JButton searchNext = new JButton("������һ��(F)");
        JButton replace = new JButton("�滻(R)");
        final JButton replaceAll = new JButton("ȫ���滻(A)");
        searchNext.setPreferredSize(new Dimension(110, 22));
        replace.setPreferredSize(new Dimension(110, 22));
        replaceAll.setPreferredSize(new Dimension(110, 22));
        
        
        // "�滻"��ť���¼�����
        replace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (replaceText.getText().length() == 0 && jta[TabNum-1].getSelectedText() != null)
                	jta[TabNum-1].replaceSelection("");
                if (replaceText.getText().length() > 0 && jta[TabNum-1].getSelectedText() != null)
                	jta[TabNum-1].replaceSelection(replaceText.getText());
            }
        });

        // "�滻ȫ��"��ť���¼�����
        replaceAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                jta[TabNum-1].setCaretPosition(0); // �����ŵ��༭����ͷ
                int a = 0, b = 0, replaceCount = 0;

                if (findText.getText().length() == 0) {
                    JOptionPane.showMessageDialog(findDialog, "����д��������!", "��ʾ", JOptionPane.WARNING_MESSAGE);
                    findText.requestFocus(true);
                    return;
                }
                while (a > -1) {

                    int FindStartPos = jta[TabNum-1].getCaretPosition();
                    String str1, str2, str3, str4, strA, strB;
                    str1 = jta[TabNum-1].getText();
                    str2 = str1.toLowerCase();
                    str3 = findText.getText();
                    str4 = str3.toLowerCase();

                    if (matchcase.isSelected()) {
                        strA = str1;
                        strB = str3;
                    } else {
                        strA = str2;
                        strB = str4;
                    }

                    if (up.isSelected()) {
                        if (jta[TabNum-1].getSelectedText() == null) {
                            a = strA.lastIndexOf(strB, FindStartPos - 1);
                        } else {
                            a = strA.lastIndexOf(strB, FindStartPos - findText.getText().length() - 1);
                        }
                    } else if (down.isSelected()) {
                        if (jta[TabNum-1].getSelectedText() == null) {
                            a = strA.indexOf(strB, FindStartPos);
                        } else {
                            a = strA.indexOf(strB, FindStartPos - findText.getText().length() + 1);
                        }

                    }

                    if (a > -1) {
                        if (up.isSelected()) {
                        	jta[TabNum-1].setCaretPosition(a);
                            b = findText.getText().length();
                            jta[TabNum-1].select(a, a + b);
                        } else if (down.isSelected()) {
                        	jta[TabNum-1].setCaretPosition(a);
                            b = findText.getText().length();
                            jta[TabNum-1].select(a, a + b);
                        }
                    } else {
                        if (replaceCount == 0) {
                            JOptionPane.showMessageDialog(findDialog, "�Ҳ��������ҵ�����!", "���±�", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(findDialog, "�ɹ��滻" + replaceCount + "��ƥ������!", "�滻�ɹ�", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    if (replaceText.getText().length() == 0 && jta[TabNum-1].getSelectedText() != null) {
                    	jta[TabNum-1].replaceSelection("");
                        replaceCount++;
                    }
                    if (replaceText.getText().length() > 0 && jta[TabNum-1].getSelectedText() != null) {
                    	jta[TabNum-1].replaceSelection(replaceText.getText());
                        replaceCount++;
                    }
                }// end while
            }
        }); /* "�滻ȫ��"��ť���¼�������� */

        
        
        // "������һ��"��ť�¼�����
        searchNext.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int a = 0, b = 0;
                int FindStartPos = jta[TabNum-1].getCaretPosition();
                String str1, str2, str3, str4, strA, strB;
                str1 = jta[TabNum-1].getText();
                str2 = str1.toLowerCase();
                str3 = findText.getText();
                str4 = str3.toLowerCase();
                // "���ִ�Сд"��CheckBox��ѡ��
                if (matchcase.isSelected()) {
                    strA = str1;
                    strB = str3;
                } else {
                    strA = str2;
                    strB = str4;
                }

                if (up.isSelected()) {
                    if (jta[TabNum-1].getSelectedText() == null) {
                        a = strA.lastIndexOf(strB, FindStartPos - 1);
                    } else {
                        a = strA.lastIndexOf(strB, FindStartPos - findText.getText().length() - 1);
                    }
                } else if (down.isSelected()) {
                    if (jta[TabNum-1].getSelectedText() == null) {
                        a = strA.indexOf(strB, FindStartPos);
                    } else {
                        a = strA.indexOf(strB, FindStartPos - findText.getText().length() + 1);
                    }

                }
                if (a > -1) {
                    if (up.isSelected()) {
                    	jta[TabNum-1].setCaretPosition(a);
                        b = findText.getText().length();
                        jta[TabNum-1].select(a, a + b);
                    } else if (down.isSelected()) {
                    	jta[TabNum-1].setCaretPosition(a);
                        b = findText.getText().length();
                        jta[TabNum-1].select(a, a + b);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "�Ҳ��������ҵ�����!", "���±�", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });/* "������һ��"��ť�¼�������� */
        // "ȡ��"��ť���¼�����
        JButton cancel = new JButton("ȡ��");
        cancel.setPreferredSize(new Dimension(110, 22));
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findDialog.dispose();
            }
        });

        // ����"�������滻"�Ի���Ľ���
        JPanel bottomPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel topPanel = new JPanel();

        JPanel direction = new JPanel();
        direction.setBorder(BorderFactory.createTitledBorder("���� "));
        direction.add(up);
        direction.add(down);
        direction.setPreferredSize(new Dimension(170, 60));
        JPanel replacePanel = new JPanel();
        replacePanel.setLayout(new GridLayout(2, 1));
        replacePanel.add(replace);
        replacePanel.add(replaceAll);

        topPanel.add(searchContentLabel);
        topPanel.add(findText);
        topPanel.add(searchNext);
        centerPanel.add(replaceContentLabel);
        centerPanel.add(replaceText);
        centerPanel.add(replacePanel);
        bottomPanel.add(matchcase);
        bottomPanel.add(direction);
        bottomPanel.add(cancel);

        con.add(topPanel);
        con.add(centerPanel);
        con.add(bottomPanel);

        // ����"�������滻"�Ի���Ĵ�С���ɸ��Ĵ�С(��)��λ�úͿɼ���
        findDialog.setSize(410, 210);
        findDialog.setResizable(false);
        findDialog.setLocation(230, 280);
        findDialog.setVisible(true);
    }

	@Override
	public void undoableEditHappened(UndoableEditEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void caretUpdate(CaretEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		
	}
    
//    private boolean shiftPressed = false;
//    private class Monitor implements KeyListener{
//    	
//    	void keyPressed(KeyEvent e) {
//    		int key = e.getKeyCode();
//    		if(e.)
//    		
//    	}
//    	
//    }
	
}