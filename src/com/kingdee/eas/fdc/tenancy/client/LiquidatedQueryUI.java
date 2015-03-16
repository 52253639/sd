/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.ILiquidated;
import com.kingdee.eas.fdc.tenancy.LiquidatedFactory;
import com.kingdee.eas.fdc.tenancy.LiquidatedInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class LiquidatedQueryUI extends AbstractLiquidatedQueryUI
{
    private static final Logger logger = CoreUIObject.getLogger(LiquidatedQueryUI.class);
    private SellProjectInfo sellProject=null;
    /**
     * output class constructor
     */
    public LiquidatedQueryUI() throws Exception
    {
        super();
    }

    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    public void actionSelect_actionPerformed(ActionEvent e) throws Exception
    {
    	if(KDTableUtil.getSelectedRow(tblMain) == null){
			MsgBox.showWarning(this, "请先选中行！");
			return;
		}
		if(getSelectedIdValues() != null){
			ArrayList id=getSelectedIdValues();
			ILiquidated liquidated=LiquidatedFactory.getRemoteInstance();
			int sus=0;
			for(int i=0;i<id.size();i++){
				IObjectPK pk = new ObjectUuidPK(id.get(i).toString());
				LiquidatedInfo info=liquidated.getLiquidatedInfo(pk);
				
				LiquidatedInfo copyInfo =new LiquidatedInfo();
				copyInfo=info;
				copyInfo.setId(BOSUuid.create(copyInfo.getBOSType()));
				copyInfo.setSellProject(sellProject);
				liquidated.addnew(copyInfo);
				sus=sus+1;
			}
			MsgBox.showInfo(this, "成功引入"+sus+"条方案设置！");
		}
		this.destroyWindow();
        super.actionSelect_actionPerformed(e);
    }

    public void actionUnSelect_actionPerformed(ActionEvent e) throws Exception
    {
    	this.destroyWindow();
        super.actionUnSelect_actionPerformed(e);
    }

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionView.setVisible(false);
		this.actionEdit.setVisible(false);
		if(this.getUIContext().get("sellProject")!=null){
			sellProject=(SellProjectInfo)this.getUIContext().get("sellProject");
		}
	}
	

}