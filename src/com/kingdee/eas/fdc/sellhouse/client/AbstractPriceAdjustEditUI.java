/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractPriceAdjustEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPriceAdjustEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRemoveRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTotalCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewTotalPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewBuildingAvgPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewRoomAvgPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldTotalPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldBuildingAvgPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOldRoomAvgPrice;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsAutoToInteger;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToIntegerType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDigit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoomSelect;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddRoomByList;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Creator;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalBuildingArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalRoomArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtTotalCount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNewTotalPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNewBuildingAvgPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtNewRoomAvgPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOldTotalPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOldBuildingAvgPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtOldRoomAvgPrice;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboToIntegerType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboDigit;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField totalAmountBalance;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField totalAmountBalanceScale;
    protected com.kingdee.eas.fdc.sellhouse.PriceAdjustInfo editData = null;
    protected ActionAddRoomByList actionAddRoomByList = null;
    /**
     * output class constructor
     */
    public AbstractPriceAdjustEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractPriceAdjustEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionAddRoomByList
        this.actionAddRoomByList = new ActionAddRoomByList(this);
        getActionManager().registerAction("actionAddRoomByList", actionAddRoomByList);
         this.actionAddRoomByList.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.tblRoom = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnAddRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRemoveRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnImportPrice = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contTotalBuildingArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalRoomArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTotalCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNewTotalPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNewBuildingAvgPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNewRoomAvgPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOldTotalPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOldBuildingAvgPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOldRoomAvgPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsAutoToInteger = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contToIntegerType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDigit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtRoomSelect = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnAddRoomByList = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.f7Creator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtTotalBuildingArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTotalRoomArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtTotalCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtNewTotalPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtNewBuildingAvgPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtNewRoomAvgPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOldTotalPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOldBuildingAvgPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOldRoomAvgPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboToIntegerType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboDigit = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.totalAmountBalance = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.totalAmountBalanceScale = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.contCreator.setName("contCreator");
        this.contName.setName("contName");
        this.tblRoom.setName("tblRoom");
        this.contCreateTime.setName("contCreateTime");
        this.btnAddRoom.setName("btnAddRoom");
        this.btnRemoveRoom.setName("btnRemoveRoom");
        this.btnImportPrice.setName("btnImportPrice");
        this.contTotalBuildingArea.setName("contTotalBuildingArea");
        this.contTotalRoomArea.setName("contTotalRoomArea");
        this.contTotalCount.setName("contTotalCount");
        this.contNewTotalPrice.setName("contNewTotalPrice");
        this.contNewBuildingAvgPrice.setName("contNewBuildingAvgPrice");
        this.contNewRoomAvgPrice.setName("contNewRoomAvgPrice");
        this.contOldTotalPrice.setName("contOldTotalPrice");
        this.contOldBuildingAvgPrice.setName("contOldBuildingAvgPrice");
        this.contOldRoomAvgPrice.setName("contOldRoomAvgPrice");
        this.chkIsAutoToInteger.setName("chkIsAutoToInteger");
        this.contToIntegerType.setName("contToIntegerType");
        this.contDigit.setName("contDigit");
        this.prmtRoomSelect.setName("prmtRoomSelect");
        this.btnAddRoomByList.setName("btnAddRoomByList");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.txtNumber.setName("txtNumber");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDescription.setName("txtDescription");
        this.f7Creator.setName("f7Creator");
        this.txtName.setName("txtName");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtTotalBuildingArea.setName("txtTotalBuildingArea");
        this.txtTotalRoomArea.setName("txtTotalRoomArea");
        this.txtTotalCount.setName("txtTotalCount");
        this.txtNewTotalPrice.setName("txtNewTotalPrice");
        this.txtNewBuildingAvgPrice.setName("txtNewBuildingAvgPrice");
        this.txtNewRoomAvgPrice.setName("txtNewRoomAvgPrice");
        this.txtOldTotalPrice.setName("txtOldTotalPrice");
        this.txtOldBuildingAvgPrice.setName("txtOldBuildingAvgPrice");
        this.txtOldRoomAvgPrice.setName("txtOldRoomAvgPrice");
        this.comboToIntegerType.setName("comboToIntegerType");
        this.comboDigit.setName("comboDigit");
        this.totalAmountBalance.setName("totalAmountBalance");
        this.totalAmountBalanceScale.setName("totalAmountBalanceScale");
        // CoreUI		
        this.setPreferredSize(new Dimension(750,629));		
        this.menuSubmitOption.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // tblRoom
        this.tblRoom.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblRoom_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblRoom.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblRoom_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelUnderline(true);		
        this.contCreateTime.setBoundLabelLength(100);
        // btnAddRoom		
        this.btnAddRoom.setText(resHelper.getString("btnAddRoom.text"));
        this.btnAddRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnRemoveRoom		
        this.btnRemoveRoom.setText(resHelper.getString("btnRemoveRoom.text"));
        this.btnRemoveRoom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRemoveRoom_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnImportPrice		
        this.btnImportPrice.setText(resHelper.getString("btnImportPrice.text"));
        this.btnImportPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnImportPrice_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contTotalBuildingArea		
        this.contTotalBuildingArea.setBoundLabelText(resHelper.getString("contTotalBuildingArea.boundLabelText"));		
        this.contTotalBuildingArea.setBoundLabelUnderline(true);		
        this.contTotalBuildingArea.setBoundLabelLength(100);
        // contTotalRoomArea		
        this.contTotalRoomArea.setBoundLabelText(resHelper.getString("contTotalRoomArea.boundLabelText"));		
        this.contTotalRoomArea.setBoundLabelLength(100);		
        this.contTotalRoomArea.setBoundLabelUnderline(true);
        // contTotalCount		
        this.contTotalCount.setBoundLabelText(resHelper.getString("contTotalCount.boundLabelText"));		
        this.contTotalCount.setBoundLabelLength(100);		
        this.contTotalCount.setBoundLabelUnderline(true);
        // contNewTotalPrice		
        this.contNewTotalPrice.setBoundLabelText(resHelper.getString("contNewTotalPrice.boundLabelText"));		
        this.contNewTotalPrice.setBoundLabelLength(100);		
        this.contNewTotalPrice.setBoundLabelUnderline(true);
        // contNewBuildingAvgPrice		
        this.contNewBuildingAvgPrice.setBoundLabelText(resHelper.getString("contNewBuildingAvgPrice.boundLabelText"));		
        this.contNewBuildingAvgPrice.setBoundLabelLength(100);		
        this.contNewBuildingAvgPrice.setBoundLabelUnderline(true);
        // contNewRoomAvgPrice		
        this.contNewRoomAvgPrice.setBoundLabelText(resHelper.getString("contNewRoomAvgPrice.boundLabelText"));		
        this.contNewRoomAvgPrice.setBoundLabelLength(100);		
        this.contNewRoomAvgPrice.setBoundLabelUnderline(true);
        // contOldTotalPrice		
        this.contOldTotalPrice.setBoundLabelText(resHelper.getString("contOldTotalPrice.boundLabelText"));		
        this.contOldTotalPrice.setBoundLabelLength(100);		
        this.contOldTotalPrice.setBoundLabelUnderline(true);
        // contOldBuildingAvgPrice		
        this.contOldBuildingAvgPrice.setBoundLabelText(resHelper.getString("contOldBuildingAvgPrice.boundLabelText"));		
        this.contOldBuildingAvgPrice.setBoundLabelLength(100);		
        this.contOldBuildingAvgPrice.setBoundLabelUnderline(true);
        // contOldRoomAvgPrice		
        this.contOldRoomAvgPrice.setBoundLabelText(resHelper.getString("contOldRoomAvgPrice.boundLabelText"));		
        this.contOldRoomAvgPrice.setBoundLabelLength(100);		
        this.contOldRoomAvgPrice.setBoundLabelUnderline(true);
        // chkIsAutoToInteger		
        this.chkIsAutoToInteger.setText(resHelper.getString("chkIsAutoToInteger.text"));
        this.chkIsAutoToInteger.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsAutoToInteger_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contToIntegerType		
        this.contToIntegerType.setBoundLabelText(resHelper.getString("contToIntegerType.boundLabelText"));		
        this.contToIntegerType.setBoundLabelUnderline(true);		
        this.contToIntegerType.setBoundLabelLength(100);
        // contDigit		
        this.contDigit.setBoundLabelText(resHelper.getString("contDigit.boundLabelText"));		
        this.contDigit.setBoundLabelLength(100);		
        this.contDigit.setBoundLabelUnderline(true);
        // prmtRoomSelect		
        this.prmtRoomSelect.setVisible(false);
        // btnAddRoomByList
        this.btnAddRoomByList.setAction((IItemAction)ActionProxyFactory.getProxy(actionAddRoomByList, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAddRoomByList.setText(resHelper.getString("btnAddRoomByList.text"));
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setForeground(new java.awt.Color(0,0,255));		
        this.kDLabelContainer1.setFont(new java.awt.Font("Dialog",1,9));
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(135);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // kDScrollPane1
        // txtDescription		
        this.txtDescription.setMaxLength(300);
        // f7Creator		
        this.f7Creator.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7Creator.setEnabled(false);		
        this.f7Creator.setCommitFormat("$number$");		
        this.f7Creator.setEditFormat("$number$");		
        this.f7Creator.setDisplayFormat("$name$");
        // txtName		
        this.txtName.setRequired(true);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // txtTotalBuildingArea		
        this.txtTotalBuildingArea.setEnabled(false);		
        this.txtTotalBuildingArea.setDataType(1);
        // txtTotalRoomArea		
        this.txtTotalRoomArea.setDataType(1);
        // txtTotalCount
        // txtNewTotalPrice		
        this.txtNewTotalPrice.setDataType(1);		
        this.txtNewTotalPrice.setEnabled(false);
        // txtNewBuildingAvgPrice		
        this.txtNewBuildingAvgPrice.setDataType(1);
        // txtNewRoomAvgPrice		
        this.txtNewRoomAvgPrice.setDataType(1);
        // txtOldTotalPrice		
        this.txtOldTotalPrice.setDataType(1);		
        this.txtOldTotalPrice.setEnabled(false);
        // txtOldBuildingAvgPrice		
        this.txtOldBuildingAvgPrice.setDataType(1);
        // txtOldRoomAvgPrice		
        this.txtOldRoomAvgPrice.setDataType(1);
        // comboToIntegerType		
        this.comboToIntegerType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum").toArray());
        this.comboToIntegerType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboToIntegerType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboDigit		
        this.comboDigit.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.DigitEnum").toArray());
        this.comboDigit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboDigit_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // totalAmountBalance
        // totalAmountBalanceScale		
        this.totalAmountBalanceScale.setPrecision(4);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 750, 750));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 750, 750));
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 10, 270, 19, 0));
        contDescription.setBounds(new Rectangle(10, 223, 673, 53));
        this.add(contDescription, new KDLayout.Constraints(10, 223, 673, 53, 0));
        contCreator.setBounds(new Rectangle(10, 35, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(10, 35, 270, 19, 0));
        contName.setBounds(new Rectangle(413, 10, 270, 19));
        this.add(contName, new KDLayout.Constraints(413, 10, 270, 19, 0));
        tblRoom.setBounds(new Rectangle(12, 406, 679, 286));
        this.add(tblRoom, new KDLayout.Constraints(12, 406, 679, 286, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreateTime.setBounds(new Rectangle(413, 35, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(413, 35, 270, 19, 0));
        btnAddRoom.setBounds(new Rectangle(319, 374, 133, 19));
        this.add(btnAddRoom, new KDLayout.Constraints(319, 374, 133, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        btnRemoveRoom.setBounds(new Rectangle(604, 374, 89, 19));
        this.add(btnRemoveRoom, new KDLayout.Constraints(604, 374, 89, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        btnImportPrice.setBounds(new Rectangle(212, 374, 89, 19));
        this.add(btnImportPrice, new KDLayout.Constraints(212, 374, 89, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        contTotalBuildingArea.setBounds(new Rectangle(10, 85, 270, 19));
        this.add(contTotalBuildingArea, new KDLayout.Constraints(10, 85, 270, 19, 0));
        contTotalRoomArea.setBounds(new Rectangle(413, 60, 270, 19));
        this.add(contTotalRoomArea, new KDLayout.Constraints(413, 60, 270, 19, 0));
        contTotalCount.setBounds(new Rectangle(10, 60, 270, 19));
        this.add(contTotalCount, new KDLayout.Constraints(10, 60, 270, 19, 0));
        contNewTotalPrice.setBounds(new Rectangle(412, 110, 270, 19));
        this.add(contNewTotalPrice, new KDLayout.Constraints(412, 110, 270, 19, 0));
        contNewBuildingAvgPrice.setBounds(new Rectangle(412, 161, 270, 19));
        this.add(contNewBuildingAvgPrice, new KDLayout.Constraints(412, 161, 270, 19, 0));
        contNewRoomAvgPrice.setBounds(new Rectangle(412, 190, 270, 19));
        this.add(contNewRoomAvgPrice, new KDLayout.Constraints(412, 190, 270, 19, 0));
        contOldTotalPrice.setBounds(new Rectangle(10, 110, 270, 19));
        this.add(contOldTotalPrice, new KDLayout.Constraints(10, 110, 270, 19, 0));
        contOldBuildingAvgPrice.setBounds(new Rectangle(9, 161, 270, 19));
        this.add(contOldBuildingAvgPrice, new KDLayout.Constraints(9, 161, 270, 19, 0));
        contOldRoomAvgPrice.setBounds(new Rectangle(10, 190, 270, 19));
        this.add(contOldRoomAvgPrice, new KDLayout.Constraints(10, 190, 270, 19, 0));
        chkIsAutoToInteger.setBounds(new Rectangle(10, 300, 264, 19));
        this.add(chkIsAutoToInteger, new KDLayout.Constraints(10, 300, 264, 19, 0));
        contToIntegerType.setBounds(new Rectangle(417, 300, 270, 19));
        this.add(contToIntegerType, new KDLayout.Constraints(417, 300, 270, 19, 0));
        contDigit.setBounds(new Rectangle(417, 337, 270, 19));
        this.add(contDigit, new KDLayout.Constraints(417, 337, 270, 19, 0));
        prmtRoomSelect.setBounds(new Rectangle(13, 374, 170, 19));
        this.add(prmtRoomSelect, new KDLayout.Constraints(13, 374, 170, 19, 0));
        btnAddRoomByList.setBounds(new Rectangle(461, 374, 133, 19));
        this.add(btnAddRoomByList, new KDLayout.Constraints(461, 374, 133, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(10, 135, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(10, 135, 270, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(412, 135, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(412, 135, 270, 19, 0));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contCreator
        contCreator.setBoundEditor(f7Creator);
        //contName
        contName.setBoundEditor(txtName);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contTotalBuildingArea
        contTotalBuildingArea.setBoundEditor(txtTotalBuildingArea);
        //contTotalRoomArea
        contTotalRoomArea.setBoundEditor(txtTotalRoomArea);
        //contTotalCount
        contTotalCount.setBoundEditor(txtTotalCount);
        //contNewTotalPrice
        contNewTotalPrice.setBoundEditor(txtNewTotalPrice);
        //contNewBuildingAvgPrice
        contNewBuildingAvgPrice.setBoundEditor(txtNewBuildingAvgPrice);
        //contNewRoomAvgPrice
        contNewRoomAvgPrice.setBoundEditor(txtNewRoomAvgPrice);
        //contOldTotalPrice
        contOldTotalPrice.setBoundEditor(txtOldTotalPrice);
        //contOldBuildingAvgPrice
        contOldBuildingAvgPrice.setBoundEditor(txtOldBuildingAvgPrice);
        //contOldRoomAvgPrice
        contOldRoomAvgPrice.setBoundEditor(txtOldRoomAvgPrice);
        //contToIntegerType
        contToIntegerType.setBoundEditor(comboToIntegerType);
        //contDigit
        contDigit.setBoundEditor(comboDigit);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(totalAmountBalance);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(totalAmountBalanceScale);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isAutoToInteger", boolean.class, this.chkIsAutoToInteger, "selected");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.f7Creator, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("toIntegerType", com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum.class, this.comboToIntegerType, "selectedItem");
		dataBinder.registerBinding("digit", com.kingdee.eas.fdc.sellhouse.DigitEnum.class, this.comboDigit, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.PriceAdjustEditUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.sellhouse.PriceAdjustInfo)ov;
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("isAutoToInteger", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("toIntegerType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("digit", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output tblRoom_editStopped method
     */
    protected void tblRoom_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output tblRoom_tableClicked method
     */
    protected void tblRoom_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnAddRoom_actionPerformed method
     */
    protected void btnAddRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnRemoveRoom_actionPerformed method
     */
    protected void btnRemoveRoom_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnImportPrice_actionPerformed method
     */
    protected void btnImportPrice_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output chkIsAutoToInteger_actionPerformed method
     */
    protected void chkIsAutoToInteger_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comboToIntegerType_itemStateChanged method
     */
    protected void comboToIntegerType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output comboDigit_itemStateChanged method
     */
    protected void comboDigit_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("isAutoToInteger"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("toIntegerType"));
        sic.add(new SelectorItemInfo("digit"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionAddRoomByList_actionPerformed method
     */
    public void actionAddRoomByList_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionAddRoomByList(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAddRoomByList() {
    	return false;
    }

    /**
     * output ActionAddRoomByList class
     */     
    protected class ActionAddRoomByList extends ItemAction {     
    
        public ActionAddRoomByList()
        {
            this(null);
        }

        public ActionAddRoomByList(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAddRoomByList.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRoomByList.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAddRoomByList.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPriceAdjustEditUI.this, "ActionAddRoomByList", "actionAddRoomByList_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "PriceAdjustEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}