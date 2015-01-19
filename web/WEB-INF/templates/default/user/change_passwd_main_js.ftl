<script type="text/javascript">
    jQuery(function ($) {
        var reg=/^[0-9a-zA-Z_]{6,15}$/;
        var aOk = false;
        var bOk = false;
        $('#newpasswd').on('input', function () {
            if (reg.test($('#newpasswd').val())) {
                $('#tipa').html("");
                aOk=true;
            }else {
                $('#tipa').html("*请输入6-15位密码(大小写字母、数字或下划线)");
                aOk=false;
            }
            if (null!=$('#confirm').val()&&""!=$('#confirm').val()) {
                if ($('#newpasswd').val()==$('#confirm').val()) {
                    $('#tipb').html("");
                    bOk=true;
                }else {
                    $('#tipb').html("*两次密码输入不一致");
                    bOk=false;
                }
            }

            if (aOk&&bOk) {
                $('#btnSubmit').removeAttr("disabled");
            }
        });
        $('#confirm').on('input', function () {
            if ($('#newpasswd').val()==$('#confirm').val()) {
                $('#tipb').html("");
                bOk=true;
            }else {
                $('#tipb').html("*两次密码输入不一致");
                bOk=false;
            }
            if (aOk&&bOk) {
                $('#btnSubmit').removeAttr("disabled");
            }
        });
    })
</script>