package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

import com.kingdee.eas.fdc.market.DocumentItemCollection;
import com.kingdee.eas.fdc.market.DocumentItemInfo;
import com.kingdee.eas.fdc.market.DocumentOptionCollection;
import com.kingdee.eas.fdc.market.DocumentOptionHorizonLayoutEnum;
import com.kingdee.eas.fdc.market.DocumentOptionInfo;
import com.kingdee.eas.fdc.market.DocumentOptionLayoutEnum;
import com.kingdee.eas.fdc.market.DocumentSubjectInfo;
import com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum;

public class XCellCommonCreater implements XCellCreaterInterface {

	public static int IN = 6;//缩进
	
	public static int IN2 = 12;//
	
	public static int IN3 = 18;//
	
	public static int SPACE_ROW = 8;//行距
	
	public static int SPACE_OPTION = 6;//选项间距
	
	public static int SPACE_OPTION_INNER = 0;//选项与TOPIC的间距

	public XJPanel[] createXCell(List buttonAndTextList,DocumentSubjectInfo subj, int width) {
		// 第一步生成所有的控件，第二步根据排列属性进行排序
		// 生成 主题
		List panelList = new ArrayList();

		/*if (subj.getSubjectType().getValue() == DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION_VALUE) {
			// 生成说明部分
			JComponent[] descs = createLabel(subj.getFont(), subj.getAbsractTopic(), IN, width, false, subj.getHorizontalAlign().getValue());
			setListObjects(panelList, descs);
		} else{*/
			
		if(true){	
			List innerXCListList = new ArrayList();//存储的内容是List，每个List表示一个强制换行
			List innerXCList = new ArrayList();
			innerXCListList.add(innerXCList);//先把第一行增加进来
			innerXCList.add(new InnerXCellContainer(subj.getAbsractTopic(),subj.getFont(),0/*间隙*/,IN/*缩进*/));//题目标题，标题肯是属于第一行的OK
			DocumentItemCollection itemCollection = subj.getItems();
			for(int itemIndex=0 ; itemIndex<itemCollection.size() ; itemIndex++){
				DocumentItemInfo item = itemCollection.get(itemIndex);
				if(itemCollection.size() > 1 || DocumentOptionLayoutEnum.ALIGN_TYPE_LINE.compareTo(subj.getAlignType()) == 0){
					//如果存在多个Item或设置了分行显示，那么每个Item都独占一行
					innerXCList = new ArrayList();
					innerXCListList.add(innerXCList);
				}
				
				if(!subj.getSubjectType().equals(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION))
					innerXCList.add(new InnerXCellContainer(item.getTopic(),item.getFont(),0/**/,IN2));//分组标题
				
				DocumentOptionCollection optionCollection = item.getOptions();
				ButtonGroup buttonGroup = new ButtonGroup();//选择框分组，用于单选按钮
				for(int optionIndex = 0 ; optionIndex<optionCollection.size() ; optionIndex++){
					DocumentOptionInfo option = optionCollection.get(optionIndex);
					if(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE.compareTo(subj.getAlignType()) == 0){
						//如果分行显示，那么每个Option都独占一行
						innerXCList = new ArrayList();
						innerXCListList.add(innerXCList);
					}
					//src start
					if(DocumentOptionLayoutEnum.ALIGN_TYPE_FLOW.compareTo(subj.getAlignType()) == 0){
						if(optionIndex==0){
							//如果横排显示，那么标题选项换一行
							innerXCList = new ArrayList();
							innerXCListList.add(innerXCList);
						}
					}
					//src end
					JToggleButton button = getJToggleButton(subj.getSubjectType().getValue());
					if(button != null && button instanceof XJRadioButton){
						buttonGroup.add(button);//将单选按钮加入到按钮分组中
					}
					JTextComponent input = getInputField(option.getXLength(),option.getFont(),width-IN3);
					if(option.getId() != null){
						//如果Option.Id 不为空,将输入框及OptionId及选择框保存
						XButtonAndText bAndT = new XButtonAndText(option.getId().toString(),button,input);
						buttonAndTextList.add(bAndT);
					}
					innerXCList.add(new InnerXCellContainer(button,SPACE_OPTION,IN3));//增加选择框
					if(option.isIsTopicInverse()){
						//如果倒置：输入框+描述
						innerXCList.add(new InnerXCellContainer(input,SPACE_OPTION_INNER,IN3));//输入框
						innerXCList.add(new InnerXCellContainer(option.getTopic(),option.getFont(),SPACE_OPTION_INNER,IN3));//增加描述文字
					} else {
						//如果不倒置：描述：输入框
						innerXCList.add(new InnerXCellContainer(option.getTopic(),option.getFont(),SPACE_OPTION_INNER,IN3));//增加描述文字
						innerXCList.add(new InnerXCellContainer(input,SPACE_OPTION_INNER,IN3));//输入框
					}
				}
			}
			//处理 innerXCListList
			//删除空的,String不能合并，因为Font可能不同
			while(innerXCListList.size() > 0 ){
				List list = (List)innerXCListList.remove(0);
				XJPanel jPanel = createJPanel();
				jPanel.setSize(width,0);
				panelList.add(jPanel);//新增一行
				boolean listIsEmpty = true;
				while(list.size() > 0){
					InnerXCellContainer con = (InnerXCellContainer)list.remove(0);
					if(con.isEmpty()){
						continue;
					}
					listIsEmpty = false;//标记以下这个行不空
					XJPanel panel = (XJPanel)panelList.remove(panelList.size() - 1);
					if(con.type == InnerXCellContainer.STR){
						JComponent[] panels = integrateWithFlow(panel, con.str, con.font, con.prevSpace, con.in, width);
						setListObjects(panelList, panels);
					}else{
						JComponent[] panels = integrateWithFlow(panel, con.comp, null, con.prevSpace, con.in, width);
						setListObjects(panelList, panels);
					}
				}
				if(listIsEmpty){
					panelList.remove(panelList.size() - 1);
				}
			}
		}
		XJPanel[] components = null;
		if (panelList.size() > 0) {
			components = new XJPanel[panelList.size()];
			panelList.toArray(components);
		}
		return components;
	}

	private static XJPanel[] integrateOthers(XJPanel one, JComponent two, int space, int in, int width) {
		List reList = new ArrayList();
		int oneWidth = in;
		if (one.getComponentCount() > 0) {
			Component oneC = one.getComponent(one.getComponentCount() - 1);
			oneWidth = oneC.getX() + oneC.getWidth();
		}else{
			space = 0;//如果作为本行的第一个控件，那么前边是不需要加 space 的，只需要考虑缩进 in 即可。
		}
		int twoWidth = two.getWidth();
		if (twoWidth + space + oneWidth > width) {
			reList.add(one);
			if (two instanceof XJPanel) {
				reList.add((XJPanel) two);
			} else {
				XJPanel j = createJPanel();
				two.setBounds(in, 0, two.getWidth(), two.getHeight());
				j.setBounds(0, 0, width, two.getHeight());
				j.add(two);
				reList.add(j);
			}
		} else {
			two.setBounds(oneWidth + space, 0, two.getWidth(), two.getHeight());
			one.add(two);
			one.setBounds(0, 0, width, Math.max(one.getHeight(), two.getHeight()));
			reList.add(one);
		}
		XJPanel[] returns = null;
		if (reList.size() > 0) {
			returns = new XJPanel[reList.size()];
			reList.toArray(returns);
		}
		return returns;
	}

	private static XJPanel[] integrateString(XJPanel one, Font font, String two, int space, int in, int width) {
		XJPanel[] returns = null;
		List reList = new ArrayList();
		int oneWidth = 0;
		int temp_in = in;
		if (one.getComponentCount() > 0) {
			Component oneC = one.getComponent(one.getComponentCount() - 1);
			oneWidth = oneC.getX() + oneC.getWidth();
			temp_in = 0;
		}
		XJLabel newTwoL = new XJLabel();
		newTwoL.setFont(font);
		String newTwoS = appendStr2Label(newTwoL, width - space - oneWidth - temp_in, true, two);
		newTwoL.setLocation(space + oneWidth + temp_in, 0);
		one.add(newTwoL);
		reList.add(one);
		if (newTwoS != null) {
			setListObjects(reList, createLabel(font, newTwoS, in, width, true, DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT_VALUE));
		}
		if (reList.size() > 0) {
			returns = new XJPanel[reList.size()];
			reList.toArray(returns);
			for (int i = 0; returns != null && i < returns.length; i++) {
				updateVerticalAlignOfJPanel(returns[i], DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_CENTER_VALUE);
			}
		}
		return returns;
	}

	private static XJPanel[] integrateWithFlow(XJPanel one, Object two, Font font, int space, int in, int width) {
		XJPanel[] pans = null;
		if (two instanceof JComponent) {
			pans = integrateOthers(one, (JComponent) two, space, in, width);
		} else if (two instanceof String) {
			pans = integrateString(one, font, (String) two, space, in, width);
		}
		for (int i = 0; pans != null && i < pans.length; i++) {
			updateVerticalAlignOfJPanel(pans[i], DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_CENTER_VALUE);
		}
		return pans;
	}


	/**
	 * 生成Label组成的Panel
	 * @param font 字体
	 * @param content 内容
	 * @param in 缩进
	 * @param maxWidth 最大宽度
	 * @param autoWidth 
	 * @param horizontalAlign 水平对齐（居中、靠左） DocumentOptionHorizonLayoutEnum
	 * @return 行数组
	 */
	private static XJPanel[] createLabel(Font font, String content, int in, int maxWidth, boolean autoWidth, int horizontalAlign) {
		//计算宽度
		int width = maxWidth - in;//默认宽度（这是经常使用的）
		if(horizontalAlign == DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_CENTER_VALUE){
			//当“居中显示”的时候，这样计算宽度（不太经常使用）
			width = maxWidth - (2 * in);
		}
		XJLabel[] labs = createPureLabel(font, content,width , autoWidth, horizontalAlign);
		if (labs == null || labs.length == 0) {
			return null;
		}
		XJPanel[] res = new XJPanel[labs.length];
		for (int i = 0; i < labs.length; i++) {
			res[i] = createJPanel();
			res[i].setBounds(0, 0, maxWidth, labs[i].getHeight());
			labs[i].setLocation(in, 0);
			res[i].add(labs[i]);
		}
		return res;
	}

	/**
	 * 生成Label
	 * @param font 字体
	 * @param content 内容
	 * @param maxWidth 最大宽度
	 * @param autoWidth 是否自适应宽度
	 * @param horizontalAlign 水平对齐方式 DocumentOptionHorizonLayoutEnum
	 * @return Label
	 */
	public static XJLabel[] createPureLabel(Font font, String content, int maxWidth, boolean autoWidth, int horizontalAlign) {
		XJLabel[] res = null;
		List list = new ArrayList();
		String newContent = content;
		while (newContent != null) {
			XJLabel newLabel = new XJLabel();
			newLabel.setFont(font);
			newLabel.setHorizontalAlignment(horizonEnum2SwingInt(horizontalAlign));
			newContent = appendStr2Label(newLabel, maxWidth, autoWidth, newContent);
			newLabel.setLocation(0, 0);
			list.add(newLabel);
		}
		if (list.size() > 0) {
			res = new XJLabel[list.size()];
			list.toArray(res);
		}
		return res;
	}
	
	/**
	 * 将本系统的参数转换成java标准参数
	 * @param horizontalAlign 水平对齐
	 * @return 水平对齐方式，参考SwingConstants
	 */
	private static int horizonEnum2SwingInt(int horizontalAlign){
		if(horizontalAlign == DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_CENTER_VALUE){
			return SwingConstants.CENTER;
		}else if (horizontalAlign == DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT_VALUE){
			return SwingConstants.LEFT;
		}else{
			return SwingConstants.LEFT;
		}
	}

	/**
	 * 将字符串拼接到Label上
	 * @param label Label
	 * @param maxWidth Label的最大宽度
	 * @param autoWidth 是否Label自动适应字符串宽度。true：Label宽度恰好存放字符串且不超过maxWidth；false：Label采用最大宽度
	 * @param str 被拼接的内容
	 * @return str被拼接后剩余的内容。为了防止超出maxWidth的宽度，str可能会有剩余
	 */
	private static String appendStr2Label(XJLabel label, int maxWidth, boolean autoWidth, String str) {
		if(maxWidth <= 0){
			return str;
		}
		String newStr = label.getText() + str;
		FontMetrics fm = label.getFontMetrics(label.getFont());
		int i = 0;
		int length = 0;
		for (; i <= newStr.length() && (length = fm.stringWidth(newStr.substring(0, i))) <= maxWidth; i++)
			;
		if (length > maxWidth) {
			i--;
			String labT = str.substring(0, i);
			label.setText(labT);
			if (!autoWidth) {
				label.setSize(maxWidth, fm.getHeight());
			} else {
				label.setSize(fm.stringWidth(labT), fm.getHeight());
			}
			return str.substring(i);
		} else {
			label.setText(newStr);
			if (!autoWidth) {
				label.setSize(maxWidth, fm.getHeight());
			} else {
				label.setSize(fm.stringWidth(newStr), fm.getHeight());
			}
			return null;
		}
	}

	/**
	 * 更新panel内部的所有控件的位置。当panel增加了新的控件后，可能需要调整panel的高度。
	 * @param panel
	 * @param align，当前不起作用。全部控件将在panel上居中摆放
	 */
	private static void updateVerticalAlignOfJPanel(XJPanel panel, int align) {
		int maxHeight = 0;
		for (int i = 0; i < panel.getComponentCount(); i++) {
			if (maxHeight < panel.getComponent(i).getHeight()) {
				maxHeight = panel.getComponent(i).getHeight();
			}
		}
		maxHeight += SPACE_ROW;
		for (int i = 0; i < panel.getComponentCount(); i++) {
			Component c = panel.getComponent(i);
			c.setLocation(c.getX(), (maxHeight - c.getHeight() + 1) / 2);
		}
		panel.setSize(panel.getWidth(), maxHeight);
	}

	/**
	 * 将objs添加到list中
	 * @param list
	 * @param objs
	 */
	private static void setListObjects(List list, Object[] objs) {
		for (int i = 0; i < objs.length; i++) {
			list.add(objs[i]);
		}
	}
	/**
	 * 获取一个选择按钮，单选按钮或多选按钮
	 * @param subjectType 参照 DocumentSubjectTypeEnum
	 * @return
	 */
	private static JToggleButton getJToggleButton(int subjectType) {
		if ( subjectType == DocumentSubjectTypeEnum.SUBJECT_TYPE_MULTIPLE_VALUE) {
			XJCheckBox box = new XJCheckBox();
			box.setSize(18,18);
			box.setBackground(Color.WHITE);
			return box;
		} else if ( subjectType == DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE_VALUE) {
			XJRadioButton radio = new XJRadioButton();
			radio.setBackground(Color.WHITE);
			radio.setSize(18,18);
			return radio;
		} else {
			return null;
		}
	}
	
	private static JTextComponent getInputField(int xLength,Font font,int maxLength){
		JTextComponent comp = null;
		if(xLength > 0 && font != null && maxLength > 0){
			int length = metric2Pixed(xLength);
			int height = length/maxLength + 1;
			if(height > 1){
				comp = new XJTextArea();
			}else{
				comp = new XJTextField();
			}
			comp.setFont(font);
			int realH = comp.getFontMetrics(comp.getFont()).getHeight() * height;
			int realW = Math.min(length,maxLength);
			comp.setSize(realW,realH);
			comp.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.BLACK));
		}
		return comp;
	}
	
	private static XJPanel createJPanel(){
		XJPanel panel = new XJPanel(null);
		panel.setBackground(Color.WHITE);
		return panel;
	}
	
	
	/**
	 * 将毫米转换为屏幕的象素数
	 * @return 象素
	 */
	private static int metric2Pixed(int mm){
		Toolkit tk = Toolkit.getDefaultToolkit();
		//不知道这样转换是否合理
		Double pix = new Double(tk.getScreenResolution() * mm / 25.4f);
		return pix.intValue();
	}
	
	
	class InnerXCellContainer{
		final static int STR = 0;
		final static int COMP = 1;
		/**
		 * 类型，参考 InnerXCellContainer.STR ; InnerXCellContainer.COMP
		 */
		int type;
		/**
		 * 字符串 ，与 InnerXCellContainer.STR 关联
		 */
		String str;
		/**
		 * 字体，字符串的字体，对控件不起作用
		 */
		Font font;
		/**
		 * 控件，与 InnerXCellContainer.COMP 关联
		 */
		JComponent comp;
		/**
		 * 记录与前一个控件的间隙，如果前面没有控件，那么此值不起作用
		 */
		int prevSpace;
		/**
		 * 缩进，如果处于行的第一个位置，那么in生效，否则不起作用
		 */
		int in;
		
		InnerXCellContainer(String str,Font font,int prevSpace,int in){
			this.type = InnerXCellContainer.STR;
			this.str = str;
			this.font = font;
			this.prevSpace = prevSpace;
			this.in = in;
		}
		
		InnerXCellContainer(JComponent comp,int prevSpace,int in){
			this.type = InnerXCellContainer.COMP;
			this.comp = comp;
			this.prevSpace = prevSpace;
			this.in = in;
		}
		
		boolean isEmpty(){
			boolean re = false;
			if(type == STR){
				if(str == null || str.trim().length() == 0){
					re = true;
				}
			}else if(type == COMP){
				if(comp == null || comp.getWidth() == 0 || comp.getHeight() == 0){
					re = true;
				}
			}else{
				re = true;
			}
			return re;
		}
	}
}
