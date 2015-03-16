package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.invite.IInviteTeam;
import com.kingdee.eas.fdc.invite.InviteTeamFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ÕÐ±êÐ¡×éListUI
 * @author guoshuaishai
 */
public class InviteTeamListUI extends AbstractInviteTeamListUI {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = CoreUIObject.getLogger(InviteTeamListUI.class);
    private final String COL_STATE = "state";
    private CommonQueryDialog commonQueryDialog = null;
    private CustomerQueryPanel filterUI = null;
    private StringBuffer msg=new StringBuffer();
    
    /**
     * output class constructor
     */
    public InviteTeamListUI() throws Exception
    {
        super();
    }

	public KDTable getBillListTable() {
		// TODO Auto-generated method stub
		return null;
	}
    
}