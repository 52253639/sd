/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.Action;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class BatchPaymentSplitSelectUI extends AbstractBatchPaymentSplitSelectUI
{
    private static final Logger logger = CoreUIObject.getLogger(BatchPaymentSplitSelectUI.class);
    
    /**
     * output class constructor
     */
    public BatchPaymentSplitSelectUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
    	PaymentBillCollection infos=new PaymentBillCollection();
    	for(int i=0;i<table.getRowCount();i++){
    		IRow row=table.getRow(i);
    		if(row.getCell("isSelect").getValue()==Boolean.TRUE){
    			infos.add((PaymentBillInfo)row.getUserObject());
    		}
    	}
    	getUIContext().put("PaymentBillCollection", infos);
    }

    /**
     * output actionSelectAll_actionPerformed
     */
    public void actionSelectAll_actionPerformed(ActionEvent e) throws Exception
    {
        for(int i=0;i<table.getRowCount();i++){
        	table.getCell(i, "isSelect").setValue(Boolean.TRUE);
        }
    }

    /**
     * output actionClearAll_actionPerformed
     */
    public void actionClearAll_actionPerformed(ActionEvent e) throws Exception
    {
        for(int i=0;i<table.getRowCount();i++){
        	table.getCell(i, "isSelect").setValue(Boolean.FALSE);
        }
    }

    /**
     * output actionSplit_actionPerformed
     */
    public void actionSplit_actionPerformed(ActionEvent e) throws Exception
    {
    	//将选择的对象重新放置到UIContext
    	storeFields();
    	destroyWindow();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	fillTable();
    	actionSelectAll.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_selectall"));
    	actionClearAll.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_deleteall"));
    	actionSplit.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_split"));
    	contMain.add(actionSelectAll);
    	contMain.add(actionClearAll);
    	contMain.setEnableActive(false);
    	
    }
    
    private void fillTable() throws Exception{
    	try{
    		table.setRefresh(false);
    		table.checkParsed();
    		table.removeRows(false);
    		
    		PaymentBillCollection infos=(PaymentBillCollection)getUIContext().get("PaymentBillCollection");
    		
    		for (Iterator iter = infos.iterator(); iter.hasNext();) {
				PaymentBillInfo info = (PaymentBillInfo) iter.next();
				IRow row=table.addRow();
				row.setUserObject(info);
				loadRow(row);
			}
    		getUIContext().put("PaymentBillCollection", null);
    		table.getStyleAttributes().setLocked(true);
    	}finally{
    		table.setRefresh(true);
    		table.repaint();
    	}
    	
    }
    private void loadRow(IRow row){
    	PaymentBillInfo info=(PaymentBillInfo)row.getUserObject();
    	row.getCell("isSelect").setValue(Boolean.FALSE);
    	row.getCell("splitState").setValue(CostSplitStateEnum.NOSPLIT);
    	row.getCell("paymentType").setValue(info.getFdcPayType().getPayType().getName());
    	row.getCell("state").setValue(info.getBillStatus());
    	row.getCell("isVouchered").setValue(Boolean.valueOf(info.isFiVouchered()));
    	row.getCell("paymentNumber").setValue(info.getNumber());
    	row.getCell("payReqNumber").setValue(info.getFdcPayReqNumber());
    	row.getCell("conNumber").setValue(info.getContractNo());
    	
    	row.getStyleAttributes().setBackground(com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper.COLOR_NOSPLIT);
    }
    
    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection selector=new SelectorItemCollection();
    	selector.add("fdcPayType.payType.name");
    	selector.add("billStatus");
    	selector.add("number");
    	selector.add("fdcPayReqNumber");
    	selector.add("contractNo");
    	selector.add("fiVouchered");
    	return selector;
    }
    
    /**
     * 
     * @param infos 满足条件的付款单
     * @param ui 	UI
     * @return
     * @throws UIException
     */
    public static PaymentBillCollection showSelectUI(PaymentBillCollection infos,CoreUI ui) throws UIException{
    	
    	if(infos==null||infos.size()==0){
    		return new PaymentBillCollection();
    	}
    	for(int i=infos.size()-1;i>=0;i--){
    		PaymentBillInfo info=infos.get(i);
    		String contractBillId = info.getContractBillId();
    		if(contractBillId!=null&&new ContractWithoutTextInfo().getBOSType().equals(BOSUuid.read(contractBillId).getType())){
    			//排除无文本
    			infos.remove(info);
    		}
    	}
		UIContext uiContext = new UIContext(ui);
		uiContext.put("PaymentBillCollection", infos);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create("com.kingdee.eas.fdc.finance.client.BatchPaymentSplitSelectUI", uiContext, null,
						"view",WinStyle.SHOW_TOOLBAR);
		uiWindow.show();
		return (PaymentBillCollection)uiWindow.getUIObject().getUIContext().get("PaymentBillCollection");
    }
    
    protected void table_tableClicked(KDTMouseEvent e) throws Exception {
    	if(e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1){
    		if(e.getColIndex()==table.getColumnIndex("isSelect")){
    			IRow row=table.getRow(e.getRowIndex());
				Boolean isSelect=(Boolean)row.getCell(e.getColIndex()).getValue();
				row.getCell(e.getColIndex()).setValue(Boolean.valueOf(isSelect==Boolean.FALSE));
    		}
    		
    	}
    }
}