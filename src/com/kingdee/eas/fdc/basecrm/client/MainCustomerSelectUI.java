/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.ActionEvent;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.FDCBaseCustomerInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MainCustomerSelectUI extends AbstractMainCustomerSelectUI
{
    private static final Logger logger = CoreUIObject.getLogger(MainCustomerSelectUI.class);
    
    /**
     * output class constructor
     */
    public MainCustomerSelectUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	this.tblMain.checkParsed();
    	super.onLoad();
    	tblMain.getStyleAttributes().setLocked(true);
    	Map con = this.getUIContext();
    	tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.ROW_SELECT);
		String dir = (String) con.get(CRMConstants.FDCSHE_PARAM_REPEATCUSSHOWRULE_DIR);
		String start = (String) con.get(CRMConstants.FDCSHE_PARAM_REPEATCUSSHOWRULE_START);
		String length = (String) con.get(CRMConstants.FDCSHE_PARAM_REPEATCUSSHOWRULE_LEN);
		
    	if(con.get("params") == null){//title的显示
    		this.setUITitle("客户合并");
    	}else{
    		this.setUITitle("确定关联客户");
    	}
    	IObjectCollection fdcMainCuses = (IObjectCollection) con.get("fdcMainCuses");
    	for(int i=0; i<fdcMainCuses.size(); i++){
    		FDCBaseCustomerInfo cus = (FDCBaseCustomerInfo) fdcMainCuses.getObject(i);
    		
    		IRow row = this.tblMain.addRow();
    		row.setUserObject(cus);
    		
    		row.getCell("id").setValue(cus.getId().toString());
    		row.getCell("isSelect").setValue(Boolean.FALSE);
    		row.getCell("name").setValue(cus.getName());
    		row.getCell("customerType").setValue(cus.getCustomerType().getAlias());
    		
    		String phone = cus.getPhone();
    		if (!FDCHelper.isEmpty(phone) && !FDCHelper.isEmpty(dir) && !FDCHelper.isEmpty(start)
					&& !FDCHelper.isEmpty(length)) {
    			int s = Integer.parseInt(start);
    			int len = Integer.parseInt(length);
				row.getCell("phone").setValue(splitPhone(phone, dir, s, len));
			} else {
				row.getCell("phone").setValue(phone);
			}
    		row.getCell("id").setValue(cus.getId());
    		row.getCell("certificateType").setValue(cus.getCertificateType());
    		row.getCell("certificateNum").setValue(cus.getCertificateNumber());
    		row.getCell("org").setValue(cus.getCreateUnit());
    		row.getCell("project").setValue(cus.getSellProject());
    		if(cus instanceof SHECustomerInfo){
    			UserInfo userInfo = ((SHECustomerInfo) cus).getPropertyConsultant();
    			row.getCell("saleman").setValue(userInfo.getName());//TODO
    		}
    		
    		row.getCell("isSelect").getStyleAttributes().setLocked(false);
    	}
    }
    
    private String splitPhone(String phone, String dir, int s, int len){
    	int slen = phone.length();
    	
    	int sIndex = 0;
		int eIndex = 0;
		if(CusClientHelper.REPEATCUSSHOWRULE_DIR_VALUE_LEFT.equals(dir)){
			sIndex = s - 1;
			eIndex = sIndex + len - 1;
		}else if(CusClientHelper.REPEATCUSSHOWRULE_DIR_VALUE_RIGHT.equals(dir)){
			eIndex = slen - s;
			sIndex = eIndex - len + 1;
		}
		
		if(sIndex > slen - 1){
			return phone;
		}
		
		if(eIndex <= 0){
			return phone;
		}
		
		if(sIndex < 0){
			sIndex = 0;
		}

		if(eIndex > slen){
			eIndex = slen;
		}
		
		StringBuffer tmp = new StringBuffer();
		if(sIndex > 0){
			tmp.append(phone.substring(0, sIndex));
		}
		
		for(int m=0; m<eIndex-sIndex+1; m++){
			tmp.append("*");
		}
		
		if(eIndex < slen){
			tmp.append(phone.substring(eIndex + 1, slen));
		}
		
		return tmp.toString();
    }
    
    protected void btnOK_actionPerformed(ActionEvent e) throws Exception {
    	boolean hasLinked = false;
    	
    	for(int i=0; i<this.tblMain.getRowCount(); i++){
    		IRow row = this.tblMain.getRow(i);
    		
    		Boolean isSel = (Boolean) row.getCell("isSelect").getValue();
    		if(isSel != null  &&  isSel.booleanValue()){
    			this.getUIContext().put("mainCus", row.getUserObject());
    			hasLinked = true;
    			break;
    		}
    	}
    	
    	if(!hasLinked){
    		if(this.getUIContext().get("params") == null){//title的显示
    		    MsgBox.showInfo(this, "请选择合并客户！");
    		}else{
    			MsgBox.showInfo(this, "请选择关联客户！");
    		}
    		return;
    	}
    	
    	if (this.getUIContext().get("params") == null && !confirmMerge()) {
    		return;
    	}
    	
    	this.destroyWindow();
    }
    
    private boolean confirmMerge() {
		if (MsgBox.isYes(MsgBox.showConfirm2(this, "客户合并后将不可恢复，是否继续操作吗?"))) {
			return true;
		} else {
			return false;
		}
	}
 
    protected void tblMain_editStarting(KDTEditEvent e) throws Exception {
		if("isSelect".equals(this.tblMain.getColumn(e.getColIndex()).getKey())){
			if(!((Boolean)e.getValue()).booleanValue()){
				for(int i=0;i<this.tblMain.getRowCount();i++){
					if(i!=e.getRowIndex()){
						this.tblMain.getRow(i).getCell("isSelect").setValue(new Boolean(false));
					}
				}
			}
		}
	}
    
    protected void btnCancel_actionPerformed(ActionEvent e) throws Exception {
    	this.destroyWindow();
    }
    
    protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
    	if(this.getUIContext().get("params") == null){
		String uiClassName = "com.kingdee.eas.fdc.sellhouse.client.CustomerRptEditUI";
		CusClientHelper.openDetailPage(this, e, tblMain, uiClassName);
    	}
	}
}