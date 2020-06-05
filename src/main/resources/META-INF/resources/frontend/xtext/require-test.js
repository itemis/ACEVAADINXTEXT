/*
 * works with '@JavaScript("./xtext/require-test.js")' in the respective java class
 *
 */


import { require, define } from "./require.js"


define("Some",[], function () {
	function Some() {};
	
	Some.prototype.doSome = function(str) { console.debug(`test-${str}`); };
	
	return Some;
});

require(["Some"], function(Some) {
	var some = new Some();
	some.doSome("some");
});
