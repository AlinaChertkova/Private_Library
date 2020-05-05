$(document).on('click','#reg-button', function(){
    var pass = $( "#password" ).val();
    var passRep = $( "#rep-password" )
    var passRepVal = $( "#rep-password" ).val();

    if (pass != passRepVal) {
    	passRep.val("");
		passRep.addClass( "warning" );
		passRep.focus();

		return false;
    }
});
