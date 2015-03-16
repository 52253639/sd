/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JScrollPane;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.event.util.BOSUtils;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.DocumentInfo;
import com.kingdee.eas.fdc.market.DocumentItemCollection;
import com.kingdee.eas.fdc.market.DocumentItemFactory;
import com.kingdee.eas.fdc.market.DocumentItemInfo;
import com.kingdee.eas.fdc.market.DocumentOptionCollection;
import com.kingdee.eas.fdc.market.DocumentOptionHorizonLayoutEnum;
import com.kingdee.eas.fdc.market.DocumentOptionInfo;
import com.kingdee.eas.fdc.market.DocumentOptionLayoutEnum;
import com.kingdee.eas.fdc.market.DocumentSubjectCollection;
import com.kingdee.eas.fdc.market.DocumentSubjectInfo;
import com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum;
import com.kingdee.eas.fdc.market.JumpConditionEnum;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerEntryInfo;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo;
import com.kingdee.eas.fdc.market.QuestionPaperDefineInfo;
import com.kingdee.eas.fdc.market.client.XDocument;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class QuestionAnswerIndexUI extends AbstractQuestionAnswerIndexUI
{
    private static final Logger logger = CoreUIObject.getLogger(QuestionAnswerIndexUI.class);
    
	XDocument xDocument;
    private DocumentItemCollection docItemColl = new DocumentItemCollection(); 
    private int currItemIndex = 0;
    
    private LinkedList itemAnsweredList = new LinkedList();  //已答题的序号数值列表
    
    private QuestionPaperAnswerInfo questPaperInfo = new QuestionPaperAnswerInfo();

    
    public QuestionAnswerIndexUI() throws Exception
    {
        super();
    }


    public void onLoad() throws Exception {
    
    	super.onLoad();
    	
    	xDocument = new XDocument();
    	xDocument.setEnableRightMouseEvent(false);
    	JScrollPane xScrollPane = new JScrollPane(xDocument);
    	this.kDPanel1.add(xScrollPane, BorderLayout.CENTER);
    	
    	String txtNumber = (String)this.getUIContext().get("TxtNumber");
    	QuestionPaperDefineInfo paperDefineInfo = (QuestionPaperDefineInfo)this.getUIContext().get("QuestionDefine");
    	SHECustomerInfo sheCustInfo = (SHECustomerInfo)this.getUIContext().get("SheCustomer");
    	CluesManageInfo cluesManInfo = (CluesManageInfo)this.getUIContext().get("CluesManage");
    	if(this.getUIContext().get("SimulateAnser")!=null) {
    		this.btnSubmit.setEnabled(false);
    	}
    	
    	
    	questPaperInfo.setNumber(txtNumber);
    	questPaperInfo.setQuestionPaper(paperDefineInfo);
    	questPaperInfo.setSheCustomer(sheCustInfo);
    	questPaperInfo.setCluesManage(cluesManInfo==null?null:cluesManInfo.getId().toString());
    	
    	Date currDate = new Date(FDCCommonServerHelper.getServerTimeStamp().getTime());
    	questPaperInfo.setBizDate(currDate);
    	questPaperInfo.setInputDate(currDate);
    	
    	questPaperInfo.setOrgUnit((FullOrgUnitInfo)SysContext.getSysContext().getCurrentOrgUnit());
    	
    	//src 加必填项,subjectId.IsRequired
    	docItemColl = DocumentItemFactory.getRemoteInstance()
    					.getDocumentItemCollection("select *,options.*,subjectId.topic,subjectId,id,subjectId.subjectType,subjectId.IsRequired where subjectId.documentId = '"+paperDefineInfo.getDocumentId()+"' ");
    	if(docItemColl.size()>0){
    		addToTheEmptyPaper(docItemColl.get(0));    		
    		reflashButtons();
    	}else{
    		this.btnLast.setVisible(false);
    		this.btnNext.setVisible(false);
    		this.btnSubmit.setVisible(false);
    	}
    	
    }
    
    
    protected void btnLast_actionPerformed(ActionEvent e) throws Exception {
    	updateAnswerColl();
    	
    	//currItemIndex --;
    	Integer lastItemIndex = (Integer)itemAnsweredList.getLast();
    	currItemIndex = lastItemIndex.intValue();
    	itemAnsweredList.removeLast();
    	
    	reflashButtons();
    	addToTheEmptyPaper(docItemColl.get(currItemIndex));
    }
    
    protected void btnNext_actionPerformed(ActionEvent e) throws Exception {
    	updateAnswerColl();
    	
    	//src start
    	checkQuestionMust(xDocument);//点下一题时校验必填
    	//src end
    	
    	itemAnsweredList.add(new Integer(currItemIndex));
    	
    	if(isFitTheJumpCondition()) {
    		currItemIndex = toJumpItemIndexNum();
    	}else{
	     	currItemIndex ++;
    	}
     	reflashButtons();
     	addToTheEmptyPaper(docItemColl.get(currItemIndex));        	
    }
    
    private int toJumpItemIndexNum(){
    	DocumentItemInfo currItemInfo = docItemColl.get(currItemIndex);
    	for (int i = 0; i < docItemColl.size(); i++) {
    		DocumentItemInfo itemInfo =  docItemColl.get(i);
			if(itemInfo.getId().toString().equals(currItemInfo.getJumpToItem().getId().toString()))
				return i;
		}
    	
    	return currItemIndex+1;
    }
    
    
    //是否符合跳转条件
    private boolean isFitTheJumpCondition(){
    	DocumentItemInfo currItemInfo = docItemColl.get(currItemIndex);
    	if(!currItemInfo.isIsToJump()) return false;
    	if(currItemInfo.getJumpToItem()==null) return false; 
    	if(currItemInfo.getJumpCont().equals(JumpConditionEnum.Null)){
    		return true;
    	}else{
    		DocumentOptionInfo chooseOpt = currItemInfo.getChooseOption(); 
    		if(chooseOpt==null) return false;
    		QuestionPaperAnswerEntryCollection newColl = xDocument.getAnswerCollection();
    		QuestionPaperAnswerEntryInfo selectOpt = newColl.get(0);
    		if(selectOpt==null) return false;
    		
    		if(currItemInfo.getJumpCont().equals(JumpConditionEnum.Choose)){
    			if(selectOpt.getOption().equals(chooseOpt.getId().toString())) 
    				return true;
    		}else if(currItemInfo.getJumpCont().equals(JumpConditionEnum.UnChoose)){
    			if(!selectOpt.getOption().equals(chooseOpt.getId().toString())) 
    				return true;    			
    		}
    	}    	
    	return false;
    }
    
    
    protected void btnSubmit_actionPerformed(ActionEvent e) throws Exception {
    	updateAnswerColl();
    	
    	//src start
    	checkQuestionMust(xDocument);//点提交时校验必填
    	//src end
    	
    	QuestionPaperAnswerFactory.getRemoteInstance().submit(questPaperInfo);
    	FDCMsgBox.showInfo("提交成功，谢谢！");
    	
    	ListUI parentUI = (ListUI)this.getUIContext().get("ParentListUI");
    	if(parentUI!=null)	parentUI.refreshList();
    	
    	this.getUIWindow().close();
    	
    	/*if(questPaperInfo.getId()==null){
	    	IObjectPK ipk = QuestionPaperAnswerFactory.getRemoteInstance().addnew(questPaperInfo);
	    	if(questPaperInfo.getId()==null){
	    		questPaperInfo.setId(BOSUuid.read(ipk.toString()));
	    	}
    	}else{
    		QuestionPaperAnswerFactory.getRemoteInstance().update(new ObjectUuidPK(questPaperInfo.getId()),questPaperInfo);
    	}*/
    }    
    
    //校验必填题
    public void checkQuestionMust(XDocument xDoc){
		//src start 问卷必填题校验
		DocumentInfo documentInfo = xDoc.getDoc();
		if(documentInfo !=null){
			//所有问卷标题
			DocumentSubjectCollection dsCollection = documentInfo.getSubjects();
			for(int subIndex = 0;subIndex <dsCollection.size();subIndex++){
				DocumentSubjectInfo subj = dsCollection.get(subIndex);
				if(subj.getIsRequired() == 1){//必填标记
					//所有问卷分组
					DocumentItemCollection itemCollection = subj.getItems();
					for(int itemIndex=0 ; itemIndex<itemCollection.size() ; itemIndex++){
						DocumentItemInfo item = itemCollection.get(itemIndex);
						//所有问卷选项
						int answer = 0;
						DocumentOptionCollection optionCollection = item.getOptions();
						items:for(int optionIndex = 0 ; optionIndex<optionCollection.size() ; optionIndex++){
							DocumentOptionInfo option = optionCollection.get(optionIndex);
							//获取答案
							QuestionPaperAnswerEntryCollection  answerEntryCollection = (QuestionPaperAnswerEntryCollection)questPaperInfo.getEntrys();
							for(int j = 0;j<answerEntryCollection.size();j++){
								QuestionPaperAnswerEntryInfo info = (QuestionPaperAnswerEntryInfo)answerEntryCollection.get(j);
								//判断必填题是否做
								if(answerEntryCollection.size()>0&&option.getId().toString().equals(info.getOption())){
									answer++;
									//填充题判断
									if(subj.getSubjectType().equals(DocumentSubjectTypeEnum.SUBJECT_TYPE_FILL)){
										
									}else{//其他题跳出循环
										break items;//跳出循环
									}
								}
							}
						}
						if(optionCollection.size()>0&&subj.getSubjectType().equals(DocumentSubjectTypeEnum.SUBJECT_TYPE_FILL)){
							if(answer !=optionCollection.size()){
								MsgBox.showInfo("带*的题目为必填！");
								SysUtil.abort();
							}
						}else{
							if(optionCollection.size()>0&&answer == 0){
								MsgBox.showInfo("带*的题目为必填！");
								SysUtil.abort();
							}
						}

					}
				}
			}
		}
		//src end
    }
    
    private void reflashButtons(){
    	if(currItemIndex!=0)
    		this.btnLast.setVisible(true);
    	else
    		this.btnLast.setVisible(false);
    		
    	if(currItemIndex!=(docItemColl.size()-1)){
    		this.btnNext.setVisible(true);
    		this.btnSubmit.setVisible(false);
    	}else{
    		this.btnNext.setVisible(false);
    		this.btnSubmit.setVisible(true);
    	}
    }
    
    private void updateAnswerColl(){
    	QuestionPaperAnswerEntryCollection oldColl = questPaperInfo.getEntrys();
    	
    	QuestionPaperAnswerEntryCollection newColl = xDocument.getAnswerCollection();
    	for (int i = 0; i < newColl.size(); i++) {
    		boolean isExist = false;
    		QuestionPaperAnswerEntryInfo newInfo = newColl.get(i);
    		if(newInfo==null) return;
    		for (int j = 0; j < oldColl.size(); j++) {
    			QuestionPaperAnswerEntryInfo thisInfo = oldColl.get(j);
    			if(thisInfo.getOption().equals(newInfo.getOption())) { //存在则更新
    				thisInfo = newInfo;
    				isExist = true;
    			}
			}
    		if(!isExist){
    			oldColl.add(newInfo);
    		}
		}
    }
    
    
    
	private void addToTheEmptyPaper(DocumentItemInfo itemInfoOrign){	
		DocumentItemInfo itemInfo = (DocumentItemInfo)itemInfoOrign.clone();
		DocumentInfo doc = new DocumentInfo();
		doc.setWidth(120);
		doc.setHeight(100);
		doc.setTopMarge(2);
		doc.setBottomMarge(5);
		doc.setRightMarge(2);
		doc.setLeftMarge(2);
		doc.setColumnCount(1);
		doc.setHeader("");
		doc.setFooter("www.kingdee.com");
		
		DocumentSubjectInfo dThree = new DocumentSubjectInfo();
		dThree.setXFontName("Dialog");
		if(itemInfo.getSubjectId()!=null) {
			dThree.setTopic(itemInfo.getSubjectId().getTopic());
			dThree.setSubjectType(itemInfo.getSubjectId().getSubjectType());
		}
		dThree.setXFontSize(12);
		dThree.setColumnCount(1);
		dThree.setSubjectNumber(0);// 对于新增数据，这里必须设置数据小于1
		dThree.setShowNumber(currItemIndex+1);
		dThree.setIsShowNumber(1);
		//src start加必填项
		dThree.setIsRequired(itemInfo.getSubjectId().getIsRequired());
		//src end
		dThree.setAlignType(DocumentOptionLayoutEnum.ALIGN_TYPE_LINE);// ALIGN_TYPE_FLOW
		dThree.setHorizontalAlign(DocumentOptionHorizonLayoutEnum.HORIZONTAL_ALIGN_LEFT);
		dThree.setXCellCreter("com.kingdee.eas.fdc.market.client.XCellCommonCreater");
		DocumentItemInfo dItem = new DocumentItemInfo();
		dItem.setXFontSize(12);
		dItem.setXFontName("Dialog");
		dThree.getItems().add(dItem);
		dThree.copyNewToOld();// 保存一份,用来与新值进行比较
		
		itemInfo.setSubjectId(dThree);
		DocumentOptionCollection  optionColl = itemInfo.getOptions();
		for (int k = 0; k < optionColl.size(); k++) {
			DocumentOptionInfo optionInfo = optionColl.get(k);
			optionInfo.setItemId(itemInfo);
		}
		
		dThree.getItems().add(itemInfo);
		
		doc.getSubjects().add(dThree);
		//xDocument.insertDSubject(dThree);
		xDocument.setDoc(doc);
		
		xDocument.setAnswerCollection(questPaperInfo.getEntrys());
}
    
    
    
    
    
}