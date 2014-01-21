Ext.define('Share.view.ConfigWin', {
    extend: 'Commons.Window',
    alias: 'widget.configwin',

    title: 'Settings',
    width: 450,
    height: 400,
    initComponent: function () {
        this.items = [
            {
                xtype: 'form',
                padding: 10,
                border: false,
                items: [
                    {
                        xtype: 'fieldset',
                        title: 'Storage',
                        defaults: {
                            msgTarget: 'side',
                            labelWidth: 75,
                            anchor: '100%'
                        },
                        items: [
                            {
                                xtype: 'textfield',
                                name: 'location',
                                fieldLabel: 'Location',
                                allowBlank: false
                            },
                            {
                                xtype: 'fieldcontainer',
                                fieldLabel: 'Limit (MB)',
                                layout: 'hbox',
//                                combineErrors: true,
//                                defaultType: 'textfield',
                                defaults: {
                                    hideLabel: 'true'
                                },
                                items: [
                                    {
                                        xtype: 'displayfield',
                                        fieldLabel: 'Limit (MB)',
                                        value: '0MB - 0%',
                                        flex: 1
                                    },
                                    {
                                        xtype: 'slider',
                                        name: 'limit',
                                        hideEmptyLabel: false,
//                                        disabled: true,
                                        value: 0,
                                        minValue: 0,
                                        maxValue: 100,
                                        useTips: true,
                                        tipText: function (thumb) {
                                            var max = thumb.slider.maxValue;
                                            var ratio = Math.floor(thumb.value * 1.0 / max * 100);
                                            var value = Math.floor(thumb.value / 1024 / 1024) + "MB";
                                            return Ext.String.format("{0} - {1}%", value, ratio);
                                        },
                                        listeners: {
                                            changecomplete: function (slider, value, thumb) {
                                                var display = slider.up("form").down("displayfield");
                                                var max = thumb.slider.maxValue;
                                                var ratio = Math.floor(thumb.value * 1.0 / max * 100);
                                                var value = Math.floor(thumb.value / 1024 / 1024) + "MB";
                                                display.setValue(Ext.String.format("{0} - {1}%", value, ratio));
                                            }
                                        },
                                        flex: 2,
                                        margins: '4 0 0 0'
                                    }
                                ]
                            }
                        ]
                    }
                ]
            }
        ];

        this.callParent(arguments);
    }
});
