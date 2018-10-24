function loginLoaded() {
	var date_input = $('input[name="birthDate"]');
	var container = $('.bootstrap-iso form').length > 0 ? $('.bootstrap-iso form').parent() : "body";
	date_input.datepicker({
		format : 'dd/mm/yyyy',
		container : container,
		todayHighlight : true,
		autoclose : true,
	});
	console.log('Login Loaded script');
}