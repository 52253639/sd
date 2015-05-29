package com.kingdee.eas.fi.cas.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI;
import com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;

public class CasPaymentBillListUICTEx extends CasPaymentBillListUI{
	public CasPaymentBillListUICTEx() throws Exception {
		super();
	}

	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id=row.getCell("id").getValue().toString();
		PaymentBillInfo info=PaymentBillFactory.getRemoteInstance().getPaymentBillInfo(new ObjectUuidPK(id));
		if(info.getContractBillId()!=null&&info.getFdcPayReqID()!=null){
			String className=PayRequestBillEditUI.class.getName();
			if(PayReqUtils.isConWithoutTxt(info.getContractBillId())){
				id=info.getContractBillId();
				className=ContractWithoutTextEditUI.class.getName();
			}
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", id);
	        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	        IUIWindow uiWindow = uiFactory.create(className, uiContext,null,OprtState.VIEW);
	        uiWindow.show();
		}else{
			super.actionTraceUp_actionPerformed(e);
		}
	}
}
