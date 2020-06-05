/*
 * works with 
 * '@JavaScript("./xtext/require-test.js")' and
 * '@JavaScript("./xtext/require-test-import.js")' 
 * in the respective java class
 *
 */

import { require, define } from "./require.js"


require(["Some"], function(Some) {
	var some = new Some();
	some.doSome("other");
});
