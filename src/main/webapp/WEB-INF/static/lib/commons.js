Ext.define('Commons.window.Window', {
    extend: 'Ext.window.Window',
    alias: 'widget.commonswindow',

    layout: 'fit',
    autoShow: true,
    modal: true,
    collapsible: true,
    constrainHeader: true,

    initComponent: function () {
        var me = this;
        this.buttons = [
            {
                text: 'Save',
                action: 'save',
                handler: Ext.Function.bind(me.saveHandler, me)
            },
            {
                text: 'Cancel',
                scope: this,
                handler: this.close
            }
        ];

        this.callParent(arguments);
    }
});

Ext.define('Commons.window.SimpleWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.simplewindow',

    layout: 'fit',
    autoShow: true,
    modal: true,
    collapsible: true,
    constrainHeader: true,

    initComponent: function () {

        this.buttons = [
            {
                text: 'OK',
                scope: this,
                handler: this.close
            }
        ];

        this.callParent(arguments);
    }
});

Ext.define('Commons.MessageBox', {
    extend: 'Ext.window.MessageBox',
    alias: 'widget.commonsmessage',

    alert: function (cfg, msg, fn, scope) {
        if (Ext.isString(cfg)) {
            cfg = {
                title: cfg,
                msg: msg,
                buttons: this.OK,
                fn: fn,
                scope: scope,
                minWidth: this.minWidth,
                icon: Ext.MessageBox.WARNING
            };
        }
        return this.show(cfg);
    },

    info: function (cfg, msg, fn, scope) {
        if (Ext.isString(cfg)) {
            cfg = {
                title: cfg,
                msg: msg,
                buttons: this.OK,
                fn: fn,
                scope: scope,
                minWidth: this.minWidth,
                icon: Ext.MessageBox.INFO
            };
        }
        return this.show(cfg);
    },
    error: function (cfg, msg, fn, scope) {
        if (Ext.isString(cfg)) {
            cfg = {
                title: cfg,
                msg: msg,
                buttons: this.OK,
                fn: fn,
                scope: scope,
                minWidth: this.minWidth,
                icon: Ext.MessageBox.ERROR
            };
        }
        return this.show(cfg);
    }
}, function () {
    Ext.CommonsMsg = new this();
});

Ext.define('Commons.SimplePropertyGrid', {
    extend: Ext.grid.Panel,
    alias: 'widget.simplepropertygrid',
    valueField: 'value',
    nameField: 'name',
    enableColumnMove: false,
    columnLines: true,
    stripeRows: false,
    trackMouseOver: false,
    enableHdMenu: false,
    gridCls: Ext.baseCSSPrefix + 'property-grid',
    initComponent: function () {
        var me = this;
        me.source = me.source || {};
        me.addCls(me.gridCls);
        me.sourceConfig = Ext.apply({}, me.sourceConfig);
        if (!me.store) {
            me.propStore = me.store = new Ext.grid.property.Store(me, me.source);
        }
        me.configure(me.sourceConfig);
        me.columns = new Ext.grid.property.HeaderContainer(me, me.store);
        me.callParent();
        me.getView().walkCells = this.walkCells;
    },

    configure: function (config) {
        var me = this,
            store = me.store,
            i = 0,
            len = me.store.getCount(),
            nameField = me.nameField,
            valueField = me.valueField,
            name, value, rec, type;

        me.configureLegacy(config);
    },

    getConfig: function (fieldName, key, defaultValue) {
        var config = this.sourceConfig[fieldName],
            out;

        if (config) {
            out = config[key];
        }
        return out || defaultValue;
    },

    setConfig: function (fieldName, key, value) {
        var sourceCfg = this.sourceConfig,
            o = sourceCfg[fieldName];

        if (!o) {
            o = sourceCfg[fieldName] = {
                __copied: true
            };
        } else if (!o.__copied) {
            o = Ext.apply({
                __copied: true
            }, o);
            sourceCfg[fieldName] = o;
        }
        o[key] = value;
        return value;
    },


    configureLegacy: function (config) {
        var me = this,
            o, key, value;

        me.copyLegacyObject(config, me.customRenderers, 'renderer');
        me.copyLegacyObject(config, me.propertyNames, 'displayName');

    },

    copyLegacyObject: function (config, o, keyName) {
        var key, value;

        for (key in o) {
            if (o.hasOwnProperty(key)) {
                if (!config[key]) {
                    config[key] = {};
                }
                config[key][keyName] = o[key];
            }
        }
    },

    walkCells: function (pos, direction, e, preventWrap, verifierFn, scope) {
        if (direction == 'left') {
            direction = 'up';
        } else if (direction == 'right') {
            direction = 'down';
        }
        pos = Ext.view.Table.prototype.walkCells.call(this, pos, direction, e, preventWrap, verifierFn, scope);
        if (pos && !pos.column) {
            pos.column = 1;
        }
        return pos;
    },

    beforeDestroy: function () {
        var me = this;
        me.callParent();
        delete me.source;
    },

    setSource: function (source, sourceConfig) {
        var me = this;

        me.source = source;
        if (sourceConfig !== undefined) {
            me.sourceConfig = Ext.apply({}, sourceConfig);
            me.configure(me.sourceConfig);
        }
        me.propStore.setSource(source);
    },


    getSource: function () {
        return this.propStore.getSource();
    },


    setProperty: function (prop, value, create) {
        this.propStore.setValue(prop, value, create);
    },


    removeProperty: function (prop) {
        this.propStore.remove(prop);
    }

});

String.prototype.isEmpty = function () {
    return this == null || this == "" || this == undefined;
}

String.prototype.isNotEmpty = function () {
    return !this.isEmpty();
}


function CommonsUtils() {
}

CommonsUtils.readableSize = function (size) {
    var units = ['B', 'KB', 'MB', 'GB'];
    var i = 0;
    var resultSize = '0';
    var unit = units[0];
    while (size > 1024) {
        unit = units[++i];
        resultSize = Math.floor(size * 100 / 1024) / 100;
        size = size / 1024;
    }
    return resultSize + unit;
}

CommonsUtils.unReadableSize = function (value) {
    var units = ['KB', 'MB', 'GB'];
    var units = {
        'KB': 1024,
        'MB': 1024 * 1024,
        'GB': 1024 * 1024 * 1024
    };
    for (var k in units) {
        if (value.indexOf(k) > -1) {
            return value.split(k)[0] * units[k];
        }
    }
    return value;
}

Ext.define('Commons.Form.DisplaySliderField', {
    extend: 'Ext.form.FieldContainer',
    alias: 'widget.displayslider',
    layout: 'hbox',
    defaults: {
        hideLabel: 'true'
    },
    initComponent: function () {
        var me = this;
        this.items = [
            {
                xtype: 'displayfield',
                value: this.value,
                fieldStyle: 'padding-left:8px',
                flex: 2
            },
            {
                xtype: 'slider',
                name: this.name,
                value: this.value,
                increment: this.sliderStep,
                padding: '0 0 0 5',
                minValue: this.minValue || 0,
                maxValue: this.maxValue || 100,
                useTips: this.useTips,
                tipText: this.tipText,
                flex: 3,
                margins: '4 0 0 0',
                listeners: {
                    changecomplete: function (slider, value, thumb) {
                        var displayField = me.down("displayfield");
                        if (me.displayFormatter)
                            value = me.displayFormatter(value);
                        displayField.setValue(value);
                    }
                }
            }
        ]
        this.callParent(arguments);
    },

    setValue: function (value) {
        if (this.displayFormatter)
            this.down("displayfield").setValue(this.displayFormatter(value));
        else
            this.down("displayfield").setValue(value);
        this.down("slider").setValue(value);
    },

    setMaxValue: function (value) {
        this.down("slider").setMaxValue(value);
    },

    getValue: function () {
        return this.down("slider").getValue();
    },

    getFormatterValue: function () {
        return this.down("displayfield").getValue();
    }
});

Ext.define('Commons.Form.TextSliderField', {
    extend: 'Ext.form.FieldContainer',
    alias: 'widget.textslider',
    layout: 'hbox',
    defaults: {
        hideLabel: 'true'
    },
    initComponent: function () {
        var me = this;
        me.textType = "textfield";
        if (me.isNumber)
            me.textType = 'numberfield';
        this.items = [
            {
                xtype: me.textType,
                value: me.value,
                allowBlank: me.allowBlank,
                vtype: me.vtype,
                step: me.numberStep,
                min: me.min || 0,
                max: me.max || 0,
                flex: 2,
                listeners: {
                    blur: function (textfield) {
                        if (!textfield.isValid())
                            return;
                        var slider = me.down("slider");

                        var value = textfield.getValue();
                        if (me.sliderFormatter)
                            value = me.sliderFormatter(value);
                        slider.setValue(value);
                    }
                }
            },
            {
                xtype: 'slider',
                name: me.name,
                value: me.value,
                padding: '0 0 0 5',
                increment: me.sliderStep,
                minValue: me.minValue || 0,
                maxValue: me.maxValue || 100,
                useTips: me.useTips,
                tipText: me.tipText,
                flex: 3,
                margins: '4 0 0 0',
                listeners: {
                    changecomplete: function (slider, value) {
                        var textfield = me.down("textfield");
                        if (me.displayFormatter)
                            value = me.displayFormatter(value);
                        textfield.setValue(value);
                    }
                }
            }
        ]
        this.callParent(arguments);
    },

    setValue: function (value) {
        var me = this;
        if (me.displayFormatter)
            me.down("textfield").setValue(me.displayFormatter(value));
        else
            me.down("textfield").setValue(value);
        me.down("slider").setValue(value);
    },

    setMaxValue: function (max, animate) {
        var me = this;
        var slider = me.down("slider"),
            textfield = me.down("textfield");//TODO for number
        if (animate) {
            var ori = slider.getValue();
            slider.setValue(me.minValue);
            slider.setMaxValue(max);
            slider.setValue(ori);
        } else {
            this.down("slider").setMaxValue(max);
        }
    },

    getValue: function () {
        return this.down("slider").getValue();
    },

    getFormatterValue: function () {
        return this.down("textfield").getValue();
    },


});


Ext.apply(Ext.form.field.VTypes, {
    IP: function (v) {
        return /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/.test(v);
    },
    IPText: 'Must be a numeric IP address',
    IPMask: /[\d\.]/i
});

Ext.apply(Ext.form.field.VTypes, {
    disk: function (v) {
        return /^(\d(.\d+)?)*(G|M|K)?B$/.test(v);
    },
    diskText: 'Must add B/KB/MB/GB at the end of number with no blank.'
});

Ext.define('Commons.Form.BrowserField', {
    extend: 'Ext.form.FieldContainer',
    alias: 'widget.browserfield',
    layout: 'hbox',
    defaults: {
        hideLabel: 'true'
    },
    initComponent: function () {
        var me = this;
        me.browserWindow = Ext.apply({browserField: me}, me.browserWindow);
        me.items = [
            {
                xtype: 'textfield',
                anchor: '100%',
                flex: 1,
                name: me.name,
                readOnly: me.readOnly,
                allowBlank: me.allowBlank,
                listeners: {
                    blur: function () {
                        me.fireEvent('blur', me);
                    }
                }
            },
            {
                xtype: 'button',
                text: me.text || ' ... ',
                iconCls: me.iconCls,
                margin: '0 0 0 5',
                handler: function () {
                    Ext.widget("browserwindow", me.browserWindow).show();
                }
            }
        ];

        me.addEvents('blur');

        me.callParent(arguments);
    },

    setValue: function (value) {
        this.down("textfield").setValue(value);
    },

    getValue: function () {
        return this.down("textfield").getValue();
    }
});

Ext.define('Commons.window.BrowserWindow', {
    extend: 'Commons.window.Window',
    alias: 'widget.browserwindow',
    title: 'Browser',
    width: 350,
    height: 380,
    initComponent: function () {
        var me = this;

        var store = Ext.create('Ext.data.TreeStore', {
            fields: ['id', 'text'],
            proxy: {
                type: 'ajax',
                url: this.url
            }

        });
        this.items = [
            {
                xtype: 'treepanel',
                region: 'center',
                rootVisible: false,
                border: false,
                store: store,
                layout: 'fit',
                tbar: [
                    {
                        iconCls: 'home',
                        tooltip: 'Home Directory',
                        handler: function () {
                            Ext.Ajax.request({
                                url: me.url + "/home",
                                success: function (res) {
                                    var result = Ext.JSON.decode(res.responseText);
                                    var home = result.data;
                                    me.down("treepanel").selectPath(me.pathFormat(home), 'id', '$');
                                }
                            });
                        }
                    },
                    {
                        iconCls: 'location',
                        tooltip: 'Current Directory',
                        handler: function () {
                           me.selectCurrentLocation();
                        }
                    },
                    '-',
                    {
                        iconCls: 'add',
                        tooltip: 'New Folder ...',
                        disabled: true,
                        action: 'add',
                        handler: function () {
                            var node = me.down("treepanel").getSelectionModel().getLastSelected();
                            Ext.Msg.prompt("New Folder", "Enter a new folder name :", function (v, name) {
                                if (v == "ok") {
                                    Ext.Ajax.request({
                                        url: me.url + "/create",
                                        params: {
                                            id: node.raw.id + name,
                                            text: name
                                        },
                                        success: function (response) {
                                            var result = Ext.JSON.decode(response.responseText);
                                            if (result.success) {
                                                node.appendChild({id: node.raw.id + name + "/", text: name, loaded: true});
                                            } else {
                                                Ext.CommonsMsg.error('Error', result.message);
                                            }
                                            node.expand();
                                        }
                                    });
                                }
                            }, this);
                        }
                    },
                    {
                        iconCls: 'delete',
                        tooltip: 'Delete Folder',
                        disabled: true,
                        action: 'delete',
                        handler: function () {
                            var node = me.down("treepanel").getSelectionModel().getLastSelected();
                            var confirm = Ext.String.format("Are you sure you want to delete the directory {0} ?", node.raw.id);
                            Ext.Msg.confirm("Confirm", confirm, function (v) {
                                if (v === "yes") {
                                    Ext.Ajax.request({
                                        url: me.url + "/delete",
                                        params: {
                                            id: node.raw.id,
                                            text: node.raw.text
                                        },
                                        success: function (response) {
                                            var result = Ext.JSON.decode(response.responseText);
                                            if (result.success) {
                                                node.remove();
                                            } else {
                                                Ext.CommonsMsg.error('Error', result.message);
                                            }
                                        }
                                    });
                                }
                            }, this);
                        }
                    },
                    '-',
                    {
                        iconCls: 'refresh',
                        tooltip: 'Refresh',
                        handler: function () {
                            me.down("treepanel").getStore().reload();
                        }
                    }
                ],
                listeners: {
                    itemdblclick: function (tree, records) {

                    },
                    selectionchange: function ( tree, records ){
                        var addBtn = me.down("button[action=add]"),
                            deleteBtn = me.down("button[action=delete]");
                        if (records.length === 0) {
                            addBtn.disable();
                            deleteBtn.disable();
                        } else {
                            addBtn.enable();
                            deleteBtn.enable();
                        }
                    },
                    viewready: function () {
                        me.selectCurrentLocation();
                    }
                }

            }
        ];

        me.addEvents('afterSave');

        this.callParent(arguments);
    },

    saveHandler: function () {
        var me = this,
            tree = me.down("treepanel"),
            browserfield = me.browserField;

        var node = tree.getSelectionModel().getLastSelected();
        var location = node.raw.id;
        browserfield.setValue(location);

        if (me.hasListeners.afterSave) {
            me.fireEvent('afterSave', browserfield);
        }
        me.close();
    },

    pathFormat: function (value) {
        value = value.replace(/\//g, "$");
        value = value.replace(/:/g, ":/");
        var dirs = value.split("$");
        for (var i = 1; i < dirs.length; i++) {
            dirs[i] = dirs[i - 1] + "/" + dirs[i];
        }
        value = dirs.join("$");
        value = value.replace(/\/\//g, "\/");
        return "$root$" + value;
    },

    selectCurrentLocation: function(){
        var me = this;
        Ext.Ajax.request({
            url: me.url + "/current",
            success: function (res) {
                var result = Ext.JSON.decode(res.responseText);
                var current = result.data;
                me.down("treepanel").selectPath(me.pathFormat(current), 'id', '$');
            }
        });
    }
});



