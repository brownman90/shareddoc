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
            'configwin textfield[name=location]': {
                blur: this.checkLocationSize
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
        var selectedNodes = this.getDirTree().getSelectionModel().getSelection();
        if (selectedNodes.length == 0) {
            Ext.CommonsMsg.alert("Warnning", "Please select a directory in left navigation tree.");
            return;
        }
        var path = this.getDirTree().getSelectionModel().getSelection()[0].raw.id;
        var store = this.getDocsStore();
        var form = this.getDocGrid().down("form").getForm();
        if (form.isValid()) {
            form.submit({
                url: '/doc/upload',
                params: {
                    path: path
                },
                waitMsg: 'Uploading your shared document ...',
                success: function (form, data) {
                    var result = data.result;
                    if (result.status)
                        store.reload();
                    else
                        Ext.CommonsMsg.error("Error", result.message);
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
                        if (result.status)
                            store.removeAll(records);
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
        Ext.create("Share.view.ConfigWin").show();

        var form = this.getConfigWin().down("form");
        form.load({
            url: '/config/settings',
            success: function (f, action) {
                var result = action.result.data;

                var limitField = form.down("textslider[itemId=limit]");
                limitField.setMaxValue(result.freeSpace);
                limitField.setValue(result.limit);

                var exceedField = form.down("displayslider[itemId=exceed]");
                exceedField.setValue(result.exceed);
            }
        });
    },

    checkLocationSize: function (field) {
        var limitSlider = field.up("form").down("slider[itemId=limit]");
        var path = field.getValue();
        Ext.Ajax.request({
            url: '/config/space',
            params: {
                path: path
            },
            success: function (res) {
                var result = Ext.JSON.decode(res.responseText);
                if (result.status) {
                    var freeSize = result.data;
                    var limitSlider = field.up("form").down("slider[itemId=limit]");
                    limitSlider.setMaxValue(freeSize);

                } else
                    Ext.CommonsMsg.error('Error', result.message);
            }
        });
    }
});
