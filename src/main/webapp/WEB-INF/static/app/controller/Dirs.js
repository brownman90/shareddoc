Ext.define('Share.controller.Dirs', {
    extend: 'Ext.app.Controller',

    stores: [ 'Dirs', 'Docs' ],

    models: [  'Dir' ],

    views: ['DirTree' ],



    init: function () {
        this.control({
            'viewport > dirtree' : {
                itemclick : this.nodeClick
            },
            'dirtree tool[id=refresh-tree-btn]':{
                click : this.refreshTree
            }

        });
    },

    nodeClick : function(tree, record){
        var path = record.raw.id;
        var docStore = this.getDocsStore();
        docStore.proxy.setExtraParam("name", "");
        docStore.proxy.setExtraParam("path", path);
        docStore.load();
    },

    refreshTree : function(btn){
        this.getDirsStore().reload();
    }



});
