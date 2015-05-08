package com.kingdee.eas.fdc.schedule.util.subway;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.eas.fdc.schedule.util.subway.TestRenderer;

/**
 * 地铁图控件 <br>
 * 本控件用于显示一串关键的节点，以及节点各自的状态，<br>
 * 就像地图站路线图，标志哪些已经过，正在到达哪站，哪些即将到达<br>
 * 
 */
public class KDSubwayMap extends JComponent {
	private static final long serialVersionUID = 8667100535673641865L;
	
	private Map<Integer,Map> map ;
//	红
	private static Color RED = new Color(255, 0, 0);
//	绿
	private static Color GREEN = new Color(146, 208, 80);
//	黄
	private static Color ORANGE = new Color(255, 255, 0);
//	粉
	private static Color PINK = new Color(230, 185, 184);
//	蓝
	private static Color BLUE= new Color(79, 129, 189);
//	茶色
	private static Color TAN= new Color(196, 189, 151);
	//如果大于两条会用到
	private int stage;
	// 宽
	private static int width = 1000;
	// 高
	private static int height = 100;
	// 左右边距
	private int clip = 50;
	// 上边距
	private int top = 53;
//	private int top = 55;

	// 最佳宽度
//	int preferredWidth = 800;
	int preferredWidth = 300 + ((itemNums - 1) * lineLength);

	// 滚动面板
	private JPanel pnlScroll;
	// 线面板
	private KDSubwayLinePanel pnlLine;
	// 节点面板
	private JPanel pnlItem;

	// 说明面板
	private static JPanel pnlDesc;

	// 节点名称集合
	private String[] names;
	// 长编码集合
	private String[] longNumbers;
	// 项目id
	private String project;
	int[] taskComleteRates ;	
	// 并集节点长编码集合
	private String[] unionNames = null;
	// 考核版连接线个数集合
	private int[] CheckLineCounts = null;
	// 考核版连接线个数集合
	private int[] ExecLineCounts = null;
	// 当前节点长编码集合
	private String[] tmpNames = null;
	private List<String> itemNamesList = new ArrayList<String>();
	// 节点名称集合
//	private String[] dates;
	// 节点名称集合 由于日期要显示颜色和Value一样，搞成object。
	private Object[] dates;
	// 节点状态集合
	private KDSubwayItemState[] allStates;
	// 节点状态
	private int[] itemStates;
	// 节点属性集合
	private  Object[] values;
	// 节点集合
	private KDSubwayItemButton[] items;
	
	// 并集节点个数
	private static int itemNums = 0;

	// 节点大小
	private static int itemSize = 10;

	// 连接线线长度(两节点中点间的距离)60
	private static int lineLength = 100;
	// 连接线线宽度
	private static int lineHeight = 4;
	// 连接线线颜色
//	private static Color lineColor = Color.BLACK;
//	private static Color lineColor = new Color(56, 93, 138);
	private static Color lineColor = new Color(70, 130, 180);
//	private static Color lineColor = new Color(0, 255, 255);
	private static Color completedColor = new Color(124, 252, 0);

//	private static Color lineColor = new Color(74, 126, 187);


	  //默认是深蓝边
//	private static Color DARK_BULE =new Color(56, 93, 138);
	// 节点渲染器
//	private KDSubwayItemRenderer renderer;
	private TestRenderer renderer;
	// 是否显示节点名称
	private boolean isShowName = true;
	// 是否显示节点名称
	private boolean isShowDate = true;
	//是否评价
	private boolean isNeedEvaluation = false;
//	灰色
//	private Color  GREY = new Color(143, 153, 102);
	//根据屏幕分辨率设置滚动条大小
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	//减去左树宽度
	private int scrollWidth = dim.width-280;

	/**
	 * 这是老的构造方法<br>
	 * 
	 * @param names
	 *            名称集合
	 * @param allStates
	 *            所有可能的状态集合
	 * @param itemStates
	 *            各节点对应的状态
	 * @param values
	 *            各节点明细属性值
	 */
	public KDSubwayMap(KDSubwayItemState[] allStates) {
//		this.allStates = allStates;
		addDesc();
	}
	/**
	 * 这是新的构造方法<br>
	 * 
	 */
	public KDSubwayMap(Map<Integer, Map> map) {
		this.map = map;
		//初始化整个控件
		initCompt();
		
	}

	/**
	 * 设置渲染<br>
	 * 
	 */
	public void setRenderer (TestRenderer renderer) {
		if(renderer!=null){
			
			this.renderer = renderer;
			for (int i = 0; i < items.length; i++) {
				if(items[i]!=null){					
					items[i].setRenderer(renderer);
				}
				
			}
		}
	}
	/**
	 * 获取渲染
	 */
	public KDSubwayItemRenderer getRenderer() {
		return renderer;
	}
	/**
	 * 初始化整个控件
	 * 先画底线，再画完成率线，最后画点
	 * 线和点都是有坐标的，画点的同时画点上面的名称与点下面的日期
	 */
	protected void initCompt() {
		setLayout(null);
		pnlScroll = new JPanel();
		pnlScroll.setLayout(null);
		//如果有完成率的话，对齐的复杂度又加大了
		//临时两个名称数组，作为归并用
		String[] tmpNamesA = null;
		String[] tmpNamesB = null;
		//目前只有两组数据考核版或执行版，所以是2
		for (int i = 0; i < 2; i++) {
			if(map.containsKey(i)){
				if(map.get(i) != null){
					Map innerMap = map.get(i);
					//考核版的名称
					tmpNamesA = (String[]) innerMap.get("names");

					//当前Names集合。执行版或者多个版本
					//目前只有两组数据考核版或执行版，所以是2
					if(i+1 < 2 && map.get(i+1) != null ){
						Map innerMapB = map.get(i+1);
						tmpNamesB = (String[]) innerMapB.get("names");
					}
					//合并上次Names
					itemNamesList  = sort(tmpNamesA,this.unionNames);
					tmpNamesA = (String[])itemNamesList.toArray(new String[itemNamesList.size()]);			
					itemNamesList = sort(tmpNamesA,tmpNamesB);					
					this.unionNames = (String[])itemNamesList.toArray(new String[itemNamesList.size()]);
					//总数是Names
					itemNums = this.unionNames.length;
				}
			}
		}
		//目前只有两组数据考核版或执行版，所以是
		for (int i = 0; i < 2; i++) {		
			if(map.containsKey(i)){
				if(map.get(i) != null){				
					Map innerMap = map.get(i);		
					this.names = (String[]) innerMap.get("names");
					tmpNames = (String[]) innerMap.get("names");
					this.longNumbers = (String[]) innerMap.get("longNumbers");
					this.project = (String) innerMap.get("project");
//					itemNums = names.length;
					this.dates = (Object[]) innerMap.get("dates");
					//所有状态
					this.allStates = (KDSubwayItemState[]) innerMap.get("allStates");
					//点的状态
					this.itemStates = (int[]) innerMap.get("itemStates");
					//完成率
					this.taskComleteRates = (int[]) innerMap.get("taskComleteRates");
					//是否需要评价
					if(innerMap.get("isNeedEvaluation")!=null){
						this.isNeedEvaluation =  innerMap.get("isNeedEvaluation").equals(true);						
					}
					//是否显示名字默认都是true
					this.isShowName = true;
					//是否显示日期
					this.isShowDate = true;
					if (innerMap.get("values") != null) {
						this.values = (Object[]) innerMap.get("values");
					} else {
						this.values = names;
					}
//					addLine(i);
//					if(i>0){						
//						addCompletedRateLine(i);
//					}
					
					//如果归并后与名称长度相等，就不需要考虑对齐问题
					if(this.names.length==itemNums){			
						//先画底线
						addLine(i);
						//再画完成率线
						if(i>0){						
							addCompletedRateLine(i);
						}
					}else{
						//不对齐
						//此处分别对比合并的数组，生成新的划线数数组
						initLineCounts(i);
						//先画底线
						addLine4diff(i);
						//再画完成率线
						if(i>0){						
							addCompletedRateLine4diff(i);
						}
					}
					//最后画点
					addItem(i);
				}
			}
		}
		//最佳展示长度
		preferredWidth = 300 + ((itemNums - 1) * lineLength);
		pnlScroll.setPreferredSize(new Dimension(preferredWidth, 0));
		//添加滚动条
		KDScrollPane sp = new KDScrollPane(pnlScroll);
		//这里是试验出来的位置，不大改，不要改这里
		sp.setBounds(0, 0, scrollWidth, 208);
		add(sp);
	}
	
	/**
	 * 对两个有序数组进行合并
	 * 用于返回的新数组，长度可能不为a,b数组之和，因为可能有重复的数字需要去掉；
	 * 对a、b两数组的值进行比较，并将小的值加到c，并将该数组下标+1；
	 * 如果相等，则将其任意一个加到c，两数组下标均+1；
	 * 如果下标超出该数组长度，则退出循环；
	 * 将没有超出数组下标的数组其余全部加到数组c中；
	 * 总是在最末级增加，剔除重复元素。
	 * 
	 * @param a
	 * @param b
	 * @return c 合并后的排序数组
	 */
	private static List<String> sort(String[] a, String[] b) {
		// 用于返回的新数组，长度可能不为a,b数组之和，因为可能有重复的数字需要去掉
		List<String> c = new ArrayList<String>();
		// a数组下标
		int aIndex = 0;
		// b数组下标
		int bIndex = 0;
		// 对a、b两数组的值进行比较，并将小的值加到c，并将该数组下标+1，
		// 如果相等，则将其任意一个加到c，两数组下标均+1
		// 如果下标超出该数组长度，则退出循环
		while (true) {
			if(a == null || b == null){
				break;
			}
			if (aIndex > a.length - 1 || bIndex > b.length - 1) {
				break;
			}
			if(a.length>=b.length){				
				if(a[aIndex] != null && b[bIndex] != null){				
					if (a[aIndex].compareTo(b[bIndex])<0) {
						c.add(a[aIndex]);
						aIndex++;
					} else if (a[aIndex].compareTo(b[bIndex])>0) {
						c.add(b[bIndex]);
						bIndex++;
					} else {
						c.add(a[aIndex]);
						aIndex++;
						bIndex++;
					}
				}
			}else{
				if(a[aIndex] != null && b[bIndex] != null){				
					if (b[bIndex].compareTo(a[aIndex])<0) {
						c.add(b[bIndex]);
						bIndex++;
					} else if (b[bIndex].compareTo(a[aIndex])>0) {
						c.add(a[aIndex]);
						aIndex++;
					} else {
						c.add(a[aIndex]);
						aIndex++;
						bIndex++;
					}
				}
			}
		}
		// 将没有超出数组下标的数组其余全部加到数组c中
		// 如果a数组还有数字没有处理
		if (aIndex <= a.length - 1) {
			for (int i = aIndex; i <= a.length - 1; i++) {
				//总是在最末级增加，剔除重复元素
				if(c.contains(a[i])){
					c.remove(a[i]);
				}
				c.add(a[i]);
			}
			// 如果b数组中还有数字没有处理
		} else if (bIndex <= b.length - 1) {
			for (int i = bIndex; i <= b.length - 1; i++) {
				//总是在最末级增加，剔除重复元素
				if(c.contains(b[i])){
					c.remove(b[i]);
				}
				c.add(b[i]);
			}
		}
		return c;
	}
	
	/**
	 * 此处分别对比合并的数组，生成新的划线数数组
	 * 这里考虑对齐问题，确定点和点之间的空余连线的个数
	 */
	protected void initLineCounts(int i) {
		//初始数组
		if(i>0){		
			//执行版
			this.ExecLineCounts = new int[this.names.length];
			int sameIndex=0;
			for(int j = 0 ; j < this.names.length;j++){				
				for(int unionIndex = sameIndex,count=1; unionIndex < this.unionNames.length ; unionIndex++,count++){				
					if(this.names[j].equals(this.unionNames[unionIndex])){
						sameIndex=unionIndex+1;						
						this.ExecLineCounts[j]=count;
						break;
					}
				}
			}
		}else{		
			//考核版
			this.CheckLineCounts = new int[this.names.length];
			int sameIndex=0;
			for(int j = 0 ; j < this.names.length;j++){				
				for(int unionIndex = sameIndex,count=1; unionIndex < this.unionNames.length ; unionIndex++,count++){				
					if(this.names[j].equals(this.unionNames[unionIndex])){
						sameIndex=unionIndex+1;
						this.CheckLineCounts[j]=count;
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 添加连接线面板，不考虑对齐，直接画所有直线
	 */
	protected void addLine() {
		int count = names.length;
		for(int i = 1; i < count; i++){
			pnlLine = new KDSubwayLinePanel(2, lineLength, lineHeight, lineColor, longNumbers[i-1], longNumbers[i], project, isNeedEvaluation,true);
			pnlLine.setBounds(clip + (i-1) * lineLength , top + 3 , pnlLine.getWidth(), pnlLine.getHeight());
			pnlScroll.add(pnlLine,-1);
		}
	}
	
	/**
	 * 添加连接线面板多版本，对齐不需要考虑点与点间连线数
	 */
	protected void addLine(int j) {
		int count = names.length;
		int tmpCount = 1;
		for (int UnionIndex = 1; UnionIndex < itemNums; UnionIndex++) {
			//不匹配画空圈 先不画
			if(UnionIndex >= tmpCount){
				
			}
			for(int tmpIndex = 1; tmpIndex < count; tmpIndex++){
				if(tmpNames != null && tmpNames[tmpIndex] != null && this.unionNames[UnionIndex] != null){	
					//相同显示具体信息，不同显示
					if(this.unionNames[UnionIndex].equals(tmpNames[tmpIndex])){
						//命中画按
						++tmpCount;
						if(j>0){				
							pnlLine = new KDSubwayLinePanel(2, lineLength, lineHeight, lineColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project, isNeedEvaluation,true);
						}else{
							pnlLine = new KDSubwayLinePanel(2, lineLength, lineHeight, lineColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project, isNeedEvaluation,false);
							
						}
						pnlLine.setBounds(clip  + 100 + (UnionIndex - 1) * lineLength , top + 3 + j * 95 , pnlLine.getWidth(), pnlLine.getHeight());
						pnlScroll.add(pnlLine,-1);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 添加连接线面板多版本,如果不对齐调用
	 */
	protected void addLine4diff(int j) {
		int count = names.length;
		int tmpCount = 1;
		for (int UnionIndex = 1; UnionIndex < itemNums; UnionIndex++) {
			//不匹配画空圈 先不画
			if(UnionIndex >= tmpCount){
				
			}
			for(int tmpIndex = 1; tmpIndex < count; tmpIndex++){
				if(tmpNames != null && tmpNames[tmpIndex] != null && this.unionNames[UnionIndex] != null){	
					//相同显示具体信息，不同显示
					if(this.unionNames[UnionIndex].equals(tmpNames[tmpIndex])){
						//命中画按
						++tmpCount;
						if(j>0){				
							//执行版
							if( ExecLineCounts[tmpIndex] > 1){
								pnlLine = new KDSubwayLinePanel(2, lineLength * ExecLineCounts[tmpIndex], lineHeight, lineColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project, isNeedEvaluation,true);
							}else{	
								if(ExecLineCounts[tmpIndex-1]>1){
									pnlLine = new KDSubwayLinePanel(2, lineLength * ExecLineCounts[tmpIndex], lineHeight, lineColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project, isNeedEvaluation,true);
								}else{									
									pnlLine = new KDSubwayLinePanel(2, lineLength * ExecLineCounts[tmpIndex-1], lineHeight, lineColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project, isNeedEvaluation,true);
								}
							}
							pnlLine.setBounds(clip  + 100 + (UnionIndex - ExecLineCounts[tmpIndex]) * lineLength  , top + 3 + j * 95 , pnlLine.getWidth(), pnlLine.getHeight());

						}else{
							//考核版
							if(CheckLineCounts[tmpIndex]>1){
								pnlLine = new KDSubwayLinePanel(2, lineLength * CheckLineCounts[tmpIndex] , lineHeight, lineColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project, isNeedEvaluation,false);

							}else{
								if(CheckLineCounts[tmpIndex-1]>1){
									pnlLine = new KDSubwayLinePanel(2, lineLength * CheckLineCounts[tmpIndex] , lineHeight, lineColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project, isNeedEvaluation,false);
								}else{									
									pnlLine = new KDSubwayLinePanel(2, lineLength * CheckLineCounts[tmpIndex-1] , lineHeight, lineColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project, isNeedEvaluation,false);
								}
							}
							pnlLine.setBounds(clip  + 100 + (UnionIndex - CheckLineCounts[tmpIndex]) * lineLength , top + 3 + j * 95 , pnlLine.getWidth(), pnlLine.getHeight());

						}
						pnlScroll.add(pnlLine,-1);
						break;
					}
				}
			}
		}
	}
	
	//初步思路，像增加点一样增加完成率
	/**
	 * 添加连接线完成率
	 */
	protected void addCompletedRateLine() {
		int count = names.length;		
		for(int i = 1; i < count; i++){
			pnlLine = new KDSubwayLinePanel(2, lineLength/100 * (taskComleteRates[i]) , 5, completedColor, longNumbers[i-1], longNumbers[i], project,isNeedEvaluation,true);
			pnlLine.setBounds(clip + (i-1) * lineLength , top + 3 , pnlLine.getWidth(), pnlLine.getHeight());
			pnlScroll.add(pnlLine,0);
		}
	}
	
	/**
	 * 添加连接线完成率，对齐不需要考虑点与点连线数目
	 */
	protected void addCompletedRateLine(int j) {
		int count = names.length;
		int tmpCount = 1;
		for (int UnionIndex = 1; UnionIndex < itemNums; UnionIndex++) {
			//不匹配画空圈 先不画空
			if(UnionIndex >= tmpCount){
				
			}
			for(int tmpIndex = 1; tmpIndex < count; tmpIndex++){
				if(tmpNames != null && tmpNames[tmpIndex] != null && this.unionNames[UnionIndex] != null){	
					//相同显示具体信息，不同显示
					if(this.unionNames[UnionIndex].equals(tmpNames[tmpIndex])){
						//命中画按钮
						++tmpCount;
						if(j>0){				
							pnlLine = new KDSubwayLinePanel(2, lineLength/100 * (taskComleteRates[tmpIndex]) , 5, completedColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project,isNeedEvaluation,true);
						}else{
							pnlLine = new KDSubwayLinePanel(2, lineLength/100 * (taskComleteRates[tmpIndex]) , 5, completedColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project,isNeedEvaluation,false);							
						}
						pnlLine.setBounds(clip + 100 + (UnionIndex-1) * lineLength , top + 3 + j * 95 , pnlLine.getWidth(), pnlLine.getHeight());
						pnlScroll.add(pnlLine,0);
						break;
					}
				}
			}
		}
	}

	/**
	 * 添加连接线完成率,如果不对齐调用
	 */
	protected void addCompletedRateLine4diff(int j) {
		int count = names.length;
		int tmpCount = 1;
		for (int UnionIndex = 1; UnionIndex < itemNums; UnionIndex++) {
			//不匹配画空圈 先不画空
			if(UnionIndex >= tmpCount){
				
			}
			for(int tmpIndex = 1; tmpIndex < count; tmpIndex++){
				if(tmpNames != null && tmpNames[tmpIndex] != null && this.unionNames[UnionIndex] != null){	
					//相同显示具体信息，不同显示
					if(this.unionNames[UnionIndex].equals(tmpNames[tmpIndex])){
						//命中画按钮
						++tmpCount;
						//下面比较难于理解，有难于说明，可以先屏蔽些调试看看~
						if(j>0){			
							//执行版
							if(ExecLineCounts[tmpIndex]>1){
								pnlLine = new KDSubwayLinePanel(2, lineLength/100  * ExecLineCounts[tmpIndex] * (taskComleteRates[tmpIndex]) , 5, completedColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project,isNeedEvaluation,true);

							}else{
								if(ExecLineCounts[tmpIndex-1]>1){
									pnlLine = new KDSubwayLinePanel(2, lineLength/100  * ExecLineCounts[tmpIndex] * (taskComleteRates[tmpIndex]) , 5, completedColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project,isNeedEvaluation,true);
								}else{									
									pnlLine = new KDSubwayLinePanel(2, lineLength/100  * ExecLineCounts[tmpIndex-1] * (taskComleteRates[tmpIndex]) , 5, completedColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project,isNeedEvaluation,true);
								}

							}
							pnlLine.setBounds(clip + 100 + (UnionIndex-ExecLineCounts[tmpIndex]) * lineLength  , top + 3 + j * 95 , pnlLine.getWidth(), pnlLine.getHeight());


						}else{
							//考核版
							if(CheckLineCounts[tmpIndex]>1){
								pnlLine = new KDSubwayLinePanel(2, lineLength/100  * CheckLineCounts[tmpIndex] * (taskComleteRates[tmpIndex]) , 5, completedColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project,isNeedEvaluation,false);	

							}else{	
								if(CheckLineCounts[tmpIndex-1]>1){
									pnlLine = new KDSubwayLinePanel(2, lineLength/100  * CheckLineCounts[tmpIndex] * (taskComleteRates[tmpIndex]) , 5, completedColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project,isNeedEvaluation,false);	
								}else{									
									pnlLine = new KDSubwayLinePanel(2, lineLength/100  * CheckLineCounts[tmpIndex-1] * (taskComleteRates[tmpIndex]) , 5, completedColor, longNumbers[tmpIndex-1], longNumbers[tmpIndex], project,isNeedEvaluation,false);	
								}
							}
							pnlLine.setBounds(clip + 100 + (UnionIndex-CheckLineCounts[tmpIndex]) * lineLength , top + 3 + j * 95 , pnlLine.getWidth(), pnlLine.getHeight());

						}
						pnlScroll.add(pnlLine,0);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 添加节点面板
	 * <p>
	 * 设置为透明，且在最上层，即可覆盖连接线
	 */	
	protected void addItem() {
		pnlItem = new JPanel();
		pnlItem.setLayout(null);
		pnlItem.setOpaque(false);
//		pnlItem.setBounds(0, 2 + stage * 95, 300 + ((itemNums - 1) * lineLength), 100);
		pnlItem.setBounds(0, 2 , 300 + ((itemNums - 1) * lineLength), 100);

		int count = names.length;
		items = new KDSubwayItemButton[itemNums];
		for(int i = 0; i < count; i++){
			Color circleColor = allStates[itemStates[i]].getStatusColor();
			//根据颜色画按钮
			RButton item = new RButton(circleColor);
			item.setName(names[i]);
			item.setOpaque(true);
			item.setPreferredSize(new Dimension(itemSize * 2, itemSize * 2));
			item.setBackground(circleColor);
			//
			item.setDetail(values[i]);
			item.setBounds(clip + i * lineLength - itemSize , top - itemSize + lineHeight / 2, itemSize * 2, itemSize * 2);
			items[i] = item;
			pnlItem.add(item,0);
//			if (circleColor.equals(TAN)){
//				RButton item = new RButton(TAN);
//				item.setName(names[i]);
//				item.setOpaque(true);
//				item.setPreferredSize(new Dimension(itemSize * 2, itemSize * 2));
//				item.setBackground(circleColor);
//				//
//				item.setDetail(values[i]);
//				item.setBounds(clip + i * lineLength - itemSize + 100, top - itemSize + lineHeight / 2, itemSize * 2, itemSize * 2);
//				items[i] = item;
//				pnlItem.add(item,0);
//				//如果未汇报
//			}else if(circleColor.equals(GREEN)){
//				RButton item = new RButton(GREEN);
//				item.setName(names[i]);
//				item.setOpaque(true);
//				item.setPreferredSize(new Dimension(itemSize * 2, itemSize * 2));
//				item.setDetail(values[i]);
//				item.setBounds(clip + i * lineLength - itemSize + 100, top - itemSize + lineHeight / 2, itemSize * 2, itemSize * 2);
//				item.setRenderer(null);
//				items[i] = item;
//				pnlItem.add(item,0);
//			}else{					
//				KDSubwayItemButton item = new KDSubwayItemButton(true);
//				item.setName(names[i]);
//				item.setOpaque(true);
//				item.setPreferredSize(new Dimension(itemSize * 6, itemSize * 2));
//				item.setBackground(circleColor);
//				item.setDetail(values[i]);
//				item.setBounds(clip + i * lineLength - itemSize * 3 + 100, top - itemSize + lineHeight / 2, itemSize * 6, itemSize * 2);
//				items[i] = item;
//				pnlItem.add(item,0);
//			}
			Map datesValue = (Map) dates[i];
			if (isShowDate) {
				if (datesValue != null){
					JLabel date = new JLabel((String)datesValue.get("date"), SwingConstants.CENTER);
					date.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
					date.setText((String)datesValue.get("date"));
					date.setBounds(clip + i * lineLength - lineLength / 2 + 5, top + lineHeight / 2 + itemSize + 5, lineLength - 10, 14);
					pnlItem.add(date,0);
				}
			}
			if (isShowName) {
				JTextArea name = new JTextArea();
				name.setAutoscrolls(true);
				name.setLineWrap(true);  
				name.setEditable(false);
				name.setText(names[i]);
				name.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
				name.setToolTipText(names[i]);
				name.setText(names[i]);
				name.setBackground(Color.LIGHT_GRAY);
				name.setBounds(clip + i * lineLength - lineLength / 2 + 5, top + lineHeight / 2 - itemSize - 45, lineLength - 10, 40);
				pnlItem.add(name,0);
			}
		}

		// 设置为最上层，这样就能覆盖住线条了，该死的线条
		pnlScroll.add(pnlItem, 0);
	}
	/**
	 * 添加节点面板
	 * <p>
	 * 设置为透明，且在最上层，即可覆盖连接线
	 * 点上名称，点下日期，已经地铁图前的说明版本的标志
	 */	
	protected void addItem(int stage) {
		pnlItem = new JPanel();
		pnlItem.setLayout(null);
		pnlItem.setOpaque(false);
//		pnlItem.setBounds(0, 5 + stage * 100, 300 + ((itemNums - 1) * lineLength), 100);
		pnlItem.setBounds(0, 2 + stage * 95, 300 + ((itemNums - 1) * lineLength), 100);
		//地铁图前的说明版本的标志
		JLabel lbl = new JLabel();
		Font font = new Font("微软雅黑", Font.BOLD, 16);
		Dimension lblSize = new Dimension(100, 80);
		lbl.setFont(font);
		lbl.setBackground(Color.PINK);
		lbl.setOpaque(true);
		lbl.setSize(lblSize);
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
		switch (stage) {
		case 0:
			lbl.setText("考核版");
			break;
		default:
			lbl.setText("执行版");
			break;			
		}
		lbl.setLocation(0, 0);
		pnlItem.add(lbl,0);
		int count = names.length;
		items = new KDSubwayItemButton[itemNums];
		int tmpCount = 0;
		for (int UnionIndex = 0; UnionIndex < itemNums; UnionIndex++) {
			//不匹配画空圈 先不画空圈
			if(UnionIndex >= tmpCount){
				
			}
			for(int tmpIndex = 0; tmpIndex < count; tmpIndex++){
				if(tmpNames != null && tmpNames[tmpIndex] != null && this.unionNames[UnionIndex] != null){	
					//相同显示具体信息，不同显示圆圈
					if(this.unionNames[UnionIndex].equals(tmpNames[tmpIndex])){
						//命中画按钮
						++tmpCount;
						if(stage==0){					
							RButton item = new RButton();
							item.setName(names[tmpIndex]);
							item.setOpaque(true);
							item.setPreferredSize(new Dimension(itemSize * 2, itemSize * 2));
//	item.setBackground(circleColor);
							item.setDetail(values[tmpIndex]);
							item.setBounds(clip + UnionIndex * lineLength - itemSize + 100, top - itemSize + lineHeight / 2, itemSize * 2, itemSize * 2);
							item.setRenderer(null);
							
							//modified by zhaoqin for R140821-0099 on 2014/12/26
							//items[tmpIndex] = item;
							items[UnionIndex] = item;
							
							pnlItem.add(item,0);
							Map datesValue = (Map) dates[tmpIndex];
							if (isShowDate) {
								if (datesValue != null){
									JLabel date = new JLabel((String)datesValue.get("date"), SwingConstants.CENTER);
									date.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
									date.setText((String)datesValue.get("date"));
									date.setBounds(clip + UnionIndex * lineLength - lineLength / 2 + 105, top + lineHeight / 2 + itemSize + 5, lineLength - 10, 14);
									pnlItem.add(date,0);
								}
							}
							if (isShowName) {
								JTextArea name = new JTextArea();
								name.setAutoscrolls(true);
								name.setLineWrap(true);  
								name.setEditable(false);
//		name.setOpaque(false);
								name.setText(names[tmpIndex]);
//		name.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200),2));
								name.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
								name.setToolTipText(names[tmpIndex]);
								name.setText(names[tmpIndex]);
								name.setBackground(Color.LIGHT_GRAY);
								// name.setText("2012-01-01");
								name.setBounds(clip + UnionIndex * lineLength - lineLength / 2 + 105, top + lineHeight / 2 - itemSize - 45, lineLength - 10, 40);
								pnlItem.add(name,0);
							}
							//stage==1
						}else{
							Color circleColor = allStates[itemStates[tmpIndex]].getStatusColor();
							//根据颜色画按钮
							RButton item = new RButton(circleColor);
							item.setName(names[tmpIndex]);
							item.setOpaque(true);
							item.setPreferredSize(new Dimension(itemSize * 2, itemSize * 2));
							item.setBackground(circleColor);
							//
							item.setDetail(values[tmpIndex]);
							item.setBounds(clip + UnionIndex * lineLength - itemSize + 100 , top - itemSize + lineHeight / 2 , itemSize * 2, itemSize * 2);
							if(tmpIndex>=itemNums){
								System.err.println("有可能出现里程碑重名导致数组越界");
							}
							
							//modified by zhaoqin for R140821-0099 on 2014/12/26
							//items[tmpIndex] = item;
							items[UnionIndex] = item;
							
							pnlItem.add(item,0);
							Map datesValue = (Map) dates[tmpIndex];
							if (isShowDate) {
								if (datesValue != null){
									JLabel date = new JLabel((String)datesValue.get("date"), SwingConstants.CENTER);
									date.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
									date.setText((String)datesValue.get("date"));
									date.setBounds(clip + UnionIndex * lineLength - lineLength / 2 + 105, top + lineHeight / 2 + itemSize + 5, lineLength - 10, 14);
									pnlItem.add(date,0);
								}
							}
							if (isShowName) {
								JTextArea name = new JTextArea();
								name.setAutoscrolls(true);
								name.setLineWrap(true);  
								name.setEditable(false);
								name.setText(names[tmpIndex]);
								name.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,2));
								name.setToolTipText(names[tmpIndex]);
								name.setText(names[tmpIndex]);
								name.setBackground(Color.LIGHT_GRAY);
								name.setBounds(clip + UnionIndex * lineLength - lineLength / 2 + 105, top + lineHeight / 2 - itemSize - 45, lineLength - 10, 40);
								pnlItem.add(name,0);
							}
							
						}	
						break;
					}
				}
			}
		}
		
		// 设置为最上层，这样就能覆盖住线条了，该死的线条
		pnlScroll.add(pnlItem, 0);
	}
	
	/**
	 * 添加说明面板
	 */
	public static  JPanel addDesc() {	
		KDSubwayItemState check = new KDSubwayItemState("计划完成日期", BLUE);
		KDSubwayItemState FINISH = new KDSubwayItemState("按时完成", GREEN);		
		KDSubwayItemState HALFPEND = new KDSubwayItemState("延时完成", ORANGE);
		KDSubwayItemState UNFINISH = new KDSubwayItemState("延时且未完成", RED);
		KDSubwayItemState undo = new KDSubwayItemState("未达到完成日期", TAN);
		KDSubwayItemState[] allStatesDesc = new KDSubwayItemState[] {check, FINISH, HALFPEND, UNFINISH, undo};
		pnlDesc = new JPanel();
		pnlDesc.setOpaque(false);
		for (int i = 0; i < allStatesDesc.length; i++) {
			Color circleColor = allStatesDesc[i].getStatusColor();
			//现在是给圆按钮上色
			RButton btn = new RButton(circleColor);
			btn.setOpaque(true);
			btn.setPreferredSize(new Dimension(itemSize * 2, itemSize * 2));
			btn.setBackground(circleColor);
			pnlDesc.add(btn);
//			}else{
//				KDSubwayItemButton btn = new KDSubwayItemButton(true);
//				btn.setOpaque(true);
//				btn.setPreferredSize(new Dimension(itemSize * 6, itemSize * 2));
//				btn.setBackground(circleColor);
//				pnlDesc.add(btn);
//			}
			JLabel label = new JLabel(allStatesDesc[i].getStatusName());
			pnlDesc.add(label);
		}
		return pnlDesc;
	}

}
