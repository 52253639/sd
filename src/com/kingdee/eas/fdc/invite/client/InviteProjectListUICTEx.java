package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.custom.fdchrchooser.HROpenUI;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;

public class InviteProjectListUICTEx extends InviteProjectListUI
{

	public InviteProjectListUICTEx() throws Exception
	{
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onLoad() throws Exception
	{
		// TODO Auto-generated method stub
		super.onLoad();
		this.tblMain.getSelectManager().setSelectMode(
				KDTSelectManager.MULTIPLE_ROW_SELECT); // 设置多选
		this.btnGrant.setVisible(true);
		this.btnGrant.setEnabled(true);
		// 增加按钮，不生成抽象类，抽象类代码被修改过
//		KDWorkButton btnGrantAuth = new KDWorkButton();
//		btnGrantAuth.setText("单据移交");
//		this.toolBar.add(btnGrantAuth);
//		btnGrantAuth.addActionListener(new java.awt.event.ActionListener()
//		{
//			public void actionPerformed(java.awt.event.ActionEvent e)
//			{
//				beforeActionPerformed(e);
//				try
//				{
//					grantAuthority_actionPerformed(e);
//				}
//				catch (Exception exc)
//				{
//					handUIException(exc);
//				}
//				finally
//				{
//					afterActionPerformed(e);
//				}
//			}
//		});

	}

	/**
	 * /** 判断是否多选
	 * 
	 * @return
	 */
	private boolean isMultiSelect()
	{
		boolean flag = false;

		int size = this.tblMain.getSelectManager().size(); // 选择的块

		// 肯定多选
		if (size > 1)
		{
			return true;
		}
		else if (size == 1) // 判断是否在一个块中多选行
		{

			KDTSelectBlock sb = this.tblMain.getSelectManager().get(0);
			int startRow = sb.getBeginRow();
			int endRow = sb.getEndRow();

			// 说明不在同一行
			if (startRow != endRow)
			{
				return true;
			}
			else
			{
				return false;
			}

		}
		else
		{
			return false;
		}

	}

	@Override
	public void actionRelate_actionPerformed(ActionEvent e) throws Exception
	{
		// TODO Auto-generated method stub
		if (isMultiSelect())
		{
			MsgBox.showInfo("请单选");
			SysUtil.abort();
		}
		super.actionRelate_actionPerformed(e);
	}

	// 更新
	@Override
	protected void btnUpdate_actionPerformed(ActionEvent e) throws Exception
	{
		// TODO Auto-generated method stub
		if (isMultiSelect())
		{
			MsgBox.showInfo("请单选");
			SysUtil.abort();
		}

		super.btnUpdate_actionPerformed(e);
	}

	// 附件
	@Override
	public void actionAttachment_actionPerformed(ActionEvent e)
			throws Exception
	{
		// TODO Auto-generated method stub
		if (isMultiSelect())
		{
			MsgBox.showInfo("请单选");
			SysUtil.abort();
		}
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * 根据选择的单据来移交权限， 把选择的行的FID保存到集合里。
	 */
	@Override
	public void grantAuthor_actionPerformed(ActionEvent e) throws Exception
	{
		// TODO Auto-generated method stub
		
		
		
		
		
		ArrayList al = new ArrayList();
		int[] selectRows = KDTableUtil.getSelectedRows(getBillListTable());
		for (int i = 0; i < selectRows.length; i++)
		{
			int selectedRow = selectRows[i]; //获得选择的行
			if(selectedRow==-1)
			{
				
			}else
			{
				Object id = tblMain.getRow(selectedRow).getCell("id").getValue();
				if(id!=null)
				{
					String inviteProjectId = id.toString();
					al.add(inviteProjectId);
				}
			}
		}
		
		//判断是否已经选择了行,如果未选择，则提示信息
		if(selectRows==null||selectRows.length==0||selectRows[0]==-1)
		{
			MsgBox.showInfo("请选择行");
			SysUtil.abort();
		}  
		
		
		UIContext uiContext = new UIContext(this);
		uiContext.put("list", al);
	    IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(HROpenUI.class.getName(), uiContext, null, OprtState.VIEW);
	    uiWindow.show();
	}

}
