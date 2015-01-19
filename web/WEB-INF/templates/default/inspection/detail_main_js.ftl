<script src="/assets/js/fuelux/fuelux.wizard.min.js"></script>
<script src="/assets/js/jquery.validate.min.js"></script>
<script src="/assets/js/additional-methods.min.js"></script>
<script src="/assets/js/bootbox.min.js"></script>
<script src="/assets/js/jquery.maskedinput.min.js"></script>
<script src="/assets/js/select2.min.js"></script>
<script src="/assets/js/chosen.jquery.min.js"></script>

<script type="text/javascript">
    jQuery(function($) {

        $('[data-rel=tooltip]').tooltip();

        $(".select2").css('width','200px').select2({allowClear:true})
                .on('change', function(){
                    $(this).closest('form').validate().element($(this));
                });

        var $validation = false;
        $('#fuelux-wizard').ace_wizard().on('change' , function(e, info){
            if(info.step == 1 && $validation) {
                if(!$('#validation-form').valid()) return false;
            }
        }).on('finished', function(e) {
        	if ($("#form-field-select-2").val()==null||$("#form-field-select-2").val()=="") {
        		bootbox.dialog({
                        message: "请至少选择一个重点评估项!",
                        buttons: {
                            "success" : {
                                "label" : "OK",
                                "className" : "btn-sm btn-primary"
                            }
                        }
                    });
                $("#form-field-select-2").focus();
        	}
        	else if ($("#form-field-select-3").val()==null||$("#form-field-select-3").val()=="") {
        		bootbox.dialog({
                        message: "请至少选择一个评估发现重点问题!",
                        buttons: {
                            "success" : {
                                "label" : "OK",
                                "className" : "btn-sm btn-primary"
                            }
                        }
                    });
                $("#form-field-select-3").focus();
        	}
        	else if ($("#form-field-select-4").val()==null||$("#form-field-select-4").val()=="") {
        		bootbox.dialog({
                        message: "请至少选择一个不合格优先排序指标!",
                        buttons: {
                            "success" : {
                                "label" : "OK",
                                "className" : "btn-sm btn-primary"
                            }
                        }
                    });
                $("#form-field-select-4").focus();
        	}
        	else if ($("#form-field-select-5").val()==null||$("#form-field-select-5").val()=="") {
        		bootbox.dialog({
                        message: "请至少选择一个日报周报不合格指标项!",
                        buttons: {
                            "success" : {
                                "label" : "OK",
                                "className" : "btn-sm btn-primary"
                            }
                        }
                    });
                $("#form-field-select-5").focus();
        	}
        	else {
        		$("#addInspection").submit();
        	}

                }).on('stepclick', function(e){
                    //return false;//prevent clicking on steps
                });
        $(".chosen-select").chosen();
//        $('#chosen-multiple-style').on('click', function(e){
//            var target = $(e.target).find('input[type=radio]');
//            var which = parseInt(target.val());
//            if(which == 2) $('#form-field-select-2').addClass('tag-input-style');
//            else $('#form-field-select-2').removeClass('tag-input-style');
//        });

        $('#skip-validation').removeAttr('checked').on('click', function(){
            $validation = this.checked;
            if(this.checked) {
                $('#sample-form').hide();
                $('#validation-form').removeClass('hide');
            }
            else {
                $('#validation-form').addClass('hide');
                $('#sample-form').show();
            }
        });

        //documentation : http://docs.jquery.com/Plugins/Validation/validate


        $.mask.definitions['~']='[+-]';
        $('#phone').mask('(999) 999-9999');

        jQuery.validator.addMethod("phone", function (value, element) {
            return this.optional(element) || /^\(\d{3}\) \d{3}\-\d{4}( x\d{1,6})?$/.test(value);
        }, "Enter a valid phone number.");

        $('#validation-form').validate({
            errorElement: 'div',
            errorClass: 'help-block',
            focusInvalid: false,
            rules: {
                email: {
                    required: true,
                    email:true
                },
                password: {
                    required: true,
                    minlength: 5
                },
                password2: {
                    required: true,
                    minlength: 5,
                    equalTo: "#password"
                },
                name: {
                    required: true
                },
                phone: {
                    required: true,
                    phone: 'required'
                },
                url: {
                    required: true,
                    url: true
                },
                comment: {
                    required: true
                },
                state: {
                    required: true
                },
                platform: {
                    required: true
                },
                subscription: {
                    required: true
                },
                gender: 'required',
                agree: 'required'
            },

            messages: {
                email: {
                    required: "Please provide a valid email.",
                    email: "Please provide a valid email."
                },
                password: {
                    required: "Please specify a password.",
                    minlength: "Please specify a secure password."
                },
                subscription: "Please choose at least one option",
                gender: "Please choose gender",
                agree: "Please accept our policy"
            },

            invalidHandler: function (event, validator) { //display error alert on form submit
                $('.alert-danger', $('.login-form')).show();
            },

            highlight: function (e) {
                $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
            },

            success: function (e) {
                $(e).closest('.form-group').removeClass('has-error').addClass('has-info');
                $(e).remove();
            },

            errorPlacement: function (error, element) {
                if(element.is(':checkbox') || element.is(':radio')) {
                    var controls = element.closest('div[class*="col-"]');
                    if(controls.find(':checkbox,:radio').length > 1) controls.append(error);
                    else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
                }
                else if(element.is('.select2')) {
                    error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
                }
                else if(element.is('.chosen-select')) {
                    error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
                }
                else error.insertAfter(element.parent());
            },

            submitHandler: function (form) {
            },
            invalidHandler: function (form) {
            }
        });




        $('#modal-wizard .modal-header').ace_wizard();
        $('#modal-wizard .wizard-actions .btn[data-dismiss=modal]').removeAttr('disabled');
    })
</script>