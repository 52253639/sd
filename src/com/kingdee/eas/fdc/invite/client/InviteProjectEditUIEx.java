package com.kingdee.eas.fdc.invite.client;

/**
 * 修改联查调用时候，用户可以修改单据
 * @author kingdee0001
 *
 */
public class InviteProjectEditUIEx extends InviteProjectEditUICTEx
{

	public InviteProjectEditUIEx() throws Exception
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 隐藏按钮
	 */
	private void hideButtons()
	{
		this.btnAddNew.setEnabled(false);
		this.btnRemove.setEnabled(false);
		this.btnCancel.setEnabled(false);
		this.btnAudit.setEnabled(false);
		this.btnUnAudit.setEnabled(false);
		this.btnAddLine.setEnabled(false);
		this.btnInsertLine.setEnabled(false);
		this.btnInsertPlan.setEnabled(false);
		this.btnRemoveLine.setEnabled(false);
		this.btnRemovePlan.setEnabled(false);
		this.btnRelate.setEnabled(false);
	}
	
	@Override
	public void onLoad() throws Exception
	{
		// TODO Auto-generated method stub
		super.onLoad();
		hideButtons();
		
	}

}
