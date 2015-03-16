/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.framework.client.ControlTypeUtil;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;


/**
 * @(#)			GradeSetUpListUI.java				
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		等级设置序事薄
 *		
 * @author		胥凯
 * @version		EAS7.0		
 * @createDate	2010-12-1	 
 * @see						
 */
public class GradeSetUpListUI extends AbstractGradeSetUpListUI
{
    public GradeSetUpListUI() throws Exception {
		super();
	}
	private static final Logger logger = CoreUIObject.getLogger(GradeSetUpListUI.class);
    
    protected FDCDataBaseInfo getBaseDataInfo() {
		return new GradeSetUpInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return GradeSetUpFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return GradeSetUpEditUI.class.getName();
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
}