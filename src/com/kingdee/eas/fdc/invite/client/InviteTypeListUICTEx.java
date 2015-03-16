package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;

import javax.swing.event.TreeSelectionEvent;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.custom.fdcinvite.IInviteAspectFacade;
import com.kingdee.eas.custom.fdcinvite.InviteAspectFacadeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.framework.client.TreePathUtil;

public class InviteTypeListUICTEx extends InviteTypeListUI
{

	private static final boolean TRUE = true;
	private static IInviteAspectFacade invite;

	private KDTSelectManager selectManager;

	public InviteTypeListUICTEx() throws Exception
	{
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception
	{
		// TODO Auto-generated method stub
		super.treeMain_valueChanged(e);
		displayEnableColor();
	}
	
	
	
	//颜色现实表格，是否引用，如果是禁用则显示灰色。否则白色
	private void displayEnableColor()
	{
//		for(int i=0;i<tblMain.getRowCount();i++)
//		{
//			//Object obj  = tblMain.getRow(i).getCell("isEnabled").getValue();
//			//tblMain.getRow(i).getCell("isEnabled").getStyleAttributes().setLocked(true);
//			
////			if(obj!=null)
////			{
////				if("TRUE".equals(obj.toString().toUpperCase()))				
////				{
////					tblMain.getRow(i).getStyleAttributes().setBackground(Color.WHITE);
////				}else
////				{
////					tblMain.getRow(i).getStyleAttributes().setBackground(Color.GRAY);
////
////				}
////			}
//		}
	}
	
	
	
	

	// 更新或者删除
	private   void update(String id, int type)
			throws BOSException
	{
		invite.updateInfo(id, type);
	}

	// 禁用，启用，判断
	private void forUpdate(int type) throws EASBizException, BOSException
	{
		// 获得选中 的行
		int selectedRow = selectManager.getActiveRowIndex();
		if (selectedRow != -1)
		{
			Object obj = this.tblMain.getRow(selectedRow).getCell("id")
					.getValue();
			if (obj != null && (!("".equals(obj))))
			{
				String id = obj.toString();
				// 从ID找到对应的单据
				InviteTypeInfo typeInfo = com.kingdee.eas.fdc.invite.InviteTypeFactory
						.getRemoteInstance().getInviteTypeInfo(
								new ObjectUuidPK(id));
				if (typeInfo != null)
				{

					update(id, type);

				}
			}
		}
	}

	// 增加启用操作
	@Override
	public void actionCancelCancel_actionPerformed(ActionEvent e)
			throws Exception
	{
		// TODO Auto-generated method stub
		super.actionCancelCancel_actionPerformed(e);
		forUpdate(1);
		refreshAfterCancelOrCancelCancel();
	}

	// 增加禁用操作
	@Override
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception
	{
		// TODO Auto-generated method stub
		super.actionCancel_actionPerformed(e);
		forUpdate(0);
		refreshAfterCancelOrCancelCancel();
		// typeInfo.setIsEnabled(false); 类似HIBERNATE
		//com.kingdee.eas.fdc.invite.InviteTypeFactory.getRemoteInstance().cancel
		// (new ObjectUuidPK(id), typeInfo);

	}

	@Override
	public void onLoad() throws Exception
	{
		// TODO Auto-generated method stub
		super.onLoad();
		selectManager = tblMain.getSelectManager();
		this.tblMain.getColumn("isEnabled").getStyleAttributes().setHided(false);
		this.tblMain.getColumn("isEnabled").getStyleAttributes().setLocked(true);
		this.btnCancel.setVisible(TRUE);
		this.btnCancelCancel.setVisible(TRUE);
		this.btnCancel.setEnabled(TRUE);
		this.btnCancelCancel.setEnabled(TRUE);
		invite = InviteAspectFacadeFactory.getRemoteInstance();
	}

	
	/**
	 * 启用，禁用完之后，刷新表格
	 * @throws Exception
	 */
	 private void refreshAfterCancelOrCancelCancel()
       throws Exception
   {
		 
		 
		 //refresh(null);
         //setPreSelecteRow();
		 
		 
		 
	      javax.swing.tree.TreePath oldPath = treeMain.getSelectionPath();
	      
	      //initTree();
	       javax.swing.tree.TreePath path = TreePathUtil.getNewTreePath(treeMain, treeMain.getModel(), oldPath);
	       execQuery();
		    //setLocatePre(false);
	       // buildTreeFilter();
	        //
	       // setSelectFirstRow(tblMain);
	       // setLocatePre(true);
		 
		 
 
       if(path != null)
          treeMain.setSelectionPath(path);
       else
           treeMain.setSelectionRow(0);
       
   }
	
	
}
