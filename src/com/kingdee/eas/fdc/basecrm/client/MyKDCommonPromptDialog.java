/*
* @(#)com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog
*
* 金蝶国际软件集团有限公司版权所有. 
*/
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;
import javax.swing.text.Document;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.LanguageManager;
import com.kingdee.bos.ctrl.common.util.StringUtil;
import com.kingdee.bos.ctrl.extendcontrols.IKDCommonPromptSelector;
import com.kingdee.bos.ctrl.extendcontrols.IReturnValueType;
import com.kingdee.bos.ctrl.extendcontrols.event.DataReadyEvent;
import com.kingdee.bos.ctrl.extendcontrols.event.DataReadyListener;
import com.kingdee.bos.ctrl.extendcontrols.ext.CommonF7KDTableListener;
import com.kingdee.bos.ctrl.extendcontrols.icons.ResourceManager;
import com.kingdee.bos.ctrl.extendcontrols.util.BosHelper;
import com.kingdee.bos.ctrl.extendcontrols.util.ParamHelper;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetListener;
import com.kingdee.bos.ctrl.kdf.form.Page;
import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootParser;
import com.kingdee.bos.ctrl.kdf.printprovider.headerfooter.ExtVarProvider;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IBlock;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.ISortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSortManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.KDTableHelper;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.CtrlPlainDocument;
import com.kingdee.bos.ctrl.swing.ICtrTextDocumentFilter;
import com.kingdee.bos.ctrl.swing.KDButton;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDPanel;
import com.kingdee.bos.ctrl.swing.KDPromptSelector;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDSeparator;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDToolBar;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.util.CtrlSwingUtilities;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.DataType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.OwnPropertyInfo;
import com.kingdee.bos.metadata.entity.PropertyInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.JoinQueryInfo;
import com.kingdee.bos.metadata.query.PropertyRefCollection;
import com.kingdee.bos.metadata.query.PropertyRefInfo;
import com.kingdee.bos.metadata.query.QueryFieldInfo;
import com.kingdee.bos.metadata.query.QueryInfo;
import com.kingdee.bos.metadata.query.SelectorInfo;
import com.kingdee.bos.metadata.query.SubEntityInfo;
import com.kingdee.bos.metadata.query.SubObjectInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.query.util.ConstDataType;
import com.kingdee.bos.sql.ParserException;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitCollection;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.cache.CacheService;
import com.kingdee.eas.framework.cache.EntityViewPK;
import com.kingdee.eas.framework.cache.QueryCacheObject;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.Uuid;

/**
 * 描述：通用F7对话框.
 * 
 * @author 符修湖, zhangfan
 * @version EAS51
 * 
* 修改人：hhmna  <p>
* 修改时间：2005-3-8 <p>
* 修改描述：adding filters of CU.  <p>
*
 * 
 */
public class MyKDCommonPromptDialog extends KDDialog implements KDPromptSelector,IKDCommonPromptSelector
{
	private static Logger logger = Logger.getLogger(MyKDCommonPromptDialog.class);

	// 确定或取消
	private boolean canceled = true;

	// 返回的数据
	private Object data;

	// query的执行器
	private IQueryExecutor queryExecutor;

	// 由外部传入的过滤条件
	private EntityViewInfo entityViewInfo;

	// 由内部集成的通用过滤返回的过滤条件
	protected EntityViewInfo innerFilter = new EntityViewInfo();

	// 决定返回值的东东
	protected SelectorItemCollection selectorCollection;

	// query对象
	private QueryInfo queryInfo;

	// 列表数据集
	private IRowSet rowSet;

	// 返回实体还是返回id
	private int returnValueType = IReturnValueType.ENTITY;

	private EventListenerList listenerList = new EventListenerList();

	// 是否支持多列多行选择的方式
	private boolean multipleSelect;

	// 标志是否要刷新table
	private boolean refresh = true;

	// 标志是否要刷新缓存的数据
	private boolean refreshCache = false;

	// 标志是否使用缓存服务
	private boolean useCacheObject = false;

	/**
	 * 是否通过CU过滤
	 */
	private boolean hasCUDefaultFilter = true;

	// 打印相关
	private static String printDateMsg = "Print_date";

	private static String printPersonMsg = "Print_person";

	private static String printPageCountMsg = "Print_pageCount";

	private UIContext maintainUIContext = null;

	private KDPanel filterPanel = null;

	//大小写敏感
    private boolean isCapitalSensitiveQuery = false;
    
//  //当前的query权限
//    private FieldPermission oldFieldPermission=null;
    
    //
    private final static String FIELD_PERMISSION_HANLE_INSTANCE  = "com.kingdee.eas.base.permission.client.F7FieldPermissionHandle";

    /**
	 * 创建通用F7对话框。 未指定父窗口。
	 */
	public MyKDCommonPromptDialog()
	{
		this((Frame) null);
		initCapitalSensitiveQuery();
	}

	/**
	 * 创建通用F7对话框。
	 * 
	 * @param owner
	 *            父窗口
	 */
	public MyKDCommonPromptDialog(Dialog owner)
	{
		this(owner, null);
		initCapitalSensitiveQuery();
	}

	/**
	 * 创建通用F7对话框(默认为模式对话框)。
	 * 
	 * @param owner
	 *            父窗口
	 */
	public MyKDCommonPromptDialog(Frame owner)
	{
		this(owner, null);
		initCapitalSensitiveQuery();
	}

	/**
	 * 创建通用F7对话框。
	 * 
	 * @param owner
	 *            父窗口
	 * @param title
	 *            对话框的标题
	 */
	public MyKDCommonPromptDialog(Dialog owner, String title)
	{
		super(owner, title, true);
		initCapitalSensitiveQuery();
	}

	/**
	 * 创建通用F7对话框。
	 * 
	 * @param owner
	 *            父窗口
	 * @param title
	 *            对话框的标题
	 */
	public MyKDCommonPromptDialog(Frame owner, String title)
	{
		super(owner, title, true);
		initCapitalSensitiveQuery();
	}
	
    private Context mainOrgContext = null;
    private OrgType typeOfMainBizOrgs;
    
    private OrgUnitInfo currentMainBizOrgUnit;
	public OrgType getMainBizOrgsType() {
        return typeOfMainBizOrgs;
    }
	public void setMainBizOrgsType(OrgType typeOfMainBizOrgs) {
        this.typeOfMainBizOrgs = typeOfMainBizOrgs;
    }
	
	/**
     * @param oui 强类型的业务组织单元
     * @param ot 组织单元类型
     * <p> 设置当前的主业务组织
     */
    public void setCurrentMainBizOrgUnit(OrgUnitInfo currentMainBizOrgUnit) {
    	this.currentMainBizOrgUnit = currentMainBizOrgUnit;
    }
    
    public OrgUnitInfo getCurrentMainBizOrgUnit() {
    	return this.currentMainBizOrgUnit;
    }
	
	public Context getMainOrgContext() {
		return mainOrgContext;
	}

	public void setMainOrgContext(Context mainOrgContext) {
		this.mainOrgContext = mainOrgContext;
	}
	
	private OrgUnitCollection mainBizOrgs;
	
	public OrgUnitCollection getMainBizOrgs() {
        return mainBizOrgs;
    }
	
	public void setMainBizOrgs(OrgUnitCollection mainBizOrgs) {
        this.mainBizOrgs =  mainBizOrgs;
    }
	/**
	 * 
	 * */
	private void initCapitalSensitiveQuery(){
        String capitalSensitiveQuery =  ParamHelper.getCapitalSensitiveQuery();
        if(capitalSensitiveQuery != null && capitalSensitiveQuery.equalsIgnoreCase("true")){
        	isCapitalSensitiveQuery = true;
        }
	}

	/**
	 * 是否使用客户端缓存对象。
	 * 
	 * @return Returns the useCacheObject.
	 */
	public boolean isUseCacheObject()
	{
		return useCacheObject;
	}

	/**
	 * 设置是否使用客户端缓存对象。
	 * 
	 * @param useCacheObject
	 *            The useCacheObject to set.
	 */
	public void setUseCacheObject(boolean useCacheObject)
	{
		this.useCacheObject = useCacheObject;
	}

	/**
	 * @return Returns the refresh.
	 */
	public boolean isRefresh()
	{
		return refresh;
	}

	/**
	 * @param refresh
	 *            The refresh to set.
	 */
	public void setRefresh(boolean refresh)
	{
		this.refresh = refresh;
	}

	public void setMaintainUIContext(UIContext context)
	{
		this.maintainUIContext = context;
	}
	public UIContext getMaintainUIContext(){
	    return this.maintainUIContext;
	}
	/**
	 * 获取EntityViewInfo对象。
	 * 
	 * @return 返回EntityViewInfo对象喝和用户自定义对象的合并对象
	 */
	public EntityViewInfo getEntityViewInfo2()
	{
		EntityViewInfo returnViewInfo = null;

		if (this.innerFilter != null)
		{
			try
			{
				returnViewInfo = new EntityViewInfo(entityViewInfo.toString());

				if (returnViewInfo.getFilter() != null && innerFilter.getFilter() != null)
				{
					// 如果用户自定义的条件为空，就不要合并了，用原来原始的条件，合并反而错（一个有内容的Fileter和没有内容的Filter合并为什么
					// 出来是 Where ？不明白）
					if (innerFilter.getFilter().getFilterItems() != null)
					{

						if (innerFilter.getFilter().getFilterItems().size() > 0)
						{
							returnViewInfo.getFilter().mergeFilter(innerFilter.getFilter(), "and");

							return returnViewInfo;
						}
					}

				}

			} catch (ParserException e)
			{
				// Don't need to do anything
			} catch (BOSException be)
			{
				// Don't need to do anything
			}

		}

		// To be optimized
		try
		{

			returnViewInfo = new EntityViewInfo(entityViewInfo.toString());
		}

		catch (ParserException e)
		{
			// Don't need to do anything
		}

		return returnViewInfo;
	}

	// private void addFilterToReturnViewInfoWhenControlTypeIAndD(EntityViewInfo
	// returnViewInfo) throws Exception {
	// FilterInfo fi=null;
	// if(getControlType().equalsIgnoreCase("I")) {
	// fi=getFilterInfoForControlTypeI();
	// } else if(getControlType().equalsIgnoreCase("D")) {
	// fi=getFilterInfoForControlTypeD();
	// }
	// if(fi!=null) {
	// if(returnViewInfo.getFilter()!=null) {
	// returnViewInfo.getFilter().mergeFilter(fi,"and");
	// } else {
	// returnViewInfo.setFilter(fi);
	// }
	// }
	// }

	/**
	 * 获取EntityViewInfo对象。
	 * 
	 * @return 返回EntityViewInfo对象喝和用户自定义对象的合并对象,此处需要对 Original EntityViewInfo 和
	 *         InnerEntityViewInfo 分不同情况进行合并 情况比较复杂
	 */
	public EntityViewInfo getEntityViewInfo()
	{
		EntityViewInfo returnViewInfo = null;

		if (this.entityViewInfo != null)
		{
			try
			{
				returnViewInfo = new EntityViewInfo(entityViewInfo.toString());
				// addFilterToReturnViewInfoWhenControlTypeIAndD(returnViewInfo);
			} catch (Exception e)
			{
				ExceptionHandler.handle(e);
			}

			if (entityViewInfo.getFilter() != null)
			{
				if (entityViewInfo.getFilter().getFilterItems() != null)
				{
					if (entityViewInfo.getFilter().getFilterItems().size() > 0)
					{
						if (this.innerFilter != null)
						{
							if (this.innerFilter.getFilter() != null)
							{
								if (innerFilter.getFilter().getFilterItems() != null)
								{
									if (innerFilter.getFilter().getFilterItems().size() > 0)
									{
										try
										{
											returnViewInfo.getFilter().mergeFilter(innerFilter.getFilter(), "and");
										} catch (BOSException be)
										{
											// If an exception is caused here,
											// will return the original
											// entityViewInfo
										}
									}

								}

							}
							// //make the sorter returned from common query
							// dialog effective.
							// if(this.innerFilter.getSorter() != null)
							// {
							// returnViewInfo.getSorter().clear();
							//							    
							// for(int i = 0 , j =
							// innerFilter.getSorter().size(); i < j ; i ++ )
							// {
							// returnViewInfo.getSorter().add(innerFilter.getSorter().get(i));
							// }
							//							    
							//							    
							// }

						}

					}
					// entityViewInfo.getFilter().getFilterItems().size()== 0
					else
					{
						// Do the same thing as it does when
						// entityViewInfo.getFilter() is null
						if (this.innerFilter != null)
						{
							if (this.innerFilter.getFilter() != null)
							{
								if (this.innerFilter.getFilter().getFilterItems() != null)
								{
									if (this.innerFilter.getFilter().getFilterItems().size() > 0)
									{
										try
										{
											returnViewInfo = new EntityViewInfo(innerFilter.toString());
											// addFilterToReturnViewInfoWhenControlTypeIAndD(returnViewInfo);
										} catch (Exception e)
										{
											ExceptionHandler.handle(e);
										}
									}
								}

							}

							// //make the sorter returned from common query
							// dialog effective.
							// if(this.innerFilter.getSorter() != null)
							// {
							// returnViewInfo.getSorter().clear();
							//							    
							// for(int i = 0 , j =
							// innerFilter.getSorter().size(); i < j ; i ++ )
							// {
							// returnViewInfo.getSorter().add(innerFilter.getSorter().get(i));
							// }
							//							    
							//							    
							// }
						}

					}

				}
				// (entityViewInfo.getFilter().getFilterItems()== null
				else
				{
					// Do the same thing as it does when
					// entityViewInfo.getFilter() is null
					if (this.innerFilter != null)
					{
						if (this.innerFilter.getFilter() != null)
						{
							if (this.innerFilter.getFilter().getFilterItems() != null)
							{
								if (this.innerFilter.getFilter().getFilterItems().size() > 0)
								{
									try
									{
										returnViewInfo = new EntityViewInfo(innerFilter.toString());
										// addFilterToReturnViewInfoWhenControlTypeIAndD(returnViewInfo);
									} catch (Exception e)
									{
										ExceptionHandler.handle(e);
									}
								}
							}

						}
					}

				}
			}
			// entityViewInfo.getFilter() == null
			else
			{
				if (this.innerFilter != null)
				{
					if (this.innerFilter.getFilter() != null)
					{
						if (this.innerFilter.getFilter().getFilterItems() != null)
						{
							if (this.innerFilter.getFilter().getFilterItems().size() > 0)
							{
								try
								{
									returnViewInfo = new EntityViewInfo(innerFilter.toString());
									// addFilterToReturnViewInfoWhenControlTypeIAndD(returnViewInfo);
								} catch (Exception e)
								{
									ExceptionHandler.handle(e);
								}
							}
						}

					}
				}

			}
		}
		// this.entityViewInfo == null
		else
		{
			if (this.innerFilter != null)
			{

				try
				{
					returnViewInfo = new EntityViewInfo(innerFilter.toString());
					// addFilterToReturnViewInfoWhenControlTypeIAndD(returnViewInfo);
				} catch (Exception e)
				{
					ExceptionHandler.handle(e);
				}
			}

		}
		// make the sorter returned from common query dialog effective.
		if (returnViewInfo != null && innerFilter != null)
		{
			if (returnViewInfo.getSorter() != null && innerFilter.getSorter() != null && innerFilter.getSorter().size()>0)
			{

				returnViewInfo.getSorter().clear();

				for (int i = 0, j = innerFilter.getSorter().size(); i < j; i++)
				{
					returnViewInfo.getSorter().add(innerFilter.getSorter().get(i));
				}

			}

		}
		if (returnViewInfo != null && mainQuery != null)
		{
			if (returnViewInfo.getSorter() != null && mainQuery.getSorter() != null && mainQuery.getSorter().size()>0)
			{
				returnViewInfo.getSorter().clear();

				for (int i = 0, j = mainQuery.getSorter().size(); i < j; i++)
				{
					returnViewInfo.getSorter().add(mainQuery.getSorter().get(i));
				}

			}

		}
		// ToDo
		// invoke a method to add information of CU
		// combineRuntimeFilterAndCU(returnViewInfo);
		return returnViewInfo;

	}

	/**
	 * 获取EntityViewInfo对象。
	 * 
	 */
	public EntityViewInfo getOriginalEntityViewInfo()
	{
		return entityViewInfo;

	}

	/**
	 * 设置EntityViewInfo对象。
	 * 
	 * @param info
	 *            EntityViewInfo对象
	 */
	public void setEntityViewInfo(EntityViewInfo info)
	{
		entityViewInfo = info;
		this.setRefresh(true);
	}

	/**
	 * 获取列表的选择方式。
	 * 
	 * @return true支持多选，false不支持多选
	 */
	public boolean isEnabledMultipleSelect()
	{
		return multipleSelect;
	}

	/**
	 * 设置列表的选择方式。
	 * 
	 * @param b
	 *            true支持多选，false不支持多选
	 */
	public void setEnabledMultiSelection(boolean b)
	{
		multipleSelect = b;
		// 单行或多行选择
		table.getSelectManager().setSelectMode(b ? KDTSelectManager.MULTIPLE_ROW_SELECT : KDTSelectManager.ROW_SELECT);
	}

	/**
	 * 用Rowset填充列表。
	 * 
	 * @param rs
	 *            rowset
	 */
	public void putRowSet(IRowSet rs)
	{
		rowSet = rs;
		this.setRefresh(true);
	}

	/**
	 * 刷新整个列表(包括表头)。
	 */
	public void refresh()
	{
		// 设置table不刷新
		this.table.setRefresh(false);

		// 清空原先的数据
		clearTable();
		// 填充数据
		fillTable();
		// 发数据准备好事件
		fireAfterDataReady(new DataReadyEvent(this, table, queryInfo));
		// 重新取数
		table.setRefresh(true);
		
		table.refresh();
		// table.repaint();
		this.setRefresh(false);
	}

	public void refreshData(boolean refreshCache)
	{
		this.refreshCache = refreshCache;
		table.removeRows();
		this.refreshCache = false;
	}

	/**
	 * 添加数据准备好事件侦听者。
	 * 
	 * @param l
	 *            事件侦听者
	 */
	public void addDataReadyListener(DataReadyListener l)
	{
		listenerList.add(DataReadyListener.class, l);
	}

	/**
	 * 删除数据准备好事件侦听者。
	 * 
	 * @param l
	 *            事件侦听者
	 */
	public void removeDataReadyListener(DataReadyListener l)
	{
		listenerList.remove(DataReadyListener.class, l);
	}

	/**
	 * 触发数据准备好事件
	 * 
	 * @param e
	 */
	void fireAfterDataReady(DataReadyEvent e)
	{
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2)
		{
			if (listeners[i] == DataReadyListener.class)
			{
				((DataReadyListener) listeners[i + 1]).afterDataReady(e);
			}
		}
	}

	// //////////////////////////////////////////////////////////////////////////////
	// 实现KDPromptSelector
	// //////////////////////////////////////////////////////////////////////////////
	/**
	 * 显示通用F7对话框。
	 */
	public void show()
	{
		if (this.isRefresh())
		{
			refresh();
		}

		this.table.requestFocusInWindow();
		// 关联btnOK到enter
		this.getRootPane().setDefaultButton(this.btnOK);
		// Set the state of btnMaintainObject according to whether the entity
		// has the information of ListUI or not
		// String uiName = this.getEntityObjectListUI();
		// if (uiName == null || uiName.equals(""))
		if (!this.isF7Maintain())
		{
			this.btnMaintainObject.setVisible(false);
		}

		// by Frank Zhang 2005.7.27
		fillFilterFields();

		//处理字段权限
		doFieldPermission();
		super.show();
	}

	/**
	 * 弹出选框关闭后，返回是否取消
	 * 
	 * @return
	 */
	public boolean isCanceled()
	{
		return canceled;
	}

	/**
	 * 获得弹出选框中的数据
	 * 
	 * @return
	 */
	public Object getData()
	{
		return data;
	}

	// //////////////////////////////////////////////////////////////////////////////
	// 创建弹出对话框的内容
	// //////////////////////////////////////////////////////////////////////////////
	private static final int DIALOG_WIDTH = 640;

	private static final int DIALOG_HEIGHT = 480;

	private static final int WIDTH = 450;

	private static final int HEIGHT = 340;

	private static final int PREFERRED_WIDTH = 350;

	private static final int PREFERRED_HEIGHT = 252;

	private static final int SEPERATOR_HEIGHT = 2;

	private static final int BUTTON_WIDTH = 73;

	private static final int BUTTON_HEIGHT = 21;

	private static final int SPAN = 10;

	private static final int BUTTON_SPAN = 3;

	private static final int OBJECT_LISTUI_WIDTH = 800;

	private static final int OBJECT_LISTUI_HEIGHT = 600;

	private KDTable table;

	private KDButton btnOK;

	private KDButton btnCancel;

	private KDWorkButton btnFilter;

	private KDWorkButton btnRefresh;

	private KDWorkButton btnPrint;

	private KDWorkButton btnPrintPreview;

	// Added for maintaining object by hhmna on Nov. 13.
	private KDWorkButton btnMaintainObject;

	private KDWorkButton btnExit;

	private KDSeparator separator;

	protected void dialogInit()
	{
		super.dialogInit();
		initDialog2();
	}

	// 使用KDLayout
	private void initDialog2()
	{
		// 设置dialog的默认大小和定位方式
		this.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(KDDialog.DISPOSE_ON_CLOSE);

		initToolBar();

		initContentPanel();

		initControls();

		this.addWindowListener(new WindowAdapter()
		{
			public void windowOpened(WindowEvent e)
			{
				table.requestFocusInWindow();
			}
			public void windowActivated(WindowEvent e)
			{
				table.requestFocusInWindow();
			}
			public void windowClosed(WindowEvent e) {
				if(canceled){
					data = null;
				}
				canceled = true;				
			}
//			public void windowClosing(WindowEvent e)
//			{
//				canceled = true;
//			}
		});
	}

    // 是否第一次排序
    private boolean isFristSortTable = true; 
    
    private SorterItemInfo pre_order = null;

    private SorterItemInfo current_order = null;

    private int order_count = 0;

    // 排序管理器。
    private ISortManager sm = null;
    
    private EntityViewInfo mainQuery = null;
    
    private SorterItemCollection oldSortItem = null;
    
    // 表格排序，默认第一次升序。
    private int sort = KDTSortManager.SORT_ASCEND;
    
    private void OrderByForTable(KDTMouseEvent e) throws Exception {
        mainQuery = this.getEntityViewInfo();
        if (e.getType() == KDTStyleConstants.HEAD_ROW) {
            if (isFristSortTable) {
                sm = new KDTSortManager(table);
                if (this.mainQuery == null) {
                    return;
                }
                if (this.mainQuery.getSorter() != null)
                    this.oldSortItem = (SorterItemCollection) this.mainQuery
                            .getSorter().clone();
                isFristSortTable = false;
            }

            String columnName = table.getColumn(e.getColIndex())
                    .getFieldName();
            // 没有绑定字段则返回，不处理。
            if (columnName == null) {
                return;
            }

            if (getNotOrderColumns() != null && getNotOrderColumns().length > 0) {
                for (int i = 0; i < getNotOrderColumns().length; i++) {
                    // 如果点击不要求排序的列，则不处理。2005-12-28
                    if (getNotOrderColumns()[i].equalsIgnoreCase(columnName)) {
                        return;
                    }
                }
            }

            if (sort == KDTSortManager.SORT_ASCEND) {
                sort = KDTSortManager.SORT_DESCEND;
            } else {
                sort = KDTSortManager.SORT_ASCEND;
            }

            sm.sort(e.getColIndex(), this.sort);
            if (this.sort == KDTSortManager.SORT_ASCEND) {
                SorterItemInfo sortName = new SorterItemInfo(table.getColumn(
                        e.getColIndex()).getFieldName());
                sortName.setSortType(SortType.ASCEND);

                if (this.pre_order == null) {
                    this.pre_order = (SorterItemInfo) sortName.clone();
                    this.current_order = (SorterItemInfo) sortName.clone();
                } else if (this.current_order != null
                        && !this.current_order.getPropertyName().equals(
                                sortName.getPropertyName())) {
                    this.order_count++;
                    // this.current_order = sortName;
                    if (order_count > 0) {
                        this.pre_order = (SorterItemInfo) current_order.clone();
                        this.order_count = 0;
                        this.current_order = (SorterItemInfo) sortName.clone();
                    }
                } else if (this.current_order != null
                        && this.current_order.getPropertyName().equals(
                                sortName.getPropertyName())) {
                    this.current_order = (SorterItemInfo) sortName.clone();
                }

                setSortForQuery(sortName, this.pre_order);

            } else {
                SorterItemInfo sortName = new SorterItemInfo(table.getColumn(
                        e.getColIndex()).getFieldName());
                sortName.setSortType(SortType.DESCEND);

                if (this.pre_order == null) {
                    this.pre_order = (SorterItemInfo) sortName.clone();
                    this.current_order = (SorterItemInfo) sortName.clone();
                } else if (this.current_order != null
                        && !this.current_order.getPropertyName().equals(
                                sortName.getPropertyName())) {
                    this.order_count++;
                    // .this.current_order = sortName;
                    if (order_count > 0) {
                        this.pre_order = (SorterItemInfo) current_order.clone();
                        this.order_count = 0;
                        this.current_order = (SorterItemInfo) sortName.clone();
                    }
                } else if (this.current_order != null
                        && this.current_order.getPropertyName().equals(
                                sortName.getPropertyName())) {
                    this.current_order = (SorterItemInfo) sortName.clone();
                }

                setSortForQuery(current_order, this.pre_order);
            }

        }
    }

    // 返回不需要排序的表列数组。
    protected String[] getNotOrderColumns() {
        return null;
    }
    protected void setSortForQuery(SorterItemInfo sortItem,
            SorterItemInfo oldSortItem) throws Exception 
    {
        // 清除以前的排序处理。
        if (this.mainQuery.getSorter() != null)
        {
            this.mainQuery.getSorter().clear();
        }

        if (sortItem.getPropertyName() != IFWEntityStruct.coreBase_ID) 
        {
            this.mainQuery.getSorter().add(sortItem);
            this.mainQuery.getSorter().add(new SorterItemInfo("id"));
        }
        // 防止业务系统的排序冲突。
        if (oldSortItem != null
                && !oldSortItem.getPropertyName().equals(
                        sortItem.getPropertyName())) 
        {
            if(isOrderPre())
            {
                this.mainQuery.getSorter().add(oldSortItem);
            }
        }
		refreshData(true);
    }
    
    /**
     * 是否增加上次的排序规则。 2006-9-26
     * @return
     */
    protected boolean isOrderPre()
    {
        return true;
    }
	private void initToolBar()
	{
		// toolbar
		KDToolBar tb = new KDToolBar(KDToolBar.NORTH);
		this.addToolBar(tb);

		// 创建button
		btnRefresh = new KDWorkButton();
		btnPrint = new KDWorkButton();
		btnPrintPreview = new KDWorkButton();
		btnFilter = new KDWorkButton();
		btnMaintainObject = new KDWorkButton();
		btnExit = new KDWorkButton();

		// 先不提供该功能
		// btnFilter.setVisible(false);

		// 设置tooltip
		btnRefresh.setToolTipText(this.getMLS("Refresh", "Refresh"));
		btnPrint.setToolTipText(this.getMLS("Print", "Print"));
		btnPrintPreview.setToolTipText(this.getMLS("PrintPreview", "PrintPreview"));
//		btnFilter.setToolTipText(this.getMLS("Filter", "Filter"));
		btnFilter.setToolTipText(this.getMLS("Query", "Query"));
		btnMaintainObject.setToolTipText(this.getMLS("MaintainObject", "MaintainObject"));
		btnExit.setToolTipText(EASResource.getString("exit"));

		// 设置 text
		btnRefresh.setText(this.getMLS("Refresh", "Refresh"));
		btnPrint.setText(this.getMLS("Print", "Print"));
		btnPrintPreview.setText(this.getMLS("PrintPreview", "PrintPreview"));
		btnFilter.setText(this.getMLS("Query", "Query"));
		btnMaintainObject.setText(this.getMLS("MaintainObject", "MaintainObject"));
		btnExit.setText(EASResource.getString("exit"));

		// 设置图标
		Icon refreshIcon = this.getMLIcon("refresh.gif");
		if (refreshIcon != null)
			btnRefresh.setIcon(refreshIcon);
		Icon printIcon = this.getMLIcon("print.gif");
		if (printIcon != null)
			btnPrint.setIcon(printIcon);
		Icon printReviewIcon = this.getMLIcon("printpreview.gif");
		if (printReviewIcon != null)
			btnPrintPreview.setIcon(printReviewIcon);
		Icon filterIcon = this.getMLIcon("filter.gif");
		if (filterIcon != null)
			btnFilter.setIcon(filterIcon);
		Icon maintainIcon = this.getMLIcon("maintenance.gif");
		if (maintainIcon != null)
			btnMaintainObject.setIcon(maintainIcon);
		btnExit.setIcon(EASResource.getIcon("imgTbtn_quit"));

		btnExit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				canceled = true;
				dispose();
			}
		});

		tb.add(btnFilter);
		tb.add(btnRefresh);
		// Added for maintaining object by hhmna on Nov. 13.
		tb.add(btnMaintainObject);
		tb.add(btnExit);

		// tb.add( btnPrint );
		// tb.add( btnPrintPreview );
	}

	private void initContentPanel()
	{
		KDScrollPane sp = new KDScrollPane(getPanelWithAllComponentsForContentPane());
		sp.setBorder(null);
		setContentPane(sp);
	}

	private KDPanel getPanelWithAllComponentsForContentPane()
	{
		allComponentsPanel = new KDPanel();
		allComponentsPanel.setLayout(new BorderLayout());
		filterPanel = getPanelForQuickFilterComponents();
		if(!hasHideQuichFilterPanel)
		{	
			allComponentsPanel.add(filterPanel, BorderLayout.NORTH);
		}
		allComponentsPanel.add(getOldContentPanel(), BorderLayout.CENTER);
		return allComponentsPanel;
	}

	
	private boolean hasHideQuichFilterPanel = false;
	
	public void setHasHideQuichFilterPanel(boolean hasHideQucikFilterPanel)
	{
		this.hasHideQuichFilterPanel = hasHideQucikFilterPanel;
		if(hasHideQuichFilterPanel)
		{
			allComponentsPanel.remove(quickFilterPanel);
			allComponentsPanel.validate();
		}else
		{
			allComponentsPanel.add(quickFilterPanel, BorderLayout.NORTH);
			allComponentsPanel.validate();
		}
	}
	
	public boolean getIsHideQuichFilterPanel()
	{
		return hasHideQuichFilterPanel;
	}
	
	private KDPanel getOldContentPanel()
	{

		oldContentPanel = new KDPanel();

		// 创建table和button
		table = new KDTable();
		btnOK = new KDButton();
		btnCancel = new KDButton();
		separator = new KDSeparator();

		btnOK.setText(this.getMLS("OK", "OK"));
		btnCancel.setText(this.getMLS("Cancel", "Cancel"));

		oldContentPanel.add(table);
		oldContentPanel.add(separator);
		oldContentPanel.add(btnOK);
		oldContentPanel.add(btnCancel);

		oldContentPanel.setBackground(new Color(0xE4E4DF));
		oldContentPanel.setLayout(new KDLayout());
		((JComponent) oldContentPanel).putClientProperty("OriginalBounds", new Rectangle(0, 0, WIDTH, HEIGHT));
		((JComponent) oldContentPanel).setPreferredSize(new Dimension(PREFERRED_WIDTH, PREFERRED_HEIGHT));

		int x;
		int y;
		int width;
		int height;
		// OK Cancel
		KDLayout.Constraints cons;

		x = WIDTH - SPAN - BUTTON_WIDTH;
		y = HEIGHT - SPAN - BUTTON_HEIGHT;
		width = BUTTON_WIDTH;
		height = BUTTON_HEIGHT;
		cons = new KDLayout.Constraints();
		// cons.originalBounds = new Rectangle(268, 310, 79, 21);
		cons.originalBounds = new Rectangle(x, y, width, height);
		cons.anchor = KDLayout.Constraints.ANCHOR_RIGHT | KDLayout.Constraints.ANCHOR_BOTTOM;
		btnCancel.putClientProperty("KDLayoutConstraints", cons);

		x = x - BUTTON_SPAN - BUTTON_WIDTH;
		cons = new KDLayout.Constraints();
		// cons.originalBounds = new Rectangle(350, 310, 79, 21);
		cons.originalBounds = new Rectangle(x, y, width, height);
		cons.anchor = KDLayout.Constraints.ANCHOR_RIGHT | KDLayout.Constraints.ANCHOR_BOTTOM;
		btnOK.putClientProperty("KDLayoutConstraints", cons);

		x = 0;
		y = y - SPAN - SEPERATOR_HEIGHT;
		width = WIDTH;
		height = SEPERATOR_HEIGHT;
		cons = new KDLayout.Constraints();
		// cons.originalBounds = new Rectangle(0, 300, 500, 2);
		cons.originalBounds = new Rectangle(x, y, width, height);
		cons.anchor = KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT | KDLayout.Constraints.ANCHOR_BOTTOM;
		separator.putClientProperty("KDLayoutConstraints", cons);

		height = y - 2 * SPAN;
		width = WIDTH - 2 * SPAN;
		x = SPAN;
		y = SPAN;
		cons = new KDLayout.Constraints();
		// cons.originalBounds = new Rectangle(10,10,300,220);
		cons.originalBounds = new Rectangle(x, y, width, height);
		cons.anchor = KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT | KDLayout.Constraints.ANCHOR_TOP
				| KDLayout.Constraints.ANCHOR_BOTTOM;
		table.putClientProperty("KDLayoutConstraints", cons);

		return oldContentPanel;
	}

	private void initControls()
	{

		// 修改KDTable的enter和tab、esc键的行为
		KDTableHelper.releaseEnterAndTab(table);
		KDTableHelper.releaseEsc(table);

		CtrlSwingUtilities.removeManagingFocusForwardTraversalKeys(table, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));

		// 设置table和button
		// 单行或多行选择
		table.getSelectManager().setSelectMode(this.isEnabledMultipleSelect() ? KDTSelectManager.MULTIPLE_ROW_SELECT : KDTSelectManager.ROW_SELECT);

		table.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.VIRTUAL_MODE_PAGE);
		// 不可编辑
		table.getStyleAttributes().setLocked(true);
		// 双击事件
		table.addKDTMouseListener(new KDTMouseListener()
		{
			public void tableClicked(KDTMouseEvent e)
			{
				if (e.getClickCount() == 2 && e.getType() == 1)
				{
					btnOK_actionPerformed(null);
				}

		        if (e.getType()==KDTStyleConstants.HEAD_ROW  
		                && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount()==1) 
		        {
		        	try
		        	{
		        		OrderByForTable(e); 
		        	} catch (Exception exc) {
	                } finally {
	                }
		            return;
		        }
			}
		});

		// 添加绑定事件
		table.addRequestRowSetListener(new RequestRowSetListener()
		{
			public void doRequestRowSet(RequestRowSetEvent e)
			{
				kdtTable_doRequestRowSet(e);
			}
		});

		table.addKDTDataFillListener(new KDTDataFillListener()
		{
			public void afterDataFill(KDTDataRequestEvent e)
			{
				setPrecisionFormat(e.getFirstRow(), e.getLastRow());
				// 默认选中第一行
				if (table.getRow(0) != null)
					table.getSelectManager().select(0, -1);
				
				handleDisplayCol(e, table);
			}
			
			//根据精度列设置显示列的Format
			private void setPrecisionFormat(int begin , int last){
				String[] precisionColumns = getPrecisionColumns();
				String[] displayColumns = getPrecisionDisplayColumns();
				if(precisionColumns == null || displayColumns == null
					|| precisionColumns.length != displayColumns.length){
					return;
				}
				for (int i = begin; i < last+1 ; i++) {	
					for(int j = 0; j < precisionColumns.length; j++){
						try{
							Integer curRowPre = (Integer)table.getRow(i).getCell(precisionColumns[j]).getValue();						
							ICell cell = table.getRow(i).getCell(displayColumns[j]);
							cell.getStyleAttributes().setNumberFormat(
									KDTableUtil.getNumberFormat(curRowPre.intValue(),true));
						}catch(Exception e){
							e.printStackTrace();
						}
					}					
				}				
			}
		});

		btnOK.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				btnOK_actionPerformed(e);
			}
		});

		btnCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				btnCancel_actionPerformed(e);
			}
		});

		btnRefresh.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				refreshData(true);
			}
		});

		btnFilter.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dataFilter();
			}
		});

		btnPrint.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				table.getPrintManager().print();
			}
		});

		btnPrintPreview.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{

				preparePrintPage(table);
				table.getPrintManager().printPreview();
			}
		});

		// Added for maintaining object by hhmna on Nov. 13.

		btnMaintainObject.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				benMaintainObject_actionPerformed(e);
			}
		});

		// ControlUtilities.registerEscapeAction(this.getRootPane(), new
		// AbstractAction() {
		// public void actionPerformed(ActionEvent e)
		// {
		// btnCancel_actionPerformed(e);
		// }
		// });
	}

	protected void handleDisplayCol(KDTDataRequestEvent e, KDTable table) {
		
		
		
	}
	
	private void clearTable()
	{
		// table.removeHeadRows();
		// table.removeRows();
		// for (int i = table.getColumnCount() - 1; i >= 0; i--) {
		// table.removeColumn(i);
		// }
		table.removeColumns();
		// table.setRowCount(-1);
	}

	private void fillTable()
	{
		if (this.queryInfo == null)
		{
			throw new IllegalArgumentException("QueryInfo can not be null!");
		}
		// 获取selector并根据selector填充table
		SelectorInfo selector = this.queryInfo.getSelector();
		int size = selector.getFields().size();
		QueryFieldInfo field;
		String[] bindContents = new String[size];

		// 扩展属性
		Map exProperties = queryInfo.getExtendedProperties();

		// 设置对话框的title
		if (exProperties != null)
		{
			Object title = exProperties.get("F7WindowTitle");
			if (title == null)
				title = exProperties.get("f7WindowTitle");

			if (title instanceof String)
			{
				String sTitle = EASResource.getString(title.toString());
				if(ResourceBase.MISSING_STRING.equals(sTitle)){
					sTitle = queryInfo.getAlias();
				}
				this.setTitle(sTitle);
			}
			else{
				String sTitle = queryInfo.getAlias();
				this.setTitle(sTitle);
			}
		}

		// 创建列
		for (int i = 0; i < size; i++)
		{
			// 只添加要显示的列
			IColumn col = table.addColumn(i);
			field = (QueryFieldInfo) selector.getFields().get(i);
			exProperties = field.getExtendedProperties();

			try
			{
				ConstDataType cdt = field.getReturnType();
				if (cdt != null)
				{
					if (cdt.equals(ConstDataType.INT) || cdt.equals(ConstDataType.NUMBER))
					{
						col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
					}
					if(cdt.equals(ConstDataType.INT) && this.isEnumField(field))
					{
						col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);					
					}
				}
			} catch (Exception e)
			{
			}

			// 记录每列对应的key
			bindContents[i] = field.getName();
			col.setKey(bindContents[i]);

			// 由扩展属性读取隐藏属性和列宽
			if (exProperties != null)
			{
				Object visible = exProperties.get("isVisibleForKDTable");
				if (visible instanceof Boolean)
				{
					col.getStyleAttributes().setHided((!((Boolean) visible).booleanValue()));
				} else if (visible instanceof String)
				{
					col.getStyleAttributes().setHided(!MyKDCommonPromptDialog.stringToboolean((String) visible));
				}

				Object colWidth = exProperties.get("ColWidthInKDTable");
				if (colWidth == null)
					colWidth = exProperties.get("colWidthInKDTable");

				if (colWidth instanceof Integer)
				{
					col.setWidth(((Integer) colWidth).intValue());
				} else if (colWidth instanceof String)
				{
					col.setWidth(MyKDCommonPromptDialog.stringToint((String) colWidth));
				}

				// 特殊要求 编码列100 名称列200
				if (field.getName().equals("number"))
				{
					col.setWidth(100);
				} else if (field.getName().equals("name"))
				{
					col.setWidth(200);
				}

				Object colFormat = exProperties.get("displayFormat");
				if (colFormat instanceof String)
				{
					String sTemp = (String) colFormat;
					if (!StringUtil.isEmptyString(sTemp))
						col.getStyleAttributes().setNumberFormat(sTemp);
				}
			}
		}

		// 设置绑定信息
		table.putBindContents("query", bindContents);
		// kdtable逻辑变动：将解析的过程滞后到显示时
		table.setFormatXml("");
		table.checkParsed(true);

		// 创建空表头
		IRow row = table.addHeadRow();
		for (int i = 0; i < size; i++)
		{
			field = (QueryFieldInfo) selector.getFields().get(i);
			// 要支持多语言
			String displayName = field.getDisplayName(SysContext.getSysContext().getLocale());
			// 设置表头单元的值
			row.getCell(i).setValue(displayName);
		}

		// 特殊需求 冻结前三列
//		table.getViewManager().setFreezeView(0, 3);
//		table.getViewManager().freeze(-1,3);
		setMergeColumn();
		
		callCommonF7KDTableListener(table);
	}

	private Object buildReturnValue()
	{
		/*
		KDTSelectManager sm = table.getSelectManager();
		int size = sm.size();
		KDTSelectBlock sb2;
		final ArrayList datas = new ArrayList();

		if (size > 0)
		{
			String mainKey = BosHelper.getMainObjectPKField(queryInfo);

			if (mainKey == null || mainKey.length() < 1)
			{
				return null;
			}

			for (int i = 0; i < size; i++)
			{
				IBlock sb = sm.get(i);
//				IBlock sb = KDTSelectBlock.change(table, sb2);

				int top = 0;
				int bottom = 0;
				
				if(sb.getMode()==KDTSelectBlock.TABLE_BLOCK)
				{//获取实际的返回的总行数
					bottom = table.getRowCount3();
				}else
				{
					//added by klaus 虚模式下全选时仅选择第一行
					top = sb.getTop()>=0?sb.getTop():0;
					bottom = sb.getBottom()>=0?sb.getBottom():0;
				}
				for (int j = top; j <= bottom; j++)
				{
					IRow row = table.getRow(j);
					
					if(row == null || row.getCell(mainKey) == null)
					{
						continue;
					}
					Object v = row.getCell(mainKey).getValue();
					String id = "";
					if (v != null)
					{
						id = v.toString();
					}

					// reutrn id
					if (this.getReturnValueType() == IReturnValueType.ID)
					{
						datas.add(id);
					}

					// return entity
					else if (this.getReturnValueType() == IReturnValueType.ENTITY)
					{
						if (id.length() < 1)
						{
//							datas.add(null);
						} else
						{
							// datas.add(BosHelper.getRemoteValue(id,
							// this.getSelectorCollection()));
							datas.add(id);
//							datas.add(this.getValue(id, this.getSelectorCollection()));
						}
					}
				}
			}
		}
		*/
		
		final ArrayList datas = new ArrayList();
		ArrayList ids  = getSelectedIdValues();
		datas.addAll(ids);
		
		if(this.getReturnValueType() == IReturnValueType.ENTITY && datas.size()>1)
		{

			LongTimeDialog ltd = null;
			Object o = SwingUtilities.getWindowAncestor(this);
			if(o instanceof Dialog)
			{
				ltd = new LongTimeDialog((Dialog)o );
			}else if(o instanceof Frame)
			{
				ltd = new LongTimeDialog((Frame)o );
			}
			ltd.setLongTimeTask(new ILongTimeTask()
			{
				public Object exec() throws Exception
				{
					getValueCollection(datas,getSelectorCollection());
					return null;
				}
				public void afterExec(Object arg0) throws Exception
				{
					
				}
			});
			ltd.show();
		}else if(this.getReturnValueType() == IReturnValueType.ENTITY && datas.size()<=1)
		{
			ArrayList returnDatas = getValueCollection(datas,getSelectorCollection());
			if(returnDatas == null){
				return returnDatas;
			}
		}
		return datas.toArray();
	}

    /**
     * 返回所选行的ID列表。
     *
     * @return
     */
    protected ArrayList getSelectedIdValues()
    {
    	ArrayList selectList = null;
        selectList = new ArrayList();
        List selectKeyIdFields=null;
        selectKeyIdFields=this.getQueryPkList();
        return ListUiHelper.getSelectedIdValues(this.table,this.getKeyFieldName(),selectList,selectKeyIdFields);
    }
    
    private Uuid queryUuid = null;
    /**
     * 获取所有的id
     */
    protected List getQueryPkList(){
        List list=new ArrayList();
        try
        {
//            IQueryExecutor iexec = getQueryExecutor(this.mainQueryPK, this.mainQuery);
        	IQueryExecutor iexec = getQueryExecutor();
            if (queryUuid == null)
            {
                queryUuid = iexec.openQuery();
            }
            list=iexec.getQueryKeys(queryUuid);
        }catch(Exception E){
          logger.error(E);
          E.printStackTrace();
        }
        return list;
    }
    /**
     * 获取query中的主键列名称，返回供编辑/删除时获取主键用，默认值为"id"，继承类可以重载
     *
     * @return query中的主键列名称
     */
    protected String getKeyFieldName()
    {
//        return "id";
    	//有些query的主键不是id的也考虑
        String mainKey = BosHelper.getMainObjectPKField(queryInfo);

		if (mainKey == null || mainKey.length() < 1)
		{
			mainKey = "id";
		}
		return mainKey;
    }
    
	public IObjectValue getValue2(String pkValue) throws BOSException
	{
		SubObjectInfo mainObjectInfo = this.getQueryInfo().getMainObject();
		SubEntityInfo mainEntityInfo = null;
		BOSUuid id = null;

		if (mainObjectInfo instanceof SubEntityInfo)
		{
			mainEntityInfo = (SubEntityInfo) mainObjectInfo;
		}

		if (mainEntityInfo != null)
		{
			return DynamicObjectFactory.getRemoteInstance().getValue(mainEntityInfo.getBOSType(), new ObjectUuidPK(id));
		} else
		{
			id = BOSUuid.read(pkValue);
			return DynamicObjectFactory.getRemoteInstance().getValue(id.getType(), new ObjectUuidPK(id));

		}
	}


	public ArrayList getValueCollection(ArrayList ids, SelectorItemCollection sic)
	{
		try
		{
			IObjectCollection coll =null;
			SubObjectInfo mainObjectInfo = this.getQueryInfo().getMainObject();
			SubEntityInfo mainEntityInfo = null;
			BOSUuid id = null;
			if(ids != null && ids.size() > 0){
				id = BOSUuid.read(ids.get(0).toString());	
			}
			
			if (mainObjectInfo instanceof SubEntityInfo)
			{
				mainEntityInfo = (SubEntityInfo) mainObjectInfo;
				// Get business object related to mainEntity.
				mainEntityInfo.getEntityRef();
			}

			EntityViewInfo evInfo = new EntityViewInfo();
			FilterInfo fInfo = new FilterInfo();
			Set ids1 =new HashSet();
			ids1.addAll(ids);
			fInfo.getFilterItems().add(new FilterItemInfo("id",ids1,CompareType.INCLUDE));
			evInfo.setFilter(fInfo);
			evInfo.getSelector().addObjectCollection(sic);
			BOSObjectType type = null;
			if (mainEntityInfo != null && mainEntityInfo.getEntityRef() != null)
			{
				type = mainEntityInfo.getEntityRef().getType();
			} else
			{
				type = id.getType();
			}
			coll =  DynamicObjectFactory.getRemoteInstance().getCollection(type,evInfo);
			ArrayList result = new ArrayList();
			/*
			for(int i=0,size=ids.size();i<size;i++)
			{
				String tmpid = (String)ids.get(i);
				for(int j=0;j<coll.size();j++)
				{
					IObjectValue ov = coll.getObject(j);
					if(ov.get("id").toString().equals(tmpid))
					{
						result.add(ov);
						coll.removeObject(ov);
					}
				}
			}
			*/
			for(int i = 0, count = coll.size(); i < count; i++){
				IObjectValue ov = coll.getObject(i);
				result.add(ov);
			}
			ids.clear();
			ids.addAll(result);
			return ids;
		} catch (BOSException e)
		{
			ExceptionHandler.handle(e);
			return null;

		}
	}
	/*
	 * Type of the return value is determined by mainObject related to the
	 * QueryInfo. ID of the return value comes from the selected row of the
	 * table. That means we can get a type-specified objectInfo whoes id is
	 * represented by another objectInfo (tyep-deffirent)
	 */
	public IObjectValue getValue(String idString, SelectorItemCollection sic)
	{
		try
		{

			SubObjectInfo mainObjectInfo = this.getQueryInfo().getMainObject();
			SubEntityInfo mainEntityInfo = null;
			BOSUuid id = null;

			if (mainObjectInfo instanceof SubEntityInfo)
			{
				mainEntityInfo = (SubEntityInfo) mainObjectInfo;
				// Get business object related to mainEntity.
				mainEntityInfo.getEntityRef();
			}

			if (mainEntityInfo != null && mainEntityInfo.getEntityRef() != null)
			{
				id = BOSUuid.read(idString);
				if (sic != null)
				{
					return DynamicObjectFactory.getRemoteInstance().getValue(mainEntityInfo.getEntityRef().getType(), new ObjectUuidPK(id), sic);
				} else
				{
					return DynamicObjectFactory.getRemoteInstance().getValue(mainEntityInfo.getEntityRef().getType(), new ObjectUuidPK(id));
				}

			} else
			{
				id = BOSUuid.read(idString);
				if (sic != null)
				{
					return DynamicObjectFactory.getRemoteInstance().getValue(id.getType(), new ObjectUuidPK(id), sic);
				} else
				{
					return DynamicObjectFactory.getRemoteInstance().getValue(id.getType(), new ObjectUuidPK(id));
				}

			}
		} catch (BOSException e)
		{

			ExceptionHandler.handle(e);
			return null;

		} finally
		{

		}
	}

	/*
	 * Algorithm: Suppose an EntityViewInfo is given when F7CommonQueryDialog is
	 * popped up and we call it as Filter_1. This Filter_1 is put into the
	 * Common_Query_Dialog when the query dialog pops up. A user can set his own
	 * filters in the common query dialog. We call this user-defined filter as
	 * Filter_2. Filter_1 and Filter_2 will be merged after the common query
	 * dialog is shut down by clicking OK button. The merged Condition will be
	 * set to the F7CommonDialog , then Refresh Table action is performed
	 * followed by setting the Filter_1,the original condition, back to the
	 * F7CommonDialog to ensure the original condition is the same as it was
	 * before the invocation of common dialog.
	 * 
	 */
	CommonQueryDialog cqd  =null;
	protected void dataFilter()
	{
		if (queryInfo == null)
			return;

		if (cqd == null) {
			cqd = new CommonQueryDialog();
			cqd.setOwner(MyKDCommonPromptDialog.this);
			cqd.setQueryObjectPK(new MetaDataPK(queryInfo.getPackage(),
					queryInfo.getName()));
			// 不支持方案保存
			cqd.setShowToolbar(false);
			cqd.setWidth(640);
			cqd.setParentUIClassName("CommonF7_" + queryInfo.getFullName());
			String s = this.getMLS("condFilter", "condFilter");// "-条件查询"

			String alias = this.getQueryInfo().getAlias();
			cqd.setTitle(this.getTitle() + s);
//			if (alias != null) {
//				cqd.setTitle(alias + s);
//			} else {
//				cqd.setTitle(s);
//			}
		}
		
//		if (innerFilter != null)
//		{
//			//因为通用过滤还不支持函数，当F7大小写不第敏感时，将快速过滤的查询条件清空。
//			//待通用过滤支持函数再恢复。zhujun_pan 20080628
//	        if(!isCapitalSensitiveQuery){
//	    		if (fValue.isVisible())
//	    		{
//	    			fValue.setText("");
//	    		} else
//	    		{
//	    			fValue2.setText("");
//	    		}
//	    		innerFilter = new EntityViewInfo();
//	        }else{
//	        	cqd.setEntityViewInfo(this.innerFilter);	
//	        }
//		}

		if (cqd.show())
		{
			// try
			// {

			// tempViewInfo1 = this.innerFilter.getFilter();
			this.innerFilter = cqd.getEntityViewInfoResult();
			// tempViewInfo = new
			// EntityViewInfo(this.getEntityViewInfo().toString());
			// tempViewInfo.getFilter().mergeFilter(this.innerFilter.getFilter(),
			// "and");

			// EntityViewInfo currentViewInfo = new
			// EntityViewInfo(getEntityViewInfo().toString());
			// this.setEntityViewInfo(tempViewInfo);
			this.rowSet = null;
			refreshData(true);

			// Set original viewInfo back to the Dialog
			// this.setEntityViewInfo(currentViewInfo);
			// }
			// catch (BOSException be)
			// {
			// ExceptionHandler.handle(this, be);
			// }
			// catch (ParserException pe)
			// {
			// ExceptionHandler.handle(this, pe);
			// }
		}
	}

	protected void btnOK_actionPerformed(ActionEvent e)
	{
		int selectRow = table.getSelectManager().getActiveRowIndex();
		if (selectRow < 0)
			return;

		canceled = false;
		this.data = buildReturnValue();
		this.dispose();
	}

	protected void btnCancel_actionPerformed(ActionEvent e)
	{
		canceled = true;
		this.data = null;
		this.dispose();
	}

	protected void benMaintainObject_actionPerformed(ActionEvent e)
	{
		try
		{
			String uiName = this.getEntityObjectListUI();

			if (uiName != null && !uiName.equals(""))
			{

				UIContext uiContext = new UIContext(this);
				uiContext.put(UIContext.ID, null);

				receiveMaintainUIContext(uiContext);

				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(uiName, uiContext);
				((JPanel) ((JDialog) uiWindow).getContentPane()).setPreferredSize(new Dimension(OBJECT_LISTUI_WIDTH, OBJECT_LISTUI_HEIGHT));
				uiWindow.show();
				this.refresh();
			} else
			{
			}

		} catch (BOSException be)
		{
			ExceptionHandler.handle(be);
		}

	}

	/**
	 * 将参数传给指定的uicontext
	 * 
	 * @param context
	 */
	private void receiveMaintainUIContext(UIContext context)
	{
		if (maintainUIContext != null)
		{
			Set keys = this.maintainUIContext.keySet();
			Iterator it = keys.iterator();
			while (it.hasNext())
			{
				Object tmpKey = it.next();
				if (!tmpKey.equals(UIContext.OWNER) && !tmpKey.equals(UIContext.ID))
				{
					Object value = maintainUIContext.get(tmpKey);
					context.put(tmpKey, value);
				}
			}
		}
	}

	protected void kdtTable_doRequestRowSet(RequestRowSetEvent e)
	{
		int start = ((Integer) e.getParam1()).intValue();
		int length = ((Integer) e.getParam2()).intValue() - start + 1;

		// 直接指定rowset
		if (this.rowSet != null)
		{
			// 只加载一次
			// if (start == 0)
			// {
			// e.setRowSet(this.rowSet);
			// }
			// else
			// {
			// e.setRowSet(null);
			// }
			// To sovle the problem
			// that the table can get only first 100 records
			// when the user inputs a string as filter into the F7 textbox and
			// commit,
			// the old implementation has been modified like the following lines
			// of code.

			e.setRowSet(this.rowSet);
			if (queryExecutor!=null ){
				int rowcount = 0;
				try {
					rowcount = queryExecutor.getRowCount();
					table.setRowCount(rowcount);
				} catch (BOSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			this.rowSet = null;

		}
		// 绑定query
		else
		{
			IRowSet rowSetTemp = null;
			// EntityViewInfo filter = getInnerFilter();
			// Modified by hhmna.

			EntityViewInfo filter = this.getEntityViewInfo();
//			if(filter == null)
//			{
//				filter = this.getEntityViewInfo();
//			}

//			logger.debug("F7 filter in getEntityViewInfo :"+(filter==null?"":filter.toString()));
			// 如果是第一次取数，先到客户端缓存要
			if ((start == 0) && (this.isUseCacheObject()))
			{
				logger.debug("F7 UseCacheObject ");
				MetaDataPK metaDataPK = new MetaDataPK(queryInfo.getPackage(), queryInfo.getName());
				EntityViewPK entityViewPK = null;
				Object obj;
				if (filter == null)
					obj = CacheService.getCacheObject(metaDataPK, this.refreshCache);
				else
				{
					entityViewPK = new EntityViewPK(filter);
					obj = CacheService.getCacheObject(metaDataPK, entityViewPK, this.refreshCache);
				}

				// 缓存中已经存在
				if (obj != null)
				{
					rowSetTemp = (IRowSet) obj;
					e.setRowSet(rowSetTemp);
					if (queryExecutor!=null ){
						int rowcount = 0;
						try {
							rowcount = queryExecutor.getRowCount();
							table.setRowCount(rowcount);
						} catch (BOSException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				// 缓存中不存在，添加到临时缓存
				else
				{
					QueryCacheObject qco = new QueryCacheObject(metaDataPK, entityViewPK);
					CacheService.addTmpCacheObject(qco);
					obj = CacheService.getCacheObject(metaDataPK, entityViewPK);
					if (obj != null)
					{
						rowSetTemp = (IRowSet) obj;
						e.setRowSet(rowSetTemp);
						if (queryExecutor!=null ){
							int rowcount = 0;
							try {
								rowcount = queryExecutor.getRowCount();
								table.setRowCount(rowcount);
							} catch (BOSException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}
			}

			// 从缓存取不到数
			if (rowSetTemp == null)
			{
				try
				{
					if (queryExecutor == null)
					{
						// 根据query获取IQueryExecutor
						MetaDataPK metaDataPK = new MetaDataPK(queryInfo.getPackage(), queryInfo.getName());
//						queryExecutor = BosHelper.createQueryExecutor(metaDataPK);
						queryExecutor = BosHelper.createQueryExecutor(metaDataPK, mainOrgContext);
					}
					// 给query对象设置entityViewInfo。

					// Make the default order condition uneffective if there is
					// one or more user defined order condition exist.
					if (filter != null && filter.getSorter() != null && filter.getSorter().size() > 0)
					{
						queryExecutor.option().isIgnoreOrder = true;
					}

					queryExecutor.setObjectView(filter);
					logger.debug("F7 filter in Common Dialog :"+filter==null?"":filter.toString());
					IRowSet rowSet = queryExecutor.executeQuery(start, length);
					e.setRowSet(rowSet);
					table.setRowCount(queryExecutor.getRowCount());
				} catch (Exception ee)
				{
					ee.printStackTrace();
				}
			}
		}
	}

	/**
	 * @return Returns the queryInfo.
	 */
	public QueryInfo getQueryInfo()
	{
		return queryInfo;
	}

	/**
	 * @param queryInfo
	 *            The queryInfo to set.
	 */
	public void setQueryInfo(QueryInfo queryInfo)
	{
		this.queryInfo = queryInfo;
		this.setRefresh(true);
	}

	// private static final String SHARE_TYPE_NAME="controlType";
	// private String controlType=null;
	// private FilterInfo filterInfoForControlTypeI;
	// private FilterInfo filterInfoForControlTypeD;

	// private String getControlType() {
	// if(controlType!=null) return controlType;
	// try {
	// EntityObjectInfo
	// eoi=(EntityObjectInfo)queryInfo.getMainObject().get("entityRef");
	// boolean hasShareType=false;
	// hasShareType=eoi.containsExtendedPropertyKey(SHARE_TYPE_NAME);
	// if(hasShareType) {
	// controlType=eoi.getExtendedProperty(SHARE_TYPE_NAME);
	// } else {
	// if(eoi.getBaseEntity().getName().equals("DataBaseD")) {
	// controlType="D";
	// } else {
	// controlType="";
	// }
	// }
	// } catch(Exception e) {
	// ExceptionHandler.handle(e);
	// }
	// return controlType;
	// }

	// private FilterInfo getFilterInfoForControlTypeI() {
	// if(filterInfoForControlTypeI!=null) return filterInfoForControlTypeI;
	// filterInfoForControlTypeI=new FilterInfo();
	// FilterItemCollection fic=filterInfoForControlTypeI.getFilterItems();
	// String
	// currentCUID=SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
	// fic.add(new FilterItemInfo("CU.id",currentCUID,CompareType.EQUALS));
	// return filterInfoForControlTypeI;
	// }
	//    
	// private FilterInfo getFilterInfoForControlTypeD() {
	// if(filterInfoForControlTypeD==null) {
	// try {
	// String
	// bosType=((EntityObjectInfo)queryInfo.getMainObject().get("entityRef")).getType().toString();
	// String
	// currentCUID=SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
	// filterInfoForControlTypeD=
	// DataBaseDFilterGetterFacadeFactory.getRemoteInstance().getFilterInfoOfDataBaseD(currentCUID,bosType);
	// } catch(Exception e) {
	// ExceptionHandler.handle(e);
	// }
	// }
	// return filterInfoForControlTypeD;
	// }

	/**
	 * @return Returns the queryExecutor.
	 */
	public IQueryExecutor getQueryExecutor()
	{
		return queryExecutor;
	}

	/**
	 * @param queryExecutor
	 *            The queryExecutor to set.
	 */
	public void setQueryExecutor(IQueryExecutor queryExecutor)
	{
		this.queryExecutor = queryExecutor;
		this.setRefresh(true);
	}

	/**
	 * @return Returns the selectorCollection.
	 */
	public SelectorItemCollection getSelectorCollection()
	{
		return selectorCollection;
	}

	/**
	 * @param selectorCollection
	 *            The selectorCollection to set.
	 */
	public void setSelectorCollection(SelectorItemCollection selectorCollection)
	{
		this.selectorCollection = selectorCollection;
	}

	/**
	 * @return Returns the returnValueType.
	 */
	public int getReturnValueType()
	{
		return returnValueType;
	}

	/**
	 * @param returnValueType
	 *            The returnValueType to set.
	 */
	public void setReturnValueType(int returnValueType)
	{
		this.returnValueType = returnValueType;
	}

	// 从资源文件取得字串，以便支持多语言
	private String getMLS(String key, String defaultValue)
	{
		Locale local = Locale.getDefault();
		//如果Default Locale被改成l2后，将短编码转成长编码（l2转成zh_CN）
		if(local.toString().toLowerCase().indexOf("l") >= 0){
			local = shortToLong(local);
		}
		return LanguageManager.getLangMessage(key, "com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog", local,defaultValue);
	}
	
	private Locale shortToLong(Locale local) {
		if("l2".equals(local.toString().toLowerCase())){
			return Locale.SIMPLIFIED_CHINESE;
		}else if("l3".equals(local.toString().toLowerCase())){
			return Locale.TRADITIONAL_CHINESE;
		}else if("l1".equals(local.toString().toLowerCase())){
			return Locale.ENGLISH;
		}else{
			logger.info("KDCommonPromptDialog.shortToLong ,illegal Locale!");
			return local;
		}
	}

	// 从资源文件取得icon，以便支持多语言
	private Icon getMLIcon(String key)
	{
		// edit by fuxiuhu，获取图片资源时性能调整
		Image bi = ResourceManager.getBufferedImage(key);
		if (bi != null)
		{
			return new ImageIcon(bi);
		}

		return null;
	}

	public static boolean stringToboolean(String val)
	{
		if (val.equalsIgnoreCase("false") || val.equalsIgnoreCase("0"))
		{
			return false;
		}
		return true;
	}

	public static int stringToint(String val)
	{
		return Integer.parseInt(val);
	}

	private String getEntityObjectListUI()
	{
		String uiName = "";
		// Attempt to get the name of listUI from QueryInfo itself first,
		// if fail to get it. Try to do the same thing from Entity.
		// 
		if (this.queryInfo == null)
		{
			return uiName;
		}
		uiName = this.queryInfo.getExtendedProperty("listUI");
		if (uiName != null && !uiName.equals(""))
		{
			return uiName;
		}

		try
		{

			SubObjectInfo info = null;
			if (queryInfo instanceof JoinQueryInfo)
			{
				info = ((JoinQueryInfo) queryInfo).getMainObject();
			}

			if (info instanceof SubEntityInfo)
			{
				uiName = ((SubEntityInfo) info).getEntityRef().getExtendedProperty("listUI");
			}
		} catch (BOSException e)
		{
			// Don't need to do anything.
		}

		return uiName;

	}

	private boolean isF7Maintain()
	{
		boolean returnValue = false;
		String isF7Maintain = "";

		if (this.queryInfo != null)
		{
			isF7Maintain = this.queryInfo.getExtendedProperty("isF7Maintain");
		}

		if (isF7Maintain != null && !isF7Maintain.equals(""))
		{
			if (isF7Maintain.trim().equalsIgnoreCase("true"))
			{
				returnValue = true;
			}

		}
		return returnValue;
	}

	/**
	 * 获取 “是否通过CU过滤”
	 */
	public boolean getIsCUEffective()
	{
		return this.hasCUDefaultFilter;
	}

	/**
	 * 设置 “是否通过CU过滤”
	 */
	public void setHasCUDefaultFilter(boolean isCUEffective)
	{
		this.hasCUDefaultFilter = isCUEffective;
	}

	protected void preparePrintPage(KDTable tblMain)
	{
		HeadFootModel header = new HeadFootModel();

		// altered by fuxiuhu, 直接获取默认sa即可
		// StyleAttributes sa = Styles.getSA( Styles.getDefaultSSA() );
		StyleAttributes sa = Styles.getDefaultSA();
		sa.setFontSize(14);
		sa.setBold(true);
		header.addRow(getPrintPageTitle(), sa);

		HeadFootModel footer = new HeadFootModel();
		String dateString = EASResource.getString(FrameWorkClientUtils.strResource + printDateMsg) + "&[Date]";

		// com.kingdee.eas.base.UserInfo =
		// SysContext.getSysContext().getCurrentUserInfo();
		String personString = EASResource.getString(FrameWorkClientUtils.strResource + printPersonMsg)
				+ SysContext.getSysContext().getCurrentUserInfo();

		String pageNumber = EASResource.getString(FrameWorkClientUtils.strResource + printPageCountMsg) + "&[Page]/&[PageCount]";

		String footerString = dateString + "&|" + personString + "&|" + pageNumber;

		// altered by fuxiuhu, 直接获取默认sa即可
		// StyleAttributes sb = Styles.getSA( Styles.getDefaultSSA() );
		StyleAttributes sb = Styles.getDefaultSA();
		footer.addRow(footerString, sb);

		preparePrintPageHeader(header);

		preparePrintPageFooter(footer);

		Page hPage = HeadFootParser.parseModel2HeadFootPage(header);
		Page fPage = HeadFootParser.parseModel2HeadFootPage(footer);
		ExtVarProvider extVarProvider = getPrintExtVarProvider();

		if (extVarProvider != null)
			tblMain.getPrintManager().setHeaderFooter(hPage, fPage, extVarProvider);
		else
			tblMain.getPrintManager().setHeaderFooter(hPage, fPage);
	}

	protected void preparePrintPageHeader(HeadFootModel headerModel)
	{

	}

	protected void preparePrintPageFooter(HeadFootModel footerModel)
	{

	}

	protected ExtVarProvider getPrintExtVarProvider()
	{
		return null;
	}

	protected String getPrintPageTitle()
	{
		return this.getTitle();
	}

	private KDPanel getPanelForQuickFilterComponents()
	{

		fFields = new KDComboBox();
		fValue = new KDTextField();
		fValue2 = new KDTextField();
		CtrlPlainDocument cpD = new CtrlPlainDocument();
		cpD.setCtrTextDocumentFilter(new NumberTextDocumentFilter(fValue2));
		fValue2.setDocument(cpD);
		fLaunch = new KDWorkButton();
		fChk = new KDCheckBox();
		quickFilterPanel = new KDPanel();

		fFields.setBounds(new Rectangle(10, 10, 100, 19));
		fFields.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				//
//				fValue.setText("");
//				fValue2.setText("");
				FilterFieldItem ffi = (FilterFieldItem) e.getItem();
				if (ffi != null)
				{
					if (ffi.getType().equals(ConstDataType.INT) || ffi.getType().equals(ConstDataType.NUMBER))
					{
						fChk.setEnabled(false);
						fChk.setSelected(false);
						fValue2.setVisible(true);
						fValue.setVisible(false);
//						fValue2.setDataType(KDFormattedTextField.INTEGER_TYPE);
//						if (ffi.getType().equals(ConstDataType.NUMBER))
//						{
//							fValue2.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
//						}
						fValue2.requestFocus();
						fValue2.selectAll();
					} else
					{
						fChk.setEnabled(true);
						fChk.setSelected(true);
						fValue.setVisible(true);
						fValue.requestFocus();
						fValue.selectAll();
				
						fValue2.setVisible(false);
					}
				}
			}
		});

		fValue.setBounds(new Rectangle(115, 10, 150, 19));
		fValue.setText("");
		CtrlSwingUtilities.removeManagingFocusForwardTraversalKeys(fValue, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		fValue.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				launchQuickFilter();
			}
		});
		fValue.setVisible(true);

		fValue2.setBounds(new Rectangle(115, 10, 150, 19));
		fValue2.setText("");
		CtrlSwingUtilities.removeManagingFocusForwardTraversalKeys(fValue2, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
		fValue2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				launchQuickFilter();
			}
		});
		fValue2.setVisible(false);
//		fValue2.setSupportedEmpty(true);
//		fValue2.setDataType(KDFormattedTextField.INTEGER_TYPE);
//		fValue2.setGroupingUsed(false);

		fLaunch.setBounds(new Rectangle(270, 10, 80, 19));
		fLaunch.setText(getMLS("LaunchQuickFilter", "QuickFilter"));
		fLaunch.setIcon(EASResource.getIcon("imgTbtn_find"));
		fLaunch.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				launchQuickFilter();
			}
		});

		fChk.setSelected(true);
		fChk.setText(getMLS("mquery", "LikeQuery"));
		fChk.setBounds(new Rectangle(355, 10, 100, 19));

		quickFilterPanel.setLayout(null);
		quickFilterPanel.add(fFields, null);
		quickFilterPanel.add(fValue, null);
		quickFilterPanel.add(fValue2, null);
		quickFilterPanel.add(fLaunch, null);
		quickFilterPanel.add(fChk);
		quickFilterPanel.setBackground(new Color(0xE4E4DF));
		quickFilterPanel.setPreferredSize(new Dimension(400, 29));

		return quickFilterPanel;

	}

	private void launchQuickFilter()
	{
		EntityViewInfo evi = getQuickFilterEntityViewInfo();
		innerFilter = evi;
		rowSet = null;
		refreshData(true);
		table.requestFocusInWindow();
	}

	private void fillFilterFields()
	{
//		if (fFields.getItemCount() == 0)
//		{
//			fFields.addItems(getFilterFields());
//		}
		fFields.removeAllItems();
		fFields.addItems(getFilterFields());
	}

	private EntityViewInfo getQuickFilterEntityViewInfo()
	{
		String theValue = "";
		if (fValue.isVisible())
		{
			theValue = fValue.getText().trim();
		} else
		{
			theValue = fValue2.getText().trim();
		}
		if (theValue.length() == 0)
			return null;
		if (fFields.getSelectedItem() == null || fFields.getItemCount() == 0)
			return null;

		String theCompareString = "=";

		if (fChk.isSelected())
		{
			theCompareString = "like";
		}

		FilterFieldItem selffi = (FilterFieldItem) fFields.getSelectedItem();
		String theFieldName = selffi.getName();
		ConstDataType theFieldType = selffi.getType();
		
		StringBuffer oql = new StringBuffer("where ");
		if(numberAndName.equals(theFieldName))
		{
			oql.append(getQuickFilterOql("number",theFieldType,theCompareString,theValue));
			oql.append(" or ");
			oql.append(getQuickFilterOql("name",theFieldType,theCompareString,theValue));
		}else if(allFilterFieldName!=null && theFieldName.equals(allFilterFieldName)){
			String[] all = allFilterFieldName.split("[+]");
			if (all.length>0){
			oql.append(getQuickFilterOql(all[0],theFieldType,theCompareString,theValue));
			for(int i=1; i<all.length; i++){
				oql.append(" or ");
				oql.append(getQuickFilterOql(all[i],theFieldType,theCompareString,theValue));
				
			}
			}
			
		}
		else 
		{
			oql.append(getQuickFilterOql(theFieldName,theFieldType,theCompareString,theValue));
		}
	 
	 
		EntityViewInfo evi = null;
		try
		{
			evi = new EntityViewInfo(oql.toString());
		} catch (Exception e)
		{
			evi = null;
		}
		return evi;
	}
	
	private String getQuickFilterOql(String fieldName,ConstDataType theFieldType ,String compareStr,String value)
	{
		String theValue = value;
		if (fChk.isSelected())
		{
			theValue = ParamHelper.getMatchString(fieldName,theValue,this.queryInfo.getFullName());
		}
		if (theFieldType.equals(ConstDataType.STRING))
		{
			theValue = "'" + theValue + "'";
		}
		
		//大小写模糊查询 zhujun_pan 20080506
		if(isCapitalSensitiveQuery){
			return fieldName + " " + compareStr + " " + theValue;
		}else{
			return "upper(" + fieldName + ")" + " " + compareStr + " " + theValue.toUpperCase();	
		}
		
	}

	private FilterFieldItem[] getFilterFields()
	{
		SelectorInfo si = queryInfo.getSelector();

		ArrayList al = new ArrayList();
		int sizeOfSI = si.size();
		for (int i = 0; i < sizeOfSI; i++)
		{
			QueryFieldInfo qfi = si.get(i);
			String theExtendPropertyValue = qfi.getExtendedProperty("isCommonQueryFilter");
			if (theExtendPropertyValue != null && theExtendPropertyValue.equals("true"))
			{
				ConstDataType cdt = null;
				try
				{
					cdt = qfi.getReturnType();
				} catch (Exception e)
				{
					cdt = ConstDataType.STRING;
				}
				if (!cdt.equals(ConstDataType.DATE) &&!cdt.equals(ConstDataType.BOOLEAN) && !cdt.equals(ConstDataType.TIME) && !cdt.equals(ConstDataType.TIMESTAMP) && !isEnumField(qfi))
				{
					al.add(new FilterFieldItem(qfi.getName(), qfi.getDisplayName(), cdt));
				}
			}
		}

		int sizeOfAL = al.size();
		FilterFieldItem[] ffis = new FilterFieldItem[sizeOfAL];
		for (int i = 0; i < sizeOfAL; i++)
		{
			ffis[i] = (FilterFieldItem) al.get(i);
		}

		if (ffis.length == 0 && isDefaultFilterFieldsEnabled)
		{
			ffis = new FilterFieldItem[2];
			ffis[0] = new FilterFieldItem("number", getMLS("number", "Number"), ConstDataType.STRING);
			ffis[1] = new FilterFieldItem("name", getMLS("name", "Name"), ConstDataType.STRING);
		}
		ffis = addNumberNameCompFilter(ffis);
		//deal 过滤所有字段的选项
		 allFilterFieldName = queryInfo.getExtendedProperty("allFilterFieldName");
//		 allFilterFieldDesc = queryInfo.getExtendedProperty("allFilterFieldDesc");
		//allFilterFieldName="name+number+mnemonicCode";
		//allFilterFieldDesc="编码+名称+助记码";

		if (allFilterFieldName != null ) {
			String allFilterFieldDesc = getMLS("numbernamehelpcode", "number+name+helpcode");
				
				
			FilterFieldItem ffisall = new FilterFieldItem(allFilterFieldName,
					allFilterFieldDesc,
					ConstDataType.STRING);
			FilterFieldItem[] rtFf = new FilterFieldItem[ffis.length + 1];
			rtFf[0] = ffisall;
			System.arraycopy(ffis, 0, rtFf, 1, ffis.length);
			ffis = rtFf;

		}

		return ffis;
	}
	private String allFilterFieldName = null;
//	private String commitFormate;
	
//	private String allFilterFieldDesc  =null;
	private static final String numberAndName = "number+name";
	private FilterFieldItem[] addNumberNameCompFilter(FilterFieldItem[] ff)
	{
		if(ff != null)
		{
			int count = 0;
			for(int i=0;i<ff.length;i++)
			{
				if(ff[i] != null && "number".equals(ff[i].getName()))
				{
					count++;
				}else if(ff[i] != null && "name".equals(ff[i].getName()))
				{
					count++;
				}
			}
			if(count==2)
			{

				FilterFieldItem ffis = new FilterFieldItem(numberAndName, getMLS("number", "Number")+"+"+getMLS("name", "Name"), ConstDataType.STRING);
				FilterFieldItem[] rtFf = new FilterFieldItem[ff.length+1];
				rtFf[0] = ffis;
				System.arraycopy(ff,0,rtFf,1,ff.length);
				ff = rtFf;
			}
		}
		
		return ff;
	}
	
	private boolean isEnumField(QueryFieldInfo qfi)
	{
		boolean isEnum = false;
		PropertyRefCollection pfc = qfi.getPropertyRefs();
		PropertyRefInfo pri = pfc.get(0);
		PropertyInfo pi = pri.getRefProperty();
		if (pi instanceof OwnPropertyInfo)
		{
			OwnPropertyInfo opi = (OwnPropertyInfo) pi;
			if (opi.getDataType().equals(DataType.ENUM))
			{
				isEnum = true;
			}
		}
		return isEnum;
	}

	private class FilterFieldItem
	{
		private String ffiName;
		private String ffiDisplayName;
		private ConstDataType fficdt;
		FilterFieldItem(String name, String displayName, ConstDataType cdt)
		{
			ffiName = name;
			ffiDisplayName = displayName;
			fficdt = cdt;
		}
		public String toString()
		{
			return ffiDisplayName;
		}
		public String getName()
		{
			return ffiName;
		}
		public ConstDataType getType()
		{
			return fficdt;
		}
	}

	private KDComboBox fFields;
	// private KDComboBox fCompareTypes;
	private KDTextField fValue;
	private KDTextField fValue2;
	private KDWorkButton fLaunch;
	private KDCheckBox fChk;
	private KDPanel quickFilterPanel;
	private KDPanel oldContentPanel;
	private KDPanel allComponentsPanel;

	public void setEnableToMaintainBizdata(boolean enabled)
	{
		btnMaintainObject.setEnabled(enabled);
	}

	public boolean isDefaultFilterFieldsEnabled = true;

	public void setIsDefaultFilterFieldsEnabled(boolean enabled)
	{
		isDefaultFilterFieldsEnabled = enabled;
	}

	private CommonF7KDTableListener cmF7TblListener;

	public void addCommonF7KDTableListener(CommonF7KDTableListener listener)
	{
		cmF7TblListener = listener;
	}

	private void callCommonF7KDTableListener(KDTable table)
	{
		if (cmF7TblListener != null)
		{
			cmF7TblListener.onTableStructureCreated(table);
		}
	}
	
	/**
	 * 上一次设置的F7值
	 */
	private Object lastData = null;
	public void setLastData(Object data)
	{
		this.lastData = data;
	}
	public Object getLastData()
	{
		return this.lastData;
	}
	
	private String[] precisionColumns = null;
	public String[] getPrecisionColumns() {
		return precisionColumns;
	}
	public void setPrecisionColumns(String[] precisionColumns) {
		this.precisionColumns = precisionColumns;
	}
	
	private String[] precisionDisplayColumns = null;
	public String[] getPrecisionDisplayColumns() {
		return precisionDisplayColumns;
	}
	public void setPrecisionDisplayColumns(String[] precisionDisplayColumns) {
		this.precisionDisplayColumns = precisionDisplayColumns;
	}	
	
	private String[] mergeColumnKeys = null;
	public String[] getMergeColumnKeys() {
		return mergeColumnKeys;
	}

	/**
	 * 设置需要进行表格融合的列集合
	 * @param mergeColumKeys
	 */
	public void setMergeColumnKeys(String[] mergeColumnKeys) {
		this.mergeColumnKeys = mergeColumnKeys;
	}

    /**
     * 增加对于单据头的表格融合设置
     */
    private void setMergeColumn()
    {
        //增加对于单据头的表格融合设置
        String mergeColumnKeys[] = getMergeColumnKeys();
        if (mergeColumnKeys != null && mergeColumnKeys.length > 0)
        {
            table.checkParsed();
            table.getGroupManager().setGroup(true);
            for (int i = 0; i < mergeColumnKeys.length; i++)
            {
            	table.getColumn(mergeColumnKeys[i]).setGroup(true);
            	table.getColumn(mergeColumnKeys[i]).setMergeable(true);
            }
        }
    }
    
    private class NumberTextDocumentFilter implements ICtrTextDocumentFilter
    {
    	private String validStr = ".";
    	private KDTextField field = null;
    	public NumberTextDocumentFilter(KDTextField txtField)
    	{
    		field = txtField;
    	}
		public boolean isValidateStr(String str) {
			for(int i=0,length = str.length();i<length;i++)
			{
				char c = str.charAt(i);
				if(!Character.isDigit(c) && validStr.indexOf(str)<0)
				{
					return false;
				}
			}
			String oldStr = field.getText();
			String newStr = oldStr+str;
			try
			{
				new BigDecimal(newStr);
			}catch(NumberFormatException e)
			{
				return false;
			}
			return true;
		}
		
		private String value = null;
		public void setValidateStr(String newValue) {
			value = newValue;			
		}

		public String getValidateStr() {
			return value;
		}

		public boolean isValidLength(Document doc) {
			return true;
		}

		public void setValidLength(int len) {
			// TODO 自动生成方法存根
			
		}
    	
    }

	public void setNullValue() {
 
		fValue.setText("");
		fValue2.setText("");
		
	}
	/**
	 * 主业务组织没有设置时处理的是当前组织的字段权限
	 */
	// [begin]字段权限处理   基础资料应该都是单组织的
    protected void doFieldPermission()
    {
        IObjectPK userPK = null;
        IObjectPK orgPK = null;
        IMetaDataPK mainQueryPK = null;
        try
        {
        	userPK = getUserPk();
        	mainQueryPK = new MetaDataPK(this.getQueryInfo().getFullName());  	
        	Class fieldPermissionServiceClass = Class.forName("com.kingdee.eas.framework.client.service.FieldPermissionService");
        	Class[] argsClass = null;
        	Object[] args = null;
        	Method method = null;
        	
        	orgPK = getOrgPK();
        	argsClass = new Class[]{IMetaDataPK.class,IObjectPK.class,IObjectPK.class,KDTable.class};
        	args = new Object[]{mainQueryPK,userPK,orgPK,table};
        	method = fieldPermissionServiceClass.getMethod("doFieldPermission", argsClass);
        	method.invoke(null, args);//处理单组织字段权限
        	
        	OrgUnitCollection orgCol = this.getMainBizOrgs();
//        	if(orgCol == null){
//        		//主业务组织没有设置时，取当前组织
//        		OrgUnitInfo orgUnitInfo= SysContext.getSysContext().getCurrentOrgUnit();
//        		orgCol = new OrgUnitCollection();
//        		orgCol.add(orgUnitInfo);
//        	}
        	if(orgCol != null){
        		if(orgCol.size() >= 1){
        			List orgIdList = getIdList(orgCol);
        			argsClass = new Class[]{IMetaDataPK.class,IObjectPK.class,List.class,KDTable.class};
        			args = new Object[]{mainQueryPK,userPK,orgIdList,table};
        			method = fieldPermissionServiceClass.getMethod("doMutilOrgPermission", argsClass);
                	method.invoke(null, args);//处理多组织字段权限
        		}
        	}      	
        }
        catch (Exception e)
        {
        	logger.info("MyKDCommonPromptDialog doF7FieldPermission err!",e);
        }
    }
    
    private List getIdList(OrgUnitCollection orgCol){
    	List list=new ArrayList();
    	for(int i = 0;i<orgCol.size();i++){
    		list.add(((OrgUnitInfo)orgCol.get(i)).getId().toString());
    	}
    	return list;
    }
    
    protected IObjectPK getUserPk() {
		IObjectPK userPK = new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId());
		return userPK;
	}
    
    /**
	 * 获取组织PK，默认实现返回null。
	 *
	 *如果设置过当前主业务组织单元，取主业务组织的id，否则取当前组织的id
	 * @return IObjectPK
	 */
	protected IObjectPK getOrgPK() {
        IObjectPK orgPK= null;
        OrgUnitInfo orgUnitInfo = this.getCurrentMainBizOrgUnit();
        if(orgUnitInfo != null){
        	orgPK = new ObjectUuidPK(orgUnitInfo.getId());
        }
        if(orgPK == null){
        	orgPK = new ObjectUuidPK(SysContext.getSysContext().getCurrentOrgUnit().getId());
        }
        return orgPK;
	}
}