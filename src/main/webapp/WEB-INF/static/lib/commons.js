Ext.define('Commons.Window', {
    extend: 'Ext.window.Window',
    alias: 'widget.commonswindow',

    layout: 'fit',
    autoShow: true,
    modal: true,
    collapsible: true,
    constrainHeader: true,

    initComponent: function () {

        this.buttons = [
            {
                text: 'Save',
                action: 'save'
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

Ext.define('Commons.SimpleWindow', {
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

CommonsUtils.disReadableSize = function (value) {
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
        this.textType = "textfield";
        if (me.isNumber)
            this.textType = 'numberfield';
        this.items = [
            {
                xtype: this.textType,
                value: this.value,
                allowBlank: this.allowBlank,
                vtype: this.vtype,
                step: this.numberStep,
                min: this.min || 0,
                max: this.max || 0,
                flex: 2,
                listeners: {
                    blur: function (field) {
                        if (!field.isValid())
                            return;
                        var value = field.getValue();
                        var slider = me.down("slider");
                        if (me.sliderFormatter)
                            value = me.sliderFormatter(value);
                        slider.setValue(value);
                    }
                }
            },
            {
                xtype: 'slider',
                name: this.name,
                value: this.value,
                padding: '0 0 0 5',
                increment: this.sliderStep,
                minValue: this.minValue || 0,
                maxValue: this.maxValue || 100,
                useTips: this.useTips,
                tipText: this.tipText,
                flex: 3,
                margins: '4 0 0 0',
                listeners: {
                    changecomplete: function (slider, value, thumb) {
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
        if (this.displayFormatter)
            this.down("textfield").setValue(this.displayFormatter(value));
        else
            this.down("textfield").setValue(value);
        this.down("slider").setValue(value);
    },

    setMaxValue: function (value) {
        this.down("slider").setMaxValue(value);
    }
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
