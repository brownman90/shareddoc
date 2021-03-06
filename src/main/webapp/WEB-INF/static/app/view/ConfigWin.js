Ext.define('Share.view.ConfigWin', {
    extend: 'Commons.window.Window',
    alias: 'widget.configwin',

    title: 'Settings',
    width: 500,
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
                            anchor: '100%'
                        },
                        items: [
                            {
                                xtype: 'browserfield',
                                fieldLabel: 'Location',
                                name: 'location',
                                itemId: 'location',
                                combineErrors: true,
                                allowBlank: false,
                                browserWindow: {
                                    id: 'configBrowser',
                                    url: '/config/fs'
                                }
                            },
                            {
                                xtype: 'textslider',
                                name: 'limit',
                                itemId: 'limit',
                                fieldLabel: 'Max Capacity',
                                vtype: 'disk',
                                combineErrors: true,
                                allowBlank: false,
                                useTips: true,
                                tipText: function (thumb) {
                                    var max = thumb.slider.maxValue;
                                    var ratio = Math.floor(thumb.value * 1.0 / max * 100);
                                    var value = CommonsUtils.readableSize(thumb.value);
                                    return Ext.String.format("{0} - {1}%", value, ratio);
                                },
                                displayFormatter: CommonsUtils.readableSize,
                                sliderFormatter: CommonsUtils.unReadableSize
                            },
                            {
                                xtype: 'displayslider',
                                name: 'exceed',
                                itemId: 'exceed',
                                fieldLabel: 'Exceed Warn',
                                sliderStep: 5,
                                useTips: true,
                                tipText: function (thumb) {
                                    return thumb.value + "%";
                                },
                                displayFormatter: function (value) {
                                    return value + "%";
                                }
                            }
                        ]
                    }
                ]
            }
        ];

        this.callParent(arguments);
    }
});
