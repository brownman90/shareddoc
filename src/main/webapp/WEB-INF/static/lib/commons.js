Ext.define('Commons.Window', {
    extend: 'Ext.window.Window',
    alias: 'widget.commonswindow',

    layout: 'fit',
    autoShow: true,
    modal: true,
    collapsible: true,
    constrainHeader: true,
    maximizable : true,

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
    maximizable: true,

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

    alert: function(cfg, msg, fn, scope) {
        if (Ext.isString(cfg)) {
            cfg = {
                title : cfg,
                msg : msg,
                buttons: this.OK,
                fn: fn,
                scope : scope,
                minWidth: this.minWidth,
                icon : Ext.MessageBox.WARNING
            };
        }
        return this.show(cfg);
    },

    info: function(cfg, msg, fn, scope) {
        if (Ext.isString(cfg)) {
            cfg = {
                title : cfg,
                msg : msg,
                buttons: this.OK,
                fn: fn,
                scope : scope,
                minWidth: this.minWidth,
                icon : Ext.MessageBox.INFO
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
},function() {
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
    return this === null || this === "" || this === undefined;
}

String.prototype.isNotEmpty = function () {
    return !this.isEmpty();
}

