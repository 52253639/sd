/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.BasicView;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTCell;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.BizEnumValueDTO;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.KDDetailedArea;
import com.kingdee.eas.fdc.basedata.util.KDDetailedAreaDialog;
import com.kingdee.eas.fdc.market.ChannelTypeCollection;
import com.kingdee.eas.fdc.market.ChannelTypeFactory;
import com.kingdee.eas.fdc.market.ChannelTypeInfo;
import com.kingdee.eas.fdc.market.ChannelTypeTreeFactory;
import com.kingdee.eas.fdc.market.ChannelTypeTreeInfo;
import com.kingdee.eas.fdc.market.EnterprisePlanCollection;
import com.kingdee.eas.fdc.market.EnterprisePlanEntryCollection;
import com.kingdee.eas.fdc.market.EnterprisePlanEntryFactory;
import com.kingdee.eas.fdc.market.EnterprisePlanEntryInfo;
import com.kingdee.eas.fdc.market.EnterprisePlanFactory;
import com.kingdee.eas.fdc.market.EnterprisePlanInfo;
import com.kingdee.eas.fdc.market.EnterpriseSellPlanCollection;
import com.kingdee.eas.fdc.market.EnterpriseSellPlanFactory;
import com.kingdee.eas.fdc.market.EnterpriseSellPlanInfo;
import com.kingdee.eas.fdc.market.ThemeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class EnterprisePlanEditUI extends AbstractEnterprisePlanEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(EnterprisePlanEditUI.class);
    private boolean isWarning=false;
    /**
     * output class constructor
     */
    public EnterprisePlanEditUI() throws Exception
    {
        super();
    }
   
    /**
     * output storeFields method
     */
	public void onLoad() throws Exception {
		
		filesType();
		
		super.onLoad();
		
//		this.setPreferredSize(getMaximumSize());
		
		txtPlanYear.setGroupingUsed(false);
		txtPlanYear.setDataType(KDFormattedTextField.INTEGER);
		
//		this.kDPlanMonth.setEditor(new com.kingdee.bos.ctrl.swing.KDSpinner.DefaultNumberEditor(this.kDPlanMonth,""));
		SpinnerNumberModel model = new SpinnerNumberModel(editData.getPlanMonth(),1,12,1);
		this.kDPlanMonth.setModel(model);
		
		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		this.menuBiz.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		
		this.actionAddNew.setVisible(false);
		this.prmtOrgUnit.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.prmtSellProject.setAccessAuthority(CtrlCommonConstant.AUTHORITY_COMMON);
		this.prmtOrgUnit.setEnabled(false);
		this.prmtSellProject.setEnabled(false);
		
		 CRMClientHelper.changeTableNumberFormat(this.tblMainPlan, new String[]{"planCost"});
			
	}
	public SelectorItemCollection getSelectors(){
		SelectorItemCollection sel=super.getSelectors();
		sel.add("entry.*");
		sel.add("entry.sellPlanEntry.*");
		sel.add("entry.sellPlanEntry.subject.*");
		sel.add("entry.sellPlanEntry.classify.*");
		sel.add("entry.sellPlanEntry.mediaName.*");
		sel.add("CU.*");
		sel.add("state");
		return sel;
	}
	protected void setAuditButtonStatus(String oprtType){
		super.setAuditButtonStatus(oprtType);
		if(STATUS_VIEW.equals(oprtType)){
			 this.btnAddTheme.setEnabled(false);
			 this.btnAddProceed.setEnabled(false);
			 this.btnDelProceed.setEnabled(false);
			 this.btnDelTheme.setEnabled(false);
			 this.btnChange.setEnabled(false);
		 }else{
			 this.btnAddTheme.setEnabled(true);
			 this.btnAddProceed.setEnabled(true);
			 this.btnDelProceed.setEnabled(true);
			 this.btnDelTheme.setEnabled(true);
			 this.btnChange.setEnabled(true);
		 }
		
		if(STATUS_ADDNEW.equals(oprtType)){
			this.txtPlanYear.setEnabled(true);
			this.kDPlanMonth.setEnabled(true);
		}else{
			this.txtPlanYear.setEnabled(false);
			this.kDPlanMonth.setEnabled(false);
		}
	}
    public void storeFields()
    {
    	storeEntry();
    	super.storeFields();
    }
    public void loadFields() {
    	detachListeners();
		super.loadFields();
		setSaveActionStatus();

        int idx = idList.getCurrentIndex();
        if (idx >= 0) {
            billIndex = "(" + (idx + 1) + ")";
        } else {
        	billIndex = "";
        }
		refreshUITitle();
		setAuditButtonStatus(this.getOprtState());
			
		loadEntry(editData.getEntry());
		
		CRMClientHelper.getFootRow(this.tblMainPlan, new String[]{"planCost"});
		attachListeners();
	}
    protected void verifyInputForSave() throws Exception {
    	if(isWarning){
    		if(!(FDCMsgBox.showConfirm2(this, "之前存在未审批的企划计划，提交将覆盖该计划，是否继续？")== FDCMsgBox.YES)){
    			SysUtil.abort();
    		}
    	}
		if(getNumberCtrl().isEnabled()) {
			VerifyInputUtil.verifyNull(this, txtNumber,"计划编号" );
		}
		VerifyInputUtil.verifyNull(this, txtNumber,"计划名称" );
		VerifyInputUtil.verifyNull(this, txtPlanYear,"计划年份" );
	}
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
		VerifyInputUtil.verifyKDTColumnNull(this,tblMainPlan,"theme");
		VerifyInputUtil.verifyKDTColumnNull(this,tblMainPlan,"enDescribe");
		VerifyInputUtil.verifyKDTColumnNull(this,tblMainPlan,"proceeding");
		VerifyInputUtil.verifyKDTColumnNull(this,tblMainPlan,"subject");
		VerifyInputUtil.verifyKDTColumnNull(this,tblMainPlan,"classify");
		VerifyInputUtil.verifyKDTColumnNull(this,tblMainPlan,"mediaName");
		VerifyInputUtil.verifyKDTColumnNull(this,tblMainPlan,"startTime");
		VerifyInputUtil.verifyKDTColumnNull(this,tblMainPlan,"endTime");
		VerifyInputUtil.verifyKDTColumnNull(this,tblMainPlan,"planCost");
	}
    /**
     * 保存分录
     */
    public void storeEntry(){
    	editData.getEntry().clear();
        for(int i=0;i<tblMainPlan.getRowCount();i++){
        	IRow row=tblMainPlan.getRow(i);
        	EnterprisePlanEntryInfo entry=(EnterprisePlanEntryInfo) row.getCell("theme").getUserObject();
        	EnterpriseSellPlanInfo  sellPlanInfo = (EnterpriseSellPlanInfo) row.getUserObject();
    		if(tblMainPlan.getCell(i, "proceeding").getValue()!=null){
    			String proceeding =(String)tblMainPlan.getCell(i, "proceeding").getValue();
    			sellPlanInfo.setProceeding(proceeding);
    		}
    		if(tblMainPlan.getCell(i, "entryState").getValue()!=null){
    			ThemeEnum entryState =(ThemeEnum)tblMainPlan.getCell(i, "entryState").getValue();
    			sellPlanInfo.setState(entryState);
    		}
    		if(tblMainPlan.getCell(i, "mediaName").getValue()!=null){
    			ChannelTypeInfo mediaName =(ChannelTypeInfo)tblMainPlan.getCell(i, "mediaName").getValue();
    			sellPlanInfo.setMediaName(mediaName);
    		}
    		if(tblMainPlan.getCell(i, "classify").getValue()!=null){
    			ChannelTypeTreeInfo classifyInfo =(ChannelTypeTreeInfo)tblMainPlan.getCell(i, "classify").getValue();
    			sellPlanInfo.setClassify(classifyInfo);
    		}
    		if(tblMainPlan.getCell(i, "subject").getValue()!=null){
    			CostAccountInfo subjectInfo =(CostAccountInfo)tblMainPlan.getCell(i, "subject").getValue();
    			sellPlanInfo.setSubject(subjectInfo);
    		}
    		Date startTime = null;
    		if(tblMainPlan.getCell(i, "startTime").getValue()!=null){
    			startTime = (Date)tblMainPlan.getCell(i, "startTime").getValue();
    			sellPlanInfo.setStartTime(startTime);
    		}
    		Date endTime = null;
    		if(tblMainPlan.getCell(i, "endTime").getValue()!=null){
    			endTime = (Date)tblMainPlan.getCell(i, "endTime").getValue();
    			sellPlanInfo.setEndTime(endTime);
    		}
    		if(tblMainPlan.getCell(i, "quantity").getValue()!=null){
    			Object quantity =tblMainPlan.getCell(i, "quantity").getValue();
    			int quan = Integer.parseInt(quantity.toString());
    			sellPlanInfo.setQuantity(quan);
    		}
    		BigDecimal planCost = FDCHelper.ZERO;
    		if(tblMainPlan.getCell(i, "planCost").getValue()!=null){
    			planCost = (BigDecimal)tblMainPlan.getCell(i, "planCost").getValue();
    			sellPlanInfo.setPlanCost(planCost);
    		}
    		if(tblMainPlan.getCell(i, "remark").getValue()!=null){
    			String remark =(String)tblMainPlan.getCell(i, "remark").getValue(); 
    			sellPlanInfo.setRemark(remark);
    		}
    		
    		if(editData.getEntry().containsObject(entry)){
    			entry.getSellPlanEntry().add(sellPlanInfo);
    			continue;
    		}else{
    			entry.getSellPlanEntry().clear();
    			if(tblMainPlan.getCell(i, "themeState").getValue()!=null){
        			ThemeEnum state =(ThemeEnum)tblMainPlan.getCell(i, "themeState").getValue();
        			entry.setState(state);
        		}
        		if(tblMainPlan.getCell(i, "enDescribe").getValue()!=null){
        			String enDescribe =(String)tblMainPlan.getCell(i, "enDescribe").getValue();
        			entry.setDescribe(enDescribe);
        		}
        		if(tblMainPlan.getCell(i, "theme").getValue()!=null){
        			String theme =(String)tblMainPlan.getCell(i, "theme").getValue();
        			entry.setTheme(theme);
        		}
        		entry.getSellPlanEntry().add(sellPlanInfo);
    			editData.getEntry().add(entry);
    		}
        }
    }
    
    private void setMY() throws Exception{
    	int year = 0;
		int month = 0;
		if(txtPlanYear.getValue() instanceof Integer)
			year = ((Integer)txtPlanYear.getValue()).intValue();
		if(txtPlanYear.getValue() instanceof BigDecimal)
			year = ((BigDecimal)txtPlanYear.getValue()).intValue();
		month = ((Integer)kDPlanMonth.getValue()).intValue();
		EnterprisePlanInfo info = getEnterpriseNew();
		if(info==null){
			txtVersion.setText("V1.0");
			getName();
			return;
		}
		if(info.getPlanYear()>year 
				|| (info.getPlanYear()==year && info.getPlanMonth()>month)){
			FDCMsgBox.showError(this,"当前年月或之后已有计划，不能在此年月新增企划计划");
			detachListeners();
			txtPlanYear.setValue(new Integer(info.getPlanYear()));
			attachListeners();
			
			kDPlanMonth.setValue(new Integer(info.getPlanMonth()));
			
		}else{
			this.tblMainPlan.removeRows();
			editData.getEntry().clear();
			if(info.getPlanYear()==year && info.getPlanMonth()==month){
				txtVersion.setText("V"+getVersionInt(info.getVersion())+".0");
				for(int i=0;i<info.getEntry().size();i++){
					editData.getEntry().add(info.getEntry().get(i));
				}
			}else{
				txtVersion.setText("V1.0");
				for(int i=0;i<info.getEntry().size();i++){
					if(info.getEntry().get(i).getState().equals(ThemeEnum.Finish)||info.getEntry().get(i).getState().equals(ThemeEnum.Canceled)){
						continue;
					}
					editData.getEntry().add(info.getEntry().get(i));
				}
			}
			this.loadEntry(editData.getEntry());
			getName();
		}
    }
    protected void txtPlanYear_dataChanged(DataChangeEvent e) throws Exception {
    	setMY();
	}
    /**
     * 改变月份改变版本
     */
    protected void kDPlanMonth_stateChanged(ChangeEvent e) throws Exception {
    	setMY();
	}
	/**
     * 版本名称
     * */
    public String getName(){
    	int year = 0;
		int month = 0;
		String Names = null;
		if(txtPlanYear.getValue() instanceof Integer)
			year = ((Integer)txtPlanYear.getValue()).intValue();
		if(txtPlanYear.getValue() instanceof BigDecimal)
			year = ((BigDecimal)txtPlanYear.getValue()).intValue();
		month = ((Integer)kDPlanMonth.getValue()).intValue();
		SellProjectInfo sell = (SellProjectInfo)prmtSellProject.getValue();
		try {
			if(sell!=null){
				SellProjectInfo sellInfo =SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sell.getId().toString()));
				String name =sellInfo.getName();
				String version =this.txtVersion.getText();
			    Names = name+year+"年"+month+"月"+"营销费用计划"+version;
			}
	     } catch (Exception e) {
		    e.printStackTrace();
	     }
	     txtName.setText(Names);
		return Names;
    }
	/**
     * 分录展现
     */
    public void loadEntry(EnterprisePlanEntryCollection entry){
    	tblMainPlan.removeRows();
		EnterprisePlanEntryCollection enterEntryColl = entry; 
	    CRMHelper.sortCollection(enterEntryColl, "seq", true);
	    for(int i=0;i<enterEntryColl.size();i++){
	    	EnterprisePlanEntryInfo  planEntryInfo =enterEntryColl.get(i);
	    	
	    	EnterpriseSellPlanCollection sellCol = planEntryInfo.getSellPlanEntry();
	    	CRMHelper.sortCollection(sellCol, "seq", true);
	    	for(int j=0;j<sellCol.size();j++){
	    		IRow row = tblMainPlan.addRow();
	    		EnterpriseSellPlanInfo sellInfo=sellCol.get(j);
	    		row.setUserObject(sellInfo);
	    		
	    		ThemeEnum themeState=planEntryInfo.getState();
	    		row.getCell("theme").setValue(planEntryInfo.getTheme()); 
				row.getCell("theme").setUserObject(planEntryInfo);
	    		row.getCell("themeState").setValue((ThemeEnum)planEntryInfo.getState()); 
				row.getCell("enDescribe").setValue(planEntryInfo.getDescribe());
				row.getCell("id").setValue(planEntryInfo.getId());
				
				if(ThemeEnum.Finish.equals(themeState)||ThemeEnum.Canceled.equals(themeState)){
					row.getCell("theme").getStyleAttributes().setLocked(true);
					row.getCell("enDescribe").getStyleAttributes().setLocked(true);
				}
	    		ThemeEnum entryState=sellInfo.getState();
				if(ThemeEnum.Finish.equals(entryState)||ThemeEnum.Canceled.equals(entryState)){
					row.getCell("proceeding").getStyleAttributes().setLocked(true);
					row.getCell("subject").getStyleAttributes().setLocked(true);
					row.getCell("classify").getStyleAttributes().setLocked(true);
					row.getCell("mediaName").getStyleAttributes().setLocked(true);
					row.getCell("startTime").getStyleAttributes().setLocked(true);
					row.getCell("endTime").getStyleAttributes().setLocked(true);
					row.getCell("quantity").getStyleAttributes().setLocked(true);
					row.getCell("planCost").getStyleAttributes().setLocked(true);
					row.getCell("remark").getStyleAttributes().setLocked(true);
					
					row.getCell("proceeding").getStyleAttributes().setBackground(new Color(245,245,245));
					row.getCell("subject").getStyleAttributes().setBackground(new Color(245,245,245));
					row.getCell("classify").getStyleAttributes().setBackground(new Color(245,245,245));
					row.getCell("mediaName").getStyleAttributes().setBackground(new Color(245,245,245));
					row.getCell("startTime").getStyleAttributes().setBackground(new Color(245,245,245));
					row.getCell("endTime").getStyleAttributes().setBackground(new Color(245,245,245));
					row.getCell("quantity").getStyleAttributes().setBackground(new Color(245,245,245));
					row.getCell("planCost").getStyleAttributes().setBackground(new Color(245,245,245));
					row.getCell("remark").getStyleAttributes().setBackground(new Color(245,245,245));
				}
				row.getCell("entryid").setValue(sellInfo.getId());
				row.getCell("proceeding").setValue(sellInfo.getProceeding());
				row.getCell("entryState").setValue((ThemeEnum)sellInfo.getState()); 
				row.getCell("subject").setValue(sellInfo.getSubject()); 
				row.getCell("classify").setValue(sellInfo.getClassify());
				row.getCell("mediaName").setValue(sellInfo.getMediaName());
				row.getCell("startTime").setValue(sellInfo.getStartTime());
				row.getCell("endTime").setValue(sellInfo.getEndTime());
				row.getCell("quantity").setValue(Integer.valueOf(sellInfo.getQuantity()));
				row.getCell("planCost").setValue(sellInfo.getPlanCost());
				row.getCell("remark").setValue(sellInfo.getRemark());
	    	}
		 }
	     tblMainPlan.getColumn("themeState").getStyleAttributes().setBackground(new Color(245,245,245));
	     tblMainPlan.getColumn("entryState").getStyleAttributes().setBackground(new Color(245,245,245));
	     tblMainPlan.getColumn("subject").getStyleAttributes().setBackground(new Color(245,245,245));
	     tblMainPlan.getColumn("classify").getStyleAttributes().setBackground(new Color(245,245,245));
	     
		 mergerTable(this.tblMainPlan, new String[]{"id"},new String[]{"theme","themeState","enDescribe"});
   }
    
//    public SelectorItemCollection getPalnThemeSelectors(){
//		SelectorItemCollection sel=super.getSelectors();
//		sel.add("*");
//		sel.add("subject.*");
//		sel.add("classify.*");
//		sel.add("mediaName.*");
//		sel.add("CU.*");
//		return sel;
//	}
    
    /**E2E
	 * 同一分录中 对计划费用列相加 并返回 数
	 * @param string1 
	 * */
	public static BigDecimal EADD(KDTable kdtEntrys,String name1) {
		BigDecimal count1=new BigDecimal(0);//name1
		BigDecimal sum   =new BigDecimal(0);// 总
		for(int i=0;i<kdtEntrys.getRowCount();i++)
		{
			if(kdtEntrys.getRow(i).getCell(name1)!=null&&kdtEntrys.getRow(i).getCell(name1).getValue()!=null
					&& !kdtEntrys.getRow(i).getCell("themeState").getValue().equals(ThemeEnum.Canceled)){
				count1=count1.add((BigDecimal)kdtEntrys.getRow(i).getCell(name1).getValue());
			}
		}
		sum=count1;
		return sum;
	}
	/**
     * 选择媒体名称，自动带出渠道类、营销项目
     */
	protected void tblMainPlan_editStopped(KDTEditEvent e) throws Exception {
	    this.txtTotalAmount.setValue(EADD(tblMainPlan, "planCost"));
	    
	    int rowIndex = e.getRowIndex();
		String keyName = tblMainPlan.getColumnKey(e.getColIndex());
		if ("theme".equals(keyName)) {
			 for(int i=0;i<tblMainPlan.getRowCount();i++){
				 IRow rmrow=tblMainPlan.getRow(i);
				 if(rmrow.getCell("id").getValue().equals(tblMainPlan.getRow(rowIndex).getCell("id").getValue())){
					 rmrow.getCell("theme").setValue(tblMainPlan.getRow(rowIndex).getCell("theme").getValue());
				 }
			 }
		}else if("enDescribe".equals(keyName)){
			for(int i=0;i<tblMainPlan.getRowCount();i++){
				 IRow rmrow=tblMainPlan.getRow(i);
				 if(rmrow.getCell("id").getValue().equals(tblMainPlan.getRow(rowIndex).getCell("id").getValue())){
					 rmrow.getCell("enDescribe").setValue(tblMainPlan.getRow(rowIndex).getCell("enDescribe").getValue());
				 }
			 }
		}else if ("subject".equals(keyName)) {
			if(tblMainPlan.getCell(rowIndex, "subject").getValue() != null){
				CostAccountInfo subject = (CostAccountInfo)tblMainPlan.getCell(rowIndex, "subject").getValue();
				if(!subject.isIsLeaf()){
					FDCMsgBox.showInfo("请选择明细级科目!");
					tblMainPlan.getCell(rowIndex, "subject").setValue(null);
					SysUtil.abort();
				}
			}
			if(e.getOldValue()!=null && !e.getOldValue().equals(tblMainPlan.getCell(rowIndex, "subject").getValue())){
				tblMainPlan.getCell(rowIndex, "classify").setValue(null);
				tblMainPlan.getCell(rowIndex, "mediaName").setValue(null);
			}
		}else if ("classify".equals(keyName)) {
			if(tblMainPlan.getCell(rowIndex, "classify").getValue()!=null){
				ChannelTypeTreeInfo classifyInfo = (ChannelTypeTreeInfo)tblMainPlan.getCell(rowIndex, "classify").getValue();
				CostAccountInfo subjectInfo =CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(classifyInfo.getCsotaccount().getId()));
    			tblMainPlan.getCell(rowIndex, "subject").setValue(subjectInfo);
    			if(tblMainPlan.getCell(rowIndex, "mediaName").getValue()!=null){
    				ChannelTypeInfo mediaName =(ChannelTypeInfo) tblMainPlan.getCell(rowIndex, "mediaName").getValue();
    				ChannelTypeCollection col=ChannelTypeFactory.getRemoteInstance().getChannelTypeCollection("select * from where treeid='"+classifyInfo.getId()+"' and id='"+mediaName.getId()+"'");
        			if(col.size()>0){
        				tblMainPlan.getCell(rowIndex, "mediaName").setValue(col.get(0));
        			}else{
        				tblMainPlan.getCell(rowIndex, "mediaName").setValue(null);
        			}
    			}
			}
		}else if ("mediaName".equals(keyName)) {
			if(tblMainPlan.getCell(rowIndex, "mediaName").getValue()!=null){
				ChannelTypeInfo mediaName =(ChannelTypeInfo)tblMainPlan.getCell(rowIndex, "mediaName").getValue();
    			String classifyId =mediaName.getTreeid().getId().toString();
    			ChannelTypeTreeInfo classifyInfo =ChannelTypeTreeFactory.getRemoteInstance().getChannelTypeTreeInfo(new ObjectUuidPK(classifyId));
    			tblMainPlan.getCell(rowIndex, "classify").setValue(classifyInfo);
    			CostAccountInfo subjectInfo =CostAccountFactory.getRemoteInstance().getCostAccountInfo(new ObjectUuidPK(classifyInfo.getCsotaccount().getId().toString()));
    			tblMainPlan.getCell(rowIndex, "subject").setValue(subjectInfo);
			}
		}else if("startTime".equals(keyName)){
			Date startDate=(Date) tblMainPlan.getRow(rowIndex).getCell("startTime").getValue();
			Date endDate=(Date) tblMainPlan.getRow(rowIndex).getCell("endTime").getValue();
			if(startDate!=null&&endDate!=null){
				if(startDate.compareTo(endDate)>0){
					tblMainPlan.getRow(rowIndex).getCell("startTime").setValue(null);
					FDCMsgBox.showWarning(this,"结束时间不能小于开始时间！");
					SysUtil.abort();
				}
			}
		}else if("endTime".equals(keyName)){
			Date startDate=(Date) tblMainPlan.getRow(rowIndex).getCell("startTime").getValue();
			Date endDate=(Date) tblMainPlan.getRow(rowIndex).getCell("endTime").getValue();
			if(startDate!=null&&endDate!=null){
				if(startDate.compareTo(endDate)>0){
					tblMainPlan.getRow(rowIndex).getCell("endTime").setValue(null);
					FDCMsgBox.showWarning(this,"结束时间不能小于开始时间！");
					SysUtil.abort();
				}
			}
		}else if("planCost".equals(keyName)){
			CRMClientHelper.getFootRow(this.tblMainPlan, new String[]{"planCost"});
		}
	}
  
	/**
     * 更改主题状态
     */
    public void actionChange_actionPerformed(ActionEvent e) throws Exception
    {
        int rowIndex = tblMainPlan.getSelectManager().getActiveRowIndex();
        if(rowIndex==-1){
        	return;
		}
        IRow row = tblMainPlan.getRow(rowIndex);
        ThemeEnum themState=(ThemeEnum) row.getCell("themeState").getValue();
        ThemeEnum updateState=null;
        if(themState.equals(ThemeEnum.Canceled))
        {
        	updateState=ThemeEnum.UnStarted;
        	row.getCell("themeState").setValue(updateState);
        	this.btnChange.setText("取消主题");
        }else if(themState.equals(ThemeEnum.UnStarted)){
        	updateState=ThemeEnum.Canceled;
        	row.getCell("themeState").setValue(updateState);
        	this.btnChange.setText("激活主题");
        }else if(themState.equals(ThemeEnum.Underway)||themState.equals(ThemeEnum.Finish)){
        	MsgBox.showWarning(this,"已完成或者进行中的主题不能进行主题状态变更!");
        	return;
        }
        for(int i=0;i<tblMainPlan.getRowCount();i++){
        	IRow rmrow=tblMainPlan.getRow(i);
        	if(rmrow.getCell("id").getValue().equals(row.getCell("id").getValue())){
        		rmrow.getCell("themeState").setValue(updateState);
        	}
        }
        this.txtTotalAmount.setValue(EADD(tblMainPlan, "planCost"));
    }

    /**
     * 新增主题
     */
    public void actionAddTheme_actionPerformed(ActionEvent e) throws Exception
    {
       	IRow row =  tblMainPlan.addRow();
        row.getCell("themeState").setValue(ThemeEnum.UnStarted);
        row.getCell("entryState").setValue(ThemeEnum.UnStarted);
        EnterpriseSellPlanInfo sellPlan=new EnterpriseSellPlanInfo();
        row.setUserObject(sellPlan);
        
        EnterprisePlanEntryInfo entry=new EnterprisePlanEntryInfo();
        entry.setId(BOSUuid.create(entry.getBOSType()));
        row.getCell("theme").setUserObject(entry);
        row.getCell("id").setValue(entry.getId());
        tblMainPlan.getSelectManager().select(row.getRowIndex(), 0);
    }
    /**
     * 删除主题
     */
    public void actionDelTheme_actionPerformed(ActionEvent e) throws Exception
    {
        int rowIndex = tblMainPlan.getSelectManager().getActiveRowIndex();
        IRow row     = tblMainPlan.getRow(rowIndex);
        if(rowIndex == -1){
        	MsgBox.showWarning(this,"请选中企划主题删除！");
        	return;
        }
        if(row.getCell("themeState").getValue().equals(ThemeEnum.Finish)||row.getCell("themeState").getValue().equals(ThemeEnum.Underway)){
        	MsgBox.showWarning(this,""+row.getCell("themeState").getValue()+"的主题不能删除!");
        	return;
        }
        Set indexSet = new HashSet();
        for(int i=0;i<tblMainPlan.getRowCount();i++){
        	IRow rmrow=tblMainPlan.getRow(i);
        	if(rmrow.getCell("id").getValue().equals(row.getCell("id").getValue())){
        		indexSet.add(new Integer(i));
        	}
        }
        indexSet.add(new Integer(rowIndex));
        
        Integer[] indexArr = new Integer[indexSet.size()];
		Object[] indexObj = indexSet.toArray();
		System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
		Arrays.sort(indexArr);
		if (indexArr == null){
			return;
		}
		for (int i = indexArr.length - 1; i >= 0; i--) {
			int rm = Integer.parseInt(String.valueOf(indexArr[i]));
			tblMainPlan.removeRow(rm);
		}
        //删除主题改变金额
        this.txtTotalAmount.setValue(EADD(tblMainPlan, "planCost"));   
    }

    /**
     * 新增事项
     * 主题信息融合
     */
    public void actionAddProceed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAddProceed_actionPerformed(e);
        int rowIndex = tblMainPlan.getSelectManager().getActiveRowIndex();
        IRow prow     = tblMainPlan.getRow(rowIndex);
        if(rowIndex == -1){
        	MsgBox.showWarning(this,"请选中企划主题！");
        	return;
        }
	    if(prow.getCell("themeState").getValue().equals(ThemeEnum.Canceled)||prow.getCell("themeState").getValue().equals(ThemeEnum.Finish)){
	    	MsgBox.showWarning(this,"已完成或者已取消的主题不能增加事项!");
        	return;
	    }
	    
        IRow row =tblMainPlan.addRow(rowIndex+1);
        EnterpriseSellPlanInfo sellPlan=new EnterpriseSellPlanInfo();
        row.setUserObject(sellPlan);
    	row.getCell("entryState").setValue(ThemeEnum.UnStarted);
    	row.getCell("theme").setValue(prow.getCell("theme").getValue());
    	row.getCell("theme").setUserObject(prow.getCell("theme").getUserObject());
    	row.getCell("themeState").setValue(prow.getCell("themeState").getValue());
    	row.getCell("enDescribe").setValue(prow.getCell("enDescribe").getValue());
    	row.getCell("id").setValue(prow.getCell("id").getValue());
        mergerTable(this.tblMainPlan, new String[]{"id"},new String[]{"theme","themeState","enDescribe"});
    }
    /**
     * 删除事项
     */
    public void actionDelProceed_actionPerformed(ActionEvent e) throws Exception
    {
        int rowIndex = tblMainPlan.getSelectManager().getActiveRowIndex();
        IRow row     = tblMainPlan.getRow(rowIndex);
        if(rowIndex == -1){
        	MsgBox.showWarning(this,"请选中事项删除！");
        	return;
        }
        if(row.getCell("entryState").getValue().equals(ThemeEnum.Finish)||row.getCell("entryState").getValue().equals(ThemeEnum.Underway)){
        	MsgBox.showWarning(this,""+row.getCell("entryState").getValue()+"的事项不能删除!");
        	return;
        }
        if(tblMainPlan.getRow(rowIndex).getCell("themeState").getValue()==null||tblMainPlan.getRow(rowIndex).getCell("themeState").getValue().equals(ThemeEnum.UnStarted)){
        	tblMainPlan.removeRow(rowIndex);
        }else{
        	tblMainPlan.getRow(rowIndex).getCell("entryState").setValue(ThemeEnum.Canceled);
        }
        
        //删除事项改变金额
        this.txtTotalAmount.setValue(EADD(tblMainPlan, "planCost"));    
        
        
    }
    public static void mergerTable(KDTable table,String coloum[],String mergeColoum[]){
		int merger=0;
		for(int i=0;i<table.getRowCount();i++){
			if(i>0){
				boolean isMerge=true;
				for(int j=0;j<coloum.length;j++){
					Object curRow=table.getRow(i).getCell(coloum[j]).getValue();
					Object lastRow=table.getRow(i-1).getCell(coloum[j]).getValue();
					if(getString(curRow).equals("")||getString(lastRow).equals("")||!getString(curRow).equals(getString(lastRow))){
						isMerge=false;
						merger=i;
					}
				}
				if(isMerge){
					for(int j=0;j<mergeColoum.length;j++){
						table.getMergeManager().mergeBlock(merger, table.getColumnIndex(mergeColoum[j]), i, table.getColumnIndex(mergeColoum[j]));
					}
				}
			}
		}
	}
	private static String getString(Object value){
		if(value==null) return "";
		if(value!=null&&value.toString().trim().equals("")){
			return "";
		}else{
			return value.toString();
		}
	}
    /**
     * 构建字段类型，使用到TableUtils类
     */
    
    public void filesType()throws Exception{
    	tblMainPlan.checkParsed();
    	tblMainPlan.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
    	
    	//主题状态
    	final KDComboBox tblMainPlan_themeState_TextField = new KDComboBox();
    	tblMainPlan_themeState_TextField.setName("tblMainPlan_themeState_TextField");
    	tblMainPlan_themeState_TextField.addItems(ThemeEnum.getEnumList().toArray());
	    KDTDefaultCellEditor tblMainPlan_themeState_CellEditor = new KDTDefaultCellEditor(tblMainPlan_themeState_TextField);
	    tblMainPlan.getColumn("themeState").setEditor(tblMainPlan_themeState_CellEditor);
	    //费用科目
		
		KDBizPromptBox f7Box = new KDBizPromptBox();
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.basedata.app.CostAccountQuery");
	        
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id", OrgConstants.DEF_CU_ID));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("number", "5001.01%", CompareType.NOTLIKE));
		evi.setFilter(filter);
		f7Box.setEntityViewInfo(evi);
		
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		tblMainPlan.getColumn("subject").setEditor(f7Editor);
		
		//渠道分类
		String classify = "com.kingdee.eas.fdc.market.app.ChannelTypeTreeQuery";
		EntityViewInfo classifyView = new EntityViewInfo();
		FilterInfo classifyfilter = new FilterInfo();
		classifyView.setFilter(classifyfilter);
		ICellEditor f7classify = new KDTDefaultCellEditor(TableUtils.getF7productType(classify,classifyView));
		tblMainPlan.getColumn("classify").setEditor(f7classify);
		
		
		//媒体名称
		f7Box = new KDBizPromptBox();
		evi= new EntityViewInfo();
		filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("statrusing",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("CU.id",SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()));
		evi.setFilter(filter);
		
		HashMap ctx=new HashMap();
		ctx.put("EntityViewInfo", evi);
		ctx.put("EnableMultiSelection", new Boolean(false));
		ctx.put("HasCUDefaultFilter", new Boolean(true));
		
		
		ChannelTypeListUI ctlistUI=new ChannelTypeListUI();
		ctlistUI.setF7Use(true, ctx);
		ctlistUI.getUIToolBar().setVisible(false);
		ctlistUI.getUIMenuBar().setVisible(false);
		f7Box.setSelector(ctlistUI);
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.tblMainPlan.getColumn("mediaName").setEditor(f7Editor);
		
		//开始时间
		final KDDatePicker tblMainPlan_startTime_PromptBox = new KDDatePicker();
		tblMainPlan_startTime_PromptBox.setName("tblMainPlan_startTime_PromptBox");
	    tblMainPlan_startTime_PromptBox.setVisible(true);
	    tblMainPlan_startTime_PromptBox.setEnabled(true);
	    KDTDefaultCellEditor tblMainPlan_startTime_CellEditor = new KDTDefaultCellEditor(tblMainPlan_startTime_PromptBox);
	    tblMainPlan.getColumn("startTime").setEditor(tblMainPlan_startTime_CellEditor);

	    //结束时间
	    final KDDatePicker tblMainPlan_endTime_PromptBox = new KDDatePicker();
	    tblMainPlan_endTime_PromptBox.setName("tblMainPlan_endTime_PromptBox");
	    tblMainPlan_endTime_PromptBox.setVisible(true);
	    tblMainPlan_endTime_PromptBox.setEnabled(true);
	    KDTDefaultCellEditor tblMainPlan_endTime_CellEditor = new KDTDefaultCellEditor(tblMainPlan_endTime_PromptBox);
	    this.tblMainPlan.getColumn("endTime").setEditor(tblMainPlan_endTime_CellEditor);
		//投放量
    	ICellEditor integerEditor = TableUtils.getCellIntegerNumberEdit();
    	tblMainPlan.getColumn("quantity").setEditor(integerEditor);
    	tblMainPlan.getColumn("quantity").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    	txtPlanYear.setPrecision(0);
    	//计划费用
    	ICellEditor bigDecimalEditor = CommerceHelper.getKDFormattedTextDecimalEditor();
    	tblMainPlan.getColumn("planCost").setEditor(bigDecimalEditor);
    	tblMainPlan.getColumn("planCost").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    }
    protected void attachListeners() {
    	addDataChangeListener(this.kDPlanMonth);
    	addDataChangeListener(this.txtPlanYear);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.kDPlanMonth);
		removeDataChangeListener(this.txtPlanYear);
	}
	protected void removeDataChangeListener(JComponent com) {
		EventListener[] listeners = null;	
  	
		if(com instanceof KDPromptBox){
			listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDPromptBox)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDFormattedTextField){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDFormattedTextField)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDDatePicker){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDDatePicker)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDComboBox){
    		listeners = com.getListeners(ItemListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDComboBox)com).removeItemListener((ItemListener)listeners[i]);
    		}
    	}else if(com instanceof KDSpinner){
    		listeners = com.getListeners(ChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDSpinner)com).removeChangeListener((ChangeListener)listeners[i]);
    		}
    	} 
		if(listeners!=null && listeners.length>0){
			listenersMap.put(com,listeners );
		}
    }
	protected void addDataChangeListener(JComponent com) {
    	EventListener[] listeners = (EventListener[] )listenersMap.get(com);
    	
    	if(listeners!=null && listeners.length>0){
	    	if(com instanceof KDPromptBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDPromptBox)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDFormattedTextField){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDFormattedTextField)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDDatePicker){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDDatePicker)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDComboBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDComboBox)com).addItemListener((ItemListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDSpinner){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDSpinner)com).addChangeListener((ChangeListener)listeners[i]);
	    		}
	    	}
    	}
    }
	protected ICoreBase getBizInterface() throws Exception {
		return EnterprisePlanFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}
	SellProjectInfo SellInfo;
	FullOrgUnitInfo fullInfo;
	/**
	 * 新增前 createNewData()
	 * **/
	protected IObjectValue createNewData() {
		sellProjectId = (String)getUIContext().get("sellProjectId");
		EnterprisePlanInfo info = getEnterpriseNew();
		if(sellProjectId != null){
			if(info==null){
				info = new EnterprisePlanInfo();
			}
			try {
				SellInfo = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProjectId));
				BOSUuid id = SellInfo.getOrgUnit().getId();
				fullInfo =FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo((new ObjectUuidPK(id)));
				info.setSellProject(SellInfo);
				info.setOrgUnit(fullInfo);
				if(info.getId()==null){
					now = SysUtil.getAppServerTime(null);
					Calendar cal = Calendar.getInstance();
					cal.setTime(now);
					info.setPlanMonth(cal.get(Calendar.MONTH)+1);
					info.setPlanYear(cal.get(Calendar.YEAR));
				}
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		if(info==null){
			info = new EnterprisePlanInfo();
		}
		String version = info.getVersion();
		info.setVersion("V"+getVersionInt(version)+".0");
		
		info.setName(info.getSellProject().getName()+info.getPlanYear()+"年"+info.getPlanMonth()+"月"+"营销费用计划"+info.getVersion());
		info.setId(null);
		info.setUseing(false);
		info.setNumber(null);
		info.setState(FDCBillStateEnum.SAVED);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setCreateTime(null);
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		
		EnterprisePlanInfo newInfo = getEnterpriseNewVersion();
		newInfo.setSellProject(SellInfo);
		newInfo.setOrgUnit(fullInfo);
		int v1 = getVersionInt(version);
		int v2 = getVersionInt(newInfo.getVersion());
		if(v1<v2){
			//最新版后还有未审批版本，新增直接转化为对未审批版本的修改
			info = newInfo;
			this.setOprtState(STATUS_EDIT);
			isWarning=true;
		}
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return info ;
	}
	/**
	 * 最新版本号
	 * */
	public int getVersionInt(String version){
		int v = 1;
		if(version!=null){
			int index = version.indexOf("V");
			v = new BigDecimal(version.substring(index+1)).intValue()+1;
		}
		return v;
	}
	/**
	 * 最新版本企划计划
	 * */
	public  EnterprisePlanInfo getEnterpriseNewVersion(){
		EnterprisePlanInfo info =new EnterprisePlanInfo();
		EntityViewInfo viewInfo = new EntityViewInfo();//建立一个实体封装对象。 
	    FilterInfo filter = new FilterInfo();//建立一个过滤对象。 
	    filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectId));
		viewInfo.setFilter(filter);
		viewInfo.setSelector(this.getSelectors());
		EnterprisePlanCollection coll =null;
		try {
			coll =EnterprisePlanFactory.getRemoteInstance().getEnterprisePlanCollection(viewInfo);
			CRMHelper.sortCollection(coll,new String[]{"planYear","planMonth","version"},false);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if(coll.size()>0){
			info = coll.get(0);
		}
		return info;
	}
	/**
	 * 启用的企划计划
	 * */
	public  EnterprisePlanInfo getEnterpriseNew(){
		EnterprisePlanInfo info =null;
		EntityViewInfo viewInfo = new EntityViewInfo();//建立一个实体封装对象。 
	    FilterInfo filter = new FilterInfo();//建立一个过滤对象。 
	    filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectId));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("useing", Boolean.TRUE));
		viewInfo.setFilter(filter);
		viewInfo.setSelector(this.getSelectors());
		EnterprisePlanCollection coll =null;
		try {
			coll =EnterprisePlanFactory.getRemoteInstance().getEnterprisePlanCollection(viewInfo);
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if(coll!=null && coll.size()>0){
			info = coll.get(0);
			EnterprisePlanEntryCollection rmcol=new EnterprisePlanEntryCollection();
			for(int i=0;i<info.getEntry().size();i++){
				EnterprisePlanEntryInfo entry=info.getEntry().get(i);
//				if(entry.getState().equals(ThemeEnum.Finish)||entry.getState().equals(ThemeEnum.Canceled)){
//					rmcol.add(entry);
//				}else{
					entry.setId(BOSUuid.create(entry.getBOSType()));
					for(int j=0;j<entry.getSellPlanEntry().size();j++){
						entry.getSellPlanEntry().get(j).setId(null);
					}
//				}
			}
			for(int i=0;i<rmcol.size();i++){
				info.getEntry().remove(rmcol.get(i));
			}
		}
		return info;
	}
	Date now = null;
	String sellProjectId = null;
	
	protected void tblMainPlan_tableClicked(KDTMouseEvent e) throws Exception {
		int rowIndex = tblMainPlan.getSelectManager().getActiveRowIndex();
		if(rowIndex==-1){
			return;
		}
		IRow row=tblMainPlan.getRow(rowIndex);
		ThemeEnum themState=(ThemeEnum) row.getCell("themeState").getValue();
		if(themState.equals(ThemeEnum.Canceled)){
        	this.btnChange.setText("激活主题");
        }else if(themState.equals(ThemeEnum.UnStarted)){
        	this.btnChange.setText("取消主题");
        }
		if(e.getColIndex() == tblMainPlan.getColumnIndex("enDescribe") )
		{
			if(ThemeEnum.Finish.equals(tblMainPlan.getRow(rowIndex).getCell("themeState").getValue())){
				this.showDialog(this, tblMainPlan, 250, 100, 2000,STATUS_VIEW);
			}else{
				this.showDialog(this, tblMainPlan, 250, 100, 2000,this.getOprtState());
			}
		}
	}
    
    public void showDialog(JComponent owner, KDTable table, int X, int Y, int len,String state)
    {
        int rowIndex = table.getSelectManager().getActiveRowIndex();
        int colIndex = table.getSelectManager().getActiveColumnIndex();
        ICell cell = table.getCell(rowIndex, colIndex);
        BasicView view = table.getViewManager().getView(5);
        Point p = view.getLocationOnScreen();
        Rectangle rect = view.getCellRectangle(rowIndex, colIndex);
        java.awt.Window parent = SwingUtilities.getWindowAncestor(owner);
        KDDetailedAreaDialog dialog;
        if(parent instanceof Dialog)
        {
            dialog = new KDDetailedAreaDialog((Dialog)parent, X, Y, true);
        }
        else
        if(parent instanceof Frame){
            dialog = new KDDetailedAreaDialog((Frame)parent, X, Y, true);
        }
        else{
            dialog = new KDDetailedAreaDialog(true);
        }
        String vals = null;
        if(cell.getValue()==null){
        	vals="";
        }else{
        	vals=cell.getValue().toString();
        }
        dialog.setData(vals);
        dialog.setPRENTE_X(p.x + rect.x + rect.width);
        dialog.setPRENTE_Y(p.y + rect.y);
        dialog.setMaxLength(len);
        if(!state.equals(STATUS_VIEW)){
        	dialog.setEditable(true);
        }else{
        	dialog.setEditable(false);
        }
        dialog.show();
        cell.setValue(dialog.getData());
        for(int i=0;i<tblMainPlan.getRowCount();i++){
			 IRow rmrow=tblMainPlan.getRow(i);
			 if(rmrow.getCell("id").getValue().equals(tblMainPlan.getRow(rowIndex).getCell("id").getValue())){
				 rmrow.getCell("enDescribe").setValue(tblMainPlan.getRow(rowIndex).getCell("enDescribe").getValue());
			 }
		 }
    }
    

    public static void setWrapFalse(ICell cell)
    {
        if(cell != null)
            cell.getStyleAttributes().setWrapText(true);
    }
    
    protected void setThisCellEditor(KDTable table, String colName, boolean isRequired, int maxLength)
    {
        KDDetailedArea area = new KDDetailedArea(280, 250);
        area.setRequired(isRequired);
        area.setEnabled(true);
        area.setMaxLength(maxLength);
        table.getColumn(colName).setEditor(new KDTDefaultCellEditor(area));
    }
}