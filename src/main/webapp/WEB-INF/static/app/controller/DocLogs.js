Ext.define('Share.controller.DocLogs', {
    extend: 'Ext.app.Controller',

    stores: [ 'DocLogs' ],

    models: [  'DocLog' ],

    views: ['DocLogGrid'],

    init: function () {
        this.control({
            'docloggrid ': {
                resize: this.afterResize
            }
        });
    },

    afterResize: function (grid, width, height, oldWidth, oldHeight) {
        var n = Math.floor((height - 57) / 30);
        var store = this.getDocLogsStore();
        store.pageSize = n != 0 ? n : 1;
        store.load();

    }

});