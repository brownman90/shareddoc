Ext.define('Share.controller.Dirs', {
    extend: 'Ext.app.Controller',

    stores: [ 'Dirs', 'Docs' ],

    models: [  'Dir' ],

    views: ['DirTree', 'DirMenu' ],


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
            'viewport > dirtree': {
                itemclick: this.nodeClick,
                itemcontextmenu: this.nodeContext
            },
            'dirtree tool[action=refresh]': {
                click: this.refreshTree
            },
            'dirmenu menuitem[action=refresh]': {
                click: this.refreshTree
            },
            'dirmenu menuitem[action=create]': {
                click: this.createDir
            },
            'dirmenu menuitem[action=delete]': {
                click: this.deleteDir
            }

        });
    },

    nodeClick: function (tree, record) {
        var path = record.raw.id;
        var docStore = this.getDocsStore();
        if (path == docStore.proxy.extraParams.path) {
            return;
        }
        docStore.proxy.setExtraParam("name", "");
        docStore.proxy.setExtraParam("path", path);
        docStore.load();
    },

    refreshTree: function () {
        this.getDirsStore().reload();
    },

    nodeContext: function (tree, node, item, index, e) {
        e.stopEvent();
        Share.TreeMenu.showAt(e.getXY());
//        var refreshBtn = AM.TreeMenu.down('menuitem[id=tree-menu-refresh]');
//        if (node.isLeaf()) {
//            refreshBtn.disable();
//        } else {
//            refreshBtn.enable();
//        }
    },

    refreshNode: function () {

    },

    createDir: function () {
        var node = this.getDirTree().getSelectionModel().getLastSelected();
        Ext.Msg.prompt("New Folder", "Enter a new folder name :", function (v, name) {
            if (v == "ok") {
                var operation = new Ext.data.Operation({
                    action: 'create',
                    params: {
                        id: node.raw.id + name,
                        text: name
                    }
                });
                var store = this.getDirsStore();
                store.getProxy().create(operation, function (data) {
                    var result = Ext.JSON.decode(data.response.responseText);
                    if (result.status) {
                        node.appendChild({id: node.raw.id + name + "/", text: name, loaded: true});
                    } else {
                        Ext.CommonsMsg.error('Error', result.message);
                    }
                    node.expand();
                });
            }
        });
    },

    deleteDir: function () {
        var node = this.getDirTree().getSelectionModel().getLastSelected();
        var confirm = Ext.String.format("Are you sure you want to delete the directory {0} ?", node.raw.id);
        Ext.Msg.confirm("Confirm", confirm, function (v) {
            if (v === "yes") {
                var operation = new Ext.data.Operation({
                    action: 'destroy',
                    params: {
                        id: node.raw.id,
                        text: node.raw.text
                    }
                });
                var store = this.getDirsStore();
                store.getProxy().destroy(operation, function (data) {
                    var result = Ext.JSON.decode(data.response.responseText);
                    if (result.status) {
                        node.remove(true);
                    } else {
                        Ext.CommonsMsg.error('Error', result.message);
                    }
                });
            }
        }, this);
    }

});
