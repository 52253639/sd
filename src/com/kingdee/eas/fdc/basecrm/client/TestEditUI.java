package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.ActionEvent;

import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.EditUI;

/**
 * 编辑界面模板代码,请注意修改历史
 * */
//主要介绍几个函数
//createNewData新增时初始化值对象edit	Data,getValue为查看或修改操作时初始化值对象editData;
//loadFields将editData的值设置到界面控件上
//storeFields将用户在界面上设置的值保存到editData值对象上
//每个方法作用纯粹单一,故不要在createNewData方法中设置控件状态,不要在storeFields中添加验证的操作
public class TestEditUI extends EditUI{
	protected boolean isLoadFields = false;
	
	public TestEditUI() throws Exception {
		super();
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}

	public void onLoad() throws Exception {
		initControl();
		
		super.onLoad();
	}
	
	private void initControl() {
		//TODO 初始化控件
		//TODO 固定规则的界面锁定,按钮锁定
	}

	public void loadFields() {
		//为了避免loadFields时触发相关控件的事件,使用该变量
		isLoadFields = true;
		//TODO 主要处理将editData加载到界面上的操作
		//TODO 根据某一属性设置另一控件的过滤条件，可以抽成独立方法在这里调用，可能会被事件中调用
		super.loadFields();
		isLoadFields = false;
	}
	
	protected void f7_dataChanged(DataChangeEvent e) throws Exception {
		if(isLoadFields){
			return;
		}
		//TODO 事件触发的操作
	}
	
	//TODO 对输入值是否合法的校验放在这里
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
	}
	
	public void storeFields() {
		//TODO 主要处理将界面上的数据获取写入editData，其他逻辑尽量不要写在这里
		super.storeFields();
	}
	
	public SelectorItemCollection getSelectors() {
		//TODO 对于未绑定的值对象字段,需要在这里指定,否则getValue默认不会查询此字段
		return super.getSelectors();
	}
	
	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		//TODO 对分录二次查询的操作，对分录的排序等操作可以放在这里
		return super.getValue(pk);
	}
	
	protected IObjectValue createNewData() {
		//TODO 新增时值对象的默认值在这里设置
		//TODO 从其他界面通过UIContext传入的值在这里获取设置到此值对象中
		return null;
	}
	
	//重载setOprtState方法，以保证其操作状态变化时，控件使能状态变化
	//TODO 控件按钮状态随操作状态控制的,控制代码写在这里
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
	}
	
}
