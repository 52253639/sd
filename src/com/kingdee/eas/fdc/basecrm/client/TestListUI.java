package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.ListUI;

/**
 * 编辑界面模板代码,请注意修改历史
 * */
//这里只针对绑定Query的序时簿界面
//1.所有针对序时簿刷新的操作,统一使用this.refresh(null);
//2.序时簿需要添加的过滤条件,统一在getQueryExecutor中实现
public class TestListUI extends ListUI{

	public TestListUI() throws Exception {
		super();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	protected String getEditUIName() {
		return null;
	}
	
	//打开编辑界面的方式
	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}
	
	//初始化默认过滤条件
	protected boolean initDefaultFilter() {
		return true;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		initTree();
		//TODO 菜单按钮的锁定,隐藏等操作
	}
	
	protected void initTree() throws Exception{
		//TODO 若存在树，初始化的操作
	}
	
	//框架默认增加CU隔离,如果希望忽略CU隔离,重写此方法返回true
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	//不关注返回数据的总条数，为提高性能时可返回True，不执行count的操作
	public boolean isIgnoreRowCount() {
		return false;
	}
	
	//如果有添加自定义过滤面板,在此处添加	 
	protected CommonQueryDialog initCommonQueryDialog() {
		CommonQueryDialog commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(null/*自定义的过滤面板CustomerQueryPanel*/);
		return commonQueryDialog;
	}
	
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		//TODO 所有需要增加的过滤条件,在这里进行添加
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		//TODO 根据选中纪录的状态设置菜单按钮状态
		super.tblMain_tableClicked(e);
	}
	
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception{
		//根据选中的树结点设置菜单状态,过滤条件的设置到getQueryExecutor中设置
		//执行查询,尤其注意不要重复调用
		this.refresh(null);
//		this.execQuery();
//		this.tblMain.removeRows();
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		
		//删除之后的操作,注意基类的方法,如果对于"是否确认删除"的提示用户选择"否",函数仍会执行到这里
	}
	
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		//TODO 某些传入编辑界面的值不要写在actionAddNew中,写在这里
		super.prepareUIContext(uiContext, e);
	}
	
	//如果设置按照某个字段定位记录,在这里实现
	protected String[] getLocateNames() {
		return super.getLocateNames();
	}
}
