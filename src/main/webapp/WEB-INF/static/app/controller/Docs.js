Ext.define('Share.controller.Docs', {
    extend: 'Ext.app.Controller',

    stores: [ 'Docs' ],

    models: [  'Doc' ],

    views: ['DocGrid'],

    refs: [
        {
            ref: 'docGrid',
            selector: 'docgrid'
        },
        {
            ref: 'dirTree',
            selector: 'dirtree'
        }
    ],

    init: function () {
        this.control({
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
                success: function (form, action) {
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
        var records = this.getDocGrid().getSelectionModel().getSelection();
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
                var data = Ext.JSON.decode(response.responseText);
                this.getDocsStore().removeAll(records);
            },
            scope: this

        })
    }




});
