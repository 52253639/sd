/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ContractSelectUI extends AbstractContractSelectUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractSelectUI.class);
    private FilterInfo filter=null;
    public ContractSelectUI() throws Exception
    {
        super();
    }

	public void onLoad() throws Exception {
		super.onLoad();
		this.tblContract.checkParsed();
		this.tblContract.getStyleAttributes().setLocked(true);
		this.tblContract.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblContractWH.checkParsed();
		this.tblContractWH.getStyleAttributes().setLocked(true);
		this.tblContractWH.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		this.addContract();
		this.addContractWH();
	}
	protected void addContract() throws BOSException{
		this.tblContract.removeRows();
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filtervalue=new FilterInfo();
		if(!this.txtNumber.getText().trim().equals("")){
			filtervalue.getFilterItems().add(new FilterItemInfo("number","%"+this.txtNumber.getText().trim()+"%",CompareType.LIKE));
		}
		if(!this.txtName.getText().trim().equals("")){
			filtervalue.getFilterItems().add(new FilterItemInfo("name","%"+this.txtName.getText().trim()+"%",CompareType.LIKE));
		}
		if(!this.txtPartB.getText().trim().equals("")){
			filtervalue.getFilterItems().add(new FilterItemInfo("partB.name","%"+this.txtPartB.getText().trim()+"%",CompareType.LIKE));
		}
		filter=(FilterInfo)this.getUIContext().get("filter");
		if(filter!=null){
			filtervalue.mergeFilter(filter, "and");
			view.setFilter(filtervalue);
		}
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("number");
		sic.add("name");
		sic.add("originalAmount");
		sic.add("amount");
		sic.add("partB.name");
		sic.add("bookedDate");
		view.setSelector(sic);
		ContractBillCollection contract=ContractBillFactory.getRemoteInstance().getContractBillCollection(view);
		for(int i=0;i<contract.size();i++){
			IRow row=this.tblContract.addRow();
			row.setUserObject(contract.get(i));
			row.getCell("id").setValue(contract.get(i).getId());
			row.getCell("number").setValue(contract.get(i).getNumber());
			row.getCell("billName").setValue(contract.get(i).getName());
			row.getCell("originalAmount").setValue(contract.get(i).getOriginalAmount());
			row.getCell("amount").setValue(contract.get(i).getAmount());
			if(contract.get(i).getPartB()!=null){
				row.getCell("partB").setValue(contract.get(i).getPartB().getName());
			}
			row.getCell("bizDate").setValue(contract.get(i).getBookedDate());
		}
	}
	protected void addContractWH() throws BOSException{
		this.tblContractWH.removeRows();
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filtervalue=new FilterInfo();
		if(!this.txtNumber.getText().trim().equals("")){
			filtervalue.getFilterItems().add(new FilterItemInfo("number","%"+this.txtNumber.getText().trim()+"%",CompareType.LIKE));
		}
		if(!this.txtName.getText().trim().equals("")){
			filtervalue.getFilterItems().add(new FilterItemInfo("name","%"+this.txtName.getText().trim()+"%",CompareType.LIKE));
		}
		if(!this.txtPartB.getText().trim().equals("")){
			filtervalue.getFilterItems().add(new FilterItemInfo("receiveUnit.name","%"+this.txtPartB.getText().trim()+"%",CompareType.LIKE));
		}
		filter=(FilterInfo)this.getUIContext().get("filter");
		if(filter!=null){
			filtervalue.mergeFilter(filter, "and");
			view.setFilter(filtervalue);
		}
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("number");
		sic.add("name");
		sic.add("originalAmount");
		sic.add("amount");
		sic.add("bookedDate");
		sic.add("receiveUnit.name");
		view.setSelector(sic);
		ContractWithoutTextCollection contractWH=ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection(view);
		for(int i=0;i<contractWH.size();i++){
			IRow row=this.tblContractWH.addRow();
			row.setUserObject(contractWH.get(i));
			row.getCell("id").setValue(contractWH.get(i).getId());
			row.getCell("number").setValue(contractWH.get(i).getNumber());
			row.getCell("billName").setValue(contractWH.get(i).getName());
			row.getCell("originalAmount").setValue(contractWH.get(i).getOriginalAmount());
			row.getCell("amount").setValue(contractWH.get(i).getAmount());
			if(contractWH.get(i).getReceiveUnit()!=null){
				row.getCell("partB").setValue(contractWH.get(i).getReceiveUnit().getName());
			}
			row.getCell("bizDate").setValue(contractWH.get(i).getBookedDate());
		}
	}
	protected void tblContract_tableClicked(KDTMouseEvent e) throws Exception {
		if(e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
			int rowIndex = this.tblContract.getSelectManager().getActiveRowIndex();
			IRow row = this.tblContract.getRow(rowIndex);
			List value=new ArrayList();
			value.add(row.getUserObject());
			this.getUIContext().put("contract", value);
			this.disposeUIWindow();
		}
	}

	protected void tblContractWH_tableClicked(KDTMouseEvent e) throws Exception {
		if(e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
			int rowIndex = this.tblContractWH.getSelectManager().getActiveRowIndex();
			IRow row = this.tblContractWH.getRow(rowIndex);
			List value=new ArrayList();
			value.add(row.getUserObject());
			this.getUIContext().put("contract", value);
			this.disposeUIWindow();
		}
	}

	protected void btnQuery_actionPerformed(ActionEvent e) throws Exception {
		if(this.kDPanel1.isShowing()){
			this.addContract();
		}else{
			this.addContractWH();
		}
	}
	
}