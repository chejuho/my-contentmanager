﻿
(function($) {
    if (jHtmlArea) {
        var oldForecolor = jHtmlArea.fn.forecolor;
        jHtmlArea.fn.forecolor = function(c) {
            if (c) {
                // If color is specified, then use the "default" method functionality
                oldForecolor.call(this, c);
            } else {
                // If no color is specified, then display color picker ui
                var that = this;
                var rng = this.getRange();
                jHtmlAreaColorPickerMenu($(".forecolor", this.toolbar), {
                    colorChosen: function(color) {
                        if ($.browser.msie) {
                            rng.execCommand("ForeColor", false, color);
                        } else {
                            that.forecolor(color);
                        }
                    }
                });
            }
        };
    }
    var menu = window.jHtmlAreaColorPickerMenu = function(ownerElement, options) {
        return new jHtmlAreaColorPickerMenu.fn.init(ownerElement, options);
    };
    menu.fn = menu.prototype = {
        jhtmlareacolorpickermenu: "0.7.0",

        init: function(ownerElement, options) {
            var opts = $.extend({}, menu.defaultOptions, options);
            var that = this;
            var owner = this.owner = $(ownerElement);
            var position = owner.position();

            if (menu.instance) {
                menu.instance.hide();
            }
            jHtmlAreaColorPickerMenu.instance = this;

            var picker = this.picker = $("<div/>").css({
                position: "absolute",
                left: position.left + opts.offsetLeft,
                top: position.top + owner.height() + opts.offsetTop,
                "z-index": opts["z-index"]
            }).addClass("jHtmlAreaColorPickerMenu");

            for (var i = 0; i < opts.colors.length; i++) {
                var c = opts.colors[i];
                $("<div/>").css("background-color", c).appendTo(picker).click(
                    (function(color) {
                        return function() {
                            if (opts.colorChosen) {
                                opts.colorChosen.call(this, color);
                            }
                            that.hide();
                        };
                    })(c)
                );
            }

            var autoHide = false;
            picker.appendTo(owner.parent()).
                show().
                mouseout(function() {
                    autoHide = true;
                    that.currentTimeout = window.setTimeout(function() { if (autoHide === true) { that.hide(); } }, 1000);
                }).
                mouseover(function() {
                    if (that.currentTimeout) {
                        window.clearTimeout(that.currentTimeout);
                        that.currentTimeout = null;
                    }
                    autoHide = false;
                });
        },
        hide: function() {
            this.picker.hide();
            this.picker.remove();
        }
    };
    menu.fn.init.prototype = menu.fn;

    menu.defaultOptions = {
        "z-index": 0,
        offsetTop: 0,
        offsetLeft: 0,
        colors: [
                 "","","","",""
        ],
        colorChosen: null
    };
})(jQuery);