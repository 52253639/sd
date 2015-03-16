/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.bibench.platform.ui.util.MsgBox;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.market.DocumentFactory;
import com.kingdee.eas.fdc.market.DocumentInfo;
import com.kingdee.eas.fdc.market.DocumentItemCollection;
import com.kingdee.eas.fdc.market.DocumentItemFactory;
import com.kingdee.eas.fdc.market.DocumentItemInfo;
import com.kingdee.eas.fdc.market.DocumentOptionInfo;
import com.kingdee.eas.fdc.market.DocumentSubjectCollection;
import com.kingdee.eas.fdc.market.DocumentSubjectInfo;
import com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum;
import com.kingdee.eas.fdc.market.JumpConditionEnum;
import com.kingdee.eas.fdc.market.QuestionPaperDefineFactory;
import com.kingdee.eas.fdc.market.QuestionPaperDefineInfo;
import com.kingdee.eas.framework.CoreBaseCollection;

/**
 * output class name
 */
public class QuestPaperDocItemJumpSetUI extends AbstractQuestPaperDocItemJumpSetUI
{
    private static final Logger logger = CoreUIObject.getLogger(QuestPaperDocItemJumpSetUI.class);
    
    /**
     * output class constructor
     */
    public QuestPaperDocItemJumpSetUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    public void loadFields() {
    	super.loadFields();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.kDTable1.checkParsed();
    	this.kDTable1.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);  //
    	this.kDTable1.getStyleAttributes().setLocked(true);
    	this.kDTable1.getColumn("jumpCondt").setEditor(CommerceHelper.getKDComboBoxEditor(JumpConditionEnum.getEnumList()));
    	
    	String selectID = (String)this.getUIContext().get(UIContext.ID);
    	QuestionPaperDefineInfo questDefineInfo = QuestionPaperDefineFactory.getRemoteInstance()
    							.getQuestionPaperDefineInfo(" where id = '"+selectID+"'");
    	
    	DocumentInfo docInfo = DocumentFactory.getRemoteInstance()
					.getDocumentInfo("select subjects.items.*,subjects.items.chooseOption.*,subjects.items.jumpToItem.*,subjects.items.subjectId.subjectType  " +
							" where id = '" + questDefineInfo.getDocumentId() + "' order by subjects.subjectNumber,subjects.items.itemNumber");
    	DocumentSubjectCollection subjectColl = docInfo.getSubjects();
    	    	
    	DocumentItemCollection allItemColl = new DocumentItemCollection();
    	for (int i = 0; i < subjectColl.size(); i++) {
    		DocumentSubjectInfo subjectInfo = subjectColl.get(i);
    		DocumentItemCollection itemColl = subjectInfo.getItems();
    		CRMHelper.sortCollection(itemColl, "itemNumber",true);
    		allItemColl.addCollection(itemColl);
		}
    	fillTheTableByKeyObjects(this.kDTable1,allItemColl);    	
    	
    }
    
	public void fillTheTableByKeyObjects(KDTable table,DocumentItemCollection itemColl)	{
		if(itemColl.size()>0)  {
    		for (int j = 0; j < itemColl.size(); j++) {
    			DocumentItemInfo itemInfo = itemColl.get(j);
    			IRow row = table.addRow();
    			row.setUserObject(itemInfo);
    			row.getCell("id").setValue(itemInfo.getId());
    			row.getCell("subjectType").setValue(itemInfo.getSubjectId().getSubjectType() );
    			row.getCell("topic").setValue(itemInfo.getTopic() );  //+ "," + itemInfo.getItemNumber()
    			if(itemInfo.isIsToJump()){
        			row.getCell("jumpCondt").getStyleAttributes().setLocked(false);
        			row.getCell("chooseOption").getStyleAttributes().setLocked(false);
        			row.getCell("jumpToItem").getStyleAttributes().setLocked(false);
    			}
    			row.getCell("jumpCondt").setValue(itemInfo.getJumpCont());
    			if(itemInfo.getSubjectId().getSubjectType()==null || !itemInfo.getSubjectId().getSubjectType().equals(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE)){
    				row.getCell("subjectType").getStyleAttributes().setLocked(true);
    				continue;
    			}else{
    				row.getCell("isToJump").setValue(new Boolean(itemInfo.isIsToJump()));
    			}
    			
    			KDBizPromptBox f7ItemPrompt = new KDBizPromptBox();
    			f7ItemPrompt.setQueryInfo("com.kingdee.eas.fdc.market.app.F7DocumentItemQuery");
    			f7ItemPrompt.setEditable(true);
    			f7ItemPrompt.setDisplayFormat("$topic$");
    			f7ItemPrompt.setEditFormat("$topic$");
    			f7ItemPrompt.setCommitFormat("$topic$");
    			EntityViewInfo jumpViewInfo = new EntityViewInfo();
		    	FilterInfo jumpFilter = new FilterInfo();
		    	jumpFilter.getFilterItems().add(new FilterItemInfo("documentId.id",itemInfo.getSubjectId().getDocumentId().getId()));
		    	String idsBeforeStr = "'"+itemInfo.getId()+"'";
		    	for (int k = 0; k < j; k++) {
		    		idsBeforeStr += ",'"+itemColl.get(k).getId()+"'";
				}
		    	jumpFilter.getFilterItems().add(new FilterItemInfo("id",idsBeforeStr,CompareType.NOTINNER)); // 
		    	//jumpFilter.getFilterItems().add(new FilterItemInfo("itemNumber",new Integer(itemInfo.getItemNumber()),CompareType.GREATER)); //
		    	jumpViewInfo.setFilter(jumpFilter);
		    	SorterItemCollection sorter = new SorterItemCollection();
		    	sorter.add(new SorterItemInfo("subjectId.subjectNumber"));
		    	sorter.add(new SorterItemInfo("itemNumber"));
		    	jumpViewInfo.setSorter(sorter);
		    	f7ItemPrompt.setEntityViewInfo(jumpViewInfo);	    			
    			row.getCell("jumpToItem").setEditor(new KDTDefaultCellEditor(f7ItemPrompt));
    			row.getCell("jumpToItem").setRenderer(new ObjectValueRender(){
    				public String getText(Object obj) {
    					if(obj instanceof DocumentItemInfo ){
    						return ((DocumentItemInfo) obj).getTopic();
    					}
    					return super.getText(obj);
    				}
    			});
    	    	
    			KDBizPromptBox f7OptionPrompt = new KDBizPromptBox();
    			f7OptionPrompt.setQueryInfo("com.kingdee.eas.fdc.market.app.F7DocumentOptionQuery");
    			f7OptionPrompt.setEditable(true);
    			f7OptionPrompt.setDisplayFormat("$topic$");
    			f7OptionPrompt.setEditFormat("$topic$");
    			f7OptionPrompt.setCommitFormat("$topic$");
				FilterInfo optionFilter = new FilterInfo();
				optionFilter.getFilterItems().add(new FilterItemInfo("itemId.id",itemInfo.getId()));			
				EntityViewInfo optionView = new EntityViewInfo();
				optionView.setFilter(optionFilter);
				f7OptionPrompt.setEntityViewInfo(optionView);
    			row.getCell("chooseOption").setEditor(new KDTDefaultCellEditor(f7OptionPrompt));
    			row.getCell("chooseOption").setRenderer(new ObjectValueRender(){
    				public String getText(Object obj) {
    					if(obj instanceof DocumentOptionInfo)
    						return ((DocumentOptionInfo)obj).getTopic();
    				return super.getText(obj);
    			}});
    	    	
    			row.getCell("chooseOption").setValue(itemInfo.getChooseOption());
    			row.getCell("jumpToItem").setValue(itemInfo.getJumpToItem());
    			
			}			
		}		
	}
    
   
    protected void btnOK_actionPerformed(ActionEvent e) throws Exception {
    	int rowCount = this.kDTable1.getRowCount();;
    	CoreBaseCollection coreBaseColl = new CoreBaseCollection(); 
    	for (int i = 0; i < rowCount; i++) {
			IRow thisRow = this.kDTable1.getRow(i);
			DocumentItemInfo itemInfo = (DocumentItemInfo)thisRow.getUserObject();
			Boolean isToJump = (Boolean)thisRow.getCell("isToJump").getValue();
			if(isToJump==null) isToJump = new Boolean(false);
			itemInfo.setIsToJump(isToJump.booleanValue());
			JumpConditionEnum jumpCondt = (JumpConditionEnum)thisRow.getCell("jumpCondt").getValue();
			if(jumpCondt==null) jumpCondt = JumpConditionEnum.Null;
			itemInfo.setJumpCont(jumpCondt);
			DocumentOptionInfo chooseOption = (DocumentOptionInfo)thisRow.getCell("chooseOption").getValue();
			itemInfo.setChooseOption(chooseOption);
			DocumentItemInfo itInfo = (DocumentItemInfo)thisRow.getCell("jumpToItem").getValue();
			itemInfo.setJumpToItem(itInfo);			
			coreBaseColl.add(itemInfo);
			SelectorItemCollection selector = new SelectorItemCollection(); 
			selector.add(new SelectorItemInfo("id"));
			selector.add(new SelectorItemInfo("isToJump"));			
			selector.add(new SelectorItemInfo("jumpCont"));
			selector.add(new SelectorItemInfo("chooseOption"));
			selector.add(new SelectorItemInfo("jumpToItem"));
			
			String waringMessage = "";
			if(isToJump.booleanValue()){
				if(itInfo==null) 
					waringMessage = "第"+(i+1)+"行，'跳转至'必须选择！";
				else if(!jumpCondt.equals(JumpConditionEnum.Null)){
					if(chooseOption==null)
						waringMessage = "第"+(i+1)+"行，'选择答案'必须选择！";
				}				
			}
			if(!waringMessage.equals("")){
				FDCMsgBox.showWarning(waringMessage);
				this.abort();
			}
			
			//DocumentItemFactory.getRemoteInstance().updatePartial(itemInfo, selector);
		}
    	DocumentItemFactory.getRemoteInstance().update(coreBaseColl);
    	FDCMsgBox.showInfo("保存成功！");
    	this.getUIWindow().close();
    }
    
    protected void btnCancel_actionPerformed(ActionEvent e) throws Exception {
    	this.getUIWindow().close();
    }
    
    protected void kDTable1_tableClicked(KDTMouseEvent e) throws Exception {
    	int colIndex = e.getColIndex();
    	int rowIndex = e.getRowIndex();
    	
    	IRow thisRow = this.kDTable1.getRow(rowIndex);
    	if(this.kDTable1.getColumn("isToJump").getColumnIndex()==colIndex){
    		DocumentItemInfo itemInfo = (DocumentItemInfo)thisRow.getUserObject();
    		if(!itemInfo.getSubjectId().getSubjectType().equals(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE))
    			return;
    		
    		ICell thisCell = thisRow.getCell(colIndex);
    		
    		Boolean isToJump = (Boolean)thisCell.getValue();
    		if(isToJump==null) isToJump = new Boolean(false);
    		thisCell.setValue(new Boolean(!isToJump.booleanValue()));
    		
    		if(isToJump.booleanValue())	{
    			thisRow.getCell("jumpCondt").getStyleAttributes().setLocked(true);
    			thisRow.getCell("chooseOption").getStyleAttributes().setLocked(true);
    			thisRow.getCell("jumpToItem").getStyleAttributes().setLocked(true);
    			
    			thisRow.getCell("jumpCondt").setValue(null);
    			thisRow.getCell("chooseOption").setValue(null);
    			thisRow.getCell("jumpToItem").setValue(null);
    		}else{
    			thisRow.getCell("jumpCondt").getStyleAttributes().setLocked(false);
    			thisRow.getCell("chooseOption").getStyleAttributes().setLocked(false);
    			thisRow.getCell("jumpToItem").getStyleAttributes().setLocked(false);   		

    		}
    		
    	}
    	
    	
    }
}