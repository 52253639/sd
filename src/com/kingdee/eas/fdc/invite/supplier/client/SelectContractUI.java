/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditTemplateFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationContractEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationContractEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationContractEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierRGContractEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierRGContractEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherContractEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherContractEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class SelectContractUI extends AbstractSelectContractUI
{
    private static final Logger logger = CoreUIObject.getLogger(SelectContractUI.class);
    private SupplierStockInfo supplier=null;
    private SupplierEvaluationTypeInfo type=null;
    private SupplierAppraiseTemplateInfo template=null;
    private IObjectValue editData=null;
    private FullOrgUnitInfo orgUnit=null;
    public SelectContractUI() throws Exception
    {
        super();
    }

    protected void kdtContractEntry_editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    	if("isSelect".equals(this.kdtContractEntry.getColumn(e.getColIndex()).getKey())){
			if(!((Boolean)e.getValue()).booleanValue()){
				for(int i=0;i<this.kdtContractEntry.getRowCount();i++){
					if(i!=e.getRowIndex()){
						this.kdtContractEntry.getRow(i).getCell("isSelect").setValue(new Boolean(false));
					}
				}
			}
		}
    }
    public void onLoad() throws Exception {
    	this.kdtContractEntry.checkParsed();
		this.kdtContractEntry.getStyleAttributes().setLocked(true);
		KDCheckBox hit = new KDCheckBox();
		hit.setSelected(false);
 		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(hit);
 		this.kdtContractEntry.getColumn("isHasContract").setEditor(editor);
 		this.kdtContractEntry.getColumn("isSelect").setEditor(editor);
 		this.kdtContractEntry.getColumn("isSelect").getStyleAttributes().setLocked(false);
 		this.kdtContractEntry.getColumn("contractAmount").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtContractEntry.getColumn("contractAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		super.onLoad();
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		supplier=(SupplierStockInfo) this.getUIContext().get("supplier");
		type=(SupplierEvaluationTypeInfo) this.getUIContext().get("type");
		template=(SupplierAppraiseTemplateInfo)this.getUIContext().get("template");
		editData=(IObjectValue)this.getUIContext().get("editData");
		orgUnit=(FullOrgUnitInfo)this.getUIContext().get("orgUnit");
		
		if(supplier==null||type==null||template==null||editData==null||orgUnit==null){
			FDCMsgBox.showWarning(this,"存在为空条件！");
			SysUtil.abort();
		}
		filter.getFilterItems().add(new FilterItemInfo("evaluationType.id",type.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("supplier.id",supplier.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("template.id",template.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",orgUnit.getId().toString()));
		if(editData instanceof FDCSplQualificationAuditBillInfo){
			if(((FDCSplQualificationAuditBillInfo)editData).getId()!=null){
				filter.getFilterItems().add(new FilterItemInfo("head.id",((FDCSplQualificationAuditBillInfo)editData).getId().toString(),CompareType.NOTEQUALS));
			}
		}else if(editData instanceof SupplierReviewGatherInfo){
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		}
		view.setFilter(filter);
		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("contractEntry.*"));
		view.getSelector().add(new SelectorItemInfo("contractEntry.contractBill.amount"));
		view.getSelector().add(new SelectorItemInfo("contractEntry.contractBill.name"));
		view.getSorter().add(new SorterItemInfo("contractEntry.seq"));
		FDCSplQualificationAuditBillCollection col=FDCSplQualificationAuditBillFactory.getRemoteInstance().getFDCSplQualificationAuditBillCollection(view);
		Map ev=new HashMap();
		Map comEv=new HashMap();
		Map contractEntryMap=new HashMap();
		for(int i=0;i<col.size();i++){
			FDCSplQualificationAuditBillInfo info=col.get(i);
			List entryEv=new ArrayList();
			List entryMapValue=new ArrayList();
			for(int j=0;j<info.getContractEntry().size();j++){
				SupplierEvaluationContractEntryInfo entry=info.getContractEntry().get(j);
				if(entry.isIsHasContract()){
					if(entry.getContractBill()!=null){
						entryEv.add(entry.getContractBill().getId());
					}
				}else{
					entryEv.add(entry.getContract());
				}
				entryMapValue.add(entry);
			}
			ev.put(info.getId().toString(), entryEv);
			comEv.put(info.getId().toString(), entryEv);
			
			contractEntryMap.put(info.getId().toString(), entryMapValue);
		}
		Iterator it=ev.entrySet().iterator();  
		Set rm=new HashSet();
        while(it.hasNext()){
        	
        	Map.Entry entry = (Map.Entry) it.next();
        	String id=entry.getKey().toString();
        	if(rm.contains(id)) continue;
        	List value=(ArrayList)entry.getValue();
        	
        	List addValue=(List) contractEntryMap.get(entry.getKey().toString());
        	
        	if(editData instanceof FDCSplQualificationAuditBillInfo){
        		SupplierEvaluationContractEntryCollection rgCol=new SupplierEvaluationContractEntryCollection();
            	
            	Iterator comIt=comEv.entrySet().iterator();
            	while(comIt.hasNext()){
            		Map.Entry comEntry = (Map.Entry) comIt.next();
                	
                	if(entry.getKey().toString().equals(comEntry.getKey().toString())) continue;
                	List comValue=(ArrayList)comEntry.getValue();
                	if(value.containsAll(comValue)&&value.size()==comValue.size()){
                		rm.add(comEntry.getKey().toString());
                	}
            	}
            	for(int i=0;i<addValue.size();i++){
            		SupplierEvaluationContractEntryInfo addEntry =(SupplierEvaluationContractEntryInfo) addValue.get(i);
            		addEntry.setId(null);
            		rgCol.add(addEntry);
            		
    				IRow row=this.kdtContractEntry.addRow();
    				row.setUserObject(rgCol);
    				row.getCell("id").setValue(id);
    				row.getCell("isSelect").setValue(Boolean.FALSE);
    				row.getCell("isHasContract").setValue(Boolean.valueOf(addEntry.isIsHasContract()));
    				if(addEntry.isIsHasContract()){
    					row.getCell("contract").setValue(addEntry.getContractBill().getName());
    				}else{
    					row.getCell("contract").setValue(addEntry.getContract());
    				}
    				row.getCell("contractAmount").setValue(addEntry.getContractAmount());
        		}
    		}else if(editData instanceof SupplierReviewGatherInfo){
    			SelectorItemCollection sel=new SelectorItemCollection();
            	sel.add("id");
            	sel.add("contractEntry.*");
            	sel.add("contractEntry.contractBill.id");
            	sel.add("contractEntry.contractBill.amount");
            	sel.add("contractEntry.contractBill.name");
            	FDCSplQualificationAuditBillInfo supplierEv=FDCSplQualificationAuditBillFactory.getRemoteInstance().getFDCSplQualificationAuditBillInfo(new ObjectUuidPK(id),sel) ;

    			SupplierReviewGatherContractEntryCollection rgCol=new SupplierReviewGatherContractEntryCollection();
    			
            	SupplierReviewGatherContractEntryInfo rgEntry=new SupplierReviewGatherContractEntryInfo();
            	rgEntry.setSupplierEvaluation(supplierEv);
            	rgCol.add(rgEntry);
            	
            	SupplierRGContractEntryCollection contractCol=new SupplierRGContractEntryCollection();
            	for(int i=0;i<supplierEv.getContractEntry().size();i++){
            		SupplierRGContractEntryInfo contractEntry=new SupplierRGContractEntryInfo();
            		contractEntry.setIsHasContract(supplierEv.getContractEntry().get(i).isIsHasContract());
            		contractEntry.setContract(supplierEv.getContractEntry().get(i).getContract());
            		contractEntry.setContractBill(supplierEv.getContractEntry().get(i).getContractBill());
            		contractEntry.setContractAmount(supplierEv.getContractEntry().get(i).getContractAmount());
            		
            		contractCol.add(contractEntry);
            	}
            	
            	Iterator comIt=comEv.entrySet().iterator();
            	while(comIt.hasNext()){
            		Map.Entry comEntry = (Map.Entry) comIt.next();
            		
            		if(entry.getKey().toString().equals(comEntry.getKey().toString())) continue;
            		
            		List comValue=(ArrayList)comEntry.getValue();
                	if(value.containsAll(comValue)&&value.size()==comValue.size()){
                		rm.add(comEntry.getKey().toString());
                		
                		SupplierReviewGatherContractEntryInfo sameRgEntry=new SupplierReviewGatherContractEntryInfo();
                		FDCSplQualificationAuditBillInfo sameSupplierEv=FDCSplQualificationAuditBillFactory.getRemoteInstance().getFDCSplQualificationAuditBillInfo(new ObjectUuidPK(comEntry.getKey().toString()),sel) ;
                		sameRgEntry.setSupplierEvaluation(sameSupplierEv);
                		rgCol.add(sameRgEntry);
                		
                		id=id+comEntry.getKey().toString();
                	}
            	}
            	for(int i=0;i<addValue.size();i++){
            		SupplierEvaluationContractEntryInfo addEntry =(SupplierEvaluationContractEntryInfo) addValue.get(i);
    				IRow row=this.kdtContractEntry.addRow();
    				row.setUserObject(rgCol);
    				row.getCell("id").setValue(id);
    				row.getCell("id").setUserObject(contractCol);
    				row.getCell("isSelect").setValue(Boolean.FALSE);
    				row.getCell("isHasContract").setValue(Boolean.valueOf(addEntry.isIsHasContract()));
    				if(addEntry.isIsHasContract()){
    					row.getCell("contract").setValue(addEntry.getContractBill().getName());
    				}else{
    					row.getCell("contract").setValue(addEntry.getContract());
    				}
    				row.getCell("contractAmount").setValue(addEntry.getContractAmount());
        		}
    		}
        }
        mergerTable(this.kdtContractEntry,new String[]{"id"},new String[]{"isSelect"});
    }
    private void mergerTable(KDTable table,String coloum[],String mergeColoum[]){
		int merger=0;
		for(int i=0;i<table.getRowCount();i++){
			if(i>0){
				boolean isMerge=true;
				for(int j=0;j<coloum.length;j++){
					Object curRow=table.getRow(i).getCell(coloum[j]).getValue();
					Object lastRow=table.getRow(i-1).getCell(coloum[j]).getValue();
					if(!getString(curRow).equals(getString(lastRow))){
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
    private String getString(Object value){
		if(value==null) return "";
		if(value!=null&&value.toString().trim().equals("")){
			return "";
		}else{
			return value.toString();
		}
	}
	protected void btnNo_actionPerformed(ActionEvent e) throws Exception {
		if(editData instanceof FDCSplQualificationAuditBillInfo){
			((FDCSplQualificationAuditBillInfo)editData).put("isCancel", true);
		}else if(editData instanceof SupplierReviewGatherInfo){
			((SupplierReviewGatherInfo)editData).put("isCancel", true);
		}
		this.destroyWindow();
	}
	protected void btnYes_actionPerformed(ActionEvent e) throws Exception {
		if(editData instanceof FDCSplQualificationAuditBillInfo){
			((FDCSplQualificationAuditBillInfo)editData).put("isCancel", false);
			((FDCSplQualificationAuditBillInfo)editData).getContractEntry().clear();
			for(int i=0;i<this.kdtContractEntry.getRowCount();i++){
				boolean isSelect=((Boolean) this.kdtContractEntry.getRow(i).getCell("isSelect").getValue()).booleanValue();
				if(isSelect){
					((FDCSplQualificationAuditBillInfo)editData).getContractEntry().addCollection((SupplierEvaluationContractEntryCollection)this.kdtContractEntry.getRow(i).getUserObject());
				}
			}
		}else if(editData instanceof SupplierReviewGatherInfo){
			((SupplierReviewGatherInfo)editData).put("isCancel", false);
			((SupplierReviewGatherInfo)editData).getEvaluationEntry().clear();
			((SupplierReviewGatherInfo)editData).getContractEntry().clear();
			for(int i=0;i<this.kdtContractEntry.getRowCount();i++){
				boolean isSelect=((Boolean) this.kdtContractEntry.getRow(i).getCell("isSelect").getValue()).booleanValue();
				if(isSelect){
					((SupplierReviewGatherInfo)editData).getEvaluationEntry().addCollection((SupplierReviewGatherContractEntryCollection)this.kdtContractEntry.getRow(i).getUserObject());
					((SupplierReviewGatherInfo)editData).getContractEntry().addCollection((SupplierRGContractEntryCollection)this.kdtContractEntry.getRow(i).getCell("id").getUserObject());
				}
			}
		}
		super.destroyWindow();
	}

}