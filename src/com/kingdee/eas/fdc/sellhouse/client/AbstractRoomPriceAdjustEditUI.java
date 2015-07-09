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
public abstract class AbstractRoomPriceAdjustEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomPriceAdjustEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPriceBillType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPriceBillMode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuilding;
    protected com.kingdee.bos.ctrl.swing.KDButton btnRoomSelect;
    protected com.kingdee.bos.ctrl.swing.KDButton btnBatchAdjust;
    protected com.kingdee.bos.ctrl.swing.KDButton btnPriceScheme;
    protected com.kingdee.bos.ctrl.swing.KDButton btnPriceImport;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAvgRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBuildingPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMaxRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMinBuildingPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMinRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDButton btnRoomDelete;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSalesareatype;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contprice;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoomSelect;
    protected com.kingdee.bos.ctrl.swing.KDButton btnAttach;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblAttach;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOpenBatch;
    protected com.kingdee.bos.ctrl.swing.KDButton btnValueInput;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane2;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPriceBillType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboPriceBillMode;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtBuilding;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAvgBuildingPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAvgRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMaxBuildingPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMaxRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMinBuildingPrice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtMinRoomPrice;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboalesareatype;
    protected com.kingdee.bos.ctrl.swing.KDNumberTextField Basisprice;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoomSelect;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPreAdjValue;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAftAdjValue;
    protected com.kingdee.bos.ctrl.swing.KDButton btnForecast;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField preAdjValue;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField aftAdjValue;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbOpenBatch;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPanel;
    protected com.kingdee.bos.ctrl.swing.KDContainer contEntry;
    protected com.kingdee.bos.ctrl.swing.KDContainer contDiffEntry;
    protected com.kingdee.bos.ctrl.swing.KDPanel overViewTab;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtPriceAdjustEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtDiffEntry;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnExecute;
    protected com.kingdee.eas.fdc.sellhouse.RoomPriceManageInfo editData = null;
    protected ActionExecute actionExecute = null;
    /**
     * output class constructor
     */
    public AbstractRoomPriceAdjustEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomPriceAdjustEditUI.class.getName());
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
        //actionExecute
        this.actionExecute = new ActionExecute(this);
        getActionManager().registerAction("actionExecute", actionExecute);
         this.actionExecute.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPriceBillType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPriceBillMode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuilding = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnRoomSelect = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnBatchAdjust = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnPriceScheme = new com.kingdee.bos.ctrl.swing.KDButton();
        this.btnPriceImport = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAvgRoomPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBuildingPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMaxRoomPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMinBuildingPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMinRoomPrice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnRoomDelete = new com.kingdee.bos.ctrl.swing.KDButton();
        this.contSalesareatype = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contprice = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRoomSelect = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnAttach = new com.kingdee.bos.ctrl.swing.KDButton();
        this.tblAttach = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contOpenBatch = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnValueInput = new com.kingdee.bos.ctrl.swing.KDButton();
        this.kDTabbedPane2 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtSellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboPriceBillType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.comboPriceBillMode = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtBuilding = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtAvgBuildingPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtAvgRoomPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtMaxBuildingPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtMaxRoomPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtMinBuildingPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtMinRoomPrice = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.pkCreateDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.comboalesareatype = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.Basisprice = new com.kingdee.bos.ctrl.swing.KDNumberTextField();
        this.prmtRoomSelect = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contPreAdjValue = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAftAdjValue = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnForecast = new com.kingdee.bos.ctrl.swing.KDButton();
        this.preAdjValue = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.aftAdjValue = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.cbOpenBatch = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.kDTabbedPanel = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.contEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contDiffEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.overViewTab = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kdtPriceAdjustEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtDiffEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnExecute = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.contSellProject.setName("contSellProject");
        this.contPriceBillType.setName("contPriceBillType");
        this.contPriceBillMode.setName("contPriceBillMode");
        this.contBuilding.setName("contBuilding");
        this.btnRoomSelect.setName("btnRoomSelect");
        this.btnBatchAdjust.setName("btnBatchAdjust");
        this.btnPriceScheme.setName("btnPriceScheme");
        this.btnPriceImport.setName("btnPriceImport");
        this.contDescription.setName("contDescription");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.contAvgRoomPrice.setName("contAvgRoomPrice");
        this.contBuildingPrice.setName("contBuildingPrice");
        this.contMaxRoomPrice.setName("contMaxRoomPrice");
        this.contMinBuildingPrice.setName("contMinBuildingPrice");
        this.contMinRoomPrice.setName("contMinRoomPrice");
        this.contCreateDate.setName("contCreateDate");
        this.contCreator.setName("contCreator");
        this.btnRoomDelete.setName("btnRoomDelete");
        this.contSalesareatype.setName("contSalesareatype");
        this.contprice.setName("contprice");
        this.contRoomSelect.setName("contRoomSelect");
        this.btnAttach.setName("btnAttach");
        this.tblAttach.setName("tblAttach");
        this.kDPanel1.setName("kDPanel1");
        this.contOpenBatch.setName("contOpenBatch");
        this.btnValueInput.setName("btnValueInput");
        this.kDTabbedPane2.setName("kDTabbedPane2");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.prmtSellProject.setName("prmtSellProject");
        this.comboPriceBillType.setName("comboPriceBillType");
        this.comboPriceBillMode.setName("comboPriceBillMode");
        this.prmtBuilding.setName("prmtBuilding");
        this.txtDescription.setName("txtDescription");
        this.txtAvgBuildingPrice.setName("txtAvgBuildingPrice");
        this.txtAvgRoomPrice.setName("txtAvgRoomPrice");
        this.txtMaxBuildingPrice.setName("txtMaxBuildingPrice");
        this.txtMaxRoomPrice.setName("txtMaxRoomPrice");
        this.txtMinBuildingPrice.setName("txtMinBuildingPrice");
        this.txtMinRoomPrice.setName("txtMinRoomPrice");
        this.pkCreateDate.setName("pkCreateDate");
        this.prmtCreator.setName("prmtCreator");
        this.comboalesareatype.setName("comboalesareatype");
        this.Basisprice.setName("Basisprice");
        this.prmtRoomSelect.setName("prmtRoomSelect");
        this.contPreAdjValue.setName("contPreAdjValue");
        this.contAftAdjValue.setName("contAftAdjValue");
        this.btnForecast.setName("btnForecast");
        this.preAdjValue.setName("preAdjValue");
        this.aftAdjValue.setName("aftAdjValue");
        this.cbOpenBatch.setName("cbOpenBatch");
        this.kDTabbedPanel.setName("kDTabbedPanel");
        this.contEntry.setName("contEntry");
        this.contDiffEntry.setName("contDiffEntry");
        this.overViewTab.setName("overViewTab");
        this.kdtPriceAdjustEntry.setName("kdtPriceAdjustEntry");
        this.kdtEntry.setName("kdtEntry");
        this.kdtDiffEntry.setName("kdtDiffEntry");
        this.btnExecute.setName("btnExecute");
        // CoreUI		
        this.setBorder(null);		
        this.setPreferredSize(new Dimension(1013,660));		
        this.btnAuditResult.setEnabled(false);		
        this.btnAuditResult.setVisible(false);		
        this.btnMultiapprove.setEnabled(false);		
        this.btnMultiapprove.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // contPriceBillType		
        this.contPriceBillType.setBoundLabelText(resHelper.getString("contPriceBillType.boundLabelText"));		
        this.contPriceBillType.setBoundLabelLength(100);		
        this.contPriceBillType.setBoundLabelUnderline(true);
        // contPriceBillMode		
        this.contPriceBillMode.setBoundLabelText(resHelper.getString("contPriceBillMode.boundLabelText"));		
        this.contPriceBillMode.setBoundLabelLength(100);		
        this.contPriceBillMode.setBoundLabelUnderline(true);
        // contBuilding		
        this.contBuilding.setBoundLabelText(resHelper.getString("contBuilding.boundLabelText"));		
        this.contBuilding.setBoundLabelLength(100);		
        this.contBuilding.setBoundLabelUnderline(true);
        // btnRoomSelect		
        this.btnRoomSelect.setText(resHelper.getString("btnRoomSelect.text"));
        this.btnRoomSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRoomSelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnBatchAdjust		
        this.btnBatchAdjust.setText(resHelper.getString("btnBatchAdjust.text"));
        this.btnBatchAdjust.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnBatchAdjust_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnPriceScheme		
        this.btnPriceScheme.setText(resHelper.getString("btnPriceScheme.text"));
        this.btnPriceScheme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnPriceScheme_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnPriceImport		
        this.btnPriceImport.setText(resHelper.getString("btnPriceImport.text"));
        this.btnPriceImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnPriceImport_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // contAvgRoomPrice		
        this.contAvgRoomPrice.setBoundLabelText(resHelper.getString("contAvgRoomPrice.boundLabelText"));		
        this.contAvgRoomPrice.setBoundLabelLength(100);		
        this.contAvgRoomPrice.setBoundLabelUnderline(true);
        // contBuildingPrice		
        this.contBuildingPrice.setBoundLabelText(resHelper.getString("contBuildingPrice.boundLabelText"));		
        this.contBuildingPrice.setBoundLabelLength(100);		
        this.contBuildingPrice.setBoundLabelUnderline(true);
        // contMaxRoomPrice		
        this.contMaxRoomPrice.setBoundLabelText(resHelper.getString("contMaxRoomPrice.boundLabelText"));		
        this.contMaxRoomPrice.setBoundLabelUnderline(true);		
        this.contMaxRoomPrice.setBoundLabelLength(100);
        // contMinBuildingPrice		
        this.contMinBuildingPrice.setBoundLabelText(resHelper.getString("contMinBuildingPrice.boundLabelText"));		
        this.contMinBuildingPrice.setBoundLabelLength(100);		
        this.contMinBuildingPrice.setBoundLabelUnderline(true);
        // contMinRoomPrice		
        this.contMinRoomPrice.setBoundLabelText(resHelper.getString("contMinRoomPrice.boundLabelText"));		
        this.contMinRoomPrice.setBoundLabelLength(100);		
        this.contMinRoomPrice.setBoundLabelUnderline(true);
        // contCreateDate		
        this.contCreateDate.setBoundLabelText(resHelper.getString("contCreateDate.boundLabelText"));		
        this.contCreateDate.setBoundLabelLength(100);		
        this.contCreateDate.setBoundLabelUnderline(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // btnRoomDelete		
        this.btnRoomDelete.setText(resHelper.getString("btnRoomDelete.text"));
        this.btnRoomDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnRoomDelete_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contSalesareatype		
        this.contSalesareatype.setBoundLabelText(resHelper.getString("contSalesareatype.boundLabelText"));		
        this.contSalesareatype.setBoundLabelLength(100);		
        this.contSalesareatype.setBoundLabelUnderline(true);
        // contprice		
        this.contprice.setBoundLabelText(resHelper.getString("contprice.boundLabelText"));		
        this.contprice.setBoundLabelUnderline(true);		
        this.contprice.setBoundLabelLength(100);		
        this.contprice.setEnabled(false);		
        this.contprice.setVisible(false);
        // contRoomSelect		
        this.contRoomSelect.setBoundLabelText(resHelper.getString("contRoomSelect.boundLabelText"));		
        this.contRoomSelect.setBoundLabelLength(100);		
        this.contRoomSelect.setBoundLabelUnderline(true);		
        this.contRoomSelect.setEnabled(false);		
        this.contRoomSelect.setVisible(false);
        // btnAttach
        this.btnAttach.setAction((IItemAction)ActionProxyFactory.getProxy(actionAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAttach.setText(resHelper.getString("btnAttach.text"));
        // tblAttach
		String tblAttachStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol2\"><c:NumberFormat>yyyy-mm-dd</c:NumberFormat></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"size\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol2\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{size}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblAttach.setFormatXml(resHelper.translateString("tblAttach",tblAttachStrXML));
        this.tblAttach.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblAttach_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // kDPanel1		
        this.kDPanel1.setBorder(BorderFactory.createEtchedBorder());
        // contOpenBatch		
        this.contOpenBatch.setBoundLabelText(resHelper.getString("contOpenBatch.boundLabelText"));		
        this.contOpenBatch.setBoundLabelLength(100);		
        this.contOpenBatch.setBoundLabelUnderline(true);
        // btnValueInput		
        this.btnValueInput.setText(resHelper.getString("btnValueInput.text"));
        this.btnValueInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnValueInput_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDTabbedPane2
        // txtNumber		
        this.txtNumber.setRequired(true);
        // txtName		
        this.txtName.setRequired(true);
        // prmtSellProject		
        this.prmtSellProject.setRequired(true);		
        this.prmtSellProject.setEnabled(false);
        // comboPriceBillType		
        this.comboPriceBillType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum").toArray());
        this.comboPriceBillType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboPriceBillType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // comboPriceBillMode		
        this.comboPriceBillMode.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum").toArray());
        this.comboPriceBillMode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboPriceBillMode_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtBuilding		
        this.prmtBuilding.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.BuildingQuery");		
        this.prmtBuilding.setEnabledMultiSelection(true);		
        this.prmtBuilding.setDisplayFormat("$name$");		
        this.prmtBuilding.setEditFormat("$number$");		
        this.prmtBuilding.setCommitFormat("$number$");
        this.prmtBuilding.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtBuilding_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtDescription		
        this.txtDescription.setMaxLength(255);
        // txtAvgBuildingPrice		
        this.txtAvgBuildingPrice.setDataType(1);		
        this.txtAvgBuildingPrice.setEnabled(false);		
        this.txtAvgBuildingPrice.setEditable(false);
        // txtAvgRoomPrice		
        this.txtAvgRoomPrice.setDataType(1);		
        this.txtAvgRoomPrice.setEnabled(false);		
        this.txtAvgRoomPrice.setEditable(false);
        // txtMaxBuildingPrice		
        this.txtMaxBuildingPrice.setDataType(1);		
        this.txtMaxBuildingPrice.setEnabled(false);		
        this.txtMaxBuildingPrice.setEditable(false);
        // txtMaxRoomPrice		
        this.txtMaxRoomPrice.setDataType(1);		
        this.txtMaxRoomPrice.setEnabled(false);		
        this.txtMaxRoomPrice.setEditable(false);
        this.txtMaxRoomPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtMaxRoomPrice_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtMinBuildingPrice		
        this.txtMinBuildingPrice.setDataType(1);		
        this.txtMinBuildingPrice.setEnabled(false);		
        this.txtMinBuildingPrice.setEditable(false);
        // txtMinRoomPrice		
        this.txtMinRoomPrice.setDataType(1);		
        this.txtMinRoomPrice.setEditable(false);		
        this.txtMinRoomPrice.setEnabled(false);
        // pkCreateDate		
        this.pkCreateDate.setEnabled(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // comboalesareatype		
        this.comboalesareatype.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.SellAreaTypeEnum").toArray());
        this.comboalesareatype.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    salesareatype_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // Basisprice
        // prmtRoomSelect		
        this.prmtRoomSelect.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomSelectByListQuery");		
        this.prmtRoomSelect.setVisible(false);		
        this.prmtRoomSelect.setEnabledMultiSelection(true);
        this.prmtRoomSelect.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtRoomSelect_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contPreAdjValue		
        this.contPreAdjValue.setBoundLabelText(resHelper.getString("contPreAdjValue.boundLabelText"));		
        this.contPreAdjValue.setBoundLabelLength(100);		
        this.contPreAdjValue.setBoundLabelUnderline(true);
        // contAftAdjValue		
        this.contAftAdjValue.setBoundLabelText(resHelper.getString("contAftAdjValue.boundLabelText"));		
        this.contAftAdjValue.setBoundLabelLength(100);		
        this.contAftAdjValue.setBoundLabelUnderline(true);
        // btnForecast		
        this.btnForecast.setText(resHelper.getString("btnForecast.text"));
        this.btnForecast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnForecast_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // preAdjValue
        // aftAdjValue
        // cbOpenBatch		
        this.cbOpenBatch.setRequired(true);		
        this.cbOpenBatch.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.OpenBatchEnum").toArray());
        // kDTabbedPanel
        // contEntry
        // contDiffEntry
        // overViewTab
        // kdtPriceAdjustEntry
		String kdtPriceAdjustEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"roomId\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"building\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"buildUnit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"roomNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"Salesareatype\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"priceType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"oldSumAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"newSumAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"oldBaseStandardPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"newBaseStandardPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"oldSceneManagerAgio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"newSceneManagerAgio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"oldSalesDirectorAgio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"newSalesDirectorAgio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"oldManagerAgio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"newManagerAgio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"oldBuildingPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"newBuildingPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"oldRoomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"newRoomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{roomId}</t:Cell><t:Cell>$Resource{building}</t:Cell><t:Cell>$Resource{buildUnit}</t:Cell><t:Cell>$Resource{roomNo}</t:Cell><t:Cell>$Resource{Salesareatype}</t:Cell><t:Cell>$Resource{priceType}</t:Cell><t:Cell>$Resource{oldSumAmount}</t:Cell><t:Cell>$Resource{newSumAmount}</t:Cell><t:Cell>$Resource{oldBaseStandardPrice}</t:Cell><t:Cell>$Resource{newBaseStandardPrice}</t:Cell><t:Cell>$Resource{oldSceneManagerAgio}</t:Cell><t:Cell>$Resource{newSceneManagerAgio}</t:Cell><t:Cell>$Resource{oldSalesDirectorAgio}</t:Cell><t:Cell>$Resource{newSalesDirectorAgio}</t:Cell><t:Cell>$Resource{oldManagerAgio}</t:Cell><t:Cell>$Resource{newManagerAgio}</t:Cell><t:Cell>$Resource{oldBuildingPrice}</t:Cell><t:Cell>$Resource{newBuildingPrice}</t:Cell><t:Cell>$Resource{oldRoomPrice}</t:Cell><t:Cell>$Resource{newRoomPrice}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtPriceAdjustEntry.setFormatXml(resHelper.translateString("kdtPriceAdjustEntry",kdtPriceAdjustEntryStrXML));
        this.kdtPriceAdjustEntry.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtPriceAdjustEntry_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtPriceAdjustEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtPriceAdjustEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtPriceAdjustEntry.putBindContents("editData",new String[] {"room.id","room.building.name","room.buildUnit.name","room.displayName","Salesareatype","priceType","oldSumAmount","newSumAmount","","","","","","","","","oldBuildingPrice","newBuildingPrice","oldRoomPrice","newRoomPrice","newBuildingArea","newRoomArea"});


        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol10\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol12\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"project\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"batch\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"build\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"productType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"decorate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"4\" /><t:Column t:key=\"modelName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"modelType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"modelArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"account\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"area\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"date\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"price\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"11\" /><t:Column t:key=\"calculateType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{batch}</t:Cell><t:Cell>$Resource{build}</t:Cell><t:Cell>$Resource{productType}</t:Cell><t:Cell>$Resource{decorate}</t:Cell><t:Cell>$Resource{modelName}</t:Cell><t:Cell>$Resource{modelType}</t:Cell><t:Cell>$Resource{modelArea}</t:Cell><t:Cell>$Resource{account}</t:Cell><t:Cell>$Resource{area}</t:Cell><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{price}</t:Cell><t:Cell>$Resource{calculateType}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));
        this.kdtEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEntry.putBindContents("editData",new String[] {"project","batch","build","productType","decorate","modelName","modelType","modelArea","account","area","date","price","calculateType","amount","remark"});


        // kdtDiffEntry
		String kdtDiffEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol6\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol9\"><c:Protection locked=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"project\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"batch\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"build\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"productType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"oldPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"newPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"subPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"oldAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"newAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"subAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol9\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{project}</t:Cell><t:Cell>$Resource{batch}</t:Cell><t:Cell>$Resource{build}</t:Cell><t:Cell>$Resource{productType}</t:Cell><t:Cell>$Resource{oldPrice}</t:Cell><t:Cell>$Resource{newPrice}</t:Cell><t:Cell>$Resource{subPrice}</t:Cell><t:Cell>$Resource{oldAmount}</t:Cell><t:Cell>$Resource{newAmount}</t:Cell><t:Cell>$Resource{subAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtDiffEntry.setFormatXml(resHelper.translateString("kdtDiffEntry",kdtDiffEntryStrXML));

        

        // btnExecute
        this.btnExecute.setAction((IItemAction)ActionProxyFactory.getProxy(actionExecute, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnExecute.setText(resHelper.getString("btnExecute.text"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 660));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 660));
        contNumber.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(10, 10, 270, 19, 0));
        contName.setBounds(new Rectangle(371, 10, 270, 19));
        this.add(contName, new KDLayout.Constraints(371, 10, 270, 19, 0));
        contSellProject.setBounds(new Rectangle(733, 10, 270, 19));
        this.add(contSellProject, new KDLayout.Constraints(733, 10, 270, 19, 0));
        contPriceBillType.setBounds(new Rectangle(10, 32, 270, 19));
        this.add(contPriceBillType, new KDLayout.Constraints(10, 32, 270, 19, 0));
        contPriceBillMode.setBounds(new Rectangle(371, 32, 270, 19));
        this.add(contPriceBillMode, new KDLayout.Constraints(371, 32, 270, 19, 0));
        contBuilding.setBounds(new Rectangle(733, 32, 270, 19));
        this.add(contBuilding, new KDLayout.Constraints(733, 32, 270, 19, 0));
        btnRoomSelect.setBounds(new Rectangle(526, 198, 83, 22));
        this.add(btnRoomSelect, new KDLayout.Constraints(526, 198, 83, 22, 0));
        btnBatchAdjust.setBounds(new Rectangle(624, 198, 83, 22));
        this.add(btnBatchAdjust, new KDLayout.Constraints(624, 198, 83, 22, 0));
        btnPriceScheme.setBounds(new Rectangle(722, 198, 83, 22));
        this.add(btnPriceScheme, new KDLayout.Constraints(722, 198, 83, 22, 0));
        btnPriceImport.setBounds(new Rectangle(820, 198, 83, 22));
        this.add(btnPriceImport, new KDLayout.Constraints(820, 198, 83, 22, 0));
        contDescription.setBounds(new Rectangle(10, 527, 993, 57));
        this.add(contDescription, new KDLayout.Constraints(10, 527, 993, 57, 0));
        kDLabelContainer2.setBounds(new Rectangle(10, 587, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(10, 587, 270, 19, 0));
        contAvgRoomPrice.setBounds(new Rectangle(10, 609, 270, 19));
        this.add(contAvgRoomPrice, new KDLayout.Constraints(10, 609, 270, 19, 0));
        contBuildingPrice.setBounds(new Rectangle(371, 587, 270, 19));
        this.add(contBuildingPrice, new KDLayout.Constraints(371, 587, 270, 19, 0));
        contMaxRoomPrice.setBounds(new Rectangle(371, 609, 270, 19));
        this.add(contMaxRoomPrice, new KDLayout.Constraints(371, 609, 270, 19, 0));
        contMinBuildingPrice.setBounds(new Rectangle(733, 587, 270, 19));
        this.add(contMinBuildingPrice, new KDLayout.Constraints(733, 587, 270, 19, 0));
        contMinRoomPrice.setBounds(new Rectangle(733, 609, 270, 19));
        this.add(contMinRoomPrice, new KDLayout.Constraints(733, 609, 270, 19, 0));
        contCreateDate.setBounds(new Rectangle(10, 631, 270, 19));
        this.add(contCreateDate, new KDLayout.Constraints(10, 631, 270, 19, 0));
        contCreator.setBounds(new Rectangle(371, 631, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(371, 631, 270, 19, 0));
        btnRoomDelete.setBounds(new Rectangle(920, 198, 83, 22));
        this.add(btnRoomDelete, new KDLayout.Constraints(920, 198, 83, 22, 0));
        contSalesareatype.setBounds(new Rectangle(10, 54, 270, 19));
        this.add(contSalesareatype, new KDLayout.Constraints(10, 54, 270, 19, 0));
        contprice.setBounds(new Rectangle(737, 633, 270, 19));
        this.add(contprice, new KDLayout.Constraints(737, 633, 270, 19, 0));
        contRoomSelect.setBounds(new Rectangle(664, 631, 270, 19));
        this.add(contRoomSelect, new KDLayout.Constraints(664, 631, 270, 19, 0));
        btnAttach.setBounds(new Rectangle(371, 54, 139, 21));
        this.add(btnAttach, new KDLayout.Constraints(371, 54, 139, 21, 0));
        tblAttach.setBounds(new Rectangle(371, 77, 631, 100));
        this.add(tblAttach, new KDLayout.Constraints(371, 77, 631, 100, 0));
        kDPanel1.setBounds(new Rectangle(5, 77, 281, 100));
        this.add(kDPanel1, new KDLayout.Constraints(5, 77, 281, 100, 0));
        contOpenBatch.setBounds(new Rectangle(733, 54, 270, 19));
        this.add(contOpenBatch, new KDLayout.Constraints(733, 54, 270, 19, 0));
        btnValueInput.setBounds(new Rectangle(397, 198, 114, 21));
        this.add(btnValueInput, new KDLayout.Constraints(397, 198, 114, 21, 0));
        kDTabbedPane2.setBounds(new Rectangle(5, 229, 999, 294));
        this.add(kDTabbedPane2, new KDLayout.Constraints(5, 229, 999, 294, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contSellProject
        contSellProject.setBoundEditor(prmtSellProject);
        //contPriceBillType
        contPriceBillType.setBoundEditor(comboPriceBillType);
        //contPriceBillMode
        contPriceBillMode.setBoundEditor(comboPriceBillMode);
        //contBuilding
        contBuilding.setBoundEditor(prmtBuilding);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtAvgBuildingPrice);
        //contAvgRoomPrice
        contAvgRoomPrice.setBoundEditor(txtAvgRoomPrice);
        //contBuildingPrice
        contBuildingPrice.setBoundEditor(txtMaxBuildingPrice);
        //contMaxRoomPrice
        contMaxRoomPrice.setBoundEditor(txtMaxRoomPrice);
        //contMinBuildingPrice
        contMinBuildingPrice.setBoundEditor(txtMinBuildingPrice);
        //contMinRoomPrice
        contMinRoomPrice.setBoundEditor(txtMinRoomPrice);
        //contCreateDate
        contCreateDate.setBoundEditor(pkCreateDate);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contSalesareatype
        contSalesareatype.setBoundEditor(comboalesareatype);
        //contprice
        contprice.setBoundEditor(Basisprice);
        //contRoomSelect
        contRoomSelect.setBoundEditor(prmtRoomSelect);
        //kDPanel1
        kDPanel1.setLayout(null);        contPreAdjValue.setBounds(new Rectangle(5, 41, 270, 19));
        kDPanel1.add(contPreAdjValue, null);
        contAftAdjValue.setBounds(new Rectangle(5, 62, 270, 19));
        kDPanel1.add(contAftAdjValue, null);
        btnForecast.setBounds(new Rectangle(5, 7, 139, 21));
        kDPanel1.add(btnForecast, null);
        //contPreAdjValue
        contPreAdjValue.setBoundEditor(preAdjValue);
        //contAftAdjValue
        contAftAdjValue.setBoundEditor(aftAdjValue);
        //contOpenBatch
        contOpenBatch.setBoundEditor(cbOpenBatch);
        //kDTabbedPane2
        kDTabbedPane2.add(kDTabbedPanel, resHelper.getString("kDTabbedPanel.constraints"));
        kDTabbedPane2.add(contEntry, resHelper.getString("contEntry.constraints"));
        kDTabbedPane2.add(contDiffEntry, resHelper.getString("contDiffEntry.constraints"));
        //kDTabbedPanel
        kDTabbedPanel.add(overViewTab, resHelper.getString("overViewTab.constraints"));
        //overViewTab
overViewTab.setLayout(new BorderLayout(0, 0));        overViewTab.add(kdtPriceAdjustEntry, BorderLayout.CENTER);
        //contEntry
contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contEntry.getContentPane().add(kdtEntry, BorderLayout.CENTER);
        //contDiffEntry
contDiffEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contDiffEntry.getContentPane().add(kdtDiffEntry, BorderLayout.CENTER);

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
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
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
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnSave);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
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
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnExecute);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("sellProject", com.kingdee.eas.fdc.sellhouse.SellProjectInfo.class, this.prmtSellProject, "data");
		dataBinder.registerBinding("priceBillType", com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum.class, this.comboPriceBillType, "selectedItem");
		dataBinder.registerBinding("priceBillMode", com.kingdee.eas.fdc.sellhouse.PriceBillTypeEnum.class, this.comboPriceBillMode, "selectedItem");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("avgBulidPrice", java.math.BigDecimal.class, this.txtAvgBuildingPrice, "value");
		dataBinder.registerBinding("avgRoomPrice", java.math.BigDecimal.class, this.txtAvgRoomPrice, "value");
		dataBinder.registerBinding("maxBuildPrice", java.math.BigDecimal.class, this.txtMaxBuildingPrice, "value");
		dataBinder.registerBinding("maxRoomPrice", java.math.BigDecimal.class, this.txtMaxRoomPrice, "value");
		dataBinder.registerBinding("minBuildPrice", java.math.BigDecimal.class, this.txtMinBuildingPrice, "value");
		dataBinder.registerBinding("minRoomPrice", java.math.BigDecimal.class, this.txtMinRoomPrice, "value");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateDate, "value");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("preAdjValue", java.math.BigDecimal.class, this.preAdjValue, "value");
		dataBinder.registerBinding("aftAdjValue", java.math.BigDecimal.class, this.aftAdjValue, "value");
		dataBinder.registerBinding("openBatch", com.kingdee.eas.fdc.sellhouse.OpenBatchEnum.class, this.cbOpenBatch, "selectedItem");
		dataBinder.registerBinding("priceAdjustEntry.priceType", com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum.class, this.kdtPriceAdjustEntry, "priceType.text");
		dataBinder.registerBinding("priceAdjustEntry.oldSumAmount", java.math.BigDecimal.class, this.kdtPriceAdjustEntry, "oldSumAmount.text");
		dataBinder.registerBinding("priceAdjustEntry.newBuildingArea", java.math.BigDecimal.class, this.kdtPriceAdjustEntry, "buildingArea.text");
		dataBinder.registerBinding("priceAdjustEntry.oldBuildingPrice", java.math.BigDecimal.class, this.kdtPriceAdjustEntry, "oldBuildingPrice.text");
		dataBinder.registerBinding("priceAdjustEntry.newBuildingPrice", java.math.BigDecimal.class, this.kdtPriceAdjustEntry, "newBuildingPrice.text");
		dataBinder.registerBinding("priceAdjustEntry.newRoomArea", java.math.BigDecimal.class, this.kdtPriceAdjustEntry, "roomArea.text");
		dataBinder.registerBinding("priceAdjustEntry.oldRoomPrice", java.math.BigDecimal.class, this.kdtPriceAdjustEntry, "oldRoomPrice.text");
		dataBinder.registerBinding("priceAdjustEntry.newRoomPrice", java.math.BigDecimal.class, this.kdtPriceAdjustEntry, "newRoomPrice.text");
		dataBinder.registerBinding("priceAdjustEntry.room.id", com.kingdee.bos.util.BOSUuid.class, this.kdtPriceAdjustEntry, "roomId.text");
		dataBinder.registerBinding("priceAdjustEntry.room.number", String.class, this.kdtPriceAdjustEntry, "roomNumber.text");
		dataBinder.registerBinding("priceAdjustEntry.newSumAmount", java.math.BigDecimal.class, this.kdtPriceAdjustEntry, "newSumAmount.text");
		dataBinder.registerBinding("priceAdjustEntry.room.displayName", String.class, this.kdtPriceAdjustEntry, "roomNo.text");
		dataBinder.registerBinding("priceAdjustEntry.Salesareatype", com.kingdee.eas.fdc.sellhouse.SellAreaTypeEnum.class, this.kdtPriceAdjustEntry, "Salesareatype.text");
		dataBinder.registerBinding("priceAdjustEntry.room.building.name", String.class, this.kdtPriceAdjustEntry, "building.text");
		dataBinder.registerBinding("priceAdjustEntry.room.buildUnit.name", String.class, this.kdtPriceAdjustEntry, "buildUnit.text");
		dataBinder.registerBinding("valueEntry", com.kingdee.eas.fdc.market.ValueInputEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("valueEntry.project", String.class, this.kdtEntry, "project.text");
		dataBinder.registerBinding("valueEntry.batch", String.class, this.kdtEntry, "batch.text");
		dataBinder.registerBinding("valueEntry.build", String.class, this.kdtEntry, "build.text");
		dataBinder.registerBinding("valueEntry.productType", String.class, this.kdtEntry, "productType.text");
		dataBinder.registerBinding("valueEntry.decorate", com.kingdee.eas.fdc.market.DecorateEnum.class, this.kdtEntry, "decorate.text");
		dataBinder.registerBinding("valueEntry.modelName", String.class, this.kdtEntry, "modelName.text");
		dataBinder.registerBinding("valueEntry.modelType", String.class, this.kdtEntry, "modelType.text");
		dataBinder.registerBinding("valueEntry.modelArea", String.class, this.kdtEntry, "modelArea.text");
		dataBinder.registerBinding("valueEntry.account", int.class, this.kdtEntry, "account.text");
		dataBinder.registerBinding("valueEntry.area", java.math.BigDecimal.class, this.kdtEntry, "area.text");
		dataBinder.registerBinding("valueEntry.date", java.util.Date.class, this.kdtEntry, "date.text");
		dataBinder.registerBinding("valueEntry.price", java.math.BigDecimal.class, this.kdtEntry, "price.text");
		dataBinder.registerBinding("valueEntry.calculateType", com.kingdee.eas.fdc.basedata.CalculateTypeEnum.class, this.kdtEntry, "calculateType.text");
		dataBinder.registerBinding("valueEntry.amount", java.math.BigDecimal.class, this.kdtEntry, "amount.text");
		dataBinder.registerBinding("valueEntry.remark", String.class, this.kdtEntry, "remark.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomPriceAdjustEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.RoomPriceManageInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProject", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceBillType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceBillMode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("avgBulidPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("avgRoomPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("maxBuildPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("maxRoomPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("minBuildPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("minRoomPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("preAdjValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("aftAdjValue", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("openBatch", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceAdjustEntry.priceType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceAdjustEntry.oldSumAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceAdjustEntry.newBuildingArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceAdjustEntry.oldBuildingPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceAdjustEntry.newBuildingPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceAdjustEntry.newRoomArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceAdjustEntry.oldRoomPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceAdjustEntry.newRoomPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceAdjustEntry.room.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceAdjustEntry.room.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceAdjustEntry.newSumAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceAdjustEntry.room.displayName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceAdjustEntry.Salesareatype", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceAdjustEntry.room.building.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("priceAdjustEntry.room.buildUnit.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("valueEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("valueEntry.project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("valueEntry.batch", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("valueEntry.build", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("valueEntry.productType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("valueEntry.decorate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("valueEntry.modelName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("valueEntry.modelType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("valueEntry.modelArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("valueEntry.account", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("valueEntry.area", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("valueEntry.date", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("valueEntry.price", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("valueEntry.calculateType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("valueEntry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("valueEntry.remark", ValidateHelper.ON_SAVE);    		
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
     * output btnRoomSelect_actionPerformed method
     */
    protected void btnRoomSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnBatchAdjust_actionPerformed method
     */
    protected void btnBatchAdjust_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnPriceScheme_actionPerformed method
     */
    protected void btnPriceScheme_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnPriceImport_actionPerformed method
     */
    protected void btnPriceImport_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnRoomDelete_actionPerformed method
     */
    protected void btnRoomDelete_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output tblAttach_tableClicked method
     */
    protected void tblAttach_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output btnValueInput_actionPerformed method
     */
    protected void btnValueInput_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comboPriceBillType_itemStateChanged method
     */
    protected void comboPriceBillType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output comboPriceBillMode_itemStateChanged method
     */
    protected void comboPriceBillMode_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output prmtBuilding_dataChanged method
     */
    protected void prmtBuilding_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtMaxRoomPrice_actionPerformed method
     */
    protected void txtMaxRoomPrice_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output salesareatype_itemStateChanged method
     */
    protected void salesareatype_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output prmtRoomSelect_dataChanged method
     */
    protected void prmtRoomSelect_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output btnForecast_actionPerformed method
     */
    protected void btnForecast_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output kdtPriceAdjustEntry_editStopped method
     */
    protected void kdtPriceAdjustEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtPriceAdjustEntry_tableClicked method
     */
    protected void kdtPriceAdjustEntry_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kdtEntry_editStopped method
     */
    protected void kdtEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("sellProject.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("sellProject.id"));
        	sic.add(new SelectorItemInfo("sellProject.number"));
        	sic.add(new SelectorItemInfo("sellProject.name"));
		}
        sic.add(new SelectorItemInfo("priceBillType"));
        sic.add(new SelectorItemInfo("priceBillMode"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("avgBulidPrice"));
        sic.add(new SelectorItemInfo("avgRoomPrice"));
        sic.add(new SelectorItemInfo("maxBuildPrice"));
        sic.add(new SelectorItemInfo("maxRoomPrice"));
        sic.add(new SelectorItemInfo("minBuildPrice"));
        sic.add(new SelectorItemInfo("minRoomPrice"));
        sic.add(new SelectorItemInfo("createTime"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("preAdjValue"));
        sic.add(new SelectorItemInfo("aftAdjValue"));
        sic.add(new SelectorItemInfo("openBatch"));
    	sic.add(new SelectorItemInfo("priceAdjustEntry.priceType"));
    	sic.add(new SelectorItemInfo("priceAdjustEntry.oldSumAmount"));
    	sic.add(new SelectorItemInfo("priceAdjustEntry.newBuildingArea"));
    	sic.add(new SelectorItemInfo("priceAdjustEntry.oldBuildingPrice"));
    	sic.add(new SelectorItemInfo("priceAdjustEntry.newBuildingPrice"));
    	sic.add(new SelectorItemInfo("priceAdjustEntry.newRoomArea"));
    	sic.add(new SelectorItemInfo("priceAdjustEntry.oldRoomPrice"));
    	sic.add(new SelectorItemInfo("priceAdjustEntry.newRoomPrice"));
    	sic.add(new SelectorItemInfo("priceAdjustEntry.room.id"));
    	sic.add(new SelectorItemInfo("priceAdjustEntry.room.number"));
    	sic.add(new SelectorItemInfo("priceAdjustEntry.newSumAmount"));
    	sic.add(new SelectorItemInfo("priceAdjustEntry.room.displayName"));
    	sic.add(new SelectorItemInfo("priceAdjustEntry.Salesareatype"));
    	sic.add(new SelectorItemInfo("priceAdjustEntry.room.building.name"));
    	sic.add(new SelectorItemInfo("priceAdjustEntry.room.buildUnit.name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("valueEntry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("valueEntry.project"));
    	sic.add(new SelectorItemInfo("valueEntry.batch"));
    	sic.add(new SelectorItemInfo("valueEntry.build"));
    	sic.add(new SelectorItemInfo("valueEntry.productType"));
    	sic.add(new SelectorItemInfo("valueEntry.decorate"));
    	sic.add(new SelectorItemInfo("valueEntry.modelName"));
    	sic.add(new SelectorItemInfo("valueEntry.modelType"));
    	sic.add(new SelectorItemInfo("valueEntry.modelArea"));
    	sic.add(new SelectorItemInfo("valueEntry.account"));
    	sic.add(new SelectorItemInfo("valueEntry.area"));
    	sic.add(new SelectorItemInfo("valueEntry.date"));
    	sic.add(new SelectorItemInfo("valueEntry.price"));
    	sic.add(new SelectorItemInfo("valueEntry.calculateType"));
    	sic.add(new SelectorItemInfo("valueEntry.amount"));
    	sic.add(new SelectorItemInfo("valueEntry.remark"));
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
     * output actionExecute_actionPerformed method
     */
    public void actionExecute_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionExecute(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionExecute() {
    	return false;
    }

    /**
     * output ActionExecute class
     */     
    protected class ActionExecute extends ItemAction {     
    
        public ActionExecute()
        {
            this(null);
        }

        public ActionExecute(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionExecute.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExecute.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionExecute.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractRoomPriceAdjustEditUI.this, "ActionExecute", "actionExecute_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomPriceAdjustEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}