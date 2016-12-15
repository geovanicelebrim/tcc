function getIP(document) {
	$(document).ready(function () {
	    $.getJSON("http://jsonip.com/?callback=?", function (data) {
	        console.log(data);
	        
	        $("input[id|='ip']").each(function (i, el) {
	            el.value = data.ip;
	        });
	    });
	});
}

function checkPassword() {
	var pass = $('#password').val();
	var conf = $('#confirm_password').val();
	
	if(pass == conf) {
		return true;
	} else {
		$('#error_password').show();
		$('#password').val("");
		$('#confirm_password').val("");
		$('#password').focus();
		return false;
	}
}

function validateEmail(idEmail) {
	var email = $(idEmail).val();
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    
    if (!re.test(email)) {
    	document.getElementById('error_email').style.display='block';
    	$('#email').focus();
    }
    
    return re.test(email);
}

function permissionFile() {
	if (!(file1 == true && file2 == true)) {
		alert("Select the files before submit.");
		return false;
	}
	return true;
}

function scrollTo(element) {
	var scrollTo = $(element);

	$('html, body').animate({
		scrollTop : scrollTo.offset().top + 300
	}, 1000);
}