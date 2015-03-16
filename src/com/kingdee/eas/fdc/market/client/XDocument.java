package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

import com.kingdee.eas.fdc.market.DocumentInfo;
import com.kingdee.eas.fdc.market.DocumentProperties;
import com.kingdee.eas.fdc.market.DocumentSubjectCollection;
import com.kingdee.eas.fdc.market.DocumentSubjectInfo;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryCollection;


public class XDocument extends JPanel implements Scrollable,Printable {
	
	private boolean enableRightMouseEvent = true;
	private DocumentInfo doc;
	private XCellsAndSubjectAndAnswer cellAndAnswer;

	/**
	 * 存储所有的页面
	 */
	private List pageList ;

	/**
	 * 被选择的题序号
	 */
	private int subjectNumberThatBeSelect;
	
	/**
	 * 被右击选择的序号
	 */
	private int subjectNumberThatBeRightClicked=-1;
	
	/**
	 * 构造函数，页面间距设置为 10
	 */
	public XDocument(){
		super(new XDocumentLayout(10/*页面间距*/));
		setBackground(Color.GRAY);
	}
	/**
	 * 设置为空文档界面
	 */
	public void setEmptyDoc(){
		setDoc(getDefaultDoc());
	}
	/**
	 * 设置文档信息
	 * @param doc
	 */
	public void setDoc(DocumentInfo doc) {
		this.doc = doc;
		reBuild();
	}
	/**
	 * 重新建立文档界面
	 */
	public void reBuild(){
		initDocument(doc);
	}
	/**
	 * 初始化界面，使用文档信息
	 * @param doc
	 */
	private void initDocument(DocumentInfo doc){
		this.removeAll();
		pageList = new ArrayList();//存储所有的页面
		cellAndAnswer = new XCellsAndSubjectAndAnswer();//存储所有的答案、控件关系
		//增加一空白页
		addXPage(createXPage(doc));
		//从第一题号开始，将题目增加到页面中
		realAddDSubjectFromIndex(0);
		//刷新页面
		updateUI();
	}
	/**
	 * 获取当前界面文档信息
	 * @return DocumentInfo
	 */
	public DocumentInfo getDoc() {
		return doc;
	}
	/**
	 * 获取当前文档的“回答”信息
	 * @return QuestionPaperAnswerEntryCollection
	 */
	public  QuestionPaperAnswerEntryCollection getAnswerCollection(){
		return cellAndAnswer.getAnswer();
	}
	/**
	 * 设置当前文档的“回答”信息
	 * @param questionPaperAnswerEntryCollection
	 */
	public void setAnswerCollection(QuestionPaperAnswerEntryCollection questionPaperAnswerEntryCollection){
		cellAndAnswer.setAnswer(questionPaperAnswerEntryCollection);
	}

	//在Xpage中，被鼠标点击事件调用
	public void selectedSubjectNumber1(int selectedSubjectNumber){
		if(subjectNumberThatBeRightClicked >=0 && selectedSubjectNumber != subjectNumberThatBeRightClicked){
			//这说明在点击鼠标前，有被双击选中的区域
			//进行变更位置的操作
			DocumentSubjectInfo curr = getSubjectInfo(selectedSubjectNumber);
			DocumentSubjectInfo willBeMoved = getSubjectInfo(subjectNumberThatBeRightClicked);
			if(willBeMoved != null && curr != null && willBeMoved.getSubjectNumber() != curr.getSubjectNumber() + 1){
				//如果当前被选不空，并且双击被选不空，并且新的位置与原来位置不同
				deleteDSubject(willBeMoved);
				willBeMoved.setSubjectNumber(curr.getSubjectNumber() + 1);
				insertDSubject(willBeMoved);
			}
		}
		setPageSubjectNumberAndColor(selectedSubjectNumber,Color.RED);
		subjectNumberThatBeSelect = selectedSubjectNumber;
		subjectNumberThatBeRightClicked = -1;
	}
	//在Xpage中，被鼠标点击事件调用
	public void selectedSubjectNumber2(int selectedSubjectNumber){
		if(!enableRightMouseEvent){
			//不响应鼠标右击事件
			return ;
		}
		setPageSubjectNumberAndColor(selectedSubjectNumber,Color.BLACK);
		subjectNumberThatBeSelect = selectedSubjectNumber;
		subjectNumberThatBeRightClicked = selectedSubjectNumber;
	}
	
	/**
	 * 设置某题号的颜色
	 * @param selectSubjectNumber 题号（题号subjectNumber不等于showNumber）
	 * @param color 颜色
	 */
	private void setPageSubjectNumberAndColor(int selectSubjectNumber,Color color){
		for(int i=0 ; i<pageList.size() ; i++){
			XPage xPage = (XPage)pageList.get(i);
			if(xPage.getMinSubjectNumber() <= selectSubjectNumber && xPage.getMaxSubjectNumber() >= selectSubjectNumber){
					xPage.setBeSelectedSubjectNumberAndColor(selectSubjectNumber,color);
					xPage.updateUI();
			}else{
				if(xPage.getBeSelectedSubjectNumber() != 0){
					xPage.setBeSelectedSubjectNumberAndColor(0,color);//为啥设置为0呢？因为序号是从1开始，O(∩_∩)O哈哈~。
					xPage.updateUI();
				}
			}
		}
	}
	
	
	public DocumentSubjectInfo getSelectedSubjectInfo(){
		return getSubjectInfo(subjectNumberThatBeSelect);
	}
	
	public DocumentSubjectInfo getSubjectInfo(int selectedSubjectNumber){
		DocumentSubjectCollection sc = doc.getSubjects();
		for(int i=0 ;i <sc.size() ; i++){
			DocumentSubjectInfo s = sc.get(i);
			if(s.getSubjectNumber() == selectedSubjectNumber){
				return s;
			}
		}
		return null;
	}
	
	public void addXPage(XPage xPage){
		pageList.add(xPage);
		add(xPage);
	}
	
	public XPage createXPage(DocumentProperties dp){
		int pWidth = metric2Pixed(dp.getWidth());
		int pHeight = metric2Pixed(dp.getHeight());
		int x = metric2Pixed(dp.getRightMarge());
		int y = metric2Pixed(dp.getTopMarge());
		int rW = metric2Pixed(dp.getWidth()-dp.getRightMarge()-dp.getLeftMarge());
		int rH = metric2Pixed(dp.getHeight()-dp.getTopMarge()-dp.getBottomMarge());
		//
		XPage xPage = new XPage(this);
		xPage.setSize(new Dimension(pWidth, pHeight));
		xPage.setMarge(new Rectangle(x,y,rW,rH));
		xPage.setColumn(dp.getColumnCount());
		//设置页眉，页脚
		xPage.setHeaderFooter(dp.getHeader(), dp.getFooter(), dp.getFont());
		xPage.init();
		return xPage;
	}
	
	public MediaPrintableArea getMediaPrintableArea(){
		//右边距
		float x = (float)doc.getRightMarge();
		//上边距
		//float fontHeight = pixed2Metric(getFontMetrics(doc.getFont()).getHeight());
		float y = 4f;//((float)doc.getTopMarge() - fontHeight)/2;
		//宽度
		float width = (float)(doc.getWidth()-doc.getRightMarge()-doc.getLeftMarge());
		if(width <= 0){
			width = 1.0f;
		}  
		//高度
		float height = (float)doc.getHeight() - (2*y);
		return new MediaPrintableArea(x,y,width,height,MediaPrintableArea.MM);
	}
	
	/**
	 * 修改后保存。只修改了内容，不修改题目的序号
	 */
	public void updateDSubject(DocumentSubjectInfo dSubject){
		int subjectNumber = dSubject.getSubjectNumber();
		int index = subjectNumber-1;
		if(index < 0){
			//保险起见，还是这样吧，其实也大可不必
			insertDSubject(dSubject);
			return;
		}
		
		int thePageWillBeChanged = Integer.MAX_VALUE;
		for(int pageIndex = 0; pageIndex<pageList.size() ; pageIndex++){
			XPage xPage = (XPage)pageList.get(pageIndex);
			if(this.haveTheSubjectNumber(xPage,subjectNumber)){
				thePageWillBeChanged = pageIndex;
				int point = xPage.getPointerBySubjectNumber(subjectNumber);
				xPage.goBackPoint(point);//让显示的区域回滚到指定位置
				xPage.getLeavings();//这样就能把其他的未加入的控件删除了。
				break;
			}
		}
		for(int i=pageList.size()-1 ; i>thePageWillBeChanged ; i--){
			//从缓存中删除这个页面
			XPage xp = (XPage)pageList.remove(i);
			//从界面上删除这个页面
			this.remove(xp);
		}
		realAddDSubjectFromIndex(index);
		
	}
	
	public void insertDSubject(DocumentSubjectInfo dSubject){
		//序号虽然是 size + 1；但是插入的时候可不能这样哦，size 才是插入时使用的索引，所以要减1；
		int totalCount = doc.getSubjects().size();
		if(dSubject.getSubjectNumber() <= 0){
			dSubject.setSubjectNumber(totalCount+1);
		}
		int subjectNumber = dSubject.getSubjectNumber();
		int index = subjectNumber-1;
		if(index < totalCount){
			int indexBeReset = doc.getSubjects().get(index).getSubjectNumber();
			//重新绘制界面
			int pageIndex = 0;
			for(; pageIndex<pageList.size() ; pageIndex++){
				XPage xPage = (XPage)pageList.get(pageIndex);
				if(this.haveTheSubjectNumber(xPage,indexBeReset)){
					int point = xPage.getPointerBySubjectNumber(indexBeReset);
					xPage.goBackPoint(point);//让显示的区域回滚到指定位置
					xPage.getLeavings();//这样就能把其他的未加入的控件删除了。
					break;
				}
			}
			for(int i=pageList.size()-1 ; i>pageIndex ; i--){
				//从缓存中删除这个页面
				XPage xp = (XPage)pageList.remove(i);;
				//从界面上删除这个页面
				this.remove(xp);
			}
		}
		//重新安排序号
		doc.getSubjects().addObject(index,dSubject);
		resetSubjectNumber(index);
		realAddDSubjectFromIndex(index);
	}
	
	public void deleteDSubject(DocumentSubjectInfo dSubject){
		int totalCount = doc.getSubjects().size();
		int subjectNumber = dSubject.getSubjectNumber();
		int index = subjectNumber-1;
		if(index < totalCount){
			int indexBeReset = doc.getSubjects().get(index).getSubjectNumber();
			//重新绘制界面
			int pageIndex = 0;
			for(; pageIndex<pageList.size() ; pageIndex++){
				XPage xPage = (XPage)pageList.get(pageIndex);
				if(this.haveTheSubjectNumber(xPage,subjectNumber)){
					int point = xPage.getPointerBySubjectNumber(indexBeReset);
					xPage.goBackPoint(point);//让显示的区域回滚到指定位置
					xPage.getLeavings();//这样就能把其他的未加入的控件删除了。
					break;
				}
			}
			for(int i=pageList.size()-1 ; i>pageIndex ; i--){
				//从缓存中删除这个页面
				XPage xp = (XPage)pageList.remove(i);;
				//从界面上删除这个页面
				this.remove(xp);
				
			}
			doc.getSubjects().removeObject(index);
			//重新安排序号
			resetSubjectNumber(index);
		}
		//doc.getSubjects().add(dSubject);
		realAddDSubjectFromIndex(index);
	}
	

	private void realAddDSubjectFromIndex(int subjectIndexInDoc){
		//subjectL.add(dSubject);
		DocumentSubjectCollection subjectCollection = doc.getSubjects();
		for(int i=subjectIndexInDoc ; i<subjectCollection.size() ; i++){
			XPage xPage = (XPage)pageList.get(pageList.size() - 1);
			//应该有个校验过程
			List buttonAndTextList = new ArrayList();
			XDimension xd = xPage.createXDimension(buttonAndTextList,subjectCollection.get(i));
			//增加区域
			addXDimension(xd);
			//增加控件关系
			addButtonAndText(buttonAndTextList);
		}
	}
	
	private void addButtonAndText(List buttonAndTextList){
		for(int i=0 ; i<buttonAndTextList.size() ; i++){
			XButtonAndText bt = (XButtonAndText)buttonAndTextList.get(i);
			cellAndAnswer.put(bt.optionId,bt);
		}
	}
	
	private void resetSubjectNumber(int index){
		DocumentSubjectCollection sc = doc.getSubjects();
		for(int i=index; i<sc.size() ; i++){
			sc.get(i).setSubjectNumber(i+1);
		}
	}
	


	protected boolean isEnableRightMouseEvent() {
		return this.enableRightMouseEvent;
	}

	public void setEnableRightMouseEvent(boolean enableRightMouseEvent) {
		this.enableRightMouseEvent = enableRightMouseEvent;
	}

	private boolean haveTheSubjectNumber( XPage page, int subjectNumber){
		return page.getMinSubjectNumber() <= subjectNumber && page.getMaxSubjectNumber()>= subjectNumber;
	}
	
	
	private void addXDimension(XDimension dimension){
		XPage xPage = (XPage)pageList.get(pageList.size() - 1);
		xPage.addXDimension(dimension);
		List leavings =  xPage.getLeavings();
		if(leavings.size() > 0){
			addXPage(createXPage(this.doc));
			for(int i=0 ; i<leavings.size() ; i++){
				addXDimension((XDimension)leavings.get(i));
			}
		}
	}
	
	public List getXPageList(){
		return pageList;
	}
	
	public int getXPageCount(){
		if(pageList == null){
			return 0;
		}else{
			return pageList.size();
		}
	}
	/**
	 * @see Printable
	 */
	public int print(Graphics graphics, PageFormat pageFormat, int page)
			throws PrinterException {
		//默认打印机为 72 dpi（Dot Per Inch）
		if(page >= getXPageCount()){
			return Printable.NO_SUCH_PAGE;
		}
		
		double scale = 72f/getScreenResolution();//计算缩放比例
		
		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.scale(scale, scale);
		((XPage)getXPageList().get(page)).xPrint(0, 0, graphics);
		return Printable.PAGE_EXISTS;
	}
	
	public int getScreenResolution(){
		Toolkit tk = Toolkit.getDefaultToolkit();
		return tk.getScreenResolution();
	}
	
	/**
	 * 将毫米转换为屏幕的象素数
	 * @return 象素
	 */
	private int metric2Pixed(int mm){
		Double pix = new Double(getScreenResolution() * mm / 25.4f);
		return pix.intValue();
	}
	
	private float pixed2Metric(int pix){
		Double metric = new Double(pix * 25.4/getScreenResolution());
		return metric.floatValue();
	}
	
	private DocumentInfo getDefaultDoc(){
		DocumentInfo doc = new DocumentInfo();
		doc.setWidth(200);
		doc.setHeight(240);
		doc.setTopMarge(15);
		doc.setBottomMarge(15);
		doc.setRightMarge(10);
		doc.setLeftMarge(10);
		doc.setColumnCount(1);
		doc.setHeader("金蝶软件-问卷调查");
		doc.setFooter("www.kingdee.com");
		return doc;
	}

	   /**
     * Returns the preferred size of the viewport for a view component.
     * This is implemented to do the default behavior of returning
     * the preferred size of the component.
     * 
     * @return the <code>preferredSize</code> of a <code>JViewport</code>
     * whose view is this <code>Scrollable</code>
     */
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }


    /**
     * Components that display logical rows or columns should compute
     * the scroll increment that will completely expose one new row
     * or column, depending on the value of orientation.  Ideally, 
     * components should handle a partially exposed row or column by 
     * returning the distance required to completely expose the item.
     * <p>
     * The default implementation of this is to simply return 10% of
     * the visible area.  Subclasses are likely to be able to provide
     * a much more reasonable value.
     * 
     * @param visibleRect the view area visible within the viewport
     * @param orientation either <code>SwingConstants.VERTICAL</code> or
     *   <code>SwingConstants.HORIZONTAL</code>
     * @param direction less than zero to scroll up/left, greater than
     *   zero for down/right
     * @return the "unit" increment for scrolling in the specified direction
     * @exception IllegalArgumentException for an invalid orientation
     * @see JScrollBar#setUnitIncrement
     */
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        switch(orientation) {
        case SwingConstants.VERTICAL:
            return visibleRect.height / 10;
        case SwingConstants.HORIZONTAL:
            return visibleRect.width / 10;
        default:
            throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }


    /**
     * Components that display logical rows or columns should compute
     * the scroll increment that will completely expose one block
     * of rows or columns, depending on the value of orientation. 
     * <p>
     * The default implementation of this is to simply return the visible
     * area.  Subclasses will likely be able to provide a much more 
     * reasonable value.
     * 
     * @param visibleRect the view area visible within the viewport
     * @param orientation either <code>SwingConstants.VERTICAL</code> or
     *   <code>SwingConstants.HORIZONTAL</code>
     * @param direction less than zero to scroll up/left, greater than zero
     *  for down/right
     * @return the "block" increment for scrolling in the specified direction
     * @exception IllegalArgumentException for an invalid orientation
     * @see JScrollBar#setBlockIncrement
     */
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        switch(orientation) {
        case SwingConstants.VERTICAL:
            return visibleRect.height;
        case SwingConstants.HORIZONTAL:
            return visibleRect.width;
        default:
            throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
    }  
    

    /**
     * Returns true if a viewport should always force the width of this 
     * <code>Scrollable</code> to match the width of the viewport.
     * For example a normal text view that supported line wrapping
     * would return true here, since it would be undesirable for
     * wrapped lines to disappear beyond the right
     * edge of the viewport.  Note that returning true for a
     * <code>Scrollable</code> whose ancestor is a <code>JScrollPane</code>
     * effectively disables horizontal scrolling.
     * <p>
     * Scrolling containers, like <code>JViewport</code>,
     * will use this method each time they are validated.  
     * 
     * @return true if a viewport should force the <code>Scrollable</code>s
     *   width to match its own
     */
    public boolean getScrollableTracksViewportWidth() {
	if (getParent() instanceof JViewport) {
	    return (((JViewport)getParent()).getWidth() > getPreferredSize().width);
	}
	return false;
    }

    /**
     * Returns true if a viewport should always force the height of this 
     * <code>Scrollable</code> to match the height of the viewport.
     * For example a columnar text view that flowed text in left to
     * right columns could effectively disable vertical scrolling by 
     * returning true here.
     * <p>
     * Scrolling containers, like <code>JViewport</code>,
     * will use this method each time they are validated.  
     * 
     * @return true if a viewport should force the Scrollables height
     *   to match its own
     */
    public boolean getScrollableTracksViewportHeight() {
	if (getParent() instanceof JViewport) {
	    return (((JViewport)getParent()).getHeight() > getPreferredSize().height);
	}
	return false;
    }

}
