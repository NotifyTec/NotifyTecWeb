$.fn.extend({
    getFormData: function () {
        var obj = {};
        
        $(this).find("input").each(function (i, input) {
            var $input = $(input);
            obj[$input.prop("name")] = $input.val();
        });

        return obj;
    }
});