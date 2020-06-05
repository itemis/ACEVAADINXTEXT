/*
 * use '@JavaScript("./xtext/jquery-test.js")' to test
 */

import { jQuery } from "./jquery.js";


var soome = async function () {
	var test = "jquery";
	var test_function = function () { }
	console.debug("calling jquery");
	console.debug(jQuery.isFunction(test));
	console.debug(jQuery.isFunction(test_function));
}();