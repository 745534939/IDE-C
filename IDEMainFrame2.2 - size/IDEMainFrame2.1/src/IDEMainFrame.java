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
	
	/*//字体格式头函数
		   private final Color colorvalues[] ={ Color.black, Color.blue, Color.red, Color.green };   
		   String styleNames[] = { "Bold", "Italic" };
		   String fontNames[] = { "宋体", "黑体","楷体","华文行楷", "隶书","幼圆","华文琥珀","仿宋","新宋体","华文细黑","华文中宋","华文彩云","华文细黑" };
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
		   
	
	//序列号
	private static final long serialVersionUID = 1L;
	
	//布局管理器
	private GridBagLayout gridBag = new GridBagLayout();
	private GridBagConstraints c = null;
	
	//内容面板
     private JPanel contentPane;
     
     
     //选项卡面板
     private int TabNum;                             //一个经常用到的参数:标签序号
     private JTextPane  jta[]=new JTextPane[20],jep; //多文件编辑（标签）
     private JScrollPane jsp[]=new JScrollPane[20];  //转换后的可滚动文档
     private JTabbedPane tab;                        //标题                        
     
     private Hnote hnote[] = new Hnote[100];			//注释
     private int cnt = 0;
   
    //编辑区
    private JTextPane textArea;
    
    //调试区
    private JTextArea   debugArea;
   // private  JTextArea debugArea;
    //输出区
    private JPanel printArea;
 
    
    //撤销管理器
    public UndoManager undoMgr = new UndoManager(); 
    
    //1：新建 
    //2：修改过
    //3：保存过的
    int flag=0;
    
    //当前文件名
    String currentFileName=null;
    //当前文件路径
    String currentPath=null;
	
   
    private JMenuItem itemNew;                 //新建
    private JMenuItem itemOpen;		           //保存
	private JMenuItem itemSave;				   //保存
	private JMenuItem itemSaveAs;			   //另存为
	private JSeparator separator;			   //分隔线
	private JSeparator separator1;			   //分隔线
	private JSeparator separator2;			   //分隔线
	private JSeparator separator3;			   //分隔线
	private JMenuItem itemExit;				   //退出
	private JMenu itemEdit;					   //编辑
	private JMenu itemRun;					   //运行
	private JMenu itemHelp;                    //帮助
	private JMenu itemDebug;                   //调试
	private JMenuItem itemBreak;               //设置断点
	private JMenuItem itemRstart;              //从头执行到第一个断点
	private JMenuItem itemStep;                //执行下一步（单步运行）
	private JMenuItem itemPoint;               //查看变量数值
	private JMenuItem itemDebug0;              //调试（开启gdb）
	private JMenuItem itemQuit;                //离开调试功能
	private JMenuItem itemUndo;				   //撤销
	private JMenuItem itemRedo;			  	   //恢复
	private JMenuItem itemCut;				   //剪切
    private JMenuItem itemCopy;				   //复制
    private JMenuItem itemPaste;			   //粘贴
    private JMenuItem itemDelete;			   //删除
	private JMenuItem itemFind;				   //查找
	private JMenuItem itemFindNext;			   //查找下一个
    private JMenuItem itemReplace;			   //替换
    private JMenuItem itemTurnTo;			   //转到
    private JMenuItem itemSelectAll;		   //全选
    private JMenuItem itemCompile;			   //编译
    private JMenuItem itemRun0;				   //运行
    private JScrollPane scrollPane;			   //滚动栏
    private JCheckBoxMenuItem itemHide;		   //隐藏注释
    
	
    
    /////////////////////////////////////////////////////////////////////////////////
	   private boolean debug = false;
	   private Action boldAction = new StyledEditorKit.BoldAction();
	   private Action underlineAction = new StyledEditorKit.UnderlineAction();
	   private Action italicAction = new StyledEditorKit.ItalicAction();
	   //////////////////////////////////////////////////////////////////////////////////////
    
  
	/**
     *  	主函数
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
     * 构造函数
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
        
        JMenu itemFile = new JMenu("文件(F)");
        itemFile.setMnemonic('F');	//设置快捷键"F"
        menuBar.add(itemFile);
        
        itemNew = new JMenuItem("新建(N)",'N');
        itemNew.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N,
                java.awt.Event.CTRL_MASK));  //设置快捷键Ctrl+"N"
        //itemNew.addActionListener(this);
        itemFile.add(itemNew);
            
        itemOpen = new JMenuItem("打开(O)",'O');
        itemOpen.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O,
                java.awt.Event.CTRL_MASK));  //设置快捷键Ctrl+"O"
        itemOpen.addActionListener(this);
        itemFile.add(itemOpen);
        
        itemSave = new JMenuItem("保存(S)");
        itemSave.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S,
                java.awt.Event.CTRL_MASK));   //设置快捷键Ctrl+"S"
        itemSave.addActionListener(this);
        itemFile.add(itemSave);
        
        itemSaveAs = new JMenuItem("另存为(A)");
        itemSaveAs.addActionListener(this);
        itemFile.add(itemSaveAs);
        
        separator = new JSeparator();  //添加分隔线
        itemFile.add(separator);
        
        itemExit = new JMenuItem("退出(X)",'X');
        //itemExit.addActionListener(this);
        itemFile.add(itemExit);
        
        itemEdit = new JMenu("编辑(E)");
        itemEdit.setMnemonic('E');
        menuBar.add(itemEdit);
        
        itemUndo = new JMenuItem("撤销(U)",'U');
        itemUndo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z,
                java.awt.Event.CTRL_MASK));  //设置快捷键Ctrl+"Z"
        itemUndo.addActionListener(this);
        itemEdit.add(itemUndo);
        
        itemRedo = new JMenuItem("恢复(R)");
        itemRedo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R,
                java.awt.Event.CTRL_MASK));  //设置快捷键Ctrl+"R"
        itemRedo.addActionListener(this);
        itemEdit.add(itemRedo);
        
        itemCut = new JMenuItem("剪切(T)",'T');
        itemCut.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X,
                java.awt.Event.CTRL_MASK));  //设置快捷键Ctrl+"X"
        itemCut.addActionListener(this);
        
        separator1 = new JSeparator();  //添加分隔线
        itemEdit.add(separator1);
        itemEdit.add(itemCut);
        
        itemCopy = new JMenuItem("复制(C)",'C');
        itemCopy.addActionListener(this);
        itemCopy.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C,
                java.awt.Event.CTRL_MASK));  //设置快捷键Ctrl+"C"
        itemEdit.add(itemCopy);
        
        itemPaste = new JMenuItem("粘贴(P)",'P');
        itemPaste.addActionListener(this);
        itemPaste.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V,
                java.awt.Event.CTRL_MASK));  //设置快捷键Ctrl+"V"
        itemEdit.add(itemPaste);
        
        itemDelete = new JMenuItem("删除(L)",'L');
        itemDelete.addActionListener(this);
        itemDelete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,  
                InputEvent.CTRL_MASK));    //设置快捷键Ctrl+"D"
        itemEdit.add(itemDelete);
        
        separator2 = new JSeparator();  //添加分隔线
        itemEdit.add(separator2);
        
        itemFind = new JMenuItem("查找(F)",'F');
        itemFind.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
                Event.CTRL_MASK));   //设置快捷键Ctrl+"F"
        itemFind.addActionListener(this);
        itemEdit.add(itemFind);
        
        itemFindNext = new JMenuItem("查找下一个(N)",'N');
        itemFindNext.setAccelerator(KeyStroke.getKeyStroke("F3"));
        itemFindNext.addActionListener(this);
        itemEdit.add(itemFindNext);
        
        itemReplace = new JMenuItem("替换(R)",'R');
        itemReplace.addActionListener(this);
        itemReplace.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
                Event.CTRL_MASK));  //设置快捷键Ctrl+"H"
        itemEdit.add(itemReplace);
        
        itemTurnTo = new JMenuItem("转到(G)",'G');
        itemTurnTo.addActionListener(this);
        itemTurnTo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
                Event.CTRL_MASK));  //设置快捷键Ctrl+"G"
        itemEdit.add(itemTurnTo);
        
        itemSelectAll = new JMenuItem("全选(A)",'A');
        itemSelectAll.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A,
                java.awt.Event.CTRL_MASK));  //设置快捷键Ctrl+"A"
        //itemSelectAll.addActionListener(this);    
        
        separator3 = new JSeparator();  //添加分隔线
        itemEdit.add(separator3);
        
        itemHide = new JCheckBoxMenuItem("隐藏注释");
        itemHide.addActionListener(this);
        itemEdit.add(itemHide);
        
        itemRun = new JMenu("运行(R)");
        itemRun.setMnemonic('R');
        menuBar.add(itemRun);
        itemRun.addActionListener(this);
        
        itemCompile = new JMenuItem("编译");
        itemCompile.setAccelerator(KeyStroke.getKeyStroke("F10"));
        itemCompile.addActionListener(this);
        itemRun.add(itemCompile);
        
        itemRun0 = new JMenuItem("运行");
        itemRun0.setAccelerator(KeyStroke.getKeyStroke("F11"));
        itemRun0.addActionListener(this);
        itemRun.add(itemRun0);
        
        
        itemDebug = new JMenu("调试(D)");
        itemDebug.setMnemonic('D');
        menuBar.add(itemDebug);
        
        itemDebug0= new JMenuItem("Debug");
        itemDebug0.setAccelerator(KeyStroke.getKeyStroke("F4"));
        itemDebug0.addActionListener(this);
        itemDebug.add(itemDebug0);
        
        
        itemBreak= new JMenuItem("设置断点");
        itemBreak.setAccelerator(KeyStroke.getKeyStroke("F5"));
        itemBreak.addActionListener(this);
        itemDebug.add(itemBreak);
        
        itemRstart= new JMenuItem("开始调试");
        itemRstart.setAccelerator(KeyStroke.getKeyStroke("F6"));
        itemRstart.addActionListener(this);
        itemDebug.add(itemRstart);
        
        itemStep= new JMenuItem("单步运行");
        itemStep.setAccelerator(KeyStroke.getKeyStroke("F7"));
        itemStep.addActionListener(this);
        itemDebug.add(itemStep);
        
        itemPoint= new JMenuItem("变量值");
        itemPoint.setAccelerator(KeyStroke.getKeyStroke("F8"));
        itemPoint.addActionListener(this);
        itemDebug.add(itemPoint);
        
        itemQuit= new JMenuItem("离开");
        itemQuit.setAccelerator(KeyStroke.getKeyStroke("F9"));
        itemQuit.addActionListener(this);
        itemDebug.add(itemQuit);
        
        
        
        
        ///////////////////////////////////////////////////////////////////////////
        JMenu colorMenu = new JMenu("颜色");
        JMenu fontMenu = new JMenu("格式(S)");
		JMenu styleMenu = new JMenu("字形");
		menuBar.add(colorMenu);
		menuBar.add(fontMenu);
		menuBar.add(styleMenu);
		JMenu fontTypeMenu = new JMenu("字体(Q)");
		fontMenu.add(fontTypeMenu);
		
		
		JMenuItem redTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("红", Color.red));
		JMenuItem orangeTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("橘", Color.orange));
		JMenuItem yellowTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("黄", Color.yellow));
		JMenuItem greenTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("绿", Color.green));
		JMenuItem blueTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("蓝", Color.blue));
		JMenuItem cyanTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("青", Color.cyan));
		JMenuItem magentaTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("紫", Color.magenta));
		JMenuItem blackTextItem = new JMenuItem(new StyledEditorKit.ForegroundAction("黑", Color.black));
		
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
		{ "楷体","宋体", "黑体","华文行楷", "隶书","幼圆","华文琥珀","仿宋","新宋体","华文细黑","华文中宋","华文彩云","华文细黑" };
		for(int i = 0; i < fontTypes.length; i++)
		{
			if(debug)
				System.out.println(fontTypes[i]);
			JMenuItem nextTypeItem = new JMenuItem(fontTypes[i]);
			nextTypeItem.setAction(new StyledEditorKit.FontFamilyAction(fontTypes[i], fontTypes[i]));
			fontTypeMenu.add(nextTypeItem);
		}
		
		JMenu fontSizeMenu = new JMenu("字号");
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
 
		boldMenuItem.setText("加粗");
		underlineMenuItem.setText("下划线");
		italicMenuItem.setText("斜体");
 
		boldMenuItem.setIcon(new ImageIcon("bold.gif"));
		underlineMenuItem.setIcon(new ImageIcon("underline.gif"));
		italicMenuItem.setIcon(new ImageIcon("italic.gif"));
 
		styleMenu.add(boldMenuItem);
		styleMenu.add(underlineMenuItem);
		styleMenu.add(italicMenuItem);
		
		////////////////////////////////////////////////////////////////////////////
        
        
        
        
        
        
        
        
        
        
        
        itemHelp = new JMenu("帮助(H)");
        itemHelp.setMnemonic('H');
        menuBar.add(itemHelp);
        
   /*     contentPane = new JPanel();
        add(contentPane, BorderLayout.CENTER);
        contentPane.setLayout(gridBag);
        //设置边框布局
    */  
       
       
   
        
        contentPane = new JPanel();
        add(contentPane, BorderLayout.CENTER);
        contentPane.setLayout(gridBag);
        //设置边框布局
        
        textArea = new JTextPane();
        //textArea.setLineWrap(true);
        
   //     textArea.setForeground( colorvalues[ 0 ] );
        textArea.setFont( new Font( "Serif", Font.PLAIN, 24 ) );
       
        
        
        TestLine view = new TestLine(); //添加行号
        scrollPane = new JScrollPane(textArea);
        scrollPane.setRowHeaderView(view);     
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("test.c", scrollPane);
         
        //添加撤销管理器
        textArea.getDocument().addUndoableEditListener(undoMgr);
        
        //VERTICAL垂直    HORIZONTAL水平
      //  scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
          
      //  TestLine view = new TestLine(); //添加行号
      //  scrollPane.setRowHeaderView(view);
        
        JTabbedPane tabbpane = new JTabbedPane();
        tabbpane.addTab("test.c", scrollPane);
        
        
        
        
       
       
        //tabbedPane    输入界面
               
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
         contentPane.setLayout(gridBag);      //这个会覆盖掉之前的编辑区，产生一个新的界面
         setContentPane(contentPane);
        
         
          
         tab = new JTabbedPane(JTabbedPane.TOP); 
         contentPane.add(tab,c); 
         contentPane.setVisible(true);
        
        
        //dubugArea   调试界面
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
        debugArea.add(new JLabel("调试区"));
        contentPane.add(debugArea, c);
         
        
     /*//printArea   输出界面
        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.9;
        c.weighty = 0.1;
        c.fill = GridBagConstraints.BOTH;
        printArea = new JPanel();
        printArea.add(new JLabel("输出区"));
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
     * 退出
     */
    private void MainFrameWidowListener() {
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
            	int result=JOptionPane.showConfirmDialog(IDEMainFrame.this, "确定关闭？", "系统提示", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                if(result==JOptionPane.OK_OPTION){
                	IDEMainFrame.this.dispose();
                    IDEMainFrame.this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                }
            }
        });
    }
    /*===================================================================*/

    /**
     * 行为动作
     */
    public void actionPerformed(ActionEvent e) {
    	if(e.getSource()==itemSave){        //保存
            //如果该文件是打开的，就可以直接保存
            save();
        }else if(e.getSource()==itemSaveAs){    //另存为
            saveAs();
        }else if(e.getSource()==itemUndo){        //撤销
            if(undoMgr.canUndo()){
                undoMgr.undo();
            }
        }else if(e.getSource()==itemRedo){        //恢复
            if(undoMgr.canRedo()){
                undoMgr.redo();
            }
        }else if(e.getSource()==itemFind){        //查找
            mySearch();
        }else if(e.getSource()==itemFindNext){    //查找下一个
            mySearch();
        }else if(e.getSource()==itemReplace){    //替换
            mySearch();
        }else if(e.getSource()==itemTurnTo){    //转到
            turnTo();
        }else if(e.getSource()==itemNew) {     //新建
        	     
              New();
        }else if(e.getSource()==itemOpen) {    //打开
        	openFile();
        }else if(e.getSource()==itemCompile) {//编译
        	compile();
        }else if(e.getSource()==itemRun0)    //运行
        {
        	run();
        }else if(e.getSource()==itemDebug0) {  //调试
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
    private void Debug()    //调试
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
    
    
    
    private void New()   //新建
    {
    	TabNum++;
        jta[TabNum-1]=new JTextPane();
        jta[TabNum-1].addFocusListener(this);
        jsp[TabNum-1]=new JScrollPane(jta[TabNum-1],JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);         
        //jta[TabNum-1].getDocument().addUndoableEditListener(undoMgr);
        jta[TabNum-1].addCaretListener(this);
        TestLine view = new TestLine(); //添加行号
		//jta[TabNum-1].setLineWrap(true);
        ((JScrollPane) jsp[TabNum-1]).setRowHeaderView(view);
        jsp[TabNum-1].setViewportView(jta[TabNum-1]);
        tab.addTab("text"+TabNum+".c",jsp[TabNum-1]);
        jep=jta[0];     
        
      //关键字高亮
      		jta[TabNum - 1].getDocument().addDocumentListener(new SyntaxHighlighter(jta[TabNum - 1]));
      		
      		//括号匹配
      		jta[TabNum - 1].addCaretListener(new BracketMatch(jta[TabNum - 1]));
      		
      		//自动缩进
      		jta[TabNum - 1].addKeyListener(new GeneTab(jta[TabNum - 1]));
      		
      		//括号自动生成
      		jta[TabNum - 1].addKeyListener(new GeneBracket(jta[TabNum - 1]));
      		
      	//注释
      		jta[TabNum - 1].getDocument().addDocumentListener(new HideNote());
    }
    
  /*===================================================================*/
    //编译运行    
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
     * 打开文件
     */
    private void openFile() {
        if(flag==2 && this.currentPath==null){
            //1、（刚启动记事本为0，刚新建文档为1）条件下修改后
            int result=JOptionPane.showConfirmDialog(IDEMainFrame.this, "是否将更改保存到无标题?", "记事本", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(result==JOptionPane.OK_OPTION){
                this.saveAs();
            }
        }else if(flag==2 && this.currentPath!=null){
            //2、（打开的文件2，保存的文件3）条件下修改
            int result=JOptionPane.showConfirmDialog(IDEMainFrame.this, "是否将更改保存到"+this.currentPath+"?", "记事本", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if(result==JOptionPane.OK_OPTION){
                this.save();
            }
        }
        //打开文件选择框
        JFileChooser choose=new JFileChooser();
        //选择文件
        int result=choose.showOpenDialog(this);
        if(result==JFileChooser.APPROVE_OPTION){
            //取得选择的文件
            File file=choose.getSelectedFile();
            //打开已存在的文件，提前将文件名存起来
            currentFileName=file.getName();
            //存在文件全路径
            currentPath=file.getAbsolutePath();
            flag=3;
            this.setTitle(this.currentPath);
            BufferedReader br=null;
            try {
                //建立文件流[字符流]
                InputStreamReader isr=new InputStreamReader(new FileInputStream(file),"GBK");
                br=new BufferedReader(isr);//动态绑定
                //读取内容
                StringBuffer sb=new StringBuffer();
                String line=null;
                while((line=br.readLine())!=null){
                    //sb.append(line+SystemParam.LINE_SEPARATOR);
                    sb.append(line + "\n");
                }
                New();
                //显示在文本框[多框]
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
     * 另存为
     */
    private void saveAs() {
        //打开保存框
        JFileChooser choose=new JFileChooser();
        //选择文件
        int result=choose.showSaveDialog(this);
        if(result==JFileChooser.APPROVE_OPTION){
            //取得选择的文件[文件名是自己输入的]
            File file=choose.getSelectedFile();
            FileWriter fw=null;
            //保存
            try {
                fw=new FileWriter(file);
                fw.write(jta[TabNum-1].getText());
                currentFileName=file.getName();
                currentPath=file.getAbsolutePath();
                //如果比较少，需要写
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
     * 保存
     */
    private void save() {
        if(this.currentPath==null){
            this.saveAs();
            if(this.currentPath==null){
                return;
            }
        }
        FileWriter fw=null;
        //保存
        try {
            fw=new FileWriter(new  File(currentPath));
            fw.write(jta[TabNum-1].getText());
            //如果比较少，需要写
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
        final JDialog gotoDialog = new JDialog(this, "转到下列行");
        JLabel gotoLabel = new JLabel("行数(L):");
        final JTextField linenum = new JTextField(5);
        linenum.setText("1");
        linenum.selectAll();

        JButton okButton = new JButton("确定");
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
                    // System.out.println("引索pos:"+pos);
                    if (pos == -1)
                        break;
                    lineNumber[t++] = pos++;
                }

                int gt = 1;
                try {
                    gt = Integer.parseInt(linenum.getText());
                } catch (NumberFormatException efe) {
                    JOptionPane.showMessageDialog(null, "请输入行数!", "提示", JOptionPane.WARNING_MESSAGE);
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

                gotoDialog.dispose();//关闭窗体
            }

        });

        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gotoDialog.dispose();
            }
        });

        //将组件添加到容器里
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
        final JDialog findDialog = new JDialog(this, "查找与替换", true);
        Container con = findDialog.getContentPane();
        con.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel searchContentLabel = new JLabel("查找内容(N) :");
        JLabel replaceContentLabel = new JLabel("替换为(P)　 :");
        final JTextField findText = new JTextField(22);
        final JTextField replaceText = new JTextField(22);
        final JCheckBox matchcase = new JCheckBox("区分大小写");
        ButtonGroup bGroup = new ButtonGroup();
        final JRadioButton up = new JRadioButton("向上(U)");
        final JRadioButton down = new JRadioButton("向下(D)");
        down.setSelected(true);
        bGroup.add(up);
        bGroup.add(down);
        JButton searchNext = new JButton("查找下一个(F)");
        JButton replace = new JButton("替换(R)");
        final JButton replaceAll = new JButton("全部替换(A)");
        searchNext.setPreferredSize(new Dimension(110, 22));
        replace.setPreferredSize(new Dimension(110, 22));
        replaceAll.setPreferredSize(new Dimension(110, 22));
        
        
        // "替换"按钮的事件处理
        replace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (replaceText.getText().length() == 0 && jta[TabNum-1].getSelectedText() != null)
                	jta[TabNum-1].replaceSelection("");
                if (replaceText.getText().length() > 0 && jta[TabNum-1].getSelectedText() != null)
                	jta[TabNum-1].replaceSelection(replaceText.getText());
            }
        });

        // "替换全部"按钮的事件处理
        replaceAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                jta[TabNum-1].setCaretPosition(0); // 将光标放到编辑区开头
                int a = 0, b = 0, replaceCount = 0;

                if (findText.getText().length() == 0) {
                    JOptionPane.showMessageDialog(findDialog, "请填写查找内容!", "提示", JOptionPane.WARNING_MESSAGE);
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
                            JOptionPane.showMessageDialog(findDialog, "找不到您查找的内容!", "记事本", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(findDialog, "成功替换" + replaceCount + "个匹配内容!", "替换成功", JOptionPane.INFORMATION_MESSAGE);
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
        }); /* "替换全部"按钮的事件处理结束 */

        
        
        // "查找下一个"按钮事件处理
        searchNext.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int a = 0, b = 0;
                int FindStartPos = jta[TabNum-1].getCaretPosition();
                String str1, str2, str3, str4, strA, strB;
                str1 = jta[TabNum-1].getText();
                str2 = str1.toLowerCase();
                str3 = findText.getText();
                str4 = str3.toLowerCase();
                // "区分大小写"的CheckBox被选中
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
                    JOptionPane.showMessageDialog(null, "找不到您查找的内容!", "记事本", JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });/* "查找下一个"按钮事件处理结束 */
        // "取消"按钮及事件处理
        JButton cancel = new JButton("取消");
        cancel.setPreferredSize(new Dimension(110, 22));
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findDialog.dispose();
            }
        });

        // 创建"查找与替换"对话框的界面
        JPanel bottomPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel topPanel = new JPanel();

        JPanel direction = new JPanel();
        direction.setBorder(BorderFactory.createTitledBorder("方向 "));
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

        // 设置"查找与替换"对话框的大小、可更改大小(否)、位置和可见性
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