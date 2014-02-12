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
                click: this.refreshNode
            },
            'dirmenu menuitem[action=refresh]': {
                click: this.refreshNode
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
        var pathField = this.getDocGrid().down("hiddenfield[name=path]");
        var path = record.raw.id;

        var docStore = this.getDocsStore();
        if (path == pathField.getValue()) {
            return;
        }
        pathField.setValue(path);
        docStore.proxy.setExtraParam("path", path);
        docStore.load();


    },

    nodeContext: function (tree, node, item, index, e) {
        e.stopEvent();
        Share.TreeMenu.showAt(e.getXY());
        var deleteBtn = Share.TreeMenu.down('menuitem[action=delete]');
        if (node.isRoot()) {
            deleteBtn.disable();
        } else {
            deleteBtn.enable();
        }
    },

    refreshNode: function () {
        var tree = this.getDirTree(),
            store = this.getDirsStore();
        var node = tree.getSelectionModel().getLastSelected();
        store.load({node: node});
    },

    createDir: function () {
        var me = this;
        var tree = this.getDirTree();
        var node = tree.getSelectionModel().getLastSelected();
        Ext.Msg.prompt("New Folder", "Enter a new folder name :", function (v, name) {
            if (v == "ok") {
                name = Ext.String.trim(name);
                if (name === '') {
                    Ext.CommonsMsg.error('Error', 'Folder name can not be empty.', Ext.Function.bind(me.createDir, me));
                    return;
                }
                Ext.Ajax.request({
                    url: '/dir/create',
                    params: {
                        id: node.raw.id + "/" + name,
                        text: name
                    },
                    success: function (response) {
                        var result = Ext.JSON.decode(response.responseText);
                        if (result.success) {
                            node.appendChild({id: node.raw.id + "/" + name, text: name, loaded: true});
                            tree.selectPath(node.lastChild.getPath('text'), 'text');
                            tree.fireEvent('itemclick', this, tree.getSelectionModel().getLastSelected());
                        } else {
                            Ext.CommonsMsg.error('Error', result.message, Ext.Function.bind(me.createDir, me));
                        }
                        node.expand();
                    }
                });
            }
        }, this);
    },

    deleteDir: function () {
        var tree = this.getDirTree();
        var node = tree.getSelectionModel().getLastSelected();
        var parentNode = node.parentNode;
        var confirm = Ext.String.format("Are you sure you want to delete the directory {0} ?", node.raw.id);
        Ext.Msg.confirm("Confirm", confirm, function (v) {
            if (v === "yes") {
                Ext.Ajax.request({
                    url: '/dir/delete',
                    params: {
                        id: node.raw.id,
                        text: node.raw.text
                    },
                    success: function (response) {
                        var result = Ext.JSON.decode(response.responseText);
                        if (result.success) {
                            node.remove();
                            tree.selectPath(parentNode.getPath('text'), 'text');
                            tree.fireEvent('itemclick', this, parentNode);
                        } else {
                            Ext.CommonsMsg.error('Error', result.message);
                        }
                    }
                });
            }
        }, this);
    }

});
