/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.sellhouse.PurCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.PurCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.PurCustomerInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;


public class PurchaseCustomerModifyUI extends AbstractPurchaseCustomerModifyUI
{
    private static final Logger logger = CoreUIObject.getLogger(PurchaseCustomerModifyUI.class);
    
    public PurchaseCustomerModifyUI() throws Exception
    {
        super();
    }

	protected IObjectValue createNewData() {
		return null;
	}
	public void onLoad() throws Exception {
		
		if(getUIContext().get("name")!=null){
			String name = (String)getUIContext().get("name");
			this.oldName.setEnabled(false);
			this.txtOldName.setText(name);
			this.txtNewName.setMaxLength(80);
			this.txtNewName.setRequired(true);
		}
	}
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(txtNewName.getText()==null||txtNewName.getText().trim().toString().equals("")){
			MsgBox.showError("新客户姓名不能为空！");
			abort();
		}
		if(getUIContext().get("id")!=null&&getUIContext().get("fid")!=null){
			String id  = (String)getUIContext().get("id");
			String fid  = getUIContext().get("fid").toString();
			if(id!=null){
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql("update T_SHE_FDCCustomer set fname_l2 = ? where fid = ?");
				builder.addParam(txtNewName.getText());
				builder.addParam(id);
				builder.executeUpdate();

				FDCSQLBuilder sql = new FDCSQLBuilder();
				sql.appendSql("update T_SHE_PurchaseCustomer set FCustomerName = ? where FCustomerID = ? and FParentID = ?");
				sql.addParam(txtNewName.getText());
				sql.addParam(id);
				sql.addParam(fid);
				sql.executeUpdate();
				
				FDCSQLBuilder sql2 = new FDCSQLBuilder();
				String oldName = txtOldName.getText();
				String newName = txtNewName.getText();
				String allName = null;
				sql2.appendSql("select FCustomerNames from T_SHE_Purchase where fid = ? ");
				sql2.addParam(fid);
				IRowSet ir  = sql2.executeQuery();
				if(ir.next()){
					allName = ir.getString("FCustomerNames");
				}
				if(allName!=null){
					allName = allName.replaceAll(oldName, newName);
					sql2.clear();
					sql2.appendSql("update T_SHE_Purchase set FCustomerNames = ? where fid = ?");
					//DB2不支持，悲情
	//				sql2.appendSql("update T_SHE_Purchase set FCustomerNames = REPLACE(FCustomerNames, ?, ?)  where fid = ?");
	//				sql2.addParam(txtOldName.getText());
	//				sql2.addParam(txtNewName.getText());
					sql2.addParam(allName);
					sql2.addParam(fid);
					sql2.executeUpdate();
				}
				//修改姓名之后，除了反写，还要记录交易信息。生成历史记录
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				SorterItemCollection sort = view.getSorter();
				SorterItemInfo sortInfo = new SorterItemInfo("lastUpdateTime");
				sortInfo.setSortType(SortType.DESCEND);
				sort.add(sortInfo);
				filter.getFilterItems().add(new FilterItemInfo("parent.id",fid));
				filter.getFilterItems().add(new FilterItemInfo("customerID",id));
				view.setFilter(filter);
				PurCustomerCollection purCuss = PurCustomerFactory.getRemoteInstance().getPurCustomerCollection(view);
				//暂时不用记录日志快照  by  zgy  2010-12-18
				if(purCuss.size()!=0){
					PurCustomerInfo pcinfo  = purCuss.get(0);
//					Timestamp time = pcinfo.getLastUpdateTime();
//					UserInfo user = pcinfo.getLastUpdateUser();
					pcinfo.setLastUpdateUser(SysContext.getSysContext().getCurrentUserInfo());
					pcinfo.setLastUpdateTime(new Timestamp(new Date().getTime()));					
//					BOSUuid bosid =BOSUuid.create("33B38C50");
//					PurCustomerFactory.getRemoteInstance().submit(pcinfo);
//					pcinfo.setOldPurCustomer(pcinfo.getId());
//					pcinfo.setId(bosid);
//					pcinfo.setLastUpdateTime(time);
//					pcinfo.setLastUpdateUser(user);
					PurCustomerFactory.getRemoteInstance().submit(pcinfo);
				}			
			}
			
		}
	    showSubmitSuccess();
	}

}