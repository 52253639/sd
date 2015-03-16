/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDSpinner;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo;
import com.kingdee.eas.fdc.aimcost.FieldTypeEnum;
import com.kingdee.eas.fdc.basedata.CalculateTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IndexVersionFactory;
import com.kingdee.eas.fdc.basedata.IndexVersionInfo;
import com.kingdee.eas.fdc.basedata.OperationPhasesCollection;
import com.kingdee.eas.fdc.basedata.OperationPhasesFactory;
import com.kingdee.eas.fdc.basedata.ProjectBaseFactory;
import com.kingdee.eas.fdc.basedata.ProjectBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.market.DynamicValueReportFacadeFactory;
import com.kingdee.eas.fdc.market.ValueInputCollection;
import com.kingdee.eas.fdc.market.ValueInputEntryInfo;
import com.kingdee.eas.fdc.market.ValueInputFactory;
import com.kingdee.eas.fdc.market.ValueInputInfo;
import com.kingdee.eas.fdc.market.ValuePlanDetialEntryCollection;
import com.kingdee.eas.fdc.market.ValuePlanDetialEntryInfo;
import com.kingdee.eas.fdc.market.ValuePlanDetialTypeEnum;
import com.kingdee.eas.fdc.market.ValuePlanEntry;
import com.kingdee.eas.fdc.market.ValuePlanEntryInfo;
import com.kingdee.eas.fdc.market.ValuePlanFactory;
import com.kingdee.eas.fdc.market.ValuePlanInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ValuePlanEditUI extends AbstractValuePlanEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ValuePlanEditUI.class);
    private EventListener[] listeners1=null;
    private EventListener[] listeners2=null;
    private EventListener[] listeners3=null;
    private KDTable table=null;
    private Map rowMap=null;
    public ValuePlanEditUI() throws Exception
    {
        super();
    }
    private void removeListiner(KDSpinner spn, EventListener[] listeners) {
		for (int i = 0; i < listeners.length; i++) {
			spn.removeChangeListener((ChangeListener) listeners[i]);
		}
	}
	private void addListiner(KDSpinner spn, EventListener[] listeners) {
		for (int i = 0; i < listeners.length; i++) {
			spn.addChangeListener((ChangeListener) listeners[i]);
		}
	}
    protected void attachListeners() {
    	if (listeners1 != null) {
			addListiner(this.spnYear, listeners1);
		}
		if (listeners2 != null) {
			addListiner(this.spnMonth, listeners2);
		}
		if (listeners3 != null) {
			addListiner(this.spnPlanYear, listeners3);
		}
	}
	protected void detachListeners() {
		listeners1 = this.spnYear.getListeners(ChangeListener.class);
		removeListiner(this.spnYear, listeners1);
		
		listeners2 = this.spnMonth.getListeners(ChangeListener.class);
		removeListiner(this.spnMonth, listeners2);
		
		listeners3 = this.spnPlanYear.getListeners(ChangeListener.class);
		removeListiner(this.spnPlanYear, listeners3);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ValuePlanFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	public void onLoad() throws Exception {
		
		super.onLoad();
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
		
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPre.setVisible(false);
		this.menuView.setVisible(false);
		
		this.menuBiz.setVisible(false);
		this.actionAddNew.setVisible(false);
		
		this.prmtProject.setEnabled(false);
		
		this.spnYear.setModel(new SpinnerNumberModel(this.spnYear.getIntegerVlaue().intValue(),1990,3099,1));
		this.spnPlanYear.setModel(new SpinnerNumberModel(this.spnPlanYear.getIntegerVlaue().intValue(),0,10,1));
		this.spnMonth.setModel(new SpinnerNumberModel(this.spnMonth.getIntegerVlaue().intValue(),1,12,1));
	}
	protected void setPlanDetialEntryInfo(IRow row,ValuePlanEntryInfo entry,ValuePlanDetialEntryInfo detailEntry,int year,int month,int quarter,ValuePlanDetialTypeEnum type){
		detailEntry.setYear(year);
		detailEntry.setMonth(month);
		detailEntry.setQuarter(quarter);
		detailEntry.setType(type);
		entry.getDetialEntry().add(detailEntry);
		String key="";
		if(type.equals(ValuePlanDetialTypeEnum.YEAR)){
			key=year+"Y";
		}else if(type.equals(ValuePlanDetialTypeEnum.MONTH)){
			key=year+"Y"+month+"M";
		}else{
			key=year+"Y"+quarter+"Q";
		}
		String typeString[]=new String[]{"c","x","s",""};
		for(int typeI=0;typeI<typeString.length;typeI++){
			row.getCell(key+typeString[typeI]+"account").setUserObject(detailEntry);
			row.getCell(key+typeString[typeI]+"area").setUserObject(detailEntry);
			row.getCell(key+typeString[typeI]+"price").setUserObject(detailEntry);
			row.getCell(key+typeString[typeI]+"amount").setUserObject(detailEntry);
			
			if(detailEntry.get(typeString[typeI]+"account")!=null&&(Integer)detailEntry.get(typeString[typeI]+"account")!=0){
				row.getCell(key+typeString[typeI]+"account").setValue(detailEntry.get(typeString[typeI]+"account"));
			}
			if(detailEntry.get(typeString[typeI]+"area")!=null&&((BigDecimal)detailEntry.get(typeString[typeI]+"area")).compareTo(FDCHelper.ZERO)!=0){
				row.getCell(key+typeString[typeI]+"area").setValue(detailEntry.get(typeString[typeI]+"area"));
			}
			if(detailEntry.get(typeString[typeI]+"price")!=null&&((BigDecimal)detailEntry.get(typeString[typeI]+"price")).compareTo(FDCHelper.ZERO)!=0){
				row.getCell(key+typeString[typeI]+"price").setValue(detailEntry.get(typeString[typeI]+"price"));
			}
			if(detailEntry.get(typeString[typeI]+"amount")!=null&&((BigDecimal)detailEntry.get(typeString[typeI]+"amount")).compareTo(FDCHelper.ZERO)!=0){
				row.getCell(key+typeString[typeI]+"amount").setValue(detailEntry.get(typeString[typeI]+"amount"));
			}
			if(typeI==typeString.length-1){
				row.getCell(key+"backAmount").setUserObject(detailEntry);
				
				if(detailEntry.get(typeString[typeI]+"backAmount")!=null&&((BigDecimal)detailEntry.get(typeString[typeI]+"backAmount")).compareTo(FDCHelper.ZERO)!=0){
					row.getCell(key+typeString[typeI]+"backAmount").setValue(detailEntry.get(typeString[typeI]+"backAmount"));
				}
			}
		}
	}
	protected void loadEntry(ValuePlanInfo info,int beginYear,int planYear){
		rowMap=new HashMap();
		for(int i=0;i<info.getEntry().size();i++){
			ValuePlanEntryInfo entry=info.getEntry().get(i);
			IRow row=table.addRow();
			row.setUserObject(entry);
			row.getCell("project").setValue(entry.getProject());
			row.getCell("operationPhases").setValue(entry.getOperationPhases());
			row.getCell("batch").setValue(entry.getBatch());
			row.getCell("productType").setValue(entry.getProductType());
			
			rowMap.put(entry.getOperationPhases()+entry.getBatch()+entry.getProductType(),row);
			if(entry.getAccount()!=0){
				row.getCell("account").setValue(entry.getAccount());
			}
			if(entry.getArea()!=null&&entry.getArea().compareTo(FDCHelper.ZERO)!=0){
				row.getCell("area").setValue(entry.getArea());
			}
			if(entry.getPrice()!=null&&entry.getPrice().compareTo(FDCHelper.ZERO)!=0){
				row.getCell("price").setValue(entry.getPrice());
			}
			if(entry.getAmount()!=null&&entry.getAmount().compareTo(FDCHelper.ZERO)!=0){
				row.getCell("amount").setValue(entry.getArea());
			}
			
			if(entry.getSaccount()!=0){
				row.getCell("saccount").setValue(entry.getSaccount());
			}
			if(entry.getSarea()!=null&&entry.getSarea().compareTo(FDCHelper.ZERO)!=0){
				row.getCell("sarea").setValue(entry.getSarea());
			}
			if(entry.getSprice()!=null&&entry.getSprice().compareTo(FDCHelper.ZERO)!=0){
				row.getCell("sprice").setValue(entry.getSprice());
			}
			if(entry.getSamount()!=null&&entry.getSamount().compareTo(FDCHelper.ZERO)!=0){
				row.getCell("samount").setValue(entry.getSamount());
			}
			
			if(entry.getDaccount()!=0){
				row.getCell("daccount").setValue(entry.getDaccount());
			}
			if(entry.getDarea()!=null&&entry.getDarea().compareTo(FDCHelper.ZERO)!=0){
				row.getCell("darea").setValue(entry.getDarea());
			}
			if(entry.getDprice()!=null&&entry.getDprice().compareTo(FDCHelper.ZERO)!=0){
				row.getCell("dprice").setValue(entry.getDprice());
			}
			if(entry.getDamount()!=null&&entry.getDamount().compareTo(FDCHelper.ZERO)!=0){
				row.getCell("damount").setValue(entry.getDamount());
			}
			
			if(entry.getPaccount()!=0){
				row.getCell("paccount").setValue(entry.getPaccount());
			}
			if(entry.getParea()!=null&&entry.getParea().compareTo(FDCHelper.ZERO)!=0){
				row.getCell("parea").setValue(entry.getParea());
			}
			if(entry.getPprice()!=null&&entry.getPprice().compareTo(FDCHelper.ZERO)!=0){
				row.getCell("pprice").setValue(entry.getPprice());
			}
			if(entry.getPamount()!=null&&entry.getPamount().compareTo(FDCHelper.ZERO)!=0){
				row.getCell("pamount").setValue(entry.getPamount());
			}
			row.getCell("remark").setValue(entry.getRemark());
			Map yearEntryMap=new HashMap();
			Map monthEntryMap=new HashMap();
			Map quarterEntryMap=new HashMap();
			for(int k=0;k<entry.getDetialEntry().size();k++){
				ValuePlanDetialEntryInfo detailEntry=entry.getDetialEntry().get(k);
				if(detailEntry.getType().equals(ValuePlanDetialTypeEnum.YEAR)){
					yearEntryMap.put(detailEntry.getYear()+"Y", detailEntry);
				}else if(detailEntry.getType().equals(ValuePlanDetialTypeEnum.MONTH)){
					monthEntryMap.put(detailEntry.getYear()+"Y"+detailEntry.getMonth()+"M", detailEntry);
				}else{
					quarterEntryMap.put(detailEntry.getYear()+"Y"+detailEntry.getQuarter()+"Q", detailEntry);
				}
			}
			for(int k=beginYear;k<beginYear+planYear;k++){
				if(yearEntryMap.containsKey(k+"Y")){
					ValuePlanDetialEntryInfo detailEntry=(ValuePlanDetialEntryInfo) yearEntryMap.get(k+"Y");
					setPlanDetialEntryInfo(row,entry,detailEntry,detailEntry.getYear(),detailEntry.getMonth(),detailEntry.getQuarter(),detailEntry.getType());
				}else{
					ValuePlanDetialEntryInfo detailEntry=new ValuePlanDetialEntryInfo();
					setPlanDetialEntryInfo(row,entry,detailEntry,k,0,0,ValuePlanDetialTypeEnum.YEAR);
				}
				if(k==beginYear){
					for(int monthk=1;monthk<13;monthk++){
						if(monthEntryMap.containsKey(k+"Y"+monthk+"M")){
							ValuePlanDetialEntryInfo detailEntry=(ValuePlanDetialEntryInfo) monthEntryMap.get(k+"Y"+monthk+"M");
							setPlanDetialEntryInfo(row,entry,detailEntry,detailEntry.getYear(),detailEntry.getMonth(),detailEntry.getQuarter(),detailEntry.getType());
						}else{
							ValuePlanDetialEntryInfo detailEntry=new ValuePlanDetialEntryInfo();
							setPlanDetialEntryInfo(row,entry,detailEntry,k,monthk,0,ValuePlanDetialTypeEnum.MONTH);
						}
					}
				}else{
					for(int quarterk=1;quarterk<5;quarterk++){
						if(quarterEntryMap.containsKey(k+"Y"+quarterk+"Q")){
							ValuePlanDetialEntryInfo detailEntry=(ValuePlanDetialEntryInfo) quarterEntryMap.get(k+"Y"+quarterk+"Q");
							setPlanDetialEntryInfo(row,entry,detailEntry,detailEntry.getYear(),detailEntry.getMonth(),detailEntry.getQuarter(),detailEntry.getType());
						}else{
							ValuePlanDetialEntryInfo detailEntry=new ValuePlanDetialEntryInfo();
							setPlanDetialEntryInfo(row,entry,detailEntry,k,0,quarterk,ValuePlanDetialTypeEnum.QUARTER);
						}
					}
				}
			}
		}
	}
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		createTable(this.editData,this.editData.getYear(),this.editData.getPlanYear(),this.editData.getMonth());
		
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()!=null){
			FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		}
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
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
		} else {
			this.unLockUI();
		}
	}
	public void storeFields() {
		super.storeFields();
		
		Set yearEntrySet=new HashSet();
		Set monthEntrySet=new HashSet();
		Set quarterEntrySet=new HashSet();
		for(int k=this.editData.getYear();k<this.editData.getYear()+this.editData.getPlanYear();k++){
			yearEntrySet.add(k+"Y");
			if(k==this.editData.getYear()){
				for(int monthk=1;monthk<13;monthk++){
					monthEntrySet.add(k+"Y"+monthk+"M");
				}
			}else{
				for(int quarterk=1;quarterk<5;quarterk++){
					quarterEntrySet.add(k+"Y"+quarterk+"Q");
				}
			}
		}
		for(int i=0;i<this.editData.getEntry().size();i++){
			ValuePlanDetialEntryCollection delCol=new ValuePlanDetialEntryCollection();
			for(int k=0;k<this.editData.getEntry().get(i).getDetialEntry().size();k++){
				ValuePlanDetialEntryInfo detailEntry=this.editData.getEntry().get(i).getDetialEntry().get(k);
				if(detailEntry.getType().equals(ValuePlanDetialTypeEnum.YEAR)){
					if(!yearEntrySet.contains(detailEntry.getYear()+"Y")){
						delCol.add(detailEntry);
					}
				}else if(detailEntry.getType().equals(ValuePlanDetialTypeEnum.MONTH)){
					if(!monthEntrySet.contains(detailEntry.getYear()+"Y"+detailEntry.getMonth()+"M")){
						delCol.add(detailEntry);
					}
				}else{
					if(!quarterEntrySet.contains(detailEntry.getYear()+"Y"+detailEntry.getQuarter()+"Q")){
						delCol.add(detailEntry);
					}
				}
			}
			for(int k=0;k<delCol.size();k++){
				this.editData.getEntry().get(i).getDetialEntry().remove(delCol.get(k));
			}
		}
	}
	protected void setXSValue() throws BOSException, SQLException{
		int beginYear=this.spnYear.getIntegerVlaue();
		OperationPhasesCollection col = OperationPhasesFactory.getRemoteInstance().getOperationPhasesCollection("select id from where projectBase.id='"+this.editData.getProject().getId().toString()+"'");
		String projectString="";
		for(int i=0;i<col.size();i++){
			if(i==col.size()-1){
				projectString=projectString+"'"+col.get(i).getId().toString()+"'";
			}else{
				projectString=projectString+"'"+col.get(i).getId().toString()+"',";
			}
		}
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql(" select entry.fproject operationPhases,entry.fbatch batch,entry.fproductType productType,year(entry.fdate) year,month(entry.fdate) month,quarter(entry.fdate) quarter,sum(entry.faccount) account,round(sum(entry.farea),2) area,round(sum(entry.famount),0) amount,(case when sum(entry.farea) is null or sum(entry.farea)=0 then 0 else round(sum(entry.famount)/sum(entry.farea),0) end )price");
		builder.appendSql(" from T_MAR_ValueInputEntry entry left join T_MAR_ValueInput vi on vi.fid=entry.fheadId left join T_BD_IndexVersion version on version.fid=vi.findexVersionId");
		builder.appendSql(" left join (select max(version.fnumber) indexVersion,vi.fprojectid from T_MAR_ValueInput vi left join T_BD_IndexVersion version on version.fid=vi.findexVersionId where vi.fisLatest=1 group by vi.fprojectid) maxVi on maxVi.indexVersion=version.fnumber and maxVi.fprojectId=vi.fprojectId");
		builder.appendSql(" where maxVi.indexVersion is not null and vi.fisLatest=1");
		if(projectString!=null&&!"".equals(projectString)){
			builder.appendSql(" and entry.fprojectid in("+projectString+")");
    	}else{
    		builder.appendSql(" and entry.fprojectid = 'null'");
    	}
		builder.appendSql(" group by entry.fproject,entry.fbatch,entry.fproductType,year(entry.fdate),month(entry.fdate),quarter(entry.fdate)");
		IRowSet rowSet=builder.executeQuery();
		while(rowSet.next()){
			String key=rowSet.getString("operationPhases")+rowSet.getString("batch")+rowSet.getString("productType");
			int year=rowSet.getInt("year");
			int month=rowSet.getInt("month");
			int quarter=rowSet.getInt("quarter");
			
			int account=rowSet.getInt("account");
			BigDecimal area=rowSet.getBigDecimal("area");
			BigDecimal price=rowSet.getBigDecimal("price");
			BigDecimal amount=rowSet.getBigDecimal("amount");
			if(rowMap.get(key)!=null){
				IRow row=(IRow) rowMap.get(key);
				if(year==beginYear){
					ICell cell=row.getCell(year+"Y"+month+"M"+"xaccount");
					if(cell!=null){
						ValuePlanDetialEntryInfo detialEntry=(ValuePlanDetialEntryInfo) cell.getUserObject();
						detialEntry.setXaccount(account);
						cell.setValue(account);
						KDTEditEvent e=new KDTEditEvent(cell);
						e.setRowIndex(row.getRowIndex());
						e.setColIndex(cell.getColumnIndex());
						e.setValue(account);
						table_editStopped(e);
					}
					cell=row.getCell(year+"Y"+month+"M"+"xarea");
					if(cell!=null){
						ValuePlanDetialEntryInfo detialEntry=(ValuePlanDetialEntryInfo) cell.getUserObject();
						detialEntry.setXarea(area);
						cell.setValue(area);
						KDTEditEvent e=new KDTEditEvent(cell);
						e.setRowIndex(row.getRowIndex());
						e.setColIndex(cell.getColumnIndex());
						e.setValue(area);
						table_editStopped(e);
					}
					cell=row.getCell(year+"Y"+month+"M"+"xprice");
					if(cell!=null){
						ValuePlanDetialEntryInfo detialEntry=(ValuePlanDetialEntryInfo) cell.getUserObject();
						detialEntry.setXprice(price);
						cell.setValue(price);
					}
					cell=row.getCell(year+"Y"+month+"M"+"xamount");
					if(cell!=null){
						ValuePlanDetialEntryInfo detialEntry=(ValuePlanDetialEntryInfo) cell.getUserObject();
						detialEntry.setXamount(amount);
						cell.setValue(amount);
						KDTEditEvent e=new KDTEditEvent(cell);
						e.setRowIndex(row.getRowIndex());
						e.setColIndex(cell.getColumnIndex());
						e.setValue(amount);
						table_editStopped(e);
					}
				}else{
					ICell cell=row.getCell(year+"Y"+quarter+"Q"+"xaccount");
					if(cell!=null){
						ValuePlanDetialEntryInfo detialEntry=(ValuePlanDetialEntryInfo) cell.getUserObject();
						detialEntry.setXaccount(account);
						cell.setValue(account);
						KDTEditEvent e=new KDTEditEvent(cell);
						e.setRowIndex(row.getRowIndex());
						e.setColIndex(cell.getColumnIndex());
						e.setValue(account);
						table_editStopped(e);
					}
					cell=row.getCell(year+"Y"+quarter+"Q"+"xarea");
					if(cell!=null){
						ValuePlanDetialEntryInfo detialEntry=(ValuePlanDetialEntryInfo) cell.getUserObject();
						detialEntry.setXarea(area);
						cell.setValue(area);
						KDTEditEvent e=new KDTEditEvent(cell);
						e.setRowIndex(row.getRowIndex());
						e.setColIndex(cell.getColumnIndex());
						e.setValue(area);
						table_editStopped(e);
					}
					cell=row.getCell(year+"Y"+quarter+"Q"+"xprice");
					if(cell!=null){
						ValuePlanDetialEntryInfo detialEntry=(ValuePlanDetialEntryInfo) cell.getUserObject();
						detialEntry.setXprice(price);
						cell.setValue(price);
					}
					cell=row.getCell(year+"Y"+quarter+"Q"+"xamount");
					if(cell!=null){
						ValuePlanDetialEntryInfo detialEntry=(ValuePlanDetialEntryInfo) cell.getUserObject();
						detialEntry.setXamount(amount);
						cell.setValue(amount);
						KDTEditEvent e=new KDTEditEvent(cell);
						e.setRowIndex(row.getRowIndex());
						e.setColIndex(cell.getColumnIndex());
						e.setValue(amount);
						table_editStopped(e);
					}
				}
			}
		}
		builder = new FDCSQLBuilder();
		builder.appendSql(" select entry.fproject operationPhases,entry.fbatch batch,entry.fproductType productType,st.year year,st.month month,st.quarter quarter,sum(saccount) account,round(sum(sarea),2) area,round(sum(samount),0) amount,(case when sum(sarea) is null or sum(sarea)=0 then 0 else round(sum(samount)/sum(sarea),0) end )price");
		builder.appendSql(" from T_MAR_ValueInputEntry entry left join T_MAR_ValueInput vi on vi.fid=entry.fheadId left join T_BD_IndexVersion version on version.fid=vi.findexVersionId");
		builder.appendSql(" left join (select year(fbusAdscriptionDate) year,month(fbusAdscriptionDate) month,quarter(fbusAdscriptionDate) quarter,banEntry.FBanBasedataEntryId buildId,room.fproductTypeId productTypeId,count(*) saccount,sum(case sign.fsellType when 'PlanningSell' then sign.fstrdPlanBuildingArea when 'PreSell' then sign.fbulidingArea else sign.fstrdActualBuildingArea end) sarea,sum(sign.fcontractTotalAmount) samount");
		builder.appendSql(" from t_she_signManage sign left join t_she_room room on room.fid=sign.froomId left join T_SHE_BanBasedataEntryList banEntry on banEntry.fheadId=room.fbuildingId where sign.fbizState in('SignApple','SignAudit') and banEntry.FBanBasedataEntryId is not null group by banEntry.FBanBasedataEntryId,room.fproductTypeId,year(fbusAdscriptionDate),month(fbusAdscriptionDate),quarter(fbusAdscriptionDate)) st on st.buildId=entry.fbuildId and st.productTypeId=entry.fproductTypeId");
		builder.appendSql(" left join (select max(version.fnumber) indexVersion,vi.fprojectid from T_MAR_ValueInput vi left join T_BD_IndexVersion version on version.fid=vi.findexVersionId where vi.fisLatest=1 group by vi.fprojectid) maxVi on maxVi.indexVersion=version.fnumber and maxVi.fprojectId=vi.fprojectId");
		builder.appendSql(" where maxVi.indexVersion is not null and vi.fisLatest=1");
		if(projectString!=null&&!"".equals(projectString)){
			builder.appendSql(" and entry.fprojectid in("+projectString+")");
    	}else{
    		builder.appendSql(" and entry.fprojectid = 'null'");
    	}
		builder.appendSql(" group by entry.fproject,entry.fbatch,entry.fproductType,st.year,st.month,st.quarter");
		rowSet=builder.executeQuery();
		while(rowSet.next()){
			String key=rowSet.getString("operationPhases")+rowSet.getString("batch")+rowSet.getString("productType");
			int year=rowSet.getInt("year");
			int month=rowSet.getInt("month");
			int quarter=rowSet.getInt("quarter");
			
			int account=rowSet.getInt("account");
			BigDecimal area=rowSet.getBigDecimal("area");
			BigDecimal price=rowSet.getBigDecimal("price");
			BigDecimal amount=rowSet.getBigDecimal("amount");
			if(rowMap.get(key)!=null){
				IRow row=(IRow) rowMap.get(key);
				if(year==beginYear){
					ICell cell=row.getCell(year+"Y"+month+"M"+"saccount");
					if(cell!=null){
						ValuePlanDetialEntryInfo detialEntry=(ValuePlanDetialEntryInfo) cell.getUserObject();
						detialEntry.setXaccount(account);
						cell.setValue(account);
						KDTEditEvent e=new KDTEditEvent(cell);
						e.setRowIndex(row.getRowIndex());
						e.setColIndex(cell.getColumnIndex());
						e.setValue(account);
						table_editStopped(e);
					}
					cell=row.getCell(year+"Y"+month+"M"+"sarea");
					if(cell!=null){
						ValuePlanDetialEntryInfo detialEntry=(ValuePlanDetialEntryInfo) cell.getUserObject();
						detialEntry.setXarea(area);
						cell.setValue(area);
						KDTEditEvent e=new KDTEditEvent(cell);
						e.setRowIndex(row.getRowIndex());
						e.setColIndex(cell.getColumnIndex());
						e.setValue(area);
						table_editStopped(e);
					}
					cell=row.getCell(year+"Y"+month+"M"+"sprice");
					if(cell!=null){
						ValuePlanDetialEntryInfo detialEntry=(ValuePlanDetialEntryInfo) cell.getUserObject();
						detialEntry.setXprice(price);
						cell.setValue(price);
					}
					cell=row.getCell(year+"Y"+month+"M"+"samount");
					if(cell!=null){
						ValuePlanDetialEntryInfo detialEntry=(ValuePlanDetialEntryInfo) cell.getUserObject();
						detialEntry.setXamount(amount);
						cell.setValue(amount);
						KDTEditEvent e=new KDTEditEvent(cell);
						e.setRowIndex(row.getRowIndex());
						e.setColIndex(cell.getColumnIndex());
						e.setValue(amount);
						table_editStopped(e);
					}
				}else{
					ICell cell=row.getCell(year+"Y"+quarter+"Q"+"saccount");
					if(cell!=null){
						ValuePlanDetialEntryInfo detialEntry=(ValuePlanDetialEntryInfo) cell.getUserObject();
						detialEntry.setXaccount(account);
						cell.setValue(account);
						KDTEditEvent e=new KDTEditEvent(cell);
						e.setRowIndex(row.getRowIndex());
						e.setColIndex(cell.getColumnIndex());
						e.setValue(account);
						table_editStopped(e);
					}
					cell=row.getCell(year+"Y"+quarter+"Q"+"sarea");
					if(cell!=null){
						ValuePlanDetialEntryInfo detialEntry=(ValuePlanDetialEntryInfo) cell.getUserObject();
						detialEntry.setXarea(area);
						cell.setValue(area);
						KDTEditEvent e=new KDTEditEvent(cell);
						e.setRowIndex(row.getRowIndex());
						e.setColIndex(cell.getColumnIndex());
						e.setValue(area);
						table_editStopped(e);
					}
					cell=row.getCell(year+"Y"+quarter+"Q"+"sprice");
					if(cell!=null){
						ValuePlanDetialEntryInfo detialEntry=(ValuePlanDetialEntryInfo) cell.getUserObject();
						detialEntry.setXprice(price);
						cell.setValue(price);
					}
					cell=row.getCell(year+"Y"+quarter+"Q"+"samount");
					if(cell!=null){
						ValuePlanDetialEntryInfo detialEntry=(ValuePlanDetialEntryInfo) cell.getUserObject();
						detialEntry.setXamount(amount);
						cell.setValue(amount);
						KDTEditEvent e=new KDTEditEvent(cell);
						e.setRowIndex(row.getRowIndex());
						e.setColIndex(cell.getColumnIndex());
						e.setValue(amount);
						table_editStopped(e);
					}
				}
			}
		}
	}
	protected IObjectValue createNewData() {
		ValuePlanInfo info=new ValuePlanInfo();
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		info.setBizDate(now);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		info.setYear(year);
		info.setMonth(month);
		
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("name");
		sel.add("engineeProject.*");
		try {
			if(this.getUIContext().get("project")!=null){
				ProjectBaseInfo pb = ProjectBaseFactory.getRemoteInstance().getProjectBaseInfo(new ObjectUuidPK(((ProjectBaseInfo)this.getUIContext().get("project")).getId()),sel);
				info.setProject(pb);
				info.setOrgUnit(pb.getEngineeProject());
				
				RptParams param=new RptParams();
				OperationPhasesCollection col = OperationPhasesFactory.getRemoteInstance().getOperationPhasesCollection("select id from where projectBase.id='"+info.getProject().getId().toString()+"'");
				String projectString="";
				for(int i=0;i<col.size();i++){
					if(i==col.size()-1){
						projectString=projectString+"'"+col.get(i).getId().toString()+"'";
					}else{
						projectString=projectString+"'"+col.get(i).getId().toString()+"',";
					}
				}
				param.setObject("project", projectString);
				param=DynamicValueReportFacadeFactory.getRemoteInstance().query(param);
				
				RptRowSet rs = (RptRowSet)((RptParams)param).getObject("rowset");
				while(rs.next()){
					ValuePlanEntryInfo entry=new ValuePlanEntryInfo();
					entry.setProject(rs.getString("project"));
					entry.setOperationPhases(rs.getString("operationPhases"));
					entry.setBatch(rs.getString("batch"));
					entry.setProductType(rs.getString("productType"));
					
					if(rs.getBigDecimal("account")!=null){
						entry.setAccount(rs.getInt("account"));
					}
					entry.setArea(rs.getBigDecimal("area"));
					entry.setPrice(rs.getBigDecimal("price"));
					entry.setAmount(rs.getBigDecimal("amount"));
					
					if(rs.getBigDecimal("saccount")!=null){
						entry.setSaccount(rs.getInt("saccount"));
					}
					entry.setSarea(rs.getBigDecimal("sarea"));
					entry.setSprice(rs.getBigDecimal("sprice"));
					entry.setSamount(rs.getBigDecimal("samount"));
					
					if(rs.getBigDecimal("daccount")!=null){
						entry.setDaccount(rs.getInt("daccount"));
					}
					entry.setDarea(rs.getBigDecimal("darea"));
					entry.setDprice(rs.getBigDecimal("dprice"));
					entry.setDamount(rs.getBigDecimal("damount"));
					
					if(rs.getBigDecimal("paccount")!=null){
						entry.setPaccount(rs.getInt("paccount"));
					}
					entry.setParea(rs.getBigDecimal("parea"));
					entry.setPprice(rs.getBigDecimal("pprice"));
					entry.setPamount(rs.getBigDecimal("pamount"));
					
					info.getEntry().add(entry);
				}
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		info.setState(FDCBillStateEnum.SAVED);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		
		info.setCreator(null);
		info.setCreateTime(null);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setLastUpdateUser(null);
		info.setLastUpdateTime(null);
		
		return info;
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel=super.getSelectors();
		sel.add("state");
		sel.add("orgUnit.*");
		sel.add("CU.*");
		sel.add("bizDate");
		sel.add("entry.*");
		sel.add("entry.detialEntry.*");
		return sel;
	}
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtProject);
	}
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
//		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
		
	}
	protected void spnMonth_stateChanged(ChangeEvent e) throws Exception {
		createTable(this.editData,this.spnYear.getIntegerVlaue(),this.spnPlanYear.getIntegerVlaue(),this.spnMonth.getIntegerVlaue());
		setXSValue();
	}
	protected void spnPlanYear_stateChanged(ChangeEvent e) throws Exception {
		createTable(this.editData,this.spnYear.getIntegerVlaue(),this.spnPlanYear.getIntegerVlaue(),this.spnMonth.getIntegerVlaue());
		setXSValue();
	}
	protected void spnYear_stateChanged(ChangeEvent e) throws Exception {
		createTable(this.editData,this.spnYear.getIntegerVlaue(),this.spnPlanYear.getIntegerVlaue(),this.spnMonth.getIntegerVlaue());
		setXSValue();
	}
	protected void addColumn(KDTable table,ValuePlanDetialTypeEnum type,int year,int month,int quarter){
		IRow headRow1=table.getHeadRow(0);
		IRow headRow2=table.getHeadRow(1);
		IRow headRow3=table.getHeadRow(2);
		IRow headRow4=table.getHeadRow(3);
		
		KDFormattedTextField txtaccount = new KDFormattedTextField();
		txtaccount.setDataType(KDFormattedTextField.INTEGER_TYPE);
		txtaccount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtaccount.setNegatived(false);
		txtaccount.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor account = new KDTDefaultCellEditor(txtaccount);
		
		KDFormattedTextField txtarea = new KDFormattedTextField();
		txtarea.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtarea.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtarea.setNegatived(false);
		txtarea.setMinimumValue(FDCHelper.ZERO);
		txtarea.setPrecision(2);
		KDTDefaultCellEditor area = new KDTDefaultCellEditor(txtarea);
		
		KDFormattedTextField txtprice = new KDFormattedTextField();
		txtprice.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtprice.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtprice.setNegatived(false);
		txtprice.setMinimumValue(FDCHelper.ZERO);
		txtprice.setPrecision(0);
		KDTDefaultCellEditor price = new KDTDefaultCellEditor(txtprice);
		
		
		String key=null;
		IColumn coloum=table.addColumn();
		int cColoumIndexBegin=coloum.getColumnIndex();
		coloum.setEditor(account);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		if(type.equals(ValuePlanDetialTypeEnum.YEAR)){
			key=year+"Y"+"caccount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年合计");
			headRow2.getCell(key).setValue("本年可售货值");
			headRow3.getCell(key).setValue("年初存量货值");
			headRow4.getCell(key).setValue("套[个]");
		}else if(type.equals(ValuePlanDetialTypeEnum.QUARTER)){
			key=year+"Y"+quarter+"Q"+"caccount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+quarter+"季度");
			headRow2.getCell(key).setValue("本季度可售货值");
			headRow3.getCell(key).setValue("季度初存量货值");
			headRow4.getCell(key).setValue("套[个]");
		}else if(type.equals(ValuePlanDetialTypeEnum.MONTH)){
			key=year+"Y"+month+"M"+"caccount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+month+"月份");
			headRow2.getCell(key).setValue("本月可售货值");
			headRow3.getCell(key).setValue("月初存量货值");
			headRow4.getCell(key).setValue("套[个]");
		}
		
		coloum=table.addColumn();
		coloum.setEditor(area);
		coloum.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		if(type.equals(ValuePlanDetialTypeEnum.YEAR)){
			key=year+"Y"+"carea";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年合计");
			headRow2.getCell(key).setValue("本年可售货值");
			headRow3.getCell(key).setValue("年初存量货值");
			headRow4.getCell(key).setValue("面积[㎡]");
		}else if(type.equals(ValuePlanDetialTypeEnum.QUARTER)){
			key=year+"Y"+quarter+"Q"+"carea";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+quarter+"季度");
			headRow2.getCell(key).setValue("本季度可售货值");
			headRow3.getCell(key).setValue("季度初存量货值");
			headRow4.getCell(key).setValue("面积[㎡]");
		}else if(type.equals(ValuePlanDetialTypeEnum.MONTH)){
			key=year+"Y"+month+"M"+"carea";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+month+"月份");
			headRow2.getCell(key).setValue("本月可售货值");
			headRow3.getCell(key).setValue("月初存量货值");
			headRow4.getCell(key).setValue("面积[㎡]");
		}
		
		coloum=table.addColumn();
		coloum.setEditor(price);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		if(type.equals(ValuePlanDetialTypeEnum.YEAR)){
			key=year+"Y"+"cprice";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年合计");
			headRow2.getCell(key).setValue("本年可售货值");
			headRow3.getCell(key).setValue("年初存量货值");
			headRow4.getCell(key).setValue("均价[元/㎡]");
		}else if(type.equals(ValuePlanDetialTypeEnum.QUARTER)){
			key=year+"Y"+quarter+"Q"+"cprice";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+quarter+"季度");
			headRow2.getCell(key).setValue("本季度可售货值");
			headRow3.getCell(key).setValue("季度初存量货值");
			headRow4.getCell(key).setValue("均价[元/㎡]");
		}else if(type.equals(ValuePlanDetialTypeEnum.MONTH)){
			key=year+"Y"+month+"M"+"cprice";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+month+"月份");
			headRow2.getCell(key).setValue("本月可售货值");
			headRow3.getCell(key).setValue("月初存量货值");
			headRow4.getCell(key).setValue("均价[元/㎡]");
		}
		
		coloum=table.addColumn();
		int cColoumIndexEnd=coloum.getColumnIndex();
		coloum.setEditor(price);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		if(type.equals(ValuePlanDetialTypeEnum.YEAR)){
			key=year+"Y"+"camount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年合计");
			headRow2.getCell(key).setValue("本年可售货值");
			headRow3.getCell(key).setValue("年初存量货值");
			headRow4.getCell(key).setValue("金额[万元]");
		}else if(type.equals(ValuePlanDetialTypeEnum.QUARTER)){
			key=year+"Y"+quarter+"Q"+"camount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+quarter+"季度");
			headRow2.getCell(key).setValue("本季度可售货值");
			headRow3.getCell(key).setValue("季度初存量货值");
			headRow4.getCell(key).setValue("金额[万元]");
		}else if(type.equals(ValuePlanDetialTypeEnum.MONTH)){
			key=year+"Y"+month+"M"+"camount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+month+"月份");
			headRow2.getCell(key).setValue("本月可售货值");
			headRow3.getCell(key).setValue("月初存量货值");
			headRow4.getCell(key).setValue("金额[万元]");
		}
		
		coloum=table.addColumn();
		int xColoumIndexBegin=coloum.getColumnIndex();
		coloum.setEditor(account);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		if(type.equals(ValuePlanDetialTypeEnum.YEAR)){
			key=year+"Y"+"xaccount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年合计");
			headRow2.getCell(key).setValue("本年可售货值");
			headRow3.getCell(key).setValue("本年新增货值");
			headRow4.getCell(key).setValue("套[个]");
		}else if(type.equals(ValuePlanDetialTypeEnum.QUARTER)){
			key=year+"Y"+quarter+"Q"+"xaccount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+quarter+"季度");
			headRow2.getCell(key).setValue("本季度可售货值");
			headRow3.getCell(key).setValue("当季度新增货值");
			headRow4.getCell(key).setValue("套[个]");
		}else if(type.equals(ValuePlanDetialTypeEnum.MONTH)){
			key=year+"Y"+month+"M"+"xaccount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+month+"月份");
			headRow2.getCell(key).setValue("本月可售货值");
			headRow3.getCell(key).setValue("当月新增货值");
			headRow4.getCell(key).setValue("套[个]");
		}
		
		coloum=table.addColumn();
		coloum.setEditor(area);
		coloum.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		if(type.equals(ValuePlanDetialTypeEnum.YEAR)){
			key=year+"Y"+"xarea";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年合计");
			headRow2.getCell(key).setValue("本年可售货值");
			headRow3.getCell(key).setValue("本年新增货值");
			headRow4.getCell(key).setValue("面积[㎡]");
		}else if(type.equals(ValuePlanDetialTypeEnum.QUARTER)){
			key=year+"Y"+quarter+"Q"+"xarea";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+quarter+"季度");
			headRow2.getCell(key).setValue("本季度可售货值");
			headRow3.getCell(key).setValue("当季度新增货值");
			headRow4.getCell(key).setValue("面积[㎡]");
		}else if(type.equals(ValuePlanDetialTypeEnum.MONTH)){
			key=year+"Y"+month+"M"+"xarea";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+month+"月份");
			headRow2.getCell(key).setValue("本月可售货值");
			headRow3.getCell(key).setValue("当月新增货值");
			headRow4.getCell(key).setValue("面积[㎡]");
		}
		
		coloum=table.addColumn();
		coloum.setEditor(price);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		if(type.equals(ValuePlanDetialTypeEnum.YEAR)){
			key=year+"Y"+"xprice";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年合计");
			headRow2.getCell(key).setValue("本年可售货值");
			headRow3.getCell(key).setValue("本年新增货值");
			headRow4.getCell(key).setValue("均价[元/㎡]");
		}else if(type.equals(ValuePlanDetialTypeEnum.QUARTER)){
			key=year+"Y"+quarter+"Q"+"xprice";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+quarter+"季度");
			headRow2.getCell(key).setValue("本季度可售货值");
			headRow3.getCell(key).setValue("当季度新增货值");
			headRow4.getCell(key).setValue("均价[元/㎡]");
		}else if(type.equals(ValuePlanDetialTypeEnum.MONTH)){
			key=year+"Y"+month+"M"+"xprice";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+month+"月份");
			headRow2.getCell(key).setValue("本月可售货值");
			headRow3.getCell(key).setValue("当月新增货值");
			headRow4.getCell(key).setValue("均价[元/㎡]");
		}
		
		coloum=table.addColumn();
		int xColoumIndexEnd=coloum.getColumnIndex();
		coloum.setEditor(price);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		if(type.equals(ValuePlanDetialTypeEnum.YEAR)){
			key=year+"Y"+"xamount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年合计");
			headRow2.getCell(key).setValue("本年可售货值");
			headRow3.getCell(key).setValue("本年新增货值");
			headRow4.getCell(key).setValue("金额[万元]");
		}else if(type.equals(ValuePlanDetialTypeEnum.QUARTER)){
			key=year+"Y"+quarter+"Q"+"xamount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+quarter+"季度");
			headRow2.getCell(key).setValue("本季度可售货值");
			headRow3.getCell(key).setValue("当季度新增货值");
			headRow4.getCell(key).setValue("金额[万元]");
		}else if(type.equals(ValuePlanDetialTypeEnum.MONTH)){
			key=year+"Y"+month+"M"+"xamount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+month+"月份");
			headRow2.getCell(key).setValue("本月可售货值");
			headRow3.getCell(key).setValue("当月新增货值");
			headRow4.getCell(key).setValue("金额[万元]");
		}
		
		coloum=table.addColumn();
		int coloumIndexBegin=coloum.getColumnIndex();
		coloum.setEditor(account);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		if(type.equals(ValuePlanDetialTypeEnum.YEAR)){
			key=year+"Y"+"account";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年合计");
			headRow2.getCell(key).setValue("本年可售货值");
			headRow3.getCell(key).setValue("小计");
			headRow4.getCell(key).setValue("套[个]");
		}else if(type.equals(ValuePlanDetialTypeEnum.QUARTER)){
			key=year+"Y"+quarter+"Q"+"account";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+quarter+"季度");
			headRow2.getCell(key).setValue("本季度可售货值");
			headRow3.getCell(key).setValue("小计");
			headRow4.getCell(key).setValue("套[个]");
		}else if(type.equals(ValuePlanDetialTypeEnum.MONTH)){
			key=year+"Y"+month+"M"+"account";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+month+"月份");
			headRow2.getCell(key).setValue("本月可售货值");
			headRow3.getCell(key).setValue("小计");
			headRow4.getCell(key).setValue("套[个]");
		}
		
		coloum=table.addColumn();
		coloum.setEditor(area);
		coloum.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		if(type.equals(ValuePlanDetialTypeEnum.YEAR)){
			key=year+"Y"+"area";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年合计");
			headRow2.getCell(key).setValue("本年可售货值");
			headRow3.getCell(key).setValue("小计");
			headRow4.getCell(key).setValue("面积[㎡]");
		}else if(type.equals(ValuePlanDetialTypeEnum.QUARTER)){
			key=year+"Y"+quarter+"Q"+"area";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+quarter+"季度");
			headRow2.getCell(key).setValue("本季度可售货值");
			headRow3.getCell(key).setValue("小计");
			headRow4.getCell(key).setValue("面积[㎡]");
		}else if(type.equals(ValuePlanDetialTypeEnum.MONTH)){
			key=year+"Y"+month+"M"+"area";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+month+"月份");
			headRow2.getCell(key).setValue("本月可售货值");
			headRow3.getCell(key).setValue("小计");
			headRow4.getCell(key).setValue("面积[㎡]");
		}
		
		coloum=table.addColumn();
		coloum.setEditor(price);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		if(type.equals(ValuePlanDetialTypeEnum.YEAR)){
			key=year+"Y"+"price";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年合计");
			headRow2.getCell(key).setValue("本年可售货值");
			headRow3.getCell(key).setValue("小计");
			headRow4.getCell(key).setValue("均价[元/㎡]");
		}else if(type.equals(ValuePlanDetialTypeEnum.QUARTER)){
			key=year+"Y"+quarter+"Q"+"price";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+quarter+"季度");
			headRow2.getCell(key).setValue("本季度可售货值");
			headRow3.getCell(key).setValue("小计");
			headRow4.getCell(key).setValue("均价[元/㎡]");
		}else if(type.equals(ValuePlanDetialTypeEnum.MONTH)){
			key=year+"Y"+month+"M"+"price";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+month+"月份");
			headRow2.getCell(key).setValue("本月可售货值");
			headRow3.getCell(key).setValue("小计");
			headRow4.getCell(key).setValue("均价[元/㎡]");
		}
		
		coloum=table.addColumn();
		int coloumIndexEnd=coloum.getColumnIndex();
		coloum.setEditor(price);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		if(type.equals(ValuePlanDetialTypeEnum.YEAR)){
			key=year+"Y"+"amount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年合计");
			headRow2.getCell(key).setValue("本年可售货值");
			headRow3.getCell(key).setValue("小计");
			headRow4.getCell(key).setValue("金额[万元]");
		}else if(type.equals(ValuePlanDetialTypeEnum.QUARTER)){
			key=year+"Y"+quarter+"Q"+"amount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+quarter+"季度");
			headRow2.getCell(key).setValue("本季度可售货值");
			headRow3.getCell(key).setValue("小计");
			headRow4.getCell(key).setValue("金额[万元]");
		}else if(type.equals(ValuePlanDetialTypeEnum.MONTH)){
			key=year+"Y"+month+"M"+"amount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+month+"月份");
			headRow2.getCell(key).setValue("本月可售货值");
			headRow3.getCell(key).setValue("小计");
			headRow4.getCell(key).setValue("金额[万元]");
		}
		
		coloum=table.addColumn();
		int sColoumIndexBegin=coloum.getColumnIndex();
		coloum.setEditor(account);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		if(type.equals(ValuePlanDetialTypeEnum.YEAR)){
			key=year+"Y"+"saccount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年合计");
			headRow2.getCell(key).setValue("本年销售货值");
			headRow3.getCell(key).setValue("本年销售货值");
			headRow4.getCell(key).setValue("套[个]");
		}else if(type.equals(ValuePlanDetialTypeEnum.QUARTER)){
			key=year+"Y"+quarter+"Q"+"saccount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+quarter+"季度");
			headRow2.getCell(key).setValue("本季度销售货值");
			headRow3.getCell(key).setValue("本季度销售货值");
			headRow4.getCell(key).setValue("套[个]");
		}else if(type.equals(ValuePlanDetialTypeEnum.MONTH)){
			key=year+"Y"+month+"M"+"saccount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+month+"月份");
			headRow2.getCell(key).setValue("本月销售货值");
			headRow3.getCell(key).setValue("本月销售货值");
			headRow4.getCell(key).setValue("套[个]");
		}
		
		coloum=table.addColumn();
		coloum.setEditor(area);
		coloum.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		if(type.equals(ValuePlanDetialTypeEnum.YEAR)){
			key=year+"Y"+"sarea";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年合计");
			headRow2.getCell(key).setValue("本年销售货值");
			headRow3.getCell(key).setValue("本年销售货值");
			headRow4.getCell(key).setValue("面积[㎡]");
		}else if(type.equals(ValuePlanDetialTypeEnum.QUARTER)){
			key=year+"Y"+quarter+"Q"+"sarea";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+quarter+"季度");
			headRow2.getCell(key).setValue("本季度销售货值");
			headRow3.getCell(key).setValue("本季度销售货值");
			headRow4.getCell(key).setValue("面积[㎡]");
		}else if(type.equals(ValuePlanDetialTypeEnum.MONTH)){
			key=year+"Y"+month+"M"+"sarea";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+month+"月份");
			headRow2.getCell(key).setValue("本月销售货值");
			headRow3.getCell(key).setValue("本月销售货值");
			headRow4.getCell(key).setValue("面积[㎡]");
		}
		
		coloum=table.addColumn();
		coloum.setEditor(price);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		if(type.equals(ValuePlanDetialTypeEnum.YEAR)){
			key=year+"Y"+"sprice";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年合计");
			headRow2.getCell(key).setValue("本年销售货值");
			headRow3.getCell(key).setValue("本年销售货值");
			headRow4.getCell(key).setValue("均价[元/㎡]");
		}else if(type.equals(ValuePlanDetialTypeEnum.QUARTER)){
			key=year+"Y"+quarter+"Q"+"sprice";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+quarter+"季度");
			headRow2.getCell(key).setValue("本季度销售货值");
			headRow3.getCell(key).setValue("本季度销售货值");
			headRow4.getCell(key).setValue("均价[元/㎡]");
		}else if(type.equals(ValuePlanDetialTypeEnum.MONTH)){
			key=year+"Y"+month+"M"+"sprice";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+month+"月份");
			headRow2.getCell(key).setValue("本月销售货值");
			headRow3.getCell(key).setValue("本月销售货值");
			headRow4.getCell(key).setValue("均价[元/㎡]");
		}
		
		coloum=table.addColumn();
		int sColoumIndexEnd=coloum.getColumnIndex();
		coloum.setEditor(price);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		int backAmountColoumIndex=0;
		if(type.equals(ValuePlanDetialTypeEnum.YEAR)){
			key=year+"Y"+"samount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年合计");
			headRow2.getCell(key).setValue("本年销售货值");
			headRow3.getCell(key).setValue("本年销售货值");
			headRow4.getCell(key).setValue("金额[万元]");
			
			coloum=table.addColumn();
			backAmountColoumIndex=coloum.getColumnIndex();
			coloum.setEditor(price);
			coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
			coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			
			key=year+"Y"+"backAmount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年合计");
			headRow2.getCell(key).setValue("回款");
			headRow3.getCell(key).setValue("回款");
			headRow4.getCell(key).setValue("金额[万元]");
		}else if(type.equals(ValuePlanDetialTypeEnum.QUARTER)){
			key=year+"Y"+quarter+"Q"+"samount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+quarter+"季度");
			headRow2.getCell(key).setValue("本季度销售货值");
			headRow3.getCell(key).setValue("本季度销售货值");
			headRow4.getCell(key).setValue("金额[万元]");
			
			coloum=table.addColumn();
			backAmountColoumIndex=coloum.getColumnIndex();
			coloum.setEditor(price);
			coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
			coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			
			key=year+"Y"+quarter+"Q"+"backAmount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+quarter+"季度");
			headRow2.getCell(key).setValue("回款");
			headRow3.getCell(key).setValue("回款");
			headRow4.getCell(key).setValue("金额[万元]");
		}else if(type.equals(ValuePlanDetialTypeEnum.MONTH)){
			key=year+"Y"+month+"M"+"samount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+month+"月份");
			headRow2.getCell(key).setValue("本月可售货值");
			headRow3.getCell(key).setValue("小计");
			headRow4.getCell(key).setValue("金额[万元]");
			
			coloum=table.addColumn();
			backAmountColoumIndex=coloum.getColumnIndex();
			coloum.setEditor(price);
			coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
			coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			
			key=year+"Y"+month+"M"+"backAmount";
			coloum.setKey(key);
			headRow1.getCell(key).setValue(year+"年"+month+"月份");
			headRow2.getCell(key).setValue("回款");
			headRow3.getCell(key).setValue("回款");
			headRow4.getCell(key).setValue("金额[万元]");
		}
		
		table.getHeadMergeManager().mergeBlock(0, cColoumIndexBegin, 0, cColoumIndexBegin+16);
		table.getHeadMergeManager().mergeBlock(1, cColoumIndexBegin, 1, coloumIndexEnd);
		table.getHeadMergeManager().mergeBlock(1, sColoumIndexBegin, 2, sColoumIndexEnd);
		table.getHeadMergeManager().mergeBlock(1, backAmountColoumIndex, 2, backAmountColoumIndex);
		
		
		table.getHeadMergeManager().mergeBlock(2, cColoumIndexBegin, 2, cColoumIndexEnd);
		table.getHeadMergeManager().mergeBlock(2, xColoumIndexBegin, 2, xColoumIndexEnd);
		table.getHeadMergeManager().mergeBlock(2, coloumIndexBegin, 2, coloumIndexEnd);
		table.getHeadMergeManager().mergeBlock(2, sColoumIndexBegin, 2, sColoumIndexEnd);
	}
	protected void addProjectColumn(KDTable table){
		IRow headRow1=table.getHeadRow(0);
		IRow headRow2=table.getHeadRow(1);
		IRow headRow3=table.getHeadRow(2);
		IRow headRow4=table.getHeadRow(3);
		
		KDFormattedTextField txtaccount = new KDFormattedTextField();
		txtaccount.setDataType(KDFormattedTextField.INTEGER_TYPE);
		txtaccount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtaccount.setNegatived(false);
		txtaccount.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor account = new KDTDefaultCellEditor(txtaccount);
		
		KDFormattedTextField txtarea = new KDFormattedTextField();
		txtarea.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtarea.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtarea.setNegatived(false);
		txtarea.setMinimumValue(FDCHelper.ZERO);
		txtarea.setPrecision(2);
		KDTDefaultCellEditor area = new KDTDefaultCellEditor(txtarea);
		
		KDFormattedTextField txtprice = new KDFormattedTextField();
		txtprice.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtprice.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtprice.setNegatived(false);
		txtprice.setMinimumValue(FDCHelper.ZERO);
		txtprice.setPrecision(0);
		KDTDefaultCellEditor price = new KDTDefaultCellEditor(txtprice);
		
		String key=null;
		IColumn coloum=table.addColumn();
		key="project";
		coloum.setKey(key);
		headRow1.getCell(key).setValue("项目");
		headRow2.getCell(key).setValue("项目");
		headRow3.getCell(key).setValue("项目");
		headRow4.getCell(key).setValue("项目");
		
		coloum=table.addColumn();
		key="operationPhases";
		coloum.setKey(key);
		headRow1.getCell(key).setValue("分期");
		headRow2.getCell(key).setValue("分期");
		headRow3.getCell(key).setValue("分期");
		headRow4.getCell(key).setValue("分期");
		
		coloum=table.addColumn();
		key="batch";
		coloum.setKey(key);
		headRow1.getCell(key).setValue("计划交房批次");
		headRow2.getCell(key).setValue("计划交房批次");
		headRow3.getCell(key).setValue("计划交房批次");
		headRow4.getCell(key).setValue("计划交房批次");
		
		coloum=table.addColumn();
		key="productType";
		coloum.setKey(key);
		headRow1.getCell(key).setValue("物业形态");
		headRow2.getCell(key).setValue("物业形态");
		headRow3.getCell(key).setValue("物业形态");
		headRow4.getCell(key).setValue("物业形态");
		
		coloum=table.addColumn();
		key="account";
		coloum.setKey(key);
		coloum.setEditor(account);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		headRow1.getCell(key).setValue("动态版总货值");
		headRow2.getCell(key).setValue("套数");
		headRow3.getCell(key).setValue("套数");
		headRow4.getCell(key).setValue("[个]");
		
		coloum=table.addColumn();
		key="area";
		coloum.setKey(key);
		coloum.setEditor(area);
		coloum.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow1.getCell(key).setValue("动态版总货值");
		headRow2.getCell(key).setValue("面积");
		headRow3.getCell(key).setValue("面积");
		headRow4.getCell(key).setValue("[㎡]");
		
		coloum=table.addColumn();
		key="price";
		coloum.setKey(key);
		coloum.setEditor(price);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow1.getCell(key).setValue("动态版总货值");
		headRow2.getCell(key).setValue("均价");
		headRow3.getCell(key).setValue("均价");
		headRow4.getCell(key).setValue("[元/㎡]");
		
		coloum=table.addColumn();
		key="amount";
		coloum.setKey(key);
		coloum.setEditor(price);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow1.getCell(key).setValue("动态版总货值");
		headRow2.getCell(key).setValue("金额");
		headRow3.getCell(key).setValue("金额");
		headRow4.getCell(key).setValue("[万元]");
		
		coloum=table.addColumn();
		key="saccount";
		coloum.setKey(key);
		coloum.setEditor(account);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		headRow1.getCell(key).setValue("已售货值");
		headRow2.getCell(key).setValue("套数");
		headRow3.getCell(key).setValue("套数");
		headRow4.getCell(key).setValue("[个]");
		
		coloum=table.addColumn();
		key="sarea";
		coloum.setKey(key);
		coloum.setEditor(area);
		coloum.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow1.getCell(key).setValue("已售货值");
		headRow2.getCell(key).setValue("面积");
		headRow3.getCell(key).setValue("面积");
		headRow4.getCell(key).setValue("[㎡]");
		
		coloum=table.addColumn();
		key="sprice";
		coloum.setKey(key);
		coloum.setEditor(price);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow1.getCell(key).setValue("已售货值");
		headRow2.getCell(key).setValue("均价");
		headRow3.getCell(key).setValue("均价");
		headRow4.getCell(key).setValue("[元/㎡]");
		
		coloum=table.addColumn();
		key="samount";
		coloum.setKey(key);
		coloum.setEditor(price);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow1.getCell(key).setValue("已售货值");
		headRow2.getCell(key).setValue("金额");
		headRow3.getCell(key).setValue("金额");
		headRow4.getCell(key).setValue("[万元]");
		
		coloum=table.addColumn();
		key="daccount";
		coloum.setKey(key);
		coloum.setEditor(account);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		headRow1.getCell(key).setValue("待售货值");
		headRow2.getCell(key).setValue("套数");
		headRow3.getCell(key).setValue("套数");
		headRow4.getCell(key).setValue("[个]");
		
		coloum=table.addColumn();
		key="darea";
		coloum.setKey(key);
		coloum.setEditor(area);
		coloum.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow1.getCell(key).setValue("待售货值");
		headRow2.getCell(key).setValue("面积");
		headRow3.getCell(key).setValue("面积");
		headRow4.getCell(key).setValue("[㎡]");
		
		coloum=table.addColumn();
		key="dprice";
		coloum.setKey(key);
		coloum.setEditor(price);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow1.getCell(key).setValue("待售货值");
		headRow2.getCell(key).setValue("均价");
		headRow3.getCell(key).setValue("均价");
		headRow4.getCell(key).setValue("[元/㎡]");
		
		coloum=table.addColumn();
		key="damount";
		coloum.setKey(key);
		coloum.setEditor(price);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow1.getCell(key).setValue("待售货值");
		headRow2.getCell(key).setValue("金额");
		headRow3.getCell(key).setValue("金额");
		headRow4.getCell(key).setValue("[万元]");
		
		coloum=table.addColumn();
		key="paccount";
		coloum.setKey(key);
		coloum.setEditor(account);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
		headRow1.getCell(key).setValue("规划货值");
		headRow2.getCell(key).setValue("套数");
		headRow3.getCell(key).setValue("套数");
		headRow4.getCell(key).setValue("[个]");
		
		coloum=table.addColumn();
		key="parea";
		coloum.setKey(key);
		coloum.setEditor(area);
		coloum.getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow1.getCell(key).setValue("规划货值");
		headRow2.getCell(key).setValue("面积");
		headRow3.getCell(key).setValue("面积");
		headRow4.getCell(key).setValue("[㎡]");
		
		coloum=table.addColumn();
		key="pprice";
		coloum.setKey(key);
		coloum.setEditor(price);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow1.getCell(key).setValue("规划货值");
		headRow2.getCell(key).setValue("均价");
		headRow3.getCell(key).setValue("均价");
		headRow4.getCell(key).setValue("[元/㎡]");
		
		coloum=table.addColumn();
		key="pamount";
		coloum.setKey(key);
		coloum.setEditor(price);
		coloum.getStyleAttributes().setNumberFormat("#,##0;-#,##0");
		coloum.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		headRow1.getCell(key).setValue("规划货值");
		headRow2.getCell(key).setValue("金额");
		headRow3.getCell(key).setValue("金额");
		headRow4.getCell(key).setValue("[万元]");
		
		table.getHeadMergeManager().mergeBlock(0, 0, 3, 0);
		table.getHeadMergeManager().mergeBlock(0, 1, 3, 1);
		table.getHeadMergeManager().mergeBlock(0, 2, 3, 2);
		table.getHeadMergeManager().mergeBlock(0, 3, 3, 3);
		
		table.getHeadMergeManager().mergeBlock(0, 4, 0, 7);
		table.getHeadMergeManager().mergeBlock(0, 8, 0, 11);
		table.getHeadMergeManager().mergeBlock(0, 12, 0, 15);
		table.getHeadMergeManager().mergeBlock(0, 16, 0, 19);
		
		for(int i=4;i<20;i++){
			table.getHeadMergeManager().mergeBlock(1, i, 2, i);
		}
	}
	protected void createTable(ValuePlanInfo info,int beginYear,int planYear,int beginMonth){
		this.panel.removeAll();
		table=new KDTable();
		table.checkParsed();
		FDCTableHelper.disableDelete(table);
		table.addHeadRow();
		table.addHeadRow();
		table.addHeadRow();
		table.addHeadRow();
		
		addProjectColumn(table);
		for(int i=beginYear;i<beginYear+planYear;i++){
			if(i==beginYear){
				addColumn(table,ValuePlanDetialTypeEnum.YEAR,i,0,0);
				table.getColumn(i+"Y"+"caccount").getStyleAttributes().setLocked(false);
				table.getColumn(i+"Y"+"caccount").setRequired(true);
				
				table.getColumn(i+"Y"+"carea").getStyleAttributes().setLocked(false);
				table.getColumn(i+"Y"+"carea").setRequired(true);
				
				table.getColumn(i+"Y"+"camount").getStyleAttributes().setLocked(false);
				table.getColumn(i+"Y"+"camount").setRequired(true);
				for(int k=1;k<13;k++){
					addColumn(table,ValuePlanDetialTypeEnum.MONTH,i,k,0);
					if(k>=beginMonth){
						table.getColumn(i+"Y"+k+"M"+"xaccount").getStyleAttributes().setLocked(false);
						table.getColumn(i+"Y"+k+"M"+"xaccount").setRequired(true);
						
						table.getColumn(i+"Y"+k+"M"+"xarea").getStyleAttributes().setLocked(false);
						table.getColumn(i+"Y"+k+"M"+"xarea").setRequired(true);
						
						table.getColumn(i+"Y"+k+"M"+"xamount").getStyleAttributes().setLocked(false);
						table.getColumn(i+"Y"+k+"M"+"xamount").setRequired(true);
						
						table.getColumn(i+"Y"+k+"M"+"saccount").getStyleAttributes().setLocked(false);
						table.getColumn(i+"Y"+k+"M"+"saccount").setRequired(true);
						
						table.getColumn(i+"Y"+k+"M"+"sarea").getStyleAttributes().setLocked(false);
						table.getColumn(i+"Y"+k+"M"+"sarea").setRequired(true);
						
						table.getColumn(i+"Y"+k+"M"+"samount").getStyleAttributes().setLocked(false);
						table.getColumn(i+"Y"+k+"M"+"samount").setRequired(true);
					}
					table.getColumn(i+"Y"+k+"M"+"backAmount").getStyleAttributes().setLocked(false);
					table.getColumn(i+"Y"+k+"M"+"backAmount").setRequired(true);
				}
			}else{
				addColumn(table,ValuePlanDetialTypeEnum.YEAR,i,0,0);
				for(int k=1;k<5;k++){
					addColumn(table,ValuePlanDetialTypeEnum.QUARTER,i,0,k);
					
					table.getColumn(i+"Y"+k+"Q"+"xaccount").getStyleAttributes().setLocked(false);
					table.getColumn(i+"Y"+k+"Q"+"xaccount").setRequired(true);
					
					table.getColumn(i+"Y"+k+"Q"+"xarea").getStyleAttributes().setLocked(false);
					table.getColumn(i+"Y"+k+"Q"+"xarea").setRequired(true);
					
					table.getColumn(i+"Y"+k+"Q"+"xamount").getStyleAttributes().setLocked(false);
					table.getColumn(i+"Y"+k+"Q"+"xamount").setRequired(true);
					
					table.getColumn(i+"Y"+k+"Q"+"saccount").getStyleAttributes().setLocked(false);
					table.getColumn(i+"Y"+k+"Q"+"saccount").setRequired(true);
					
					table.getColumn(i+"Y"+k+"Q"+"sarea").getStyleAttributes().setLocked(false);
					table.getColumn(i+"Y"+k+"Q"+"sarea").setRequired(true);
					
					table.getColumn(i+"Y"+k+"Q"+"samount").getStyleAttributes().setLocked(false);
					table.getColumn(i+"Y"+k+"Q"+"samount").setRequired(true);
					
					table.getColumn(i+"Y"+k+"Q"+"backAmount").getStyleAttributes().setLocked(false);
					table.getColumn(i+"Y"+k+"Q"+"backAmount").setRequired(true);
				}
			}
		}
		
		IRow headRow1=table.getHeadRow(0);
		IRow headRow2=table.getHeadRow(1);
		IRow headRow3=table.getHeadRow(2);
		IRow headRow4=table.getHeadRow(3);
		
		IColumn coloum=table.addColumn();
		coloum.setKey("remark");
		headRow1.getCell("remark").setValue("说明");
		headRow2.getCell("remark").setValue("说明");
		headRow3.getCell("remark").setValue("说明");
		headRow4.getCell("remark").setValue("说明");
		table.getHeadMergeManager().mergeBlock(0, coloum.getColumnIndex(), 3, coloum.getColumnIndex());
		coloum.getStyleAttributes().setLocked(false);
		coloum.setRequired(true);
		
		for(int i=0;i<table.getColumnCount();i++){
			if(!table.getColumn(i).isRequired()){
				table.getColumn(i).getStyleAttributes().setLocked(true);
				table.getColumn(i).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_DISABLE_BG_COLOR);
			}
		}
		KDContainer contEntry = new KDContainer();
		contEntry.setName(table.getName());
		contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        
		contEntry.getContentPane().add(table, BorderLayout.CENTER);
		
		this.panel.add(contEntry,"销售计划");
		
		loadEntry(info,beginYear,planYear);
		
		table.addKDTEditListener(new KDTEditAdapter() {
			public void editStopped(KDTEditEvent e) {
				table_editStopped(e);
			}
		});
	}
	protected void setValue(String colKey,ValuePlanDetialEntryInfo detialEntry,Object value){
		String key=null;
		if(colKey.indexOf("M")>0){
			key=colKey.substring(colKey.indexOf("M")+1,colKey.length());
		}else if(colKey.indexOf("Q")>0){
			key=colKey.substring(colKey.indexOf("Q")+1,colKey.length());
		}else{
			key=colKey.substring(colKey.indexOf("Y")+1,colKey.length());
		}
		detialEntry.put(key, value);
	}
	private void table_editStopped(KDTEditEvent e) {
		ValuePlanDetialEntryInfo detialEntry=(ValuePlanDetialEntryInfo) table.getRow(e.getRowIndex()).getCell(e.getColIndex()).getUserObject();
		String colKey=table.getColumnKey(e.getColIndex());
		if(colKey.equals("remark")){
			ValuePlanEntryInfo entry=(ValuePlanEntryInfo) table.getRow(e.getRowIndex()).getUserObject();
			entry.setRemark((String) e.getValue());
			return;
		}
		int beginYear=this.spnYear.getIntegerVlaue();
		int planYear=this.spnPlanYear.getIntegerVlaue();
		int year=0;
		if(colKey.indexOf("M")>0){
			year=Integer.parseInt(colKey.substring(0,colKey.indexOf("Y")));
		}else if(colKey.indexOf("Q")>0){
			year=Integer.parseInt(colKey.substring(0,colKey.indexOf("Y")));
		}else{
			year=Integer.parseInt(colKey.substring(0,colKey.indexOf("Y")));
		}
		setValue(colKey,detialEntry,e.getValue());
		
		String priceColKey=null;
		if(colKey.indexOf("area")>0){
			priceColKey=table.getColumnKey(e.getColIndex()+1);
			Object amount=table.getRow(e.getRowIndex()).getCell(e.getColIndex()+2).getValue();
			table.getRow(e.getRowIndex()).getCell(priceColKey).setValue(FDCHelper.divide(FDCHelper.multiply(amount, 10000), e.getValue(), 0, BigDecimal.ROUND_HALF_UP));
			setValue(priceColKey,detialEntry,table.getRow(e.getRowIndex()).getCell(priceColKey).getValue());
		}else if(colKey.indexOf("amount")>0){
			priceColKey=table.getColumnKey(e.getColIndex()-1);
			Object area=table.getRow(e.getRowIndex()).getCell(e.getColIndex()-2).getValue();
			table.getRow(e.getRowIndex()).getCell(e.getColIndex()-1).setValue(FDCHelper.divide(FDCHelper.multiply(e.getValue(), 10000), area, 0, BigDecimal.ROUND_HALF_UP));
			setValue(priceColKey,detialEntry,table.getRow(e.getRowIndex()).getCell(priceColKey).getValue());
		}
		if(year==beginYear){
			String yearXColKey=null;
			ValuePlanDetialEntryInfo yearXDetialEntry=null;
			if(colKey.indexOf("xaccount")>0){
				yearXColKey=year+"Y"+"xaccount";
				yearXDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(e.getRowIndex()).getCell(yearXColKey).getUserObject();
				int xaccount=0;
				for(int monthk=1;monthk<13;monthk++){
					int account=0;
					if(table.getRow(e.getRowIndex()).getCell(year+"Y"+monthk+"M"+"xaccount").getValue()!=null){
						account=(Integer)table.getRow(e.getRowIndex()).getCell(year+"Y"+monthk+"M"+"xaccount").getValue();
					}
					xaccount=xaccount+account;
				}
				table.getRow(e.getRowIndex()).getCell(yearXColKey).setValue(xaccount);
				setValue(yearXColKey,yearXDetialEntry,xaccount);
			}else if(colKey.indexOf("xarea")>0){
				yearXColKey=year+"Y"+"xarea";
				yearXDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(e.getRowIndex()).getCell(yearXColKey).getUserObject();
				BigDecimal xarea=FDCHelper.ZERO;
				for(int monthk=1;monthk<13;monthk++){
					BigDecimal area=FDCHelper.ZERO;
					if(table.getRow(e.getRowIndex()).getCell(year+"Y"+monthk+"M"+"xarea").getValue()!=null){
						area=(BigDecimal)table.getRow(e.getRowIndex()).getCell(year+"Y"+monthk+"M"+"xarea").getValue();
					}
					xarea=FDCHelper.add(xarea, area);
				}
				table.getRow(e.getRowIndex()).getCell(yearXColKey).setValue(xarea);
				setValue(yearXColKey,yearXDetialEntry,xarea);
				
				table.getRow(e.getRowIndex()).getCell(year+"Y"+"xprice").setValue(FDCHelper.divide(FDCHelper.multiply(table.getRow(e.getRowIndex()).getCell(year+"Y"+"xamount").getValue(), 10000), xarea, 0, BigDecimal.ROUND_HALF_UP));
				setValue(year+"Y"+"xprice",yearXDetialEntry,table.getRow(e.getRowIndex()).getCell(year+"Y"+"xprice").getValue());
			}else if(colKey.indexOf("xamount")>0){
				yearXColKey=year+"Y"+"xamount";
				yearXDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(e.getRowIndex()).getCell(yearXColKey).getUserObject();
				BigDecimal xamount=FDCHelper.ZERO;
				for(int monthk=1;monthk<13;monthk++){
					BigDecimal amount=FDCHelper.ZERO;
					if(table.getRow(e.getRowIndex()).getCell(year+"Y"+monthk+"M"+"xamount").getValue()!=null){
						amount=(BigDecimal)table.getRow(e.getRowIndex()).getCell(year+"Y"+monthk+"M"+"xamount").getValue();
					}
					xamount=FDCHelper.add(xamount, amount);
				}
				table.getRow(e.getRowIndex()).getCell(yearXColKey).setValue(xamount);
				setValue(yearXColKey,yearXDetialEntry,xamount);
				
				table.getRow(e.getRowIndex()).getCell(year+"Y"+"xprice").setValue(FDCHelper.divide(FDCHelper.multiply(xamount, 10000), table.getRow(e.getRowIndex()).getCell(year+"Y"+"xarea").getValue(), 0, BigDecimal.ROUND_HALF_UP));
				setValue(year+"Y"+"xprice",yearXDetialEntry,table.getRow(e.getRowIndex()).getCell(year+"Y"+"xprice").getValue());
			}else if(colKey.indexOf("saccount")>0){
				yearXColKey=year+"Y"+"saccount";
				yearXDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(e.getRowIndex()).getCell(yearXColKey).getUserObject();
				int saccount=0;
				for(int monthk=1;monthk<13;monthk++){
					int account=0;
					if(table.getRow(e.getRowIndex()).getCell(year+"Y"+monthk+"M"+"saccount").getValue()!=null){
						account=(Integer)table.getRow(e.getRowIndex()).getCell(year+"Y"+monthk+"M"+"saccount").getValue();
					}
					saccount=saccount+account;
				}
				table.getRow(e.getRowIndex()).getCell(yearXColKey).setValue(saccount);
				setValue(yearXColKey,yearXDetialEntry,saccount);
			}else if(colKey.indexOf("sarea")>0){
				yearXColKey=year+"Y"+"sarea";
				yearXDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(e.getRowIndex()).getCell(yearXColKey).getUserObject();
				BigDecimal sarea=FDCHelper.ZERO;
				for(int monthk=1;monthk<13;monthk++){
					BigDecimal area=FDCHelper.ZERO;
					if(table.getRow(e.getRowIndex()).getCell(year+"Y"+monthk+"M"+"sarea").getValue()!=null){
						area=(BigDecimal)table.getRow(e.getRowIndex()).getCell(year+"Y"+monthk+"M"+"sarea").getValue();
					}
					sarea=FDCHelper.add(sarea, area);
				}
				table.getRow(e.getRowIndex()).getCell(yearXColKey).setValue(sarea);
				setValue(yearXColKey,yearXDetialEntry,sarea);
				
				table.getRow(e.getRowIndex()).getCell(year+"Y"+"sprice").setValue(FDCHelper.divide(FDCHelper.multiply(table.getRow(e.getRowIndex()).getCell(year+"Y"+"samount").getValue(), 10000), sarea, 0, BigDecimal.ROUND_HALF_UP));
				setValue(year+"Y"+"sprice",yearXDetialEntry,table.getRow(e.getRowIndex()).getCell(year+"Y"+"sprice").getValue());
			}else if(colKey.indexOf("samount")>0){
				yearXColKey=year+"Y"+"samount";
				yearXDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(e.getRowIndex()).getCell(yearXColKey).getUserObject();
				BigDecimal samount=FDCHelper.ZERO;
				for(int monthk=1;monthk<13;monthk++){
					BigDecimal amount=FDCHelper.ZERO;
					if(table.getRow(e.getRowIndex()).getCell(year+"Y"+monthk+"M"+"samount").getValue()!=null){
						amount=(BigDecimal)table.getRow(e.getRowIndex()).getCell(year+"Y"+monthk+"M"+"samount").getValue();
					}
					samount=FDCHelper.add(samount, amount);
				}
				table.getRow(e.getRowIndex()).getCell(yearXColKey).setValue(samount);
				setValue(yearXColKey,yearXDetialEntry,samount);
				
				table.getRow(e.getRowIndex()).getCell(year+"Y"+"sprice").setValue(FDCHelper.divide(FDCHelper.multiply(samount, 10000), table.getRow(e.getRowIndex()).getCell(year+"Y"+"sarea").getValue(), 0, BigDecimal.ROUND_HALF_UP));
				setValue(year+"Y"+"sprice",yearXDetialEntry,table.getRow(e.getRowIndex()).getCell(year+"Y"+"sprice").getValue());
			}else if(colKey.indexOf("backAmount")>0){
				yearXColKey=year+"Y"+"backAmount";
				yearXDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(e.getRowIndex()).getCell(yearXColKey).getUserObject();
				BigDecimal totalBackAmount=FDCHelper.ZERO;
				for(int monthk=1;monthk<13;monthk++){
					BigDecimal backAmount=FDCHelper.ZERO;
					if(table.getRow(e.getRowIndex()).getCell(year+"Y"+monthk+"M"+"backAmount").getValue()!=null){
						backAmount=(BigDecimal)table.getRow(e.getRowIndex()).getCell(year+"Y"+monthk+"M"+"backAmount").getValue();
					}
					totalBackAmount=FDCHelper.add(totalBackAmount, backAmount);
				}
				table.getRow(e.getRowIndex()).getCell(yearXColKey).setValue(totalBackAmount);
				setValue(yearXColKey,yearXDetialEntry,totalBackAmount);
			}
		}else{
			String yearXColKey=null;
			ValuePlanDetialEntryInfo yearXDetialEntry=null;
			if(colKey.indexOf("xaccount")>0){
				yearXColKey=year+"Y"+"xaccount";
				yearXDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(e.getRowIndex()).getCell(yearXColKey).getUserObject();
				int xaccount=0;
				for(int quarterk=1;quarterk<5;quarterk++){
					int account=0;
					if(table.getRow(e.getRowIndex()).getCell(year+"Y"+quarterk+"Q"+"xaccount").getValue()!=null){
						account=(Integer)table.getRow(e.getRowIndex()).getCell(year+"Y"+quarterk+"Q"+"xaccount").getValue();
					}
					xaccount=xaccount+account;
				}
				table.getRow(e.getRowIndex()).getCell(yearXColKey).setValue(xaccount);
				setValue(yearXColKey,yearXDetialEntry,xaccount);
			}else if(colKey.indexOf("xarea")>0){
				yearXColKey=year+"Y"+"xarea";
				yearXDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(e.getRowIndex()).getCell(yearXColKey).getUserObject();
				BigDecimal xarea=FDCHelper.ZERO;
				for(int quarterk=1;quarterk<5;quarterk++){
					BigDecimal area=FDCHelper.ZERO;
					if(table.getRow(e.getRowIndex()).getCell(year+"Y"+quarterk+"Q"+"xarea").getValue()!=null){
						area=(BigDecimal)table.getRow(e.getRowIndex()).getCell(year+"Y"+quarterk+"Q"+"xarea").getValue();
					}
					xarea=FDCHelper.add(xarea, area);
				}
				table.getRow(e.getRowIndex()).getCell(yearXColKey).setValue(xarea);
				setValue(yearXColKey,yearXDetialEntry,xarea);
				
				table.getRow(e.getRowIndex()).getCell(year+"Y"+"xprice").setValue(FDCHelper.divide(FDCHelper.multiply(table.getRow(e.getRowIndex()).getCell(year+"Y"+"xamount").getValue(), 10000), xarea, 0, BigDecimal.ROUND_HALF_UP));
				setValue(year+"Y"+"xprice",yearXDetialEntry,table.getRow(e.getRowIndex()).getCell(year+"Y"+"xprice").getValue());
			}else if(colKey.indexOf("xamount")>0){
				yearXColKey=year+"Y"+"xamount";
				yearXDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(e.getRowIndex()).getCell(yearXColKey).getUserObject();
				BigDecimal xamount=FDCHelper.ZERO;
				for(int quarterk=1;quarterk<5;quarterk++){
					BigDecimal amount=FDCHelper.ZERO;
					if(table.getRow(e.getRowIndex()).getCell(year+"Y"+quarterk+"Q"+"xamount").getValue()!=null){
						amount=(BigDecimal)table.getRow(e.getRowIndex()).getCell(year+"Y"+quarterk+"Q"+"xamount").getValue();
					}
					xamount=FDCHelper.add(xamount, amount);
				}
				table.getRow(e.getRowIndex()).getCell(yearXColKey).setValue(xamount);
				setValue(yearXColKey,yearXDetialEntry,xamount);
				
				table.getRow(e.getRowIndex()).getCell(year+"Y"+"xprice").setValue(FDCHelper.divide(FDCHelper.multiply(xamount, 10000), table.getRow(e.getRowIndex()).getCell(year+"Y"+"xarea").getValue(), 0, BigDecimal.ROUND_HALF_UP));
				setValue(year+"Y"+"xprice",yearXDetialEntry,table.getRow(e.getRowIndex()).getCell(year+"Y"+"xprice").getValue());
			}else if(colKey.indexOf("saccount")>0){
				yearXColKey=year+"Y"+"saccount";
				yearXDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(e.getRowIndex()).getCell(yearXColKey).getUserObject();
				int saccount=0;
				for(int quarterk=1;quarterk<5;quarterk++){
					int account=0;
					if(table.getRow(e.getRowIndex()).getCell(year+"Y"+quarterk+"Q"+"saccount").getValue()!=null){
						account=(Integer)table.getRow(e.getRowIndex()).getCell(year+"Y"+quarterk+"Q"+"saccount").getValue();
					}
					saccount=saccount+account;
				}
				table.getRow(e.getRowIndex()).getCell(yearXColKey).setValue(saccount);
				setValue(yearXColKey,yearXDetialEntry,saccount);
			}else if(colKey.indexOf("sarea")>0){
				yearXColKey=year+"Y"+"sarea";
				yearXDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(e.getRowIndex()).getCell(yearXColKey).getUserObject();
				BigDecimal sarea=FDCHelper.ZERO;
				for(int quarterk=1;quarterk<5;quarterk++){
					BigDecimal area=FDCHelper.ZERO;
					if(table.getRow(e.getRowIndex()).getCell(year+"Y"+quarterk+"Q"+"sarea").getValue()!=null){
						area=(BigDecimal)table.getRow(e.getRowIndex()).getCell(year+"Y"+quarterk+"Q"+"sarea").getValue();
					}
					sarea=FDCHelper.add(sarea, area);
				}
				table.getRow(e.getRowIndex()).getCell(yearXColKey).setValue(sarea);
				setValue(yearXColKey,yearXDetialEntry,sarea);
				
				table.getRow(e.getRowIndex()).getCell(year+"Y"+"sprice").setValue(FDCHelper.divide(FDCHelper.multiply(table.getRow(e.getRowIndex()).getCell(year+"Y"+"samount").getValue(), 10000), sarea, 0, BigDecimal.ROUND_HALF_UP));
				setValue(year+"Y"+"sprice",yearXDetialEntry,table.getRow(e.getRowIndex()).getCell(year+"Y"+"sprice").getValue());
			}else if(colKey.indexOf("samount")>0){
				yearXColKey=year+"Y"+"samount";
				yearXDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(e.getRowIndex()).getCell(yearXColKey).getUserObject();
				BigDecimal samount=FDCHelper.ZERO;
				for(int quarterk=1;quarterk<5;quarterk++){
					BigDecimal amount=FDCHelper.ZERO;
					if(table.getRow(e.getRowIndex()).getCell(year+"Y"+quarterk+"Q"+"samount").getValue()!=null){
						amount=(BigDecimal)table.getRow(e.getRowIndex()).getCell(year+"Y"+quarterk+"Q"+"samount").getValue();
					}
					samount=FDCHelper.add(samount, amount);
				}
				table.getRow(e.getRowIndex()).getCell(yearXColKey).setValue(samount);
				setValue(yearXColKey,yearXDetialEntry,samount);
				
				table.getRow(e.getRowIndex()).getCell(year+"Y"+"sprice").setValue(FDCHelper.divide(FDCHelper.multiply(samount, 10000), table.getRow(e.getRowIndex()).getCell(year+"Y"+"sarea").getValue(), 0, BigDecimal.ROUND_HALF_UP));
				setValue(year+"Y"+"sprice",yearXDetialEntry,table.getRow(e.getRowIndex()).getCell(year+"Y"+"sprice").getValue());
			}else if(colKey.indexOf("backAmount")>0){
				yearXColKey=year+"Y"+"backAmount";
				yearXDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(e.getRowIndex()).getCell(yearXColKey).getUserObject();
				BigDecimal totalBackAmount=FDCHelper.ZERO;
				for(int quarterk=1;quarterk<5;quarterk++){
					BigDecimal backAmount=FDCHelper.ZERO;
					if(table.getRow(e.getRowIndex()).getCell(year+"Y"+quarterk+"Q"+"backAmount").getValue()!=null){
						backAmount=(BigDecimal)table.getRow(e.getRowIndex()).getCell(year+"Y"+quarterk+"Q"+"backAmount").getValue();
					}
					totalBackAmount=FDCHelper.add(totalBackAmount, backAmount);
				}
				table.getRow(e.getRowIndex()).getCell(yearXColKey).setValue(totalBackAmount);
				setValue(yearXColKey,yearXDetialEntry,totalBackAmount);
			}
		}
		
		for(int k=beginYear;k<beginYear+planYear;k++){
			if(k==beginYear){
				setXJColumn(e.getRowIndex(),k+"Y");
				for(int monthk=1;monthk<13;monthk++){
					setXJColumn(e.getRowIndex(),k+"Y"+monthk+"M");
					
					if(monthk==1){
						setCLColumn(e.getRowIndex(),k+"Y"+monthk+"M",true);
					}else{
						setCLColumn(e.getRowIndex(),k+"Y"+monthk+"M",false);
					}
					
					setXJColumn(e.getRowIndex(),k+"Y"+monthk+"M");
				}
			}else{
				setXJColumn(e.getRowIndex(),k+"Y");
				
				setCLColumn(e.getRowIndex(),k+"Y",false);
				
				for(int quarterk=1;quarterk<5;quarterk++){
					setXJColumn(e.getRowIndex(),k+"Y"+quarterk+"Q");
					
					if(quarterk==1){
						setCLColumn(e.getRowIndex(),k+"Y"+quarterk+"Q",true);
					}else{
						setCLColumn(e.getRowIndex(),k+"Y"+quarterk+"Q",false);
					}
					
					setXJColumn(e.getRowIndex(),k+"Y"+quarterk+"Q");
				}
			}
		}
		
	}
	protected void setCLColumn(int rowIndex,String key,boolean isFirst){
		String xColKey=key+"caccount";
		ValuePlanDetialEntryInfo yearCDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(rowIndex).getCell(xColKey).getUserObject();
		if(isFirst){
			int caccount=0;
			if(table.getRow(rowIndex).getCell(table.getColumnIndex(xColKey)-17).getValue()!=null){
				caccount=(Integer)table.getRow(rowIndex).getCell(table.getColumnIndex(xColKey)-17).getValue();
			}
			table.getRow(rowIndex).getCell(xColKey).setValue(caccount);
			setValue(xColKey,yearCDetialEntry,caccount);
		}else{
			int xaccount=0;
			if(table.getRow(rowIndex).getCell(table.getColumnIndex(xColKey)-9).getValue()!=null){
				xaccount=(Integer)table.getRow(rowIndex).getCell(table.getColumnIndex(xColKey)-9).getValue();
			}
			
			int saccount=0;
			if(table.getRow(rowIndex).getCell(table.getColumnIndex(xColKey)-5).getValue()!=null){
				saccount=(Integer)table.getRow(rowIndex).getCell(table.getColumnIndex(xColKey)-5).getValue();
			}
			table.getRow(rowIndex).getCell(xColKey).setValue(xaccount-saccount);
			setValue(xColKey,yearCDetialEntry,xaccount-saccount);
		}
		xColKey=key+"carea";
		yearCDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(rowIndex).getCell(xColKey).getUserObject();
		if(isFirst){
			BigDecimal carea=FDCHelper.ZERO;
			if(table.getRow(rowIndex).getCell(table.getColumnIndex(xColKey)-17).getValue()!=null){
				carea=(BigDecimal)table.getRow(rowIndex).getCell(table.getColumnIndex(xColKey)-17).getValue();
			}
			table.getRow(rowIndex).getCell(xColKey).setValue(carea);
			setValue(xColKey,yearCDetialEntry,carea);
		}else{
			BigDecimal carea=FDCHelper.subtract(table.getRow(rowIndex).getCell(table.getColumnIndex(xColKey)-9).getValue(), table.getRow(rowIndex).getCell(table.getColumnIndex(xColKey)-5).getValue());
			table.getRow(rowIndex).getCell(xColKey).setValue(carea);
			setValue(xColKey,yearCDetialEntry,carea);
		}
		
		xColKey=key+"camount";
		yearCDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(rowIndex).getCell(xColKey).getUserObject();
		if(isFirst){
			BigDecimal camount=FDCHelper.ZERO;
			if(table.getRow(rowIndex).getCell(table.getColumnIndex(xColKey)-17).getValue()!=null){
				camount=(BigDecimal)table.getRow(rowIndex).getCell(table.getColumnIndex(xColKey)-17).getValue();
			}
			table.getRow(rowIndex).getCell(xColKey).setValue(camount);
			setValue(xColKey,yearCDetialEntry,camount);
		}else{
			BigDecimal camount=FDCHelper.subtract(table.getRow(rowIndex).getCell(table.getColumnIndex(xColKey)-9).getValue(), table.getRow(rowIndex).getCell(table.getColumnIndex(xColKey)-5).getValue());
			table.getRow(rowIndex).getCell(xColKey).setValue(camount);
			setValue(xColKey,yearCDetialEntry,camount);
		}
		
		xColKey=key+"cprice";
		yearCDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(rowIndex).getCell(xColKey).getUserObject();
		BigDecimal price=FDCHelper.divide(FDCHelper.multiply(table.getRow(rowIndex).getCell(key+"camount").getValue(), 10000), table.getRow(rowIndex).getCell(key+"carea").getValue(), 0, BigDecimal.ROUND_HALF_UP);
		table.getRow(rowIndex).getCell(xColKey).setValue(price);
		setValue(xColKey,yearCDetialEntry,price);
	}
	protected void setXJColumn(int rowIndex,String key){
		String xColKey=key+"account";
		ValuePlanDetialEntryInfo yearXDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(rowIndex).getCell(xColKey).getUserObject();
		int caccount=0;
		if(table.getRow(rowIndex).getCell(key+"caccount").getValue()!=null){
			caccount=(Integer)table.getRow(rowIndex).getCell(key+"caccount").getValue();
		}
		int xaccount=0;
		if(table.getRow(rowIndex).getCell(key+"xaccount").getValue()!=null){
			xaccount=(Integer)table.getRow(rowIndex).getCell(key+"xaccount").getValue();
		}
		table.getRow(rowIndex).getCell(xColKey).setValue(caccount+xaccount);
		setValue(xColKey,yearXDetialEntry,caccount+xaccount);
		
		xColKey=key+"area";
		yearXDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(rowIndex).getCell(xColKey).getUserObject();
		BigDecimal area=FDCHelper.add(table.getRow(rowIndex).getCell(key+"carea").getValue(), table.getRow(rowIndex).getCell(key+"xarea").getValue());
		table.getRow(rowIndex).getCell(xColKey).setValue(area);
		setValue(xColKey,yearXDetialEntry,area);
		
		xColKey=key+"amount";
		yearXDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(rowIndex).getCell(xColKey).getUserObject();
		BigDecimal amount=FDCHelper.add(table.getRow(rowIndex).getCell(key+"camount").getValue(), table.getRow(rowIndex).getCell(key+"xamount").getValue());
		table.getRow(rowIndex).getCell(xColKey).setValue(amount);
		setValue(xColKey,yearXDetialEntry,amount);
		
		xColKey=key+"price";
		yearXDetialEntry=(ValuePlanDetialEntryInfo) table.getRow(rowIndex).getCell(xColKey).getUserObject();
		BigDecimal price=FDCHelper.divide(FDCHelper.multiply(table.getRow(rowIndex).getCell(key+"amount").getValue(), 10000), table.getRow(rowIndex).getCell(key+"area").getValue(), 0, BigDecimal.ROUND_HALF_UP);
		table.getRow(rowIndex).getCell(xColKey).setValue(price);
		setValue(xColKey,yearXDetialEntry,price);
	}
}