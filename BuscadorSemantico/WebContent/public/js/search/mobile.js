var mobile = (/iphone|ipad|ipod|android|blackberry|mini|windows\sce|palm/i.test(navigator.userAgent.toLowerCase()));

function focusOnlyNotMobile(input) {
	if(mobile) {
		
	} else {
		$(input).focus();
	}
}