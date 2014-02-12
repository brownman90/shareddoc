Ext.define('Share.controller.DocLogs', {
    extend: 'Ext.app.Controller',

    stores: [ 'DocLogs' ],

    models: [  'DocLog' ],

    views: ['DocLogGrid']

});