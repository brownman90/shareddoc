Ext.define('Share.controller.Docs', {
    extend: 'Ext.app.Controller',

    stores: [ 'Docs' ],

    models: [  'Doc' ],

    views: ['DocList'],

    refs: [
        {
            ref: 'docList',
            selector: 'doclist'
        },
        {
            ref: 'dirTree',
            selector: 'dirtree'
        }
    ],

    init: function () {
        this.control({
            'viewport > userlist': {
                itemdblclick: this.openProperties,
                itemcontextmenu: this.userlistContext
            },
            'doclist  button[action=search]': {
                click: this.searchDocs
            },
            'doclist button[action=upload]': {
                click: this.upload
            }

        });
    },

    searchDocs: function () {
        var nameField = this.getDocList().down("textfield[name=name]");
        var store = this.getDocsStore();
        store.proxy.setExtraParam("name", nameField.getValue());
        store.load();
    },

    upload : function(){
        var selectedNodes = this.getDirTree().getSelectionModel().getSelection();
        if (selectedNodes.length == 0) {
            Ext.CommonsMsg.alert("Warnning", "Please select a directory in left navigation tree.");
            return;
        }
        var path = this.getDirTree().getSelectionModel().getSelection()[0].raw.id;
        var store = this.getDocsStore();
        var form = this.getDocList().down("form").getForm();
        if(form.isValid()){
            form.submit({
                url: '/doc/upload',
                params: {
                    path: path
                },
                waitMsg: 'Uploading your shared document ...',
                success: function(form, action) {
                    store.reload();
                },
                failure : function(){

                }
            });
        } else {
            Ext.CommonsMsg.alert("Warnning", "Please select a document to upload.");
        }

    }


});
