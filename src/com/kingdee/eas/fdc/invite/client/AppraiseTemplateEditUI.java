/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.cm.common.client.GeneralF7TreeListUI;
import com.kingdee.eas.cm.common.client.GeneralKDPromptSelectorAdaptor;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineInfo;
import com.kingdee.eas.fdc.invite.AppraiseGuideLineTypeInfo;
import com.kingdee.eas.fdc.invite.AppraiseTemplateEntryInfo;
import com.kingdee.eas.fdc.invite.AppraiseTemplateFactory;
import com.kingdee.eas.fdc.invite.AppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.AppraiseTempletTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AppraiseTemplateEditUI extends AbstractAppraiseTemplateEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AppraiseTemplateEditUI.class);
    
    private final String TYPE_COL = "guideLineType";
    private final String NAME_COL = "guideLineName";
    private final String WEIGHT_COL = "weight";
    private final String DESC_COL = "description";
    
    private KDBizPromptBox F7NameCol = new KDBizPromptBox();
    /**
     * output class constructor
     */
    public AppraiseTemplateEditUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
        if(!editData.isIsUserWidth()){
        	for(Iterator it = editData.getTemplateEntry().iterator();it.hasNext();){
        		  AppraiseTemplateEntryInfo info =  (AppraiseTemplateEntryInfo) it.next();
        		  info.setWeight(FDCHelper.toBigDecimal(1));
        	}
        }
    }
	
	protected IObjectValue createNewData() {
		AppraiseTemplateInfo tempInfo = new AppraiseTemplateInfo();
		
		AppraiseTempletTypeInfo type = (AppraiseTempletTypeInfo)(getUIContext().get("APPTEMPLATETYPE"));
		tempInfo.setTemplateType(type);
		
		tempInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		Date createDate = new Date();
		tempInfo.setCreateTime(new Timestamp(createDate.getTime()));
		
		tempInfo.setState(FDCBillStateEnum.SAVED);
		
		tempInfo.setIsUserWidth(true);
		
		return tempInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return AppraiseTemplateFactory.getRemoteInstance();
	}
	public void onLoad() throws Exception {
		
		kdtTemplateEntry.checkParsed();
		//addKDTableLisener();
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		
		super.onLoad();
		
		initHeadStyle();
		initTableStyle();
		//查看时，仍然能够删除评标指标类型的相关数据
		if(getOprtState()== "VIEW"){
			this.kdtTemplateEntry.setEnabled(false);
		
		}else{
			this.kdtTemplateEntry.setEnabled(true);
		}
		
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("guideLineType.*"));
	
		F7NameCol.setSelectorCollection(sic);
		
		F7NameCol.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent eventObj) {
				setTypeColValue(eventObj);
			}
			
		});
		
		F7NameCol.addSelectorListener(new SelectorListener()
		{

			public void willShow(SelectorEvent e) {
				try {
					setF7NameColFilter(e);
				} catch (Exception e1) {
					logger.error(e1);
				}
			}
			
		});
		
		if(OprtState.EDIT.equals(getOprtState()) || OprtState.VIEW.equals(getOprtState()))
		{
			setFootRowSum();
		}
		
		if(getOprtState().equals(OprtState.VIEW))
		{
			if(getUIContext().get("CAN_ADD") != null)
			{
				Boolean canAdd = (Boolean)(getUIContext().get("CAN_ADD") );
				if(canAdd.booleanValue())
				{
					if(editData.getState().equals(FDCBillStateEnum.SUBMITTED))
					{
//						this.actionAddNew.setEnabled(true);
						this.actionEdit.setEnabled(true);
						this.actionSubmit.setEnabled(false);
						this.actionRemove.setEnabled(true);
						
						this.actionAudit.setEnabled(true);
						this.actionAudit.setVisible(true);
						
						this.actionUnAudit.setEnabled(false);
						this.actionUnAudit.setVisible(true);
					}
					else if(editData.getState().equals(FDCBillStateEnum.AUDITTED))
					{
//						this.actionAddNew.setEnabled(true);
						this.actionEdit.setEnabled(false);
						this.actionSubmit.setEnabled(false);
						this.actionRemove.setEnabled(false);
						
						this.actionAudit.setEnabled(false);
						this.actionAudit.setVisible(true);
						
						this.actionUnAudit.setEnabled(true);
						this.actionUnAudit.setVisible(true);
					}
					else
					{
//						this.actionAddNew.setEnabled(true);
						this.actionEdit.setEnabled(true);
						this.actionSubmit.setEnabled(false);
						this.actionRemove.setEnabled(true);
						
						this.actionAudit.setEnabled(false);
						this.actionAudit.setVisible(false);
						
						this.actionUnAudit.setEnabled(false);
						this.actionUnAudit.setVisible(false);
					}
					
					this.actionAddLine.setEnabled(false);
					this.actionInsertLine.setEnabled(false);
					this.actionRemoveLine.setEnabled(false);
				}
				else
				{
//					this.actionAddNew.setEnabled(false);
					this.actionEdit.setEnabled(false);
					this.actionSubmit.setEnabled(false);
					this.actionRemove.setEnabled(false);
					
					this.actionAudit.setEnabled(false);
					this.actionAudit.setVisible(false);
					
					this.actionUnAudit.setEnabled(false);
					this.actionUnAudit.setVisible(false);
					
					if(editData.getState().equals(FDCBillStateEnum.SUBMITTED))
					{
						this.actionAudit.setVisible(true);
						this.actionUnAudit.setVisible(true);
					}
					
					this.actionAddLine.setEnabled(false);
					this.actionInsertLine.setEnabled(false);
					this.actionRemoveLine.setEnabled(false);
				}
			}
			else
			{
				this.actionAddLine.setEnabled(false);
				this.actionInsertLine.setEnabled(false);
				this.actionRemoveLine.setEnabled(false);
			}
			
		}
		else if(getOprtState().equals(OprtState.EDIT))
		{
			if(editData.getState().equals(FDCBillStateEnum.SUBMITTED))
			{
				this.actionSave.setEnabled(false);
			}
			
			this.actionAddLine.setEnabled(true);
			this.actionInsertLine.setEnabled(true);
			this.actionRemoveLine.setEnabled(true);
		}
		else if(getOprtState().equals(OprtState.ADDNEW))
		{
			this.actionAddLine.setEnabled(true);
			this.actionInsertLine.setEnabled(true);
			this.actionRemoveLine.setEnabled(true);
		}
		else
		{
			this.actionAddLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
		}
		
		remove(btnAttachment);
		this.btnAttachment.setVisible(false);
		this.contState.setVisible(false);
		this.combState.setVisible(false);
		Map uiContext = this.getUIContext();
		if(uiContext.containsKey("isSelectTemplate")&&uiContext.get("isSelectTemplate") instanceof Boolean&&((Boolean)uiContext.get("isSelectTemplate")).booleanValue()){
			this.actionAddNew.setVisible(false);
			this.actionEdit.setVisible(false);
			this.actionSave.setVisible(false);
			this.actionSubmit.setVisible(false);
			this.actionAudit.setVisible(false);
			this.actionUnAudit.setVisible(false);
			this.actionRemove.setVisible(false);
			this.actionInsertEntry.setVisible(false);
			this.actionAddEntry.setVisible(false);
			this.actionRemoveEntry.setVisible(false);
			this.actionInsertLine.setVisible(false);
			this.actionAddLine.setVisible(false);
			this.actionRemoveLine.setVisible(false);
		}
		
		this.txtDescription.setMaxLength(200);
		
		FDCTableHelper.disableDelete(this.kdtTemplateEntry);
		
	    isUseWidth.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				KDCheckBox c = (KDCheckBox) e.getSource();
				kdtTemplateEntry.getColumn("weight").getStyleAttributes().setHided(!c.isSelected());
//				kdtTemplateEntry.setFootManager(null);
//				kdtTemplateEntry.addFootRow(-1,kdtTemplateEntry.getFootRow(0));
				kdtTemplateEntry.getFootRow(0).getStyleAttributes().setHided(!c.isSelected());
//				kdtTemplateEntry.getFootRow(0).getStyleAttributes().setLocked(true);
				
			}});
		
	    kdtTemplateEntry.getColumn("weight").getStyleAttributes().setHided(!editData.isIsUserWidth());
		kdtTemplateEntry.getFootManager().getFootRow(0).getStyleAttributes().setHided(true);
		
		
		actionPrintPreview.setVisible(true);
		actionPrint.setVisible(true);
		actionPrintPreview.setEnabled(true);
		actionPrint.setEnabled(true);
	}
	
	private void setF7NameColFilter(SelectorEvent e) throws Exception
	{
		EntityViewInfo view = new EntityViewInfo();
		
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("guideLineType.id"));
		view.getSelector().add(new SelectorItemInfo("guideLineType.number"));
		
		view.getSelector().add(new SelectorItemInfo("guideLineType.name"));
		view.getSelector().add(new SelectorItemInfo("number"));
		view.getSelector().add(new SelectorItemInfo("name"));
		
		view.getSelector().add(new SelectorItemInfo("isEnable"));
		view.getSelector().add(new SelectorItemInfo("description"));
		
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnable", Boolean.valueOf(true)));
		view.setFilter(filter);
		F7NameCol.setEntityViewInfo(view);
		
		if (F7NameCol.getSelector() != null && F7NameCol.getSelector() instanceof com.kingdee.eas.framework.client.ListUI) {
			((com.kingdee.eas.framework.client.ListUI)F7NameCol.getSelector()).setFilterForQuery(filter);
			((com.kingdee.eas.framework.client.ListUI)F7NameCol.getSelector()).onLoad();

		}else {
			F7NameCol.getEntityViewInfo().setFilter(filter);
			F7NameCol.getQueryAgent().resetRuntimeEntityView();
			F7NameCol.setRefresh(true);
		}
	}
	public SelectorItemCollection getSelectors() {

		SelectorItemCollection sic = new SelectorItemCollection();
		
        sic.add(new SelectorItemInfo("*"));
        sic.add(new SelectorItemInfo("templateType.*"));
        sic.add(new SelectorItemInfo("templateEntry.id"));
        
        sic.add(new SelectorItemInfo("templateEntry.weight"));
        sic.add(new SelectorItemInfo("templateEntry.description"));
        
        
        sic.add(new SelectorItemInfo("templateEntry.guideLineType.*"));
        sic.add(new SelectorItemInfo("templateEntry.guideLine.*"));
        
        sic.add(new SelectorItemInfo("templateEntry.guideLine.guideLineType.*"));
        
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("lastUpdateUser.*"));

        return sic;
	}
	protected void initHeadStyle()
	{

		this.actionCopy.setEnabled(false);
		this.actionCopy.setVisible(false);
		this.actionPrintPreview.setEnabled(false);
		this.actionPrintPreview.setVisible(false);
		
		this.actionPrint.setEnabled(false);
		this.actionPrint.setVisible(false);
		this.actionPre.setEnabled(false);
		this.actionPre.setVisible(false);
		
		this.actionFirst.setEnabled(false);
		this.actionFirst.setVisible(false);
		this.actionNext.setEnabled(false);
		this.actionNext.setVisible(false);
		
		this.actionLast.setVisible(false);
		this.actionLast.setEnabled(false);
		
		this.actionCancel.setEnabled(false);
		this.actionCancel.setVisible(false);
		
		this.actionCancelCancel.setEnabled(false);
		this.actionCancelCancel.setVisible(false);
		
		this.contTemplateType.setEnabled(false);
		this.prmtTemplateType.setEnabled(false);
		
		this.contCreator.setEnabled(false);
		this.prmtCreator.setEnabled(false);
		this.contCreateTime.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		
		this.contAuditor.setEnabled(false);
		this.prmtAuditor.setEnabled(false);
		this.contAuditDate.setEnabled(false);
		this.pkAuditDate.setEnabled(false);
		
		this.actionAttachment.setEnabled(false);
		this.actionAttachment.setVisible(false);

		this.contEntry.setEnableActive(false);
		this.contState.setEnabled(false);
		this.combState.setEnabled(false);
		
//		remove(btnAddEntry);
//		remove(btnInsertEntry);
//		remove(btnRemoveEntry);
		remove(btnAddLine);
		remove(btnInsertLine);
		remove(btnRemoveLine);
		
		
		this.actionAddEntry.setVisible(false);
		this.actionAddEntry.setEnabled(false);
		
		this.actionInsertEntry.setVisible(false);
		this.actionInsertEntry.setEnabled(false);
		
		this.actionRemoveEntry.setEnabled(false);
		this.actionRemoveEntry.setVisible(false);
		
		remove(btnAddLine);
		remove(btnInsertLine);
		remove(btnRemoveLine);
		
		this.contEntry.addButton(btnAddLine);
		this.contEntry.addButton(btnInsertLine);
		this.contEntry.addButton(btnRemoveLine);
		
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		
		//this.btnSave.setIcon(EASResource.getIcon("imgTbtn_save"));
		//this.btnSubmit.setIcon(EASResource.getIcon("imgTbtn_s"));
		
		this.actionTraceDown.setEnabled(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setEnabled(false);
		this.actionTraceUp.setVisible(false);
		
		this.actionWorkFlowG.setVisible(false);
		this.actionWorkFlowG.setEnabled(false);
		
		this.actionAuditResult.setVisible(false);
		this.actionAuditResult.setEnabled(false);
		
		this.actionNextPerson.setEnabled(false);
		this.actionNextPerson.setVisible(false);
		this.actionCalculator.setEnabled(false);
		this.actionCalculator.setVisible(false);
		
		this.actionMultiapprove.setEnabled(false);
		this.actionMultiapprove.setVisible(false);
		this.actionCreateFrom.setEnabled(false);
		this.actionCreateFrom.setVisible(false);
		
		this.actionSubmit.setEnabled(true);
		this.actionSubmit.setVisible(true);
		
		this.prmtCreator.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
		this.prmtAuditor.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");
		this.prmtTemplateType.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7AppraiseTemplateTypeQuery");
	
		this.contAuditor.setEnabled(false);
		this.prmtAuditor.setEnabled(false);
		this.contCreator.setEnabled(false);
		this.prmtCreator.setEnabled(false);
		
		this.contCreateTime.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		this.contAuditDate.setEnabled(false);
		this.pkAuditDate.setEnabled(false);
		
		
		
	}

	protected void initTableStyle() throws Exception
	{
		this.kdtTemplateEntry.checkParsed();
		
		//设置评价指标类型F7
		IColumn typeCol = this.kdtTemplateEntry.getColumn(TYPE_COL);
		
		typeCol.getStyleAttributes().setLocked(true);
		typeCol.getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.LEFT);
		KDBizPromptBox F7TypeCol = new KDBizPromptBox();
			
		
		F7TypeCol.setDisplayFormat("$name$");
		F7TypeCol.setEditFormat("$name$");
		F7TypeCol.setCommitFormat("$name$");
		
		
	
		try {
		    GeneralKDPromptSelectorAdaptor selectorLisenter = null;
		    selectorLisenter = new GeneralKDPromptSelectorAdaptor(
		    		F7NameCol, 
		    		new F7AppraiseGuideLineSelectUI(), 
		    		this, 
		    		null,
		            "com.kingdee.eas.fdc.invite.app.AppraiseGuideLineQuery",
					"guideLineType.id");
		   //assignSelector(custF7, selectorLisenter);
		   
		    F7NameCol.setSelector(selectorLisenter);
		    F7NameCol.addSelectorListener(selectorLisenter);
		    F7NameCol.setDisplayFormat("$name$");
		    F7NameCol.setEditFormat("$name$");
		    F7NameCol.setCommitFormat("$name$");
		    
		   
		selectorLisenter .setIsMultiSelect(false);
		} catch (Exception e) {
		    super.handUIException(e);
		}
		

	    KDTDefaultCellEditor F7EditorType = new KDTDefaultCellEditor(
			 F7TypeCol);
		KDTDefaultCellEditor F7NameEditorType = new KDTDefaultCellEditor(
				F7NameCol);
		
		
		typeCol.setEditor(F7EditorType);
		
		typeCol.getStyleAttributes().setBackground(FDCTableHelper.lockColor);
		
		//设置评价指标名称F7
		IColumn nameCol = this.kdtTemplateEntry.getColumn(NAME_COL);
		nameCol.setRequired(true);
		nameCol.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);

		
//	    以前的实现代码	
//		F7NameCol.setDisplayFormat("$name$");
//		F7NameCol.setEditFormat("$name$");
//		F7NameCol.setCommitFormat("$name$");
//		
//		F7NameCol.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7AppraiseGuideLineQuery");
		
//		KDTDefaultCellEditor F7EditorName = new KDTDefaultCellEditor(F7NameCol);
		nameCol.setEditor(F7NameEditorType);
		
		//设置权重列为正数且两位有效数字
		KDFormattedTextField weightField = new KDFormattedTextField(KDFormattedTextField.BIGDECIMAL_TYPE);
     	weightField.setPrecision(2);
		weightField.setNegatived(false);
		weightField.setSupportedEmpty(false);
		
		KDTDefaultCellEditor editorWeight = new KDTDefaultCellEditor(weightField);
		this.kdtTemplateEntry.getColumn(WEIGHT_COL).setEditor(editorWeight);
		
		this.kdtTemplateEntry.getColumn(WEIGHT_COL).setRequired(true);
		
		this.kdtTemplateEntry.getColumn(WEIGHT_COL).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtTemplateEntry.getColumn(WEIGHT_COL).getStyleAttributes().setNumberFormat("#,###.00");
		
		
		
		
		
		//设置说明列字符长度
		KDTextField txtDesc = new KDTextField();
		txtDesc.setMaxLength(100);
		
		KDTDefaultCellEditor editorDesc = new KDTDefaultCellEditor(txtDesc);
		this.kdtTemplateEntry.getColumn(DESC_COL).setEditor(editorDesc);
	
		//设置合计行
		KDTFootManager footRowManager= kdtTemplateEntry.getFootManager();
		IRow footRow = null;
		if(footRowManager==null){

			footRowManager = new KDTFootManager(kdtTemplateEntry);
			footRowManager.addFootView();
			kdtTemplateEntry.setFootManager(footRowManager);
			
			footRow= footRowManager.addFootRow(0);
			footRow.setUserObject("FDC_PARAM_TOTALCOST");
			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			
			kdtTemplateEntry.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
			kdtTemplateEntry.getIndexColumn().setWidth(60);
			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
			
			//设置到第一个可视行
			footRowManager.addIndexText(0, "合计");
		}else{
			footRow=kdtTemplateEntry.getFootRow(0);
			if(footRow.getUserObject()==null||!footRow.getUserObject().equals("FDC_PARAM_TOTALCOST")){
				footRow=kdtTemplateEntry.addFootRow(1);
			};
		}
	}
	

	protected KDTable getDetailTable()
	{
		return this.kdtTemplateEntry;
	}
	/**
	 * 获取与表格绑定的对象信息
	 * 
	 * @param table
	 * @return
	 */
	protected IObjectValue createNewDetailData(KDTable table) {
		AppraiseTemplateEntryInfo info = new AppraiseTemplateEntryInfo();
		info.setWeight(new BigDecimal("1"));
		return info;
	}
	
    protected void setTypeColValue(DataChangeEvent e)
    {
    	int index = this.kdtTemplateEntry.getSelectManager().getActiveRowIndex();
    	IRow row = this.kdtTemplateEntry.getRow(index);
    	
    	if(e.getNewValue() != null)
    	{
    		AppraiseGuideLineInfo guideLineInfo = (AppraiseGuideLineInfo)(e.getNewValue());
    		
    		for(int i = 0; i < kdtTemplateEntry.getRowCount(); ++i)
    		{
    			if(i != index)
    			{
    				IRow tmpRow = kdtTemplateEntry.getRow(i);
    				if(tmpRow.getCell(NAME_COL).getValue() != null)
    				{
    					if(tmpRow.getCell(NAME_COL).getValue() instanceof AppraiseGuideLineInfo)
    					{
    						AppraiseGuideLineInfo tmpLineInfo = (AppraiseGuideLineInfo)(tmpRow.getCell(NAME_COL).getValue());
    						if(guideLineInfo.getId().equals(tmpLineInfo.getId()))
    						{
    							Integer indexInt = new Integer(index+1);
    							Integer iInt = new Integer(i+1);
    							StringBuffer buffer = new StringBuffer("第" + indexInt.toString());
    							buffer.append("行和");
    							buffer.append("第");
    							
    							buffer.append(iInt.toString() +"行不能为同一个评标指标。");
    							FDCMsgBox.showWarning(this,buffer.toString());
    							
    							kdtTemplateEntry.removeRow(index);
    							
    							abort();
    						}
    					}
    				}
    			}
    		}
    		AppraiseGuideLineTypeInfo guideLineTypeInfo = guideLineInfo.getGuideLineType();
    		row.getCell(TYPE_COL).setValue(guideLineTypeInfo);
    		
    	}
    	else
    	{
    		row.getCell(TYPE_COL).setValue(null);
    	}
    
    }
   
    protected void initDataStatus() {
    	super.initDataStatus();
    }
   
    protected void verifyInput(ActionEvent e) throws Exception {
    	super.verifyInput(e);
    	
    	
    }
    
    /**
	 * 将编辑界面上的表格编辑事件封装成一个适配器
	 */
	protected EntryEditAdapter entryEditLs = new EntryEditAdapter();
	
	/**
	 * 内部类，实现的通用的表格编辑适配器
	 * 
	 * @author xiaobin_li
	 * 
	 */
	protected class EntryEditAdapter extends KDTEditAdapter {
		public void editStopped(KDTEditEvent evt) {
			try {
				kdtTemplateEntry_editStopped(evt);
			} catch (Exception e) {
				e.printStackTrace();
				AppraiseTemplateEditUI.this.handleException(e);
			}
		}
	}
  

    protected void kdtTemplateEntry_editStopped(KDTEditEvent e)   		throws Exception {

    	
    	int colIndex = e.getColIndex();
    	int rowIndex = e.getRowIndex();
    
    	String colName  = kdtTemplateEntry.getColumnKey(colIndex);
    	if(kdtTemplateEntry.getRow(rowIndex).getCell(WEIGHT_COL).getValue() == null){
    		kdtTemplateEntry.getRow(rowIndex).getCell(WEIGHT_COL).setValue("0.0");
    	}
    	
    	if(colName.equals(WEIGHT_COL))
    	{
    		   		
    		if(kdtTemplateEntry.getRow(rowIndex).getCell(WEIGHT_COL).getValue() != null)
    		{
    			BigDecimal tmp = new BigDecimal(kdtTemplateEntry.getRow(rowIndex).getCell(WEIGHT_COL).getValue().toString());
    			
    			if(tmp.compareTo(FDCHelper.ZERO) <= 0)
    			{
    				FDCMsgBox.showWarning(this,"权重不能小于0。");
    				kdtTemplateEntry.getRow(rowIndex).getCell(WEIGHT_COL).setValue(BigDecimal.ONE);
    				
    				abort();
    			}
    			if(tmp.compareTo(new BigDecimal("100") ) > 0)
    			{
    				FDCMsgBox.showWarning(this,"权重不能大于100。");
    				kdtTemplateEntry.getRow(rowIndex).getCell(WEIGHT_COL).setValue(BigDecimal.ONE);
    				
    				abort();
    			}
    			
    			setFootRowSum();
    		}
    	}
    }

	
    private void addKDTableLisener() {
        kdtTemplateEntry.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
               if(e.getKeyCode()==KeyEvent.VK_DELETE){
            	   System.out.println("DEL被按下了！");
               }
            }
        });
    }
    
    
  
    
    
    
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	
	
	
	
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionSubmit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		
		this.actionAudit.setEnabled(false);
		this.actionAudit.setVisible(true);
		
		this.actionUnAudit.setEnabled(true);
		this.actionUnAudit.setVisible(true);
		
		
		this.actionAddLine.setEnabled(false);
		this.actionInsertLine.setEnabled(false);
		this.actionRemoveLine.setEnabled(false);
		
		remove(btnAttachment);
		this.btnAttachment.setVisible(false);
	}
	
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setEnabled(false);
		this.actionSubmit.setEnabled(false);
		this.actionRemove.setEnabled(false);
		
		this.actionAudit.setEnabled(true);
		this.actionAudit.setVisible(true);
		
		this.actionUnAudit.setEnabled(false);
		this.actionUnAudit.setVisible(true);
		
		this.actionAddLine.setEnabled(false);
		this.actionInsertLine.setEnabled(false);
		this.actionRemoveLine.setEnabled(false);
		
		remove(btnAttachment);
		this.btnAttachment.setVisible(false);
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);

		this.actionAudit.setEnabled(false);
		this.actionAudit.setVisible(false);
		
		this.actionUnAudit.setEnabled(false);
		this.actionUnAudit.setVisible(false);
		
		this.actionEdit.setEnabled(false);
		
		this.actionAddLine.setEnabled(true);
		this.actionInsertLine.setEnabled(true);
		this.actionRemoveLine.setEnabled(true);
		
		remove(btnAttachment);
		this.btnAttachment.setVisible(false);
		
		setFootRowSum();
		
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		
		this.actionAddLine.setEnabled(true);
		this.actionInsertLine.setEnabled(true);
		this.actionRemoveLine.setEnabled(true);
		
		remove(btnAttachment);
		this.btnAttachment.setVisible(false);
		
		this.contCreator.setEnabled(false);
		this.prmtCreator.setEnabled(false);
		this.contCreateTime.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		
		this.contAuditor.setEnabled(false);
		this.prmtAuditor.setEnabled(false);
		this.contAuditDate.setEnabled(false);
		this.pkAuditDate.setEnabled(false);
		
		this.contState.setEnabled(false);
		this.combState.setEnabled(false);
		this.contTemplateType.setEnabled(false);
		this.prmtTemplateType.setEnabled(false);
		this.kdtTemplateEntry.setEnabled(true);
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		remove(btnAttachment);
		this.btnAttachment.setVisible(false);
		
		super.actionSave_actionPerformed(e);
		
		remove(btnAttachment);
		this.btnAttachment.setVisible(false);
		
		for(int i = 0; i < this.kdtTemplateEntry.getRowCount(); ++i)
		{
			IRow row = kdtTemplateEntry.getRow(i);
			
			if(row.getCell(this.NAME_COL).getValue() != null)
			{
				if(row.getCell(this.NAME_COL).getValue() instanceof AppraiseGuideLineInfo)
				{
					AppraiseGuideLineInfo lineInfo = (AppraiseGuideLineInfo)(row.getCell(this.NAME_COL).getValue());

					row.getCell(this.TYPE_COL).setValue(lineInfo.getGuideLineType());
				}
			}
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		
		this.actionAddLine.setEnabled(true);
		this.actionInsertLine.setEnabled(true);
		this.actionRemoveLine.setEnabled(true);
		
		remove(btnAttachment);
		this.btnAttachment.setVisible(false);
		this.kdtTemplateEntry.setEnabled(true);
	}
	public void actionAddLine_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddLine_actionPerformed(e);
		
		setFootRowSum();
	}
	
	public void actionInsertLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionInsertLine_actionPerformed(e);
		
		setFootRowSum();
	}
	
	public void actionRemoveLine_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionRemoveLine_actionPerformed(e);
		
		setFootRowSum();
	}
	
	private void setFootRowSum()
	{
		IRow footRow = kdtTemplateEntry.getFootRow(0);
    	
    	BigDecimal sum = new BigDecimal("0");
    	
    	for(int i= 0; i < kdtTemplateEntry.getRowCount(); ++i )
    	{
    		IRow tmpRow = kdtTemplateEntry.getRow(i);
    		if(tmpRow.getCell(WEIGHT_COL).getValue() != null)
    		{
    			BigDecimal tmp = new BigDecimal(tmpRow.getCell(WEIGHT_COL).getValue().toString());
    			
    			sum = sum.add(tmp);
    		}
    		
    		if(tmpRow.getCell(NAME_COL).getValue() != null)
    		{
    			AppraiseGuideLineInfo lineInfo = (AppraiseGuideLineInfo)tmpRow.getCell(NAME_COL).getValue();
    			if(lineInfo.getGuideLineType() != null)
    			{
    				tmpRow.getCell(TYPE_COL).setValue(lineInfo.getGuideLineType().getName());
    			}
    		}
    	}

    	footRow.getCell(WEIGHT_COL).setValue(sum);
	}
	protected boolean isShowAttachmentAction()
	{
		return false;
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		
		this.actionAudit.setVisible(false);
		this.actionUnAudit.setVisible(false);
		
		if(kdtTemplateEntry.getRowCount() > 0)
		{
			for(int i = 0; i < kdtTemplateEntry.getRowCount(); ++i)
			{
				IRow row = kdtTemplateEntry.getRow(i);
				// guideLineType guideLineName 
				if(row.getCell(NAME_COL).getValue() != null && row.getCell(NAME_COL).getValue() instanceof AppraiseGuideLineInfo)
				{
					AppraiseGuideLineInfo guideLineInfo = (AppraiseGuideLineInfo)row.getCell(NAME_COL).getValue();
					AppraiseGuideLineTypeInfo guideLineTypeInfo = guideLineInfo.getGuideLineType();
					row.getCell(TYPE_COL).setValue(guideLineTypeInfo);
				}
			}
		}
	}
	
	protected void initDapButtons() throws Exception {
		
		super.initDapButtons();
		
		this.contAuditor.setEnabled(false);
		this.prmtAuditor.setEnabled(false);
		this.contCreator.setEnabled(false);
		this.prmtCreator.setEnabled(false);
		
		this.contCreateTime.setEnabled(false);
		this.pkCreateTime.setEnabled(false);
		this.contAuditDate.setEnabled(false);
		this.pkAuditDate.setEnabled(false);
	}
	
	protected void verifyInputForSave() throws Exception {
		// TODO Auto-generated method stub
		super.verifyInputForSave();
		
		if(editData.getNumber()==null||editData.getNumber().trim().length()==0){
			FDCMsgBox.showWarning(this,"评标模板编码不能为空");
			abort();
		}
		
		if(editData.getName()==null||editData.getName().trim().length()==0){
			FDCMsgBox.showWarning(this,"评标模板名称不能为空");
			abort();
		}
		
		for(int i=0;i<kdtTemplateEntry.getRowCount();i++){
			
			if(kdtTemplateEntry.getCell(i, 0).getValue() == null || kdtTemplateEntry.getCell(i, 1).getValue()==null ||kdtTemplateEntry.getCell(i, 2).getValue() == null){
				FDCMsgBox.showWarning(this,"评标指标名称的相关参数不完整！");
				abort();
			}
			
		}
	}

	protected void verifyInputForSubmint() throws Exception {
		// TODO Auto-generated method stub
		super.verifyInputForSubmint();
		
		if(editData.getNumber()==null||editData.getNumber().trim().length()==0){
			FDCMsgBox.showWarning(this,"评标模板编码不能为空");
			abort();
		}
		
		if(editData.getName()==null||editData.getName().trim().length()==0){
			FDCMsgBox.showWarning(this,"评标模板名称不能为空");
			abort();
		}
		
		if(editData.getDescription() != null)
		{
			if(editData.getDescription().trim().length() > 200)
			{
				FDCMsgBox.showWarning(this,"备注长度不能超过200");
				abort();
			}
		}
		//检测分录
		for(int i = 0; i < kdtTemplateEntry.getRowCount(); ++i)
		{
			IRow row = kdtTemplateEntry.getRow(i);
			Integer index = new Integer(i+1);
			String warning = "第" + index.toString() + "行、第" ;
			if(row.getCell(this.NAME_COL).getValue() == null)
			{
				warning = warning + "2列评标指标名称不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			
			if(row.getCell(this.WEIGHT_COL).getValue() == null)
			{
				warning = warning + "3列权重不能为空";
				FDCMsgBox.showWarning(this,warning);
				abort();
			}
			
			if(row.getCell(this.WEIGHT_COL).getValue() != null)
			{
				BigDecimal tmpWeight = new BigDecimal(row.getCell(this.WEIGHT_COL).getValue().toString());
				BigDecimal tmpOne = new BigDecimal("1");
				BigDecimal tmpHundered = new BigDecimal("100");
				if(tmpWeight.compareTo(FDCHelper.ZERO) <= 0)
				{
					warning = warning + "3列权重小于等于0";
					FDCMsgBox.showWarning(this,warning);
					abort();
				}
				
				if(tmpWeight.compareTo(tmpHundered) > 0)
				{
					warning = warning + "3列权重不能大于100";
					FDCMsgBox.showWarning(this,warning);
					abort();
				}
			}
			
			if(row.getCell(this.DESC_COL).getValue() != null)
			{
				String descrption = row.getCell(this.DESC_COL).getValue().toString();
				if(descrption.trim().length() > 100)
				{
					warning = warning + "4列备注长度不超过100";
					FDCMsgBox.showWarning(this,warning);
					abort();
				}
			}
		}
		
		for(int rowIndex = 0; rowIndex < kdtTemplateEntry.getRowCount(); ++rowIndex)
		{
			for(int j = 0; j < kdtTemplateEntry.getRowCount(); ++ j)
			{
				if(rowIndex != j)
				{
					if(kdtTemplateEntry.getRow(rowIndex).getCell(this.NAME_COL).getValue() != null &&
							kdtTemplateEntry.getRow(j).getCell(this.NAME_COL).getValue() != null	)
					{
						
					}
					AppraiseGuideLineInfo rowIndexMat = (AppraiseGuideLineInfo)(kdtTemplateEntry.getRow(rowIndex).getCell(this.NAME_COL).getValue());
					
					AppraiseGuideLineInfo jMat = (AppraiseGuideLineInfo)(kdtTemplateEntry.getRow(j).getCell(this.NAME_COL).getValue());
					
					if(rowIndexMat.getId().equals(jMat.getId()))
					{
						Integer indexInt = new Integer(rowIndex+1);
						Integer jInt = new Integer(j+1);
						StringBuffer buffer = new StringBuffer("第" + indexInt.toString());
						buffer.append("行和");
						buffer.append("第");
						
						buffer.append(jInt.toString() +"行为同一个评标指标，不能执行此操作。");
						FDCMsgBox.showWarning(this,buffer.toString());
						
						kdtTemplateEntry.getRow(j).getCell(this.NAME_COL).setValue(null);
						
						abort();
					}
						
				}
			}
		}
		
		IRow footRow = kdtTemplateEntry.getFootRow(0);
    	
    	BigDecimal sum = new BigDecimal("0");
    	
    	for(int i= 0; i < kdtTemplateEntry.getRowCount(); ++i )
    	{
    		IRow tmpRow = kdtTemplateEntry.getRow(i);
    		if(tmpRow.getCell(WEIGHT_COL).getValue() != null)
    		{
    			BigDecimal tmp = new BigDecimal(tmpRow.getCell(WEIGHT_COL).getValue().toString());
    			
    			sum = sum.add(tmp);
    		}
    	}

//    	if(sum.compareTo(new BigDecimal("100") )!= 0)
//    	{
//    		FDCMsgBox.showWarning(this,"权重合计不等于100");
//			abort();
//    	}
    	footRow.getCell(WEIGHT_COL).setValue(sum);
	}
	
	protected void tblDetail_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		
		super.tblDetail_tableSelectChanged(e);
	}

	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}

	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 打印
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null
				&& !com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(editData
						.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		InvitePrintDataProvider data = new InvitePrintDataProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	/**
	 * 打印预览
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		logger.info("打印预览");
		ArrayList idList = new ArrayList();
		if (editData != null
				&& !com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(editData
						.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		InvitePrintDataProvider data = new InvitePrintDataProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	// 获得无文本合同套打对应的目录
	protected String getTDFileName() {
		return "/bim/fdc/invite/AppraiseTemplateForPrint";
	}
	
	// 对应的套打Query
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.invite.app.AppraiseTemplateForPrintQuery");
	}
}