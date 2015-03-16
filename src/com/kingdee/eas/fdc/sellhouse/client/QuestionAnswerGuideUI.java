/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerCollection;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerFactory;
import com.kingdee.eas.fdc.market.QuestionPaperAnswerInfo;
import com.kingdee.eas.fdc.market.QuestionPaperDefineInfo;
import com.kingdee.eas.fdc.sellhouse.CluesManageInfo;
import com.kingdee.eas.fdc.sellhouse.QuestPaperBizSceneEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;

/**
 * output class name
 */
public class QuestionAnswerGuideUI extends AbstractQuestionAnswerGuideUI
{
    private static final Logger logger = CoreUIObject.getLogger(QuestionAnswerGuideUI.class);
    
    /**
     * output class constructor
     */
    private boolean isExitNumberRule = false;
    
    public QuestionAnswerGuideUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	this.prmtQuestionDefine.addSelectorListener(
    		new SelectorListener(){public void willShow(SelectorEvent arg0) {
    			QuestPaperBizSceneEnum bizSceneEnum = (QuestPaperBizSceneEnum)QuestionAnswerGuideUI.this.combBizScene.getSelectedItem();
    			
    			EntityViewInfo viewInfo = new EntityViewInfo();
    			FilterInfo filter = new FilterInfo();
    			filter.getFilterItems().add(new FilterItemInfo("bizScene",bizSceneEnum.getValue()));
    			viewInfo.setFilter(filter);    			
    			QuestionAnswerGuideUI.this.prmtQuestionDefine.setEntityViewInfo(viewInfo);
    			QuestionAnswerGuideUI.this.prmtQuestionDefine.getQueryAgent().resetRuntimeEntityView();
    			QuestionAnswerGuideUI.this.prmtQuestionDefine.setRefresh(true);
    	}});
    	
    	this.txtNumber.setRequired(true);
    	this.prmtQuestionDefine.setRequired(true);
    	this.prmtSheCustomer.setRequired(true);
    	
		QuestionPaperAnswerInfo questPaperInfo = new QuestionPaperAnswerInfo();
		if(CommerceHelper.isExistNumberRule(questPaperInfo)){
			isExitNumberRule = true;
			this.txtNumber.setText(CommerceHelper.getNumberByRule(questPaperInfo));
			this.txtNumber.setEnabled(false);
		}
		
    }

    protected void btnNext_actionPerformed(ActionEvent e) throws Exception {
    	String txtNumber = this.txtNumber.getText().trim();
    	QuestionPaperDefineInfo questPaperInfo = (QuestionPaperDefineInfo)this.prmtQuestionDefine.getValue();
    	
    	if(txtNumber.equals("") && !isExitNumberRule){
    		FDCMsgBox.showWarning("单据编码必须录入！");
    		return;
    	}
    	if(questPaperInfo==null){
    		FDCMsgBox.showWarning("问卷定义必须录入！");
    		return;    		
    	}
    	
    	SHECustomerInfo sheCustInfo = null;
    	CluesManageInfo custInfo = null;
    	QuestPaperBizSceneEnum bizScenEnum = (QuestPaperBizSceneEnum)this.combBizScene.getSelectedItem();
    	if(bizScenEnum!=null && bizScenEnum.equals(QuestPaperBizSceneEnum.Clue)){
    		custInfo = (CluesManageInfo)this.prmtSheCustomer.getValue();
        	if(custInfo==null){
        		FDCMsgBox.showWarning("线索客户必须录入！");
        		return;    		
        	}    	    		
    	}else{
    		sheCustInfo = (SHECustomerInfo)this.prmtSheCustomer.getValue();
        	if(sheCustInfo==null){
        		FDCMsgBox.showWarning("问卷客户必须录入！");
        		return;    		
        	}    	
    	}
    	
		UIContext uiContext = new UIContext(this); 		
		uiContext.put("TxtNumber", txtNumber);
		uiContext.put("QuestionDefine", questPaperInfo);
		uiContext.put("SheCustomer", sheCustInfo);    
		uiContext.put("CluesManage", custInfo);
		uiContext.put("ParentListUI", this.getUIContext().get("ParentListUI"));

		this.getUIWindow().close();
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(QuestionAnswerIndexUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();   
 		    	
    }
    
    
    protected void btnCancle_actionPerformed(ActionEvent e) throws Exception {
    	this.getUIWindow().close();    	
    }

    
    protected void combBizScene_itemStateChanged(ItemEvent e) throws Exception {
    	QuestPaperBizSceneEnum bizScenEnum = (QuestPaperBizSceneEnum)e.getItem();
    	if(bizScenEnum!=null && bizScenEnum.equals(QuestPaperBizSceneEnum.Clue)){
    		this.prmtSheCustomer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CluesManageQuery");
    		this.contSheCustomer.setBoundLabelText("线索客户");
    	}else{
    		this.prmtSheCustomer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHECustomerQuery");
    		this.contSheCustomer.setBoundLabelText("问卷客户");
    	}
    	
    }
    
    protected void prmtSheCustomer_dataChanged(DataChangeEvent e)
    		throws Exception {
    	QuestPaperBizSceneEnum bizScenEnum = (QuestPaperBizSceneEnum)this.combBizScene.getSelectedItem();
    	
    	QuestionPaperAnswerCollection questAnswerColl = new QuestionPaperAnswerCollection();
    	if(bizScenEnum!=null && bizScenEnum.equals(QuestPaperBizSceneEnum.Clue)){
    		CluesManageInfo custInfo = (CluesManageInfo)e.getNewValue();
    		questAnswerColl = QuestionPaperAnswerFactory.getRemoteInstance()
    					.getQuestionPaperAnswerCollection("select id,number,questionPaper.topric,questionPaper.bizScene where sheCustomer.id = '"+custInfo.getId()+"'");
    	}else{
    		SHECustomerInfo sheCustInfo = (SHECustomerInfo)e.getNewValue();
    		questAnswerColl = QuestionPaperAnswerFactory.getRemoteInstance()
						.getQuestionPaperAnswerCollection("select id,number,questionPaper.topric,questionPaper.bizScene where cluesManage = '"+sheCustInfo.getId()+"'");    		
    	}
    		
    	for (int i = 0; i < questAnswerColl.size(); i++) {
    		QuestionPaperAnswerInfo questAnswerInfo = questAnswerColl.get(i);
    		IRow addRow = this.kDTable1.addRow();
    		addRow.getCell("id").setValue(questAnswerInfo.getId());
    		addRow.getCell("number").setValue(questAnswerInfo.getNumber());
    		addRow.getCell("topic").setValue(questAnswerInfo.getQuestionPaper().getTopric());
    		addRow.getCell("bizScene").setValue(questAnswerInfo.getQuestionPaper().getBizScene());
    		addRow.setUserObject(questAnswerInfo);
		}
    	
    	
    	
    }
    
    
    
}