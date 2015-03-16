/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.invite.InvitePreSplitFactory;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class InvitePreSplitListUI extends AbstractInvitePreSplitListUI
{
	private static final Logger logger = CoreUIObject.getLogger(InvitePreSplitListUI.class);

	private final String COL_ID = "id";
	private final String COL_STATE = "state";
	private final String COL_ISASSCONTRACT = "isAssContract";

	private final String COL_ISCONTRACTSPLIT = "isContractSplit";
	private final String COL_NUMBER = "number";
	private final String COL_INVITEPROJECT_NAME = "inviteProject.name" ;

	private final String COL_PRESPLIT_AMOUNT = "preSplitAmount";
	private final String COL_CREATOR_NUMBER = "creator.number";

	private final String COL_CREATOR_NAME = "creator.name";
	private final String COL_CREATE_TIME = "createTime";
	private final String COL_AUDITOR_NUMBER = "auditor.number";

	private final String COL_AUDITOR_NAME = "auditor.name";
	private final String COL_AUDIT_TIME = "auditTime";
	/**
	 * output class constructor
	 */
	public InvitePreSplitListUI() throws Exception
	{
		super();
	}
	public KDTable getBillListTable() {
		// TODO Auto-generated method stub
		return null;
	}

}