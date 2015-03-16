/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitGroupInfo;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.assistant.client.F7MeasureUnitTreeDetailListUI;
import com.kingdee.eas.basedata.master.material.client.MaterialClientTools;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.cm.common.client.GeneralKDPromptSelectorAdaptor;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.TableUtils;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherCollection;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherContractEntryCollection;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherContractEntryFactory;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherContractEntryInfo;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherEntryCollection;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherEntryFactory;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherEntryInfo;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherFactory;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherInfo;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockFactory;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockInfo;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierPersonEntryInfo;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierSplAreaEntryCollection;
import com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierStockSupplierSplAreaEntryInfo;
import com.kingdee.eas.fdc.invite.markesupplier.ValueForMoneyEnum;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketAccreditationTypeInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketGradeSetUpFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketGradeSetUpInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketLevelSetUpFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSplAuditIndexInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateCollection;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateE1Collection;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateE1Factory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateE1Info;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierAppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.MarketSupplierBusinessModeFactory;
import com.kingdee.eas.fdc.invite.supplier.AppraiseTypeEnum;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.fdc.invite.supplier.client.SelectContractUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.hr.base.client.f7.F7ConfigManager;
import com.kingdee.eas.hr.base.util.HRParamUtil;
import com.kingdee.eas.hr.emp.client.EmployeeMultiF7PromptBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;

/**
 * output class name
 */
public class MarketSupplierReviewGatherEditUI extends AbstractMarketSupplierReviewGatherEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketSupplierReviewGatherEditUI.class);
    private List coloums=new ArrayList();
    private Set audittempid=new HashSet();
    private Set supplierEvaluationid=new HashSet();
    private KDLabelContainer bl;
    private KDDatePicker dabl;
    private MarketSupplierAppraiseTemplateCollection pcol=new MarketSupplierAppraiseTemplateCollection();
    /**
     * output class constructor
     */
    public MarketSupplierReviewGatherEditUI() throws Exception
    {
        super();
    }
    public void storeFields()
    {
    	editData.getContractEntry().clear();
    	if(UIRuleUtil.isNotNull(dabl.getValue())){
    		editData.setName(dabl.getText());
    	}
    	for(int i=0;i<this.kdtContractEntry.getRowCount();i++){
    		IRow row = this.kdtContractEntry.getRow(i);
    		MarketSupplierReviewGatherContractEntryInfo entry=new MarketSupplierReviewGatherContractEntryInfo();
    		if(row.getUserObject()!=null){
    			entry=(MarketSupplierReviewGatherContractEntryInfo) row.getUserObject();
    		}
    		entry.setSeq(i);
    		boolean ishasContract = ((Boolean) this.kdtContractEntry.getRow(i).getCell("isHasContract").getValue()).booleanValue();
    		entry.setIsHasContract(ishasContract);
    		if(ishasContract){
    			Object contract=this.kdtContractEntry.getRow(i).getCell("contract").getValue();
    			if(contract instanceof ContractBillInfo){
    				entry.setContractBill((ContractBillInfo) contract);
    			}
    		}else{
    			entry.setContract(UIRuleUtil.getString(this.kdtContractEntry.getRow(i).getCell("contract").getValue()));
    		}
    		if(UIRuleUtil.isNull(row.getCell("contractAmount").getValue())){
    			entry.setContractAmount(BigDecimal.ZERO);
    		}else{
    			entry.setContractAmount(UIRuleUtil.getBigDecimal(this.kdtContractEntry.getRow(i).getCell("contractAmount").getValue()));
    		}if(UIRuleUtil.isNull(row.getCell("contractPrice").getValue())){
    			entry.setContractPrice(BigDecimal.ZERO);
    		}else{
    			entry.setContractPrice(UIRuleUtil.getBigDecimal(this.kdtContractEntry.getRow(i).getCell("contractPrice").getValue()));
    		}if(UIRuleUtil.isNull(row.getCell("Quantity").getValue())){
    			entry.setQuantity(BigDecimal.ZERO);
    		}else{
    			entry.setQuantity(UIRuleUtil.getBigDecimal(this.kdtContractEntry.getRow(i).getCell("Quantity").getValue()));
    		}if(UIRuleUtil.isNotNull(row.getCell("ValueForMoney").getValue())){
    			entry.setValueForMoney((ValueForMoneyEnum) row.getCell("ValueForMoney").getValue());
    		}if(UIRuleUtil.isNotNull(row.getCell("uniy").getValue())){
    			entry.setUnit((MeasureUnitInfo)row.getCell("uniy").getValue());
    		}
    		editData.getContractEntry().add(entry);
    	}
        super.storeFields();
    }
    public void loadFields() {
    	detachListeners();
		super.loadFields();
		setSaveActionStatus();
		if(UIRuleUtil.isNotNull(editData.getName())){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
			try {
				this.dabl.setValue(df.parse(editData.getName()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		this.txtSplArea.setText(getSupplierSplArea(this.editData.getSupplier()));
		if(this.editData.getSupplier()!=null){
			this.txtSplArea.setText(getSupplierSplArea(this.editData.getSupplier()));
			if(this.editData.getSupplier().getInviteType()!=null){
				try {
					this.txtInviteType.setText(InviteTypeFactory.getRemoteInstance().getInviteTypeInfo(new ObjectUuidPK(this.editData.getSupplier().getInviteType().getId())).getName());
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
       	 	}
			if(this.editData.getSupplier().getSupplierBusinessMode()!=null){
     			try {
					this.txtBusinessMode.setText(MarketSupplierBusinessModeFactory.getRemoteInstance().getMarketSupplierBusinessModeInfo(new ObjectUuidPK(this.editData.getSupplier().getSupplierBusinessMode().getId())).getName());
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
		Object obj[] = new Object[kdtPerson.getRowCount()];
		for (int i = 0; i < kdtPerson.getRowCount(); i++) {
			obj[i] = kdtPerson.getCell(i, "person").getValue();
		}
//		this.loadEntry();
		this.loadContractEntry();
		setEntryScorePass();
		for (int i = 0; i < kdtContractEntry.getRowCount(); i++) {
			setEntryContractEditor(i);
		}
		prmtEntry.setValue(obj);
		
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
		for (int i = 0; i < kdtEntry.getRowCount(); i++) {
        	KDTableHelper.autoFitRowHeight(this.kdtEntry, i);
		}
	}
    protected void loadContractEntry(){
    	this.kdtContractEntry.removeRows();
    	MarketSupplierReviewGatherContractEntryCollection  col = this.editData.getContractEntry();
    	CRMHelper.sortCollection(col, "seq", true);
    	for(int i=0;i<col.size();i++){
    		MarketSupplierReviewGatherContractEntryInfo  entry =col.get(i);
			IRow row=this.kdtContractEntry.addRow();
			row.setUserObject(entry);
			row.getCell("isHasContract").setValue(Boolean.valueOf(entry.isIsHasContract()));
			if(entry.isIsHasContract()){
				row.getCell("contract").setValue(entry.getContractBill().getName());
			}else{
				row.getCell("contract").setValue(entry.getContract());
			}
			row.getCell("contractAmount").setValue(entry.getContractAmount());
			row.getCell("contractPrice").setValue(entry.getContractPrice());
			row.getCell("Quantity").setValue(entry.getQuantity());
			if(entry.getUnit()!=null){
				try {
					row.getCell("uniy").setValue(MeasureUnitFactory.getRemoteInstance().getMeasureUnitInfo(new ObjectUuidPK(entry.getUnit().getId())));
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
			row.getCell("ValueForMoney").setValue(entry.getValueForMoney());
		}
    }
    protected void storeContractEntry(){
    }
    protected void clearColoums(List list){
    	for(int i=0;i<coloums.size();i++){
    		this.kdtEntry.removeColumn(this.kdtEntry.getColumnIndex(coloums.get(i).toString()));
    	}
    	coloums.clear();
    }
    protected void loadEntry(){
    	MarketSupplierReviewGatherEntryCollection col=this.editData.getEntry();
    	this.kdtEntry.removeRows();
    	this.clearColoums(coloums);
    	Map id=new HashMap();
    	List objectEntry=new ArrayList();
    	for(int i=0;i<col.size();i++){
    		MarketSupplierReviewGatherEntryInfo entry=col.get(i);
    		if(id.get(entry.getAuditTemplate().getTemplateEntry().getSplAuditIndex().getId().toString())==null){
    			List value=new ArrayList();
    			value.add(entry);
    			id.put(entry.getAuditTemplate().getTemplateEntry().getSplAuditIndex().getId().toString(), value);
    		}else{
    			((List)id.get(entry.getAuditTemplate().getTemplateEntry().getSplAuditIndex().getId().toString())).add(entry);
    		}
    	}
        Iterator it=id.entrySet().iterator();  
        while(it.hasNext()){
        	Map.Entry entry = (Map.Entry) it.next();
        	IRow row=this.kdtEntry.addRow();
        	List value=(ArrayList)entry.getValue();
        	for(int i=0;i<value.size();i++){
        		MarketSupplierReviewGatherEntryInfo srgentry=(MarketSupplierReviewGatherEntryInfo) value.get(i);
        		if(!coloums.contains(srgentry.getAuditTemplate().getAuditPerson().getId().toString())){
        			IColumn coloum=null;
        			for(int j=0;j<coloums.size();j++){
        				if(srgentry.getAuditTemplate().getAuditDept().getId().toString().equals(this.kdtEntry.getColumn(coloums.get(j).toString()).getExpressions())){
        					coloum=this.kdtEntry.addColumn(this.kdtEntry.getColumnIndex(coloums.get(j).toString())+1);
        					break;
        				}
        			}
        			if(coloum==null){
        				coloum=this.kdtEntry.addColumn(this.kdtEntry.getColumnCount()-1);
        			}
        			coloum.setKey(srgentry.getAuditTemplate().getAuditPerson().getId().toString());
        			coloum.setExpressions(srgentry.getAuditTemplate().getAuditDept().getId().toString());
        			coloum.getStyleAttributes().setLocked(true);
        			this.kdtEntry.getHeadRow(0).getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).setValue(srgentry.getAuditTemplate().getAuditDept().getName());
        			if(this.kdtEntry.getHeadRow(1)!=null){
        				this.kdtEntry.getHeadRow(1).getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).setValue(srgentry.getAuditTemplate().getAuditPerson().getName());
        			}else{
        				this.kdtEntry.addHeadRow().getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).setValue(srgentry.getAuditTemplate().getAuditPerson().getName());
        			}
        			
        			objectEntry.add(srgentry.getAuditTemplate().getAuditPerson());
        			coloums.add(srgentry.getAuditTemplate().getAuditPerson().getId().toString());
        		}
        		if(AppraiseTypeEnum.PASS.equals(srgentry.getAuditTemplate().getTemplateEntry().getAppraiseType())){
    				row.getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).setValue(srgentry.getAuditTemplate().getIsPass().getAlias());
        			row.getCell("score").getStyleAttributes().setLocked(true);
        		}else{
        			row.getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).getStyleAttributes().setNumberFormat("#0.00");
    				row.getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
    				row.getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).setValue(srgentry.getAuditTemplate().getScore());
        		}
        		row.setUserObject(srgentry.getAuditTemplate().getTemplateEntry());
        		row.getCell(srgentry.getAuditTemplate().getAuditPerson().getId().toString()).setUserObject(srgentry);
        		row.getCell("guideType").setValue(srgentry.getAuditTemplate().getTemplateEntry().getSplAuditIndex().getIndexGroup().getName());
        		row.getCell("guideName").setValue(srgentry.getAuditTemplate().getTemplateEntry().getSplAuditIndex().getName());
        		row.getCell("fullNum").setValue(srgentry.getAuditTemplate().getTemplateEntry().getSplAuditIndex().getFullMarkStandard());
        		row.getCell("weight").setValue(srgentry.getAuditTemplate().getTemplateEntry().getWeight());
        		if(row.getCell("remark").getValue()!=null){
        			row.getCell("remark").setValue(row.getCell("remark").getValue().toString()+"\n"+srgentry.getAuditTemplate().getAuditPerson().getName()+":"+srgentry.getAuditTemplate().getRemark());
        		}else{
        			row.getCell("remark").setValue(srgentry.getAuditTemplate().getAuditPerson().getName()+":"+srgentry.getAuditTemplate().getRemark());
        		}
        		row.getCell("score").setValue(srgentry.getScore());
        	}
        }
        List sort=new ArrayList();
        for(int i=0;i<this.kdtEntry.getRowCount();i++){
        	if(((MarketSupplierReviewGatherEntryInfo)this.kdtEntry.getRow(i).getUserObject()).getSeq()>this.kdtEntry.getRowCount()){
        		sort.add(this.kdtEntry.getRow(i).clone());
        		continue;
        	}
        	for(int j=0;j<this.kdtEntry.getRowCount();j++){
        		if(i+1==((MarketSupplierReviewGatherEntryInfo)this.kdtEntry.getRow(j).getUserObject()).getSeq()){
            		sort.add(this.kdtEntry.getRow(j).clone());
            		continue;
            	}
        	}
        }
        this.kdtEntry.removeRows();
        for(int i=0;i<sort.size();i++){
        	IRow row=this.kdtEntry.addRow();
        	IRow sortRow=(IRow) sort.get(i);
        	row.setUserObject(sortRow.getUserObject());
        	for(int j=0;j<this.kdtEntry.getColumnCount();j++){
        		row.getCell(j).setValue(sortRow.getCell(j).getValue());
        		row.getCell(j).setUserObject(sortRow.getCell(j).getUserObject());
        		if(this.kdtEntry.getColumnKey(j).equals("remark")&&row.getCell("remark").getValue()!=null){
        			int height=row.getCell("remark").getValue().toString().split(":").length;
        			if(height>1){
        				row.setHeight(row.getHeight()*(height-1));
        			}
        		}
        	}
//        	SupplierGuideEntryInfo sg=(SupplierGuideEntryInfo) row.getUserObject();
//        	if(AppraiseTypeEnum.PASS.equals(sg.getAppraiseType())){
//        		row.getCell("score").getStyleAttributes().setLocked(true);
//			}
        }
        getFootRow(this.kdtEntry,new String[]{"weight"});
        BigDecimal score =MarketSupplierEvaluationEditUI.getTotalScore(this.kdtEntry,"score","weight");
		this.kdtEntry.getFootManager().getFootRow(0).getCell("score").setValue(score);
		
        if(coloums.size()>0){
        	int merger=0;
        	for(int i=0;i<this.kdtEntry.getColumnCount();i++){
    			if(i>0){
    				boolean isMerge=false;
    				if(this.kdtEntry.getHeadRow(0).getCell(i).getValue().toString().equals(this.kdtEntry.getHeadRow(0).getCell(i-1).getValue().toString())){
    					isMerge=true;
    					merger=i;
    				}
    				if(isMerge){
    					this.kdtEntry.getHeadMergeManager().mergeBlock(0, i-1, 0, merger);
    				}
    			}
    		}
        	this.kdtEntry.getHeadMergeManager().mergeBlock(0, this.kdtEntry.getColumnIndex("guideType"), 1, this.kdtEntry.getColumnIndex("guideType"));
        	this.kdtEntry.getHeadMergeManager().mergeBlock(0, this.kdtEntry.getColumnIndex("guideName"), 1, this.kdtEntry.getColumnIndex("guideName"));
        	this.kdtEntry.getHeadMergeManager().mergeBlock(0, this.kdtEntry.getColumnIndex("fullNum"), 1, this.kdtEntry.getColumnIndex("fullNum"));
        	this.kdtEntry.getHeadMergeManager().mergeBlock(0, this.kdtEntry.getColumnIndex("weight"), 1, this.kdtEntry.getColumnIndex("weight"));
        	this.kdtEntry.getHeadMergeManager().mergeBlock(0, this.kdtEntry.getColumnIndex("remark"), 1, this.kdtEntry.getColumnIndex("remark"));
        	this.kdtEntry.getHeadMergeManager().mergeBlock(0, this.kdtEntry.getColumnIndex("score"), 1, this.kdtEntry.getColumnIndex("score"));
        
			for(int i=0;i<coloums.size();i++){
				this.kdtEntry.getColumn(coloums.get(i).toString()).getStyleAttributes().setNumberFormat("#0.00");
				this.kdtEntry.getColumn(coloums.get(i).toString()).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			
				this.kdtEntry.getFootManager().getFootRow(0).getCell(coloums.get(i).toString()).setValue(MarketSupplierEvaluationEditUI.getTotalScore(this.kdtEntry,coloums.get(i).toString(),"weight"));
			}
        }
		
        if(objectEntry.size()>0){
    		this.prmtEntry.setValue(objectEntry.toArray());
    	}else{
    		this.prmtEntry.setValue(null);
    	}
    }
    public void getFootRow(KDTable tblMain,String[] columnName){
		IRow footRow = null;
        KDTFootManager footRowManager = tblMain.getFootManager();
        if(footRowManager == null)
        {
            String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
            footRowManager = new KDTFootManager(tblMain);
            footRowManager.addFootView();
            tblMain.setFootManager(footRowManager);
            footRow = footRowManager.addFootRow(0);
            footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
            tblMain.getIndexColumn().setWidthAdjustMode((short)1);
            tblMain.getIndexColumn().setWidth(30);
            footRowManager.addIndexText(0, total);
        } else
        {
            footRow = footRowManager.getFootRow(0);
        }
        int columnCount = tblMain.getColumnCount();
        for(int c = 0; c < columnCount; c++)
        {
            String fieldName = tblMain.getColumn(c).getKey();
            for(int i = 0; i < columnName.length; i++)
            {
                String colName = (String)columnName[i];
                if(colName.equalsIgnoreCase(fieldName))
                {
                    ICell cell = footRow.getCell(c);
                    cell.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
                    cell.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
                    cell.getStyleAttributes().setFontColor(java.awt.Color.BLACK);
                    cell.setValue(getColumnValueSum(tblMain,colName));
                }
            }
        }
        footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
	}
    public BigDecimal getColumnValueSum(KDTable table,String columnName) {
    	BigDecimal sum = new BigDecimal(0);
        for(int i=0;i<table.getRowCount();i++){
        	if(table.getRow(i).getCell(columnName).getValue()!=null ){
        		if( table.getRow(i).getCell(columnName).getValue() instanceof BigDecimal){
        			sum = sum.add((BigDecimal)table.getRow(i).getCell(columnName).getValue());
        		}else if(table.getRow(i).getCell(columnName).getValue() instanceof Integer){
            		String value = table.getRow(i).getCell(columnName).getValue().toString();
            		sum = sum.add(new BigDecimal(value));
            	}
        	}
        }
        return sum;
    }
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sel=super.getSelectors();
    	sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("state");
    	sel.add("name");
    	sel.add("supplier.purchaseOrgUnit.*");
    	sel.add("supplier.inviteType.*");
    	sel.add("supplier.grade.*");
    	sel.add("supplier.isPass");
    	sel.add("supplier.storageDate");
    	sel.add("supplier.storageNumber");
    	sel.add("srcIsPass");
    	sel.add("entry.*");
    	sel.add("entry.auditTemplate.*");
    	sel.add("entry.auditTemplate.templateEntry.*");
    	sel.add("entry.auditTemplate.auditPerson.*");
    	sel.add("entry.auditTemplate.auditDept.*");
    	sel.add("entry.auditTemplate.templateEntry.*");
    	sel.add("entry.auditTemplate.templateEntry.splAuditIndex.*");
    	sel.add("entry.auditTemplate.templateEntry.splAuditIndex.*");
    	sel.add("entry.auditTemplate.templateEntry.splAuditIndex.indexGroup.*");
    	
    	sel.add("evaluationEntry.*");
    	sel.add("evaluationEntry.supplierEvaluation.id");
    	
    	sel.add("contractEntry.*");
    	sel.add("contractEntry.contractBill.*");
		return sel;
	}
	protected String getSupplierSplArea(MarketSupplierStockInfo info){
		if(info==null) return null;
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("supplierSplAreaEntry.fdcSplArea.*");
		try {
			info=MarketSupplierStockFactory.getRemoteInstance().getMarketSupplierStockInfo(new ObjectUuidPK(info.getId()), sel);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		String text="";
		MarketSupplierStockSupplierSplAreaEntryCollection col=info.getSupplierSplAreaEntry();
		for(int i=0;i<col.size();i++) {
			MarketSupplierStockSupplierSplAreaEntryInfo entry = col.get(i);
			if(i==col.size()-1){
				text=text+entry.getFdcSplArea().getName();
			}else{
				text=text+entry.getFdcSplArea().getName()+";";
			}
		}
    	return text;
    }
	public void onLoad() throws Exception {
		initTable();
		this.contBusinessMode.setVisible(false);
		this.contContractor.setVisible(false);
		this.contManager.setVisible(false);
		this.contContractorPhone.setVisible(false);
		this.contManagerPhone.setVisible(false);
		
		this.kDPanel2.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)),"供应商评审"));
        
		
		bl = new KDLabelContainer();
		dabl = new KDDatePicker();
		prmtEntry.setEnabledMultiSelection(true);
		this.kdtContractEntry.checkParsed();
		this.kdtEntry.checkParsed();
		super.onLoad();
		this.prmtSupplier.setQueryInfo("com.kingdee.eas.fdc.invite.markesupplier.app.MarketSupplierStockQuery");
		this.prmtEvaluationType.setQueryInfo("com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.MarketAccreditationTypeQuery");
		this.prmtTemplate.setQueryInfo("com.kingdee.eas.fdc.invite.markesupplier.app.F7MarketSupplierAppraiseTemplateQuery");
		this.prmtGrade.setQueryInfo("com.kingdee.eas.fdc.invite.markesupplier.marketbase.app.MarketGradeSetUpQuery");
		this.btnSelectContract.setVisible(false);
		this.contLYHScore.setVisible(false);
		this.contLYGCRate.setVisible(false);
		this.contLYHRate.setVisible(false);
		this.contLYGCScore.setVisible(false);
		this.contAmount.setVisible(false);
		this.cbIsOver.setVisible(false);
		this.contSrcLevel.setVisible(false);
		this.contLevel.setVisible(false);
		this.prmtEntry.setCommitFormat("$name$");		
        this.prmtEntry.setEditFormat("$name$");		
        this.prmtEntry.setDisplayFormat("$name$");		
        this.prmtEntry.setEnabledMultiSelection(true);
		this.kdtPerson_detailPanel.setVisible(false);
		this.btnAddRow.setIcon( EASResource.getIcon("imgTbtn_addline"));
		this.btninsrtRow.setIcon( EASResource.getIcon("imgTbtn_insert"));
		this.btnRemoveRow.setIcon( EASResource.getIcon("imgTbtn_deleteline"));
		EmployeeMultiF7PromptBox person = new EmployeeMultiF7PromptBox(this);
		person.setIsSingleSelect(false);
		person.showNoPositionPerson(false);
		person.setIsShowAllAdmin(true);
		prmtEntry.setSelector(person);
		this.kdtEntry.getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("remark").getStyleAttributes().setLocked(false);
		contEntry.setBoundLabelText("参与评审人员");
		initControl();
		this.setToolTipText("供应商评审");
		this.setUITitle("供应商评审");
		this.kdtContractEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                	kdtContractEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
		this.txtLinkJob.setEnabled(true);
		this.txtLinkPerson.setEnabled(true);
		this.txtLinkPhone.setEnabled(true);
		this.txtContractor.setEnabled(true);
		this.txtContractorPhone.setEnabled(true);
		this.txtManager.setEnabled(true);
		this.txtManagerPhone.setEnabled(true);
		this.txtLinkPerson.setRequired(true);
		this.txtLinkPhone.setRequired(true);
		this.txtContractor.setRequired(true);
		this.txtContractorPhone.setRequired(true);
		this.txtManager.setRequired(true);
		this.txtManagerPhone.setRequired(true);
		//补录日期
		bl.setBoundLabelText("补录数据入库日期");		
		bl.setBoundLabelLength(120);		
		bl.setBoundLabelUnderline(true);
		bl.setBoundEditor(dabl);
		bl.getBoundLabel().setForeground(Color.RED);
		bl.setBounds(new Rectangle(645, 60, 270, 19));
        kDPanel1.add(bl, new KDLayout.Constraints(645, 60, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        bl.setBounds(new Rectangle(645, 80, 115, 19));
        kDPanel1.add(dabl, new KDLayout.Constraints(645, 80, 115, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE));
		
        prmtEvaluationType_dataChanged(null);
	}
	protected void btnAddRow_actionPerformed(ActionEvent e) throws Exception {
		this.kdtContractEntry.addRow();
		kdtContractEntry.getCell(kdtContractEntry.getRowCount()-1, "isHasContract").setValue(false);
	}
	protected void btninsrtRow_actionPerformed(ActionEvent e) throws Exception {
		int index = this.kdtContractEntry.getSelectManager().getActiveRowIndex();
		if(index ==-1||index==0){
			this.kdtContractEntry.addRow(0);
			kdtContractEntry.getCell(0, "isHasContract").setValue(false);
		}else{
			this.kdtContractEntry.addRow(index);
			kdtContractEntry.getCell(0, "isHasContract").setValue(false);
		}
	}
	protected void btnRemoveRow_actionPerformed(ActionEvent e) throws Exception {
		int index = this.kdtContractEntry.getSelectManager().getActiveRowIndex();
		if(index ==-1){
			return;
		}
		this.kdtContractEntry.removeRow(index);
	}
	protected void initTable(){
		this.kdtEntry.checkParsed();
		KDFormattedTextField txtWeight = new KDFormattedTextField();
		txtWeight.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtWeight.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtWeight.setPrecision(2);
		txtWeight.setMaximumValue(new BigDecimal(5));
		txtWeight.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor weight = new KDTDefaultCellEditor(txtWeight);
		this.kdtEntry.getColumn("score").setEditor(weight);
		this.kdtEntry.getColumn("score").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtEntry.getColumn("score").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
	 	this.kdtEntry.getStyleAttributes().setLocked(true);
	 	this.kdtEntry.getColumn("score").getStyleAttributes().setLocked(false);
	 	
	 	this.kdtEntry.getColumn("weight").getStyleAttributes().setNumberFormat("#0.00");
	 	this.kdtEntry.getColumn("weight").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	 	
		KDTextArea indexValue_TextField = new KDTextArea();
		indexValue_TextField.setAutoAdjustCaret(true);
//		indexValue_TextField.setLineWrap(true);
		KDTDefaultCellEditor indexValue_CellEditor = new KDTDefaultCellEditor(indexValue_TextField);
		this.kdtEntry.getColumn("remark").getStyleAttributes().setWrapText(true);
		this.kdtEntry.getColumn("fullNum").getStyleAttributes().setWrapText(true);
		this.kdtEntry.getColumn("remark").setEditor(indexValue_CellEditor);
		this.kdtEntry.getColumn("remark").setRequired(true);
		this.kdtContractEntry.getColumn("contractAmount").setRequired(true);
		this.kdtContractEntry.getColumn("contractPrice").setRequired(true);
		this.kdtContractEntry.getColumn("Quantity").setRequired(true);
		this.kdtContractEntry.getColumn("uniy").setRequired(true);
		this.kdtContractEntry.getColumn("ValueForMoney").setRequired(true);
		
//		this.kdtContractEntry.getStyleAttributes().setLocked(true);
		
		
		KDFormattedTextField kdtEntry_weight_TextField = new KDFormattedTextField();
        kdtEntry_weight_TextField.setVisible(true);
        kdtEntry_weight_TextField.setEditable(true);
        kdtEntry_weight_TextField.setHorizontalAlignment(2);
        kdtEntry_weight_TextField.setDataType(1);
        kdtEntry_weight_TextField.setMinimumValue(new java.math.BigDecimal("-1.0E18"));
        kdtEntry_weight_TextField.setMaximumValue(new java.math.BigDecimal("1.0E18"));
        kdtEntry_weight_TextField.setPrecision(2);
        KDTDefaultCellEditor kdtEntry_weight_CellEditor = new KDTDefaultCellEditor(kdtEntry_weight_TextField);
        this.kdtContractEntry.getColumn("contractAmount").setEditor(kdtEntry_weight_CellEditor);
        this.kdtContractEntry.getColumn("contractPrice").setEditor(kdtEntry_weight_CellEditor);
        this.kdtContractEntry.getColumn("Quantity").setEditor(kdtEntry_weight_CellEditor);
        KDComboBox kdtE1_ScoreType_ComboBox = new KDComboBox();
        kdtE1_ScoreType_ComboBox.setVisible(true);
        kdtE1_ScoreType_ComboBox.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.invite.markesupplier.ValueForMoneyEnum").toArray());
        KDTDefaultCellEditor kdtE1_ScoreType_CellEditor = new KDTDefaultCellEditor(kdtE1_ScoreType_ComboBox);
        this.kdtContractEntry.getColumn("ValueForMoney").setEditor(kdtE1_ScoreType_CellEditor);
		KDCheckBox hit = new KDCheckBox();
		hit.setSelected(false);
 		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(hit);
 		KDBizPromptBox  unit = new KDBizPromptBox();
 		MaterialClientTools.setMeasureUnitF7(this, unit);
 		unit.setCommitFormat("$name$");		
 		unit.setEditFormat("$number$");		
 		unit.setDisplayFormat("$name$");	
 		this.kdtContractEntry.getColumn("isHasContract").setEditor(editor);
 		
 		this.kdtContractEntry.getColumn("uniy").setEditor(new KDTDefaultCellEditor(unit));
 		
 		this.kdtContractEntry.getColumn("contractAmount").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtContractEntry.getColumn("contractAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		this.kdtContractEntry.getColumn("manager").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.kdtContractEntry.getColumn("managerPhone").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		this.kdtContractEntry.getColumn("manager").getStyleAttributes().setLocked(false);
		this.kdtContractEntry.getColumn("managerPhone").getStyleAttributes().setLocked(false);
		
		
        btnAddRow.setBounds(new Rectangle(830, 13, 50, 19));
        kDPanel4.add(btnAddRow, new KDLayout.Constraints(830, 13, 50, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btninsrtRow.setBounds(new Rectangle(881, 13, 50, 19));
        kDPanel4.add(btninsrtRow, new KDLayout.Constraints(881, 13, 50, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRemoveRow.setBounds(new Rectangle(932, 13, 50, 19));
        kDPanel4.add(btnRemoveRow, new KDLayout.Constraints(932, 13, 50, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
      
	}
	protected void setF7Filter(MarketSupplierStockInfo supplier,MarketAccreditationTypeInfo type,MarketSupplierAppraiseTemplateInfo template) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		Set org=new HashSet();
		if(editData.getOrgUnit()!=null){
			org.add(editData.getOrgUnit().getId().toString());
		}
		org.add(OrgConstants.DEF_CU_ID);
		filter.getFilterItems().add(new FilterItemInfo("state",Integer.valueOf(SupplierStateEnum.APPROVE_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("purchaseOrgUnit.id",org,CompareType.INCLUDE));
		view.setFilter(filter);
		this.prmtSupplier.setEntityViewInfo(view);
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnable",Boolean.TRUE));
		view.setFilter(filter);
		this.prmtEvaluationType.setEntityViewInfo(view);
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		view.setFilter(filter);
		this.prmtGrade.setEntityViewInfo(view);
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnable",Boolean.TRUE));
		if(this.prmtEvaluationType.getValue()!=null){
			filter.getFilterItems().add(new FilterItemInfo("AccreditationType.id",((MarketAccreditationTypeInfo)this.prmtEvaluationType.getValue()).getId()));
		}
		view.setFilter(filter);
		this.prmtTemplate.setEntityViewInfo(view);
		if(supplier!=null&&type!=null&&template!=null){
			prmtTemplate.setEnabled(true);
			prmtTemplate.setEditable(true);
		}
	}
	protected void btnSelectContract_actionPerformed(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSupplier);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtEvaluationType);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtTemplate);
		
		this.storeFields();
		
		UIContext uiContext = new UIContext(this);
		
		Boolean isCancel=Boolean.FALSE;
		uiContext.put("supplier", this.prmtSupplier.getValue());
		uiContext.put("type", this.prmtEvaluationType.getValue());
		uiContext.put("template", this.prmtTemplate.getValue());
		uiContext.put("editData", this.editData);
		uiContext.put("table", this.kdtContractEntry);
		uiContext.put("orgUnit", this.editData.getOrgUnit());
		uiContext.put("isCancel",isCancel);
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(SelectContractUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
		
		
//		if(!(Boolean) this.editData.get("isCancel")){
//			this.prmtEntry.setValue(null);
//			
//			this.loadContractEntry();
//			
//			setF7Filter((MarketSupplierStockInfo)this.prmtSupplier.getValue(),(MarketAccreditationTypeInfo)this.prmtEvaluationType.getValue(),(MarketSupplierAppraiseTemplateInfo)this.prmtTemplate.getValue());
//		    setComEnable();
//		}
	}
	protected void setComEnable(){
		MarketSupplierStockInfo supplier=(MarketSupplierStockInfo) this.prmtSupplier.getValue();
		MarketAccreditationTypeInfo type=(MarketAccreditationTypeInfo) this.prmtEvaluationType.getValue();
		MarketSupplierAppraiseTemplateInfo template=(MarketSupplierAppraiseTemplateInfo)this.prmtTemplate.getValue();
		if(type!=null&&supplier!=null&&template!=null){
			if(type.getNumber().equals("002")){
//				this.pkStorageDate.setEnabled(true);
			}else{
				this.pkStorageDate.setEnabled(false);
			}
			if(type.getNumber().equals("006")||type.getNumber().equals("007")){
				this.cbIsOver.setEnabled(true);
				if(this.cbIsOver.isSelected()){
					this.prmtLevel.setEnabled(false);
				}else{
					this.prmtLevel.setEnabled(true);
				}
			}else{
				this.cbIsOver.setEnabled(false);
				this.prmtLevel.setEnabled(false);
			}
			if(type.getNumber().equals("002")||type.getNumber().equals("003")||
					type.getNumber().equals("004")||type.getNumber().equals("005")){
        		this.prmtGrade.setEnabled(true);
				this.cbIsPass.setEnabled(true);
			}else{
				this.prmtGrade.setEnabled(false);
				this.cbIsPass.setEnabled(false);
			}
        	if(type.getNumber().equals("003")||type.getNumber().equals("004")||
					type.getNumber().equals("005")){
        		if(this.editData.getContractEntry().size()>0){
    				this.prmtEntry.setEnabled(true);
    			}else{
    				this.prmtEntry.setEnabled(true);
    			}
        		if(this.oprtState.equals(OprtState.VIEW)){
					this.btnSelectContract.setEnabled(false);
				}else{
					this.btnSelectContract.setEnabled(true);
				}
        	}else{
        		this.prmtEntry.setEnabled(true);
        		this.btnSelectContract.setEnabled(false);
        	}
        }else{
        	if(type!=null&&supplier!=null){
        		this.prmtTemplate.setEnabled(true);
        	}else{
        		this.prmtTemplate.setEnabled(false);
        	}
        	this.pkStorageDate.setEnabled(false);
        	
        	this.cbIsOver.setEnabled(false);
			this.prmtLevel.setEnabled(false);
			
			this.prmtGrade.setEnabled(false);
			this.cbIsPass.setEnabled(false);
			
        	this.prmtEntry.setEnabled(true);
        	this.btnSelectContract.setEnabled(false);
        }
	}
	protected void initControl() {
		try {
			setF7Filter((MarketSupplierStockInfo)this.prmtSupplier.getValue(),(MarketAccreditationTypeInfo)this.prmtEvaluationType.getValue(),(MarketSupplierAppraiseTemplateInfo)this.prmtTemplate.getValue());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.btnCalculator.setVisible(true);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		MarketAccreditationTypeInfo info=(MarketAccreditationTypeInfo) this.prmtEvaluationType.getValue();
		if(info!=null&&(info.getNumber().equals("004"))){
			this.txtLYHRate.setEnabled(true);
			this.txtLYGCRate.setEnabled(true);
			this.txtLYGCScore.setEnabled(true);
    		this.txtLYHRate.setRequired(true);
    		this.txtLYGCRate.setRequired(true);
    		this.txtLYGCScore.setRequired(true);
	    }else{
	    	this.txtLYHRate.setEnabled(false);
			this.txtLYGCRate.setEnabled(false);
			this.txtLYGCScore.setEnabled(false);
			this.txtLYHRate.setRequired(false);
    		this.txtLYGCRate.setRequired(false);
    		this.txtLYGCScore.setRequired(false);
		}
		this.txtLYGCScore.setNegatived(false);
		this.txtLYGCScore.setMinimumValue(new BigDecimal(0));
		this.txtLYGCScore.setMaximumValue(new BigDecimal(100));
		this.txtLYHRate.setNegatived(false);
		this.txtLYHRate.setMinimumValue(new BigDecimal(0));
		this.txtLYHRate.setMaximumValue(new BigDecimal(100));
		this.txtLYGCRate.setNegatived(false);
		this.txtLYGCRate.setMinimumValue(new BigDecimal(0));
		this.txtLYGCRate.setMaximumValue(new BigDecimal(100));
		
		setComEnable();
		
		if (this.getOprtState().equals(OprtState.VIEW)) {
			this.kdtContractEntry.setEditable(true);
			
			this.kdtContractEntry.getColumn("isHasContract").getStyleAttributes().setLocked(true);
	 		this.kdtContractEntry.getColumn("contract").getStyleAttributes().setLocked(true);
			this.kdtContractEntry.getColumn("contractAmount").getStyleAttributes().setLocked(true);
		}
		Color color = Color.BLUE;
		this.txtSupplierName.setForeground(color);
	}
	protected void prmtSupplier_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception{
		 boolean isChanged = true;
		 isChanged = BizCollUtil.isF7ValueChanged(e);
         if(!isChanged){
        	 return;
         }
         MarketSupplierStockInfo infos=(MarketSupplierStockInfo) this.prmtSupplier.getValue();
         if(infos==null){
        	 this.txtSplArea.setText(null);
     		 this.txtInviteType.setText(null);
     		 this.txtSupplierName.setText(null);
    		 this.txtBusinessMode.setText(null);
    		 this.prmtSrcGrade.setValue(null);
    		 this.prmtSrcLevel.setValue(null);
    		 
    		 this.txtPartProject.setText(null);
     		 this.txtLinkPerson.setText(null);
     		 this.txtLinkPhone.setText(null);
     		 this.txtLinkJob.setText(null);
     		 this.txtContractor.setText(null);
     		 this.txtContractorPhone.setText(null);
     		 this.txtManager.setText(null);
     		 this.txtManagerPhone.setText(null);
     		 
     		 this.txtStorageNumber.setText(null);
     		 this.pkStorageDate.setValue(null);
     		 
    		 this.editData.setSrcIsPass(null);
         }else{
        	 this.prmtTemplate.setValue(null);
        	 this.prmtEvaluationType.setValue(null);
        	 this.kdtEntry.removeRows();
        	 this.kdtContractEntry.removeRows();
        	 MarketSupplierStockInfo info = MarketSupplierStockFactory.getRemoteInstance().getMarketSupplierStockInfo(new ObjectUuidPK(infos.getId()));
        	 this.txtSplArea.setText(getSupplierSplArea(info));
        	 if(info.getInviteType()!=null){
        		 this.txtInviteType.setText(InviteTypeFactory.getRemoteInstance().getInviteTypeInfo(new ObjectUuidPK(info.getInviteType().getId())).getName());
        	 }
     		 this.txtSupplierName.setText(info.getName());
     		 if(info.getSupplierBusinessMode()!=null){
     			this.txtBusinessMode.setText(MarketSupplierBusinessModeFactory.getRemoteInstance().getMarketSupplierBusinessModeInfo(new ObjectUuidPK(info.getSupplierBusinessMode().getId())).getName());
     		 }
     		 if(info.getGrade()!=null){
    			 this.prmtSrcGrade.setValue(MarketGradeSetUpFactory.getRemoteInstance().getMarketGradeSetUpInfo(new ObjectUuidPK(info.getGrade().getId())));
    		 }
     		 if(info.getLevel()!=null){
     			 this.prmtSrcLevel.setValue(MarketLevelSetUpFactory.getRemoteInstance().getMarketLevelSetUpInfo(new ObjectUuidPK(info.getLevel().getId())));
     		 }
     		
     		 this.txtPartProject.setText(info.getPartProject());
     		 this.txtLinkPerson.setText(info.getAuthorizePerson());
     		 this.txtLinkPhone.setText(info.getAuthorizePhone());
     		 this.txtLinkJob.setText(info.getAuthorizeJob());
     		 this.txtContractor.setText(info.getContractor());
     		 this.txtContractorPhone.setText(info.getContractorPhone());
     		 this.txtManager.setText(info.getManager());
     		 this.txtManagerPhone.setText(info.getManagerPhone());
     		 
     		 this.txtStorageNumber.setText(info.getStorageNumber());
     		 this.pkStorageDate.setValue(info.getStorageDate());
     		 
     		 this.editData.setSrcIsPass(info.getIsPass());
         }
         setF7Filter((MarketSupplierStockInfo)this.prmtSupplier.getValue(),(MarketAccreditationTypeInfo)this.prmtEvaluationType.getValue(),(MarketSupplierAppraiseTemplateInfo)this.prmtTemplate.getValue());
         
         setComEnable();
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		prmtEvaluationType_dataChanged(null);
	}
	protected void prmtEvaluationType_dataChanged(DataChangeEvent e) throws Exception {
		boolean isChanged = true;
		MarketAccreditationTypeInfo info=(MarketAccreditationTypeInfo) this.prmtEvaluationType.getValue();
		if(e!=null){
			isChanged = BizCollUtil.isF7ValueChanged(e);
			if(!isChanged){
				return;
			}
			this.prmtTemplate.setValue(null);
			if(this.prmtSupplier.getValue()!=null){
				MarketSupplierStockInfo Stockinfo = (MarketSupplierStockInfo)this.prmtSupplier.getValue();
				String oql = null;
				if(editData.getId()==null)
					oql = "select id where Supplier.id = '"+Stockinfo.getId().toString()+"' and evaluationType.number='002'";
				else
					oql = "select id where Supplier.id = '"+Stockinfo.getId().toString()+"' and evaluationType.number='002' and id <> '"+editData.getId().toString()+"'";
				MarketSupplierReviewGatherCollection coll = MarketSupplierReviewGatherFactory.getRemoteInstance().getMarketSupplierReviewGatherCollection(oql);
				if(info==null){return;};
				if(info.getNumber().equals("003")&& coll.size()<1){
					MsgBox.showInfo("当前供应商未做考察评审，请先做考察评审！");
					this.prmtEvaluationType.setValue(null);
					SysUtil.abort();
				}
			}
		}
		
        if(this.prmtSupplier.getValue()!=null&&this.prmtEvaluationType.getValue()!=null){
//			if(this.prmtTemplate.getValue()!=null){
//				if(!((MarketAccreditationTypeInfo)this.prmtEvaluationType.getValue()).getId().toString().equals(((MarketSupplierAppraiseTemplateInfo)this.prmtTemplate.getValue()).getEvaluationType().getId().toString())){
//					this.prmtTemplate.setValue(null);
//				}
//			}
        }else{
			this.prmtTemplate.setValue(null);
		}
        if(info!=null){
        	if(!(info.getNumber().equals("006")||info.getNumber().equals("007"))){
        		this.cbIsOver.setSelected(false);
        		this.prmtLevel.setValue(null);
        	}
        	if(!(info.getNumber().equals("002")||info.getNumber().equals("003")||
        			info.getNumber().equals("004")||info.getNumber().equals("005"))){
            	this.prmtGrade.setValue(null);
            	this.cbIsPass.setSelectedItem(null);
    		}
            if(!(info.getNumber().equals("003")||info.getNumber().equals("004")||
            		info.getNumber().equals("005"))){
    			this.kdtContractEntry.removeRows();
    			this.editData.getContractEntry().clear();
    			this.btnAddRow.setEnabled(false);
    			this.btninsrtRow.setEnabled(false);
    			this.btnRemoveRow.setEnabled(false);
    		}else{
    			this.btnAddRow.setEnabled(true);
    			this.btninsrtRow.setEnabled(true);
    			this.btnRemoveRow.setEnabled(true);
    		}
            
            if(info.getNumber().equals("004")){
        		this.txtLYHRate.setEnabled(true);
        		this.txtLYGCRate.setEnabled(true);
        		this.txtLYGCScore.setEnabled(true);
        		this.txtLYHRate.setRequired(true);
        		this.txtLYGCRate.setRequired(true);
        		this.txtLYGCScore.setRequired(true);
        	}else{
        		removeDataChangeListener(this.txtLYGCRate);
        		removeDataChangeListener(this.txtLYHRate);
        		removeDataChangeListener(this.txtLYGCScore);
        		this.txtLYHRate.setEnabled(false);
        		this.txtLYGCRate.setEnabled(false);
        		this.txtLYGCScore.setEnabled(false);
        		this.txtLYHRate.setRequired(false);
        		this.txtLYGCRate.setRequired(false);
        		this.txtLYGCScore.setRequired(false);
        		
        		this.txtLYHScore.setValue(null);
        		this.txtLYHRate.setValue(null);
        		this.txtLYGCScore.setValue(null);
        		this.txtLYGCRate.setValue(null);
        		this.txtAmount.setValue(this.kdtEntry.getFootManager()!=null?this.kdtEntry.getFootManager().getFootRow(0).getCell("score").getValue():BigDecimal.ZERO);
        		
        		addDataChangeListener(this.txtLYGCRate);
        		addDataChangeListener(this.txtLYHRate);
        		addDataChangeListener(this.txtLYGCScore);
        	}
        }else{
        	removeDataChangeListener(this.txtLYGCRate);
    		removeDataChangeListener(this.txtLYHRate);
    		removeDataChangeListener(this.txtLYGCScore);
    		
        	this.txtLYHRate.setEnabled(false);
    		this.txtLYGCRate.setEnabled(false);
    		this.txtLYGCScore.setEnabled(false);
    		this.txtLYHRate.setRequired(false);
    		this.txtLYGCRate.setRequired(false);
    		this.txtLYGCScore.setRequired(false);
    		
    		this.txtLYHScore.setValue(null);
    		this.txtLYHRate.setValue(null);
    		this.txtLYGCScore.setValue(null);
    		this.txtLYGCRate.setValue(null);
    		this.txtAmount.setValue(this.kdtEntry.getFootManager().getFootRow(0).getCell("score").getValue());
    		
    		addDataChangeListener(this.txtLYGCRate);
    		addDataChangeListener(this.txtLYHRate);
    		addDataChangeListener(this.txtLYGCScore);
        }
        setF7Filter((MarketSupplierStockInfo)this.prmtSupplier.getValue(),(MarketAccreditationTypeInfo)this.prmtEvaluationType.getValue(),(MarketSupplierAppraiseTemplateInfo)this.prmtTemplate.getValue());
		setComEnable();
	}
	protected void prmtTemplate_dataChanged(DataChangeEvent e) throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
        if(!isChanged){
        	return;
        }
        setF7Filter((MarketSupplierStockInfo)this.prmtSupplier.getValue(),(MarketAccreditationTypeInfo)this.prmtEvaluationType.getValue(),(MarketSupplierAppraiseTemplateInfo)this.prmtTemplate.getValue());
        
        setComEnable();	
        MarketSupplierAppraiseTemplateInfo info=(MarketSupplierAppraiseTemplateInfo)this.prmtTemplate.getValue();
        boolean isShowWarn=false;
        boolean isUpdate=false;
        if(this.kdtEntry.getRowCount()>0){
        	isShowWarn=true;
        }
        if(isShowWarn){
        	if(FDCMsgBox.showConfirm2(this, "评审模板改变会覆盖供应商评审数据，是否继续？")== FDCMsgBox.YES){
        		isUpdate=true;
            }
        }else{
        	isUpdate=true;
        }
        if(isUpdate){
        	this.kdtEntry.removeRows();
        	if(info!=null){
        		this.storeFields();
            	info=MarketSupplierAppraiseTemplateFactory.getRemoteInstance().getMarketSupplierAppraiseTemplateInfo(new ObjectUuidPK(info.getId()));
        		MarketSupplierAppraiseTemplateE1Collection guideEntry=info.getE1();
        		for(int i=0;i<guideEntry.size();i++){
        			MarketSupplierAppraiseTemplateE1Info e1info = MarketSupplierAppraiseTemplateE1Factory.getRemoteInstance()
        			.getMarketSupplierAppraiseTemplateE1Info(new ObjectUuidPK(((MarketSupplierAppraiseTemplateE1Info)guideEntry.get(i)).getId()));
        			IRow  irow = kdtEntry.addRow();
        			irow.getCell("guideType").setValue(e1info.getAccreditationwd());
        			if(e1info.getIndexName()!=null){
        				irow.getCell("guideName").setValue(MarketSplAuditIndexFactory.getRemoteInstance().getMarketSplAuditIndexInfo(new ObjectUuidPK(e1info.getIndexName().getId())));
        			}
        			irow.getCell("fullNum").setValue(e1info.getThreeStandard());
        			irow.getCell("weight").setValue(e1info.getQz());
        		}
        	}
        	TableUtils.getFootRow(this.kdtEntry,new String[]{"weight"});
        	setEntryScorePass();
        	BigDecimal score =MarketSupplierEvaluationEditUI.getTotalScore(this.kdtEntry,"score","weight");
        	IRow footRow = null;
        	KDTFootManager footRowManager = kdtEntry.getFootManager();
        	if(footRowManager == null)
        	{
	        	String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
	        	footRowManager = new KDTFootManager(kdtEntry);
	        	footRowManager.addFootView();
	        	kdtEntry.setFootManager(footRowManager);
	        	footRow = footRowManager.addFootRow(0);
	        	footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
	        	kdtEntry.getIndexColumn().setWidthAdjustMode((short)1);
	        	kdtEntry.getIndexColumn().setWidth(30);
	        	footRowManager.addIndexText(0, total);
        	} else{
        		footRow = footRowManager.getFootRow(0);
        	}
        	footRow.getCell("score").setValue(score);
        	for (int i = 0; i < kdtEntry.getRowCount(); i++) {
            	KDTableHelper.autoFitRowHeight(this.kdtEntry, i);
    		}
        }
	}
	protected void prmtGrade_dataChanged(DataChangeEvent e) throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
        if(!isChanged){
        	return;
        }
        MarketGradeSetUpInfo info=(MarketGradeSetUpInfo) this.prmtGrade.getValue();
        if(info!=null){
        	this.cbIsPass.setSelectedItem(info.getIsPass());
        }
	}
	
	protected void prmtEntry_dataChanged(DataChangeEvent e) throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
        if(!isChanged){
        	return;
        }
        if(prmtEntry.getValue()!=null){
        	kdtPerson.removeRows();
        	Object obj[] = (Object[])prmtEntry.getData();
        	for(int i = 0; i < obj.length; i++){
//	          	prmtEntry.setValue(objvalue);
	          	IRow irwo = kdtPerson.addRow();
	          	irwo.getCell("person").setValue(obj[i]);
        	}
        }
	}
	protected void cbIsOver_itemStateChanged(ItemEvent e) throws Exception {
		MarketAccreditationTypeInfo info=(MarketAccreditationTypeInfo) this.prmtEvaluationType.getValue();
		if(info!=null){
			if(info.getNumber().equals("006")||info.getNumber().equals("007")){
				if(this.cbIsOver.isSelected()){
					this.prmtLevel.setEnabled(false);
					this.prmtLevel.setValue(null);
				}else{
					this.prmtLevel.setEnabled(true);
				}
			}
		}
	}
	protected void kdtContractEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception{
		if(this.kdtContractEntry.getColumnKey(e.getColIndex()).equals("isHasContract")){
			setEntryContractEditor(e.getRowIndex());
			this.kdtContractEntry.getRow(e.getRowIndex()).getCell("contract").setValue(null);
			this.kdtContractEntry.getRow(e.getRowIndex()).getCell("contractAmount").setValue(null);
		}else{
			if(this.kdtContractEntry.getColumnKey(e.getColIndex()).equals("contract")){
				Object contract=this.kdtContractEntry.getRow(e.getRowIndex()).getCell(e.getColIndex()).getValue();
				if(contract!=null){
					if(contract instanceof ContractBillInfo){
						this.kdtContractEntry.getRow(e.getRowIndex()).getCell("contractAmount").setValue(((ContractBillInfo) contract).getAmount());
					}
				}else{
					if(contract instanceof ContractBillInfo){
						this.kdtContractEntry.getRow(e.getRowIndex()).getCell("contractAmount").setValue(null);
					}
				}
			}
		}
		DecimalFormat df = new DecimalFormat("##.00");
		if(this.kdtContractEntry.getColumnKey(e.getColIndex()).equals("contractPrice")){
			double contractAmount = UIRuleUtil.getBigDecimalValue(this.kdtContractEntry.getRow(e.getRowIndex()).getCell("contractAmount").getValue());
			double contractPrice = UIRuleUtil.getBigDecimalValue(this.kdtContractEntry.getRow(e.getRowIndex()).getCell("contractPrice").getValue());
			this.kdtContractEntry.getRow(e.getRowIndex()).getCell("Quantity").setValue(df.format(contractAmount/contractPrice));
		}else if(this.kdtContractEntry.getColumnKey(e.getColIndex()).equals("Quantity")){
			double contractAmount = UIRuleUtil.getBigDecimalValue(this.kdtContractEntry.getRow(e.getRowIndex()).getCell("contractAmount").getValue());
			double contractPrice = UIRuleUtil.getBigDecimalValue(this.kdtContractEntry.getRow(e.getRowIndex()).getCell("Quantity").getValue());
			this.kdtContractEntry.getRow(e.getRowIndex()).getCell("contractPrice").setValue(df.format(contractAmount/contractPrice));
		}else if(this.kdtContractEntry.getColumnKey(e.getColIndex()).equals("contractAmount")){
			this.kdtContractEntry.getRow(e.getRowIndex()).getCell("Quantity").setValue(null);
			this.kdtContractEntry.getRow(e.getRowIndex()).getCell("contractPrice").setValue(null);
		}
	}
	protected void setEntryContractEditor(int row){
		boolean isHasContract=((Boolean) this.kdtContractEntry.getRow(row).getCell("isHasContract").getValue()).booleanValue();
		if(isHasContract){
			KDBizPromptBox prmtcontract = new KDBizPromptBox();
			prmtcontract.setDisplayFormat("$name$");
			prmtcontract.setEditFormat("$number$");
			prmtcontract.setCommitFormat("$number$");
			prmtcontract.setRequired(true);
			prmtcontract.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractBillF7Query");

			prmtcontract.addSelectorListener(new SelectorListener() {
				public void willShow(SelectorEvent e) {
					KDBizPromptBox f7 = (KDBizPromptBox) e.getSource();
					f7.getQueryAgent().resetRuntimeEntityView();
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					
					filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
					filter.getFilterItems().add(new FilterItemInfo("contractType.longNumber","07YX%",CompareType.LIKE));
					if(editData.getOrgUnit()!=null){
						try {
							FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(editData.getOrgUnit().getId()));
							filter.getFilterItems().add(new FilterItemInfo("orgUnit.longNumber", orgUnitInfo.getLongNumber()+"%",CompareType.LIKE));
						} catch (EASBizException e1) {
							e1.printStackTrace();
						} catch (BOSException e1) {
							e1.printStackTrace();
						}
					}
					view.setFilter(filter);
					f7.setEntityViewInfo(view);
				}
			});
			KDTDefaultCellEditor contractEditor=new KDTDefaultCellEditor(prmtcontract);
			this.kdtContractEntry.getRow(row).getCell("contract").setEditor(contractEditor);
			ObjectValueRender ovrName = new ObjectValueRender();
			ovrName.setFormat(new BizDataFormat("$name$"));
			this.kdtContractEntry.getColumn("contract").setRenderer(ovrName);
			
			this.kdtContractEntry.getRow(row).getCell("contractAmount").getStyleAttributes().setLocked(true);
		}else{
			KDTextField contract = new KDTextField();
			contract.setMaxLength(200);
			KDTDefaultCellEditor contractEditor=new KDTDefaultCellEditor(contract);
			this.kdtContractEntry.getRow(row).getCell("contract").setEditor(contractEditor);
			this.kdtContractEntry.getRow(row).getCell("contractAmount").getStyleAttributes().setLocked(false);
		}
	}
	public void setEntryScorePass(){
    	Map map = new HashMap();
        if(this.prmtTemplate.getValue()!=null){
			if(this.prmtTemplate.getValue()!=null){
				String tempID = ((MarketSupplierAppraiseTemplateInfo)this.prmtTemplate.getValue()).getId().toString();
				try {
					String oql = "select id where id='"+tempID+"' ";
					MarketSupplierAppraiseTemplateInfo  tempinfo =MarketSupplierAppraiseTemplateFactory.getRemoteInstance().getMarketSupplierAppraiseTemplateInfo(oql);
					MarketSupplierAppraiseTemplateE1Collection e1col = tempinfo.getE1();
					for (int i = 0; i < e1col.size(); i++) {
						MarketSupplierAppraiseTemplateE1Info e1info = MarketSupplierAppraiseTemplateE1Factory.getRemoteInstance()
						.getMarketSupplierAppraiseTemplateE1Info(new ObjectUuidPK(((MarketSupplierAppraiseTemplateE1Info)e1col.get(i)).getId()));
						map.put(e1info.getIndexName().getId().toString(), e1info.getId().toString());
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			MarketSplAuditIndexInfo entry=(MarketSplAuditIndexInfo) this.kdtEntry.getRow(i).getCell("guideName").getValue();
			MarketSupplierAppraiseTemplateE1Info  E1entry = null;
			String evalType = entry.getId().toString();
			try {
				if(map.get(evalType)!=null){
					E1entry = MarketSupplierAppraiseTemplateE1Factory.getRemoteInstance()
					.getMarketSupplierAppraiseTemplateE1Info(new ObjectUuidPK((String)map.get(evalType)));
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if(E1entry!=null&&AppraiseTypeEnum.PASS.equals(E1entry.getScoreType())){
				this.kdtEntry.getRow(i).getCell("score").getStyleAttributes().setLocked(true);
				this.kdtEntry.getRow(i).getCell("isPass").getStyleAttributes().setLocked(false);
				this.kdtEntry.getRow(i).getCell("isPass").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			}else{
				this.kdtEntry.getRow(i).getCell("isPass").getStyleAttributes().setLocked(true);
				this.kdtEntry.getRow(i).getCell("score").getStyleAttributes().setLocked(false);
				this.kdtEntry.getRow(i).getCell("score").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			}
		}
		
		BigDecimal score =MarketSupplierEvaluationEditUI.getTotalScore(this.kdtEntry,"score","weight");
    	IRow footRow = null;
    	KDTFootManager footRowManager = kdtEntry.getFootManager();
    	if(footRowManager == null)
    	{
        	String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
        	footRowManager = new KDTFootManager(kdtEntry);
        	footRowManager.addFootView();
        	kdtEntry.setFootManager(footRowManager);
        	footRow = footRowManager.addFootRow(0);
        	footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
        	kdtEntry.getIndexColumn().setWidthAdjustMode((short)1);
        	kdtEntry.getIndexColumn().setWidth(30);
        	footRowManager.addIndexText(0, total);
    	} else{
    		footRow = footRowManager.getFootRow(0);
    	}
    	footRow.getCell("score").setValue(score);
    }
    
	protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception{
		if(e.getRowIndex()!=-1)
			KDTableHelper.autoFitRowHeight(this.kdtEntry, e.getRowIndex());
		if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("score")){
			BigDecimal score =MarketSupplierEvaluationEditUI.getTotalScore(this.kdtEntry,"score","weight");
	    	if(score.compareTo(new BigDecimal("100")) >0){
	    		FDCMsgBox.showWarning(this, "综合得分之和不能大于100！");
	    		this.kdtEntry.getRow(e.getRowIndex()).getCell("score").setValue(null);
	    	}
	    	IRow footRow = null;
        	KDTFootManager footRowManager = kdtEntry.getFootManager();
        	if(footRowManager == null)
        	{
	        	String total = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
	        	footRowManager = new KDTFootManager(kdtEntry);
	        	footRowManager.addFootView();
	        	kdtEntry.setFootManager(footRowManager);
	        	footRow = footRowManager.addFootRow(0);
	        	footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
	        	kdtEntry.getIndexColumn().setWidthAdjustMode((short)1);
	        	kdtEntry.getIndexColumn().setWidth(30);
	        	footRowManager.addIndexText(0, total);
        	} else{
        		footRow = footRowManager.getFootRow(0);
        	}
        	footRow.getCell("score").setValue(score.setScale(2, BigDecimal.ROUND_HALF_UP));
	    	
	    	MarketAccreditationTypeInfo info=(MarketAccreditationTypeInfo) this.prmtEvaluationType.getValue();
	    	if(info!=null&&info.getNumber().equals("004")){
	    		this.txtLYHScore.setValue(score.setScale(2, BigDecimal.ROUND_HALF_UP));
	    	}else{
	    		this.txtAmount.setValue(score.setScale(2, BigDecimal.ROUND_HALF_UP));
	    	}
		}
    }
	protected void attachListeners() {
		addDataChangeListener(this.prmtSupplier);
		addDataChangeListener(this.prmtEvaluationType);
		addDataChangeListener(this.prmtGrade);
		addDataChangeListener(this.prmtEntry);
		addDataChangeListener(this.prmtTemplate);
		addDataChangeListener(this.cbIsOver);
		addDataChangeListener(this.txtLYGCRate);
		addDataChangeListener(this.txtLYHRate);
		addDataChangeListener(this.txtLYGCScore);
		addDataChangeListener(this.prmtEvaluationType);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.prmtSupplier);
		removeDataChangeListener(this.prmtEvaluationType);
		removeDataChangeListener(this.prmtGrade);
		removeDataChangeListener(this.prmtEntry);
		removeDataChangeListener(this.prmtTemplate);
		removeDataChangeListener(this.cbIsOver);
		removeDataChangeListener(this.txtLYGCRate);
		removeDataChangeListener(this.txtLYHRate);
		removeDataChangeListener(this.txtLYGCScore);
		removeDataChangeListener(this.prmtEvaluationType);
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected IObjectValue createNewData() {
		MarketSupplierReviewGatherInfo info= new MarketSupplierReviewGatherInfo();
		if(getUIContext().get("org")!=null){
			info.setOrgUnit(((OrgStructureInfo) getUIContext().get("org")).getUnit());
		}
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		try {
			info.setBizDate(FDCCommonServerHelper.getServerTimeStamp());
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		info.setIsOver(false);
		info.setAmount(FDCHelper.ZERO);
		return info;
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
	}
	
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSupplier);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtEvaluationType);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtTemplate);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtEntry);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
		
		MarketAccreditationTypeInfo type=(MarketAccreditationTypeInfo)this.prmtEvaluationType.getValue();
//		if(type.getNumber().equals("002")){
//			FDCClientVerifyHelper.verifyEmpty(this, this.pkStorageDate);
//		}
		if(type.getNumber().equals("003")){
			if(!this.cbIsOver.isSelected()){
//				FDCClientVerifyHelper.verifyEmpty(this, this.prmtLevel);
			}
		}
		if(!(type.getNumber().equals("002")||type.getNumber().equals("003")||
				type.getNumber().equals("004")||type.getNumber().equals("005"))){
        	this.prmtGrade.setValue(null);
        	this.cbIsPass.setSelectedItem(null);
		}else{
			FDCClientVerifyHelper.verifyEmpty(this, this.prmtGrade);
			FDCClientVerifyHelper.verifyEmpty(this, this.cbIsPass);
		}
		if(type.getNumber().equals("003")){
			if(this.kdtContractEntry.getRowCount()==0){
				FDCMsgBox.showWarning(this,"合同分录不能为空！");
				SysUtil.abort();
			}
		}
		if(this.kdtEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"供应商评审分录不能为空！");
			SysUtil.abort();
		}
		setF7Filter((MarketSupplierStockInfo)this.prmtSupplier.getValue(),(MarketAccreditationTypeInfo)this.prmtEvaluationType.getValue(),(MarketSupplierAppraiseTemplateInfo)this.prmtTemplate.getValue());
		Map map = new HashMap();
		if(this.prmtTemplate.getValue()!=null){
			if(this.prmtTemplate.getValue()!=null){
				String tempID = ((MarketSupplierAppraiseTemplateInfo)this.prmtTemplate.getValue()).getId().toString();
				try {
					String oql = "select id where id='"+tempID+"' ";
					MarketSupplierAppraiseTemplateInfo  tempinfo =MarketSupplierAppraiseTemplateFactory.getRemoteInstance().getMarketSupplierAppraiseTemplateInfo(oql);
					MarketSupplierAppraiseTemplateE1Collection e1col = tempinfo.getE1();
					for (int i = 0; i < e1col.size(); i++) {
						MarketSupplierAppraiseTemplateE1Info e1info = MarketSupplierAppraiseTemplateE1Factory.getRemoteInstance()
						.getMarketSupplierAppraiseTemplateE1Info(new ObjectUuidPK(((MarketSupplierAppraiseTemplateE1Info)e1col.get(i)).getId()));
						map.put(e1info.getIndexName().getId().toString(), e1info.getId().toString());
					}
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
		for (int i = 0; i < this.kdtEntry.getRowCount(); i++) {
			IRow row=this.kdtEntry.getRow(i);
			MarketSplAuditIndexInfo entry=(MarketSplAuditIndexInfo) row.getCell("guideName").getValue();
			MarketSupplierAppraiseTemplateE1Info  E1entry = null;
			String evalType = entry.getId().toString();
			if(map.get(evalType)!=null){
				E1entry = MarketSupplierAppraiseTemplateE1Factory.getRemoteInstance()
				.getMarketSupplierAppraiseTemplateE1Info(new ObjectUuidPK((String)map.get(evalType)));
			}
			if(UIRuleUtil.isNull(row.getCell("remark").getValue())){
				FDCMsgBox.showWarning(this,"情况描述不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("remark"));
				SysUtil.abort();
			}
			if(E1entry!=null&&AppraiseTypeEnum.WEIGHT.equals(E1entry.getScoreType())){
				if(row.getCell("score").getValue()==null){
					FDCMsgBox.showWarning(this,"供应商评审综合得分不能为空！");
					this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("score"));
					SysUtil.abort();
				}
			}else{
				if(row.getCell("isPass").getValue()==null){
					FDCMsgBox.showWarning(this,"供应商评审合格与否不能为空！");
					this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("isPass"));
					SysUtil.abort();
				}
			}
		}
		FDCClientVerifyHelper.verifyEmpty(this, txtLinkPerson);
		FDCClientVerifyHelper.verifyEmpty(this, txtLinkPhone);
		
		if(type.getNumber().equals("003")){
			if(this.kdtContractEntry.getRowCount()==0){
				FDCMsgBox.showWarning(this,"合同分录不能为空！");
				SysUtil.abort();
			}
			for(int i = 0; i < this.kdtContractEntry.getRowCount(); i++){
				IRow row=this.kdtContractEntry.getRow(i);
				boolean isHasContract=((Boolean) this.kdtContractEntry.getRow(i).getCell("isHasContract").getValue()).booleanValue();
				if(isHasContract){
					if(row.getCell("contract").getValue()==null){
						FDCMsgBox.showWarning(this,"合同名称不能为空！");
						this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("contract"));
						SysUtil.abort();
					}
				}else{
					if(row.getCell("contract").getValue()==null||"".equals(row.getCell("contract").getValue().toString().trim())){
						FDCMsgBox.showWarning(this,"合同名称不能为空！");
						this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("contract"));
						SysUtil.abort();
					}
					if(row.getCell("contractAmount").getValue()==null){
						FDCMsgBox.showWarning(this,"合同金额不能为空！");
						this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("contractAmount"));
						SysUtil.abort();
					}
					if(((BigDecimal)row.getCell("contractAmount").getValue()).compareTo(FDCHelper.ZERO)==0){
						FDCMsgBox.showWarning(this,"合同签订金额不能为0！");
						this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("contractAmount"));
						SysUtil.abort();
					}
					
					if(row.getCell("contractPrice").getValue()==null){
						FDCMsgBox.showWarning(this,"合同单价不能为空！");
						this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("contractPrice"));
						SysUtil.abort();
					}
					if((UIRuleUtil.getBigDecimal(row.getCell("contractPrice").getValue())).compareTo(FDCHelper.ZERO)==0){
						FDCMsgBox.showWarning(this,"合同单价不能为0！");
						this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("contractPrice"));
						SysUtil.abort();
					}
					if(row.getCell("Quantity").getValue()==null){
						FDCMsgBox.showWarning(this,"合同分录数量不能为空！");
						this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("Quantity"));
						SysUtil.abort();
					}
					if((UIRuleUtil.getBigDecimal(row.getCell("Quantity").getValue())).compareTo(FDCHelper.ZERO)==0){
						FDCMsgBox.showWarning(this,"合同分录数量不能为0！");
						this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("Quantity"));
						SysUtil.abort();
					}
					if(UIRuleUtil.isNull(row.getCell("ValueForMoney").getValue())){
						FDCMsgBox.showWarning(this,"合同分录性价比不能为空！");
						this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("ValueForMoney"));
						SysUtil.abort();
					}
					if(UIRuleUtil.isNull(row.getCell("uniy").getValue())){
						FDCMsgBox.showWarning(this,"合同分录单位不能为空！");
						this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("uniy"));
						SysUtil.abort();
					}
				}
				for(int j=0;j<this.kdtContractEntry.getRowCount();j++){
					if(j==i) continue;
					IRow nRow=this.kdtContractEntry.getRow(j);
					boolean nIsHasContract=((Boolean) this.kdtContractEntry.getRow(j).getCell("isHasContract").getValue()).booleanValue();
					if(nIsHasContract==isHasContract){
						if(isHasContract){
							if(nRow.getCell("contract").getValue()==null){
								continue;
							}
							ContractBillInfo contract=(ContractBillInfo) row.getCell("contract").getValue();
							ContractBillInfo nContract=(ContractBillInfo) nRow.getCell("contract").getValue();
							if(contract.getId().toString().equals(nContract.getId().toString())){
								FDCMsgBox.showWarning(this,"合同名称不能重复！");
								this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("contract"));
								SysUtil.abort();
							}
						}else{
							if(nRow.getCell("contract").getValue()==null||"".equals(nRow.getCell("contract").getValue().toString().trim())){
								continue;
							}
							String contract=(String) row.getCell("contract").getValue();
							String nContract=(String) nRow.getCell("contract").getValue();
							if(contract.toString().equals(nContract.toString())){
								FDCMsgBox.showWarning(this,"合同名称不能重复！");
								this.kdtContractEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("contract"));
								SysUtil.abort();
							}
						}
					}
				}
			}
		}
	}
	protected void setAuditButtonStatus(String oprtType){
    	super.setAuditButtonStatus(oprtType);
    	if(!SysContext.getSysContext().getCurrentOrgUnit().isIsPurchaseOrgUnit()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
			this.actionAudit.setEnabled(false);
		}
    }
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
		} else {
			this.unLockUI();
		}
		setComEnable();
	}
	protected void txtLYGCRate_dataChanged(DataChangeEvent e) throws Exception {
		BigDecimal lygcScore=this.txtLYGCScore.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYGCScore.getBigDecimalValue();
		BigDecimal lygcRate=this.txtLYGCRate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYGCRate.getBigDecimalValue().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
	
		removeDataChangeListener(this.txtLYHRate);
		this.txtLYHRate.setValue(new BigDecimal(100).subtract(this.txtLYGCRate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYGCRate.getBigDecimalValue()));
		addDataChangeListener(this.txtLYHRate);
		
		BigDecimal lyhScore=this.txtLYHScore.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYHScore.getBigDecimalValue();
		BigDecimal lyhRate=this.txtLYHRate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYHRate.getBigDecimalValue().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);

		this.txtAmount.setValue(lygcScore.multiply(lygcRate).add(lyhScore.multiply(lyhRate)));
	}
	protected void txtLYHRate_dataChanged(DataChangeEvent e) throws Exception {
		BigDecimal lyhScore=this.txtLYHScore.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYHScore.getBigDecimalValue();
		BigDecimal lyhRate=this.txtLYHRate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYHRate.getBigDecimalValue().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
		
		removeDataChangeListener(this.txtLYGCRate);
		this.txtLYGCRate.setValue(new BigDecimal(100).subtract(this.txtLYHRate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYHRate.getBigDecimalValue()));
		addDataChangeListener(this.txtLYGCRate);
		
		BigDecimal lygcScore=this.txtLYGCScore.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYGCScore.getBigDecimalValue();
		BigDecimal lygcRate=this.txtLYGCRate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYGCRate.getBigDecimalValue().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
		
		this.txtAmount.setValue(lygcScore.multiply(lygcRate).add(lyhScore.multiply(lyhRate)));
	}
	protected void txtLYGCScore_dataChanged(DataChangeEvent e) throws Exception {
		BigDecimal lygcScore=this.txtLYGCScore.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYGCScore.getBigDecimalValue();
		BigDecimal lygcRate=this.txtLYGCRate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYGCRate.getBigDecimalValue().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
		
		BigDecimal lyhScore=this.txtLYHScore.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYHScore.getBigDecimalValue();
		BigDecimal lyhRate=this.txtLYHRate.getBigDecimalValue()==null?FDCHelper.ZERO:this.txtLYHRate.getBigDecimalValue().divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);

		this.txtAmount.setValue(lygcScore.multiply(lygcRate).add(lyhScore.multiply(lyhRate)));
	}
	public boolean checkBeforeWindowClosing() {
		if (this.getOprtState().equals(OprtState.VIEW)) {
			SelectorItemCollection sel=new SelectorItemCollection();
	    	sel.add("manager");
	    	sel.add("managerPhone");
			for(int i=0;i<this.kdtContractEntry.getRowCount();i++){
	    		IRow row=this.kdtContractEntry.getRow(i);
	    		MarketSupplierReviewGatherContractEntryInfo  entry =(MarketSupplierReviewGatherContractEntryInfo) row.getUserObject();
	    		if(UIRuleUtil.isNotNull(row.getCell("manager").getValue()))
	    			entry.setManager((String) row.getCell("manager").getValue());
	    		if(UIRuleUtil.isNotNull(row.getCell("managerPhone").getValue()))
	    			entry.setManagerPhone((String) row.getCell("managerPhone").getValue());
	    		try {
	    			MarketSupplierReviewGatherContractEntryFactory.getRemoteInstance().updatePartial(entry, sel);
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
		return super.checkBeforeWindowClosing();
	}
	protected void btnViewSupplier_actionPerformed(ActionEvent e) throws Exception {
		MarketSupplierStockInfo supplier=(MarketSupplierStockInfo) this.prmtSupplier.getValue();
		if(supplier!=null){
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", supplier.getId());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(MarketSupplierStockEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}else{
			FDCMsgBox.showWarning(this,"请先选择供应商！");
		}
	}
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.invite.markesupplier.MarketSupplierReviewGatherFactory.getRemoteInstance();
    }

    /**
     * output createNewDetailData method
     */
    protected IObjectValue createNewDetailData(KDTable table)
    {
		
        return null;
    }


}