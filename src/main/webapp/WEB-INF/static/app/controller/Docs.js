Ext.define('Share.controller.Docs', {
    extend: 'Ext.app.Controller',

    stores: [ 'Docs' ],

    models: [  'Doc' ],

    views: ['DocGrid', 'ConfigWin'],

    refs: [
        {
            ref: 'docGrid',
            selector: 'docgrid'
        },
        {
            ref: 'dirTree',
            selector: 'dirtree'
        },
        {
            ref: 'configWin',
            selector: 'configwin'
        },
        {
            ref: 'docLogGrid',
            selector: 'docloggrid'
        }
    ],

    init: function () {
        this.control({
            'docgrid ': {
                selectionchange: this.selectionchange
            },
            'docgrid  button[action=search]': {
                click: this.searchDocs
            },
            'docgrid button[action=upload]': {
                click: this.upload
            },
            'docgrid button[action=selections]': {
                toggle: this.toggleSelections
            },
            'docgrid button[action=delete]': {
                click: this.deleteDocs
            },
            'docgrid tool[action=settings]': {
                click: this.openConfigWin
            },
            'configwin browserfield[itemId=location]': {
                blur: this.checkLocationSize
            },
            'browserwindow[id=configBrowser]': {
                afterSave: this.checkLocationSize
            },
            'configwin button[action=save]': {
                click: this.saveConfig
            }

        });
    },

    searchDocs: function () {
        var nameField = this.getDocGrid().down("textfield[name=name]");
        var store = this.getDocsStore();
        store.proxy.setExtraParam("name", nameField.getValue());
        store.load();
    },

    upload: function () {
        var docLogGrid = this.getDocLogGrid();
        var pathField = this.getDocGrid().down("hiddenfield[name=path]");
        if (pathField.getValue() == "") {
            Ext.CommonsMsg.alert("Warning", "Please select a directory in left directory navigation.");
            return;
        }
        var store = this.getDocsStore();
        var form = this.getDocGrid().down("form").getForm();
        if (form.isValid()) {
            form.submit({
                url: '/doc/upload',
                waitMsg: 'Uploading your shared document ...',
                success: function (form, data) {
                    store.reload();
                },
                failure: function (form, data) {
                    Ext.CommonsMsg.error("Error", data.result.message);
                }
            });
        } else {
            Ext.CommonsMsg.alert("Warnning", "Please select a document to upload.");
        }
    },

    toggleSelections: function (btn, pressed) {
        if (pressed) {
            this.getDocGrid().columns[0].show();
            btn.setTooltip("Disable Multi-Select");
        } else {
            this.getDocGrid().columns[0].hide();
            btn.setTooltip("Enable Multi-Select");
        }
    },

    deleteDocs: function (btn) {
        var docLogGrid = this.getDocLogGrid();
        var store = this.getDocsStore();
        var records = this.getDocGrid().getSelectionModel().getSelection();
        var confirm = "Are you sure you want to delete " + (records.length == 1 ? "this document." : "these " + records.length + " documents.");
        Ext.Msg.confirm("Confirm", confirm, function (v) {
            if (v === "yes") {
                var idList = new Array();
                Ext.Array.each(records, function (name, index) {
                    idList.push(records[index].raw.id);
                });
                Ext.Ajax.request({
                    url: '/doc/deletes',
                    params: {
                        idList: idList
                    },
                    success: function (response) {
                        var result = Ext.JSON.decode(response.responseText);
                        if (result.success) {
                            store.remove(records);
                        }
                        else
                            Ext.CommonsMsg.error('Error', result.message);
                    }
                })
            }
        });
    },

    selectionchange: function (selModel, records) {
        var grid = this.getDocGrid();
        var deleteBtn = grid.down("button[action=delete]"),
            downloadBtn = grid.down("button[action=download]");

        if (records.length === 0) {
            deleteBtn.disable();
            downloadBtn.disable();
        } else {
            deleteBtn.enable();
            downloadBtn.enable();
        }

    },

    openConfigWin: function () {
        Ext.widget("configwin").show();

        var form = this.getConfigWin().down("form");
        form.load({
            url: '/config/get',
            success: function (f, action) {
                var result = action.result.data;
                var limit = result.limit,
                    exceed = result.exceed,
                    freeSpace = result.freeSpace;

                var limitField = form.down("textslider[itemId=limit]");
                if (freeSpace < limit)
                    limit = freeSpace;
                limitField.setMaxValue(freeSpace);

                limitField.setValue(limit);

                var exceedField = form.down("displayslider[itemId=exceed]");
                exceedField.setValue(exceed);


            }
        });
    },

    checkLocationSize: function (field) {
        var limitSlider = field.up("form").down("textslider[itemId=limit]");
        var path = field.getValue();
        if (path === "")  return;
        Ext.Ajax.request({
            url: '/config/space',
            params: {
                path: path
            },
            success: function (res) {
                var result = Ext.JSON.decode(res.responseText);
                if (result.success) {
                    var freeSize = result.data;
                    limitSlider.setMaxValue(freeSize, true);
                } else
                    Ext.CommonsMsg.error('Error', result.message);
            }
        });
    },

    saveConfig: function(btn){
        var win = btn.up("window"),
            form = win.down("form").getForm();
        if(form.isValid()){
            form.submit({
                url: '/config/set',
                waitMsg: 'Setting SDP configuration ...',
                success: function (form, data) {
                    win.close();
                },
                failure: function (form, data) {
                    Ext.CommonsMsg.error("Error", data.result.message);
                }
            });
        }
    }
});
