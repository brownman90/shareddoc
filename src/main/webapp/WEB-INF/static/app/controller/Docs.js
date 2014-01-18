Ext.define('Share.controller.Docs', {
    extend: 'Ext.app.Controller',

    stores: [ 'Docs' ],

    models: [  'Doc' ],

    views: ['DocList' ],

    refs: [
        {
            ref: 'docList',
            selector: 'doclist'
        },
        {
            ref: 'usersTree',
            selector: 'usertree'
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
        this.getDocsStore().load();
    },

    upload : function(){
        var store = this.getDocsStore();
        var form = this.getDocList().down("form").getForm();
        if(form.isValid()){
            form.submit({
                url: '/doc/upload',
                waitMsg: 'Uploading your shared document ...',
                success: function(form, action) {
                    store.reload();
                },
                failure : function(){

                }
            });
        }

    }


});
