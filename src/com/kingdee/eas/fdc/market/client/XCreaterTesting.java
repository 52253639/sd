package com.kingdee.eas.fdc.market.client;



/**
 * 从090317开始，此类作废
 */
public class XCreaterTesting {
//	
//	public static DocumentInfo doc(){
//		DocumentInfo doc = new DocumentInfo();
//		doc.setWidth(200);
//		doc.setHeight(240);
//		doc.setTopMarge(15);
//		doc.setBottomMarge(15);
//		doc.setRightMarge(20);
//		doc.setLeftMarge(0);
//		doc.setColumnCount(3);
//		doc.setHeader("金蝶软件-问卷调查");
//		doc.setFooter("www.kingdee.com");
//		
//		doc.getSubjects().add(one());
//		doc.getSubjects().add(two());
//		doc.getSubjects().add(three());
//		doc.getSubjects().add(four());
//		doc.getSubjects().add(five());
//		doc.getSubjects().add(six());
//		doc.getSubjects().add(seven());
//		doc.getSubjects().add(eight());
//		doc.getSubjects().add(nine());
//		doc.getSubjects().add(ten());
//		
//		return doc;
//	}
//	
//	
//	
//	public static DocumentSubjectInfo ten(){
//		
//
//		DocumentSubjectInfo dTen = new DocumentSubjectInfo();
//		dTen.setFont(new Font("Dialog",Font.PLAIN,16));
//		dTen.setTopic("特殊题目");
//		dTen.setColumnCount(2);
//		dTen.setSubjectNumber(10);
//		dTen.setShowNumber(8);
//		dTen.setIsShowNumber(1);
//		dTen.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_FLOW);//ALIGN_TYPE_FLOW
//		dTen.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE);
//		dTen.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
//		dTen.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellFDCCreater");
//		
//		DocumentItemInfo dItemAnimal = new DocumentItemInfo("动物：",new Font("Dialog",Font.BOLD,16));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("老虎",new Font("Dialog",Font.PLAIN,16)));
//		dTen.getItems().add(dItemAnimal);
//
//		
//		XDimension three = new XDimension(199,0,1,3);
//		three.setDSubject(dTen);
//		return dTen;
//	
//	
//	}
//	
//	
//	public static DocumentSubjectInfo nine(){
//
//		DocumentSubjectInfo dNine = new DocumentSubjectInfo();
//		dNine.setFont(new Font("Dialog",Font.PLAIN,16));
//		dNine.setTopic("您喜欢以下那些动植物？");
//		dNine.setColumnCount(2);
//		dNine.setSubjectNumber(9);
//		dNine.setShowNumber(8);
//		dNine.setIsShowNumber(1);
//		dNine.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_FLOW);//ALIGN_TYPE_FLOW
//		dNine.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE);
//		dNine.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
//		dNine.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
//		
//		DocumentItemInfo dItemAnimal = new DocumentItemInfo("动物：",new Font("Dialog",Font.BOLD,16));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("老虎",new Font("Dialog",Font.PLAIN,16)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("狮子",new Font("Dialog",Font.PLAIN,16)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("蚂蚁",new Font("Dialog",Font.PLAIN,16)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("大老虎",new Font("Dialog",Font.PLAIN,16)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("大狮子",new Font("Dialog",Font.PLAIN,16)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("大蚂蚁",new Font("Dialog",Font.PLAIN,16)));
//		dNine.getItems().add(dItemAnimal);
//
//		DocumentItemInfo dItemPlenty = new DocumentItemInfo("植物：",new Font("Dialog",Font.BOLD,50));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("松树",new Font("Dialog",Font.PLAIN,40)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("桂花",new Font("Dialog",Font.PLAIN,40)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("槐树",new Font("Dialog",Font.PLAIN,40)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("大松树",new Font("Dialog",Font.PLAIN,16)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("大桂花",new Font("Dialog",Font.PLAIN,20)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("大槐树",new Font("Dialog",Font.PLAIN,16)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("二松树",new Font("Dialog",Font.PLAIN,12)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("二桂花",new Font("Dialog",Font.PLAIN,12)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("二槐树",new Font("Dialog",Font.PLAIN,12)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("三松树",new Font("Dialog",Font.PLAIN,40)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("三桂花",new Font("Dialog",Font.PLAIN,40)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("三槐树",new Font("Dialog",Font.PLAIN,40)));
//		dNine.getItems().add(dItemPlenty);
//		
//		
//		XDimension three = new XDimension(199,0,1,3);
//		three.setDSubject(dNine);
//		return dNine;
//	
//	}
//
//	public static DocumentSubjectInfo eight(){
//		DocumentSubjectInfo dEight = new DocumentSubjectInfo();
//		dEight.setFont(new Font("Dialog",Font.PLAIN,16));
//		dEight.setTopic("您喜欢以下那些动植物？");
//		dEight.setColumnCount(1);
//		dEight.setXFontSize(14);
//		dEight.setXFontName("Dialog");
//		dEight.setSubjectNumber(8);
//		dEight.setShowNumber(7);
//		dEight.setIsShowNumber(1);
//		dEight.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);//ALIGN_TYPE_FLOW
//		dEight.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_MULTIPLE);
//		dEight.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
//		dEight.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
//		
//		DocumentItemInfo dItemAnimal = new DocumentItemInfo("动物：",new Font("Dialog",Font.BOLD,12));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("老虎",new Font("Dialog",Font.PLAIN,12)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("狮子",new Font("Dialog",Font.PLAIN,12)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("蚂蚁",new Font("Dialog",Font.PLAIN,12)));
//		dEight.getItems().add(dItemAnimal);
//
//		DocumentItemInfo dItemPlenty = new DocumentItemInfo("植物：",new Font("Dialog",Font.BOLD,12));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("松树",new Font("Dialog",Font.PLAIN,12)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("桂花",new Font("Dialog",Font.PLAIN,12)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("槐树",new Font("Dialog",Font.PLAIN,12)));
//		dEight.getItems().add(dItemPlenty);
//		
//		
//		XDimension three = new XDimension(199,0,1,3);
//		three.setDSubject(dEight);
//		return dEight;
//	}
//
//	public static DocumentSubjectInfo seven(){
//		DocumentSubjectInfo dSeven = new DocumentSubjectInfo();
//		dSeven.setFont(new Font("Dialog",Font.PLAIN,16));
//		dSeven.setTopic("您最喜欢的动植物是？");
//		dSeven.setColumnCount(1);
//		dSeven.setSubjectNumber(7);
//		dSeven.setShowNumber(6);
//		dSeven.setIsShowNumber(1);
//		dSeven.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);//ALIGN_TYPE_FLOW
//		dSeven.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE);
//		dSeven.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
//		dSeven.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
//		DocumentItemInfo dItemAnimal = new DocumentItemInfo("动物：",new Font("Dialog",Font.BOLD,12));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("老虎",new Font("Dialog",Font.PLAIN,12)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("狮子",new Font("Dialog",Font.PLAIN,12)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("蚂蚁",new Font("Dialog",Font.PLAIN,12)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("大象",new Font("Dialog",Font.PLAIN,12)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("狐狸",new Font("Dialog",Font.PLAIN,12)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("鲸鱼",new Font("Dialog",Font.PLAIN,12)));
//		dItemAnimal.getOptions().add(new DocumentOptionInfo("长颈鹿",new Font("Dialog",Font.PLAIN,12)));
//		dSeven.getItems().add(dItemAnimal);
//		
//
//		DocumentItemInfo dItemPlenty = new DocumentItemInfo("植物：",new Font("Dialog",Font.BOLD,12));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("松树",new Font("Dialog",Font.PLAIN,12)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("桂花",new Font("Dialog",Font.PLAIN,12)));
//		dItemPlenty.getOptions().add(new DocumentOptionInfo("槐树",new Font("Dialog",Font.PLAIN,12)));
//		dSeven.getItems().add(dItemPlenty);
//		
//		
//		
//		XDimension three = new XDimension(199,0,1,3);
//		three.setDSubject(dSeven);
//		return dSeven;
//	}
//
//	public static DocumentSubjectInfo six(){
//		DocumentSubjectInfo dSix = new DocumentSubjectInfo();
//		dSix.setFont(new Font("Dialog",Font.PLAIN,16));
//		dSix.setTopic("以下那个是你最喜欢的球星？");
//		dSix.setColumnCount(1);
//		dSix.setSubjectNumber(6);
//		dSix.setShowNumber(5);
//		dSix.setIsShowNumber(1);
//		dSix.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);//ALIGN_TYPE_FLOW
//		dSix.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE);
//		dSix.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
//		dSix.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
//		DocumentItemInfo dItem = new DocumentItemInfo();//"我的标题",new Font("Dialog",Font.PLAIN,14)
//		dSix.getItems().add(dItem);
//		dItem.getOptions().add(new DocumentOptionInfo("鲁尼",new Font("Dialog",Font.PLAIN,12)));
//		dItem.getOptions().add(new DocumentOptionInfo("贝克汉姆",new Font("Dialog",Font.PLAIN,12)));
//		dItem.getOptions().add(new DocumentOptionInfo("C罗",new Font("Dialog",Font.PLAIN,12)));
//		dItem.getOptions().add(new DocumentOptionInfo("亨利",new Font("Dialog",Font.PLAIN,12)));
//		XDimension three = new XDimension(199,0,1,3);
//		three.setDSubject(dSix);
//		return dSix;
//	}
//
//	public static DocumentSubjectInfo five(){
//		DocumentSubjectInfo dFive = new DocumentSubjectInfo();
//		dFive.setFont(new Font("Dialog",Font.PLAIN,16));
//		dFive.setTopic("以下电影你最喜欢那个？");
//		dFive.setColumnCount(1);
//		dFive.setSubjectNumber(5);
//		dFive.setShowNumber(4);
//		dFive.setIsShowNumber(1);
//		dFive.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);//ALIGN_TYPE_FLOW
//		dFive.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE);
//		dFive.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
//		dFive.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
//		DocumentItemInfo dItem = new DocumentItemInfo();//"我的标题",new Font("Dialog",Font.PLAIN,14)
//		dFive.getItems().add(dItem);
//		dItem.getOptions().add(new DocumentOptionInfo("功夫之王",new Font("Dialog",Font.PLAIN,12)));
//		dItem.getOptions().add(new DocumentOptionInfo("疯狂的赛车",new Font("Dialog",Font.PLAIN,12)));
//		//dItem.getOptions().add(new DOption("赤壁",new Font("Dialog",Font.PLAIN,12)));
//		dItem.getOptions().add(new DocumentOptionInfo("刺杀希特勒",new Font("Dialog",Font.PLAIN,12)));
//		XDimension three = new XDimension(199,0,1,3);
//		three.setDSubject(dFive);
//		return dFive;
//	}
//
//	public static DocumentSubjectInfo four(){
//		DocumentSubjectInfo dFour = new DocumentSubjectInfo();
//		dFour.setFont(new Font("Dialog",Font.PLAIN,16));
//		dFour.setTopic("以下电影你喜欢看那些？");
//		dFour.setColumnCount(1);
//		dFour.setSubjectNumber(4);
//		dFour.setShowNumber(3);
//		dFour.setIsShowNumber(1);
//		dFour.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);//ALIGN_TYPE_FLOW
//		dFour.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_MULTIPLE);
//		dFour.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
//		dFour.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
//		DocumentItemInfo dItem = new DocumentItemInfo();//"我的标题",new Font("Dialog",Font.PLAIN,14)
//		dFour.getItems().add(dItem);
//		dItem.getOptions().add(new DocumentOptionInfo("功夫之王",new Font("Dialog",Font.PLAIN,12)));
//		dItem.getOptions().add(new DocumentOptionInfo("疯狂的赛车",new Font("Dialog",Font.PLAIN,12)));
//		dItem.getOptions().add(new DocumentOptionInfo("赤壁",new Font("Dialog",Font.PLAIN,12)));
//		dItem.getOptions().add(new DocumentOptionInfo("刺杀希特勒",new Font("Dialog",Font.PLAIN,12)));
//		XDimension three = new XDimension(199,0,1,3);
//		three.setDSubject(dFour);
//		return dFour;
//	}
//
//	public static DocumentSubjectInfo three(){
//		DocumentSubjectInfo dThree = new DocumentSubjectInfo();
//		dThree.setFont(new Font("Dialog",Font.PLAIN,16));
//		dThree.setTopic("以下运动您喜欢那个？");
//		dThree.setXFontSize(16);
//		dThree.setColumnCount(1);
//		dThree.setSubjectNumber(3);
//		dThree.setShowNumber(2);
//		dThree.setIsShowNumber(1);
//		dThree.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);//ALIGN_TYPE_FLOW
//		dThree.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_MULTIPLE);
//		dThree.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
//		dThree.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
//		DocumentItemInfo dItem = new DocumentItemInfo();//"我的标题",new Font("Dialog",Font.PLAIN,14)
//		dItem.setTopic("球类");
//		dItem.setXFontSize(13);
//		dItem.setItemNumber(0);
//		dThree.getItems().add(dItem);
//		DocumentOptionInfo o1 = null;
//		//
//		o1 = new DocumentOptionInfo();
//		o1.setTopic("篮球");
//		o1.setXFontName("Dialog");
//		o1.setXFontSize(12);
//		o1.setDesc01("备注1");
//		o1.setXLength(0);
//		o1.setXHeight(0);
//		o1.setOptionNumber(0);
//		dItem.getOptions().add(o1);
//		//
//		o1 = new DocumentOptionInfo();
//		o1.setTopic("橄榄");
//		o1.setXFontName("Dialog");
//		o1.setXFontSize(12);
//		o1.setDesc01("备注2");
//		o1.setXLength(0);
//		o1.setXHeight(0);
//		o1.setOptionNumber(0);
//		dItem.getOptions().add(o1);
//		//
//		o1 = new DocumentOptionInfo();
//		o1.setTopic("游泳");
//		o1.setXFontName("Dialog");
//		o1.setXFontSize(12);
//		o1.setDesc01("备注3");
//		o1.setXLength(0);
//		o1.setXHeight(0);
//		o1.setOptionNumber(0);
//		dItem.getOptions().add(o1);
//		//
//		XDimension three = new XDimension(199,0,1,3);
//		three.setDSubject(dThree);
//		return dThree;
//	}
//
//	public static DocumentSubjectInfo two(){
//		DocumentSubjectInfo dTwo = new DocumentSubjectInfo();
//		dTwo.setColumnCount(1);
//		dTwo.setSubjectNumber(2);
//		dTwo.setShowNumber(1);
//		dTwo.setIsShowNumber(1);
//		dTwo.setFont(new Font("Dialog",Font.PLAIN,16));
//		dTwo.setTopic("您的年龄是？");
//		dTwo.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);
//		dTwo.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_FILL);
//		dTwo.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
//		dTwo.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
//		return dTwo;
//	}
//
//	public static DocumentSubjectInfo one(){
//		DocumentSubjectInfo dOne = new DocumentSubjectInfo();
//		dOne.setColumnCount(2);
//		dOne.setSubjectNumber(1);
//		dOne.setIsShowNumber(0);
//		dOne.setFont(new Font("Dialog",Font.PLAIN,40));
//		dOne.setTopic("关于会员的爱好的调查");
//		dOne.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_CENTER);
//		dOne.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);
//		dOne.setSubjectType(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION);
//		dOne.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
//		return dOne;
//	}
}
